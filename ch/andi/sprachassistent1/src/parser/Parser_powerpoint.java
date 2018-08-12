package parser;

import bgFunc.MyParser;
import bgFunc.Processes;
import execute.OpenProgram;
import jna.office.PowerpointControl;

public class Parser_powerpoint implements BaseParser{

	public void parse(String input, String tag) {

		PowerpointControl powerpointControl = null;
		
		try {
			powerpointControl = new PowerpointControl();
			if (tag.equals("fontSize")) {
				String[] endingStrings = { " auf ", " zu ", " font ", " fontgrösse ", " textgrösse ", " schrift ", " schriftgrösse ", "text " };
				int endingIndex = 0;
				for (int i = 0; i < endingStrings.length; i++) {
					int index = input.lastIndexOf(endingStrings[i]) + endingStrings[i].length();
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
				}
			} else if (tag.equals("folie")) {
				if (input.contains("neue")) {
					powerpointControl.newSlide();
				} else if (input.contains("nächste")) {
					powerpointControl.nextSlide();
				} else if (input.contains("vorherige")) {
					powerpointControl.previousSlide();
				}

			} else if (tag.equals("präsentation")) {
				if (input.startsWith("öffne") || input.startsWith("beginne")) {
					powerpointControl.startSlideShow();
				}
				else if (input.startsWith("schliesse")) {
					powerpointControl.stopSlideShow();
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
					OpenProgram.open(Processes.getPowerPointPath());
				}
			} else if (input.startsWith("speicher")) {
				if (input.contains("unter") || input.contains("als")) {
					powerpointControl.saveAs();
				} else {
					powerpointControl.saveDocument();
				}
			}
		} finally {
			powerpointControl.disposeFactory();
		}
	}
}
