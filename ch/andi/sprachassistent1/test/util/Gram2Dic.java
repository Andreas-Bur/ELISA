package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Gram2Dic {

	public Gram2Dic() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		List<String> gramWordlist = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader("sphinx_data_small/etc/my_model.gram"))) {

			String line = br.readLine();

			while (line != null) {
				if (line.contains("=") && !line.startsWith("//")) {
					line = decode(line);
					String content = line.split("=")[1].replaceAll("\\(", " ").replaceAll("\\)", " ").replaceAll("\\|", " ");
					//System.out.println("1. "+content);
					content = content.replaceAll(";", " ").replaceAll("\\[", " ").replaceAll("\\]", " ");
					//System.out.println("2. "+content);
					content = content.replaceAll("\\<(.*?)\\>", " ").trim().replaceAll(" +", " ");
					//System.out.println("3. "+content);
					gramWordlist.addAll(Arrays.asList(content.split(" ")));
					//System.out.println(Arrays.toString(content.split(" ")));
				}

				line = br.readLine();
			}

			gramWordlist.sort(String.CASE_INSENSITIVE_ORDER);
			

			for (int i = 0; i < gramWordlist.size(); i++) {
				gramWordlist.set(i, encode(gramWordlist.get(i)));
				//System.out.println(list.get(i));
			}
			//remove duplicates
			Set<String> entries = new LinkedHashSet<>(gramWordlist);
			gramWordlist.clear();
			gramWordlist.addAll(entries);
		}
		
		try(BufferedReader br = new BufferedReader(new FileReader("sphinx_data_small/etc/voxforge_edited2.dic"))) {
		    String line = br.readLine();
		    int curGramWordIndex = 0;
		    while (line != null) {
		    	if(line.startsWith(gramWordlist.get(curGramWordIndex)+" ")) {
		    		curGramWordIndex++;
		    		System.out.println(line);
		    		if(curGramWordIndex == gramWordlist.size()) {
		    			break;
		    		}
		    	}
		        line = br.readLine();
		    }

		    if(curGramWordIndex < gramWordlist.size()) {
		    	System.err.println("ERROR: \""+gramWordlist.get(curGramWordIndex)+"\" wurde nicht gefunden!");
		    }
		}
	}

	static String decode(String input) {
		input = input.replace("%ue%", "ü");
		input = input.replace("%oe%", "ö");
		input = input.replace("%ae%", "ä");
		return input;
	}
	
	static String encode(String input) {
		input = input.replace("ü", "%ue%");
		input = input.replace("ö", "%oe%");
		input = input.replace("ä", "%ae%");
		return input;
	}
}
