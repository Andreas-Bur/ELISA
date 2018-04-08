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
			System.out.println("(OpenProgram.open) started application: "+path);
		} catch (IOException e) {
			System.err.println("ERROR: failed to run program located at "+path);
			e.printStackTrace();
		} 
	}
	
	public static void main(String[] args) {
		open("C:\\Windows\\sytem32\\cmd.exe");
	}

}
