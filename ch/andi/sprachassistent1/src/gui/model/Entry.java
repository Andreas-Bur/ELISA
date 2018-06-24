package gui.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class Entry {
	
	private final ObjectProperty<CheckBox> aktiv;
	private final ObjectProperty<TextField> name;
	private final ObjectProperty<TextField> sprache;
	private final ObjectProperty<TextField> pfad;
	private final StringProperty type;
	private final CheckBox checkbox;

	public Entry() {
		this(true, null, null, null, null);
	}
	
	public Entry(String type) {
		this(true, null, null, null, type);
	}
	
	public Entry(boolean aktiv, String sprache, String name, String pfad, String type) {
		checkbox = new CheckBox();
		checkbox.setSelected(aktiv);

		this.aktiv = new SimpleObjectProperty<CheckBox>(checkbox);
		this.sprache = new SimpleObjectProperty<TextField>(new TextField(sprache));
		this.name = new SimpleObjectProperty<TextField>(new TextField(name));
		this.pfad = new SimpleObjectProperty<TextField>(new TextField(pfad));
		this.type = new SimpleStringProperty(type);
	}
	
	public void putOldEntryProperty() {
		checkbox.getProperties().put("old_"+type.get(), new Entry(isAktiv(), getSprache(), getName(), getPfad(), getType().get()));
		//System.out.println("(Entry.putOldEntryProperty) put properties in old_"+type.get()+": "+this.toString() );
	}
	
	public boolean isAktiv() {
		return aktiv.get().isSelected();
	}
	
	public void setAktiv(boolean aktiv) {
		this.aktiv.get().setSelected(aktiv);
	}
	
	public ObjectProperty<CheckBox> aktivProperty() {
		return aktiv;
	}
	
	public String getName() {
		return name.get().getText();
	}
	
	public void setName(String name) {
		this.name.set(new TextField(name));
	}
	
	public ObjectProperty<TextField> nameProperty() {
		return name;
	}
	
	public String getSprache() {
		return sprache.get().getText();
	}
	
	public void setSprache(String sprache) {
		this.sprache.set(new TextField(sprache));
	}
	
	public ObjectProperty<TextField> spracheProperty() {
		return sprache;
	}
	
	public String getPfad() {
		return pfad.get().getText();
	}
	
	public void setPfad(String pfad) {
		this.pfad.set(new TextField(pfad));
	}
	
	public ObjectProperty<TextField> pfadProperty() {
		return pfad;
	}
	
	public StringProperty getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return "("+isAktiv()+"|"+getSprache()+"|"+getName()+"|"+getPfad()+"|"+getType()+")";
	}
	
}
