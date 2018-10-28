
package exception;

import model.*;
import java.util.*;

////////// Fixes for Runtime Exceptions in FileIO.java ////////// 

public class Fix1to100 {
	
	////////// PROPERTIES //////////
	
	private Scanner in = new Scanner(System.in);
	
	////////// CONSTRUCTORS //////////
	
	public Fix1to100() {
		
	}
	
	////////// INSTANCE METHODS //////////
	
	//FileNotFoundException fix	
	public String fix1() {
		System.out.println("Invalid file directory");
		System.out.println("Enter a valid file name");
		String fname = in.nextLine();
		return fname;
	}
	
	//IOException fix	
	public void fix2() {
		System.out.println("An input output error has occured");
		System.out.println("Refresh the process and try again");
	}
	
	//ClassNotFoundException fix	
	public Automobile fix3() {
		System.out.println("Serialized file contains invalid class");
		System.out.println("Creating new Automobile object to replace invalid class");
		
		//Console input for automobile make
		System.out.println("Enter the automobile make: ");
		String tempMake = in.nextLine();
		
		//Console input for automobile model
		System.out.println("Enter the automobile model: ");
		String tempModel = in.nextLine();
		
		//Console input for automobile year and input validation
		System.out.println("Enter the automobile year: ");
		int tempYear = 0;
		try {
			tempYear = in.nextInt();
		}
		catch (NumberFormatException e) {
			tempYear = fix4();
		}
		
		//Console input for automobile base price and input validation
		System.out.println("Enter the automobile base price: ");
		float tempBasePrice = 0;
		try {
			tempBasePrice = in.nextFloat();
		}
		catch (NumberFormatException e) {
			tempBasePrice = fix5();
		}
		
		//Console input for number of option sets and input validation
		System.out.println("Enter the number of option sets: ");
		int tempOptSetLength = 0;
		try {
			tempOptSetLength = in.nextInt();
		}
		catch (NumberFormatException e) {
			tempOptSetLength = fix4();
		}
		
		Automobile a1 = new Automobile(tempMake, tempModel, tempYear, tempBasePrice, tempOptSetLength);
		System.out.println("Successfully created new Automobile object");
		
		return a1;
	}
	
	//NumberFormatException fix for integer data type	
	public int fix4() {
		int x = 0;
		boolean flag = false;
		do {
			System.out.println("Invalid data type, enter an integer value");
			String str = in.nextLine();
			try {
				x = Integer.parseInt(str);
				flag = true;
			}
			catch (NumberFormatException e) {
				flag = false;
			}
		} while (flag == false);
		
		return x;
	}
	
	//NumberFormatException fix for float data type	
	public float fix5() {
		float x = 0;
		boolean flag = false;
		do {
			System.out.println("Invalid data type, enter a floating-point value");
			String str = in.nextLine();
			try {
				x = Float.parseFloat(str);
				flag = true;
			}
			catch (NumberFormatException e) {
				flag = false;
			}
		} while (flag == false);
		
		return x;
	}
	
	//NullPointerException fix for empty Automobile object
	public Automobile fix6() {
		Automobile a1 = new Automobile();
		System.out.println("Created new Automobile object to replace null object");
		return a1;
	}
	
}
