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
		PowerpointControl powerpointControl = null;
		try {
			powerpointControl = new PowerpointControl();
			if (tag.equals("fontSize")) {
				String[] endingStrings = { " auf ", " zu ", " font ", " fontgrösse ", " textgrösse ", " schrift ",
						" schriftgrösse ", "text " };
				int endingIndex = 0;
				for (int i = 0; i < endingStrings.length; i++) {
					int index = input.lastIndexOf(endingStrings[i])+endingStrings[i].length();
					endingIndex = endingIndex > index ? endingIndex : index;
				}
				int size = MyParser.getNumber(input.substring(endingIndex));
				powerpointControl.setTextSize(size);
			} else if (tag.equals("textProperties")) {
				if (input.contains("fett")) {
					powerpointControl.setTextBoldState(!input.contains("nicht"));
				} else if (input.contains("kursiv")) {
					powerpointControl.setTextItalicState(!input.contains("nicht"));
				} else if (input.contains("unterstrichen") || input.contains("unterstreiche")) {
					powerpointControl.setTextUnderlineState(!input.contains("nicht"));
				} else if (input.contains("durchgestrichen") || input.contains("streiche")) {
					powerpointControl.setTextStrikethroughState(!input.contains("nicht"));
				}
			}

			else if (input.startsWith("erstelle")) {
				if (input.contains("dokument") || input.contains("datei")) {
					powerpointControl.newDocument();
				}
			} else if (input.startsWith("öffne")) {
				if (input.contains("dokument") || input.contains("datei")) {
					powerpointControl.openDocument();
				} else if (input.contains("neues fenster")) {
					OpenProgram.open(Processes.WORD_PATH);
				}
			} else if (input.startsWith("speicher")) {
				if(input.contains("unter")||input.contains("als")) {
					powerpointControl.saveAs();
				}else {
					powerpointControl.saveDocument();
				}
			}
		} finally {
			powerpointControl.disposeFactory();
			Ole32.INSTANCE.CoUninitialize();
		}
	}
}
