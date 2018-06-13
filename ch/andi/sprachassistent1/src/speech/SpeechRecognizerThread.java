package speech;

import java.io.IOException;

import bgFunc.Words;
import edu.cmu.sphinx.api.Microphone;
import edu.cmu.sphinx.api.SpeechResult;
import main.Main;
import parser.IntentDetector;

public class SpeechRecognizerThread implements Runnable {

	static volatile boolean keywordActivationState = false;
	public static volatile boolean restart = false;
	public static volatile MyLiveRecognizer recognizer;

	@Override
	public void run() {

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
			IntentDetector.parse(result.getHypothesis().toLowerCase());
			//System.out.println("next recognition cycle");
		}
		recognizer.stopRecognition();
	}
	
	public static void restart() {
		Microphone mic = recognizer.getMicrophone();
		recognizer.forceStopRecognition();
		try {
			recognizer = new MyLiveRecognizer(mic);
		} catch (IOException e) {
			e.printStackTrace();
		}
		recognizer.startRecognition(true);
		System.out.println("RESTARTED RECOGNIZER");
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
