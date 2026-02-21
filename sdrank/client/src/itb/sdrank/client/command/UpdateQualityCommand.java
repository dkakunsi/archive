package itb.sdrank.client.command;

import java.io.IOException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import itb.sdrank.client.message.QualityMessage;
import itb.sdrank.client.message.SimpleMessage;

public class UpdateQualityCommand extends AbstractCommand {

    private String urlTemplate;
    private String deviceId;
    private String attribute;
    private QualityMessage quality;

    public UpdateQualityCommand(String deviceId, String attribute,
	    QualityMessage quality) {
	super();
	this.urlTemplate = this.HOST + "/description/%s/resource/%s";
	this.deviceId = deviceId;
	this.attribute = attribute;
	this.quality = quality;
    }

    @Override
    public SimpleMessage execute() throws CommandException {
	RestTemplate restTemplate = new RestTemplate();
	ObjectMapper mapper = new ObjectMapper();
	HttpEntity<String> entity;
	ResponseEntity<String> response;

	System.out.println(String.format("Requesting SDRank: url: %s", getUri()));
	System.out.println(quality.toString());

	try {
	    String value = mapper.writeValueAsString(quality);
	    entity = new HttpEntity<>(value, getHeaders());
	    response = restTemplate.exchange(getUri(), HttpMethod.PUT, entity,String.class);

	    String text = response.getBody();
	    return mapper.readValue(text, SimpleMessage.class);
	} catch (IOException e) {
	    throw new CommandException(e);
	}
    }

    private String getUri() {
	return String.format(this.urlTemplate, deviceId, attribute);
    }

    @Override
    public void readValue(String[] inputs) throws CommandException {

    }
}
