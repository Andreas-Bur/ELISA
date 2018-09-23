package bgFunc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyParser {

	public static boolean means(String input, String meaning) {

		System.out.println("(MyParser.means) input: " + input);

		Pattern pattern = Pattern.compile(meaning, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(input);

		if (matcher.find()) {
			return true;
		}
		return false;
	}

	public static String getContainedProgramName(String input) {

		String[] programNames = MyFiles.getAllNames(MyFiles.PROGRAMS_PATH);
		String[] autoProgramNames = MyFiles.getAllNames(MyFiles.AUTO_PROGRAMS_PATH);

		String output = "";

		for (String name : programNames) {
			if (input.toLowerCase().contains(name.toLowerCase())) {
				output = output.length() > name.length() ? output : name;
			}
		}

		for (String name : autoProgramNames) {
			if (input.toLowerCase().contains(name.toLowerCase())) {
				output = output.length() > name.length() ? output : name;
			}
		}

		if (output.equals("")) {
			// DEBUG
			System.err.println("DEBUG: No program name in input found: " + input);
			return null;
		}
		return output;
	}

	public static String getContainedFileName(String input) {

		List<String> fileNames = Arrays.asList(MyFiles.getAllNames(MyFiles.FILES_PATH));
		Collections.sort(fileNames);
		Collections.reverse(fileNames);

		for (String name : fileNames) {
			if (input.toLowerCase().contains(name.toLowerCase())) {
				return name;
			}
		}
		// DEBUG
		System.err.println("DEBUG: No file name in input found: " + input);
		return null;
	}

	public static String getContainedWebsiteName(String input) {

		List<String> websiteNames = Arrays.asList(MyFiles.getAllNames(MyFiles.WEBSITES_PATH));
		Collections.sort(websiteNames);
		Collections.reverse(websiteNames);

		for (String name : websiteNames) {
			if (input.toLowerCase().contains(name.toLowerCase())) {
				return name;
			}
		}
		// DEBUG
		System.err.println("DEBUG: No website name in input found: " + input);
		return null;
	}

	public static int extractIntFromText(String input) {
		return getNumber(getNumberText(input));
	}

	public static String getNumberText(String input) {
		ArrayList<String> parts = new ArrayList<String>();
		parts.addAll(Arrays.asList(input.split(" ")));
		String number = "";

		while (parts.size() > 0 && !isNumberPart(parts.get(0))) {
			parts.remove(0);
		}
		while (parts.size() > 0 && !isNumberPart(parts.get(parts.size() - 1))) {
			parts.remove(parts.size() - 1);
		}

		for (int i = 0; i < parts.size(); i++) {
			number += parts.get(i) + " ";
		}
		return number.trim();
	}

	private static int getNumber(String input) {
		input = " "+input+" ";

		if (input == null || input.equals("")) {
			return -1;
		}

		int out = 0;
		String[] numbersTo19 = { "null", "eins", "zwei", "drei", "vier", "fünf", "sechs", "sieben", "acht", "neun", "zehn", "elf",
				"zwölf", "dreizehn", "vierzehn", "fünfzehn", "sechzehn", "siebzehn", "achtzehn", "neunzehn" };
		String[] digits = { "ein", "zwei", "drei", "vier", "fünf", "sechs", "sieben", "acht", "neun" };
		String[] tens = { "zwanzig", "dreissig", "vierzig", "fünfzig", "sechzig", "siebzig", "achtzig", "neunzig" };

		String[] hundredParts = input.split("hundert");
		String[] tenParts;
		if (input.contains("hundert")) {
			if(hundredParts.length>1) {
				if (hundredParts[1].trim().startsWith("und ")) {
					hundredParts[1] = hundredParts[1].trim().substring(4);
				}
				tenParts = hundredParts[1].split(" und ");
			}else {
				tenParts = new String[0];
			}
			
			for (int i = 0; i < digits.length; i++) {
				if (hundredParts[0].trim().equals(digits[i])) {
					out += i * 100;
				}
			}
			out += 100;
		} else {
			tenParts = hundredParts[0].split(" und ");
		}

		out: if (tenParts.length == 1) {
			for (int i = 0; i < numbersTo19.length; i++) {
				if (tenParts[0].trim().equals(numbersTo19[i])) {
					out += i;
					break out;
				}
			}
			for (int i = 0; i < tens.length; i++) {
				if (tenParts[0].trim().equals(tens[i])) {
					out += (i + 2) * 10;
					break out;
				}
			}
		} else if (tenParts.length == 2) {
			for (int i = 0; i < digits.length; i++) {
				if (tenParts[0].trim().equals(digits[i])) {
					out += i + 1;
					break;
				}
			}
			for (int i = 0; i < tens.length; i++) {
				if (tenParts[1].trim().equals(tens[i])) {
					out += (i + 2) * 10;
					break;
				}
			}
		}

		return out;
	}

	public static boolean isNumberPart(String input) {
		String[] parts = { "null", "eins", "ein", "zwei", "drei", "vier", "fünf", "sechs", "sieben", "acht", "neun", "zehn",
				"elf", "zwölf", "dreizehn", "vierzehn", "fünfzehn", "sechzehn", "siebzehn", "achtzehn", "neunzehn", "zwanzig",
				"dreissig", "vierzig", "fünfzig", "sechzig", "siebzig", "achtzig", "neunzig", "hundert" };

		for (String part : parts) {
			if (part.equals(input)) {
				return true;
			}
		}
		return false;
	}
}
