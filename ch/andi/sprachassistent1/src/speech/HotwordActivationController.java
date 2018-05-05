package speech;

import feedback.Sound;

public class HotwordActivationController implements Runnable {

    public void run() {
        try {
        	SpeechRecognizerThread.activateHotword();
        	Sound.playHotwordActivated();
			Thread.sleep(5000);
			SpeechRecognizerThread.deactivateHotword();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}       
    }
}