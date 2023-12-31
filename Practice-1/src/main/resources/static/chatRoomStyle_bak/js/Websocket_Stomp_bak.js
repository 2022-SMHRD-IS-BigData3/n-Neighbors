// Websocket & Stomp initialize
var sock = new SockJS("/ws/chat");
var ws = Stomp.over(sock);
var reconnect = 0;

// Vue.js
var vm = new Vue({
	el: '#app',
	data: {
		roomId: '',
		room: {},
		sender: '',
		message: '',
		messages: []
	},
	created() {
		this.roomId = "세종대화";
		this.sender = localStorage.getItem('wschat.sender');
		this.findRoom();
	},
	methods: {
		findRoom: function() {
			axios.get('/chat/room/' + this.roomId).then(response => {
				this.room = response.data;
			});
		},
		sendMessage: function() {
			ws.send("/app/chat/message", {}, JSON.stringify({
				type: 'TALK',
				roomId: this.roomId,
				sender: this.userNick,
				message: this.message
			}));
			this.message = '';
		},
		recvMessage: function(recv) {
			this.messages.unshift({
				"type": recv.type,
				"sender": recv.type == 'ENTER' ? '[알림]' : recv.userNick,
				"message": recv.message
			});
		}
	}
});

function connect() {
	ws.connect({}, function(frame) {
		ws.subscribe("/topic/chat/room/" + vm.$data.roomId, function(message) {
			var recv = JSON.parse(message.body);
			vm.recvMessage(recv);
		});
		ws.send("/app/chat/message", {}, JSON.stringify({
			type: 'ENTER',
			roomId: vm.$data.roomId,
			sender: vm.$data.userNick
		}));
	}, function(error) {
		if (reconnect++ <= 5) {
			setTimeout(function() {
				console.log("connection reconnect");
				sock = new SockJS("/ws/chat");
				ws = Stomp.over(sock);
				connect();
			}, 10 * 1000);
		}
	});
}

connect();