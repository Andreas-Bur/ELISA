package jna.office;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Ole32;
import com.sun.jna.platform.win32.Variant.VARIANT;
import com.sun.jna.platform.win32.COM.COMException;
import com.sun.jna.platform.win32.COM.COMLateBindingObject;
import com.sun.jna.platform.win32.COM.IDispatch;

public class Word extends COMLateBindingObject {
	
	public static void main(String[] args) {
		Ole32.INSTANCE.CoInitializeEx(Pointer.NULL, Ole32.COINIT_MULTITHREADED);
		try {
			Word word = new Word();
			System.out.println(word.getPathOfActiveDocument());
		} finally {
			Ole32.INSTANCE.CoUninitialize();
		}
	}

	public Word() throws COMException {
		super("Word.Application", true);
	}

	public Word(boolean visible) throws COMException {
		this();
		this.setVisible(visible);
	}

	public void setVisible(boolean bVisible) throws COMException {
		this.setProperty("Visible", bVisible);
	}

	public String getVersion() throws COMException {
		return this.getStringProperty("Version");
	}

	private ActiveDocument getActiveDocument() {
		return new ActiveDocument(this.getAutomationProperty("ActiveDocument"));
	}

	private Documents getDocuments() {
		Documents pDocuments = new Documents(this.getAutomationProperty("Documents", this.getApplication().getIDispatch()));
		return pDocuments;
	}

	private Application getApplication() {
		return new Application(this.getAutomationProperty("Application"));
	}

	private FileDialog getOpenDialog() {
		return new FileDialog(this.getAutomationProperty("FileDialog", this, new VARIANT(1)));
	}
	
	private FileDialog getSaveAsDialog() {
		return new FileDialog(this.getAutomationProperty("FileDialog", this, new VARIANT(2)));
	}
	
	public String getPathOfActiveDocument() throws COMException{
		return getActiveDocument().getStringProperty("FullName");
	}
	
	public boolean isActiveDocumentSaved() {
		if(getPathOfActiveDocument().contains(":")) {
			return true;
		}
		return false;
	}
	
	// Invokes
	// -----------------------------------------------------------------------------------------------------

	public void newDocument() throws COMException {
		this.invokeNoReply("Add", getDocuments());
	}

	/*public void openDocument(String filename) throws COMException {
		this.invokeNoReply("Open", getDocuments(), new VARIANT(filename));
	}*/

	/**
	 * Open the dialog to open a document
	 */
	public void showOpenDocumentDialog() throws COMException {
		this.invokeNoReply("Show", getOpenDialog());
		this.invokeNoReply("Execute", getOpenDialog());
	}

	public void closeActiveDocument(boolean bSave) throws COMException {
		this.invokeNoReply("Close", getActiveDocument(), new VARIANT(bSave));
	}

	public void quit() throws COMException {
		this.invokeNoReply("Quit");
	}

	public void insertText(String text) throws COMException {
		Selection pSelection = new Selection(this.getAutomationProperty("Selection", this.getIDispatch()));
		this.invokeNoReply("TypeText", pSelection, new VARIANT(text));
	}

	public void save(/*boolean bNoPrompt, LONG originalFormat*/) throws COMException {
		//VARIANT vtNoPrompt = new VARIANT(bNoPrompt);
		//VARIANT vtOriginalFormat = new VARIANT(originalFormat);

		this.invokeNoReply("Save", this.getDocuments()/*, vtNoPrompt, vtOriginalFormat*/);
	}
	
	public void showSaveAsDialog() throws COMException {
		this.invokeNoReply("Show", getSaveAsDialog());
		this.invokeNoReply("Execute", getSaveAsDialog());
	}

	/*public void SaveAs(String FileName, LONG FileFormat) throws COMException {
		VARIANT vtFileName = new VARIANT(FileName);
		VARIANT vtFileFormat = new VARIANT(FileFormat);

		this.invokeNoReply("SaveAs", this.getActiveDocument(), vtFileName, vtFileFormat);
	}*/

	// Classes
	// -------------------------------------------------------------------------------------------------------

	public class Application extends COMLateBindingObject {
		public Application(IDispatch iDispatch) throws COMException {
			super(iDispatch);
		}
	}

	public class Documents extends COMLateBindingObject {
		public Documents(IDispatch iDispatch) throws COMException {
			super(iDispatch);
		}
	}

	public class ActiveDocument extends COMLateBindingObject {
		public ActiveDocument(IDispatch iDispatch) throws COMException {
			super(iDispatch);
		}
	}

	public class Selection extends COMLateBindingObject {
		public Selection(IDispatch iDispatch) throws COMException {
			super(iDispatch);
		}
	}

	private class FileDialog extends COMLateBindingObject {
		public FileDialog(IDispatch iDispatch) throws COMException {
			super(iDispatch);
		}
	}

}
