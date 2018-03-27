package commands;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import execute.OpenProgram;

public class Parser_öffne {

	public Parser_öffne(String input) {

	}
	
	public void parse(String input) {
		System.out.println("öffne parser: "+input);
		String[] words = input.split(" ");
		String programName = words[words.length-1];
		String path = getPathOfProgram(programName);
		OpenProgram op = new OpenProgram();
		op.open(path);
	}
	
	private String getPathOfProgram(String programName) {

		try {
			BufferedReader br = new BufferedReader(new FileReader("data/programsPath.txt"));
			String line = br.readLine();

			while(line != null) {
				if(line.split("\\|")[0].equals(programName)) {
					return line.split("\\|")[1];
				}
				line = br.readLine();
			}
			System.err.println("Couldn't find path to "+programName);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
