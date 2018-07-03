package parser;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Ole32;

import bgFunc.MyParser;
import bgFunc.Processes;
import execute.OpenProgram;
import jna.office.PowerpointControl;

public class Parser_powerpoint {
	
	public static void parse(String input) {
		parse(input, "");
	}

	public static void parse(String input, String tag) {

		Ole32.INSTANCE.CoInitializeEx(Pointer.NULL, Ole32.COINIT_MULTITHREADED);
		try {
			if (tag.equals("fontSize")) {
				String[] endingStrings = { " auf ", " zu ", " font ", " fontgrösse ", " textgrösse ", " schrift ",
						" schriftgrösse ", "text " };
				int endingIndex = 0;
				for (int i = 0; i < endingStrings.length; i++) {
					int index = input.lastIndexOf(endingStrings[i])+endingStrings[i].length();
					endingIndex = endingIndex > index ? endingIndex : index;
				}
				int size = MyParser.getNumber(input.substring(endingIndex));
				PowerpointControl.setTextSize(size);
			} else if (tag.equals("textProperties")) {
				if (input.contains("fett")) {
					PowerpointControl.setTextBoldState(!input.contains("nicht"));
				} else if (input.contains("kursiv")) {
					PowerpointControl.setTextItalicState(!input.contains("nicht"));
				} else if (input.contains("unterstrichen") || input.contains("unterstreiche")) {
					PowerpointControl.setTextUnderlineState(!input.contains("nicht"));
				} else if (input.contains("durchgestrichen") || input.contains("streiche")) {
					PowerpointControl.setTextStrikethroughState(!input.contains("nicht"));
				}
			}

			else if (input.startsWith("erstelle")) {
				if (input.contains("dokument") || input.contains("datei")) {
					PowerpointControl.newDocument();
				}
			} else if (input.startsWith("öffne")) {
				if (input.contains("dokument") || input.contains("datei")) {
					PowerpointControl.openDocument();
				} else if (input.contains("neues fenster")) {
					OpenProgram.open(Processes.WORD_PATH);
				}
			} else if (input.startsWith("speicher")) {
				if(input.contains("unter")||input.contains("als")) {
					PowerpointControl.saveAs();
				}else {
					PowerpointControl.saveDocument();
				}
			}
		} finally {
			Ole32.INSTANCE.CoUninitialize();
		}
	}
}
