package jna.office.powerpoint;

import com.sun.jna.platform.win32.COM.util.annotation.ComInterface;
import com.sun.jna.platform.win32.COM.util.annotation.ComMethod;

@ComInterface(iid = "{91493462-5A91-11CF-8700-00AA0060263B}")
public interface Presentations {
	@ComMethod
	void Add();
}
