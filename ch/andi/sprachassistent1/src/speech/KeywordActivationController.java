package speech;

public class KeywordActivationController extends Thread {

    @Override
    public void run() {
        try {
        	SpeechRecognizerThread.activateKeyword();
			Thread.sleep(5000);
			SpeechRecognizerThread.deactivateKeyword();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}       
    }
}