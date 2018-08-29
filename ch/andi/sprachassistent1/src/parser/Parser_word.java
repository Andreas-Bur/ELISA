package parser;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Ole32;

import bgFunc.MyParser;
import bgFunc.Processes;
import execute.OpenProgram;
import jna.office.WordControl;

public class Parser_word implements BaseParser {

	public void parse(String input, String tag) {

		WordControl wordControl = null;
		try {
			wordControl = new WordControl();
			if (tag.equals("fontSize")) {
				wordControl.setTextSize(MyParser.extractIntFromText(input));
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
			} else if (tag.equals("officeObj")) {
				if(input.contains("bild")) {
					wordControl.openImageDialog();
				} else if(input.contains("tabelle")) {
					
				} else if(input.contains("diagramm")) {
					
				} else if(input.contains("kopfzeile")) {
					
				} else if(input.contains("fusszeile")) {
					
				} else if(input.contains("seitenzahl")) {
					
				}
			} else if (input.startsWith("erstelle")) {
				if (input.contains("dokument") || input.contains("datei")) {
					wordControl.newDocument();
				}
			} else if (input.startsWith("öffne")) {
				if (input.contains("dokument") || input.contains("datei")) {
					wordControl.openDocumentDialog();
				} else if (input.contains("neues fenster")) {
					OpenProgram.open(Processes.getWordPath());
				}
			} else if (input.startsWith("speicher")) {
				if (input.contains("unter") || input.contains("als")) {
					wordControl.saveAsDialog();
				} else {
					wordControl.saveDocument();
				}
			}
		} finally {
			wordControl.disposeFactory();
		}
	}
	public static void main(String[] args) {
		Ole32.INSTANCE.CoInitializeEx(Pointer.NULL, Ole32.COINIT_MULTITHREADED);
		try {
			WordControl wc = new WordControl();
			wc.openDocumentDialog();
		} finally {
			Ole32.INSTANCE.CoUninitialize();
		}
	}

}
