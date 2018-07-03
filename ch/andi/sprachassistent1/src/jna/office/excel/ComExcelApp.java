package jna.office.excel;

import com.sun.jna.platform.win32.COM.util.IUnknown;
import com.sun.jna.platform.win32.COM.util.annotation.ComObject;

@ComObject(progId="Excel.Application")
public interface ComExcelApp extends IUnknown{

}
