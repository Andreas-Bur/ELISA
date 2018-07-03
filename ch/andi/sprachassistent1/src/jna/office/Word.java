package jna.office;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Ole32;
import com.sun.jna.platform.win32.Variant.VARIANT;
import com.sun.jna.platform.win32.COM.COMException;

public class Word extends Office {

	public static void main(String[] args) {
		Ole32.INSTANCE.CoInitializeEx(Pointer.NULL, Ole32.COINIT_MULTITHREADED);
		try {
			Word word = new Word();
			int i = 10;
			word.setSelectionFontSize(i);
		} finally {
			Ole32.INSTANCE.CoUninitialize();
		}
	}

	public Word() throws COMException {
		super("Word.Application");
	}
	
	// COMLateBindingObjects
	// -----------------------------------------------------------------------------------------------------

	private ActiveDocument getActiveDocument() {
		return new ActiveDocument(this.getAutomationProperty("ActiveDocument"));
	}

	private Documents getDocuments() {
		return new Documents(this.getAutomationProperty("Documents", this.getApplication().getIDispatch()));
	}

	public String getPathOfActiveDocument() throws COMException {
		return getActiveDocument().getStringProperty("FullName");
	}
	
	// Properties
	// -----------------------------------------------------------------------------------------------------

	public boolean isActiveDocumentSaved() {
		if (getPathOfActiveDocument().contains(":")) {
			return true;
		}
		return false;
	}

	// Invokes
	// -----------------------------------------------------------------------------------------------------

	public void newDocument() throws COMException {
		this.invokeNoReply("Add", getDocuments());
	}

	public void closeActiveDocument(boolean bSave) throws COMException {
		this.invokeNoReply("Close", getActiveDocument(), new VARIANT(bSave));
	}

	public void save(boolean bNoPrompt) throws COMException {
		this.invokeNoReply("Save", this.getDocuments(), new VARIANT(bNoPrompt));
	}
}
