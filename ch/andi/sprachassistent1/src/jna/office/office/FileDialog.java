package jna.office.office;

import com.sun.jna.platform.win32.COM.util.annotation.ComInterface;
import com.sun.jna.platform.win32.COM.util.annotation.ComMethod;

@ComInterface(iid = "{000C0362-0000-0000-C000-000000000046}")
public interface FileDialog {

	@ComMethod
	void Show();

	@ComMethod
	void Execute();

}
