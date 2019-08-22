package com.axwayaustralia.springmicro.data;

public class Stock {

	private String name;
	private String price;
	
	public Stock(String name, String price) {
		this.name = name;
		this.price = price;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}
	
	
}
