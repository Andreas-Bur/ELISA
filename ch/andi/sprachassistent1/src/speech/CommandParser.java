package speech;

public class CommandParser {

	public CommandParser() {
		
	}
	
	public void parse(String input) {
		System.out.println("parse: "+input);
		input = removePoliteness(input);
		if(input.startsWith("könntest du") || input.startsWith("könnten sie")) {
			input = restructureInputAsCommand(input);
		}
		
		
	}
	
	private String removePoliteness(String input) {
		input = input.replaceAll("entschuldigung", ""); 
		input = input.replaceAll("bitte", ""); 
		input = input.replaceAll("danke", ""); 
		input = input.replaceAll("vielen dank", ""); 
		
		input = removeMultipleSpaces(input);
		return input;
	}
	
	private String removeMultipleSpaces(String input) {
		input = input.trim().replaceAll(" +", " ");
		return input;
	}
	
	private String restructureInputAsCommand(String input) {
		String[] parts = input.split(" ");
		String lastWord = parts[parts.length-1];
		input = input.substring(0, input.length()-lastWord.length()-1);
		input = input.replace("könntest du", lastWord.substring(0, lastWord.length()-1));
		return input;
	}
	
	public void öffne() {
		
	}
	
	public void schliesse() {
		
	}
	
	//debug
	public static void main(String[] args) {
		CommandParser cp = new CommandParser();
		String in = "Könntest du ein Foto von diesem Bildschirm machen".toLowerCase();
		in = cp.removePoliteness(in);
		System.out.println(in);
		in = cp.restructureInputAsCommand(in);
		System.out.println(in);
		
	}

}
