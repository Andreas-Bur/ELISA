package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Tlhelp32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.win32.W32APIOptions;

public class Test1 {

	public Test1() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		getProcessesJNA();
	}
	
	public static void wmic() {
		String line;
		try {
		        Process proc = Runtime.getRuntime().exec("wmic.exe");
		        BufferedReader input = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		        OutputStreamWriter oStream = new OutputStreamWriter(proc.getOutputStream());
		        oStream .write("process where name='explorer.exe'");
		        oStream .flush();
		        oStream .close();
		        while ((line = input.readLine()) != null) {
		            System.out.println(line);
		        }
		        input.close();
		    } catch (IOException ioe) {
		        ioe.printStackTrace();
		    }
	}
	
	private static void getProcesses(){
		try {
			String line;
			Process p = Runtime.getRuntime().exec(System.getenv("windir") + "\\system32\\" + "tasklist.exe");
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = input.readLine()) != null) {
				System.out.println(line); // <-- Parse data here.
			}
			input.close();
		} catch (Exception err) {
			err.printStackTrace();
		}
	}
	
	private static void getProcessesJNA(){
		Kernel32 kernel32 = (Kernel32) Native.loadLibrary(Kernel32.class, W32APIOptions.UNICODE_OPTIONS);
        Tlhelp32.PROCESSENTRY32.ByReference processEntry = new Tlhelp32.PROCESSENTRY32.ByReference();          

        WinNT.HANDLE snapshot = kernel32.CreateToolhelp32Snapshot(Tlhelp32.TH32CS_SNAPPROCESS, new WinDef.DWORD(0));
        try  {
            while (kernel32.Process32Next(snapshot, processEntry)) {             
                System.out.println(processEntry.th32ProcessID + "\t" + Native.toString(processEntry.szExeFile));
            }
        }
        finally {
            kernel32.CloseHandle(snapshot);
        }
	}

}
