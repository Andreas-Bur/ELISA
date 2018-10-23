package jna.office.word;

import com.sun.jna.platform.win32.COM.util.annotation.ComInterface;
import com.sun.jna.platform.win32.COM.util.annotation.ComMethod;

@ComInterface(iid = "{00020424-0000-0000-C000-000000000046}")
public interface Documents {
	@ComMethod
	Document Add();
	
	
}
