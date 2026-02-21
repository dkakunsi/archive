package itb.sdrank.client.message;

import java.io.Serializable;

public class SimpleMessage implements Serializable {

    private static final long serialVersionUID = 7439585017368520076L;
    protected String message;

    public SimpleMessage() {
	super();
    }

    public String getMessage() {
	return message;
    }

    public void setMessage(String message) {
	this.message = message;
    }

    @Override
    public String toString() {
	return "SimpleMessage [message=" + message + "]";
    }
    
    public void print() {
	System.out.println(toString());
    }
    
    public void printMessage() {
	System.out.println(message);
    }
}
