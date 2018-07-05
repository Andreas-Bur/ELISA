package parser;

import bgFunc.MyParser;
import bgFunc.Processes;
import execute.OpenProgram;
import jna.office.ExcelControl;

public class Parser_excel {

	public static void parse(String input) {
		parse(input, "");
	}

	public static void parse(String input, String tag) {
		ExcelControl excelControl = null;
		try {

			excelControl = new ExcelControl();
			if (tag.equals("fontSize")) {
				String[] endingStrings = { " auf ", " zu ", " font ", " fontgrösse ", " textgrösse ", " schrift ",
						" schriftgrösse ", "text " };
				int endingIndex = 0;
				for (int i = 0; i < endingStrings.length; i++) {
					int index = input.lastIndexOf(endingStrings[i]) + endingStrings[i].length();
					endingIndex = endingIndex > index ? endingIndex : index;
				}
				int size = MyParser.getNumber(input.substring(endingIndex));
				excelControl.setTextSize(size);
			} else if (tag.equals("textProperties")) {
				if (input.contains("fett")) {
					excelControl.setTextBoldState(!input.contains("nicht"));
				} else if (input.contains("kursiv")) {
					excelControl.setTextItalicState(!input.contains("nicht"));
				} else if (input.contains("unterstrichen") || input.contains("unterstreiche")) {
					excelControl.setTextUnderlineState(!input.contains("nicht"));
				} /*
					 * else if (input.contains("durchgestrichen") ||
					 * input.contains("streiche")) {
					 * excelControl.setTextStrikethroughState(!input.contains(
					 * "nicht")); }
					 */
			}

			else if (input.startsWith("erstelle")) {
				if (input.contains("dokument") || input.contains("datei")) {
					excelControl.newDocument();
				}
			} else if (input.startsWith("öffne")) {
				if (input.contains("dokument") || input.contains("datei")) {
					excelControl.openDocument();
				} else if (input.contains("neues fenster")) {
					OpenProgram.open(Processes.WORD_PATH);
				}
			} else if (input.startsWith("speicher")) {
				if (input.contains("unter") || input.contains("als")) {
					excelControl.saveAs();
				} else {
					excelControl.saveDocument();
				}
			}
		} finally {
			excelControl.disposeFactory();
		}

	}
}
