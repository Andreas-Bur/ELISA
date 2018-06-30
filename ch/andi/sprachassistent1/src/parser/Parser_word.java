package parser;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Ole32;

import bgFunc.Processes;
import execute.OpenProgram;
import jna.office.WordControl;

public class Parser_word {

	public static void parse(String input) {

		Ole32.INSTANCE.CoInitializeEx(Pointer.NULL, Ole32.COINIT_MULTITHREADED);
		try {
			if (input.startsWith("erstelle")) {
				if (input.contains("dokument") || input.contains("datei")) {
					WordControl.newDocument();
				}
			} else if (input.startsWith("öffne")) {
				if (input.contains("dokument") || input.contains("datei")) {
					WordControl.openDocument();
				}else if(input.contains("neues fenster")) {
					OpenProgram.open(Processes.WORD_PATH);
				}
			} else if (input.startsWith("speicher")) {
				WordControl.saveDocument();
			}
		} finally {
			Ole32.INSTANCE.CoUninitialize();
		}
	}

}
