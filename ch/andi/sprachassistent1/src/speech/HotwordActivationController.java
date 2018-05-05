package speech;

public class HotwordActivationController implements Runnable {

    public void run() {
        try {
        	SpeechRecognizerThread.activateHotword();
			Thread.sleep(5000);
			SpeechRecognizerThread.deactivateHotword();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}       
    }
}