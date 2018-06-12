package gui.view;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import bgFunc.MyFiles;
import bgFunc.Words;
import gui.MainApp;
import gui.model.Programm;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import speech.MyLiveRecognizer;
import speech.SpeechRecognizerThread;

public class EinstellungenProgrammeController {

	@FXML
	private TableView<Programm> programTable;
	@FXML
	private TableColumn<Programm, CheckBox> aktivColumn;
	@FXML
	private TableColumn<Programm, TextField> spracheColumn;
	@FXML
	private TableColumn<Programm, TextField> nameColumn;
	@FXML
	private TableColumn<Programm, TextField> pfadColumn;

	private MainApp mainApp;

	private Stage progEinstStage;

	public EinstellungenProgrammeController() {
	}

	@FXML
	private void initialize() {
		aktivColumn.setCellValueFactory(cellData -> cellData.getValue().aktivProperty());
		spracheColumn.setCellValueFactory(cellData -> cellData.getValue().spracheProperty());
		nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
		pfadColumn.setCellValueFactory(cellData -> cellData.getValue().pfadProperty());

		System.out.println(aktivColumn);
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
				// TODO: ERROR
				System.err.println("ERROR: Sprachtyp "+sprache+" wurde nicht erkannt.");
				continue;
			}
			if (!new File(pfad).exists() || !Files.isExecutable(Paths.get(pfad))) {
				// TODO: ERROR
				System.err.println("ERROR: "+pfad+" ist kein Programm.");
				continue;
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
		//System.out.println(Arrays.toString(autoOutput.toArray()));
		System.out.println(permOutput);
		//System.out.println(Arrays.toString(permOutput.toArray()));
		MyFiles.writeFile(autoOutput, MyFiles.AUTO_PROGRAMS_PATH);
		MyFiles.writeFile(permOutput, MyFiles.PROGRAMS_PATH);
		progEinstStage.close();

		
		Main.restartSpeechRecognizerThread = true;
	}
	
	@FXML
	private void closeWindow() {
		progEinstStage.close();
	}

	public void setProgEinstStage(Stage progEinstStage) {
		this.progEinstStage = progEinstStage;
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		programTable.setItems(mainApp.getProgramData());
	}

}
