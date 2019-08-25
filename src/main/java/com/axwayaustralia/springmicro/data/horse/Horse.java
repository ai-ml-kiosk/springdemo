package com.axwayaustralia.springmicro.data.horse;

public class Horse implements Comparable<Horse> {

	private String name;
	private long position;
	private String jockey;
	private String price;
	private String number;
	private boolean hasFinished;
	private Long distanceTravelled;
	
	public Horse(String number, String name, String jockey) {
		this.name = name;
		this.number = number;
		this.jockey = jockey;
		this.position = Long.parseLong(number);
		this.price = "0.00";
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
	 * @return the position
	 */
	public long getPosition() {
		return position;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(long position) {
		this.position = position;
	}
	/**
	 * @return the jockey
	 */
	public String getJockey() {
		return jockey;
	}
	/**
	 * @param jockey the jockey to set
	 */
	public void setJockey(String jockey) {
		this.jockey = jockey;
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
	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}


	/**
	 * @return the hasFinished
	 */
	public boolean isHasFinished() {
		return hasFinished;
	}


	/**
	 * @param hasFinished the hasFinished to set
	 */
	public void setHasFinished(boolean hasFinished) {
		this.hasFinished = hasFinished;
	}


	/**
	 * @return the distanceTravelled
	 */
	public Long getDistanceTravelled() {
		return distanceTravelled;
	}


	/**
	 * @param distanceTravelled the distanceTravelled to set
	 */
	public void setDistanceTravelled(long distanceTravelled) {
		this.distanceTravelled = distanceTravelled;
	}

	@Override
	public int compareTo(Horse o) {
		    return getDistanceTravelled().compareTo(o.getDistanceTravelled());
	}
}
