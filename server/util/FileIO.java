
package util;

import java.io.*;
import java.util.*;
import model.*;
import exception.*;
import adapter.*;

public class FileIO implements Debuggable {

	////////// PROPERTIES //////////
	
	private String fname = null;
	private int fType;

	////////// CONSTRUCTORS //////////
	
	public FileIO() {
		
	}
	
	public FileIO(String fname) {
		this.fname = fname;
	}
	
	////////// GETTERS AND SETTERS //////////
	
	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	////////// INSTANCE METHODS //////////
	
	public Automobile createAutoObject(String fname) throws AutoException {
		this.fname = fname;
		
		if (fname.contains(".txt"))
			this.fType = 1;
		else if (fname.contains(".prop"))
			this.fType = 2;
		else
			this.fType = 0;
		
		Automobile a1 = null;
		switch (fType) {
			case 1:
				a1 = buildAutoObject1(a1, fname);
				break;
			case 2:
				a1 = buildAutoObject2(a1, fname);
				break;
			default:
				System.out.println("File type not registered");
		}
		
		return a1;
	}
	
	public Automobile createAutoObject(Object file) {
		
		if (isTextFile(file)) 
			this.fType = 1;
		else if (isPropertiesFile(file))
			this.fType = 2;
		else
			this.fType = 0;
		
		Automobile a1 = null;
		switch (fType) {
			case 1:
				a1 = buildAutoObject1(a1, file);
				break;
			case 2:
				a1 = buildAutoObject2(a1, file);
				break;
			default:
				System.out.println("File type not registered");
		}
		
		return a1;
	}
	
	////////// METHODS TO READ TEXT FILE //////////
	
	//Method to build Automobile object from text file
	public Automobile buildAutoObject1(Automobile a1, String fname) throws AutoException {
		int counter = 0; 
		int numOfOpt = countOptions();
		try {
			FileReader f1 = new FileReader(this.fname);
			BufferedReader b1 = new BufferedReader(f1);
			String tempMake = null;
			String tempModel = null;
			int tempYear = 0;
			double tempPrice = 0;
			boolean eof = false;
			while (!eof) {
				String line = b1.readLine();
				counter++;
				if (DEBUG)
					System.out.println("Reading: " + line);
				if (line == null)
					eof = true;
				else { //Read lines when eof not reached
					if (counter == 1) { //Read line 1 (Make)
						tempMake = line; 
					}
					if (counter == 2) { //Read line 2 (Model)
						tempModel = line;
					}
					if (counter == 3) { //Read line 3 (Year)
						boolean flag = false;
						do {
							try {
								tempYear = lineToInt(line);
								flag = true;
							}
							catch (AutoException e) {
								switch (e.getErrno()) {
									case 4:
										tempYear = Integer.parseInt(e.fix(e.getErrno()).toString());
										flag = true;
										break;
								}
							}
						} while (flag == false);
					}
					if (counter == 4) { //Read line 4 (Base Price)
						boolean flag = false;
						do {
							try {
								tempPrice = lineToFloat(line);
								flag = true;
							}
							catch (AutoException e) {
								switch (e.getErrno()) {
									case 5:
										tempPrice = Float.parseFloat(e.fix(e.getErrno()).toString());
										flag = true;
										break;
								}
							}
						} while (flag == false);
					}
					if (counter == 5) { //Create new Automobile object
						if (line.contains("Hybrid")) {
							a1 = new Hybrid(tempMake, tempModel, tempYear, tempPrice, numOfOpt, "20kWh");
						}
						else if (line.contains("Truck")) {
							a1 = new Truck(tempMake, tempModel, tempYear, tempPrice, numOfOpt);
						}
						else {
							a1 = new Automobile(tempMake, tempModel, tempYear, tempPrice, numOfOpt);
						}
						if (DEBUG) {
							System.out.println("Automotive object instantiated");
							System.out.println("Make: " + a1.getMake());
							System.out.println("Model: " + a1.getModel());
							System.out.println("Year: " + a1.getYear());
							System.out.println("Base price: $" + a1.getBasePrice());
							System.out.println("Number of config options: " + a1.getOptSet().size());
						}
					}
					if (counter > 5) { //Reading line 6 onwards (OptionSet and Option)
						int numOfTokens = countTokens(line, ";");
						a1 = buildOptSet(a1, (counter - 6), line, numOfTokens);
					}
				}
			}
			b1.close();
		} 
		catch (FileNotFoundException e) {
			throw new AutoException(1, "File not found");
		}
		catch (IOException e) {
			throw new AutoException(2, "Check I/O stream");
		}
		finally {
			
		}
		return a1;
	}
	
