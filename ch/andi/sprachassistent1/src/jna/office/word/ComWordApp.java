package jna.office.word;

import com.sun.jna.platform.win32.COM.util.IUnknown;
import com.sun.jna.platform.win32.COM.util.annotation.ComObject;

@ComObject(progId="Word.Application")
public interface ComWordApp extends IUnknown{

}
