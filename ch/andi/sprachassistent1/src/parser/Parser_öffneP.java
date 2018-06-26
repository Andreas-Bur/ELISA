package parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.INT_PTR;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinUser.WNDENUMPROC;
import com.sun.jna.ptr.IntByReference;

import bgFunc.MyParser;
import bgFunc.MyPaths;
import bgFunc.Processes;
import execute.OpenProgram;

public class Parser_öffneP {

	public static void parse(String input) {
		System.out.println("(Parser_öffneP.parse) input: " + input);
		String[] words = input.split(" ");

		String args = input.substring(words[0].length() + 1, input.length());
		String programName = MyParser.getContainedProgramName(args);

		if (MyParser.means(args, ".*neu(\\w){0,2} fenster")) {
			System.out.println("(Parser_öffneP.parse) neues Fenster");

			String path = MyPaths.getPathOfKnownApp(programName);
			// if args contain a program name -> run that program
			if (path != null) {
				OpenProgram.open(path);
			}
			// else -> run new instance of program in foreground
			else {
				OpenProgram.open(MyPaths.getPathOfForegroundApp());
			}

		} else {
			System.out.println("(Parser_öffneP.parse) kein neues Fenster");
			String path = MyPaths.getPathOfKnownApp(programName);

			// if programName is running -> move into foreground
			if (Processes.isProcessRunning(path)) {
				System.out.println("(Parser_öffneP.parse) is already running");
				String[] pathParts = path.split("\\\\");

				List<HWND> hwnds = getHwndsOfPid(Processes.getPidOfProcess(pathParts[pathParts.length - 1]));
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
				}

				System.out.println("(Parser_öffne.parse) moved to foregound");

			} else {
				OpenProgram.open(path);
			}
		}
	}

	private static List<HWND> getHwndsOfPid(int pid) {

		My_WNDENUMPROC my_enumproc = new My_WNDENUMPROC(pid);
		User32.INSTANCE.EnumWindows(my_enumproc, null);

		return my_enumproc.getHwnds();
	}

	/*public static void main(String[] args) {
		Parser_öffne.parse("öffne _Wireshark");
		//System.out.println(Processes.getPidOfProcess("eclipse.exe"));
		//Parser_öffne.getHwndsOfPid(Processes.getPidOfProcess("eclipse.exe"));
	}*/

	private static class My_WNDENUMPROC implements WNDENUMPROC {

		private List<HWND> output = new ArrayList<HWND>();
		private int pid;

		public My_WNDENUMPROC(int pid) {
			this.pid = pid;
		}

		@Override
		public boolean callback(HWND hWnd, Pointer data) {
			IntByReference test = new IntByReference();
			User32.INSTANCE.GetWindowThreadProcessId(hWnd, test);

			if (test.getValue() == pid) {
				// System.out.println("GetWindowThreadProcessId == pid");

				char[] buffer = new char[200];

				int result = User32.INSTANCE.GetWindowText(hWnd, buffer, 200);

				if (result == 0 || !User32.INSTANCE.IsWindowVisible(hWnd)) {
					return true;
				}

				System.out.println("Window title: " + String.copyValueOf(buffer));

				output.add(hWnd);
				
				return false; //set to true if all visible windows should be returned

			}
			return true; //set to true if all visible windows should be returned
		}

		public List<HWND> getHwnds() {
			return this.output;
		}
	}
}