	//Method to build Automobile object from concatenated string of text file
	public Automobile buildAutoObject1(Automobile a1, Object file) {
		StringBuffer sbuff = (StringBuffer) file;
		String concatTxt = sbuff.toString();
		StringTokenizer st = new StringTokenizer(concatTxt, "\n");
		String tempMake = null;
		String tempModel = null;
		int tempYear = 0;
		double tempPrice = 0;
		int numOfOptSets = countTokens(concatTxt, "\n") - 4;
		int count = 0;
		
		while (st.hasMoreTokens()) {
			if (count == 0) {
				tempMake = st.nextToken();
			}
			if (count == 1) {
				tempModel = st.nextToken();
			}
			if (count == 2) {
				boolean flag = false;
				do {
					try {
						tempYear = lineToInt(st.nextToken());
						flag = true;
					}
					catch (AutoException e) {
						switch (e.getErrno()) {
							case 4:
								tempYear = Integer.parseInt(e.fix(e.getErrno()).toString());
								flag = true;
								break;
						}
					}
				} while (flag == false);
			}
			if (count == 3) {
				boolean flag = false;
				do {
					try {
						tempPrice = lineToFloat(st.nextToken());
						flag = true;
					}
					catch (AutoException e) {
						switch (e.getErrno()) {
							case 5:
								tempPrice = Float.parseFloat(e.fix(e.getErrno()).toString());
								flag = true;
								break;
						}
					}
				} while (flag == false);
				
			}
			if (count == 4) {
				String temp = st.nextToken();
				if (temp.contains("Hybrid")) {
					a1 = new Hybrid(tempMake, tempModel, tempYear, tempPrice, numOfOptSets, "20kWh");
				}
				else if (temp.contains("Truck")) {
					a1 = new Truck(tempMake, tempModel, tempYear, tempPrice, numOfOptSets);
				}
				else {
					a1 = new Automobile(tempMake, tempModel, tempYear, tempPrice, numOfOptSets);
				}
				
				try {
					if (DEBUG) {
						a1.getOptSet();
						System.out.println("Automotive object instantiated");
						System.out.println("Make: " + a1.getMake());
						System.out.println("Model: " + a1.getModel());
						System.out.println("Year: " + a1.getYear());
						System.out.println("Base price: $" + a1.getBasePrice());
						System.out.println("Number of config options: " + a1.getOptSet().size());
					}
				}
				catch (AutoException e) {
					switch (e.getErrno()) {
						case 101:
							a1.setMake(e.fix(101).toString());
							break;
						case 102:
							a1.setModel(e.fix(102).toString());
							break;
						case 103:
							a1.setBasePrice(Double.parseDouble(e.fix(103).toString()));
							break;
						case 104:
							a1.setOptSetLength(Integer.parseInt(e.fix(104).toString()));
							break;
						case 106:
							a1.setYear(Integer.parseInt(e.fix(106).toString()));
					}	
				}
			}
			if (count > 4) {
				String concatOpts = st.nextToken();
				int numOfOpt = countTokens(concatOpts, ";");
				a1 = buildOptSet(a1, (count - 5), concatOpts, numOfOpt);
			}
			count++;
		}
		
		return a1;
	}

