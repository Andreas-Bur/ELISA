package jna.key;

import com.sun.jna.platform.win32.User32;

import main.Main;
import speech.HotwordActivationController;
import speech.SpeechRecognizerThread;

public class KeyHook implements Runnable {

	@Override
	public void run() {

		short r_control = 0;

		while (!Main.quit) {
			if (User32.INSTANCE.GetAsyncKeyState(User32.VK_RCONTROL) == 0 && SpeechRecognizerThread.isHotwordActive() && r_control != 0) {
				System.out.println("DEACTIVATE KEY");
				SpeechRecognizerThread.deactivateHotword();
				r_control = 0;
				new Thread(new HotwordActivationController(1000, false)).start();
			} else if (User32.INSTANCE.GetAsyncKeyState(User32.VK_RCONTROL) != 0 && !SpeechRecognizerThread.isHotwordActive()) {
				System.out.println("ACTIVATE KEY");
				SpeechRecognizerThread.activateHotword();
				r_control = 0x0001;
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
