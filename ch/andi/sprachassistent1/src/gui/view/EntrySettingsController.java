package gui.view;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import bgFunc.MyFiles;
import feedback.AlertController;
import gui.MainApp;
import gui.model.Entry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import speech.SpeechRecognizerThread;

public class EntrySettingsController {

	@FXML
	private TableView<Entry> entryTable;
	@FXML
	private TableColumn<Entry, CheckBox> aktivColumn;
	@FXML
	private TableColumn<Entry, TextField> spracheColumn;
	@FXML
	private TableColumn<Entry, TextField> nameColumn;
	@FXML
	private TableColumn<Entry, TextField> pfadColumn;
	@FXML
	private Button saveButton;

	private Stage progEinstStage;
	private int typeIndex;
	private String[] typeNames = { "program", "file", "website" };
	ArrayList<String[]> entriesToRemove = new ArrayList<>();

	@FXML
	private void initialize() {
		aktivColumn.setCellValueFactory(cellData -> cellData.getValue().aktivProperty());
		spracheColumn.setCellValueFactory(cellData -> cellData.getValue().spracheProperty());
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		pfadColumn.setCellValueFactory(cellData -> cellData.getValue().pfadProperty());
	}

	private void saveProgramDataFile() {

		ArrayList<String> autoOutput = new ArrayList<String>();
		ArrayList<String> permOutput = new ArrayList<String>();
		ArrayList<String> autoLines = new ArrayList<String>(Arrays.asList(MyFiles.getFileContent(MyFiles.AUTO_PROGRAMS_PATH)));

		removeDeletedEntries();

		for (int i = 0; i < aktivColumn.getTableView().getItems().size(); i++) {
			String aktiv = aktivColumn.getCellData(i).isSelected() ? "Y" : "N";
			String sprache = spracheColumn.getCellData(i).getText();
			String plainName = nameColumn.getCellData(i).getText();
			String name = "_" + plainName.replaceAll(" ", "_");
			String pfad = pfadColumn.getCellData(i).getText();

			if (isKnownLanguage(sprache, plainName) && isExe(pfad, plainName)) {
				String combined = name + "|" + pfad + "|" + sprache + "|" + aktiv;

				if (autoLines.contains(combined)) {
					autoOutput.add(combined);
				} else {
					permOutput.add(combined);
				}
				changeSpeechFiles(typeNames[0], i);
			}
		}

		MyFiles.writeFile(autoOutput, MyFiles.AUTO_PROGRAMS_PATH);
		MyFiles.writeFile(permOutput, MyFiles.PROGRAMS_PATH);
		progEinstStage.close();

		SpeechRecognizerThread.restart();
	}

	private void saveFileDataFile() {
		System.out.println("saveFileDataFile");

		ArrayList<String> output = new ArrayList<String>();

		removeDeletedEntries();

		for (int i = 0; i < aktivColumn.getTableView().getItems().size(); i++) {
			String aktiv = aktivColumn.getCellData(i).isSelected() ? "Y" : "N";
			String sprache = spracheColumn.getCellData(i).getText();
			String plainName = nameColumn.getCellData(i).getText();
			String name = "_" + plainName.replaceAll(" ", "_");
			String pfad = pfadColumn.getCellData(i).getText();

			if (isKnownLanguage(sprache, plainName) && isFile(pfad, plainName)) {
				output.add(name + "|" + pfad + "|" + sprache + "|" + aktiv);
				changeSpeechFiles(typeNames[1], i);
			}
		}

		MyFiles.writeFile(output, MyFiles.FILES_PATH);
		progEinstStage.close();
		SpeechRecognizerThread.restart();
	}

	private void saveWebsiteDataFile() {
		System.out.println("saveWebsiteDataFile");
		ArrayList<String> output = new ArrayList<String>();

		removeDeletedEntries();

		for (int i = 0; i < aktivColumn.getTableView().getItems().size(); i++) {
			String aktiv = aktivColumn.getCellData(i).isSelected() ? "Y" : "N";
			String sprache = spracheColumn.getCellData(i).getText();
			String plainName = nameColumn.getCellData(i).getText();
			String pfad = pfadColumn.getCellData(i).getText();
			
			

			if (pfad != null && !pfad.startsWith("http://") && !pfad.startsWith("https://")) {
				pfad = "https://" + pfad;
			}
			if(sprache != null) {
				sprache = sprache.toUpperCase().trim();
			}
			if(plainName != null) {
				plainName = plainName.trim();
				System.out.println("plainName: " + plainName);
			}
			
			if (isKnownLanguage(sprache, plainName) && isValidName(plainName) && isWebsite(pfad, plainName)) {
				String name = "_" + plainName.replaceAll(" ", "_");
				output.add(name + "|" + pfad + "|" + sprache + "|" + aktiv);
				changeSpeechFiles(typeNames[2], i);
			} else {
				MyFiles.writeFile(output, MyFiles.WEBSITES_PATH);
				return;
			}
		}

		MyFiles.writeFile(output, MyFiles.WEBSITES_PATH);
		progEinstStage.close();
		SpeechRecognizerThread.restart();
	}

	private boolean isKnownLanguage(String sprache, String name) {
		if (sprache != null && (sprache.equals("DE") || sprache.equals("EN"))) {
			return true;
		}
		AlertController.showSprachErrorDialog(sprache, name);
		// System.err.println("DEBUG: Sprachtyp " + sprache + " wurde nicht
		// erkannt.");
		return false;
	}

