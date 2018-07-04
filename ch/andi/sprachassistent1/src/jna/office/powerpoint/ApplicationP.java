package jna.office.powerpoint;

import com.sun.jna.platform.win32.Variant.VARIANT;
import com.sun.jna.platform.win32.COM.util.IConnectionPoint;
import com.sun.jna.platform.win32.COM.util.IUnknown;
import com.sun.jna.platform.win32.COM.util.annotation.ComInterface;
import com.sun.jna.platform.win32.COM.util.annotation.ComMethod;
import com.sun.jna.platform.win32.COM.util.annotation.ComProperty;

import jna.office.excel.Workbook;
import jna.office.excel.Workbooks;
import jna.office.excel.Worksheet;
import jna.office.office.DocumentWindow;
import jna.office.office.FileDialog;
import jna.office.office.Selection;
import jna.office.word.Document;
import jna.office.word.Documents;

@ComInterface(iid = "{91493442-5A91-11CF-8700-00AA0060263B}")
public interface ApplicationP extends IUnknown, IConnectionPoint {

	/*@ComProperty
	String getVersion();

	@ComProperty
	boolean getVisible();
	
	@ComProperty
	String getCaption();*/

	@ComProperty
	void setVisible(boolean value);
	
	/*@ComMethod
	void Quit();*/
	
	/**
	 * @param type (1: open; 2: saveAs)
	 * @return 
	 */
	@ComProperty
	FileDialog getFileDialog(VARIANT type);

	@ComProperty
	Selection getSelection();
	
	@ComProperty
	Presentations getPresentations();
	
	@ComProperty
	Presentation getActivePresentation();
	
	@ComProperty
	DocumentWindow getActiveWindow();
	
}
