package parser;

import bgFunc.MyParser;
import bgFunc.MyPaths;
import execute.OpenWebsite;

public class Parser_öffneW implements BaseParser{

	public void parse(String input, String tag) {
		System.out.println("(Parser_öffneW.parse) input: " + input);
		String[] words = input.split(" ");

		String args = input.substring(words[0].length() + 1, input.length());
		String websiteName = MyParser.getContainedWebsiteName(args);

		String url = MyPaths.getURLOfKnownWebsite(websiteName);

		if (url != null) {
			OpenWebsite.open(url);
		} else {
			System.err.println("WARNING: URL of website " + websiteName + " couldn't be found!");
		}
	}
}
