package commands;

import bgFunc.MyParser;

public class Parser_schliesse {

	public Parser_schliesse(String input) {
		// TODO Auto-generated constructor stub
	}
	
	public static void parse(String input) {
		System.out.println("schliesse parser: "+input);
		String[] words = input.split(" ");
		
		String args = input.substring(words[0].length()+1, input.length());
		String programName = MyParser.getContainedProgramName(args);
	
		if (MyParser.means(args, "(?!.*nicht.*).*dies(\\w){0,2} (bildschirm)?fenster")) {
			
		} else if (MyParser.means(args, "(?!.*nicht.*).*dies(\\w){0,2} programm")){
			
		}
	}

}
