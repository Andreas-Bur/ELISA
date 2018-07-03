package jna.office.excel;

import com.sun.jna.platform.win32.COM.util.annotation.ComInterface;
import com.sun.jna.platform.win32.COM.util.annotation.ComProperty;

@ComInterface(iid = "{000208D8-0000-0000-C000-000000000046}")
public interface Worksheet {
	@ComProperty
	Workbook getParent();

	@ComProperty
	String getName();

	@ComProperty
	Range getRange(String cell1);

	@ComProperty
	Range getRange(String cell1, String cell2);

	@ComProperty
	Application getApplication();

	@ComProperty
	Range getCells();

	/*@ComProperty
	Shapes getShapes();*/

	@ComProperty
	Range getRows(Object identifier);

	@ComProperty
	Range getColumns(Object identifier);
}
