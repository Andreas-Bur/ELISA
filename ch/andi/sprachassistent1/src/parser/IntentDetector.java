package parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bgFunc.MyFiles;
import bgFunc.MyPaths;
import bgFunc.Processes;
import bgFunc.Startup;
import feedback.AlertController;
import feedback.FeedbackController;
import gui.MainApp;
import gui.TrayIconController;
import main.Main;
import speech.HotwordActivationController;
import speech.SpeechRecognizerThread;
import util.NoActiveOfficeProgramException;

public class IntentDetector {

	static String input = "";
	static String tag = "";

	public static void parse(String input, ArrayList<String> tags) {

		System.out.println("(IntentDetector.parse) input: " + input + " | tag: " + tags);
		tag = "";
		IntentDetector.input = input;

		if (tags == null) {
			tags = new ArrayList<>();
		}

		if (input.equals("<unk>")) {
			return;
		}

		if (tags.contains("hotword")) {
			System.out.println("rec elisa");
			new Thread(new HotwordActivationController()).start();
			return;
		}

		if (!SpeechRecognizerThread.isHotwordActive()) {
			System.err.println("DEBUG: HOTWORD NOT ACTIVE");
			return;
		}
		new Thread(new HotwordActivationController(0, false)).start();

		if (tags.contains("stop")) {
			// TODO nachfragen
			System.out.println("DEBUG: recognized stop");
			Main.quitProgram();
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

		BaseParser parser = null;
		try {
			parser = getParser(input, tags);
		} catch (NoActiveOfficeProgramException e1) {
			e1.showErrorAlert(input);
			e1.printStackTrace();
			return;
		}

		try {
			if (parser == null) {
				AlertController.showErrorDialog("Unbekannter Befehl",
						"Der Befehl \"" + input.replaceAll("_", " ") + "\" konnte nicht erkannt werden!");
				return;
			}

			System.out.println("Use parser: " + parser.getClass().getName() + " with input: " + input + " and tag: " + tag);
			parser.parse(input, tag);
			MainApp.addExecutedCommand(IntentDetector.input.replaceAll("_", " ").replaceAll(" +", " ").trim());
			new Thread(new FeedbackController(TrayIconController.SUCCESS_ICON, 5000)).start();

		} catch (SecurityException e) {
			AlertController.showErrorDialog("Fehler",
					"Der Befehl \"" + input.replaceAll("_", " ") + "\" konnte nicht ausgeführt werden!");
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			//Ignore since some some word commands throw an error even when executed successfully
			//AlertController.showErrorDialog("Fehler", "Der Befehl \"" + input.replaceAll("_", " ") + "\" konnte nicht ausgeführt werden!");
			//e.printStackTrace();
		}
	}

	private static BaseParser getParser(String input, ArrayList<String> tags) throws NoActiveOfficeProgramException {

		if (tags.contains("öffne")) {
			if (getTagTypeParser(tags) == null) {
				if (tags.contains("präsentation")) {
					tag = "präsentation";
					return new Parser_powerpoint();
				} else {
					return getActiveOfficeProgramParser();
				}
			} else {
				return getTagTypeParser(tags);
			}
		} else if (tags.contains("schliesse")) {
			if (tags.contains(tag = "präsentation")) {
				return new Parser_powerpoint();
			} else {
				return new Parser_schliesseP();
			}
		} else if (tags.contains("sperre")) {
			return new Parser_sperre();
		} else if (tags.contains("max")) {
			tag = "max";
			return new Parser_window();
		} else if (tags.contains("min")) {
			tag = "min";
			return new Parser_window();
		} else if (tags.contains("screenshot")) {
			return new Parser_screenshot();
		} else if (tags.contains("taste")) {
			return new Parser_taste();
		} else if (tags.contains("kopiere")) {
			tag = "kopiere";
			return new Parser_taste();
		} else if (tags.contains("einfügen") && !tags.contains("officeObj")) {
			tag = "einfügen";
			return new Parser_taste();
		} else if (tags.contains("ausschneiden")) {
			tag = "ausschneiden";
			return new Parser_taste();
		} else if (tags.contains("auswählen")) {
			tag = "auswählen";
			return new Parser_taste();
		}

		// Office exclusive commands
		else if (tags.contains("folie")) {
			if (getActiveOfficeProgramParser().getClass().equals(Parser_powerpoint.class)) {
				tag = "folie";
				return new Parser_powerpoint();
			} else {

			}
		} else if (tags.contains("officeObj")) {
			tag = "officeObj";
			return getActiveOfficeProgramParser();
		} else if (tags.contains("drucke")) {
			tag = "drucke";
			return getActiveOfficeProgramParser();
		} else if (tags.contains("erstelle")) {
			return getActiveOfficeProgramParser();
		} else if (tags.contains("speichere") || tags.contains("speicher")) {
			return getActiveOfficeProgramParser();
		} else if (tags.contains("textProperties")) {
			tag = "textProperties";
			return getActiveOfficeProgramParser();
		} else if (tags.contains("fontSize")) {
			tag = "fontSize";
			return getActiveOfficeProgramParser();
		} else if (tags.contains("fontSize2")) {
			tag = "fontSize2";
			return getActiveOfficeProgramParser();
		} else if (tags.size() > 0) {
			System.err.println("DEBUG: ERROR: Tags " + tags + " are unknown!");
		}

		return null;
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

		String[] lines = MyFiles.getFileContent(Startup.dataDir + "\\commandSynonyms.txt");
		List<String> meaning = new ArrayList<>();
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
				if (input.startsWith(synonyms[m] + " ")) {
					meaning.add(parts[0]);
					output = input.replace(synonyms[m], parts[0]);
					break;
				}
			}
		}

		if (meaning.size() >= 2) {
			// DEBUG
			System.err.println("DEBUG: command conflict beween: " + Arrays.toString(meaning.toArray()));
			System.err.println("DEBUG: Interpreted it as: " + meaning.get(meaning.size() - 1));
		}
		if (meaning.size() == 0) {
			// DEBUG
			System.err.println("DEBUG: synonyms for command \"" + input.split(" ")[0] + "\" not found!");
			return input;
		}

		return output;
	}

	private static BaseParser getActiveOfficeProgramParser() throws NoActiveOfficeProgramException {
		String activeProgram = MyPaths.getPathOfForegroundApp();

		if (activeProgram.equalsIgnoreCase(Processes.getWordPath())) {
			return new Parser_word();
		} else if (activeProgram.equalsIgnoreCase(Processes.getExcelPath())) {
			return new Parser_excel();
		} else if (activeProgram.equalsIgnoreCase(Processes.getPowerPointPath())) {
			return new Parser_powerpoint();
		}
		throw new NoActiveOfficeProgramException(input, activeProgram);
	}

	private static BaseParser getTagTypeParser(ArrayList<String> tags) {
		if (tags.contains("program")) {
			return new Parser_öffneP();
		} else if (tags.contains("file")) {
			return new Parser_öffneF();
		} else if (tags.contains("website")) {
			return new Parser_öffneW();
		}
		return null;
	}
}
