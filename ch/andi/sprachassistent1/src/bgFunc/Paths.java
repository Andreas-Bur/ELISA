package bgFunc;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Psapi;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.ptr.IntByReference;

public class Paths {

	public Paths() {
		System.out.println(getPathOfForegroundApp());
	}

	public static void main(String[] args) {
		new Paths();
	}

	public static String getPathOfForegroundApp() {
		Psapi psapi = Psapi.INSTANCE;

		HWND focusedWindow = User32.INSTANCE.GetForegroundWindow();
		byte[] name = new byte[1024];

		IntByReference pid = new IntByReference();
		User32.INSTANCE.GetWindowThreadProcessId(focusedWindow, pid);

		HANDLE process = Kernel32.INSTANCE.OpenProcess(Kernel32.PROCESS_QUERY_INFORMATION | Kernel32.PROCESS_VM_READ, false, pid.getValue());
		
		psapi.GetModuleFileNameExA(process, null, name, 1024);
		String path = Native.toString(name);
		return path;
	}

	public static String getPathOfKnownApp(String programName) {
		
		String[] names = Files.getAllNames(Files.PROGRAMS_PATH);
		String[] paths = Files.getAllPaths(Files.PROGRAMS_PATH);
		
		for(int i = 0; i < names.length; i++) {
			if(names[i].equals(programName)) {
				return paths[i];
			}
		}
		return null;
	}
}