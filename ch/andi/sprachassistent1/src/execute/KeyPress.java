package execute;

import java.util.Arrays;

import com.sun.jna.platform.win32.BaseTSD;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser.INPUT;

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

	public void press(int... keys) {
		System.out.println("KeyPress.press: " + Arrays.toString(keys));

		releaseLControl();

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

	private void releaseLControl() {
		input.input.ki.wVk = new WinDef.WORD(User32.VK_CONTROL);
		input.input.ki.dwFlags = new WinDef.DWORD(2);
		User32.INSTANCE.SendInput(new WinDef.DWORD(1), (INPUT[]) input.toArray(1), input.size());
	}

	public static void main(String[] args) {
		KeyPress press = new KeyPress();
		press.press('\r', '\n');
	}
}