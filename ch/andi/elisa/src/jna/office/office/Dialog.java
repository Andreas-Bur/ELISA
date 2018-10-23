package jna.office.office;

import com.sun.jna.platform.win32.COM.util.annotation.ComInterface;
import com.sun.jna.platform.win32.COM.util.annotation.ComMethod;

@ComInterface(iid = "{0002087A-0000-0000-C000-000000000046}")
public interface Dialog {
	
	@ComMethod
	void Show();
}
