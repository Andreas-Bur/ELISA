package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import bgFunc.Words;

public class Gram2Dic {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		List<String> gramWordlist = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader("sphinx_data_small/etc/my_model.gram"))) {

			String line = br.readLine();

			while (line != null) {
				if (line.contains("=") && !line.startsWith("//")) {
					String content = line.split("=")[1];
					// System.out.println("1. "+content);
					content = content.replaceAll("[;\\*\\[\\]\\(\\)\\|]", " ");
					// System.out.println("2. "+content);
					content = content.replaceAll("\\<(.*?)\\>", " ").replaceAll("\\{(.*?)\\}", " ").replaceAll("/.*/", "");

					content = content.replaceAll("_\\S+ |$", " ").trim().replaceAll(" +", " ");
					// System.out.println("3. "+content);
					if (!content.equals("")) {
						gramWordlist.addAll(Arrays.asList(content.split(" ")));
					}
					// System.out.println(Arrays.toString(content.split(" ")));
				}

				line = br.readLine();
			}
			System.out.println(gramWordlist);
			gramWordlist.sort(String.CASE_INSENSITIVE_ORDER);

			for (int i = 0; i < gramWordlist.size(); i++) {
				gramWordlist.set(i, gramWordlist.get(i));
				// System.out.println(list.get(i));
			}
			// remove duplicates
			Set<String> entries = new LinkedHashSet<>(gramWordlist);
			gramWordlist.clear();
			gramWordlist.addAll(entries);
		}

		List<String> lines = new ArrayList<>();
		lines.addAll(Files.readAllLines(Paths.get("sphinx_data_small/etc/voxforge_edited2.dic"), Charset.forName("UTF-8")));

		int curGramWordIndex = 0;
		for (String line : lines) {
			if (line.startsWith(gramWordlist.get(curGramWordIndex) + " ")) {
				curGramWordIndex++;
				System.out.println(line);
				if (curGramWordIndex == gramWordlist.size()) {
					break;
				}
			}
		}

		if (curGramWordIndex < gramWordlist.size()) {
			System.err.println("ERROR: \"" + gramWordlist.get(curGramWordIndex) + "\" wurde nicht gefunden!");
		}

	}

}
