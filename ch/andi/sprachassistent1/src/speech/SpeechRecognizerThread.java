package speech;

import java.io.IOException;

import bgFunc.Words;
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
			parser.parse(Words.decode(result.getHypothesis()).toLowerCase());
			//System.out.println("next recognition cycle");
		}
		recognizer.stopRecognition();
	}

	public static boolean isHotwordActive() {
		return keywordActivationState;
	}
	
	static void activateHotword() {
		System.out.println("activate Keyword");
		keywordActivationState = true;
	}
	
	static void deactivateHotword() {
		System.out.println("deactivate Keyword");
		keywordActivationState = false;
	}
}
