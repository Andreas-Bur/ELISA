package bgFunc;

public class Words {

	static String[][] letterPhoneCombinations = new String[][] { { "ttsch", "CH" }, { "tsch", "CH" }, { "dsch", "CH" },
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

	public Words() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) {
		System.out.println(wordsToPhones("hallo hallo hallo"));
	}
	
	public static String wordsToPhones(String input) {
		
		String[] words = input.split(" ");
		String output = "";
		
		for(String word : words) {
			output += singleWordToPhones(word)+" ";
		}
		
		return output.trim();
	}

	private static String singleWordToPhones(String word) {

		word = word.toLowerCase();
		String output = "";

		while (!word.equals("") && word != null) {
			for(int i = 0; i < letterPhoneCombinations.length; i++) {
				if(word.startsWith(letterPhoneCombinations[i][0])) {
					output += letterPhoneCombinations[i][1];
					word = word.replaceFirst(letterPhoneCombinations[i][0], "");
					break;
				}
			}
			System.out.println("Word: "+word);
			output += " ";
		}

		return output.trim();
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

}
