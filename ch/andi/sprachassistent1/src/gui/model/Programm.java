package gui.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class Programm {
	
	private final CheckBox aktiv;
	private final TextField name;
	private final TextField sprache;
	private final TextField pfad;

	public Programm() {
		this(true, null, null, null);
	}
	
	public Programm(boolean aktiv, String sprache, String name, String pfad) {
		this.aktiv = new CheckBox();
		this.aktiv.setSelected(aktiv);
		this.sprache = new TextField(sprache);
		this.name = new TextField(name);
		this.pfad = new TextField(pfad);
	}
	
	public boolean isAktiv() {
		return aktiv.isSelected();
	}
	
	public void setAktiv(boolean aktiv) {
		this.aktiv.setSelected(aktiv);
	}
	
	public BooleanProperty aktivProperty() {
		return new SimpleBooleanProperty(aktiv.isSelected());
	}
	
	public String getName() {
		return name.getText();
	}
	
	public void setName(String name) {
		this.name.setText(name);
	}
	
	public StringProperty nameProperty() {
		return new SimpleStringProperty(name.getText());
	}
	
	public String getSprache() {
		return sprache.getText();
	}
	
	public void setSprache(String sprache) {
		this.sprache.setText(sprache);
	}
	
	public StringProperty spracheProperty() {
		return new SimpleStringProperty(sprache.getText());
	}
	
	public String getPfad() {
		return pfad.getText();
	}
	
	public void setPfad(String pfad) {
		this.pfad.setText(pfad);
	}
	
	public StringProperty pfadProperty() {
		return new SimpleStringProperty(pfad.getText());
	}
	
}
