package bgFunc;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import gui.AlertController;

public class Startup {

	private static final String elisaDir = System.getProperty("user.home") + "\\.ELISA";
	public static final String sphinxDir = elisaDir + "\\sphinx";
	public static final String paramDir = sphinxDir + "\\model_parameters";
	public static final String dataDir = elisaDir + "\\data";

	public Startup() {
		System.out.println("firstSetup");

		try {
			createFoldersAndFiles();
		} catch (IOException e) {
			AlertController.showIOExceptionDialog("Erstellen");
			e.printStackTrace();
		}
	}

	public static void createFoldersAndFiles() throws IOException {
		String officePath = "";
		if (!Files.isDirectory(Paths.get(sphinxDir)) || new File(sphinxDir).listFiles().length == 0) {
			Files.createDirectories(Paths.get(sphinxDir));
			Files.copy(Paths.get("res\\setup\\sphinx\\EntryNames.gram"), Paths.get(sphinxDir + "\\EntryNames.gram"));
			Files.copy(Paths.get("res\\setup\\sphinx\\feat.params"), Paths.get(sphinxDir + "\\feat.params"));
			Files.copy(Paths.get("res\\setup\\sphinx\\my_model.gram"), Paths.get(sphinxDir + "\\my_model.gram"));
			Files.copy(Paths.get("res\\setup\\sphinx\\voxforge_small.dic"), Paths.get(sphinxDir + "\\voxforge_small.dic"));
			Files.copy(Paths.get("res\\setup\\sphinx\\voxforge.filler"), Paths.get(sphinxDir + "\\voxforge.filler"));
			Files.copy(Paths.get("res\\setup\\sphinx\\voxforge.phone"), Paths.get(sphinxDir + "\\voxforge.phone"));
		}

		if (!Files.isDirectory(Paths.get(paramDir)) || new File(paramDir).listFiles().length == 0) {
			Files.createDirectories(Paths.get(paramDir));
			Files.copy(Paths.get("res\\setup\\model_parameters\\feat.params"), Paths.get(paramDir + "\\feat.params"));
			Files.copy(Paths.get("res\\setup\\model_parameters\\feature_transform"), Paths.get(paramDir + "\\feature_transform"));
			Files.copy(Paths.get("res\\setup\\model_parameters\\mdef"), Paths.get(paramDir + "\\mdef"));
			Files.copy(Paths.get("res\\setup\\model_parameters\\means"), Paths.get(paramDir + "\\means"));
			Files.copy(Paths.get("res\\setup\\model_parameters\\mixture_weights"), Paths.get(paramDir + "\\mixture_weights"));
			Files.copy(Paths.get("res\\setup\\model_parameters\\noisedict"), Paths.get(paramDir + "\\noisedict"));
			Files.copy(Paths.get("res\\setup\\model_parameters\\transition_matrices"),
					Paths.get(paramDir + "\\transition_matrices"));
			Files.copy(Paths.get("res\\setup\\model_parameters\\variances"), Paths.get(paramDir + "\\variances"));
		}

		if (!Files.isDirectory(Paths.get(dataDir)) || new File(dataDir).listFiles().length == 0) {
			Files.createDirectories(Paths.get(dataDir));
			Files.copy(Paths.get("res\\setup\\data\\commandSynonyms.txt"), Paths.get(dataDir + "\\commandSynonyms.txt"));
			Files.createFile(Paths.get(dataDir + "\\autoProgramsPath.txt"));
			Files.createFile(Paths.get(dataDir + "\\filesPath.txt"));
			Files.createFile(Paths.get(dataDir + "\\programsPath.txt"));
			Files.createFile(Paths.get(dataDir + "\\websitesPath.txt"));
			Files.createFile(Paths.get(dataDir + "\\removedFilesPath.txt"));
			Files.createFile(Paths.get(dataDir + "\\removedProgramsPath.txt"));
			Files.createFile(Paths.get(dataDir + "\\removedWebsitesPath.txt"));

			officePath = getOfficeDir();
			MyFiles.addNewLineToFile(MyFiles.PROGRAMS_PATH, "_powerpoint|" + officePath + "\\POWERPNT.exe|EN|Y");
			MyFiles.addNewLineToFile(MyFiles.PROGRAMS_PATH, "_word|" + officePath + "\\WINWORD.exe|EN|Y");
			MyFiles.addNewLineToFile(MyFiles.PROGRAMS_PATH, "_excel|" + officePath + "\\EXCEL.exe|EN|Y");
		}

		if (Files.notExists(Paths.get(dataDir + "\\settings.txt"))) {
			Files.createFile(Paths.get(dataDir + "\\settings.txt"));
			if(officePath.equals("")) {
				officePath = getOfficeDir();
			}
			MyFiles.addNewLineToFile(dataDir + "\\settings.txt", "hotkeyIndex|0");
			MyFiles.addNewLineToFile(dataDir + "\\settings.txt", "officePath|"+officePath);
		}
	}

	public static void deleteFolderAndFiles() throws IOException {
		Files.walkFileTree(Paths.get(elisaDir), new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				Files.delete(file);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				Files.delete(dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}

	private static String getOfficeDir() {

		String[] paths = { "C:\\Program Files\\Microsoft Office\\", "C:\\Program Files (x86)\\Microsoft Office\\",
				"C:\\Program Files\\Microsoft Office 16\\", "C:\\Program Files (x86)\\Microsoft Office 16\\",
				"C:\\Program Files\\Microsoft Office 15\\", "C:\\Program Files (x86)\\Microsoft Office 15\\", "C:\\" };

		for (int i = 0; i < paths.length; i++) {
			try {
				ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "cd " + paths[i] + " && dir WINWORD.EXE /S /B");
				builder.redirectErrorStream(true);
				Process p = builder.start();
				BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String output = r.readLine();
				if (output.startsWith("C:")) {
					System.out.println("i: " + i);
					return output.substring(0, output.lastIndexOf('\\'));
				}
			} catch (IOException e) {
				AlertController.showIOExceptionDialog("Lesen");
				e.printStackTrace();
			}
		}

		AlertController.showErrorDialog("Fehler",
				"Die Office Programme konnten nicht gefunden werden. \r\nBitte stellen Sie sicher, dass sie installiert sind.");
		System.exit(0);
		return null;
	}

}
