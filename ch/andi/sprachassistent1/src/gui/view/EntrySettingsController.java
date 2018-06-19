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
import javafx.fxml.FXML;
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
			String name = "_" + nameColumn.getCellData(i).getText().replaceAll(" ", "_");
			String pfad = pfadColumn.getCellData(i).getText();

			if (!sprache.equals("DE") && !sprache.equals("EN")) {
				MyAlert.showSprachErrorDialog(sprache, nameColumn.getCellData(i).getText());
				System.err.println("ERROR: Sprachtyp "+sprache+" wurde nicht erkan nt.");
				return;
			}
			if (!new File(pfad).exists() || !Files.isExecutable(Paths.get(pfad))) {
				MyAlert.showPfadErrorDialog(nameColumn.getCellData(i).getText(), pfad);
				System.err.println("ERROR: "+pfad+" ist kein Programm.");
				return;
			}

			String combined = name + "|" + pfad + "|" + sprache + "|" + aktiv;
			//System.out.println("combined: "+combined);
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
				
				MyFiles.replaceProgramInDict(oldName, name, sprache);
				MyFiles.replaceProgramInGram(oldName, name);
				aktivColumn.getCellData(i).getProperties().put("old_name", name);
			}else if(!sprache.matches(oldSprache)) {
				MyFiles.replaceProgramInDict(oldName, name, sprache);
				aktivColumn.getCellData(i).getProperties().put("old_sprache", sprache);
			}
			if(!aktiv.matches(oldAktiv)) {
				if(aktiv.equals("Y")) {
					MyFiles.addProgramsToGram(new String[] {name});
				}else if(aktiv.equals("N")){
					MyFiles.removeProgramFromGram(name);
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
	
	@FXML
	private void closeWindow() {
		progEinstStage.close();
	}

	public void setProgEinstStage(Stage progEinstStage) {
		this.progEinstStage = progEinstStage;
	}

	public void setMainApp(MainApp mainApp, ObservableList<Entry> entries) {
		this.mainApp = mainApp;
		System.out.println(entries);
		entryTable.setItems(entries);
	}

}
