package jna.key;

import java.awt.event.KeyEvent;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinDef.HMODULE;
import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinUser.HHOOK;
import com.sun.jna.platform.win32.WinUser.KBDLLHOOKSTRUCT;
import com.sun.jna.platform.win32.WinUser.LowLevelKeyboardProc;

public class KeyHook {
	private final User32 user32;
	private HHOOK hHook;
	private boolean[] keyStates = new boolean[KeyEvent.VK_QUOTE+1];

	public KeyHook() {
		LowLevelKeyboardProc keyHook;
		user32 = User32.INSTANCE;
		HMODULE hModule = Kernel32.INSTANCE.GetModuleHandle(null);
		
		keyHook = new LowLevelKeyboardProc() {
			
			@Override
			public LRESULT callback(int nCode, WPARAM wParam, KBDLLHOOKSTRUCT lParam) {
				if (nCode >= 0 && nCode <= KeyEvent.VK_QUOTE) {
                    switch(wParam.intValue()) {
                    case WinUser.WM_KEYUP:
                    case WinUser.WM_SYSKEYUP:
                    	keyStates[nCode] = false;
                    	break;
                    case WinUser.WM_KEYDOWN:
                    case WinUser.WM_SYSKEYDOWN:
                    	keyStates[nCode] = true;
                    	break;

                        
                    }
                    
                    //TODO check for combinations
                    
                    //return new LRESULT(1); //to inhibit event
                
                }
                Pointer ptr = lParam.getPointer();
                long peer = Pointer.nativeValue(ptr);
                return user32.CallNextHookEx(null, nCode, wParam, new LPARAM(peer));
                
			}
		};
		
		hHook = user32.SetWindowsHookEx(WinUser.WH_KEYBOARD_LL, keyHook, hModule, 0);
		
	}

	public void unhook() {
		user32.UnhookWindowsHookEx(hHook);
	}

}
