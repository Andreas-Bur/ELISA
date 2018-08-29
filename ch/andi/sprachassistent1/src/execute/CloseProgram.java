package execute;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser;

import bgFunc.Processes;
import gui.AlertController;
import jna.My_WNDENUMPROC;

public class CloseProgram {

	public static void closeTopWindowOfProgram(String path, String programName) {
		if (Processes.isProcessRunning(path)) {
			String[] pathParts = path.split("\\\\");

			List<HWND> hwnds = new ArrayList<>();
			int[] pids = Processes.getPIDsOfProcess(pathParts[pathParts.length - 1]);
			for (int pid : pids) {
				hwnds.addAll(getHwndsOfPid(pid));
			}

			if (hwnds.size() > 1) {
				System.err.println("DEBUG: (CloseProgram.close) found " + hwnds.size()
						+ " hwnds instead of one. Closed the one with index 0.");
			}

			HWND hwnd = hwnds.get(0);
			User32.INSTANCE.PostMessage(hwnd, WinUser.WM_CLOSE, null, null);

		} else {
			System.err.println("DEBUG: The application \"" + programName + "\" isn't running!");
		}
	}

	public static void quitProgram(String path) {
		String[] pathParts = path.split("\\\\");
		String exeName = pathParts[pathParts.length - 1];
		if (Processes.isProcessRunning(path)) {
			List<HWND> hwnds = new ArrayList<>();

			int[] pids;
			int lastHwndsSize = 0;

			while ((pids = Processes.getPIDsOfProcess(exeName)) != null) {
				for (int pid : pids) {
					hwnds.addAll(getHwndsOfPid(pid));
				}
				if(lastHwndsSize > 0 && lastHwndsSize<hwnds.size()) {
					System.out.println("cancel closing "+path);
					return;
				}
				lastHwndsSize = hwnds.size();

				if (hwnds.size() == 0) {
					break;
				}
				
					HWND HWND_TOPMOST = new HWND(Pointer.createConstant(-1));
					HWND HWND_NOTOPMOST = new HWND(Pointer.createConstant(-2));

					int curThreadId = Kernel32.INSTANCE.GetCurrentThreadId();
					int threadProcessId = User32.INSTANCE.GetWindowThreadProcessId(User32.INSTANCE.GetForegroundWindow(), null);
					User32.INSTANCE.AttachThreadInput(new DWORD(threadProcessId), new DWORD(curThreadId), true);
					User32.INSTANCE.SetWindowPos(hwnds.get(0), HWND_TOPMOST, 0, 0, 0, 0, User32.SWP_NOSIZE | User32.SWP_NOMOVE);
					User32.INSTANCE.SetWindowPos(hwnds.get(0), HWND_NOTOPMOST, 0, 0, 0, 0, User32.SWP_NOSIZE | User32.SWP_NOMOVE);
					User32.INSTANCE.PostMessage(hwnds.get(0), WinUser.WM_CLOSE, null, null);
				
				hwnds.clear();
			}
			try {
				Runtime.getRuntime().exec("taskkill /IM \"" + exeName + "\"");
			} catch (IOException e) {
				AlertController.showIOExceptionDialog("Lesen");
				e.printStackTrace();
			}
		} else {
			System.err.println("DEBUG: The application at \"" + path + "\" isn't running!");
		}
	}

	private static List<HWND> getHwndsOfPid(int pid) {

		My_WNDENUMPROC my_enumproc = new My_WNDENUMPROC(pid, true);
		User32.INSTANCE.EnumWindows(my_enumproc, null);
		return my_enumproc.getHwnds();
	}
	
	public static void main(String[] args) {
		quitProgram("C:\\Program Files (x86)\\Microsoft Office\\root\\Office16\\WINWORD.EXE");
	}

}
