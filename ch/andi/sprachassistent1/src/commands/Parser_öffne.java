package commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bgFunc.Paths;
import execute.OpenProgram;

public class Parser_öffne {

	public Parser_öffne(String input) {
		/*try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		parse(input);*/
	}

	public void parse(String input) {
		System.out.println("öffne parser: " + input);
		String[] words = input.split(" ");
		String programName = words[words.length - 1];
		String args = input.substring(words[0].length()+1, input.length());

		if (means(args, "neu(\\w){0,2} fenster")) {
			OpenProgram.open(Paths.getPathOfForegroundApp());

		} else {
			String path = Paths.getPathOfKnownApp(programName);
			OpenProgram.open(path);
		}
	}

	private boolean means(String input, String meaning) {

		System.out.println(":"+input);

		Pattern pattern = Pattern.compile(meaning);
		Matcher matcher = pattern.matcher(input);

		if (matcher.find()) {
			return true;
		}

		return false;
	}

	public static void main(String[] args) {
		//new Parser_öffne("öffne neues fenster");
	}

}
