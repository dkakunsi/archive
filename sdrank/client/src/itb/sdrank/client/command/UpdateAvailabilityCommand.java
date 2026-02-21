package itb.sdrank.client.command;

import java.io.IOException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import itb.sdrank.client.message.RankingMessage;
import itb.sdrank.client.message.SimpleMessage;

public class UpdateAvailabilityCommand extends AbstractCommand {

    private String urlTemplate;
    private String deviceId;
    private String mode;
    private String uri;

    public UpdateAvailabilityCommand() {
	super();
	this.urlTemplate = this.HOST + "/description/%s/operation/mode/%s";
    }

    @Override
    public SimpleMessage execute() throws CommandException {
	UriComponentsBuilder builder = UriComponentsBuilder
		.fromHttpUrl(this.getUri()).queryParam("uri", uri);

	System.out
		.println(String.format("Requesting SDRank: url: %s", getUri()));

	RestTemplate restTemplate = new RestTemplate();
	HttpEntity<String> entity = new HttpEntity<>(getHeaders());
	ResponseEntity<String> response = restTemplate.exchange(
		builder.build().encode().toUri(), HttpMethod.PUT, entity,
		String.class);

	String text = response.getBody();
	ObjectMapper mapper = new ObjectMapper();

	try {
	    return mapper.readValue(text, RankingMessage.class);
	} catch (IOException e) {
	    throw new CommandException(e);
	}
    }

    private String getUri() {
	return String.format(this.urlTemplate, deviceId, mode);
    }

    @Override
    public void readValue(String[] inputs) throws CommandException {
	if (!inputs[0].equals("availability"))
	    throw new CommandException("Request is not availability request");
	if (inputs.length < 3)
	    throw new CommandException("Request is not in format");

	readDevice(inputs[1]);
	readMode(inputs[2]);

	if (inputs[2].equals("true")) {
	    if (inputs.length != 4)
		throw new CommandException("Request is not in format!");
	    readUri(inputs[3]);
	}
    }

    private void readDevice(String input) throws CommandException {
	if (input != null && !input.equals("")) {
	    this.deviceId = input;
	} else {
	    throw new CommandException("Device ID cannot be null or empty");
	}
    }

    private void readMode(String input) throws CommandException {
	if (input != null && checkMode(input)) {
	    this.mode = input;
	} else {
	    throw new CommandException(
		    "Operation mode cannot be null or empty and must be true or false");
	}
    }

    private boolean checkMode(String input) {
	return "true".equals(input) || "false".equals(input);
    }

    private void readUri(String uri) throws CommandException {
	if (uri != null && !uri.equals("")) {
	    this.uri = uri;
	} else {
	    throw new CommandException(
		    "Url cannot be null or empty and must be true or false");
	}
    }

}
