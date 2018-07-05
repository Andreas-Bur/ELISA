package jna.office.powerpoint;

import com.sun.jna.platform.win32.Variant.VARIANT;
import com.sun.jna.platform.win32.COM.util.annotation.ComInterface;
import com.sun.jna.platform.win32.COM.util.annotation.ComProperty;

@ComInterface(iid = "{91493469-5A91-11CF-8700-00AA0060263B}")
public interface Slides {
	@ComProperty
	Slide getAddSlide(VARIANT index, CustomLayout layout);

	@ComProperty
	Slide getItem(int index);

	@ComProperty
	SlideRange getRange(int index);
}
