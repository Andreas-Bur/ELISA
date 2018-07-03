package jna.office;

public class PowerpointControl {

	static PowerPoint powerpoint = new PowerPoint();

	public static void newDocument() {
		powerpoint.newDocument();
	}
	
	public static void openDocument() {
		powerpoint.showOpenDocumentDialog();
	}
	
	public static void saveAs() {
		powerpoint.showSaveAsDialog();
	}
	
	public static void saveDocument() {
		if(powerpoint.isActiveDocumentSaved()) {
			powerpoint.save(true);
		}else {
			powerpoint.showSaveAsDialog();
		}
	}
	public static void setTextBoldState(boolean state) {
		powerpoint.setSelectionBoldState(state);
	}
	public static void setTextItalicState(boolean state) {
		powerpoint.setSelectionItalicState(state);
	}
	public static void setTextUnderlineState(boolean state) {
		powerpoint.setSelectionUnderlineState(state);
	}
	public static void setTextStrikethroughState(boolean state) {
		powerpoint.setSelectionStrikethroughState(state);
	}
	public static void setTextSize(int size) {
		powerpoint.setSelectionFontSize(size);
	}
}
