package jna.office.word;

import com.sun.jna.platform.win32.COM.util.annotation.ComInterface;
import com.sun.jna.platform.win32.COM.util.annotation.ComMethod;
import com.sun.jna.platform.win32.COM.util.annotation.ComProperty;

@ComInterface(iid="{00020424-0000-0000-C000-000000000046}")
public interface Document {
	@ComProperty
	String getPath();
	
	@ComMethod
	void Save();
}
