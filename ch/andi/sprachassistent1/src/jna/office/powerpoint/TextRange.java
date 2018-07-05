package jna.office.powerpoint;

import com.sun.jna.platform.win32.COM.util.annotation.ComInterface;
import com.sun.jna.platform.win32.COM.util.annotation.ComProperty;

import jna.office.office.Font;

@ComInterface(iid = "{0002124A-0000-0000-C000-000000000046}")
public interface TextRange {
	@ComProperty
	Font getFont();
}
