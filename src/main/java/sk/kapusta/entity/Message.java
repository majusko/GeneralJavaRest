package sk.kapusta.entity;

import java.io.Serializable;


@SuppressWarnings("serial")
public class Message implements Serializable {
	
	private Long id;
	
	private String to;
	
	private String body;
	
	public Message() {

	}
	
	public Message(String to, String body) {
		super();
		this.to = to;
		this.body = body;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
