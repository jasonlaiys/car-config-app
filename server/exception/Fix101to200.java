
package exception;

import java.util.*;

////////// Fixes for Runtime Exceptions in model package ////////// 

public class Fix101to200 {
	
	////////// PROPERTIES //////////
	
	private Scanner in = new Scanner(System.in);
	
	////////// CONSTRUCTORS //////////
	
	public Fix101to200() {
		
	}
	
	////////// INSTANCE METHODS //////////
	
	//NullPointerException fix for property 'make' in Automobile
	public String fix101() {
		System.out.println("Enter Automobile make: ");
		String make = in.nextLine();
		return make;
	}
	
	//NullPointerException fix for property 'model' in Automobile
	public String fix102() {
		System.out.println("Enter Automobile model: ");
		String model = in.nextLine();
		return model;
	}
	
	//NullPointerException fix for property 'basePrice' in Automobile
	public float fix103() {
		System.out.println("Enter Automobile base price: ");
		float basePrice = 0;
		boolean flag = false;
		do {
			String str = in.nextLine();
			try {
				basePrice = Float.parseFloat(str);
				flag = true;
			}
			catch (NumberFormatException e) {
				System.out.println("Invalid data type, enter a floating-point value");
				flag = false;
			}
		} while (flag == false);
		
		return basePrice;
	}
	
	//NullPointerException fix for property 'optSet' in Automobile
	public int fix104() {
		System.out.println("Array object for Automobile object is empty");
		System.out.println("Enter the number of option sets for Automobile object: ");
		int numOfOptSets = 0;
		boolean flag = false;
		do {
			String str = in.nextLine();
			try {
				numOfOptSets = Integer.parseInt(str);
				flag = true;
			}
			catch (NumberFormatException e) {
				System.out.println("Invalid data type, enter an integer value");
				flag = false;
			}
		} while (flag == false);
		
		return numOfOptSets;
	}
	
	//ArrayIndexOutOfBoundsException fix for property 'optSet' in Automobile
	public int fix105() {
		System.out.println("Enter a correct array index value: ");
		int arrayIndex = 0;
		boolean flag = false;
		do {
			String str = in.nextLine();
			try {
				arrayIndex = Integer.parseInt(str);
				flag = true;
			}
			catch (NumberFormatException e) {
				System.out.println("Invalid data type, enter an integer value");
				flag = false;
			}
		} while (flag == false);
		
		return arrayIndex;
	}
	
	//NullPointerException fix for property 'year' in Automobile
		public int fix106() {
			System.out.println("Enter Automobile year of make: ");
			int year = 0;
			boolean flag = false;
			do {
				String str = in.nextLine();
				try {
					year = Integer.parseInt(str);
					flag = true;
				}
				catch (NumberFormatException e) {
					System.out.println("Invalid data type, enter an integer value");
					flag = false;
				}
			} while (flag == false);
			
			return year;
		}
}
