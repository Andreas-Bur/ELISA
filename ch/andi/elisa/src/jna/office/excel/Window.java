package jna.office.excel;

import com.sun.jna.platform.win32.COM.util.annotation.ComInterface;
import com.sun.jna.platform.win32.COM.util.annotation.ComProperty;

@ComInterface(iid = "{00020893-0000-0000-C000-000000000046}")
public interface Window {
	@ComProperty
	Range getRangeSelection();
}
