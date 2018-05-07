package bgFunc;

public class Words {

	static String[][] germanLetterPhoneCombinations = new String[][] { { "ttsch", "CH" }, { "tsch", "CH" }, { "dsch", "CH" },
			{ "sch", "SH" }, { "tst", "CH T" }, { "dst", "CH T" }, { "tsp", "CH P" }, { "dsp", "CH P" }, { "qu", "K V" },
			{ "au", "AW" }, { "ei", "AY" }, { "ie", "IIH" }, { "dg", "JH" }, { "ng", "NG" }, { "nk", "NG K" }, { "öh", "OHH" },
			{ "eu", "OI" }, { "äu", "OI" }, { "pf", "PF" }, { "tz", "TS" }, { "ts", "TS" }, { "ch", "CC" }, { "ck", "K" },
			{ "ah", "AAH" }, { "aa", "AAH" }, { "eh", "EHH" }, { "ee", "EHH" }, { "ih", "IIH" }, { "oh", "OOH" }, { "oo", "OOH" },
			{ "uh", "UU" }, { "uu", "UU" }, { "sp", "SH P" }, { "cc", "K" }, { "ll", "L" }, { "mm", "M" }, { "nn", "N" },
			{ "ss", "S" }, { "a", "A" }, { "b", "B" }, { "c", "TS EEH" }, { "d", "D" }, { "e", "EH" }, { "f", "F" }, { "g", "G" },
			{ "h", "HH" }, { "i", "IH" }, { "j", "Y" }, { "k", "K" }, { "l", "L" }, { "m", "M" }, { "n", "N" }, { "o", "OO" },
			{ "p", "P" }, { "q", "K" }, { "r", "RR" }, { "s", "S" }, { "t", "T" }, { "u", "UH" }, { "v", "V" }, { "w", "V" },
			{ "x", "K S" }, { "y", "UE" }, { "z", "TS" }, { "ä", "EH" }, { "ö", "OE" }, { "ü", "UE" }

	};
	
	static String[][] englishLetterPhoneCombinations = new String[][] {
		
	};

	public Words() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) {
		//System.out.println(germanWordsToPhones("hallo hallo hallo"));
		System.out.println(countSyllables("friday"));
	}
	
	public static String germanWordsToPhonemes(String input) {
		
		String[] words = input.split(" ");
		String output = "";
		
		for(String word : words) {
			output += singleGermanWordToPhonemes(word)+" ";
		}
		
		return output.trim();
	}
	
	private static String singleGermanWordToPhonemes(String word) {

		word = word.toLowerCase();
		String output = "";

		while (!word.equals("") && word != null) {
			for(int i = 0; i < germanLetterPhoneCombinations.length; i++) {
				if(word.startsWith(germanLetterPhoneCombinations[i][0])) {
					output += germanLetterPhoneCombinations[i][1];
					word = word.replaceFirst(germanLetterPhoneCombinations[i][0], "");
					break;
				}
			}
			System.out.println("Word: "+word);
			output += " ";
		}

		return output.trim();
	}
	
	public static String englishWordsToPhonemes(String input) {
		
		String[] words = input.split(" ");
		String output = "";
		
		for(String word : words) {
			output += singleEnglishWordToPhonemes(word)+" ";
		}
		
		return output.trim();

	}
	
	private static String singleEnglishWordToPhonemes(String word) { 
		word = word.toLowerCase();
		
		String tempWord = word;
		String output = "";
		
		while (!tempWord.equals("") && tempWord != null) {
			
			if(tempWord.startsWith("y")) {
				if(countSyllables(word)>=2) {
					output += "IH";
					tempWord = tempWord.substring(0, tempWord.length()-1);
				}else {
					output += "AY";
					tempWord = tempWord.substring(0, tempWord.length()-1);
				}
			}
			
			if(tempWord.startsWith("c")) {
				if((""+tempWord.charAt(1)).matches("[aou]")) {
					output += "K";
					tempWord = tempWord.substring(0, tempWord.length()-1);
				}
			}
			output += " ";
		}
		return output;
	}

	public static String decode(String input) {
		input = input.replace("%ue%", "ü");
		input = input.replace("%oe%", "ö");
		input = input.replace("%ae%", "ä");
		return input;
	}

	public static String encode(String input) {
		input = input.replace("ü", "%ue%");
		input = input.replace("ö", "%oe%");
		input = input.replace("ä", "%ae%");
		return input;
	}
	
	private static int countSyllables(String word) {
		
		int count = 0;
		
		word = word.replaceFirst("e*$", "");
		
		for(int i = word.length()-1; i >= 0; i--) {
			if(isVowel(word.charAt(i)) && i >= 1 && isVowel(word.charAt(i-1))){
				
				count++;
				word = word.replaceFirst("..$","");
				System.out.println("two: "+word);
				i--;
			}
			else if(isVowel(word.charAt(i))){
				
				count++;
				word = word.replaceFirst(".$","");
				System.out.println("one: "+word);
				//i--;
			}
			else {
				
				word = word.replaceFirst(".$","");
				System.out.println("none: "+word);
			}
		}
		
		if(count == 0) {
			count = 1;
		}
		
		return count;
	}
	
	private static boolean isVowel(char c) {
		if((""+c).matches("[aeiouy]")) {
			return true;
		}
		return false;
	}

}
