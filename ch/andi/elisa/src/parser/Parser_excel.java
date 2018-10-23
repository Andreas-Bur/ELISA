package parser;

import bgFunc.MyParser;
import bgFunc.Processes;
import execute.OpenProgram;
import feedback.AlertController;
import jna.office.ExcelControl;

public class Parser_excel implements BaseParser {

	public void parse(String input, String tag) {
		ExcelControl excelControl = null;
		try {

			excelControl = new ExcelControl();
			if (tag.equals("fontSize")) {
				excelControl.setTextSize(MyParser.extractIntFromText(input));
			} else if (tag.equals("fontSize2")) {
				if (input.contains("vergrösser")) {
					excelControl.increaseTextSize();
				} else if (input.contains("verkleiner")) {
					excelControl.decreaseTextSize();
				}
			} else if (tag.equals("textProperties")) {
				if (input.contains("fett")) {
					excelControl.setTextBoldState(!input.contains("nicht"));
				} else if (input.contains("kursiv")) {
					excelControl.setTextItalicState(!input.contains("nicht"));
				} else if (input.contains("unterstrichen") || input.contains("unterstreiche")) {
					excelControl.setTextUnderlineState(!input.contains("nicht"));
				}
			} else if (tag.equals("officeObj")) {
				if (input.contains("bild")) {
					excelControl.openImageDialog();
				} else if (input.contains("diagramm")) {

				} else {
					AlertController.showWrongContextCommandError(input, "Excel");
				}
			} else if (tag.equals("drucke")) {
				excelControl.openPrintDialog();
			} else if (tag.equals("color")) {
				String[][] colors = { { "automatisch", "-4105" }, { "schwarz", "1" }, { "blau", "5" }, { "hellgrün", "4" },
						{ "dunkelblau", "11" }, { "dunkelrot", "9" }, { "dunkelgelb", "12" }, { "grau", "16" }, { "grün", "10" },
						{ "pink", "7" }, { "rot", "3" }, { "türkis", "8" }, { "violett", "21" }, { "weiss", "2" },
						{ "gelb", "6" } };
				if (input.contains("text")) {
					for (int i = 0; i < colors.length; i++) {
						if (input.matches(".*\\b" + colors[i][0] + "\\b.*")) {
							excelControl.setTextColor(Integer.parseInt(colors[i][1]));
							break;
						}
					}
				} else if (input.contains("zelle") || input.contains("hintergrund")
						|| (input.contains("markiere") && input.contains("text"))) {
					for (int i = 0; i < colors.length; i++) {
						if (input.matches(".*\\b" + colors[i][0] + "\\b.*")) {
							excelControl.setCellColor(Integer.parseInt(colors[i][1]));
							break;
						}
					}
				}
			} else if (input.startsWith("erstelle")) {
				if (input.contains("dokument") || input.contains("datei")) {
					excelControl.newDocument();
				}
			} else if (input.startsWith("öffne")) {
				if (input.contains("dokument") || input.contains("datei")) {
					excelControl.openDocument();
				} else if (input.contains("neues fenster")) {
					OpenProgram.open(Processes.getExcelPath());
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
