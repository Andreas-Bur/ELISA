package execute;

import java.util.ArrayList;
import java.util.List;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinDef.HWND;

import bgFunc.Processes;
import jna.My_WNDENUMPROC;

public class WindowController {
	
	public static void showWindow(String path) {
		
		List<HWND> hwnds = getHwndsOfProcess(path);

		for (HWND hwnd : hwnds) {
			System.out.println("hwnd");
			HWND HWND_TOPMOST = new HWND(Pointer.createConstant(-1));
			HWND HWND_NOTOPMOST = new HWND(Pointer.createConstant(-2));

			int curThreadId = Kernel32.INSTANCE.GetCurrentThreadId();
			int threadProcessId = User32.INSTANCE.GetWindowThreadProcessId(User32.INSTANCE.GetForegroundWindow(), null);
			User32.INSTANCE.AttachThreadInput(new DWORD(threadProcessId), new DWORD(curThreadId), true);
			User32.INSTANCE.SetWindowPos(hwnd, HWND_TOPMOST, 0, 0, 0, 0, User32.SWP_NOSIZE | User32.SWP_NOMOVE);
			User32.INSTANCE.SetWindowPos(hwnd, HWND_NOTOPMOST, 0, 0, 0, 0, User32.SWP_NOSIZE | User32.SWP_NOMOVE);
			User32.INSTANCE.SetForegroundWindow(hwnd);
			User32.INSTANCE.AttachThreadInput(new DWORD(threadProcessId), new DWORD(curThreadId), false);
			User32.INSTANCE.SetFocus(hwnd);
		}
	}
	
	public static void maximizeAndShow(String path) {
		List<HWND> hwnds = WindowController.getHwndsOfProcess(path);

		for (HWND hwnd : hwnds) {
			
			HWND HWND_TOPMOST = new HWND(Pointer.createConstant(-1));
			HWND HWND_NOTOPMOST = new HWND(Pointer.createConstant(-2));

			int curThreadId = Kernel32.INSTANCE.GetCurrentThreadId();
			int threadProcessId = User32.INSTANCE.GetWindowThreadProcessId(User32.INSTANCE.GetForegroundWindow(), null);
			User32.INSTANCE.AttachThreadInput(new DWORD(threadProcessId), new DWORD(curThreadId), true);
			User32.INSTANCE.SetWindowPos(hwnd, HWND_TOPMOST, 0, 0, 0, 0, User32.SWP_NOSIZE | User32.SWP_NOMOVE);
			User32.INSTANCE.SetWindowPos(hwnd, HWND_NOTOPMOST, 0, 0, 0, 0, User32.SWP_NOSIZE | User32.SWP_NOMOVE);
			User32.INSTANCE.SetForegroundWindow(hwnd);
			User32.INSTANCE.AttachThreadInput(new DWORD(threadProcessId), new DWORD(curThreadId), false);
			User32.INSTANCE.SetFocus(hwnd);
			
			int SW_SHOWNORMAL = 3;
			User32.INSTANCE.ShowWindow(hwnd, SW_SHOWNORMAL);
		}
	}
	
	public static void minimize(String path) {
		List<HWND> hwnds = WindowController.getHwndsOfProcess(path);

		for (HWND hwnd : hwnds) {
			int SW_FORCEMINIMIZE = 11;
			User32.INSTANCE.ShowWindow(hwnd, SW_FORCEMINIMIZE);
		}
	}
	
	static List<HWND> getHwndsOfProcess(String path) {
		String[] pathParts = path.split("\\\\");

		List<HWND> hwnds = new ArrayList<>();
		int[] pids = Processes.getPIDsOfProcess(pathParts[pathParts.length - 1]);
		for (int pid : pids) {
			hwnds.addAll(getHwndsOfPid(pid));
		}
		return hwnds;
	}

	static List<HWND> getHwndsOfPid(int pid) {
		My_WNDENUMPROC my_enumproc = new My_WNDENUMPROC(pid, false);
		User32.INSTANCE.EnumWindows(my_enumproc, null);
		return my_enumproc.getHwnds();
	}

}
