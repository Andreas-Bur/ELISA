package parser;

import bgFunc.MyParser;
import bgFunc.MyPaths;
import execute.CloseProgram;

public class Parser_schliesseP {

	public static void parse(String input) {
		System.out.println("(Parser_schliesseP.parse) input: " + input);
		String[] words = input.split(" ");

		String args = input.substring(words[0].length() + 1, input.length());
		String programName = MyParser.getContainedProgramName(args);

		if (MyParser.means(args, ".*(das|dieses) fenster")) {

			String path = MyPaths.getPathOfKnownApp(programName);
			if (path != null) {
				CloseProgram.closeTopWindowOfProgram(path, programName);
			}
			else {
				path = MyPaths.getPathOfForegroundApp();
				CloseProgram.closeTopWindowOfProgram(path, path);
			}

		} else if (MyParser.means(args, ".*(das|dieses) programm")) {
			String path = MyPaths.getPathOfForegroundApp();
			System.out.println("(Parser_schliesseP.parse) path: "+path);
			if(programName==null) {
				String[] parts = path.split("\\\\");
				programName=parts[parts.length-1];
			}
			CloseProgram.quitProgram(path, programName);
		} else { 
			String path = MyPaths.getPathOfKnownApp(programName);
			CloseProgram.quitProgram(path, programName);
		}
	}
}
