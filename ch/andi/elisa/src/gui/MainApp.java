package gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import bgFunc.MyFiles;
import feedback.AlertController;
import gui.model.Entry;
import gui.view.EntrySettingsController;
import gui.view.MainWindowController;
import gui.view.SettingsController;
import javafx.application.Application;
import javafx.application.Platform;
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

	public static ObservableList<Entry> programData = FXCollections.observableArrayList();
	public static ObservableList<Entry> fileData = FXCollections.observableArrayList();
	public static ObservableList<Entry> websiteData = FXCollections.observableArrayList();
	private static ArrayList<String> executedCommands = new ArrayList<>();
	private static MainWindowController mainWindowController;

	public MainApp() {
		Platform.setImplicitExit(false);
		programData.clear();
		fileData.clear();
		websiteData.clear();
		programData.addAll(getEntriesFromFile(getProgramsDataFile(), "program"));
		fileData.addAll(getEntriesFromFile(getFilesDataFile(), "file"));
		websiteData.addAll(getEntriesFromFile(getWebsitesDataFile(), "website"));
	}

	@Override
	public void start(Stage primaryStage) {
		showWindow(primaryStage);
	}

	public void showWindow(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("ELISA - Sprachassistent");

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
			AlertController.showIOExceptionDialog("Lesen");
			e.printStackTrace();
		}
	}

	public void showIoOverview() {

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/MainWindow.fxml"));
			AnchorPane mainPane = (AnchorPane) loader.load();

			rootLayout.setCenter(mainPane);

			mainWindowController = loader.getController();
			mainWindowController.setMainApp(this);
		} catch (IOException e) {
			AlertController.showIOExceptionDialog("Lesen");
			e.printStackTrace();
		}
	}

	private static ArrayList<String> getProgramsDataFile() {
		ArrayList<String> lines = new ArrayList<String>(Arrays.asList(MyFiles.getFileContent(MyFiles.PROGRAMS_PATH)));
		lines.addAll(Arrays.asList(MyFiles.getFileContent(MyFiles.AUTO_PROGRAMS_PATH)));
		return lines;
	}

	private static ArrayList<String> getFilesDataFile() {
		ArrayList<String> lines = new ArrayList<String>(Arrays.asList(MyFiles.getFileContent(MyFiles.FILES_PATH)));
		return lines;
	}

	private ArrayList<String> getWebsitesDataFile() {
		ArrayList<String> lines = new ArrayList<String>(Arrays.asList(MyFiles.getFileContent(MyFiles.WEBSITES_PATH)));
		return lines;
	}

	private ArrayList<Entry> getEntriesFromFile(ArrayList<String> lines, String type) {
		ArrayList<Entry> list = new ArrayList<>();
		for (String line : lines) {
			String[] parts = line.split("\\|");
			boolean aktiv = parts[3].equals("Y") ? true : false;
			String sprache = parts[2];
			String name = parts[0].replaceAll("_", " ").trim();
			String pfad = parts[1];
			Entry entry = new Entry(aktiv, sprache, name, pfad, type);
			entry.putOldEntryProperty();
			list.add(entry);
		}
		return list;
	}

	public void showEinstellungenEntry(int type) {
		String[] types = { "Programme", "Dateien", "Webseiten" };

		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/EntrySettingsWindow.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			Stage progEinstStage = new Stage();
			progEinstStage.setTitle("Einstellungen - " + types[type]);
			progEinstStage.initModality(Modality.WINDOW_MODAL);
			progEinstStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			progEinstStage.setScene(scene);

			EntrySettingsController controller = loader.getController();
			controller.setProgEinstStage(progEinstStage);

			if (type == 0) {
				controller.setParams(getUpdatedProgramData(), type);
			} else if (type == 1) {
				controller.setParams(getUpdatedFileData(), type);
			} else if (type == 2) {
				controller.setParams(getUpdatedWebsiteData(), type);
			}

			progEinstStage.showAndWait();

		} catch (IOException e) {
			AlertController.showIOExceptionDialog("Lesen");
			e.printStackTrace();
		}
	}

	public void showSettings() {

		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/SettingsDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			Stage settingsStage = new Stage();
			settingsStage.setTitle("Einstellungen");
			settingsStage.initModality(Modality.WINDOW_MODAL);
			settingsStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			settingsStage.setScene(scene);

			SettingsController controller = loader.getController();
			controller.setProgEinstStage(settingsStage);

			settingsStage.showAndWait();

		} catch (IOException e) {
			AlertController.showIOExceptionDialog("Lesen");
			e.printStackTrace();
		}
	}

	public void showDialog(String title, String path) {

		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource(path));
			AnchorPane page = (AnchorPane) loader.load();

			Stage settingsStage = new Stage();
			settingsStage.setTitle(title);
			settingsStage.initModality(Modality.WINDOW_MODAL);
			settingsStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			settingsStage.setScene(scene);

			settingsStage.showAndWait();

		} catch (IOException e) {
			AlertController.showIOExceptionDialog("Lesen");
			e.printStackTrace();
		}
	}

	public ArrayList<String> getExecutedCommands() {
		return executedCommands;
	}

	public static void addExecutedCommand(String command) {
		executedCommands.add(command);
		mainWindowController.populateTextArea();
	}
	
	private ObservableList<Entry> getUpdatedProgramData(){
		programData.clear();
		programData.addAll(getEntriesFromFile(getProgramsDataFile(), "program"));
		return programData;
	}
	private ObservableList<Entry> getUpdatedFileData(){
		fileData.clear();
		fileData.addAll(getEntriesFromFile(getFilesDataFile(), "file"));
		return fileData;
	}
	private ObservableList<Entry> getUpdatedWebsiteData(){
		websiteData.clear();
		websiteData.addAll(getEntriesFromFile(getWebsitesDataFile(), "website"));
		return websiteData;
	}
}