	public int countOptions() throws AutoException {
		int lines = 0;
		try {
			FileReader f1 = new FileReader(fname);
			BufferedReader b1 = new BufferedReader(f1);
			boolean eof = false;
			while (!eof) {
				String line = b1.readLine();
				lines++;
				if (DEBUG)
					System.out.println("Reading: " + line);
				if (line == null) 
					eof = true;
			}
			if (lines < 6) 
				lines = 0;
			if (DEBUG) {
				if (lines < 6)
					System.out.println("Missing automotive properties");
				else {
					System.out.println("Lines successfully read: " + lines);
					System.out.println("Number of option sets detected: " + (lines - 5));
				}
			}
			b1.close();
		}
		catch (FileNotFoundException e) {
			throw new AutoException(1, "File not found");
		}
		catch (IOException e) {
			throw new AutoException(2, "Check I/O stream");
		}
		finally {
			
		}
		return (lines - 6);
	}
	
	public Automobile buildOptSet(Automobile a1, int optSetIndex, String line, int numOfTokens) {
		int token = 0;
		StringTokenizer st = new StringTokenizer(line, ";");
		String optName = st.nextToken();
		if (DEBUG)
			System.out.println("OptionSet Name read: " + optName);
		
		//Creating temporary Option Set
		OptionSet tempSet = new OptionSet(optName, numOfTokens);
		a1.setOneOptSet(tempSet, optSetIndex);
		
		//Creating Option for Option Set
		while (st.hasMoreTokens()) {
			String temp = st.nextToken();
			token++;
			if (DEBUG)
				System.out.println("Main token #" + token + " read: " + temp);
			boolean flag = false;
			do {
				try {
					a1 = buildOpt(a1, optSetIndex, token - 1, temp);
					flag = true;
				}
				catch (AutoException e) {
					switch (e.getErrno()) {
						case 5:
							a1.setOptPrice(optSetIndex, token - 1, Float.parseFloat(e.fix(e.getErrno()).toString()));
							flag = true;
							break;
					}
				}
			} while (flag == false);
		}
		return a1;
	}
	
	public Automobile buildOpt(Automobile a1, int optSetIndex, int optIndex, String frag) throws AutoException {
		int token = 0;
		StringTokenizer st = new StringTokenizer(frag, ",");
		
		//Reading Option name token
		String tempName = st.nextToken();
		a1.setOptName(optSetIndex, optIndex, tempName);
		token++;
		if (DEBUG)
			System.out.println("Sub token #" + token + " read: " + tempName);
		
		//Reading Option price token
		float tempPrice = 0;
		token++;
		try {
			tempPrice = Float.parseFloat(st.nextToken());
			a1.setOptPrice(optSetIndex, optIndex, tempPrice);
			if (DEBUG)
				System.out.println("Sub token #" + token + " read: " + tempPrice);
		}
		catch (NumberFormatException e) {
			throw new AutoException(5, "Invalid floating-point number");
		}
		finally {
			
		}
		return a1;
	}
	
	public int countTokens(String line, String delim) {
		int tokens = 0;
		StringTokenizer st = new StringTokenizer(line, delim);
		while (st.hasMoreTokens()) {
			String temp = st.nextToken();
			tokens++;
		}
		if (tokens <= 0)
			tokens = 0;
		else
			tokens -= 1;
		if (DEBUG) {
			System.out.println("Number of tokens: " + (tokens));
		}
		return tokens;
	}
	
	////////// METHODS TO READ PROPERTIES FILE //////////
	
