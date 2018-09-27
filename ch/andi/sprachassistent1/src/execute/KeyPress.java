package execute;

import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinUser.INPUT;
import com.sun.jna.platform.win32.WinUser.KEYBDINPUT;

import bgFunc.Settings;

public class KeyPress {

	INPUT input;

	public KeyPress() {
		input = new INPUT();
		input.type = new WinDef.DWORD(INPUT.INPUT_KEYBOARD);
		input.input.setType("ki");
		input.input.ki.wScan = new WinDef.WORD(0);
		input.input.ki.time = new WinDef.DWORD(0);
		input.input.ki.dwExtraInfo = new BaseTSD.ULONG_PTR(0);
	}

	/**
	 * Simuliert, dass die angegebenen Tasten gedrückt werden.
	 * @param keys Die KeyCodes der zu drückenden Tasten
	 */
	public void press(int... keys) {

		if (Settings.getHotkeyIndex() == 0) {
			releaseRControl();
		} else if (Settings.getHotkeyIndex() == 1) {
			releaseLControl();
		}

		for (int key : keys) {
			input.input.ki.wVk = new WinDef.WORD(key);
			input.input.ki.dwFlags = new WinDef.DWORD(0);
			User32.INSTANCE.SendInput(new WinDef.DWORD(1), (INPUT[]) input.toArray(1), input.size());
		}

		for (int key : keys) {
			input.input.ki.wVk = new WinDef.WORD(key);
			input.input.ki.dwFlags = new WinDef.DWORD(2);
			User32.INSTANCE.SendInput(new WinDef.DWORD(1), (INPUT[]) input.toArray(1), input.size());
		}
	}

	/**
	 * Simuliert, dass die linke Ctrl-Taste losgelassen wird, um
	 * Tastenkombinationen mit anderen Tasten zu verhindern.
	 */
	private void releaseLControl() {
		input.input.ki.wVk = new WinDef.WORD(User32.VK_CONTROL);
		input.input.ki.dwFlags = new WinDef.DWORD(2);
		User32.INSTANCE.SendInput(new WinDef.DWORD(1), (INPUT[]) input.toArray(1), input.size());
	}

	/**
	 * Simuliert, dass die rechte Ctrl-Taste losgelassen wird, um
	 * Tastenkombinationen mit anderen Tasten zu verhindern.
	 */
	private void releaseRControl() {

		WinUser.INPUT[] inputArray = (WinUser.INPUT[]) new WinUser.INPUT().toArray(2);

		inputArray[0].type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
		inputArray[0].input.setType("ki");
		inputArray[0].input.ki.time = new WinDef.DWORD(0);
		inputArray[0].input.ki.wVk = new WinDef.WORD(0);
		inputArray[0].input.ki.dwExtraInfo = new BaseTSD.ULONG_PTR(0);
		inputArray[0].input.ki.dwFlags = new WinDef.DWORD(
				KEYBDINPUT.KEYEVENTF_SCANCODE | KEYBDINPUT.KEYEVENTF_KEYUP | KEYBDINPUT.KEYEVENTF_EXTENDEDKEY);
		inputArray[0].input.ki.wScan = new WinDef.WORD(0xE0);

		inputArray[1].type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
		inputArray[1].input.setType("ki");
		inputArray[1].input.ki.time = new WinDef.DWORD(0);
		inputArray[1].input.ki.wVk = new WinDef.WORD(0);
		inputArray[1].input.ki.dwExtraInfo = new BaseTSD.ULONG_PTR(0);
		inputArray[1].input.ki.dwFlags = new WinDef.DWORD(
				KEYBDINPUT.KEYEVENTF_SCANCODE | KEYBDINPUT.KEYEVENTF_KEYUP | KEYBDINPUT.KEYEVENTF_EXTENDEDKEY);
		inputArray[1].input.ki.wScan = new WinDef.WORD(0x1D);

		User32.INSTANCE.SendInput(new WinDef.DWORD(inputArray.length), inputArray, inputArray[0].size());
	}
}