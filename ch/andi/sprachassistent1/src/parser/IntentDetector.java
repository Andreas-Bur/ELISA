package parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bgFunc.MyFiles;
import bgFunc.MyPaths;
import bgFunc.Processes;
import feedback.FeedbackController;
import gui.AlertController;
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
		
		IntentDetector.input = input;

		if (tags == null) {
			tags = new ArrayList<>();
		}

		if (tags.contains("hotword")) {
			System.out.println("rec elisa");
			new Thread(new HotwordActivationController()).start();
			return;
		}

		if (!SpeechRecognizerThread.isHotwordActive()) {
			System.err.println("HOTWORD NOT ACTIVE");
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

		BaseParser parser = null;
		try {
			parser = getParser(input, tags);
		} catch (NoActiveOfficeProgramException e1) {
			e1.showErrorAlert(input);
			e1.printStackTrace();
		}
		
		try {
			if (parser == null) {
				AlertController.showErrorDialog("Unbekannter Befehl", "Der Befehl \"" + input + "\" konnte nicht erkannt werden!");
				return;
			}

			System.out.println("Use parser: " + parser.getClass().getName() + " with input: " + input + " and tag: " + tag);
			parser.parse(input, tag);
			new Thread(new FeedbackController(TrayIconController.SUCCESS_ICON, 5000)).start();

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	private static BaseParser getParser(String input, ArrayList<String> tags) throws NoActiveOfficeProgramException{
		
		if (tags.contains("öffne")) {
			if (getTagTypeParser(tags) == null) {
				if (tags.contains("präsentation")) {
					tag = "präsentation";
					return new Parser_powerpoint();
				} else {
					return getActiveOfficeProgramParser();
				}
			}else {
				return getTagTypeParser(tags);
			}
		} else if (tags.contains("schliesse")) {
			if (tags.contains("präsentation")) {
				tag = "präsentation";
				return new Parser_powerpoint();
			} else {
				return new Parser_schliesseP();
			}
		} else if (tags.contains("sperre")) {
			return new Parser_sperre();
		} else if (tags.contains("screenshot")) {
			return new Parser_screenshot();
		} else if (tags.contains("taste")) {
			return new Parser_taste();
		} else if (tags.contains("kopiere")) {
			tag = "kopiere";
			return new Parser_taste();
		} else if (tags.contains("einfügen")) {
			tag = "einfügen";
			return new Parser_taste();
		} else if (tags.contains("ausschneiden")) {
			tag = "ausschneiden";
			return new Parser_taste();
		} else if (tags.contains("auswählen")) {
			tag = "auswählen";
			return new Parser_taste();
		} 

		//Office exclusive commands
		else if (tags.contains("folie")) {
			if(getActiveOfficeProgramParser().getClass().equals(Parser_powerpoint.class)) {
				tag = "folie";
				return new Parser_powerpoint();
			} else {
				
			}
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
			System.err.println("DEBUG: ERROR: Tags "+tags+" are unknown!");
		}
		
		//throw new NoActiveOfficeProgramException("The command \""+command+"\" is not applicable for the application "+MyPaths.getPathOfForegroundApp());
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
			// DEBUG
			System.err.println("ERROR: command conflict beween: " + Arrays.toString(meaning.toArray()));
			System.err.println("Interpreted it as: " + meaning.get(meaning.size() - 1));
		}
		if (meaning.size() == 0) {
			// DEBUG
			System.err.println("WARNING: synonyms for command \"" + input.split(" ")[0] + "\" not found!");
			return input;
		}

		return output;
	}

	private static BaseParser getActiveOfficeProgramParser() {
		String activeProgram = MyPaths.getPathOfForegroundApp();

		if (activeProgram.equalsIgnoreCase(Processes.WORD_PATH)) {
			return new Parser_word();
		} else if (activeProgram.equalsIgnoreCase(Processes.EXCEL_PATH)) {
			return new Parser_excel();
		} else if (activeProgram.equalsIgnoreCase(Processes.WORD_PATH)) {
			return new Parser_powerpoint();
		}
		return null;
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
