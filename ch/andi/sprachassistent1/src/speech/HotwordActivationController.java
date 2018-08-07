package speech;

import feedback.Sound;
import gui.TrayIconController;
import main.Main;

public class HotwordActivationController implements Runnable {

	int millis;
	boolean sound;

	public HotwordActivationController(int millis, boolean sound) {
		this.millis = millis;
		this.sound = sound;
	}

	public HotwordActivationController() {
		millis = 5000;
		sound = true;
	}

	public void run() {
		try {
			SpeechRecognizerThread.activateHotword();
			if (sound) {
				Main.trayIconController.setIcon(TrayIconController.LOADING_ICON);
				Sound.playHotwordActivated();
			}
			Thread.sleep(millis);
			SpeechRecognizerThread.deactivateHotword();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			Main.trayIconController.setDefaultIcon();
		}
	}
}