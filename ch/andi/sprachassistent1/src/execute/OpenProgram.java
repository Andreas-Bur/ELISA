package execute;

import java.io.IOException;

public class OpenProgram {
	
	public static void open(String path) {
		try {
			
			if(path.toLowerCase().endsWith("windows\\system32\\cmd.exe")) {
				Process process = new ProcessBuilder(path,"/c","start").start();
			}
			else {
				Process process = new ProcessBuilder(path).start();
			}
			System.out.println("started process: "+path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		//open("C:\\Windows\\system32\\cmd.exe");
	}

}
