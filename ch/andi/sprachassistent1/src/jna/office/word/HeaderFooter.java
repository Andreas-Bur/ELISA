package jna.office.word;

import com.sun.jna.platform.win32.COM.util.annotation.ComInterface;
import com.sun.jna.platform.win32.COM.util.annotation.ComProperty;

import jna.office.office.Range;

@ComInterface(iid = "{00020985-0000-0000-C000-000000000046}")
public interface HeaderFooter {
	@ComProperty
	Range getRange();
	
	@ComProperty
	PageNumbers getPageNumbers();
	
}
