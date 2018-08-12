package parser;

import java.util.ArrayList;
import java.util.List;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinDef.HWND;

import bgFunc.MyParser;
import bgFunc.MyPaths;
import bgFunc.Processes;
import execute.OpenProgram;
import jna.My_WNDENUMPROC;

public class Parser_öffneP implements BaseParser {

	public void parse(String input, String tag) {
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

				List<HWND> hwnds = new ArrayList<>();
				int[] pids = Processes.getPIDsOfProcess(pathParts[pathParts.length - 1]);
				for (int pid : pids) {
					hwnds.addAll(getHwndsOfPid(pid));
				}

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

				System.out.println("(Parser_öffne.parse) moved to foregound");

			} else {
				OpenProgram.open(path);
			}
		}
	}

	private static List<HWND> getHwndsOfPid(int pid) {
		My_WNDENUMPROC my_enumproc = new My_WNDENUMPROC(pid, false);
		User32.INSTANCE.EnumWindows(my_enumproc, null);
		return my_enumproc.getHwnds();
	}
}
