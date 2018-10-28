
package client;

import model.*;
import java.util.*;

public class SelectCarOptions {

	////////// PROPERTIES //////////
	private Scanner in = new Scanner(System.in);
	
	////////// CONSTRUCTORS //////////
	
	public SelectCarOptions() {
		
	}
	
	////////// INSTANCE METHODS //////////
	
	public void configureAuto(Object obj) {
		Automobile a1 = (Automobile) obj;
		
		System.out.println("Configuring " + a1.getMakeModelYear() + ": ");
		for (int i = 0; i < a1.getOptSetLength(); i++) {
			System.out.println("Which option do you want for " + a1.getOptSetName(i) + "?");
			a1.printOneOptSet(i);
			
			System.out.print("Enter your choice: ");
			int choice = in.nextInt();
			
			a1.setOptChoice(i, (choice - 1));
			
			System.out.println("");
		}
		
		System.out.println("You configured your " + a1.getMakeModelYear() + " to have: ");
		a1.printAllOptChoice();
		System.out.println("Your " + a1.getMakeModelYear() + " configuration costs $" + a1.getTotalChoicePrice());
		System.out.println("");
		System.out.println("Press any key to return to main menu");
	}
	
	public boolean isAutomobile(Object obj) {
		boolean isAutomobile = false;
		
		try {
			Automobile a1 = (Automobile) obj;
			isAutomobile = true;
		}
		catch (ClassCastException e) {
			isAutomobile = false;
		}
		
		return isAutomobile;
	}
	
}
