
package model;

import java.io.*;

public class Option implements Serializable {

	////////// PROPERTIES //////////
	
	private String name;
	private float price;

	////////// CONSTRUCTORS //////////
	
	public Option() {
		
	}
	
	public Option(String name, float price) {
		this.name = name;
		this.price = price;
	}

	////////// GETTERS AND SETTERS //////////
	
	protected String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}

	protected float getPrice() {
		return price;
	}

	protected void setPrice(float price) {
		this.price = price;
	}
	
	////////// PRINT METHODS //////////
	
	protected void printdata() {
		System.out.println(name + ", $" + price);
	}
}