package util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SortDic {

	public SortDic() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		List<String> list = new ArrayList<>();
		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("sphinx_data/etc/voxforge_edited.dic"), StandardCharsets.UTF_8))) {
		    String line = br.readLine();
		    
		    while (line != null) {
		    	
		    	list.add(line);
		    	
		        line = br.readLine();
		    }
		    list.sort(String.CASE_INSENSITIVE_ORDER);
		    for (int i = 0; i < list.size(); i++) {
				list.set(i, encode(list.get(i)));
				System.out.println(list.get(i));
			}
		}
		
		Path file = Paths.get("sphinx_data_small/etc/voxforge_edited2.dic");
		Files.write(file, list, Charset.forName("UTF-8"));
	}
	static String encode(String input) {
		input = input.replace("ü", "%ue%");
		input = input.replace("ö", "%oe%");
		input = input.replace("ä", "%ae%");
		return input;
	}
}
