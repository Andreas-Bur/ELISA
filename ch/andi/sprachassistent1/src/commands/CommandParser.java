package commands;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class CommandParser {

	public CommandParser() {
		
	}
	
	public void parse(String input) {
		System.out.println("parse: "+input);
		input = removePoliteness(input);
		
		if(input.startsWith("könntest du") || input.startsWith("könnten sie")) {
			input = restructureInputAsCommand(input);
		}
		
		String firstWord = input.split(" ")[0];
		Class<?> cls;
		try {
			cls = Class.forName("commands.Parser_"+firstWord);
			Constructor<?> constr = cls.getConstructor(String.class);
			Object instance = constr.newInstance(input);
			cls.getMethod("parse", String.class)
			.invoke(instance, input);
		} catch (ClassNotFoundException e) {
			System.err.println("Command \""+firstWord+"\" couldn't be found!");
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
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
		
		if(input.startsWith("könntest du"))
			input = input.replace("könntest du", lastWord.substring(0, lastWord.length()-1));
		else if(input.startsWith("könnten sie")) 
			input = input.replace("könnten sie", lastWord.substring(0, lastWord.length()-1));
		
		return input;
	}
	
	//debug
	public static void main(String[] args) {
		CommandParser cp = new CommandParser();
		String in = "Könntest du ein Foto von diesem Bildschirm öffnen".toLowerCase();
		cp.parse(in);
		
	}

}
