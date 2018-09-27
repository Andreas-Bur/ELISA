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
import feedback.AlertController;
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

			HWND hwnd = hwnds.get(0);
			User32.INSTANCE.PostMessage(hwnd, WinUser.WM_CLOSE, null, null);

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

				// Wenn ein neues Fenster aufgegangen ist (zB. um nachzufragen
				// ob Dokument gespeichert werden soll) wird der Schliessprozess
				// abgebrochen
				if (lastHwndsSize > 0 && lastHwndsSize < hwnds.size()) {
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
		}
	}

	private static List<HWND> getHwndsOfPid(int pid) {
		My_WNDENUMPROC my_enumproc = new My_WNDENUMPROC(pid, true);
		User32.INSTANCE.EnumWindows(my_enumproc, null);
		return my_enumproc.getHwnds();
	}
}
