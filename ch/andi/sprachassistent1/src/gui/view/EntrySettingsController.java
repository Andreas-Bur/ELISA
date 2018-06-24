package gui.view;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import bgFunc.MyFiles;
import edu.cmu.sphinx.jsgf.JSGFGrammar;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import gui.MainApp;
import gui.MyAlert;
import gui.model.Entry;
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
import speech.MyLiveRecognizer;
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

	private MainApp mainApp;

	private Stage progEinstStage;
	private int typeIndex;
	private String[] typeNames = { "program", "file", "website" };
	ArrayList<String[]> entriesToRemove = new ArrayList<>();

	public EntrySettingsController() {
	}

	@FXML
	private void initialize() {
		aktivColumn.setCellValueFactory(cellData -> cellData.getValue().aktivProperty());
		spracheColumn.setCellValueFactory(cellData -> cellData.getValue().spracheProperty());
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		pfadColumn.setCellValueFactory(cellData -> cellData.getValue().pfadProperty());
	}

	@FXML
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

			if (!isKnownLanguage(sprache, plainName) || !isExe(pfad, plainName)) {
				return;
			}

			String combined = name + "|" + pfad + "|" + sprache + "|" + aktiv;

			if (autoLines.contains(combined)) {
				autoOutput.add(combined);
			} else {
				permOutput.add(combined);
			}
			changeSpeechFiles(typeNames[0], i);
			
		}

		//System.out.println(autoOutput);
		//System.out.println(permOutput);

		MyFiles.writeFile(autoOutput, MyFiles.AUTO_PROGRAMS_PATH);
		MyFiles.writeFile(permOutput, MyFiles.PROGRAMS_PATH);
		progEinstStage.close();

		SpeechRecognizerThread.restart();
	}

	private void saveFileDataFile() {
		System.out.println("saveFileDataFile");

		ArrayList<String> output = new ArrayList<String>();

		for (int i = 0; i < aktivColumn.getTableView().getItems().size(); i++) {
			String aktiv = aktivColumn.getCellData(i).isSelected() ? "Y" : "N";
			String sprache = spracheColumn.getCellData(i).getText();
			String plainName = nameColumn.getCellData(i).getText();
			String name = "_" + plainName.replaceAll(" ", "_");
			String pfad = pfadColumn.getCellData(i).getText();

			if (!isKnownLanguage(sprache, plainName) || !isFile(pfad, plainName)) {
				return;
			}
			output.add(name + "|" + pfad + "|" + sprache + "|" + aktiv);
			changeSpeechFiles(typeNames[1], i);
		}
		//System.out.println(output);
		MyFiles.writeFile(output, MyFiles.FILES_PATH);
		progEinstStage.close();
		SpeechRecognizerThread.restart();
	}

	private void saveWebsiteDataFile() {
		System.out.println("saveWebsiteDataFile");
		ArrayList<String> output = new ArrayList<String>();

		for (int i = 0; i < aktivColumn.getTableView().getItems().size(); i++) {
			String aktiv = aktivColumn.getCellData(i).isSelected() ? "Y" : "N";
			String sprache = spracheColumn.getCellData(i).getText();
			String plainName = nameColumn.getCellData(i).getText();
			String name = "_" + plainName.replaceAll(" ", "_");
			String pfad = pfadColumn.getCellData(i).getText();

			if (!isKnownLanguage(sprache, plainName) || !isWebsite(pfad, plainName)) {
				return;
			}
			output.add(name + "|" + pfad + "|" + sprache + "|" + aktiv);
			changeSpeechFiles(typeNames[2], i);
		}
		//System.out.println(output);
		MyFiles.writeFile(output, MyFiles.WEBSITES_PATH);
		progEinstStage.close();
		SpeechRecognizerThread.restart();
	}

	private boolean isKnownLanguage(String sprache, String name) {
		if (sprache.equals("DE") || sprache.equals("EN")) {
			return true;
		}
		MyAlert.showSprachErrorDialog(sprache, name);
		System.err.println("ERROR: Sprachtyp " + sprache + " wurde nicht erkannt.");
		return false;
	}

	private boolean isExe(String pfad, String name) {
		if (new File(pfad).exists() && Files.isExecutable(Paths.get(pfad))) {
			return true;
		}
		MyAlert.showProgramPathErrorDialog(name, pfad);
		System.err.println("ERROR: " + pfad + " ist kein Programm.");
		return false;
	}

	private boolean isFile(String pfad, String name) {
		if (new File(pfad).exists()) {
			return true;
		}
		MyAlert.showProgramPathErrorDialog(name, pfad);
		System.err.println("ERROR: " + pfad + " ist keine Datei.");
		return false;
	}

	private boolean isWebsite(String url, String name) {
		if (!url.matches("http[s]://www\\..*\\..*")) {
			System.err.println("not matching url");
			return false;
		}
		try {
			URL testUrl = new URL(url);
			testUrl.toURI();
			return true;
		} catch (Exception exception) {
			System.err.println("Exception: not a URL");
			return false;
		}
	}

	private void changeSpeechFiles(String type, int index) {

		Entry oldFile = (Entry) aktivColumn.getCellData(index).getProperties().get("old_" + type);
		Entry file = new Entry(aktivColumn.getCellData(index).isSelected(), spracheColumn.getCellData(index).getText(),
				nameColumn.getCellData(index).getText(), pfadColumn.getCellData(index).getText(), oldFile.getType().get());

		//System.out.println("changeSpeechFiles (old): " + oldFile.toString());
		//System.out.println("changeSpeechFiles (new): " + file.toString());

		if (!file.getName().matches("_?" + oldFile.getName())) {
			System.out.println("INFO: Name changed -> Ersetze " + oldFile.getName() + " mit " + file.getName());

			MyFiles.replaceEntryInDict(oldFile.getName(), file.getName(), file.getSprache());
			MyFiles.replaceEntryInGram(type, oldFile.getName(), file.getName());
		} else if (!file.getSprache().equals(oldFile.getSprache())) {
			System.out.println("INFO: Language changed -> Ersetze " + file.getName() + " mit neuer Aussprache");
			MyFiles.replaceEntryInDict(oldFile.getName(), file.getName(), file.getSprache());
		}
		if (file.isAktiv() != oldFile.isAktiv()) {
			System.out.println("INFO: Aktiv changed -> It's now "+file.isAktiv());
			if (file.isAktiv()) {
				MyFiles.addEntryToGram(type, new String[] { file.getName() });
			} else {
				MyFiles.removeEntryFromGram(type, file.getName());
			}
		}
		aktivColumn.getCellData(index).getProperties().put("old_" + type, file);
		//System.out.println("INFO: (changeSpeechFiles) put: "+file.toString());
	}

	private void removeDeletedEntries() {
		System.out.println("removeDeletedEntries: "+entriesToRemove.toString());
		for (int i = 0; i < entriesToRemove.size(); i++) {
			String name = "_" + entriesToRemove.get(i)[0].replace(" ", "_");
			MyFiles.removeEntryFromGram(typeNames[typeIndex], name);
			MyFiles.removeLineFromDict(name);
			if(typeIndex==0) {
				//TODO: nachfragen, ob nicht mehr danach gesucht werden soll
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
		//System.out.println("newEntry");
		entryTable.getItems().add(new Entry(typeNames[typeIndex]));
		Entry entry = new Entry(typeNames[typeIndex]);
		aktivColumn.getCellData(entryTable.getItems().size() - 1).getProperties().put("old_" + typeNames[typeIndex], entry);
		System.out.println("INFO: (newEntry) put: "+entry.toString());
	}

	@FXML
	private void removeEntry() {
		Entry removed = entryTable.getItems().remove(entryTable.getSelectionModel().getSelectedIndex());
		entriesToRemove.add(new String[] {removed.getName(), removed.getPfad()});
	}

	public void setProgEinstStage(Stage progEinstStage) {
		this.progEinstStage = progEinstStage;
	}

	public void setMainApp(MainApp mainApp, ObservableList<Entry> entries, int type) {
		this.mainApp = mainApp;
		typeIndex = type;
		entryTable.setItems(entries);

		String[][] columnNames = { { "Aktiv", "Sprache", "Programmname", "Pfad" }, { "Aktiv", "Sprache", "Dateiname", "Pfad" },
				{ "Aktiv", "Sprache", "Webseitenname", "URL" } };
		aktivColumn.setText(columnNames[type][0]);
		spracheColumn.setText(columnNames[type][1]);
		nameColumn.setText(columnNames[type][2]);
		pfadColumn.setText(columnNames[type][3]);

		saveButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				switch (type) {
				case 0:
					saveProgramDataFile();
					break;
				case 1:
					saveFileDataFile();
					break;
				case 2:
					saveWebsiteDataFile();
					break;
				}
			}
		});
	}

}
