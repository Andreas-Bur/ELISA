package jna.office;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Ole32;
import com.sun.jna.platform.win32.Variant.VARIANT;
import com.sun.jna.platform.win32.WinDef.LCID;
import com.sun.jna.platform.win32.COM.util.Factory;

import jna.office.excel.ApplicationE;
import jna.office.excel.ComExcelApp;
import jna.office.excel.Workbook;
import jna.office.office.FileDialog;

public class ExcelControl {

	Factory fact;
	ApplicationE excelApp;
	int timeoutMilliseconds = 10000;

	public ExcelControl() {

		fact = new Factory(timeoutMilliseconds);
		fact.setLCID(new LCID(0x0409));

		ComExcelApp excel = fact.fetchObject(ComExcelApp.class);

		excelApp = excel.queryInterface(ApplicationE.class);
		excelApp.setVisible(true);
		setTextSize(10);

	}

	public static void main(String[] args) {
		Ole32.INSTANCE.CoInitializeEx(Pointer.NULL, Ole32.COINIT_MULTITHREADED);
		ExcelControl excelControl = new ExcelControl();
		Ole32.INSTANCE.CoUninitialize();
	}

	public void newDocument() {
		Workbook newWorkbook = excelApp.getWorkbooks().Add();
	}

	public void openDocument() {
		fact.disableTimeout();
		FileDialog openDialog = excelApp.getFileDialog(new VARIANT(1));
		openDialog.Show();
		openDialog.Execute();
		fact.enableTimeout();
	}

	public void saveAs() {
		fact.disableTimeout();
		FileDialog openDialog = excelApp.getFileDialog(new VARIANT(2));
		openDialog.Show();
		openDialog.Execute();
		fact.enableTimeout();
	}

	public void saveDocument() {
		if (excelApp.getActiveWorkbook().getPath().contains(":")) {
			excelApp.getActiveWorkbook().Save();
		} else {
			saveAs();
		}
	}

	public void setTextBoldState(boolean state) {
		excelApp.getSelection().getFont().setBold(state);
	}

	public void setTextItalicState(boolean state) {
		excelApp.getSelection().getFont().setItalic(state);
	}

	public void setTextUnderlineState(boolean state) {
		excelApp.getSelection().getFont().setUnderline(state);
	}

	public void setTextStrikethroughState(boolean state) {
		excelApp.getSelection().getFont().setStrikethrough(state);
	}

	public void setTextSize(int size) {
		excelApp.getSelection().getFont().setSize(size);
	}

	public void disposeFactory() {
		fact.disposeAll();
	}
}
