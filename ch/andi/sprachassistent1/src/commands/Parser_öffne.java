package commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;

import bgFunc.Files;
import bgFunc.Paths;
import bgFunc.Processes;
import execute.OpenProgram;

public class Parser_öffne {

	public Parser_öffne(String input) {

	}

	public void parse(String input) {
		System.out.println("öffne parser: " + input);
		String[] words = input.split(" ");
		
		String args = input.substring(words[0].length()+1, input.length());
		String programName = getContainedProgramName(args);

		if (means(args, "(?!.*nicht.*).*neu(\\w){0,2} fenster")) {
			System.out.println("neues Fenster");
			//if args contain a program name -> run that program
			String path = Paths.getPathOfKnownApp(programName);
			OpenProgram.open(path);

			//else -> run new instance of program in foreground
			OpenProgram.open(Paths.getPathOfForegroundApp());

		} else {
			String path = Paths.getPathOfKnownApp(programName);
			
			//if programName is running -> move in to foreground
			if(Processes.isProcessRunning(path)) {
				String[] pathParts = path.split("\\\\");
				HWND hwnd = User32.INSTANCE.FindWindow(null, Processes.getTitleOfProcess(pathParts[pathParts.length-1]));
				User32.INSTANCE.ShowWindow(hwnd, 9); // SW_RESTORE
				User32.INSTANCE.SetForegroundWindow(hwnd);
				
			}else {
				OpenProgram.open(path);
			}
		}
	}

	private boolean means(String input, String meaning) {

		System.out.println(":"+input);

		Pattern pattern = Pattern.compile(meaning, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(input);

		if (matcher.find()) {
			return true;
		}

		return false;
	}
	
	private static String getContainedProgramName(String input) {
		
		String[] programNames = Files.getAllNames(Files.PROGRAMS_PATH);
		for(int i = 0; i < programNames.length; i++) {
			if(input.contains(programNames[i])) {
				return programNames[i];
			}
		}
		return null;
	}

}
