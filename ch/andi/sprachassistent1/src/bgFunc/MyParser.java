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
		
		System.err.println("WARNING: No program name in input found: "+input);
		return null;
	}
	
	public static String getContainedFileName(String input) {

		String[] fileNames = MyFiles.getAllNames(MyFiles.FILES_PATH);
		
		for (int i = 0; i < fileNames.length; i++) {
			if (input.toLowerCase().contains(fileNames[i].toLowerCase())) {
				return fileNames[i];
			}
		}
		
		System.err.println("WARNING: No file name in input found: "+input);
		return null;
	}
	
	public static String getContainedWebsiteName(String input) {

		String[] websiteNames = MyFiles.getAllNames(MyFiles.WEBSITES_PATH);
		
		for (int i = 0; i < websiteNames.length; i++) {
			if (input.toLowerCase().contains(websiteNames[i].toLowerCase())) {
				return websiteNames[i];
			}
		}
		
		System.err.println("WARNING: No website name in input found: "+input);
		return null;
	}
}
