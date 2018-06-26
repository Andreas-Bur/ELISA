package execute;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class OpenFile {

	public static void open(String path) {
		
		Desktop dt = Desktop.getDesktop();
	    try {
			dt.open(new File(path));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		/*try {

			Process process = new ProcessBuilder("\""+path+"\"").start();

			System.out.println("(OpenFile.open) opening file: " + path);
		} catch (IOException e) {
			System.err.println("ERROR: failed to open file located at " + path);
			e.printStackTrace();
		}*/
	}
}
