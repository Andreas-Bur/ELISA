package jna.office;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Ole32;
import com.sun.jna.platform.win32.Variant.VARIANT;
import com.sun.jna.platform.win32.WinDef.LCID;
import com.sun.jna.platform.win32.COM.util.Factory;

import jna.office.office.Dialogs;
import jna.office.office.FileDialog;
import jna.office.word.ApplicationW;
import jna.office.word.ComWordApp;

public class WordControl {

	Factory fact;
	ApplicationW wordApp;
	int timeoutMilliseconds = 10000;

	public WordControl() {
		fact = new Factory(timeoutMilliseconds);
		fact.setLCID(new LCID(0x0409));

		ComWordApp word = fact.fetchObject(ComWordApp.class);
		wordApp = word.queryInterface(ApplicationW.class);
		wordApp.setVisible(true);
	}

	public static void main(String[] args) {
		Ole32.INSTANCE.CoInitializeEx(Pointer.NULL, Ole32.COINIT_MULTITHREADED);
		WordControl wordControl = new WordControl();
		//wordControl.setTextBgColor(2);
		wordControl.wordApp.getSelection().getRange().setStyle(-50);
		Ole32.INSTANCE.CoUninitialize();
	}

	public void newDocument() {
		wordApp.getDocuments().Add();
	}

	public void openDocumentDialog() {
		fact.disableTimeout();
		FileDialog openDialog = wordApp.getFileDialog(new VARIANT(1));
		openDialog.Show();
		openDialog.Execute();
		fact.enableTimeout();
	}

	public void saveAsDialog() {
		fact.disableTimeout();
		FileDialog openDialog = wordApp.getFileDialog(new VARIANT(2));
		openDialog.Show();
		openDialog.Execute();
		fact.enableTimeout();
	}

	public void saveDocument() {
		//check if the path is a filepath
		if (wordApp.getActiveDocument().getPath().contains(":")) {
			wordApp.getActiveDocument().Save();
		} else {
			saveAsDialog();
		}
	}
	
	public void openImageDialog() {
		fact.disableTimeout();
		Dialogs d = wordApp.getDialogs();
		d.getItem(new VARIANT(163)).Show();
		fact.enableTimeout();
	}
	
	public void openPrintDialog() {
		Dialogs d = wordApp.getDialogs();
		d.getItem(new VARIANT(88)).Show();
	}
	
	public void openTableDialog() {
		Dialogs d = wordApp.getDialogs();
		d.getItem(new VARIANT(129)).Show();
	}
	
	public void setTextColor(int color) {
		wordApp.getSelection().getFont().setColorIndex(color);
	}
	
	public void setTextBgColor(int color) {
		wordApp.getSelection().getFont().getShading().setBackgroundPatternColorIndex(color);
	}
	
	public void setStyle(int style) {
		wordApp.getSelection().getRange().setStyle(style);
	}
	
	public void resetTextColor() {
		wordApp.getSelection().getFont().setColorIndex(0);
	}

	public void setTextBoldState(boolean state) {
		wordApp.getSelection().getFont().setBold(state);
	}

	public void setTextItalicState(boolean state) {
		wordApp.getSelection().getFont().setItalic(state);
	}

	public void setTextUnderlineState(boolean state) {
		wordApp.getSelection().getFont().setUnderline(state);
	}

	public void setTextSize(int size) {
		wordApp.getSelection().getFont().setSize(size);
	}

	public void increaseTextSize() {
		setTextSize(wordApp.getSelection().getFont().getSize() + 2);
	}

	public void decreaseTextSize() {
		setTextSize(wordApp.getSelection().getFont().getSize() - 2);
	}
	
	public void addPageNumbers() {
		wordApp.getActiveDocument().getSections().getItem(1).getFooters().getItem(1).getPageNumbers().Add(4, true);
	}

	public void disposeFactory() {
		fact.disposeAll();
	}
}
