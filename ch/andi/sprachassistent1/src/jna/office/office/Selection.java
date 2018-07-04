package jna.office.office;

import com.sun.jna.platform.win32.COM.util.annotation.ComInterface;
import com.sun.jna.platform.win32.COM.util.annotation.ComProperty;

import jna.office.powerpoint.TextRange;

@ComInterface(iid = "{00020975-0000-0000-C000-000000000046}")
public interface Selection {

	@ComProperty
	Font getFont();
	
	@ComProperty
	Range getRange();
	
	@ComProperty
	TextRange getTextRange();
	
}
