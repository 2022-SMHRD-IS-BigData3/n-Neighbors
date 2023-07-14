function leaveChatRoom() {
	var roomId = getRoomIdFromURL(); // URL에서 roomId 추출하는 로직 구현 필요
	// userChatOut 경로로 이동
	window.location.href = '/userChatOut?roomId=' + roomId;
}

function getRoomIdFromURL() {
	var searchParams = new URLSearchParams(window.location.search);
	var roomId = searchParams.get('roomId');
	return roomId;
}