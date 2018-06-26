package execute;

import java.io.IOException;

import bgFunc.MyPaths;

public class OpenWebsite {

	public static void open(String path) {

		try {
			Process process = new ProcessBuilder("\""+MyPaths.getPathOfDefaultBrowser()+"\" \""+path+"\"").start();
			System.out.println("(OpenWebsite.open) opening website: " + path+" in browser: "+MyPaths.getPathOfDefaultBrowser());
		} catch (IOException e) {
			System.err.println("ERROR: failed to open website located at " + path+" in browser: "+MyPaths.getPathOfDefaultBrowser());
			e.printStackTrace();
		}
	}
}
