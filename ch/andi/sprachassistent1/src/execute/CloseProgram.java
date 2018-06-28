package execute;

import java.util.ArrayList;
import java.util.List;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.ptr.IntByReference;

import bgFunc.MyPaths;
import bgFunc.Processes;
import jna.My_WNDENUMPROC;

public class CloseProgram {

	public static void main(String[] args) {

		close(MyPaths.getPathOfKnownApp("_word"), "word.exe");

	}

	public static void close(String path, String programName) {
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
		if (Processes.isProcessRunning(path)) {
			// String[] pathParts = path.split("\\\\");
			// HWND hwnd = User32.INSTANCE.FindWindow(null,
			// Processes.getTitleOfProcess(pathParts[pathParts.length - 1]));

			int pids[] = Processes.getPIDsOfProcess(programName);

			for (int i = 0; i < pids.length; i++) {
				System.out.println("pid: " + pids[i]);
				HWND hwnd = FindMainWindowFromPid(pids[i]);
				User32.INSTANCE.PostMessage(hwnd, WinUser.WM_QUIT, null, null);
			}
			// HWND hwnd = FindMainWindowFromPid(pids[0]);
			// User32.INSTANCE.PostMessage(hwnd, WinUser.WM_DESTROY, null,
			// null);

		} else {
			System.err.println("ERROR: The application \"" + programName + "\" isn't running!");
		}
	}

	public static HWND FindMainWindowFromPid(final long targetProcessId) {

		final List<HWND> resultList = new ArrayList<HWND>();
		class ParentWindowCallback implements WinUser.WNDENUMPROC {
			@Override
			public boolean callback(HWND hWnd, Pointer lParam) {
				IntByReference lpdwProcessId = new IntByReference();
				User32.INSTANCE.GetWindowThreadProcessId(hWnd, lpdwProcessId);
				long pid = lpdwProcessId.getPointer().getInt(0);
				if (pid == targetProcessId)
					if (resultList.isEmpty())
						resultList.add(hWnd);
				return true;
			}
		}

		User32.INSTANCE.EnumWindows(new ParentWindowCallback(), new Pointer(0));
		if (!resultList.isEmpty())
			return resultList.get(0);
		return null;
	}

	private static List<HWND> getHwndsOfPid(int pid) {

		My_WNDENUMPROC my_enumproc = new My_WNDENUMPROC(pid);
		User32.INSTANCE.EnumWindows(my_enumproc, null);
		return my_enumproc.getHwnds();
	}

}
