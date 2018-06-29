package parser;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Ole32;

import jna.office.WordControl;

public class Parser_word {

	public static void parse(String input) {

		Ole32.INSTANCE.CoInitializeEx(Pointer.NULL, Ole32.COINIT_MULTITHREADED);
		try {
			if (input.startsWith("erstelle")) {
				if (input.contains("dokument") || input.contains("datei")) {
					WordControl.newDocument();
				}
			}else if (input.startsWith("öffne")) {
				if (input.contains("dokument") || input.contains("datei")) {
					WordControl.openDocument();
				}
			}else if (input.startsWith("speichere")) {
				if (input.contains("dokument") || input.contains("datei")) {
					WordControl.openDocument();
				}
			}
		} finally {
			Ole32.INSTANCE.CoUninitialize();
		}
	}

}
