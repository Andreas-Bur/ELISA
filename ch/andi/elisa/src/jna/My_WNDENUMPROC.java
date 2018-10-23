package jna;

import java.util.ArrayList;
import java.util.List;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser.WNDENUMPROC;
import com.sun.jna.ptr.IntByReference;

public class My_WNDENUMPROC implements WNDENUMPROC {

	private List<HWND> output = new ArrayList<HWND>();
	private int pid;
	private boolean returnAllHwnds;

	public My_WNDENUMPROC(int pid, boolean returnAllHwnds) {
		this.pid = pid;
		this.returnAllHwnds = returnAllHwnds;
	}

	@Override
	public boolean callback(HWND hWnd, Pointer data) {
		IntByReference test = new IntByReference();
		User32.INSTANCE.GetWindowThreadProcessId(hWnd, test);

		if (test.getValue() == pid) {

			char[] buffer = new char[200];

			int result = User32.INSTANCE.GetWindowText(hWnd, buffer, 200);

			if (result == 0 || !User32.INSTANCE.IsWindowVisible(hWnd) || User32.INSTANCE.IsIconic(hWnd)) {
				return true;
			}

			output.add(hWnd);

			return returnAllHwnds;
		}
		return true;
	}

	public List<HWND> getHwnds() {
		return output;
	}
}