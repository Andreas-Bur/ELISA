package jna.office;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Ole32;
import com.sun.jna.platform.win32.Variant.VARIANT;
import com.sun.jna.platform.win32.COM.COMException;
import com.sun.jna.platform.win32.COM.COMLateBindingObject;
import com.sun.jna.platform.win32.COM.IDispatch;

public class Excel extends Office {

	public Excel() throws COMException {
		super("Excel.Application");
	}
	
	public static void main(String[] args) {
		Ole32.INSTANCE.CoInitializeEx(Pointer.NULL, Ole32.COINIT_MULTITHREADED);
		try {
			Excel excel = new Excel();
			System.out.println(excel.getSelectedText());
		} finally {
			Ole32.INSTANCE.CoUninitialize();
		}
	}

	// Invokes
	// -----------------------------------------------------------------------------------------------------

	public void newDocument() throws COMException {
		this.invokeNoReply("Add", getDocuments());
	}

	public void save(boolean bNoPrompt) throws COMException {
		VARIANT vtNoPrompt = new VARIANT(bNoPrompt);
		this.invokeNoReply("Save", this.getDocuments(), vtNoPrompt);
	}

	public void closeActiveDocument(boolean bSave) throws COMException {
		this.invokeNoReply("Close", getActiveWorkbook(), new VARIANT(bSave));
	}

	// COMLateBindingObjects
	// -------------------------------------------------------------------------------------------------------

	private ActiveWorkbook getActiveWorkbook() {
		return new ActiveWorkbook(this.getAutomationProperty("ActiveWorkbook"));
	}

	private Workbooks getDocuments() {
		return new Workbooks(this.getAutomationProperty("WorkBooks"));
	}

	private ActiveSheet getActiveDocument() {
		return new ActiveSheet(this.getAutomationProperty("ActiveSheet"));
	}

	private Cell getActiveCell() {
		return new Cell(this.getAutomationProperty("ActiveCell", getActiveWindow()));
	}

	// Properties
	// -------------------------------------------------------------------------------------------------------

	public String getPathOfActiveDocument() throws COMException {
		return getActiveDocument().getStringProperty("FullName");
	}

	public boolean isActiveDocumentSaved() {
		if (getPathOfActiveDocument().contains(":")) {
			return true;
		}
		return false;
	}

	public void insertValue(String range, String value) throws COMException {
		Range pRange = new Range(this.getAutomationProperty("Range", this.getActiveDocument(), new VARIANT(range)));
		this.setProperty("Value", pRange, new VARIANT(value));
	}

	// Classes
	// -------------------------------------------------------------------------------------------------------

	private class Workbooks extends COMLateBindingObject {
		public Workbooks(IDispatch iDispatch) throws COMException {
			super(iDispatch);
		}
	}

	private class ActiveWorkbook extends COMLateBindingObject {
		public ActiveWorkbook(IDispatch iDispatch) throws COMException {
			super(iDispatch);
		}
	}

	private class ActiveSheet extends COMLateBindingObject {
		public ActiveSheet(IDispatch iDispatch) throws COMException {
			super(iDispatch);
		}
	}

	private class Cell extends COMLateBindingObject {
		public Cell(IDispatch iDispatch) throws COMException {
			super(iDispatch);
		}
	}
}
