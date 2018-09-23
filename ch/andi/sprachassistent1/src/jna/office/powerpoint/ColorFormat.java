package jna.office.powerpoint;

import com.sun.jna.platform.win32.COM.util.annotation.ComInterface;
import com.sun.jna.platform.win32.COM.util.annotation.ComProperty;

@ComInterface(iid="91493452-5A91-11CF-8700-00AA0060263B")
public interface ColorFormat {

	@ComProperty
	void setRGB(int r, int g, int b);
	
	@ComProperty
	void setRGB(int color);
	
}
