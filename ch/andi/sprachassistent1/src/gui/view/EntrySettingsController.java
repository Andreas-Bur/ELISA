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
			}else{
				permOutput.add(combined);
			}
			
			String oldName = (String) aktivColumn.getCellData(i).getProperties().get("old_name");
			String oldSprache = (String) aktivColumn.getCellData(i).getProperties().get("old_sprache");
			String oldAktiv = (String) aktivColumn.getCellData(i).getProperties().get("old_aktiv");
			
			if(!name.matches("_?"+oldName)) {
				System.out.println("INFO: Ersetze "+oldName+" mit "+name);
				
				MyFiles.replaceEntryInDict(oldName, name, sprache);
				MyFiles.replaceProgramInGram(oldName, name);
				aktivColumn.getCellData(i).getProperties().put("old_name", name);
			}else if(!sprache.matches(oldSprache)) {
				MyFiles.replaceEntryInDict(oldName, name, sprache);
				aktivColumn.getCellData(i).getProperties().put("old_sprache", sprache);
			}
			if(!aktiv.matches(oldAktiv)) {
				if(aktiv.equals("Y")) {
					MyFiles.addEntryToGram("autoPrograms", new String[] {name});
				}else if(aktiv.equals("N")){
					MyFiles.removeEntryFromGram("autoPrograms", name);
				}
				aktivColumn.getCellData(i).getProperties().put("old_aktiv", aktiv);
			}
		}
		System.out.println(autoOutput);
		System.out.println(permOutput);

		MyFiles.writeFile(autoOutput, MyFiles.AUTO_PROGRAMS_PATH);
		MyFiles.writeFile(permOutput, MyFiles.PROGRAMS_PATH);
		progEinstStage.close();
		
		SpeechRecognizerThread.restart();
	}
	
	private boolean isKnownLanguage(String sprache, String name) {
		if (sprache.equals("DE") || sprache.equals("EN")) {
			return true;
		}
		MyAlert.showSprachErrorDialog(sprache, name);
		System.err.println("ERROR: Sprachtyp "+sprache+" wurde nicht erkannt.");
		return false;
	}
	
	private boolean isExe(String pfad, String name) {
		if (new File(pfad).exists() && Files.isExecutable(Paths.get(pfad))) {
			return true;
		}
		MyAlert.showProgramPathErrorDialog(name, pfad);
		System.err.println("ERROR: "+pfad+" ist kein Programm.");
		return false;
	}
	
	private boolean isFile(String pfad, String name) {
		if (new File(pfad).exists()) {
			return true;
		}
		MyAlert.showProgramPathErrorDialog(name, pfad);
		System.err.println("ERROR: "+pfad+" ist keine Datei.");
		return false;
	}
	
	private void saveFileDataFile() {
		System.out.println("saveFileDataFile");
		
		ArrayList<String> output = new ArrayList<String>();
		ArrayList<String> fileLines = new ArrayList<String>(Arrays.asList(MyFiles.getFileContent(MyFiles.FILES_PATH)));
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
		}
		progEinstStage.close();
		SpeechRecognizerThread.restart();
	}
	
	private void saveWebsiteDataFile() {
		System.out.println("saveWebsiteDataFile");
		progEinstStage.close();
		SpeechRecognizerThread.restart();
	}
	
	@FXML
	private void closeWindow() {
		progEinstStage.close();
	}

	public void setProgEinstStage(Stage progEinstStage) {
		this.progEinstStage = progEinstStage;
	}

	public void setMainApp(MainApp mainApp, ObservableList<Entry> entries, String[] columnNames, int type) {
		this.mainApp = mainApp;
		System.out.println(entries);
		entryTable.setItems(entries);
		aktivColumn.setText(columnNames[0]);
		spracheColumn.setText(columnNames[1]);
		nameColumn.setText(columnNames[2]);
		pfadColumn.setText(columnNames[3]);
		
		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				switch(type) {
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
