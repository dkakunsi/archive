package itb.sdrank.client;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import itb.sdrank.client.command.AbstractCommand;
import itb.sdrank.client.command.CommandException;
import itb.sdrank.client.command.DeviceConnectionCommand;
import itb.sdrank.client.command.RequestAlternativeCommand;
import itb.sdrank.client.command.RequestRankingCommand;
import itb.sdrank.client.command.SendFileCommand;
import itb.sdrank.client.command.UpdateAvailabilityCommand;
import itb.sdrank.client.command.UpdateQualityCommand;
import itb.sdrank.client.message.QualityMessage;
import itb.sdrank.client.message.RankingItem;
import itb.sdrank.client.message.RankingMessage;
import itb.sdrank.client.message.SimpleMessage;
import itb.sdrank.client.notification.NotificationChannel;
import itb.sdrank.client.notification.NotificationException;

public class SDRankClient {

    public static void main(String args[]) {
	State.setState(State.WAITING);
	onProcess();
    }

    public static void onProcess() {
	String text;
	String[] inputs;
	Scanner reader = new Scanner(System.in);

	do {
	    try {
		System.out.print("SDRank: client > ");
		text = reader.nextLine();
		inputs = text.split(" ");

		if ("exit".equals(inputs[0])) {
		    State.setState(State.EXIT);
		} else if ("reset".equals(inputs[0])) {
		    State.setState(State.WAITING);
		} else if ("state".equals(inputs[0])) {
		    State.print();
		} else if ("stop".equals(inputs[0])) {
		    NotificationChannel.destroy();
		    State.setState(State.WAITING);
		} else if ("ranking".equals(inputs[0])) {
		    requestRanking(inputs);
		} else if ("alternative".equals(inputs[0])) {
		    alternativeRanking(inputs);
		} else if ("quality".equals(inputs[0])) {
		    updateQuality(inputs);
		} else if ("availability".equals(inputs[0])) {
		    updateAvailability(inputs);
		} else if ("file".equals(inputs[0])) {
		    sendFile(inputs);
		}
	    } catch (CommandException | NotificationException e) {
		System.out.println(e.getMessage());
	    }
	} while (!State.getState().equals(State.EXIT));

	System.out.println("Bye!");
	reader.close();
    }

    private static void requestRanking(String[] inputs)
	    throws CommandException, NotificationException {
	AbstractCommand command = new RequestRankingCommand();
	command.readValue(inputs);
	RankingMessage message = (RankingMessage) command.execute();

	if (message.checkItems()) {
	    NotificationChannel.initialize();
	    NotificationChannel.addListeningRanking(message.getRankingId());

	    for (RankingItem item : message.getItems()) {
		DeviceConnectionCommand devCommand = new DeviceConnectionCommand(
			item);
		devCommand.execute();
	    }
	} else {
	    message.printMessage();
	}
    }

    private static void alternativeRanking(String[] inputs)
	    throws CommandException, NotificationException {
	AbstractCommand command = new RequestAlternativeCommand();
	command.readValue(inputs);
	RankingMessage message = (RankingMessage) command.execute();

	if (message.checkItems()) {
	    NotificationChannel.initialize();
	    NotificationChannel.addListeningRanking(message.getRankingId());

	    for (RankingItem item : message.getItems()) {
		DeviceConnectionCommand devCommand = new DeviceConnectionCommand(
			item);
		devCommand.execute();
	    }
	} else {
	    message.printMessage();
	}
    }

    private static void updateQuality(String[] inputs) throws CommandException {
	AbstractCommand command;
	String attribute = "temperature";

	try {
	    Map<String, List<QualityMessage>> qualityMessages = readFile(
		    inputs[1]);
	    for (Map.Entry<String, List<QualityMessage>> entry : qualityMessages
		    .entrySet()) {
		for (QualityMessage qualityMessage : entry.getValue()) {
		    command = new UpdateQualityCommand(entry.getKey(),
			    attribute, qualityMessage);
		    command.execute();

		    Thread.sleep(5000l);
		}
	    }
	} catch (IOException | InterruptedException e) {
	    throw new CommandException(e);
	}
    }

    private static Map<String, List<QualityMessage>> readFile(
	    String fileLocation)
	    throws JsonParseException, JsonMappingException, IOException {
	ObjectMapper mapper = new ObjectMapper();
	File file = new File(fileLocation);

	return mapper.readValue(file,
		new TypeReference<Map<String, List<QualityMessage>>>() {
		});
    }

    private static void updateAvailability(String[] inputs)
	    throws CommandException {
	AbstractCommand command = new UpdateAvailabilityCommand();
	command.readValue(inputs);

	SimpleMessage message = command.execute();
	message.printMessage();
    }

    private static void sendFile(String[] inputs) throws CommandException {
	AbstractCommand command = new SendFileCommand();
	command.readValue(inputs);
	SimpleMessage message = command.execute();
	message.printMessage();
    }
}
