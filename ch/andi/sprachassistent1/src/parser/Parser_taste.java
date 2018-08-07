package parser;

import java.awt.event.KeyEvent;

import execute.KeyPress;

public class Parser_taste implements BaseParser{

	public void parse(String input, String tag) {
		KeyPress keyPress = new KeyPress();

		if (tag.contains("kopiere")) {
			keyPress.press(KeyEvent.VK_CONTROL, 'C');
		} else if (tag.contains("ausschneiden")) {
			keyPress.press(KeyEvent.VK_CONTROL, 'X');
		} else if (tag.contains("einfügen")) {
			keyPress.press(KeyEvent.VK_CONTROL, 'V');
		} else if (tag.contains("auswählen")) {
			keyPress.press(KeyEvent.VK_CONTROL, 'A');
		}

		else if (input.contains("enter")) {
			keyPress.press('\r');
			keyPress.press('\n');
		} else if (input.contains("escape")) {
			keyPress.press(KeyEvent.VK_ESCAPE);
		} else if (input.contains("tab")) {
			keyPress.press(KeyEvent.VK_TAB);
		}
	}
}
