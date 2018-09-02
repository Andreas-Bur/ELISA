package jna.office.excel;

import com.sun.jna.platform.win32.COM.util.annotation.ComInterface;
import com.sun.jna.platform.win32.COM.util.annotation.ComProperty;

@ComInterface(iid = "{00020846-0000-0000-C000-000000000046}")
public interface Range {

	/*
	 * @ComProperty Application getApplication();
	 * 
	 * @ComProperty String getText();
	 * 
	 * @ComMethod void Select();
	 * 
	 * @ComProperty void setValue(String value);
	 * 
	 * @ComMethod void Activate();
	 * 
	 * @ComProperty Range getItem(Object rowIndex, Object columnIndex);
	 * 
	 * @ComProperty void setValue(Object data);
	 * 
	 * @ComProperty Object getValue();
	 * 
	 * @ComProperty void setFormula(String data);
	 * 
	 * @ComProperty String getFormula();
	 * 
	 * @ComProperty void setNumberFormat(String data);
	 * 
	 * @ComProperty String getNumberFormat();
	 */

	/*
	 * @ComProperty Range getEntireColumn();
	 * 
	 * @ComMethod void AutoFit();
	 * 
	 * @ComProperty public Range getResize(Object rowSize, Object columnSize);
	 * 
	 * @ComProperty void setOrientation(int degree);
	 * 
	 * @ComProperty int getOrientation();
	 * 
	 * @ComProperty void setWrapText(boolean wrap);
	 * 
	 * @ComProperty boolean getWrapText();
	 */

	@ComProperty
	Interior getInterior();


	/*
	 * @ComProperty Borders getBorders();
	 */

	/*
	 * @ComProperty int getTop();
	 * 
	 * @ComProperty void setTop(int value);
	 * 
	 * @ComProperty int getLeft();
	 * 
	 * @ComProperty void setLeft(int value);
	 * 
	 * @ComProperty String getName();
	 * 
	 * @ComProperty void setName(String name);
	 * 
	 * @ComProperty void setAddress(String name);
	 */
}
