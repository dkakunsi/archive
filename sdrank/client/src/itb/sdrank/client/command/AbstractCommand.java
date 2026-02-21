package itb.sdrank.client.command;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import itb.sdrank.client.message.SimpleMessage;

public abstract class AbstractCommand {

    protected final String HOST = "http://localhost:8080/sdrank";

    protected AbstractCommand() {
	super();
    }

    public abstract SimpleMessage execute() throws CommandException;
    
    public abstract void readValue(String[] inputs) throws CommandException;
    
    protected HttpHeaders getHeaders() {
	List<MediaType> acceptableMediaTypes = new ArrayList<>();
	acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_JSON);
	headers.setAccept(acceptableMediaTypes);

	return headers;
    }
}
