package parser;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bgFunc.MyFiles;
import bgFunc.MyPaths;
import bgFunc.Processes;
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
		input = replaceCommandSynonyms(input);

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

		String className = "";
		String tag = null;

		if (tags.contains("öffne")) {
			if (getTagType(tags) != null) {
				className = "öffne" + getTagType(tags);
			} else if (tags.contains("präsentation")) {
				className = "powerpoint";
				tag = "präsentation";
			} else {
				className = getActiveOfficeProgramName();
			}
			if (className == null) {
				className = "öffneP";
			}
		} else if (tags.contains("schliesse")) {
			if(tags.contains("präsentation")) {
				className = "powerpoint";
				tag = "präsentation";
			}else {
				className = "schliesseP";
			}
		} else if (tags.contains("sperre")) {
			className = "sperre";
		} else if (tags.contains("screenshot")) {
			className = "screenshot";
		} else if (tags.contains("taste")) {
			className = "taste";
		} else if (tags.contains("kopiere")) {
			className = "taste";
			tag = "kopiere";
		} else if (tags.contains("einfügen")) {
			className = "taste";
			tag = "einfügen";
		} else if (tags.contains("ausschneiden")) {
			className = "taste";
			tag = "ausschneiden";
		} else if (tags.contains("auswählen")) {
			className = "taste";
			tag = "auswählen";
		} else if (tags.contains("folie")) {
			className = "powerpoint";
			tag = "folie";
		} else if (tags.contains("erstelle")) {
			className = getActiveOfficeProgramName();
		} else if (tags.contains("speichere") || tags.contains("speicher")) {
			className = getActiveOfficeProgramName();
		} else if (tags.contains("textProperties")) {
			className = getActiveOfficeProgramName();
			tag = "textProperties";
		} else if (tags.contains("fontSize")) {
			className = getActiveOfficeProgramName();
			tag = "fontSize";
		} else if (tags.contains("fontSize2")) {
			className = getActiveOfficeProgramName();
			tag = "fontSize2";
		} else if (tags.size() > 0) {
			className = tags.get(tags.size() - 1);
		}

		// System.out.println("input: " + input);

		String firstWord = input.split(" ")[0];
		Class<?> cls;
		try {
			if (tags.size() > 0 && className != null) {
				cls = Class.forName("parser.Parser_" + className);
			} else {
				System.err.println("WARNING: (IntentDetector) tag is null -> used firstWord: " + firstWord);
				cls = Class.forName("parser.Parser_" + firstWord);
			}
			Constructor<?> constr = cls.getConstructor();
			Object instance = constr.newInstance();
			if (tag != null) {
				System.out.println("Use parser: " + cls.getName() + " with input: " + input + " and tag: " + tag);
				cls.getMethod("parse", String.class, String.class).invoke(instance, input, tag);
			} else {
				System.out.println("Use parser: " + cls.getName() + " with input: " + input);
				cls.getMethod("parse", String.class).invoke(instance, input);
			}
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
		input = input.replace("könntest du ", "").replace("könnten sie ", "");
		String[] parts = input.split(" ");
		String lastWord = parts[parts.length - 1];

		String outputString = lastWord.substring(0, lastWord.length() - 1).trim();
		parts[parts.length - 1] = "";

		for (int i = 0; i < parts.length - 1; i++) {
			outputString += " " + parts[i];
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
					// TODO: zwei Wörter Verben in gram einbauen
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
			System.err.println("WARNING: synonyms for command \"" + input.split(" ")[0] + "\" not found!");
			return input;
		}

		return output;
	}

	private static String getActiveOfficeProgramName() {
		String activeProgram = MyPaths.getPathOfForegroundApp();

		if (activeProgram.equalsIgnoreCase(Processes.WORD_PATH)) {
			return "word";
		} else if (activeProgram.equalsIgnoreCase(Processes.EXCEL_PATH)) {
			return "excel";
		} else if (activeProgram.equalsIgnoreCase(Processes.WORD_PATH)) {
			return "powerpoint";
		}
		return null;
	}

	private static String getTagType(ArrayList<String> tags) {
		if (tags.contains("program")) {
			return "P";
		} else if (tags.contains("file")) {
			return "F";
		} else if (tags.contains("website")) {
			return "W";
		}
		return null;
	}
}
