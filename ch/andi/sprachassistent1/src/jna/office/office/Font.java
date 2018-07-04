package jna.office.office;

import com.sun.jna.platform.win32.COM.util.annotation.ComInterface;
import com.sun.jna.platform.win32.COM.util.annotation.ComProperty;

@ComInterface(iid="{00020420-0000-0000-C000-000000000046}")
public interface Font {

	@ComProperty
	void setBold(boolean state);
	
	@ComProperty
	void setItalic(boolean state);
	
	@ComProperty
	void setUnderline(boolean state);
	
	@ComProperty
	void setStrikethrough(boolean state);
	
	@ComProperty
	void setSize(int size);
	
}
