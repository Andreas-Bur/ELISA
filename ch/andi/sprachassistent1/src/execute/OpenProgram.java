package execute;

import java.io.IOException;

public class OpenProgram {

	public OpenProgram() {
	}
	
	public void open(String path) {
		try {
			Process process = new ProcessBuilder(path).start();
			System.out.println("started process");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