	//Method to build Automobile object from properties file
	public Automobile buildAutoObject2(Automobile a1, String fname) throws AutoException {
		Properties props = new Properties();
		try {
			FileInputStream f1 = new FileInputStream(fname);
			props.load(f1);
		}
		catch (FileNotFoundException e) {
			throw new AutoException(1, "File not found");
		}
		catch (IOException e) {
			throw new AutoException(2, "Check I/O stream");
		}
		finally {
			String make, model;
			int year = 0, numOfOptSets;
			float basePrice = 0;
			
			//Read make and model
			make = props.getProperty("Make");
			model = props.getProperty("Model");
			
			//Read year
			boolean flag = false;
			do {
				try {
					year = lineToInt(props.getProperty("Year"));
					flag = true;
				}
				catch (AutoException e) {
					switch (e.getErrno()) {
						case 4:
							year = Integer.parseInt(e.fix(e.getErrno()).toString());
							flag = true;
							break;
					}
				}
			} while (flag == false);
			
			//Read base price
			flag = false;
			do {
				try {
					basePrice = lineToFloat(props.getProperty("BasePrice"));
					flag = true;
				}
				catch (AutoException e) {
					switch (e.getErrno()) {
						case 5:
							basePrice = Float.parseFloat(e.fix(e.getErrno()).toString());
							flag = true;
							break;
					}
				}
			} while (flag == false);
			
			
			//Count number of OptionSets
			for (numOfOptSets = 1; numOfOptSets < 100; numOfOptSets++) {
				String str = "OptionSet" + numOfOptSets;
				if (props.getProperty(str) == null)
					break;
			}
			numOfOptSets -= 1;
			
			//Determine Automobile type
			if (props.getProperty("Type") == "Hybrid") {
				a1 = new Hybrid(make, model, year, basePrice, numOfOptSets, "20kWh");
			}
			else if (props.getProperty("Type") == "Truck" ) {
				a1 = new Truck();
			}
			else {
				a1 = new Automobile(make, model, year, basePrice, numOfOptSets);
			}
			
			if (DEBUG) {
					System.out.println("Automotive object instantiated");
					System.out.println("Make: " + a1.getMake());
					System.out.println("Model: " + a1.getModel());
					System.out.println("Year: " + a1.getYear());
					System.out.println("Base price: $" + a1.getBasePrice());
					System.out.println("Number of config options: " + a1.getOptSet().size());
			}
			
			//Building OptionSets
			if (DEBUG)
				System.out.println("Building OptionSets ... ");
			
			for (int i = 1; i <= numOfOptSets; i++) {
				String optSetKey = "OptionSet" + i;
				String str1 = "OptionName" + i;
				String str2 = "OptionPrice" + i;
				int counter = 0;
				
				for (char ch = 'a'; ch <= 'z'; ch++) {
					String optNameKey = str1 + ch;
					String optPriceKey = str2 + ch;
					
					if (props.getProperty(optNameKey) == null || props.getProperty(optPriceKey) == null)
						break;
					
					counter++;
				}
				
				if (DEBUG) 
					System.out.println("Number of Options detected: " + counter);
				
				if (DEBUG) {
					System.out.print("Reading value of " + optSetKey + " ... ");
					System.out.println(props.getProperty(optSetKey));
				}
				a1.setOneOptSet((new OptionSet(props.getProperty(optSetKey), counter)), (i - 1));
				
				int j = 0;
				for (char ch = 'a'; ch <= 'z'; ch++) {
					if (DEBUG)
						System.out.println("Building Options ... ");
					
					String optNameKey = str1 + ch;
					String optPriceKey = str2 + ch;
					
					if (props.getProperty(optNameKey) == null || props.getProperty(optPriceKey) == null)
						break;
					
					if (DEBUG)
						System.out.print("Reading " + optNameKey + " ... ");
					String tempOptName = props.getProperty(optNameKey);
					if (DEBUG)
						System.out.println(tempOptName);
					
					if (DEBUG)
						System.out.print("Reading " + optPriceKey + " ... ");
					float tempOptPrice = Float.parseFloat(props.getProperty(optPriceKey));
					if (DEBUG)
						System.out.println(tempOptPrice);
					
					a1.setOneOptSetOpt((i - 1), j, tempOptName, tempOptPrice);
					j++;
				}
				
			}
			
		}
		
		return a1;
	}
	
