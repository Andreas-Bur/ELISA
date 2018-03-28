package bgFunc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Psapi;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinNT.HANDLE;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;

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

		HANDLE process = Kernel32.INSTANCE.OpenProcess(0x0400 | 0x0010, false, pid.getValue());
		psapi.GetModuleFileNameExA(process, null, name, 1024);
		String nameString = Native.toString(name);
		return nameString;
	}

	public static String getPathOfKnownApp(String programName) {

		try {
			BufferedReader br = new BufferedReader(new FileReader("data/programsPath.txt"));
			String line = br.readLine();

			while (line != null) {
				if (line.split("\\|")[0].equals(programName)) {
					return line.split("\\|")[1];
				}
				line = br.readLine();
			}
			System.err.println("Couldn't find path to " + programName);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getPathOfKnownFile(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("data/filesPath.txt"));
			String line = br.readLine();

			while (line != null) {
				if (line.split("\\|")[0].equals(filename)) {
					return line.split("\\|")[1];
				}
				line = br.readLine();
			}
			System.err.println("Couldn't find path to " + filename);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}