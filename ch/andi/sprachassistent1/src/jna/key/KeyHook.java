package jna.key;

import com.sun.jna.platform.win32.User32;

import speech.HotwordActivationController;
import speech.SpeechRecognizerThread;

public class KeyHook implements Runnable {

	@Override
	public void run() {

		short l_control = 0;

		while (true) {
			if (User32.INSTANCE.GetAsyncKeyState(User32.VK_LCONTROL) == 0 && SpeechRecognizerThread.isHotwordActive()
					&& l_control != 0) {
				System.out.println("DEACTIVATE KEY");
				SpeechRecognizerThread.deactivateHotword();
				l_control = 0;
				new Thread(new HotwordActivationController(1000, false)).start();
			} else if (User32.INSTANCE.GetAsyncKeyState(User32.VK_LCONTROL) != 0 && !SpeechRecognizerThread.isHotwordActive()) {
				System.out.println("ACTIVATE KEY");
				SpeechRecognizerThread.activateHotword();
				l_control = 1;
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		KeyHook hook = new KeyHook();
		hook.run();
	}
}
