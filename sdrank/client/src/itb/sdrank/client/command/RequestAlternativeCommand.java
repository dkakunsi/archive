package itb.sdrank.client.command;

import java.io.IOException;
import java.util.regex.Pattern;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import itb.sdrank.client.message.RankingMessage;
import itb.sdrank.client.message.SimpleMessage;

public class RequestAlternativeCommand extends AbstractCommand {

    private String urlTemplate;
    private String ranking;
    private String candidate;
    private String number;

    public RequestAlternativeCommand() {
	super();
	this.urlTemplate = this.HOST + "/ranking/%s/candidate/%s/number/%s";
    }

    private String getUri() {
	return String.format(this.urlTemplate, ranking, candidate, number);
    }

    @Override
    public SimpleMessage execute() throws CommandException {
	UriComponentsBuilder builder = UriComponentsBuilder
		.fromHttpUrl(this.getUri());

	System.out.println(String.format("Requesting SDRank: url: %s", getUri()));

	RestTemplate restTemplate = new RestTemplate();
	HttpEntity<String> entity = new HttpEntity<>(getHeaders());
	ResponseEntity<String> response = restTemplate.exchange(
		builder.build().encode().toUri(), HttpMethod.GET, entity,
		String.class);

	String text = response.getBody();
	ObjectMapper mapper = new ObjectMapper();

	try {
	    return mapper.readValue(text, RankingMessage.class);
	} catch (IOException e) {
	    throw new CommandException(e);
	}
    }

    @Override
    public void readValue(String[] inputs) throws CommandException {
	if (!inputs[0].equals("alternative"))
	    throw new CommandException("Request is not alternative request");
	if (inputs.length != 4)
	    throw new CommandException("Request is not in format");

	readRanking(inputs[1]);
	readCandidate(inputs[2]);
	readRequestedNumber(inputs[3]);
    }

    private void readRanking(String input) throws CommandException {
	if (input != null && !input.equals("")) {
	    this.ranking = input;
	} else {
	    throw new CommandException("Ranking cannot be null or empty");
	}
    }

    private void readCandidate(String input) throws CommandException {
	if (input != null && !input.equals("")) {
	    this.candidate = input;
	} else {
	    throw new CommandException("Candidate cannot be null or empty");
	}
    }

    private void readRequestedNumber(String input) throws CommandException {
	if (Pattern.matches("[0-9]", input)) {
	    number = input;
	} else {
	    throw new CommandException("Number is not meet the format");
	}
    }
}
