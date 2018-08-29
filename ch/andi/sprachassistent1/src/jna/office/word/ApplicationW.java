package jna.office.word;

import com.sun.jna.platform.win32.Variant.VARIANT;
import com.sun.jna.platform.win32.COM.util.IConnectionPoint;
import com.sun.jna.platform.win32.COM.util.IUnknown;
import com.sun.jna.platform.win32.COM.util.annotation.ComInterface;
import com.sun.jna.platform.win32.COM.util.annotation.ComMethod;
import com.sun.jna.platform.win32.COM.util.annotation.ComProperty;

import jna.office.office.Dialogs;
import jna.office.office.FileDialog;
import jna.office.office.Selection;

@ComInterface(iid = "{00020970-0000-0000-C000-000000000046}")
public interface ApplicationW extends IUnknown, IConnectionPoint {

	@ComProperty
	String getVersion();

	@ComProperty
	boolean getVisible();

	@ComProperty
	String getCaption();

	@ComProperty
	void setVisible(boolean value);

	@ComMethod
	void Quit();

	/**
	 * @param type
	 *            (1: open; 2: saveAs)
	 * @return
	 */
	@ComProperty
	FileDialog getFileDialog(VARIANT type);

	@ComProperty
	Selection getSelection();

	@ComProperty
	Documents getDocuments();

	@ComProperty
	Document getActiveDocument();
	
	@ComProperty
	Dialogs getDialogs();

}
