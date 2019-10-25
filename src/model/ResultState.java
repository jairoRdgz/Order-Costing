package model;

import java.util.ArrayList;

public class ResultState {
	private String name;
	private String Period;
	private ArrayList<Order> orders;
	
	public ResultState(String n, String p) {
		this.name = n;
		this.Period = p;
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
	
	public double compraMaterialDirecto() { //poner en english plz
		double result = 0.0;
		for (int i = 0; i <orders.size() ; i++) {
			result+= orders.get(i).getMd();
		}
		return result;
	}
}
