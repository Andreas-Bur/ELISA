package execute;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.BOOL;

public class ComputerControl {

	public static void lockComputer() {
		BOOL resultValue = User32.INSTANCE.LockWorkStation();
		if(resultValue == null) {
			System.err.println("ERROR: (ComputerControl.lockComputer) error value: "+Kernel32.INSTANCE.GetLastError());
		}else {
			System.out.println("locked with value: "+resultValue);
		}
	}
}