	//Method to build Automobile object with properties object
	public Automobile buildAutoObject2(Automobile a1, Object file) {
		Properties props = (Properties) file;
		
		String make, model;
		int year = 0, numOfOptSets;
		float basePrice = 0;
		
		//Read make and model
		make = props.getProperty("Make");
		model = props.getProperty("Model");
		
		//Read year
		boolean flag = false;
		do {
			try {
				year = lineToInt(props.getProperty("Year"));
				flag = true;
			}
			catch (AutoException e) {
				switch (e.getErrno()) {
					case 4:
						year = Integer.parseInt(e.fix(e.getErrno()).toString());
						flag = true;
						break;
				}
			}
		} while (flag == false);
		
		//Read base price
		flag = false;
		do {
			try {
				basePrice = lineToFloat(props.getProperty("BasePrice"));
				flag = true;
			}
			catch (AutoException e) {
				switch (e.getErrno()) {
					case 5:
						basePrice = Float.parseFloat(e.fix(e.getErrno()).toString());
						flag = true;
						break;
				}
			}
		} while (flag == false);
		
		
		//Count number of OptionSets
		for (numOfOptSets = 1; numOfOptSets < 100; numOfOptSets++) {
			String str = "OptionSet" + numOfOptSets;
			if (props.getProperty(str) == null)
				break;
		}
		numOfOptSets -= 1;
		
		//Determine Automobile type
		if (props.getProperty("Type") == "Hybrid") {
			a1 = new Hybrid(make, model, year, basePrice, numOfOptSets, "20kWh");
		}
		else if (props.getProperty("Type") == "Truck" ) {
			a1 = new Truck();
		}
		else {
			a1 = new Automobile(make, model, year, basePrice, numOfOptSets);
		}
		
		try {
			if (DEBUG) {
				a1.getOptSet();
				System.out.println("Automotive object instantiated");
				System.out.println("Make: " + a1.getMake());
				System.out.println("Model: " + a1.getModel());
				System.out.println("Year: " + a1.getYear());
				System.out.println("Base price: $" + a1.getBasePrice());
				System.out.println("Number of config options: " + a1.getOptSet().size());
			}
		}
		catch (AutoException e) {
			switch (e.getErrno()) {
				case 101:
					a1.setMake(e.fix(101).toString());
					break;
				case 102:
					a1.setModel(e.fix(102).toString());
					break;
				case 103:
					a1.setBasePrice(Double.parseDouble(e.fix(103).toString()));
					break;
				case 104:
					a1.setOptSetLength(Integer.parseInt(e.fix(104).toString()));
					break;
				case 106:
					a1.setYear(Integer.parseInt(e.fix(106).toString()));
			}			
		}
		
		//Building OptionSets
		if (DEBUG)
			System.out.println("Building OptionSets ... ");
		
		for (int i = 1; i <= numOfOptSets; i++) {
			String optSetKey = "OptionSet" + i;
			String str1 = "OptionName" + i;
			String str2 = "OptionPrice" + i;
			int counter = 0;
			
			for (char ch = 'a'; ch <= 'z'; ch++) {
				String optNameKey = str1 + ch;
				String optPriceKey = str2 + ch;
				
				if (props.getProperty(optNameKey) == null || props.getProperty(optPriceKey) == null)
					break;
				
				counter++;
			}
			
			if (DEBUG) 
				System.out.println("Number of Options detected: " + counter);
			
			if (DEBUG) {
				System.out.print("Reading value of " + optSetKey + " ... ");
				System.out.println(props.getProperty(optSetKey));
			}
			a1.setOneOptSet((new OptionSet(props.getProperty(optSetKey), counter)), (i - 1));
			
			int j = 0;
			for (char ch = 'a'; ch <= 'z'; ch++) {
				if (DEBUG)
					System.out.println("Building Options ... ");
				
				String optNameKey = str1 + ch;
				String optPriceKey = str2 + ch;
				
				if (props.getProperty(optNameKey) == null || props.getProperty(optPriceKey) == null)
					break;
				
				if (DEBUG)
					System.out.print("Reading " + optNameKey + " ... ");
				String tempOptName = props.getProperty(optNameKey);
				if (DEBUG)
					System.out.println(tempOptName);
				
				if (DEBUG)
					System.out.print("Reading " + optPriceKey + " ... ");
				float tempOptPrice = Float.parseFloat(props.getProperty(optPriceKey));
				if (DEBUG)
					System.out.println(tempOptPrice);
				
				a1.setOneOptSetOpt((i - 1), j, tempOptName, tempOptPrice);
				j++;
			}
			
		}
		
		return a1;
	}
	
