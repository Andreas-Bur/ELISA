package jna.office.word;

import com.sun.jna.platform.win32.COM.util.annotation.ComInterface;
import com.sun.jna.platform.win32.COM.util.annotation.ComProperty;

@ComInterface(iid = "{0002095A-0000-0000-C000-000000000046}")
public interface Sections {
	
	@ComProperty
	Section getItem(int index);
}
