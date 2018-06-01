package gui.view;

import gui.MainApp;
import gui.model.Programm;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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
	private void initialize(){
		aktivColumn.setCellValueFactory(new PropertyValueFactory<Programm, CheckBox>("Aktiv"));
		spracheColumn.setCellValueFactory(new PropertyValueFactory<Programm, TextField>("Sprache"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<Programm, TextField>("Programmname"));
		pfadColumn.setCellValueFactory(new PropertyValueFactory<Programm, TextField>("Pfad"));
	}
	
	public void setProgEinstStage(Stage progEinstStage) {
        this.progEinstStage = progEinstStage;
    }
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		programTable.setItems(mainApp.getProgramData());
	}

}
