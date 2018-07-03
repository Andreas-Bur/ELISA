package jna.office;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Ole32;
import com.sun.jna.platform.win32.WinDef.LCID;
import com.sun.jna.platform.win32.COM.util.Factory;

import jna.office.excel.Application;
import jna.office.excel.ComExcelApp;
import jna.office.excel.Workbook;

public class ExcelControl {

	//static Excel excel = new Excel();
	Factory fact;
	Application excelApp;
	public ExcelControl() {
		Ole32.INSTANCE.CoInitializeEx(Pointer.NULL, Ole32.COINIT_MULTITHREADED);
		fact = new Factory();
		fact.setLCID(new LCID(0x0409));
		try {
			ComExcelApp excel = fact.createObject(ComExcelApp.class);
            excelApp = excel.queryInterface(Application.class);
            excelApp.setVisible(true);
			newDocument();
		} finally {
			fact.disposeAll();
			Ole32.INSTANCE.CoUninitialize();
		}
	}

	public static void main(String[] args) {
		ExcelControl excelControl = new ExcelControl();
	}

	public void newDocument() {
		Workbook workbook = excelApp.getWorkbooks().Add();
	}

	public void openDocument() {
		//excel.showOpenDocumentDialog();
	}

	public void saveAs() {
		//excel.showSaveAsDialog();
	}

	public void saveDocument() {
		/*if (excel.isActiveDocumentSaved()) {
			excel.save(true);
		} else {
			excel.showSaveAsDialog();
		}*/
	}

	public void setTextBoldState(boolean state) {
		//excel.setSelectionBoldState(state);
	}

	public void setTextItalicState(boolean state) {
		//excel.setSelectionItalicState(state);
	}

	public void setTextUnderlineState(boolean state) {
		//excel.setSelectionUnderlineState(state);
	}

	public void setTextStrikethroughState(boolean state) {
		//excel.setSelectionStrikethroughState(state);
	}

	public void setTextSize(int size) {
		//excel.setSelectionFontSize(size);
	}
}
