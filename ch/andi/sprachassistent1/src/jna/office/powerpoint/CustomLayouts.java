package jna.office.powerpoint;

import com.sun.jna.platform.win32.Variant.VARIANT;
import com.sun.jna.platform.win32.COM.util.annotation.ComInterface;
import com.sun.jna.platform.win32.COM.util.annotation.ComProperty;

@ComInterface(iid="{914934F2-5A91-11CF-8700-00AA0060263B}")
public interface CustomLayouts {
	@ComProperty
	CustomLayout getItem(VARIANT index);
}
