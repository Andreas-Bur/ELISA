package jna.office.powerpoint;

import com.sun.jna.platform.win32.COM.util.annotation.ComInterface;
import com.sun.jna.platform.win32.COM.util.annotation.ComMethod;

@ComInterface(iid = "{9149345A-5A91-11CF-8700-00AA0060263B}")
public interface SlideShowSettings {

	@ComMethod
	void Run();
}
