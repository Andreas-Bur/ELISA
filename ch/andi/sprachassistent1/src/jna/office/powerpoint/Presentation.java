package jna.office.powerpoint;

import com.sun.jna.platform.win32.COM.util.annotation.ComInterface;
import com.sun.jna.platform.win32.COM.util.annotation.ComMethod;
import com.sun.jna.platform.win32.COM.util.annotation.ComProperty;

import jna.office.office.DocumentWindow;

@ComInterface(iid = "{9149349D-5A91-11CF-8700-00AA0060263B}")
public interface Presentation {
	@ComProperty
	String getPath();

	@ComMethod
	void Save();
	
	@ComProperty
	Slides getSlides();
	
	@ComProperty
	Master getSlideMaster();

}
