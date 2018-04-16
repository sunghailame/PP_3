'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');

var stompClient = null;
var username = null;
var courseCode = null;

var profColor = '#053E92';

var colors = [
    '#FFA64F', '#FFE34F', '#7653D8', '#DBFA4D', '#D542CE', '#46E359', '#FF584F'
];

function connect(event) {
    username = document.querySelector('#name').value.trim();
    courseCode = document.querySelector('#course').value.trim();
    
    if(username) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');
        document.getElementById('chatHeader').innerHTML = "Start chatting with "+courseCode;
        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}


function onConnected() {
    //Subscribe to the course's public topic
    stompClient.subscribe('/topic/public/'+courseCode, onMessageReceived);

    //Add user to the course's app instance
    stompClient.send("/app/chat.addUser/"+courseCode, {}, JSON.stringify({sender: username})
    )

    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            role : role
        };
        //Send the message to the course's topic
        stompClient.send("/app/chat.sendMessage/"+courseCode, {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
	 var message = JSON.parse(payload.body);

	    var messageElement = document.createElement('li');

	        messageElement.classList.add('chat-message');

	        var avatarElement = document.createElement('i');
	        var avatarText = document.createTextNode(message.role[0]);
	        avatarElement.appendChild(avatarText);
	        
	        if(message.role == 'prof'){
	        	avatarElement.style['background-color'] = profColor;
	        } else {
	        	avatarElement.style['background-color'] = getAvatarColor(message.sender);
	        }
	        
	        messageElement.appendChild(avatarElement);

	        var usernameElement = document.createElement('span');
	        var usernameText = document.createTextNode(message.sender);
	        usernameElement.appendChild(usernameText);
	        messageElement.appendChild(usernameElement);

	    var textElement = document.createElement('p');
	    var messageText = document.createTextNode(message.content);
	    textElement.appendChild(messageText);

	    messageElement.appendChild(textElement);

	    messageArea.appendChild(messageElement);
	    messageArea.scrollTop = messageArea.scrollHeight;
}


function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
}

usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)