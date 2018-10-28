
package model;

import java.util.*;
import exception.*;

import java.io.*;

public class Automobile implements Serializable {

	////////// PROPERTIES //////////
	
	private String make;
	private String model;
	private int year;
	private double basePrice;
	private ArrayList<OptionSet> optionSet;
	private ArrayList<Option> choice;

	////////// CONSTRUCTORS //////////

	public Automobile() {
		
	}
	
	public Automobile(String make, String model, int year, double x, int y) {
		this.make = make;
		this.model = model;
		this.year = year;
		this.basePrice = x;
		this.optionSet = new ArrayList<OptionSet>(y);
		this.choice = new ArrayList<Option>(y);
		
		for (int i = 0; i < y; i++) {
			optionSet.add(i, new OptionSet());
			choice.add(i, new Option());
		}
	
	}
	
	////////// GETTERS AND SETTERS //////////
	
	public String getMake() throws AutoException {
		if (make == null) {
			throw new AutoException(101, "Property 'make' of Automobile object is empty");
		}
		else
			return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() throws AutoException {
		if (model == null) {
			throw new AutoException(102, "Property 'model' of Automobile object is empty");
		}
		else 
			return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
	public String getMakeModel() {
		StringBuffer sbuff = null;
		boolean flag = false;
		do {
			try {
				getMake();
				getModel();
				flag = true;
			}
			catch (AutoException e) {
				switch (e.getErrno()) {
					case 101:
						make = e.fix(101).toString();
						break;
					case 102:
						model = e.fix(102).toString();
						break;
				}
			}
		} while (flag == false);
		
		sbuff = new StringBuffer(make);
		sbuff.append(" " + model);
		String str = sbuff.toString();
		return str;
	}
	
	public int getYear() throws AutoException {
		if (year == 0) {
			throw new AutoException(106, "Property 'year' of Automobile object is empty");
		}
		else
			return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	public String getMakeModelYear() {
		StringBuffer sbuff = null;
		boolean flag = false;
		do {
			try {
				getMake();
				getModel();
				getYear();
				flag = true;
			}
			catch (AutoException e) {
				switch (e.getErrno()) {
					case 101:
						make = e.fix(101).toString();
						break;
					case 102:
						model = e.fix(102).toString();
						break;
					case 106:
						year = Integer.parseInt(e.fix(106).toString());
				}
			}
		} while (flag == false);
		
		sbuff = new StringBuffer(make);
		sbuff.append(" " + model + " " + year);
		String str = sbuff.toString();
		return str;
	}
	
	public double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(double basePrice) {
		this.basePrice = basePrice;
	}
	
	public OptionSet getOneOptSet(int x) throws AutoException {
		try {
			optionSet.get(x).getSetName();
		}
		catch (ArrayIndexOutOfBoundsException e) {
			throw new AutoException(105, "Array index out of bounds for property 'optionSet' in Automobile object");
		}
		catch (NullPointerException e) {
			throw new AutoException(104, "Property 'optionSet' of specified array index for Automobile object is empty");
		}
		return optionSet.get(x);
	}
	
	public ArrayList<OptionSet> getOptSet() throws AutoException {
		if (optionSet == null) 
			throw new AutoException(104, "Property 'optionSet' of Automobile object is empty");
		else 
			return optionSet;
	}
	
	public void setOneOptSet(OptionSet optSet, int x) {
		this.optionSet.set(x,  optSet);
	}
	
	public void setOptSet(ArrayList<OptionSet> optSet) {
		this.optionSet = optSet;
	}
	
	public int getOptSetLength() {
		return optionSet.size();
	}
	
	public void setOptSetLength(int x) {
		optionSet = new ArrayList<OptionSet>(x);
		choice = new ArrayList<Option>(x);
		for (int i = 0; i < x; i++) {
			optionSet.add(i, new OptionSet());
			choice.add(i, new Option());
		}
		System.out.println("Successfully created " + x + " option sets");
	}
	
	////////// CHOICE METHODS //////////
	
	public ArrayList<Option> getOptChoice() {
		return choice;
	}
	
	public Option getOneOptChoice(int x) {
		return choice.get(x);
	}
	
	public String getOneChoiceName(int x) {
		return choice.get(x).getName();
	}
	
	public double getOneChoicePrice(int x) {
		return choice.get(x).getPrice();
	}
	
	public double getTotalChoicePrice() {
		double totalPrice = basePrice;
		for (int i = 0; i < choice.size(); i++) {
			totalPrice += choice.get(i).getPrice();
		}
		return totalPrice;
	}
	
	public void setOptChoice(int x, int y) {
		optionSet.get(x).setChoice(optionSet.get(x).getOneOpt(y));
		choice.set(x, optionSet.get(x).getChoice());
	}
	
	public void setOptChoice(String setName, String optName) {
		if (optionSet.size() == 0) {
			System.out.println("Property \"" + setName + "\" not found. OptionSet is empty.");
		}
		else {
			for (int i = 0; i < optionSet.size(); i++) {
				if (setName.equals(optionSet.get(i).getSetName())) {
					for (int j = 0; j < getOptLength(i); j++) {
						if (optName.equals(getOptName(i, j))) {
							setOptChoice(i, j);
							System.out.println("Configured " + make + " " + model + " to have " + optName);
							break;
						}
						else {
							if (j == getOptLength(i) - 1 && !optName.equals(getOptName(i, j))) {
								System.out.println("Option \"" + optName + "\" not found");
							}
						}
					}
					break;
				}
				else {
					if (i == optionSet.size() - 1 && !setName.equals(optionSet.get(i).getSetName()))
						System.out.println("Property \"" + setName + "\" not found");
				}
			}
		}
	}
	
	public String getAutoKey() {
		StringBuffer sbuff = null;
		boolean flag = false;
		do {
			try {
				getMake();
				getModel();
				getYear();
				flag = true;
			}
			catch (AutoException e) {
				switch (e.getErrno()) {
					case 101:
						make = e.fix(101).toString();
						break;
					case 102:
						model = e.fix(102).toString();
						break;
					case 106:
						year = Integer.parseInt(e.fix(106).toString());
				}
			}
		} while (flag == false);
		
		sbuff = new StringBuffer(make);
		sbuff.append("_" + model.replace(' ', '_') + "_" + year);
		String autoKey = sbuff.toString();
		return autoKey;
	} 

	////////// ACCESS TO OPTIONSET //////////
	
	public String getOptSetName(int x) {
		return optionSet.get(x).getSetName();
	}
	
	public void setOptSetName(int x, String setName) {
		this.optionSet.get(x).setSetName(setName);
	}
	
	public String getOptName(int x, int y) {
		return optionSet.get(x).getOptName(y);
	}
	
	public void setOptName(int x, int y, String optName) {
		this.optionSet.get(x).setOptName(y, optName);
	}
	
	public float getOptPrice(int x, int y) {
		return optionSet.get(x).getOptPrice(y);
	}
	
	public void setOptPrice(int x, int y, float optPrice) {
		this.optionSet.get(x).setOptPrice(y, optPrice);
	}
	
	public void setOneOptSetOpt(int x, int y, String name, float price) {
		this.optionSet.get(x).buildOption(y, name, price);
	}
	
	public int getOptLength(int x) {
		return optionSet.get(x).getOptLength();
	}

	////////// INSTANCE METHODS //////////
	
	public OptionSet findOptionSet(String optSetName) {
		try {
			getOptSet();
		}
		catch (AutoException e) {
			switch (e.getErrno()) {
				case 104:
					this.setOptSetLength(Integer.parseInt(e.fix(e.getErrno()).toString()));
			}
		}
		finally {
			OptionSet optSet = null;
			for (int i = 0; i < optionSet.size(); i++) {
				if (optSetName.equals(getOptSetName(i))) {
					try {
						optSet = getOneOptSet(i);
					}
					catch (AutoException e) {
						
					}
					return optSet;
				}
				if (!optSetName.equals(getOptSetName(i)) && i == (optionSet.size() - 1)) {
					System.out.println("Property \"" + optSetName + "\" not found"); 
				}
			}
		}
		
		return null;
	}
	
	public Option findOption(String optSetName, String optName) {
		try {
			getOptSet();
		}
		catch (AutoException e) {
			switch (e.getErrno()) {
				case 104:
					this.setOptSetLength(Integer.parseInt(e.fix(e.getErrno()).toString()));
			}
		}
		finally {
			Option opt = null;
			for (int i = 0; i < optionSet.size(); i++) {
				if (optSetName.equals(optionSet.get(i).getSetName())) {
					for (int j = 0; j < getOptLength(i); j++) {
						if (optName.equals(getOptName(i, j))) {
							try {
								opt = getOneOptSet(i).getOneOpt(j);
							}
							catch (AutoException e) {
								
							}
							return opt;
						}
						else {
							if (j == getOptLength(i) - 1 && !optName.equals(getOptName(i,j)))
								System.out.println("Option \"" + optName + "\" not found");
						}
					}
					break;
				}
				else {
					if (i == optionSet.size() - 1 && !optSetName.equals(optionSet.get(i).getSetName()))
						System.out.println("Property \"" + optSetName + "\" not found");
				}
			}
		}
		
		return null;
	}
	
	public void updateOptSetName(String str, String newSetName) {
		try {
			getOptSet();
		}
		catch (AutoException e) {
			switch (e.getErrno()) {
				case 104:
					this.setOptSetLength(Integer.parseInt(e.fix(e.getErrno()).toString()));
			}
		}
		finally {
			for (int i = 0; i < optionSet.size(); i++) {
				if (str.equals(getOptSetName(i))) {
					setOptSetName(i, newSetName);
					System.out.println("Update success! Property " + str + " renamed to " + newSetName);
					break;
				}
				if (!str.equals(getOptSetName(i)) && i == (optionSet.size() - 1)) {
					System.out.println("Property \"" + str + "\" not found"); 
				}
			}
		}
	}
		
	public void updateOptName(String optSetName, String optName, String newOptName) {
		try {
			getOptSet();
		}
		catch (AutoException e) {
			switch (e.getErrno()) {
				case 104:
					this.setOptSetLength(Integer.parseInt(e.fix(e.getErrno()).toString()));
			}
		}
		finally {
			for (int i = 0; i < optionSet.size(); i++) {
				if (optSetName.equals(optionSet.get(i).getSetName())) {
					for (int j = 0; j < getOptLength(i); j++) {
						if (optName.equals(getOptName(i, j))) {
							setOptName(i, j, newOptName);
							System.out.println("Update success! Option " + optName + " renamed to " + newOptName);
							break;
						}
						else {
							if (j == getOptLength(i) - 1 && !optName.equals(getOptName(i,j)))
								System.out.println("Option \"" + optName + "\" not found");
						}
					}
					break;
				}
				else {
					if (i == optionSet.size() - 1 && !optSetName.equals(optionSet.get(i).getSetName()))
						System.out.println("Property \"" + optSetName + "\" not found");
				}
			}
		}
	}
	
	public void updateOptPrice(String optSetName, String optName, float newOptPrice) {
		try {
			getOptSet();
		}
		catch (AutoException e) {
			switch (e.getErrno()) {
				case 104:
					this.setOptSetLength(Integer.parseInt(e.fix(e.getErrno()).toString()));
			}
		}
		finally {
			for (int i = 0; i < optionSet.size(); i++) {
				if (optSetName.equals(optionSet.get(i).getSetName())) {
					for (int j = 0; j < getOptLength(i); j++) {
						if (optName.equals(getOptName(i, j))) {
							setOptPrice(i, j, newOptPrice);
							System.out.println("Update success! Option price for " + optName + " updated to $" + newOptPrice);
							break;
						}
						else {
							if (j == getOptLength(i) - 1 && !optName.equals(getOptName(i,j)))
								System.out.println("Option \"" + optName + "\" not found");
						}
					}
					break;
				}
				else {
					if (i == optionSet.size() - 1 && !optSetName.equals(optionSet.get(i).getSetName()))
						System.out.println("Property \"" + optSetName + "\" not found");
				}
			}
		}
	}
	
	public void deleteOptSet(int x) {
		this.optionSet.set(x, new OptionSet());
		System.out.println("Option set successfully deleted");
	}
	
	public void deleteOptSet(String str) {
		for (int i = 0; i < optionSet.size(); i++) {
			if (str.equals(getOptSetName(i))) {
				deleteOptSet(i);
			}
			if (!str.equals(getOptSetName(i)) && i == (optionSet.size() - 1)) {
				System.out.println("Property \"" + str + "\" not found"); 
			}
		}
	}
	
	public void deleteOpt(int x, int y) {
		optionSet.get(x).setOneOpt(null, y);;
		System.out.println("Option successfully deleted");
	}
	
	public void deleteOpt(String optSetName, String optName) {
		for (int i = 0; i < optionSet.size(); i++) {
			if (optSetName.equals(optionSet.get(i).getSetName())) {
				for (int j = 0; j < getOptLength(i); j++) {
					if (optName.equals(getOptName(i, j))) {
						deleteOpt(i,j);
						break;
					}
					else {
						if (j == getOptLength(i) - 1 && !optName.equals(getOptName(i,j)))
							System.out.println("Option \"" + optName + "\" not found");
					}
				}
				break;
			}
			else {
				if (i == optionSet.size() - 1 && !optSetName.equals(optionSet.get(i).getSetName()))
					System.out.println("Property \"" + optSetName + "\" not found");
			}
		}
	}

	////////// PRINT METHODS //////////
	
	public void printMake() {
		System.out.println("\n--------------------");
		System.out.println("Make: " + make);
		System.out.println("--------------------\n");
	}
	
	public void printModel() {
		System.out.println("\n--------------------");
		System.out.println("Model: " + model);
		System.out.println("--------------------\n");
	}
	
	public void printMakeModel() {
		System.out.println("\n--------------------");
		System.out.println(make + " " + model);
		System.out.println("--------------------\n");
	}
	
	public void printYear() {
		System.out.println("\n--------------------");
		System.out.println("Year: " + year);
		System.out.println("--------------------\n");
	}
	
	public void printBasePrice() {
		System.out.println("\n--------------------");
		System.out.println("Base price: $" + basePrice);
		System.out.println("--------------------\n");
	}
	
	public void printOptSet() {
		System.out.println("\n--------------------");
		if (optionSet == null) 
			System.out.println("There are no option sets for this Automobile object");
		else {
			for (int i = 0; i < optionSet.size(); i++) {
				if (optionSet.get(i) == null)
					System.out.println("Option Set: N/A");
				else
					optionSet.get(i).printdata();
			}
			System.out.println("--------------------\n");
		}
	}
	
	public void printOneOptSet(int x) {
		System.out.println("\n--------------------");
		if (optionSet == null) 
			System.out.println("There are no option sets for this Automobile object");
		else {
			if (optionSet.get(x) == null)
				System.out.println("Option set does not exist");
			else
				optionSet.get(x).printdata();
			System.out.println("--------------------\n");
		}
	}
	
	public void printOneOpt(int x, int y) {
		System.out.println("\n--------------------");
		if (optionSet == null) 
			System.out.println("There are no option sets for this Automobile object");
		else {
			if (optionSet.get(x).getOneOpt(y) == null)
				System.out.println("Option does not exist");
			else
				optionSet.get(x).printOneOpt(y);
			System.out.println("--------------------\n");
		}
	}
	
	public void printAllOptChoice() {
		for (int i = 0; i < choice.size(); i++) {
			System.out.print(optionSet.get(i).getSetName() + ": ");
			if (choice.get(i) == null)
				System.out.println("There are no configurations chosen for this option set");
			else
				System.out.println(choice.get(i).getName());
		}
	}
	
	public void printOneOptChoice(int x) {
		System.out.print(optionSet.get(x).getSetName() + ": ");
		if (choice.get(x) == null)
			System.out.println("There are no configurations chosen for this option set");
		else
			System.out.println(choice.get(x).getName());
	}
	
	public void printTotalChoicePrice() {
		System.out.println("\n--------------------");
		System.out.println("Total price for this configuraton is: $" + getTotalChoicePrice());
		System.out.println("--------------------\n");
	}
	
	public void printData() {
		System.out.println("\n--------------------");
		if (optionSet == null) 
			System.out.println("There are no option sets for this Automobile object");
		else {
			System.out.println(make + " " + model);
			System.out.println("Year: " + year);
			System.out.println("Base Price: $" + basePrice);
			for (int i = 0; i < optionSet.size(); i++) {
				if (optionSet.get(i) == null)
					System.out.println("Option Set: N/A");
				else
					optionSet.get(i).printdata();
			}
			System.out.println("--------------------\n");
		}
	}
}
