package jna.office.powerpoint;

import com.sun.jna.platform.win32.COM.util.IUnknown;
import com.sun.jna.platform.win32.COM.util.annotation.ComObject;

@ComObject(progId = "PowerPoint.Application")
public interface ComPowerpointApp extends IUnknown {

}
