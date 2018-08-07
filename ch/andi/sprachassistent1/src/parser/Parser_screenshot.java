package parser;

import execute.ComputerControl;

public class Parser_screenshot implements BaseParser{

	public void parse(String input, String tag) {
		ComputerControl.takeScreenshot();
	}
}
