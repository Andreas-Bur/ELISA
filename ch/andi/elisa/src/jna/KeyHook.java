package jna;

import com.sun.jna.platform.win32.User32;

import bgFunc.Settings;
import speech.HotwordActivationController;
import speech.SpeechRecognizerThread;

public class KeyHook implements Runnable {
	
	int[] hotkeyCodes = {User32.VK_RCONTROL, User32.VK_LCONTROL};
	public volatile static int index = Settings.getHotkeyIndex();

	@Override
	public void run() {

		short l_control = 0;

		while (true) {
			if (User32.INSTANCE.GetAsyncKeyState(hotkeyCodes[index]) == 0 && SpeechRecognizerThread.isHotwordActive()
					&& l_control != 0) {
				SpeechRecognizerThread.deactivateHotword();
				l_control = 0;
				new Thread(new HotwordActivationController(1000, false)).start();
			} else if (User32.INSTANCE.GetAsyncKeyState(hotkeyCodes[index]) != 0 && !SpeechRecognizerThread.isHotwordActive()) {
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
}
