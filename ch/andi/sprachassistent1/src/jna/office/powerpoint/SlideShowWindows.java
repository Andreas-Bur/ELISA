package jna.office.powerpoint;

import com.sun.jna.platform.win32.COM.util.annotation.ComInterface;
import com.sun.jna.platform.win32.COM.util.annotation.ComProperty;

@ComInterface(iid = "{91493456-5A91-11CF-8700-00AA0060263B}")
public interface SlideShowWindows {

	@ComProperty
	int getCount();

	@ComProperty
	SlideShowWindow getItem(int index);
}
