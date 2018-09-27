package bgFunc;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Psapi;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.ptr.IntByReference;

public class MyPaths {

	public static String getPathOfForegroundApp() {
		Psapi psapi = Psapi.INSTANCE;

		HWND focusedWindow = User32.INSTANCE.GetForegroundWindow();
		byte[] name = new byte[1024];

		IntByReference pid = new IntByReference();
		User32.INSTANCE.GetWindowThreadProcessId(focusedWindow, pid);

		HANDLE process = Kernel32.INSTANCE.OpenProcess(Kernel32.PROCESS_QUERY_INFORMATION | Kernel32.PROCESS_VM_READ, false,
				pid.getValue());

		psapi.GetModuleFileNameExA(process, null, name, 1024);
		String path = Native.toString(name);

		return path;
	}

	public static String getPathOfKnownApp(String programName) {

		String[] names = MyFiles.getAllNames(MyFiles.PROGRAMS_PATH);
		String[] paths = MyFiles.getAllPaths(MyFiles.PROGRAMS_PATH);
		String[] autoNames = MyFiles.getAllNames(MyFiles.AUTO_PROGRAMS_PATH);
		String[] autoPaths = MyFiles.getAllPaths(MyFiles.AUTO_PROGRAMS_PATH);

		for (int i = 0; i < names.length; i++) {
			if (names[i].equalsIgnoreCase(programName)) {
				return paths[i];
			}
		}

		for (int i = 0; i < autoNames.length; i++) {
			if (autoNames[i].equalsIgnoreCase(programName)) {
				return autoPaths[i];
			}
		}

		return null;
	}

	public static String getPathOfKnownFile(String fileName) {

		String[] names = MyFiles.getAllNames(MyFiles.FILES_PATH);
		String[] paths = MyFiles.getAllPaths(MyFiles.FILES_PATH);

		for (int i = 0; i < names.length; i++) {
			if (names[i].equalsIgnoreCase(fileName)) {
				return paths[i];
			}
		}
		return null;
	}

	public static String getURLOfKnownWebsite(String websiteName) {

		String[] names = MyFiles.getAllNames(MyFiles.WEBSITES_PATH);
		String[] urls = MyFiles.getAllPaths(MyFiles.WEBSITES_PATH);

		for (int i = 0; i < names.length; i++) {
			if (names[i].equalsIgnoreCase(websiteName)) {
				return urls[i];
			}
		}
		return null;
	}
}