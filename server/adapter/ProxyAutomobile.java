
package adapter;

import model.*; 
import util.*;
import exception.*;
import scale.*;
import server.*;

import java.util.*;

public abstract class ProxyAutomobile implements Debuggable {
	
	////////// PROPERTIES //////////
	
	private static Fleet<Automobile> fleet = new Fleet<Automobile>();
	private Collection<Automobile> col = fleet.getLHM().values();
	
	////////// GETTERS AND SETTERS //////////
	
	public static Fleet<Automobile> getFleet() {
		return fleet;
	}

	////////// INSTANCE METHODS //////////
	
	//METHOD TO BUILD AUTOMOBILE OBJECT WITH FILE DIRECTORY
	public void buildAuto(String fname) {
		FileIO f1 = new FileIO();
		Automobile a1 = null;
		String serfname = null;
		boolean flag = false;
		
		//Calling function in FileIO to build Automobile object
		do {
			try {
				a1 = f1.createAutoObject(fname);
				flag = true;
			}
			catch(AutoException e) {
				switch (e.getErrno()) {
					case 1:
						fname = e.fix(e.getErrno()).toString();
						break;
					case 2:
						e.fix(e.getErrno());
						System.exit(1);
				}
			}
		} while (flag == false);
		
		//Calling function in FileIO to serialize Automobile object
		flag = false;
		do {
			try {
				serfname = f1.writeData(a1);
				flag = true;
			}
			catch (AutoException e) {
				switch (e.getErrno()) {
					case 1:
						serfname = e.fix(e.getErrno()).toString();
						break;
					case 2:
						e.fix(e.getErrno());
						System.exit(1);
				}
			}
		} while (flag == false);
		
		//Calling function in FileIO to de-serialize Automobile object
		flag = false;
		do {
			try {
				a1 = f1.readData(serfname);
				flag = true;
			}
			catch (AutoException e) {
				switch (e.getErrno()) {
					case 1:
						serfname = e.fix(e.getErrno()).toString();
						break;
					case 2:
						e.fix(e.getErrno());
						System.exit(1);
					case 3:
						a1 = (Automobile) e.fix(e.getErrno());
						flag = true;
						break;
				}
			}
		} while (flag == false);
		
		fleet.setElement(a1.getAutoKey(), a1);
		
	}
	
	//METHOD TO BUILD AUTOMOBILE OBJECT WITH FILE OBJECT
	public void buildAuto(Object file) {
		FileIO f1 = new FileIO();
		Automobile a1 = null;
		String serfname = null;
		boolean flag = false;
		
		//Calling function in FileIO to build Automobile object
		a1 = f1.createAutoObject(file);
		
		//Calling function in FileIO to serialize Automobile object
		flag = false;
		do {
			try {
				serfname = f1.writeData(a1);
				flag = true;
			}
			catch (AutoException e) {
				switch (e.getErrno()) {
					case 1:
						serfname = e.fix(e.getErrno()).toString();
						break;
					case 2:
						e.fix(e.getErrno());
						System.exit(1);
				}
			}
		} while (flag == false);
		
		//Calling function in FileIO to de-serialize Automobile object
		flag = false;
		do {
			try {
				a1 = f1.readData(serfname);
				flag = true;
			}
			catch (AutoException e) {
				switch (e.getErrno()) {
					case 1:
						serfname = e.fix(e.getErrno()).toString();
						break;
					case 2:
						e.fix(e.getErrno());
						System.exit(1);
					case 3:
						a1 = (Automobile) e.fix(e.getErrno());
						flag = true;
						break;
				}
			}
		} while (flag == false);
		
		fleet.setElement(a1.getAutoKey(), a1);
	}
	
	//METHOD TO GET ALL AUTOMOBILE MODELS IN COLLECTION
	public String getAllModels() {
		StringBuffer sbuff = new StringBuffer();
		String all = null;
		int counter = 1;
		for (Iterator<Automobile> itr = col.iterator(); itr.hasNext(); counter++) {
			Automobile a1 = itr.next();
			sbuff.append(counter + ". " + a1.getMakeModelYear() + "\n");
		}
		all = sbuff.toString();
		return all;
	}
	
	
	//METHOD TO FIND AUTOMOBILE OBJECT BY MODEL
	public Automobile findAuto(String model) {
		for (Iterator<Automobile> itr = col.iterator(); itr.hasNext(); ) {
			Automobile a1 = itr.next();
			if (a1.getMakeModelYear().contains(model)) {
				return a1;
			}
			else {
				if (!itr.hasNext()) {
					System.out.println(model + " not found in database.");
				}
			}
		}
		
		return null;
	}
	
