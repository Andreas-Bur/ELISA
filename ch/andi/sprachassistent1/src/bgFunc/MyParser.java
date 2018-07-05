package bgFunc;

import java.util.Arrays;
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

		for (int i = 0; i < programNames.length; i++) {
			if (input.toLowerCase().contains(programNames[i].toLowerCase())) {
				return programNames[i];
			}
		}

		for (int i = 0; i < autoProgramNames.length; i++) {
			if (input.toLowerCase().contains(autoProgramNames[i].toLowerCase())) {
				return autoProgramNames[i];
			}
		}

		System.err.println("WARNING: No program name in input found: " + input);
		return null;
	}

	public static String getContainedFileName(String input) {

		String[] fileNames = MyFiles.getAllNames(MyFiles.FILES_PATH);

		for (int i = 0; i < fileNames.length; i++) {
			if (input.toLowerCase().contains(fileNames[i].toLowerCase())) {
				return fileNames[i];
			}
		}

		System.err.println("WARNING: No file name in input found: " + input);
		return null;
	}

	public static String getContainedWebsiteName(String input) {

		String[] websiteNames = MyFiles.getAllNames(MyFiles.WEBSITES_PATH);

		for (int i = 0; i < websiteNames.length; i++) {
			if (input.toLowerCase().contains(websiteNames[i].toLowerCase())) {
				return websiteNames[i];
			}
		}

		System.err.println("WARNING: No website name in input found: " + input);
		return null;
	}

	public static int getNumber(String input) {
		int out = 0;
		String[] numbersTo19 = { "null", "eins", "zwei", "drei", "vier", "fünf", "sechs", "sieben", "acht", "neun", "zehn", "elf",
				"zwölf", "dreizehn", "vierzehn", "fünfzehn", "sechzehn", "siebzehn", "achtzehn", "neunzehn" };
		String[] digits = { "ein", "zwei", "drei", "vier", "fünf", "sechs", "sieben", "acht", "neun" };
		String[] tens = { "zwanzig", "dreissig", "vierzig", "fünfzig", "sechzig", "siebzig", "achtzig", "neunzig" };

		String[] hundredParts = input.split("hundert ");
		String[] tenParts;
		out: if (hundredParts.length > 1) {
			if (hundredParts[1].startsWith("und ")) {
				hundredParts[1] = hundredParts[1].substring(4);
			}
			tenParts = hundredParts[1].split(" und ");
			for (int i = 0; i < digits.length; i++) {
				if (hundredParts[0].trim().equals(digits[i])) {
					out += (i + 1) * 100;
					break out;
				}
			}
			out += 100;
		} else {
			tenParts = hundredParts[0].split(" und ");
		}
		System.out.println("tenParts: " + Arrays.toString(tenParts));

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

	public static void main(String[] args) {
		System.out.println(MyParser.getNumber("vierzehn"));
	}
}
