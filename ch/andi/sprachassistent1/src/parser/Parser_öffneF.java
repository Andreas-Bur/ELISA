package parser;

import bgFunc.MyParser;
import bgFunc.MyPaths;
import execute.OpenFile;

public class Parser_öffneF {

	public static void parse(String input) {
		System.out.println("(Parser_öffneF.parse) input: " + input);
		String[] words = input.split(" ");

		String args = input.substring(words[0].length() + 1, input.length());
		String fileName = MyParser.getContainedFileName(args);

		String path = MyPaths.getPathOfKnownFile(fileName);

		if (path != null) {
			OpenFile.open(path);
		}else {
			System.err.println("WARNING: Path of file " + fileName + " couldn't be found!");
		}
	}
}