	//METHOD TO PRINT AUTOMOBILE OBJECT
	public void printAuto(String model) {
		for (Iterator<Automobile> itr = col.iterator(); itr.hasNext(); ) {
			Automobile a1 = itr.next();
			if (a1.getMakeModel().contains(model)) {
				a1.printData();
				break;
			}
			else {
				if (!itr.hasNext()) {
					System.out.println(model + " not found in database.");
				}
			}
		}
	}
	
	//METHOD TO UPDATE AUTOMOBILE OPTIONSET NAME
	public void updateOptionSetName(String model, String optSetName, String newSetName) {
		for (Iterator<Automobile> itr = col.iterator(); itr.hasNext(); ) {
			Automobile a1 = itr.next();
			if (a1.getMakeModel().contains(model)) {
				a1.updateOptSetName(optSetName, newSetName);
				fleet.replaceElement(a1.getAutoKey(), a1);
				break;
			}
			else {
				if (!itr.hasNext()) {
					System.out.println(model + " not found in database.");
				}
			}
		}
	}
	
	//METHOD TO UPDATE AUTOMOBILE OPTION PRICE
	public void updateOptionPrice(String model, String optSetName, String optName, float newOptPrice) {
		for (Iterator<Automobile> itr = col.iterator(); itr.hasNext(); ) {
			Automobile a1 = itr.next();
			if (a1.getMakeModel().contains(model)) {
				a1.updateOptPrice(optSetName, optName, newOptPrice);
				fleet.replaceElement(a1.getAutoKey(), a1);
				break;
			}
			else {
				if (!itr.hasNext()) {
					System.out.println(model + " not found in database.");
				}
			}
		}
	}
	
	//METHOD TO FIX ERRORS REGARDING AUTOMOBILE OBJECT
	public void fix(String model, int errno) {
		AutoException ae1 = new AutoException();
		for (Iterator<Automobile> itr = col.iterator(); itr.hasNext(); ) {
			Automobile a1 = itr.next();
			if (a1.getMakeModel().contains(model)) {
				switch (errno) {
					case 104:
						a1.setOptSetLength(Integer.parseInt(ae1.fix(errno).toString()));
				}
				fleet.replaceElement(a1.getAutoKey(), a1);
				break;
			}
			else {
				if (!itr.hasNext()) {
					System.out.println(model + " not found in database.");
				}
			}
		}
	}
	
	//METHOD TO CONFIGURE AUTOMOBILE OBJECT WITH OPTIONS
	public void chooseOption(String model, String setName, String optName) {
		for (Iterator<Automobile> itr = col.iterator(); itr.hasNext(); ) {
			Automobile a1 = itr.next();
			if (a1.getMakeModel().contains(model)) {
				a1.setOptChoice(setName, optName);
				fleet.replaceElement(a1.getAutoKey(), a1);
				break;
			}
			else {
				if (!itr.hasNext()) {
					System.out.println(model + " not found in database.");
				}
			}
		}
	}
	
	//METHOD TO OBTAIN TOTAL AUTOMOBILE CONFIGURATION PRICE
	public void calculateOptionTotal(String model) {
		for (Iterator<Automobile> itr = col.iterator(); itr.hasNext(); ) {
			Automobile a1 = itr.next();
			if (a1.getMakeModel().contains(model)) {
				a1.printTotalChoicePrice();
				break;
			}
			else {
				if (!itr.hasNext()) {
					System.out.println(model + " not found in database.");
				}
			}
		}
	}
	
	//METHOD TO START THREAD
	public void startThread(int threadno, int opnumber, String model, String setName, String newSetName) {
		EditOptions thread = new EditOptions(threadno, opnumber, model, setName, newSetName);
		thread.start();
	}
	
	//METHOD TO START SERVER
	public void serve(int port) {
		DefaultServerSocket server = new DefaultServerSocket(port);
		server.start();
	}

}
