package parser;

import java.awt.event.KeyEvent;

import execute.KeyPress;

public class Parser_taste {

	public static void parse(String input) {
		KeyPress keyPress = new KeyPress();
		if(input.contains("enter")) {
			keyPress.press('\r');
			keyPress.press('\n');
		} else if(input.contains("escape")) {
			keyPress.press(KeyEvent.VK_ESCAPE);
		}else if(input.contains("tab")) {
			keyPress.press(KeyEvent.VK_TAB);
		}
	}
}
