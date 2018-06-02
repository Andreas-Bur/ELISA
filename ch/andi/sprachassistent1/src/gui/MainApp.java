package gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import bgFunc.MyFiles;
import gui.model.Programm;
import gui.view.EinstellungenProgrammeController;
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
    
    private ObservableList<Programm> programData = FXCollections.observableArrayList();

	@Override
	public void start(Stage primaryStage) {
		showWindow(primaryStage);
	}
	
	public MainApp() {
		readProgramDataFile();
	}
	
	private void readProgramDataFile() {
		ArrayList<String> lines = new ArrayList<String>(Arrays.asList(MyFiles.getFileContent(MyFiles.PROGRAMS_PATH)));
		lines.addAll(Arrays.asList(MyFiles.getFileContent(MyFiles.AUTO_PROGRAMS_PATH)));
		
		for(String line : lines) {
			String[] parts = line.split("\\|"); //name|pfad|sprache|aktiv
			System.out.println(Arrays.toString(parts));
			boolean aktiv = parts[3].equals("Y") ? true : false;
			String sprache = parts[2];
			String name = parts[0].replaceAll("_", " ").trim();
			String pfad = parts[1];
			programData.add(new Programm(aktiv, sprache, name, pfad));
		}
	}
	
	public ObservableList<Programm> getProgramData(){
		return programData;
	}
	
	public void showWindow(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Sprachassistent");

        setupRootLayout();

        showIoOverview();
	}

	public void setupRootLayout(){
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

	public void showIoOverview(){

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
	
	public void showEinstellungenProgramme() {
		try {

	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainApp.class.getResource("view/ProgramsSettingsWindow.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        Stage progEinstStage = new Stage();
	        progEinstStage.setTitle("Einstellungen - Programme");
	        progEinstStage.initModality(Modality.WINDOW_MODAL);
	        progEinstStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        progEinstStage.setScene(scene);

	        EinstellungenProgrammeController controller = loader.getController();
	        controller.setProgEinstStage(progEinstStage);
	        controller.setMainApp(this);

	        progEinstStage.showAndWait();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public static void main(String[] args) {
		launch(args);
	}
}
