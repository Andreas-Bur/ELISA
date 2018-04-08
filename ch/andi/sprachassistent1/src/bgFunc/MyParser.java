package bgFunc;

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

		String[] programNames = Files.getAllNames(Files.PROGRAMS_PATH);
		for (int i = 0; i < programNames.length; i++) {
			if (input.contains(programNames[i])) {
				return programNames[i];
			}
		}
		return null;
	}
}
