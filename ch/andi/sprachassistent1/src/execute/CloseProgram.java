package execute;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser;

import bgFunc.Processes;
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
				System.err.println("ERROR: (CloseProgram.close) found " + hwnds.size()
						+ " hwnds instead of one. Closed the one with index 0.");
			}

			HWND hwnd = hwnds.get(0);
			User32.INSTANCE.PostMessage(hwnd, WinUser.WM_CLOSE, null, null);

		} else {
			System.err.println("ERROR: The application \"" + programName + "\" isn't running!");
		}
	}

	public static void quitProgram(String path, String programName) {
		String[] pathParts = path.split("\\\\");
		String exeName = pathParts[pathParts.length - 1];
		if (Processes.isProcessRunning(path)) {
			List<HWND> hwnds = new ArrayList<>();

			int[] pids;

			while ((pids = Processes.getPIDsOfProcess(exeName)) != null) {
				for (int pid : pids) {
					hwnds.addAll(getHwndsOfPid(pid));
				}
				if (hwnds.size() > 1) {
					System.err.println("ERROR: (CloseProgram.quitProgram) found " + hwnds.size()
							+ " hwnds instead of one. Closed the one with index 0.");
				}
				if (hwnds.size() == 0) {
					break;
				}
				User32.INSTANCE.PostMessage(hwnds.get(0), WinUser.WM_CLOSE, null, null);
				hwnds.clear();
			}
			try {
				Process p = Runtime.getRuntime().exec("taskkill /IM \"" + exeName + "\"");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.err.println("ERROR: The application \"" + programName + "\" isn't running!");
		}
	}

	private static List<HWND> getHwndsOfPid(int pid) {

		My_WNDENUMPROC my_enumproc = new My_WNDENUMPROC(pid);
		User32.INSTANCE.EnumWindows(my_enumproc, null);
		return my_enumproc.getHwnds();
	}

}
