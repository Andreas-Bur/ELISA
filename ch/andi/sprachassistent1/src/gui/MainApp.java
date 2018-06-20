package gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import bgFunc.MyFiles;
import gui.model.Entry;
import gui.view.EntrySettingsController;
import gui.view.IoWindowController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;

	private ObservableList<Entry> programData = FXCollections.observableArrayList();
	private ObservableList<Entry> fileData = FXCollections.observableArrayList();
	private ObservableList<Entry> websiteData = FXCollections.observableArrayList();
	private ArrayList<ObservableList<Entry>> entryData = new ArrayList<>();
	

	@Override
	public void start(Stage primaryStage) {
		showWindow(primaryStage);
	}

	public MainApp() {
		System.out.println("Main");
		programData.addAll(getEntriesFromFile(getProgramDataFile(), "program"));
		fileData.addAll(getEntriesFromFile(getFilesDataFile(), "file"));
		websiteData.addAll(getEntriesFromFile(getWebseitenDataFile(), "website"));
		entryData.add(programData);
		entryData.add(fileData);
		entryData.add(websiteData);
	}

	private ArrayList<String> getProgramDataFile(){
		ArrayList<String> lines = new ArrayList<String>(Arrays.asList(MyFiles.getFileContent(MyFiles.PROGRAMS_PATH)));
		lines.addAll(Arrays.asList(MyFiles.getFileContent(MyFiles.AUTO_PROGRAMS_PATH)));
		return lines;
	}
	
	private ArrayList<String> getFilesDataFile(){
		ArrayList<String> lines = new ArrayList<String>(Arrays.asList(MyFiles.getFileContent(MyFiles.FILES_PATH)));
		return lines;
	}
	
	private ArrayList<String> getWebseitenDataFile(){
		ArrayList<String> lines = new ArrayList<String>(Arrays.asList(MyFiles.getFileContent(MyFiles.WEBSITES_PATH)));
		return lines;
	}

	private ArrayList<Entry> getEntriesFromFile(ArrayList<String> lines, String type) {
		ArrayList<Entry> list = new ArrayList<>();
		for (String line : lines) {
			String[] parts = line.split("\\|"); // name|pfad|sprache|aktiv
			System.out.println(Arrays.toString(parts));
			boolean aktiv = parts[3].equals("Y") ? true : false;
			String sprache = parts[2];
			String name = parts[0].replaceAll("_", " ").trim();
			String pfad = parts[1];
			list.add(new Entry(aktiv, sprache, name, pfad, type));
		}
		return list;
	}

	public ObservableList<Entry> getProgramsData() {
		return programData;
	}
	
	public ObservableList<Entry> getFilesData() {
		return fileData;
	}
	
	public ObservableList<Entry> getWebeitenData() {
		return websiteData;
	}

	public void showWindow(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Sprachassistent");

		setupRootLayout();
		showIoOverview();
	}

	public void setupRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showIoOverview() {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/IoWindow.fxml"));
			AnchorPane ioOverview = (AnchorPane) loader.load();

			rootLayout.setCenter(ioOverview);

			IoWindowController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showEinstellungenProgramms() {
		showEinstellungenEntry(0);
	}
	
	public void showEinstellungenFiles() {
		showEinstellungenEntry(1);
	}
	
	public void showEinstellungenWebseiten() {
		showEinstellungenEntry(2);
	}
	
	private void showEinstellungenEntry(int type) {
		String[] types = {"Programme", "Dateien", "Webseiten"};
		
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/EntrySettingsWindow.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			Stage progEinstStage = new Stage();
			progEinstStage.setTitle("Einstellungen - "+types[type]);
			progEinstStage.initModality(Modality.WINDOW_MODAL);
			progEinstStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			progEinstStage.setScene(scene);

			EntrySettingsController controller = loader.getController();
			controller.setProgEinstStage(progEinstStage);

			controller.setMainApp(this, entryData.get(type), type);

			progEinstStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
