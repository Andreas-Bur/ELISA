package test;

import com.sun.jna.Pointer;
import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;

public class EnumWindowsTest {
    // Equivalent JNA mappings
    public interface User32 extends StdCallLibrary {
        User32 INSTANCE = (User32) Native.loadLibrary("user32", User32.class);

        interface WNDENUMPROC extends StdCallCallback {
            boolean callback(Pointer hWnd, Pointer arg);
        }

        boolean EnumWindows(WNDENUMPROC lpEnumFunc, Pointer arg);

        int GetWindowTextA(Pointer hWnd, byte[] lpString, int nMaxCount);
    }

    public static void main(String[] args) {
        final User32 user32 = User32.INSTANCE;

        user32.EnumWindows(new User32.WNDENUMPROC() {

            int count;

            public boolean callback(Pointer hWnd, Pointer userData) {
                byte[] windowText = new byte[512];
                user32.GetWindowTextA(hWnd, windowText, 512);
                String wText = Native.toString(windowText);
                wText = (wText.isEmpty()) ? "" : "; text: " + wText;
                System.out.println("Found window " + hWnd + ", total " + ++count + wText);
                return true;
            }
        }, null);
    }
}