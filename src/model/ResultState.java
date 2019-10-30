package model;

import java.util.ArrayList;

public class ResultState {
	private String name;
	private String Period;
	private ArrayList<Order> orders;
	
	
	public ResultState(String n, String p) {
		this.name = n;
		this.Period = p;
		orders = new ArrayList<Order>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPeriod() {
		return Period;
	}

	public void setPeriod(String period) {
		Period = period;
	}

	public ArrayList<Order> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}
	
	public void addOrder(Order order) {
		orders.add(order);
	}
	
	public double consumoMaterialDirecto() {
		double result = 0.0;
		for (int i = 0; i <orders.size() ; i++) {
			result+= orders.get(i).getMd();
		}
		return result;
	}
	
	public double manoDeObraDirecta() {
		double result = 0.0;
		for (int i = 0; i <orders.size() ; i++) {
			if(orders.get(i).isActual()) {
				result+= orders.get(i).getMod();
			}
		}
		return result;
	}
	
	public double manoDeObraDirectaPasada() {
		double result = 0.0;
		for (int i = 0; i <orders.size() ; i++) {
			if(orders.get(i).isActual()==false) {
				result+= orders.get(i).getMod();
			}
		}
		return result;
	}
	
	public double costoIndirectoDeFabricacion() {
		double result = 0.0;
		for (int i = 0; i <orders.size() ; i++) {
			if(orders.get(i).isActual()) {
				result+= orders.get(i).getCif();
			}
		}
		return result;
	}
	
	public double costoIndirectoDeFabricacionPasado() {
		double result = 0.0;
		for (int i = 0; i <orders.size() ; i++) {
			if(orders.get(i).isActual()==false) {
				result+= orders.get(i).getCif();
			}
		}
		return result;
	}
	
	public double inventarioFinalPP() {
		double result = 0.0;
		for (int i = 0; i <orders.size() ; i++) {
			if(orders.get(i).isActual()) {
				if(orders.get(i).getStatus().equals("in Process")) {
					double aggregateCosts = orders.get(i).getMd() + orders.get(i).getMod() + orders.get(i).getCif();
					result += aggregateCosts;
				}
			}
		}
		return result;
	}
	
	public double inventarioInicialPP() {
		double result = 0.0;
		for (int i = 0; i <orders.size() ; i++) {
			if(orders.get(i).isActual()==false) {
				if(orders.get(i).getStatus().equals("in Process")) {
					double aggregateCosts = orders.get(i).getMd() + orders.get(i).getMod() + orders.get(i).getCif();
					result += aggregateCosts;
				}
			}
		}
		return result;
	}
	
	public double inventarioFinalPT() {
		double result = 0.0;
		for (int i = 0; i <orders.size() ; i++) {
			if(orders.get(i).isActual()) {
				if(orders.get(i).getStatus().equals("Finished")) {
					double aggregateCosts = orders.get(i).getMd() + orders.get(i).getMod() + orders.get(i).getCif();
					result += aggregateCosts;
				}
			}
		}
		return result;
	}
	
	public double inventarioInicialPT() {
		double result = 0.0;
		for (int i = 0; i <orders.size() ; i++) {
			if(orders.get(i).isActual()==false) {
				if(orders.get(i).getStatus().equals("Finished")) {
					double aggregateCosts = orders.get(i).getMd() + orders.get(i).getMod() + orders.get(i).getCif();
					result += aggregateCosts;
				}
			}
		}
		return result;
	}
	
	public void calcularCif(double realRate) {
		for (int i = 0; i < orders.size(); i++) {
			orders.get(i).calculateCif(realRate, orders.get(i).getMod());
		}
	}
}
