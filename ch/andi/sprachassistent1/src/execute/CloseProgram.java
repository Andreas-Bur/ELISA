package execute;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinDef.HWND;

import bgFunc.Processes;

public class CloseProgram {

	public CloseProgram() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static void close(String path, String programName) {
		if (Processes.isProcessRunning(path)) {
			String[] pathParts = path.split("\\\\");
			HWND hwnd = User32.INSTANCE.FindWindow(null, Processes.getTitleOfProcess(pathParts[pathParts.length - 1]));

			User32.INSTANCE.PostMessage(hwnd, WinUser.WM_CLOSE, null, null);
			
		} else {
			System.err.println("ERROR: The application \"" + programName + "\" isn't running!");
		}
	}
	
	public static void quit(String path, String programName) {
		if (Processes.isProcessRunning(path)) {
			String[] pathParts = path.split("\\\\");
			HWND hwnd = User32.INSTANCE.FindWindow(null, Processes.getTitleOfProcess(pathParts[pathParts.length - 1]));

			User32.INSTANCE.PostMessage(hwnd, WinUser.WM_QUIT, null, null);
			
		} else {
			System.err.println("ERROR: The application \"" + programName + "\" isn't running!");
		}
	}

}
