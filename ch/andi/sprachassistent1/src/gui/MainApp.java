package gui;

import java.io.IOException;

import gui.view.IoOverviewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;
    private BorderPane rootLayout;

	@Override
	public void start(Stage primaryStage) {
		
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
            loader.setLocation(MainApp.class.getResource("view/IoOverview.fxml"));
			AnchorPane personOverview = (AnchorPane) loader.load();

			rootLayout.setCenter(personOverview);

			IoOverviewController controller = loader.getController();
			controller.setMainApp(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		launch(args);
	}
}
