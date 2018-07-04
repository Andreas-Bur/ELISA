package jna.office;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Ole32;
import com.sun.jna.platform.win32.Variant.VARIANT;
import com.sun.jna.platform.win32.WinDef.LCID;
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

	public static void main(String[] args) {
		Ole32.INSTANCE.CoInitializeEx(Pointer.NULL, Ole32.COINIT_MULTITHREADED);
		try {
			PowerpointControl powerpointControl = new PowerpointControl();
			powerpointControl.newSlide();
		}finally {
			fact.disableTimeout();
			Ole32.INSTANCE.CoUninitialize();
		}
	}

	public void newDocument() {
		powerpointApp.getPresentations().Add();
	}
	
	public void newSlide() {
		CustomLayout layout = powerpointApp.getActivePresentation().getSlideMaster().getCustomLayouts().getItem(new VARIANT(2));
		int activeSlideIndex = powerpointApp.getActiveWindow().getView().getSlide().getSlideIndex();
		powerpointApp.getActivePresentation().getSlides().getAddSlide(new VARIANT(activeSlideIndex+1), layout);
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
		if (powerpointApp.getActivePresentation().getPath().contains(":")) {
			powerpointApp.getActivePresentation().Save();
		} else {
			saveAs();
		}
	}

	public void setTextBoldState(boolean state) {
		powerpointApp.getSelection().getFont().setBold(state);
	}

	public void setTextItalicState(boolean state) {
		powerpointApp.getSelection().getFont().setItalic(state);
	}

	public void setTextUnderlineState(boolean state) {
		powerpointApp.getSelection().getFont().setUnderline(state);
	}

	public void setTextStrikethroughState(boolean state) {
		powerpointApp.getSelection().getFont().setStrikethrough(state);
	}

	public void setTextSize(int size) {
		powerpointApp.getSelection().getFont().setSize(size);
	}

	public void disposeFactory() {
		fact.disposeAll();
	}
}
