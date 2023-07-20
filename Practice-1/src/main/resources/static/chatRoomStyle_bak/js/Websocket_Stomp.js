// Websocket & Stomp 초기화
var sock = new SockJS("/ws/chat");
var ws = Stomp.over(sock);
var reconnect = 0;

// Vue.js
var vm = new Vue({
	el: '#app',
	data: {
		roomId: '',
		userNick: '',
		message: '',
		messages: [],
		showNotification: false,
		notification: ''
	},
	created() {
		this.roomId = '세종대화'; // 실제 룸 ID로 대체
		this.getuserNickFromDB();
	},
	mounted() {
		this.$nextTick(() => {
			this.scrollToBottom();
		});
	},
	methods: {
		getuserNickFromDB() {
			// 데이터베이스에서 보낸 사람 값을 검색하기 위한 API 호출
			// 'userNick' 속성에 값 할당
			// 'YOUR_SENDER_VALUE'를 데이터베이스의 실제 값으로 바꿉니다
			this.userNick = 'YOUR_SENDER_VALUE';
		},
		sendMessage() {
			ws.send("/app/chat/message", {}, JSON.stringify({
				type: 'TALK',
				roomId: this.roomId,
				userNick: this.userNick,
				message: this.message
			}));
			this.message = '';
		},
		recvMessage(recv) {
			if (recv.type === 'ENTER' && recv.userNick !== this.userNick) {
				// 대화방에 들어갈 때 알림 표시
				this.showNotification = true;
				this.notification = recv.userNick + ' 채팅방에 입장하였습니다.';
				setTimeout(() => {
					this.showNotification = false;
					this.notification = '';
				}, 5000); // 5초 후 알림 숨기기
			} else {
				// 수신된 메시지를 메시지 배열에 추가합니다
				this.messages.unshift({
					"type": recv.type,
					"userNick": recv.type === 'ENTER' ? '[알림]' : recv.userNick,
					"message": recv.message
				});
			}
		},
		scrollToBottom() {
			// 메시지 컨테이너의 맨 아래로 스크롤합니다
			const messagesContainer = this.$refs.messagesContainer;
			messagesContainer.scrollTop = messagesContainer.scrollHeight;
		}
	},
	updated() {
		this.$nextTick(() => {
			this.scrollToBottom();
		});
	}
});

function connect() {
	ws.connect({}, function(frame) {
		// 대화방 항목 가입
		ws.subscribe("/topic/chat/room/" + vm.$data.roomId, function(message) {
			var recv = JSON.parse(message.body);
			vm.recvMessage(recv);
		});

		// 발신인의 입장을 알리는 ENTER 메시지 보내기
		ws.send("/app/chat/message", {}, JSON.stringify({
			type: 'ENTER',
			roomId: vm.$data.roomId,
			userNick: vm.$data.userNick
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