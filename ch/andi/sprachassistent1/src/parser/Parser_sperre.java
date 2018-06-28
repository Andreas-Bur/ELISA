package parser;

import execute.ComputerControl;

public class Parser_sperre {

	public static void parse(String input) {
		System.out.println("Parser_sperre.parse: input: "+input);
		if(input.contains("computer")) {
			ComputerControl.lockComputer();
		}
	}

}
