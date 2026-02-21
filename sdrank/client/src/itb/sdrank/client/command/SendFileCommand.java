package itb.sdrank.client.command;

import java.io.File;
import java.io.IOException;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import itb.sdrank.client.message.SimpleMessage;

public class SendFileCommand extends AbstractCommand {

    private File file;
    private String urlTemplate;
    
    public SendFileCommand() {
	super();
	this.urlTemplate = this.HOST + "/description";
    }
    
    @Override
    public SimpleMessage execute() throws CommandException {
	RestTemplate restTemplate = new RestTemplate();
	ObjectMapper mapper = new ObjectMapper();

	System.out.println(String.format("Requesting SDRank: url: %s", this.urlTemplate));
	
	try {
	    HttpEntity<LinkedMultiValueMap<String, Object>> entity = new HttpEntity<>(getValueMap(), getHeaders());
	    ResponseEntity<String> response = restTemplate.exchange(this.urlTemplate, HttpMethod.POST, entity, String.class);

	    String text = response.getBody();
	    return mapper.readValue(text, SimpleMessage.class);
	} catch (IOException e) {
	    throw new CommandException(e);
	}
    }

    @Override
    public void readValue(String[] inputs) throws CommandException {
	if (!inputs[0].equals("file"))
	    throw new CommandException("Request is not file request");
	if (inputs.length != 2)
	    throw new CommandException("Request is not in format");

	this.file = new File(inputs[1]);
    }
    
    private LinkedMultiValueMap<String, Object> getValueMap() {
	LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
	map.add("descriptionFile", new FileSystemResource(file));
	
	return map;
    }
    
    @Override
    protected HttpHeaders getHeaders() {
	HttpHeaders headers = super.getHeaders();
	headers.setContentType(MediaType.MULTIPART_FORM_DATA);

	return headers;
    }

}
