package parser;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bgFunc.MyFiles;
import bgFunc.MyParser;
import bgFunc.MyPaths;
import bgFunc.Processes;
import jna.My_WNDENUMPROC;
import main.Main;
import speech.HotwordActivationController;
import speech.SpeechRecognizerThread;

public class IntentDetector {

	public static void parse(String input, ArrayList<String> tags) {
		System.out.println("(IntentDetector.parse) input: " + input + " | tag: " + tags);

		if (tags.contains("hotword")) {
			System.out.println("rec elisa");
			new Thread(new HotwordActivationController()).start();
			return;
		}

		if (!SpeechRecognizerThread.isHotwordActive() && !input.startsWith("!")) {
			System.err.println("HOTWORD NOT ACTIVE");
			return;
		} else if (input.startsWith("!")) {
			input = input.substring(1);
		}

		if ("<unk>".equals(input)) {
			return;
		}

		if (tags.contains("stop")) {
			// TODO nachfragen
			System.out.println("recognized stop");
			Main.quit = true;
			return;
		}

		input = removePoliteness(input);

		for (int i = 0; i < tags.size(); i++) {
			if (tags.get(i).endsWith("_G")) {
				tags.set(i, tags.get(i).substring(0, tags.get(i).length() - 2));
				if (tags.get(i).equals("")) {
					tags.remove(i);
				}
				input = restructureInputAsCommand(input);
				System.out.println("restructured: " + input);
			}
		}

		input = replaceCommandSynonyms(input);

		String className = "";
		if (tags.contains("öffne")) {
			if (tags.contains("program") || tags.contains("file") || tags.contains("website")) {
				className += "öffne";
			} else {
				className = getActiveOfficeProgramName();
			}
		} else if (tags.contains("schliesse")) {
			className += "schliesse";
		} else if (tags.contains("sperre")) {
			className += "sperre";
		} else if (tags.contains("screenshot")) {
			className += "screenshot";
		} else if (tags.contains("erstelle")) {
			className = getActiveOfficeProgramName();
		} else if (tags.contains("speichere")) {
			className = getActiveOfficeProgramName();
		} else {
			className += tags.get(tags.size() - 1);
		}
		if (tags.contains("program")) {
			className += "P";
		} else if (tags.contains("file")) {
			className += "F";
		} else if (tags.contains("website")) {
			className += "W";
		}

		System.out.println("input: " + input);

		String firstWord = input.split(" ")[0];
		Class<?> cls;
		try {
			if (tags.size() > 0) {
				cls = Class.forName("parser.Parser_" + className);
			} else {
				System.err.println("WARNING: (IntentDetector) tag is null -> used firstWord: " + firstWord);
				cls = Class.forName("parser.Parser_" + firstWord);
			}

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
		ArrayList<String> output = new ArrayList<>();

		output.add(lastWord.substring(0, lastWord.length() - 1));
		parts[parts.length - 1] = "";
		for (int i = 0; i < parts.length; i++) {
			if (parts[i].startsWith("_")) {
				output.add(parts[i]);
			}
		}
		for (int i = 0; i < parts.length; i++) {
			if (parts[i].startsWith("neu") && parts[i + 1].equals("fenster")) {
				output.add("in einem neuen fenster");
			}
		}

		String outputString = "";
		for (String part : output) {
			outputString += " " + part;
		}

		return outputString.trim();
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

					// suche nach gespaltenen Befehlen die schlecht umgeformt
					// wurden
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

	private static String getActiveOfficeProgramName() {
		String activeProgram = MyPaths.getPathOfForegroundApp();

		switch (activeProgram) {
		case Processes.WORD_PATH:
			return "word";
		case Processes.EXCEL_PATH:
			return "excel";
		case Processes.POWERPOINT_PATH:
			return "powerpoint";
		default:
			return null;
		}
	}
}
