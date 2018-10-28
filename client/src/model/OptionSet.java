
package model;

import java.io.*;
import java.util.*;

public class OptionSet implements Serializable {

	////////// PROPERTIES //////////
	
	private String setName;
	private ArrayList<Option> option;
	private Option choice;

	////////// CONSTRUCTORS //////////
	
	public OptionSet() {
		
	}
	
	public OptionSet(String name, int size) {
		this.setName = name;
		this.option = new ArrayList<Option>(size);
		
		for (int i = 0; i < size; i++) {
			option.add(i, new Option());
		}
	}

	////////// GETTERS AND SETTERS //////////
	
	protected String getSetName() {
		return setName;
	}

	protected void setSetName(String setName) {
		this.setName = setName;
	}
	
	protected Option getOneOpt(int x) {
		return option.get(x);
	}
	
	protected ArrayList<Option> getOpt() {
		return option;
	}
	
	protected void setOneOpt(Option opt, int x) {
		this.option.set(x, opt);
	}
	
	protected void setOpt(ArrayList<Option> opt) {
		this.option = opt;
	}
	
	protected Option getChoice() {
		return choice;
	}

	protected void setChoice(Option choice) {
		this.choice = choice;
	}

	protected int getOptLength() {
		return option.size();
	}
	
	////////// ACCESS TO OPTION //////////
	
	protected void buildOption(int x, String name, float price) {
		Option tempOpt = new Option(name, price);
		this.option.set(x, tempOpt);
	}
	
	protected String getOptName(int x) {
		return option.get(x).getName();
	}
	
	protected void setOptName(int x, String newOptName) {
		Option tempOpt = this.option.get(x);
		tempOpt.setName(newOptName);
		this.option.set(x, tempOpt);
	}
	
	protected float getOptPrice(int x) {
		return option.get(x).getPrice();
	}
	
	protected void setOptPrice(int x, float newOptPrice) {
		Option tempOpt = this.option.get(x);
		tempOpt.setPrice(newOptPrice);
		this.option.set(x, tempOpt);
	}
	
	////////// PRINT METHODS //////////
	
	protected void printdata() {
		System.out.println("Option Set: " + setName);
		for (int i = 0; i < option.size(); i++) {
			if (option.get(i) == null) 
				System.out.println((i + 1) + ": N/A");
			else {
				if (option.get(i).getName() == null)
					;
				else {
					System.out.print((i + 1) + ": ");
					option.get(i).printdata();
				}
			}
		}
	}
	
	protected void printOneOpt(int x) {
		System.out.println("Option Set: " + setName);
		option.get(x).printdata();
	}
	
}
