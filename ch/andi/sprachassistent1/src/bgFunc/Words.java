package bgFunc;

public class Words {

	public Words() {
		// TODO Auto-generated constructor stub
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
