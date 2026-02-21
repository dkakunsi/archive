package itb.sdrank.web;

public class SimpleResponse {
	protected String message;

	public SimpleResponse() {
		super();
	}
	
	public SimpleResponse(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
