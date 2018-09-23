package jna.office.office;

import com.sun.jna.platform.win32.COM.util.annotation.ComInterface;
import com.sun.jna.platform.win32.COM.util.annotation.ComProperty;

import jna.office.powerpoint.ColorFormat;
import jna.office.word.Shading;

@ComInterface(iid = "{0002084D-0000-0000-C000-000000000046}")
public interface Font {

	@ComProperty
	void setBold(boolean state);

	@ComProperty
	void setItalic(boolean state);

	@ComProperty
	void setUnderline(boolean state);

	@ComProperty
	void setStrikeThrough(boolean state);

	@ComProperty
	void setSize(int size);

	@ComProperty
	int getSize();

	@ComProperty
	void setColorIndex(int color);
	
	@ComProperty
	Shading getShading();
	
	@ComProperty
	ColorFormat getColor();

}
