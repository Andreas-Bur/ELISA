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
			word.save();
		}else {
			word.showSaveAsDialog();
		}
	}
}
