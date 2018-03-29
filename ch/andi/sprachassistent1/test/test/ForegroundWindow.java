package test;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;

import bgFunc.Processes;

public class ForegroundWindow {

	public static void main(String[] args) {

		HWND hwnd = User32.INSTANCE.FindWindow(null, Processes.getTitleOfProcess("notepad++.exe"));
		User32.INSTANCE.ShowWindow(hwnd, 9); // SW_RESTORE
		User32.INSTANCE.SetForegroundWindow(hwnd);

	}
}