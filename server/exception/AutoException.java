
package exception;

import model.*;
import java.io.*;
import java.util.*;
import java.text.*;

public class AutoException extends Exception {
	
	////////// PROPERTIES //////////
	
	private int errno;
	private String errmsg;
	
	////////// CONSTRUCTORS //////////
	
	public AutoException() {
		super();
	}
	
	public AutoException(int errno) {
		super();
		this.errno = errno;
		this.errmsg = "N/A";
		printdata();
	}
	
	public AutoException(String errmsg) {
		super();
		this.errmsg = errmsg;
		printdata();
	}
	
	public AutoException(int errno, String errmsg) {
		super();
		this.errno = errno;
		this.errmsg = errmsg;
		printdata();
	}
	
	////////// GETTERS AND SETTERS //////////
	
	public int getErrno() {
		return errno;
	}

	public void setErrno(int errno) {
		this.errno = errno;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	
	////////// INSTANCE METHODS //////////
	
	public Object fix(int errno) {
		Fix1to100 f1 = new Fix1to100();
		Fix101to200 f2 = new Fix101to200();
		
		//Error source from util package
		if (errno > 0 && errno <= 100) {
			switch (errno) {
				case 1:	
					String fname = f1.fix1();
					writelog("Error 1: FileNotFoundException in package util. Exception handled.");
					return fname;
				case 2:
					f1.fix2();
					writelog("Error 2: IOException in package util. System successfully exited.");
					break;
				case 3:
					Automobile a1 = f1.fix3();
					writelog("Error 3: ClassNotFoundException in package util. Exception handled.");
					return a1;
				case 4:
					int x = f1.fix4();
					writelog("Error 4: NumberFormatException for type integer in package util. Exception handled.");
					return x;
				case 5:
					float y = f1.fix5();
					writelog("Error 5: NumberFormatException for type float in package util. Exception handled.");
					return y;	
				case 6:
					Automobile a2 = f1.fix6();
					writelog("Error 6: NullPointerException in package util for empty Automobile object. Exception handled.");
					return a2;
			}
		}
		
		//Error source from model package
		if (errno > 100 && errno <= 200) {
			switch (errno) {
				case 101:
					String make = f2.fix101();
					writelog("Error 101: NullPointerException in package model for empty Automobile property 'make'. Exception handled.");
					return make;
				case 102:
					String model = f2.fix102();
					writelog("Error 102: NullPointerException in package model for empty Automobile property 'model'. Exception handled.");
					return model;
				case 103:
					float basePrice = f2.fix103();
					writelog("Error 103: NullPointerException in package model for empty Automobile property 'basePrice'. Exception handled.");
					return basePrice;
				case 104:
					int numOfOptSets = f2.fix104();
					writelog("Error 104: NullPointerException in package model for empty Automobile property 'optSet'. Exception handled.");
					return numOfOptSets;
				case 105:
					int arrayIndex = f2.fix105();
					writelog("Error 105: ArrayIndexOutOfBoundsException in package model for invalid array index in Automobile property 'optSet'. Exception handled.");
					return arrayIndex;
				case 106:
					int year = f2.fix106();
					writelog("Error 106: NullPointerException in package model for empty Automobile property 'year'. Exception handled.");
					return year;
			}
		}
		
		return null;
	}
	
	////////// PRINT METHODS //////////
	
	//Print error data
	public void printdata() {
		if (errno == 0) {
			System.out.println("\nError " + errno + ": Unspecified error");
			System.out.println(errmsg);
		}
		else {
			System.out.println("\nError " + errno);
			System.out.println(errmsg);
		}
	}
	
	//Write error logs to text file
	//Code snippet acquired from stackoverflow.com 
	public void writelog(String errlog) {
		try {
			DateFormat d1 = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
			Date date = new Date();
			PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("errlog.txt", true)));
			writer.println("[" + d1.format(date) + "] " + errlog);
			writer.close();
		}
		catch (IOException e) {
			System.out.println("IO Error, try restarting the process");
			System.exit(1);
		}
		finally {
			
		}
	}
	
}
