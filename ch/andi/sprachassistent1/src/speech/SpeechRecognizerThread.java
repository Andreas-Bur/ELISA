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
			long time = System.nanoTime();
			recognizer = new MyLiveRecognizer();
			recognizer.startRecognition(true);

			System.out.println("Recognizer is ready: " + (System.nanoTime() - time) / 1000000000.0);
			System.out.println("Total startup time: " + (System.nanoTime() - Main.totalTime) / 1000000000.0);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		while (!Main.quit) {

			SpeechResult result = recognizer.getResult();

			new Thread(new Runnable() {
				public void run() {
					IntentDetector.parse(result.getHypothesis().toLowerCase(), result.getTags());
				}
			}).start();
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

	public static void activateHotword() {
		System.out.println("activate Keyword");
		keywordActivationState = true;
	}

	public static void deactivateHotword() {
		System.out.println("deactivate Keyword");
		keywordActivationState = false;
	}
}
