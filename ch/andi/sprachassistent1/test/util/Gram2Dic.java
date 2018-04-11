package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Gram2Dic {

	public Gram2Dic() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		List<String> list = new ArrayList<>();

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
					list.addAll(Arrays.asList(content.split(" ")));
					//System.out.println(Arrays.toString(content.split(" ")));
				}

				line = br.readLine();
			}

			list.sort(String.CASE_INSENSITIVE_ORDER);

			for (int i = 0; i < list.size(); i++) {
				list.set(i, encode(list.get(i)));
				//System.out.println(list.get(i));
			}
		}
		
		try(BufferedReader br = new BufferedReader(new FileReader("sphinx_data_small/etc/voxforge_edited2.dic"))) {
		    String line = br.readLine();
		    int curI = 0;
		    while (line != null) {
		    	if(line.startsWith(list.get(curI))) {
		    		curI++;
		    		System.out.println(line);
		    		if(curI == list.size()) {
		    			break;
		    		}
		    	}
		        line = br.readLine();
		    }
		    if(curI < list.size()) {
		    	System.err.println("ERROR: "+list.get(curI)+" wurde nicht gefunden!");
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