	////////// METHODS FOR SERIALIZATION //////////
	
	public String writeData(Automobile a1) throws AutoException {
		boolean flag = false;
		
		//Checking if Automobile object is null
		do {
			try {
				checkNull(a1);
				flag = true;
			}
			catch (AutoException e) {
				switch (e.getErrno()) {
					case 6:
						a1 = (Automobile) e.fix(e.getErrno());
						break;
				}
			}
			finally {
				
			}
		} while (flag == false);
		
		StringBuffer sbuff = new StringBuffer(a1.getAutoKey());
		sbuff.append(".dat");
		String serfname = sbuff.toString();
		
		//Serialize and return serfname
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(serfname));
			if (DEBUG) {
				System.out.println(serfname + " created");
			}
			out.writeObject(a1);
			if (DEBUG) {
				a1.printData();
				System.out.println("Data successfully written");
			}
			out.close();	
		}
		catch (IOException e){
			throw new AutoException(2, "Check I/O stream");
		}
		finally {
			
		}
		return serfname;
	}
	
	public Automobile readData(String serfname) throws AutoException {
		Automobile newa1 = null;
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(serfname));
			if (DEBUG) {
				System.out.println("Reading " + serfname);
			}
			newa1 = (Automobile) in.readObject();
			if (DEBUG) {
				newa1.printData();
				System.out.println("Data successfully read");
			}
			in.close();
		}
		catch (ClassNotFoundException e) {
			throw new AutoException(3, "Object not found");
		}
		catch (FileNotFoundException e) {
			throw new AutoException(1, "File not found");
		}
		catch (IOException e) {
			throw new AutoException(2, "Check I/O stream");
		}
		finally {
			
		}
		return newa1;
	}
	
	////////// HELPERS //////////
	
	public void checkNull(Object a1) throws AutoException {
		if (a1 == null)
			throw new AutoException(6, "Empty object detected");
	}

	public float lineToFloat(String line) throws AutoException {
		float temp = 0;
		try {
			temp = Float.parseFloat(line);
		}
		catch (NumberFormatException e) {
			throw new AutoException(5, "Invalid floating-point number");
		}
		finally {
			
		}
		return temp;
	}
	
	public int lineToInt(String line) throws AutoException {
		int temp = 0;
		try {
			temp = Integer.parseInt(line);
		}
		catch (NumberFormatException e) {
			throw new AutoException(4, "Invalid integer number");
		}
		return temp;
	}
	
	public boolean isPropertiesFile(Object file) {
		boolean flag = false;
		try {
			Properties props = (Properties) file;
			flag = true;
		}
		catch (ClassCastException e) {
			flag = false;
		}
		
		return flag;
	}
	
	public boolean isTextFile(Object file) {
		boolean flag = false;
		try {
			StringBuffer str = (StringBuffer) file;
			flag = true;
		}
		catch (ClassCastException e) {
			flag = false;
		}
		
		return flag;
	}
	
}
