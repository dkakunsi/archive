package itb.sdrank.client.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import itb.sdrank.client.message.RankingMessage;
import itb.sdrank.client.message.SimpleMessage;

public class RequestRankingCommand extends AbstractCommand {

    private String urlTemplate;
    private final String PATTERN = "^-?\\d+(\\.\\d+)?$";
    private String domainAttribute;
    private String location;
    private String number;
    private String comparisonValues;

    public RequestRankingCommand() {
	super();
	this.urlTemplate = this.HOST + "/%s/location/%s/number/%s";
    }

    @Override
    public SimpleMessage execute() throws CommandException {
	UriComponentsBuilder builder = UriComponentsBuilder
		.fromHttpUrl(this.getUri())
		.queryParam("comparisonValues", getComparisonValues());

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

    private String getUri() {
	return String.format(this.urlTemplate, domainAttribute, location,
		number);
    }

    @Override
    public void readValue(String[] inputs) throws CommandException {
	if (!inputs[0].equals("ranking"))
	    throw new CommandException("Request is not ranking request");
	if (inputs.length != 5)
	    throw new CommandException("Request is not in format");

	readDomainAttribute(inputs[1]);
	readRequestedLocation(inputs[2]);
	readComparisonValues(inputs[3]);
	readRequestedNumber(inputs[4]);
    }

    private void readDomainAttribute(String input) throws CommandException {
	if (input != null && !input.equals("")) {
	    this.domainAttribute = input;
	} else {
	    throw new CommandException(
		    "Domain attribute cannot be null or empty");
	}
    }

    private void readRequestedLocation(String input) throws CommandException {
	String[] loc = input.split(",");

	if (loc.length == 2 && Pattern.matches(PATTERN, loc[0])
		&& Pattern.matches(PATTERN, loc[1])) {
	    location = input;
	} else {
	    throw new CommandException("Location is not meet the format");
	}
    }

    private void readComparisonValues(String input) throws CommandException {
	String[] qualities = input.split(",");
	if (checkAhpComparison(qualities) || checkRatingComparison(qualities)) {
	    comparisonValues = input;
	} else {
	    throw new CommandException("Comparison is not meet the format");
	}
    }

    private boolean checkAhpComparison(String[] qualities) {
	if (qualities.length == 6 && Pattern.matches(PATTERN, qualities[0])
		&& Pattern.matches(PATTERN, qualities[1])
		&& Pattern.matches(PATTERN, qualities[2])
		&& Pattern.matches(PATTERN, qualities[3])
		&& Pattern.matches(PATTERN, qualities[4])
		&& Pattern.matches(PATTERN, qualities[5])) {
	    return true;
	}

	return false;
    }

    private boolean checkRatingComparison(String[] qualities) {
	if (qualities.length == 4 && Pattern.matches(PATTERN, qualities[0])
		&& Pattern.matches(PATTERN, qualities[1])
		&& Pattern.matches(PATTERN, qualities[2])
		&& Pattern.matches(PATTERN, qualities[3])) {
	    return true;
	}

	return false;
    }

    private void readRequestedNumber(String input) throws CommandException {
	if (Pattern.matches("[0-9]", input)) {
	    number = input;
	} else {
	    throw new CommandException("Number is not meet the format");
	}
    }

    public Object[] getComparisonValues() {
	if (comparisonValues != null && comparisonValues != "") {
	    List<Object> list = new ArrayList<>();
	    for (String str : comparisonValues.split(",")) {
		list.add(str);
	    }
	    return list.toArray(new Object[list.size()]);
	}
	return new Object[0];
    }
}
