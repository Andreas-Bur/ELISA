package parser;

import execute.ComputerControl;

public class Parser_sperre implements BaseParser {

	public void parse(String input, String tag) {
		if (input.contains("computer")) {
			ComputerControl.lockComputer();
		}
	}

}
