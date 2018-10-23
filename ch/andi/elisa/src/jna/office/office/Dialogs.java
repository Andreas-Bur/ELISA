package jna.office.office;

import com.sun.jna.platform.win32.Variant.VARIANT;
import com.sun.jna.platform.win32.COM.util.annotation.ComInterface;
import com.sun.jna.platform.win32.COM.util.annotation.ComProperty;

@ComInterface(iid = "{00020879-0000-0000-C000-000000000046}")
public interface Dialogs {
	
	@ComProperty
	Dialog getItem(VARIANT index);
}
