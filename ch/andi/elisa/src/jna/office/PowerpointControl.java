package jna.office;

import com.sun.jna.platform.win32.Variant.VARIANT;
import com.sun.jna.platform.win32.WinDef.LCID;
import com.sun.jna.platform.win32.COM.COMInvokeException;
import com.sun.jna.platform.win32.COM.util.Factory;

import jna.office.office.FileDialog;
import jna.office.powerpoint.ApplicationP;
import jna.office.powerpoint.ComPowerpointApp;
import jna.office.powerpoint.CustomLayout;

public class PowerpointControl {

	static Factory fact;
	ApplicationP powerpointApp;
	int timeoutMilliseconds = 10000;

	public PowerpointControl() {
		fact = new Factory(timeoutMilliseconds);
		fact.setLCID(new LCID(0x0409));

		ComPowerpointApp powerpoint = fact.fetchObject(ComPowerpointApp.class);
		powerpointApp = powerpoint.queryInterface(ApplicationP.class);
		powerpointApp.setVisible(true);
	}

	public void newDocument() {
		powerpointApp.getPresentations().Add();
	}

	public void newSlide() {
		CustomLayout layout = powerpointApp.getActivePresentation().getSlideMaster().getCustomLayouts().getItem(new VARIANT(2));
		int activeSlideIndex = 1;
		try {
			activeSlideIndex = powerpointApp.getActiveWindow().getView().getSlide().getSlideIndex();
		}catch(COMInvokeException e) {
			lastSlide();
			activeSlideIndex = powerpointApp.getActiveWindow().getView().getSlide().getSlideIndex();
		}
		powerpointApp.getActivePresentation().getSlides().getAddSlide(new VARIANT(activeSlideIndex + 1), layout);
		goToSlide(activeSlideIndex+1);
	}

	public void openDocument() {
		fact.disableTimeout();
		FileDialog openDialog = powerpointApp.getFileDialog(new VARIANT(1));
		openDialog.Show();
		openDialog.Execute();
		fact.enableTimeout();
	}

	public void saveAs() {
		fact.disableTimeout();
		FileDialog openDialog = powerpointApp.getFileDialog(new VARIANT(2));
		openDialog.Show();
		openDialog.Execute();
		fact.enableTimeout();
	}

	public void saveDocument() {
		// Überprüft ob der Pfad ein Dateipfad ist und somit die Datei bereits
		// einmal gespeichert wurde
		if (powerpointApp.getActivePresentation().getPath().contains(":")) {
			powerpointApp.getActivePresentation().Save();
		} else {
			saveAs();
		}
	}

	public void setTextColor(int color) {
		powerpointApp.getActiveWindow().getSelection().getTextRange().getFont().getColor().setRGB(color);
	}

	public void setTextBoldState(boolean state) {
		powerpointApp.getActiveWindow().getSelection().getTextRange().getFont().setBold(state);
	}

	public void setTextItalicState(boolean state) {
		powerpointApp.getActiveWindow().getSelection().getTextRange().getFont().setItalic(state);
	}

	public void setTextUnderlineState(boolean state) {
		powerpointApp.getActiveWindow().getSelection().getTextRange().getFont().setUnderline(state);
	}

	public void setTextSize(int size) {
		powerpointApp.getActiveWindow().getSelection().getTextRange().getFont().setSize(size);
	}

	public void increaseTextSize() {
		setTextSize(powerpointApp.getActiveWindow().getSelection().getFont().getSize() + 2);
	}

	public void decreaseTextSize() {
		setTextSize(powerpointApp.getActiveWindow().getSelection().getFont().getSize() - 2);
	}

	public void startSlideShow() {
		powerpointApp.getActivePresentation().getSlideShowSettings().Run();
	}

	public void stopSlideShow() {
		powerpointApp.getSlideShowWindows().getItem(1).getView().Exit();
	}

	public void nextSlide() {
		if (powerpointApp.getSlideShowWindows().getCount() > 0) {
			powerpointApp.getSlideShowWindows().getItem(1).getView().Next();
		} else {
			int newIndex = powerpointApp.getActiveWindow().getView().getSlide().getSlideIndex() + 1;
			powerpointApp.getActivePresentation().getSlides(newIndex).Select();
		}
	}

	public void previousSlide() {
		if (powerpointApp.getSlideShowWindows().getCount() > 0) {
			powerpointApp.getSlideShowWindows().getItem(1).getView().Previous();
		} else {
			int newIndex = powerpointApp.getActiveWindow().getView().getSlide().getSlideIndex() - 1;
			powerpointApp.getActivePresentation().getSlides(newIndex).Select();
		}
	}

	public void firstSlide() {
		if (powerpointApp.getSlideShowWindows().getCount() > 0) {
			powerpointApp.getSlideShowWindows().getItem(1).getView().First();
		} else {
			powerpointApp.getActivePresentation().getSlides(1).Select();
		}
	}

	public void lastSlide() {
		if (powerpointApp.getSlideShowWindows().getCount() > 0) {
			powerpointApp.getSlideShowWindows().getItem(1).getView().Last();
		} else {
			powerpointApp.getActivePresentation()
					.getSlides(powerpointApp.getActivePresentation().getSlides().getCount().intValue()).Select();
		}
	}

	public void goToSlide(int index) {
		if (powerpointApp.getSlideShowWindows().getCount() > 0) {
			powerpointApp.getSlideShowWindows().getItem(1).getView().GotoSlide(index);
		} else {
			powerpointApp.getActivePresentation().getSlides(index).Select();
		}
	}

	public void disposeFactory() {
		fact.disposeAll();
	}
}
