package parser;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bgFunc.MyFiles;
import main.Main;
import speech.HotwordActivationController;
import speech.MyLiveRecognizer;
import speech.SpeechRecognizerThread;

public class CommandParser {

	public CommandParser() {

	}

	public void parse(String input) {
		System.out.println("(CommandParser.parse) input: " + input);
		
		if(input.matches("(hey |hallo )?elisa")) {
			System.out.println("rec elisa");
			new Thread(new HotwordActivationController()).start();
			return;
		}
		
		if(!SpeechRecognizerThread.isHotwordActive()) {
			return;
		}

		if ("<unk>".equals(input)) {
			// System.out.println("recognized unknown input");
			return;
		}
		
		if ("stoppe".equals(input)) {
			// TODO nachfragen
			System.out.println("recognized stoppe");
			Main.quit = true;
			return;
		}

		input = removePoliteness(input);

		if (input.startsWith("könntest du") || input.startsWith("könnten sie")) {
			input = restructureInputAsCommand(input);
		}

		input = replaceCommandSynonyms(input);

		System.out.println("input: " + input);

		String firstWord = input.split(" ")[0];
		Class<?> cls;
		try {
			cls = Class.forName("commands.Parser_" + firstWord);
			Constructor<?> constr = cls.getConstructor();
			Object instance = constr.newInstance();
			cls.getMethod("parse", String.class).invoke(instance, input);
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR: Command \"" + firstWord + "\" couldn't be found!");
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

	}

	private static String removePoliteness(String input) {
		input = input.replaceAll("entschuldigung", "");
		input = input.replaceAll("bitte", "");
		input = input.replaceAll("danke", "");
		input = input.replaceAll("vielen dank", "");

		input = removeMultipleSpaces(input);
		return input;
	}

	private static String removeMultipleSpaces(String input) {
		input = input.trim().replaceAll(" +", " ");
		return input;
	}

	private static String restructureInputAsCommand(String input) {
		String[] parts = input.split(" ");
		String lastWord = parts[parts.length - 1];
		input = input.substring(0, input.length() - lastWord.length() - 1);

		if (input.startsWith("könntest du"))
			input = input.replace("könntest du", lastWord.substring(0, lastWord.length() - 1));
		else if (input.startsWith("könnten sie"))
			input = input.replace("könnten sie", lastWord.substring(0, lastWord.length() - 1));

		return input;
	}

	private static String replaceCommandSynonyms(String input) {

		String[] lines = MyFiles.getFileContent("data/commandSynonyms.txt");
		List<String> meaning = new ArrayList<>();
		String firstWord = input.split(" ")[0];
		String output = "";

		for (int n = 0; n < lines.length; n++) {
			String[] parts = lines[n].split(":");
			if (input.startsWith(parts[0])) {
				meaning.add(parts[0]);
				output = input;
				continue;
			}
			String[] synonyms = parts[1].split(",");
			for (int m = 0; m < synonyms.length; m++) {
				if (synonyms[m].contains("|")) {
					String[] wordParts = synonyms[m].split("\\|");

					if (input.startsWith(wordParts[0] + " ") && input.endsWith(" " + wordParts[1])) {
						output = parts[0] + input.substring(wordParts[0].length(), input.length() - wordParts[1].length()).trim();
					}

					// suche nach gespaltenen Befehlen die schlecht umgeformt wurden
					else if (firstWord.startsWith(wordParts[1]) && firstWord.endsWith(wordParts[0])
							&& firstWord.length() == synonyms[m].length() - 1) {

						meaning.add(parts[0]);
						output = input.replace(firstWord, parts[0]);
					}
				} else {
					if (input.startsWith(synonyms[m] + " ")) {
						meaning.add(parts[0]);
						output = input.replace(synonyms[m], parts[0]);
						break;
					}
				}
			}
		}

		if (meaning.size() >= 2) {
			System.err.println("ERROR: command conflict beween: " + Arrays.toString(meaning.toArray()));
			System.err.println("Interpreted it as: " + meaning.get(meaning.size() - 1));
		}
		if (meaning.size() == 0) {
			System.err.println("ERROR: command \"" + input.split(" ")[0] + "\" not found!");
			return input;
		}

		return output;
	}

	// debug
	public static void main(String[] args) {
		//CommandParser cp = new CommandParser();
		// String in = "schliesse firefox".toLowerCase();
		// cp.parse(in);
		//cp.parse("zeige mir word");
	}

}
