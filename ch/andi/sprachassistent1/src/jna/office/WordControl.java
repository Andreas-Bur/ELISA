package jna.office;

public class WordControl {

	static Word word = new Word();

	public static void newDocument() {
		word.newDocument();
	}
	
	public static void openDocument() {
		word.showOpenDocumentDialog();
	}
	
	public static void saveAs() {
		word.showSaveAsDialog();
	}
	
	public static void saveDocument() {
		if(word.isActiveDocumentSaved()) {
			word.save(true);
		}else {
			word.showSaveAsDialog();
		}
	}
	public static void setTextBoldState(boolean state) {
		word.setSelectionBoldState(state);
	}
	public static void setTextItalicState(boolean state) {
		word.setSelectionItalicState(state);
	}
	public static void setTextUnderlineState(boolean state) {
		word.setSelectionUnderlineState(state);
	}
	public static void setTextStrikethroughState(boolean state) {
		word.setSelectionStrikethroughState(state);
	}
	public static void setTextSize(int size) {
		word.setSelectionFontSize(size);
	}
}
