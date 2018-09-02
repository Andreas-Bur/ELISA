package jna.office.powerpoint;

import com.sun.jna.platform.win32.COM.util.annotation.ComInterface;
import com.sun.jna.platform.win32.COM.util.annotation.ComMethod;
import com.sun.jna.platform.win32.COM.util.annotation.ComProperty;

@ComInterface(iid = "{91493458-5A91-11CF-8700-00AA0060263B}")
public interface View {
	@ComProperty
	Slide getSlide();

	@ComMethod
	void Next();

	@ComMethod
	void Previous();

	@ComMethod
	void Exit();
	
	@ComMethod
	void GotoSlide(int index);
	
	@ComMethod
	void First();
	
	@ComMethod
	void Last();

}