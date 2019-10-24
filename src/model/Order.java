package model;

public class Order {
	
	public final static String INPROCESS = "in Process";
	public final static String FINISHED = "Finished";
	public final static String SOLD = "Sold";
	
	private int id;
	private double md;
	private double mod;
	private double cif;
	private String status;
	
	public Order(int id, double md, double mod, String status) {
		this.id = id;
		this.md = md;
		this.mod = mod;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getMd() {
		return md;
	}

	public void setMd(double md) {
		this.md = md;
	}

	public double getMod() {
		return mod;
	}

	public void setMod(double mod) {
		this.mod = mod;
	}
	
	public double getCif() {
		return cif;
	}

	public void setCif(double cif) {
		this.cif = cif;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public void calculateCif(double rate, double base) {
		cif = rate * base;
	}
	
}
