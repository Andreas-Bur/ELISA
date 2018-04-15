package commands;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinUser.WNDENUMPROC;

import bgFunc.MyParser;
import bgFunc.Paths;
import bgFunc.Processes;
import execute.OpenProgram;

public class Parser_öffne {

	public Parser_öffne() {

	}

	public static void parse(String input) {
		System.out.println("(Parser_öffne.parse) input: " + input);
		String[] words = input.split(" ");

		String args = input.substring(words[0].length() + 1, input.length());
		String programName = MyParser.getContainedProgramName(args);

		if (MyParser.means(args, "(?!.*nicht.*).*neu(\\w){0,2} (bildschirm)?fenster")) {
			System.out.println("(Parser_öffne.parse) neues Fenster");
			
			String path = Paths.getPathOfKnownApp(programName);
			// if args contain a program name -> run that program
			if (path != null) {
				OpenProgram.open(path);
			}
			// else -> run new instance of program in foreground
			else {
				OpenProgram.open(Paths.getPathOfForegroundApp());
			}

		} else {
			System.out.println("(Parser_öffne.parse) kein neues Fenster");
			String path = Paths.getPathOfKnownApp(programName);

			// if programName is running -> move into foreground
			if (Processes.isProcessRunning(path)) {
				System.out.println("(Parser_öffne.parse) is already running");
				String[] pathParts = path.split("\\\\");
				
				HWND hwnd = User32.INSTANCE.FindWindow(null, Processes.getTitleOfProcess(pathParts[pathParts.length - 1]));
				User32.INSTANCE.ShowWindow(hwnd, 9); // SW_RESTORE
				User32.INSTANCE.SetForegroundWindow(hwnd);
				System.out.println("(Parser_öffne.parse) moved to foregound");

			} else {
				OpenProgram.open(path);
			}
		}
	}
	
	private static HWND getHwndOfPid(int pid) {
		
		User32.INSTANCE.EnumWindows(new WNDENUMPROC() {
			
			@Override
			public boolean callback(HWND hWnd, Pointer data) {
				// TODO Auto-generated method stub
				return false;
			}
		}, null);
		
		return null;
	}

	/*
	 * public static void main(String[] args) { Parser_öffne parser = new
	 * Parser_öffne(""); parser.parse("öffne ein neues bildschirmfenster"); }
	 */
}