	private boolean isValidName(String name) {
		if (name != null && name.matches("[\\wäöüÄÖÜ ]*")) {
			int occurences = 0;
			ObservableList<Entry> entries = FXCollections.observableArrayList();
			entries.addAll(MainApp.programData);
			entries.addAll(MainApp.fileData);
			entries.addAll(MainApp.websiteData);

			for (Entry entry : entries) {
				System.out.println(name.trim().replace("_", " ") + " | " + entry.getName().trim());
				if (name.trim().replace("_", " ").equals(entry.getName().trim())) {
					occurences++;
				}
			}
			if (occurences == 1) {
				return true;
			} else {
				System.err.println("occurences: " + occurences);
			}
		}
		AlertController.showNameErrorDialog(name);
		// System.err.println("DEBUG: Sprachtyp " + sprache + " wurde nicht
		// erkannt.");
		return false;
	}

	private boolean isExe(String pfad, String name) {
		if (new File(pfad).exists() && Files.isExecutable(Paths.get(pfad))) {
			return true;
		}
		AlertController.showProgramPathErrorDialog(name, pfad);
		// System.err.println("DEBUG: " + pfad + " ist kein Programm.");
		return false;
	}

	private boolean isFile(String pfad, String name) {
		if (new File(pfad).exists() && new File(pfad).isFile()) {
			return true;
		}
		AlertController.showProgramPathErrorDialog(name, pfad);
		// System.err.println("DEBUG: " + pfad + " ist keine Datei.");
		return false;
	}

	private boolean isWebsite(String url, String name) {
		try {
			URL testUrl = new URL(url);
			testUrl.toURI();
			return true;
		} catch (URISyntaxException | MalformedURLException exception) {
			AlertController.showErrorDialog("URL Fehler", "Die URL \"" + url + "\" der Webseite \"" + name + "\" ist ungültig.");
			// System.err.println("DEBUG: not a URL");
			return false;
		}
	}

	private void changeSpeechFiles(String type, int index) {

		Entry oldFile = (Entry) aktivColumn.getCellData(index).getProperties().get("old_" + type);
		Entry file = new Entry(aktivColumn.getCellData(index).isSelected(), spracheColumn.getCellData(index).getText().toUpperCase().trim(),
				nameColumn.getCellData(index).getText().trim(), pfadColumn.getCellData(index).getText(), oldFile.getType().get());

		if (!file.getName().matches("_?" + oldFile.getName())) {
			System.out.println("INFO: Name changed -> Ersetze " + oldFile.getName() + " mit " + file.getName());

			MyFiles.replaceEntryInDict(oldFile.getName(), file.getName(), file.getSprache());
			MyFiles.replaceEntryInGram(type, oldFile.getName(), file.getName());
		} else if (!file.getSprache().equals(oldFile.getSprache())) {
			System.out.println("INFO: Language changed -> Ersetze " + file.getName() + " mit neuer Aussprache");
			MyFiles.replaceEntryInDict(oldFile.getName(), file.getName(), file.getSprache());
		}
		if (file.isAktiv() != oldFile.isAktiv()) {
			System.out.println("INFO: Aktiv changed -> It's now " + file.isAktiv());
			if (file.isAktiv()) {
				MyFiles.addEntryToGram(type, new String[] { file.getName() });
			} else {
				MyFiles.removeEntryFromGram(type, file.getName());
			}
		}
		aktivColumn.getCellData(index).getProperties().put("old_" + type, file);
	}

	private void removeDeletedEntries() {
		System.out.println("removeDeletedEntries: " + entriesToRemove);
		for (int i = 0; i < entriesToRemove.size(); i++) {
			String name = "_" + entriesToRemove.get(i)[0].replace(" ", "_");
			MyFiles.removeEntryFromGram(typeNames[typeIndex], name);
			MyFiles.removeLineFromDict(name);
			if (typeIndex == 0) {
				MyFiles.addNewLineToFile(MyFiles.REMOVED_PROGRAMS_PATHS, entriesToRemove.get(i)[1]);
			}
		}
		entriesToRemove.clear();
	}

	@FXML
	private void closeWindow() {
		progEinstStage.close();
	}

	@FXML
	private void newEntry() {
		entryTable.getItems().add(new Entry(typeNames[typeIndex]));
		Entry entry = new Entry(typeNames[typeIndex]);
		aktivColumn.getCellData(entryTable.getItems().size() - 1).getProperties().put("old_" + typeNames[typeIndex], entry);
		System.out.println("INFO: (newEntry) put: " + entry.toString());
	}

	@FXML
	private void removeEntry() {

		Entry oldEntry = (Entry) aktivColumn.getCellData(entryTable.getSelectionModel().getSelectedIndex()).getProperties()
				.get("old_" + typeNames[typeIndex]);
		entryTable.getItems().remove(entryTable.getSelectionModel().getSelectedIndex());

		if (oldEntry.getName() != null) {
			entriesToRemove.add(new String[] { oldEntry.getName(), oldEntry.getPfad() });
		} else {
			System.out.println("ignored null entry");
		}
	}

	public void setProgEinstStage(Stage progEinstStage) {
		this.progEinstStage = progEinstStage;
	}

	public void setParams(ObservableList<Entry> entries, int type) {
		typeIndex = type;
		entryTable.setItems(entries);

		String[][] columnNames = { { "Programmname", "Pfad" }, { "Dateiname", "Pfad" }, { "Webseitenname", "URL" } };
		aktivColumn.setText("Aktiv");
		spracheColumn.setText("Sprache");
		nameColumn.setText(columnNames[type][0]);
		pfadColumn.setText(columnNames[type][1]);

		saveButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (type == 0) {
					saveProgramDataFile();
				} else if (type == 1) {
					saveFileDataFile();
				} else if (type == 2) {
					saveWebsiteDataFile();
				}
			}
		});
	}

}
