package parser;

import bgFunc.MyParser;
import bgFunc.Processes;
import execute.OpenProgram;
import jna.office.WordControl;

public class Parser_word implements BaseParser{

	public void parse(String input, String tag) {

		WordControl wordControl = null;
		try {
			wordControl = new WordControl();
			if (tag.equals("fontSize")) {
				String[] endingStrings = { " auf ", " zu ", " font ", " fontgrösse ", " textgrösse ", " schrift ",
						" schriftgrösse ", "text " };
				int endingIndex = 0;
				for (int i = 0; i < endingStrings.length; i++) {
					int index = input.lastIndexOf(endingStrings[i]) + endingStrings[i].length();
					endingIndex = endingIndex > index ? endingIndex : index;
				}
				int size = MyParser.getNumber(input.substring(endingIndex));
				wordControl.setTextSize(size);
			} else if (tag.equals("fontSize2")) {
				if (input.contains("vergrösser")) {
					wordControl.increaseTextSize();
				} else if (input.contains("verkleiner")) {
					wordControl.decreaseTextSize();
				}
			} else if (tag.equals("textProperties")) {
				if (input.contains("fett")) {
					wordControl.setTextBoldState(!input.contains("nicht"));
				} else if (input.contains("kursiv")) {
					wordControl.setTextItalicState(!input.contains("nicht"));
				} else if (input.contains("unterstrichen") || input.contains("unterstreiche")) {
					wordControl.setTextUnderlineState(!input.contains("nicht"));
				}
			}

			else if (input.startsWith("erstelle")) {
				if (input.contains("dokument") || input.contains("datei")) {
					wordControl.newDocument();
				}
			} else if (input.startsWith("öffne")) {
				if (input.contains("dokument") || input.contains("datei")) {
					wordControl.openDocument();
				} else if (input.contains("neues fenster")) {
					OpenProgram.open(Processes.getWordPath());
				}
			} else if (input.startsWith("speicher")) {
				if (input.contains("unter") || input.contains("als")) {
					wordControl.saveAs();
				} else {
					wordControl.saveDocument();
				}
			}
		} finally {
			wordControl.disposeFactory();
		}
	}

}
