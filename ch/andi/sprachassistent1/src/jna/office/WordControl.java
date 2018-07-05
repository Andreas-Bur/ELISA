package jna.office;

import com.sun.jna.platform.win32.Variant.VARIANT;
import com.sun.jna.platform.win32.WinDef.LCID;
import com.sun.jna.platform.win32.COM.util.Factory;

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

	/*public static void main(String[] args) {
		Ole32.INSTANCE.CoInitializeEx(Pointer.NULL, Ole32.COINIT_MULTITHREADED);
		WordControl wordControl = new WordControl();
		wordControl.decreaseTextSize();
		Ole32.INSTANCE.CoUninitialize();
	}*/

	public void newDocument() {
		wordApp.getDocuments().Add();
	}

	public void openDocument() {
		fact.disableTimeout();
		FileDialog openDialog = wordApp.getFileDialog(new VARIANT(1));
		openDialog.Show();
		openDialog.Execute();
		fact.enableTimeout();
	}

	public void saveAs() {
		fact.disableTimeout();
		FileDialog openDialog = wordApp.getFileDialog(new VARIANT(2));
		openDialog.Show();
		openDialog.Execute();
		fact.enableTimeout();
	}

	public void saveDocument() {
		if (wordApp.getActiveDocument().getPath().contains(":")) {
			wordApp.getActiveDocument().Save();
		} else {
			saveAs();
		}
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

	public void setTextStrikethroughState(boolean state) {
		wordApp.getSelection().getFont().setStrikeThrough(state);
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

	public void disposeFactory() {
		fact.disposeAll();
	}
}
