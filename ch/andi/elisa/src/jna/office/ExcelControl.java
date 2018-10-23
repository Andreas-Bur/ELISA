package jna.office;

import com.sun.jna.platform.win32.Variant.VARIANT;
import com.sun.jna.platform.win32.WinDef.LCID;
import com.sun.jna.platform.win32.COM.util.Factory;

import jna.office.excel.ApplicationE;
import jna.office.excel.ComExcelApp;
import jna.office.office.Dialogs;
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
	}

	public void newDocument() {
		excelApp.getWorkbooks().Add();
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

	public void openImageDialog() {
		fact.disableTimeout();
		Dialogs d = excelApp.getDialogs();
		d.getItem(new VARIANT(342)).Show();
		fact.enableTimeout();
	}

	public void openPrintDialog() {
		fact.disableTimeout();
		Dialogs d = excelApp.getDialogs();
		d.getItem(new VARIANT(8)).Show();
		fact.enableTimeout();
	}

	public void saveDocument() {
		// Überprüft ob der Pfad ein Dateipfad ist und somit die Datei bereits
		// einmal gespeichert wurde
		if (excelApp.getActiveWorkbook().getPath().contains(":")) {
			excelApp.getActiveWorkbook().Save();
		} else {
			saveAs();
		}
	}

	public void setTextColor(int color) {
		excelApp.getSelection().getFont().setColorIndex(color);
	}

	public void setCellColor(int color) {
		excelApp.getActiveWindow().getRangeSelection().getInterior().setColorIndex(color);
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

	public void setTextSize(int size) {
		excelApp.getSelection().getFont().setSize(size);
	}

	public void increaseTextSize() {
		setTextSize(excelApp.getSelection().getFont().getSize() + 2);
	}

	public void decreaseTextSize() {
		setTextSize(excelApp.getSelection().getFont().getSize() - 2);
	}

	public void disposeFactory() {
		fact.disposeAll();
	}
}
