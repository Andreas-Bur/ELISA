package jna.office.office;

import com.sun.jna.platform.win32.COM.util.annotation.ComInterface;
import com.sun.jna.platform.win32.COM.util.annotation.ComProperty;

import jna.office.powerpoint.View;

@ComInterface(iid = "{91493455-5A91-11CF-8700-00AA0060263B}")
public interface DocumentWindow {
	@ComProperty
	View getView();

	@ComProperty
	Selection getSelection();
}
