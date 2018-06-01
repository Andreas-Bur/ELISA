package gui;

import java.io.IOException;

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
		System.out.println("MainApp");
		programData.add(new Programm(true, "DE", "Test1", "D:\\testpfad"));
	}
	
	public ObservableList<Programm> getProgramData(){
		System.out.println("getProgramData");
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
