package parser;

import java.awt.event.KeyEvent;

import execute.KeyPress;

public class Parser_taste {

	public static void parse(String input) {
		if(input.contains("enter")) {
			KeyPress.press(KeyEvent.VK_ENTER);
		} else if(input.contains("escape")) {
			KeyPress.press(KeyEvent.VK_ESCAPE);
		}
	}
}
