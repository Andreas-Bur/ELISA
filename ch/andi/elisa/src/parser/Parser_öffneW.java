package parser;

import bgFunc.MyParser;
import bgFunc.MyPaths;
import execute.OpenWebsite;
import feedback.AlertController;

public class Parser_öffneW implements BaseParser{

	public void parse(String input, String tag) {
		String[] words = input.split(" ");

		String args = input.substring(words[0].length() + 1, input.length());
		String websiteName = MyParser.getContainedWebsiteName(args);

		String url = MyPaths.getURLOfKnownWebsite(websiteName);

		if (url != null) {
			OpenWebsite.open(url);
		} else {
			AlertController.showErrorDialog("Datei Fehler", "Die URL der Webseite \""+websiteName+"\" konnte nicht gefunden werden. Bitte ändern Sie den Eintrag in den Einstellungen. ");
		}
	}
}
