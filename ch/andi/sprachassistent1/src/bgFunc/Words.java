package bgFunc;

public class Words {

	private static String[][] germanLetterPhoneCombinations = new String[][] { { "ttsch", "CH" }, { "tsch", "CH" },
			{ "dsch", "CH" }, { "sch", "SH" }, { "tst", "CH T" }, { "dst", "CH T" }, { "tsp", "CH P" }, { "dsp", "CH P" },
			{ "qu", "K V" }, { "au", "AW" }, { "ei", "AY" }, { "ie", "IIH" }, { "dg", "JH" }, { "ng", "NG" }, { "nk", "NG K" },
			{ "�h", "OHH" }, { "eu", "OI" }, { "�u", "OI" }, { "pf", "PF" }, { "tz", "TS" }, { "ts", "TS" }, { "ch", "CC" },
			{ "ck", "K" }, { "ah", "AAH" }, { "aa", "AAH" }, { "eh", "EHH" }, { "ee", "EHH" }, { "ih", "IIH" }, { "oh", "OOH" },
			{ "oo", "OOH" }, { "uh", "UU" }, { "uu", "UU" }, { "sp", "SH P" }, { "cc", "K" }, { "ll", "L" }, { "mm", "M" },
			{ "nn", "N" }, { "ss", "S" }, { "a", "A" }, { "b", "B" }, { "c", "TS EEH" }, { "d", "D" }, { "e", "EH" },
			{ "f", "F" }, { "g", "G" }, { "h", "HH" }, { "i", "IH" }, { "j", "Y" }, { "k", "K" }, { "l", "L" }, { "m", "M" },
			{ "n", "N" }, { "o", "OO" }, { "p", "P" }, { "q", "K" }, { "r", "RR" }, { "s", "S" }, { "t", "T" }, { "u", "UH" },
			{ "v", "V" }, { "w", "V" }, { "x", "K S" }, { "y", "UE" }, { "z", "TS" }, { "�", "EH" }, { "�", "OE" }, { "�", "UE" }

	};

	private static String[][] englishLetterPhoneCombinations = new String[][] {

			{ "^ee.*$", "IIH", "2" }, { "^ea.*$", "IY", "2" }, { "^ai.*$", "EH IH", "2" }, { "^th$", "TH", "2" },
			{ "^th.*$", "DH", "2" }, { "^ay.*$", "EH IH", "2" }, { "^ire.*$", "AY EX", "3" }, { "^ch[aeiou].*$", "CH", "2" },
			{ "^ch[^aeiou].*$", "K", "2" }, { "^ck.*$", "K", "2" }, { "^sh.*$", "SH", "2" }, { "^ph.*$", "F", "2" },
			{ "^wh.*$", "W", "2" }, { "^ng.*$", "NG", "2" }, { "^qu.*$", "K W", "2" }, { "^igh.*$", "AY", "2" },
			{ "^dge.*$", "JH", "2" }, { "^dg.*$", "JH", "2" }, { "^tio.*$", "SH AX", "3" }, { "^y$", "IY", "1" },
			{ "^e$", "", "1" }, { "^bb.*$", "B", "2" }, { "^cc.*$", "K", "2" }, { "^dd.*$", "D", "2" }, { "^ff.*$", "F", "2" },
			{ "^gg.*$", "G", "2" }, { "^ll.*$", "L", "2" }, { "^mm.*$", "M", "2" }, { "^nn.*$", "NG", "2" },
			{ "^oo.*$", "UU", "2" }, { "^pp.*$", "P", "2" }, { "^rr.*$", "R", "2" }, { "^ss.*$", "S", "2" },
			{ "^tt.*$", "TH", "2" }, { "^zz.*$", "Y", "2" }, { "^gh.*$", "", "2" }, { "^a[^aeiou]*e$", "EE IH", "1" },
			{ "^a.*$", "EH", "1" }, { "^b.*$", "B", "1" }, { "^f.*$", "F", "1" }, { "^h.*$", "HH", "1" },
			{ "^i[^aeiou]*e$", "AA IH", "1" }, { "^i.*$", "IH", "1" }, { "^j.*$", "JH", "1" }, { "^l.*$", "L", "1" },
			{ "^m.*$", "M", "1" }, { "^o[^aeiou]*e$", "OOH", "1" }, { "^o.*$", "OO", "1" }, { "^p.*$", "P", "1" },
			{ "^q.*$", "K", "1" }, { "^r.*$", "R", "1" }, { "^s.*$", "S", "1" }, { "^t.*$", "T", "1" },
			{ "^u[^aeiou]*e$", "UUH", "1" }, { "^u.*$", "UU", "1" }, { "^v.*$", "V", "1" }, { "^w.*$", "W", "1" },
			{ "^x.*$", "K S", "1" }, { "^z.*$", "Z", "1" }, { "^kn.*$", "N", "2" }, { "^k.*$", "K", "1" }, { "^n.*$", "N", "1" },
			{ "^ed$", "D", "2" }, { "^d.*$", "D", "1" }, { "^e[^aeiou]*e$", "IIH", "1" }, { "^e.*$", "EH", "1" },
			{ "^�.*$", "EH", "1" }, { "^�.*$", "OE", "1" }, { "^�.*$", "UE", "1" }

	};

