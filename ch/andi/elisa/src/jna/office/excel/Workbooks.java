package jna.office.excel;

import com.sun.jna.platform.win32.COM.util.annotation.ComInterface;
import com.sun.jna.platform.win32.COM.util.annotation.ComMethod;
import com.sun.jna.platform.win32.COM.util.annotation.ComProperty;

@ComInterface(iid = "{000208DB-0000-0000-C000-000000000046}")
public interface Workbooks {

	@ComProperty
	long getCount();

	@ComMethod
	Workbook Item(long index);

	@ComMethod
	Workbook Open(Object FileName);

	@ComMethod
	Workbook Add();
}
