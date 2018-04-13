package whiteboard.Message;

public class Message{
	
	public String sender;
	public String content;
	
	public Message(String sender, String content){
		this.sender = sender;
		this.content = content;
	}
	
	
	Message(){
		
	}


	public String getSender() {
		return sender;
	}


	public void setSender(String sender) {
		this.sender = sender;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}
	
}