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

var profColor = '#AE5E81';
//var profPic = 'https://img.thedailybeast.com/image/upload/c_crop,d_placeholder_euli9k,h_1440,w_2560,x_0,y_0/dpr_2.0/c_limit,w_740/fl_lossy,q_auto/v1515805265/180112-fisher-african-lions-tease_uaafsx';
var colors = [
	//'http://www.catster.com/wp-content/uploads/2017/11/Angry-cat-growling-or-hissing-ears-back.jpg',
	//'https://acm202.files.wordpress.com/2016/09/cats-politics-tn.jpg',
	//'https://img.buzzfeed.com/buzzfeed-static/static/2015-03/3/16/enhanced/webdr09/enhanced-7711-1425417143-1.jpg?downsize=715:*&output-format=auto&output-quality=auto',
	//'https://www.rd.com/wp-content/uploads/2016/04/01-cat-wants-to-tell-you-laptop.jpg',
	//'http://honesttopaws.com/wp-content/uploads/sites/5/2017/05/banana-cat-1.png',
	//'https://i.amz.mshcdn.com/zy65wibIKGJwrQ3LlrTIKPGfDoE=/1200x630/2017%2F11%2F12%2F85%2F0cb95ccbac6441e7a30fb7d1d01b094d.bc1ee.png'
	
    '#F4E8C1', '#A0C1B8', '#709FB0', '#726A95', '#351F39'
];

function connect(event) {
    username = document.querySelector('#name').value.trim();
    courseCode = document.querySelector('#course').value.trim();
    
    if(username) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');
        document.getElementById('chatHeader').innerHTML = "Chat with "+courseCode;
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
	    
	   /* var avatarElement = document.createElement('img');
	    if(message.role == 'prof'){
	    	avatarElement.setAttribute('src', profPic);
	    } else {
	    	avatarElement.setAttribute('src', getAvatarColor(message.sender);
	    }
	    avatarElement.width = '20';
	    avatarElement.height = '20';
	    messageElement.appendChild(avatarElement);
	    */
	
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