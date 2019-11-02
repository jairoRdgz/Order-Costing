package model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Order {
	
	public final static String INPROCESS = "in Process";
	public final static String FINISHED = "Finished";
	public final static String SOLD = "Sold";
	
	private SimpleStringProperty id;
	private SimpleDoubleProperty md;
	private SimpleDoubleProperty mod;
	private SimpleDoubleProperty cif;
	private SimpleBooleanProperty actual;
	private SimpleDoubleProperty hours;
	private SimpleStringProperty status;
	
	public Order(String id, double md, double mod, String status, boolean actual, double hours) {
		this.id = new SimpleStringProperty(id);
		this.md = new SimpleDoubleProperty(md);
		this.mod = new SimpleDoubleProperty(mod);
		this.status = new SimpleStringProperty(status);
		this.actual = new SimpleBooleanProperty(actual);
		this.hours = new SimpleDoubleProperty(hours);
	}

	public String getId() {
		return id.get();
	}

	public void setId(String id) {
		this.id = new SimpleStringProperty(id);
	}

	public double getMd() {
		return md.get();
	}

	public void setMd(double md) {
		this.md = new SimpleDoubleProperty(md);
	}

	public double getMod() {
		return mod.get();
	}

	public void setMod(double mod) {
		this.mod = new SimpleDoubleProperty(mod);
	}
	
	public double getCif() {
		return cif.get();
	}

	public void setCif(double cif) {
		this.cif =new SimpleDoubleProperty(cif);
	}

	public String getStatus() {
		return status.get();
	}

	public void setStatus(String status) {
		this.status = new SimpleStringProperty(status);;
	}
	
	public void calculateCif(double rate, double base) {
		cif = new SimpleDoubleProperty(rate*base);
	}

	public boolean isActual() {
		return actual.get();
	}

	public void setActual(boolean actual) {
		this.actual = new SimpleBooleanProperty(actual);
	}

	public double getHours() {
		return hours.get();
	}

	public void setHours(double hours) {
		this.hours = new SimpleDoubleProperty(hours);
	}

}