	private static String[][] englishLetterPronounciations = new String[][] {

			{ "a", "EE IH" }, { "b", "B IIH" }, { "c", "S IIH" }, { "d", "D IIH" }, { "e", "IIH" }, { "f", "EH F" },
			{ "g", "CH IIH" }, { "h", "EE IH JH" }, { "i", "AA IH" }, { "j", "JH EH IH" }, { "k", "K EE IH" }, { "l", "EH L" },
			{ "m", "EH M" }, { "n", "EH N" }, { "o", "AO UU" }, { "p", "P IY" }, { "q", "K Y UUH" }, { "r", "AH R" },
			{ "s", "EH S" }, { "t", "T IIH" }, { "u", "Y UH" }, { "v", "W IIH" }, { "w", "D AH B L Y UH" }, { "x", "EH K S" },
			{ "y", "W AY" }, { "z", "Z EH T" }

	};

	public Words() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// System.out.println(germanWordsToPhones("hallo hallo hallo"));
		System.out.println(englishWordsToPhonemes("libreOffice"));
	}
	
	public static String getPhonemes(String language, String input) {
		if(language.equalsIgnoreCase("DE")) {
			return germanWordsToPhonemes(input);
		}
		else if(language.equalsIgnoreCase("EN")) {
			return englishWordsToPhonemes(input);
		}
		else {
			System.err.println("ERROR: (Words.getPhonemes) Die Sprache "+language+" konnte nicht erkannt werden.");
			return null;
		}
	}

	public static String germanWordsToPhonemes(String input) {
		
		input = prepareInput(input);

		String[] words = input.split(" ");
		String output = "";

		for (String word : words) {
			output += singleGermanWordToPhonemes(word) + " ";
		}

		return output.trim();
	}

	private static String singleGermanWordToPhonemes(String word) {

		word = word.toLowerCase();
		String output = "";

		while (!word.equals("") && word != null) {
			for (int i = 0; i < germanLetterPhoneCombinations.length; i++) {
				if (word.startsWith(germanLetterPhoneCombinations[i][0])) {
					output += germanLetterPhoneCombinations[i][1];
					word = word.replaceFirst(germanLetterPhoneCombinations[i][0], "");
					break;
				}
			}
			System.out.println("Word: " + word);
			output += " ";
		}

		return output.trim();
	}

	public static String englishWordsToPhonemes(String input) {
		
		input = prepareInput(input);
		
		System.out.println("englishWordsToPhonemes: "+input);

		String[] words = input.split(" ");
		String output = "";

		for (String word : words) {
			output += singleEnglishWordToPhonemes(word) + " ";
		}

		return output.trim();

	}

	private static String singleEnglishWordToPhonemes(String word) {
		word = word.toLowerCase();

		String tempWord = word;
		String output = "";
		
		for(int i = 0; i < englishLetterPronounciations.length; i++) {
			if(word.matches(englishLetterPronounciations[i][0])) {
				return englishLetterPronounciations[i][1];
			}
		}

		aussen: while (!tempWord.equals("") && tempWord != null) {

			//System.out.println(tempWord);
			output += " ";

			for (int i = 0; i < englishLetterPhoneCombinations.length; i++) {
				if (tempWord.matches(englishLetterPhoneCombinations[i][0])) {
					output += englishLetterPhoneCombinations[i][1];
					tempWord = tempWord.substring(Integer.parseInt(englishLetterPhoneCombinations[i][2]));
					continue aussen;
				}
			}

			if (tempWord.matches("^kn.*$") && word.matches("^kn.*$")) {
				output += "N";
				tempWord = tempWord.substring(2);
			} else if (tempWord.matches("k.*$")) {
				output += "K";
				tempWord = tempWord.substring(1);
			} else if (tempWord.matches("n.*$")) {
				output += "N";
				tempWord = tempWord.substring(1);
			} else if (tempWord.matches("ed$") && ("" + word.charAt(word.length() - 3)).matches("[^dt]")) {
				output += "D";
				tempWord = tempWord.substring(2);
			} else if (tempWord.matches("e[^aeiou]*e$")) {
				output += "IIH";
				tempWord = tempWord.substring(1);
			} else if (tempWord.matches("e.*$")) {
				output += "EH";
				tempWord = tempWord.substring(1);
			} else if (tempWord.startsWith("y")) {
				if (countSyllables(word) >= 2) {
					output += "IH";
					tempWord = tempWord.substring(1);
				} else {
					output += "AY";
					tempWord = tempWord.substring(1);
				}
			} else if (tempWord.startsWith("c")) {

				if (("" + tempWord.charAt(1)).matches("[chk]")) {
					output += "K";
					tempWord = tempWord.substring(2);
				} else if (("" + tempWord.charAt(1)).matches("[aou]")) {
					output += "K";
					tempWord = tempWord.substring(1);
				} else if (("" + tempWord.charAt(1)).matches("[eiy]")) {
					output += "S";
					tempWord = tempWord.substring(1);
				}
			} else if (tempWord.startsWith("g")) {

				if (("" + tempWord.charAt(1)).matches("[eiy]")) {
					output += "CH";
					tempWord = tempWord.substring(1);
				} else /*if (("" + tempWord.charAt(1)).matches("[ua]"))*/ {
					output += "G";
					tempWord = tempWord.substring(1);
				}
			}
		}
		return output.trim();
	}
	
	private static boolean isUpperCase(char c) {
		return (""+c).equals((""+c).toUpperCase());
	}
	
	private static String prepareInput(String input) {
		
		input = input.replaceAll("_", " ").trim();
		
		for(int i = 0; i < input.length(); i++) {
			if(isUpperCase(input.charAt(i)) && i > 0 && input.charAt(i-1)!=' ') {
				input = input.substring(0, i)+" "+input.substring(i);
				i++;
			}
		}
		
		return input;
		
	}

	private static int countSyllables(String word) {

		int count = 0;

		word = word.replaceFirst("e*$", "");

		for (int i = word.length() - 1; i >= 0; i--) {
			if (isVowel(word.charAt(i)) && i >= 1 && isVowel(word.charAt(i - 1))) {
				count++;
				word = word.replaceFirst("..$", "");
				//System.out.println("two: " + word);
				i--;
			} else if (isVowel(word.charAt(i))) {
				count++;
				word = word.replaceFirst(".$", "");
				//System.out.println("one: " + word);
				// i--;
			} else {
				word = word.replaceFirst(".$", "");
				//System.out.println("none: " + word);
			}
		}

		if (count == 0) {
			count = 1;
		}

		return count;
	}

	private static boolean isVowel(char c) {
		if (("" + c).matches("[aeiouy]")) {
			return true;
		}
		return false;
	}

}
