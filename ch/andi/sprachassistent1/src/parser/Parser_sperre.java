package parser;

import execute.ComputerControl;

public class Parser_sperre implements BaseParser{

	public void parse(String input, String tag) {
		System.out.println("Parser_sperre.parse: input: " + input);
		if (input.contains("computer")) {
			ComputerControl.lockComputer();
		}
	}

}
