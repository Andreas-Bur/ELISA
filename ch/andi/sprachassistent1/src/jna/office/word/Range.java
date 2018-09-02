package jna.office.word;

import com.sun.jna.platform.win32.COM.util.annotation.ComInterface;
import com.sun.jna.platform.win32.COM.util.annotation.ComProperty;

@ComInterface(iid="{0002095E-0000-0000-C000-000000000046}")
public interface Range {
	@ComProperty
	void setStyle(int style);
}
