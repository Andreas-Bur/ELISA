package jna.office.powerpoint;

import com.sun.jna.platform.win32.COM.util.annotation.ComInterface;
import com.sun.jna.platform.win32.COM.util.annotation.ComProperty;

@ComInterface(iid = "{9149346C-5A91-11CF-8700-00AA0060263B}")
public interface Master {
	@ComProperty
	CustomLayouts getCustomLayouts();
}
