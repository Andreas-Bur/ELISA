package speech;

import java.io.IOException;

import commands.CommandParser;
import edu.cmu.sphinx.api.SpeechResult;
import main.Main;

public class SpeechRecognizerThread implements Runnable {

	static volatile boolean keywordActivationState = false;

	@Override
	public void run() {

		MyLiveRecognizer recognizer;
		CommandParser parser = new CommandParser();

		try {
			recognizer = new MyLiveRecognizer();
			recognizer.startRecognition(true);
			System.out.println("Recognizer is ready");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		while (!Main.quit) {

			SpeechResult result = recognizer.getResult();
			parser.parse(decode(result.getHypothesis()).toLowerCase());
			
		}
		recognizer.stopRecognition();
	}

	static String decode(String input) {
		input = input.replace("%ue%", "ü");
		input = input.replace("%oe%", "ö");
		input = input.replace("%ae%", "ä");
		return input;
	}
	
	public static boolean isKeywordActive() {
		return keywordActivationState;
	}
	
	static void activateKeyword() {
		keywordActivationState = true;
	}
	
	static void deactivateKeyword() {
		keywordActivationState = false;
	}
}
