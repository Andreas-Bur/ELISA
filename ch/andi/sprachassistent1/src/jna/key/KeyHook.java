package jna.key;

import java.awt.event.KeyEvent;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HMODULE;
import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinUser.HHOOK;
import com.sun.jna.platform.win32.WinUser.KBDLLHOOKSTRUCT;
import com.sun.jna.platform.win32.WinUser.LowLevelKeyboardProc;
import com.sun.jna.platform.win32.WinUser.MSG;

public class KeyHook implements Runnable{
	private final User32 user32 = User32.INSTANCE;
	private HHOOK hHook;
	private boolean[] keyStates = new boolean[1000];
	final int R_SHIFT = WinUser.VK_RSHIFT;
	final int R_CONTROL = WinUser.VK_RCONTROL;
	final int DECIMAL = 190;

	@Override
	public void run() {
		LowLevelKeyboardProc keyHook;
		HMODULE hModule = Kernel32.INSTANCE.GetModuleHandle(null);

		keyHook = new LowLevelKeyboardProc() {

			@Override
			public LRESULT callback(int nCode, WPARAM wParam, KBDLLHOOKSTRUCT lParam) {
				if (nCode >= 0 && nCode <= KeyEvent.VK_QUOTE) {
					switch (wParam.intValue()) {
					case WinUser.WM_KEYUP:
					case WinUser.WM_SYSKEYUP:
						keyStates[lParam.vkCode] = false;
						break;
					case WinUser.WM_KEYDOWN:
					case WinUser.WM_SYSKEYDOWN:
						keyStates[lParam.vkCode] = true;
						break;
					}

					// TODO check for combinations
					
					
					
					if(keyStates[R_SHIFT] && keyStates[R_CONTROL] && keyStates[DECIMAL]) {
						System.out.println("key combination!");
						//TODO activate speech recognizer
						return new LRESULT(1);
					}
				}
				Pointer ptr = lParam.getPointer();
				long peer = Pointer.nativeValue(ptr);
				return user32.CallNextHookEx(null, nCode, wParam, new LPARAM(peer));

			}
		};

		hHook = user32.SetWindowsHookEx(WinUser.WH_KEYBOARD_LL, keyHook, hModule, 0);
		
		int returnValue;
		MSG msg = new MSG();
		while((returnValue = user32.GetMessage(msg, null, 0, 0)) != 0) {
			if(returnValue == -1) {
				System.err.println("Error while retrieving key message.");
				break;
			}
			else {
				System.err.println("got message");
				user32.TranslateMessage(msg);
				user32.DispatchMessage(msg);
			}
		}
		user32.UnhookWindowsHookEx(hHook);
		
	}

	public void unhook() {
		user32.UnhookWindowsHookEx(hHook);
	}

}
