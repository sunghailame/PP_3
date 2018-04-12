package whiteboard.messaging;

import whiteboard.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MessageController {

	@GetMapping("prof/chat")
	public String index_get_mapping() {
		return "prof/chat";
	}
	
    @MessageMapping("prof/chat/chat.sendMessage")
    @SendTo("/topic/public")
    public Message sendMessage(@Payload Message message) {
        return message;
    }

    @MessageMapping("prof/chat/chat.addUser")
    @SendTo("/topic/public")
    public Message addUser(@Payload Message message, SimpMessageHeaderAccessor headerAccessor) {
    	
    	
        headerAccessor.getSessionAttributes().put("username", message.getSender());
        return message;
    }

}