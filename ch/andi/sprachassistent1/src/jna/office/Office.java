package jna.office;

import com.sun.jna.platform.win32.Variant.VARIANT;
import com.sun.jna.platform.win32.COM.COMException;
import com.sun.jna.platform.win32.COM.COMLateBindingObject;
import com.sun.jna.platform.win32.COM.IDispatch;

public class Office extends COMLateBindingObject {

	public Office(String application) {
		super(application, true);
	}

	// Properties
	// -------------------------------------------------------------------------------------------------------

	String getVersion() throws COMException {
		return this.getStringProperty("Version");
	}

	String getCaption() throws COMException {
		return this.getStringProperty("Caption");
	}

	String getSelectedText() {
		return getSelection().getStringProperty("Text");
	}
	String getSelectedText(Selection sel) {
		return sel.getStringProperty("Text");
	}

	boolean isSelectionBold() {
		int state = getFont(getSelection()).getIntProperty("Bold");
		if (state == -1) {
			return true;
		}
		return false;
	}

	boolean isSelectionItalic() {
		int state = getFont(getSelection()).getIntProperty("Italic");
		if (state == -1) {
			return true;
		}
		return false;
	}

	boolean isSelectionUnderlined() {
		int state = getFont(getSelection()).getIntProperty("Underline");
		if (state == -1) {
			return true;
		}
		return false;
	}

	boolean isSelectionStruckthrough() {
		int state = getFont(getSelection()).getIntProperty("Strikethrough");
		if (state == -1) {
			return true;
		}
		return false;
	}

	// Invokes
	// -------------------------------------------------------------------------------------------------------

	void showOpenDocumentDialog() throws COMException {
		this.invokeNoReply("Show", getOpenDialog());
		this.invokeNoReply("Execute", getOpenDialog());
	}

	void showSaveAsDialog() throws COMException {
		this.invokeNoReply("Show", getSaveAsDialog());
		this.invokeNoReply("Execute", getSaveAsDialog());
	}

	void quit() throws COMException {
		this.invokeNoReply("Quit");
	}

	public void insertText(String text) throws COMException {
		this.invokeNoReply("TypeText", getSelection(), new VARIANT(text));
	}

	public void deleteText() throws COMException {
		this.invokeNoReply("Delete", getSelection());
	}

	public void setSelectionBoldState(boolean state) {
		getFont(getSelection()).setProperty("Bold", state);
	}

	public void setSelectionItalicState(boolean state) {
		getFont(getSelection()).setProperty("Italic", state);
	}

	public void setSelectionUnderlineState(boolean state) {
		getFont(getSelection()).setProperty("Underline", state);
	}

	public void setSelectionStrikethroughState(boolean state) {
		getFont(getSelection()).setProperty("Strikethrough", state);
	}

	public void setSelectionFontSize(int size) {
		getFont(getSelection()).setProperty("Size", size);
	}

	// Methods
	// -------------------------------------------------------------------------------------------------------

	public void replaceText(String text) throws COMException {
		deleteText();
		insertText(text);
	}

	// COMLateBindingObjects
	// -------------------------------------------------------------------------------------------------------

	Application getApplication() {
		return new Application(this.getAutomationProperty("Application"));
	}

	ActiveWindow getActiveWindow() {
		return new ActiveWindow(this.getAutomationProperty("ActiveWindow"));
	}

	FileDialog getOpenDialog() {
		return new FileDialog(this.getAutomationProperty("FileDialog", this, new VARIANT(1)));
	}

	FileDialog getSaveAsDialog() {
		return new FileDialog(this.getAutomationProperty("FileDialog", this, new VARIANT(2)));
	}

	Selection getSelection() {
		return new Selection(this.getAutomationProperty("Selection", this.getIDispatch()));
	}
	
	Selection getSelection(COMLateBindingObject obj) {
		return new Selection(obj.getAutomationProperty("Selection", obj.getIDispatch()));
	}

	Font getFont(COMLateBindingObject obj) {
		return new Font(obj.getAutomationProperty("Font"));
	}

	// Classes
	// -------------------------------------------------------------------------------------------------------

	class Application extends COMLateBindingObject {
		public Application(IDispatch iDispatch) throws COMException {
			super(iDispatch);
		}
	}

	class Documents extends COMLateBindingObject {
		public Documents(IDispatch iDispatch) throws COMException {
			super(iDispatch);
		}
	}

	class ActiveDocument extends COMLateBindingObject {
		public ActiveDocument(IDispatch iDispatch) throws COMException {
			super(iDispatch);
		}
	}

	class ActiveWindow extends COMLateBindingObject {
		public ActiveWindow(IDispatch iDispatch) throws COMException {
			super(iDispatch);
		}
	}

	class Selection extends COMLateBindingObject {
		public Selection(IDispatch iDispatch) throws COMException {
			super(iDispatch);
		}
	}

	class FileDialog extends COMLateBindingObject {
		public FileDialog(IDispatch iDispatch) throws COMException {
			super(iDispatch);
		}
	}

	class Font extends COMLateBindingObject {
		public Font(IDispatch iDispatch) throws COMException {
			super(iDispatch);
		}
	}

	class Range extends COMLateBindingObject {
		public Range(IDispatch iDispatch) throws COMException {
			super(iDispatch);
		}
	}

}
