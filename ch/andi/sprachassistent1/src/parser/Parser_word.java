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
				if (input.contains("bild")) {
					wordControl.openImageDialog();
				} else if (input.contains("tabelle")) {
					wordControl.openTableDialog();
				} else if (input.contains("diagramm")) {

				} else if (input.contains("seitenzahl")) {
					wordControl.addPageNumbers();
				}
			} else if (tag.equals("textFormat")) {
				if (input.matches(".*\\btitel\\b.*")) {
					wordControl.setStyle(-63);
				} else if (input.matches(".*\\büberschrift\\b.*")) {
					wordControl.setStyle(-2);
				} else if (input.matches(".*\\buntertitel\\b.*")) {
					wordControl.setStyle(-75);
				} else if (input.matches(".*\\bzitat\\b.*")) {
					wordControl.setStyle(-181);
				} else if (input.matches(".*\\bliste\\b.*") || input.matches(".*\\baufzählung\\b.*")) {
					wordControl.setStyle(-49);
				} else if (input.matches(".*\\bnummerierung\\b.*")) {
					wordControl.setStyle(-50);
				} else if (input.matches(".*\\bnormal\\b.*")) {
					wordControl.setStyle(-1);
				}
			} else if (tag.equals("drucke")) {
				wordControl.openPrintDialog();
			} else if (tag.equals("color")) {
				String[][] colors = { { "automatisch", "0" }, { "schwarz", "1" }, { "blau", "2" }, { "hellgrün", "4" },
						{ "dunkelblau", "9" }, { "dunkelrot", "13" }, { "dunkelgelb", "14" }, { "grau", "15" }, { "grün", "11" },
						{ "pink", "5" }, { "rot", "6" }, { "türkis", "3" }, { "violett", "12" }, { "weiss", "8" },
						{ "gelb", "7" } };
				if ((input.contains("färbe") || input.contains("mache")) && input.contains("text")) {
					for (int i = 0; i < colors.length; i++) {
						if (input.matches(".*\\b" + colors[i][0] + "\\b.*")) {
							wordControl.setTextColor(Integer.parseInt(colors[i][1]));
							break;
						}
					}
				} else if ((input.contains("färbe") || input.contains("mache")) && input.contains("hintergrund")
						|| (input.contains("markiere") && input.contains("text"))) {
					for (int i = 0; i < colors.length; i++) {
						if (input.matches(".*\\b" + colors[i][0] + "\\b.*")) {
							wordControl.setTextBgColor(Integer.parseInt(colors[i][1]));
							break;
						}
					}
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
