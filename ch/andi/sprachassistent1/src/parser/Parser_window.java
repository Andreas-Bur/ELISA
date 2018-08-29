package parser;

import bgFunc.MyParser;
import bgFunc.MyPaths;
import execute.WindowController;

public class Parser_window implements BaseParser {

	@Override
	public void parse(String input, String tag) {

		String[] words = input.split(" ");
		String args = input.substring(words[0].length() + 1, input.length());
		String programName = MyParser.getContainedProgramName(args);
		String path = MyPaths.getPathOfKnownApp(programName);

		if (tag.contains("max")) {
			if (MyParser.means(args, ".*(fenster|programm)")) {
				WindowController.maximizeAndShow(MyPaths.getPathOfForegroundApp());
			} else if(programName != null){
				WindowController.maximizeAndShow(path);
			}

		} else if (tag.contains("min")) {
			if (MyParser.means(args, ".*(fenster|programm)")) {
				WindowController.minimize(MyPaths.getPathOfForegroundApp());
			} else if(programName != null){
				WindowController.minimize(path);
			}
		}
	}
}
