package jna.office.powerpoint;

import com.sun.jna.platform.win32.Variant.VARIANT;
import com.sun.jna.platform.win32.COM.util.IConnectionPoint;
import com.sun.jna.platform.win32.COM.util.IUnknown;
import com.sun.jna.platform.win32.COM.util.annotation.ComInterface;
import com.sun.jna.platform.win32.COM.util.annotation.ComProperty;

import jna.office.office.Dialogs;
import jna.office.office.DocumentWindow;
import jna.office.office.FileDialog;

@ComInterface(iid = "{91493442-5A91-11CF-8700-00AA0060263B}")
public interface ApplicationP extends IUnknown, IConnectionPoint {

	/*
	 * @ComProperty String getVersion();
	 * 
	 * @ComProperty boolean getVisible();
	 * 
	 * @ComProperty String getCaption();
	 */

	@ComProperty
	void setVisible(boolean value);

	/*
	 * @ComMethod void Quit();
	 */

	/**
	 * @param type
	 *            (1: open; 2: saveAs)
	 * @return
	 */
	@ComProperty
	FileDialog getFileDialog(VARIANT type);

	@ComProperty
	Presentations getPresentations();

	@ComProperty
	Presentation getActivePresentation();

	@ComProperty
	DocumentWindow getActiveWindow();

	@ComProperty
	SlideShowWindows getSlideShowWindows();

	@ComProperty
	Dialogs getDialogs();
}
