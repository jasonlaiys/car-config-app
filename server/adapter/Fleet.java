
package adapter;

import java.util.*;
import model.*;

public class Fleet<T extends Automobile> implements Debuggable {
	
	////////// PROPERTIES //////////
	
	private LinkedHashMap<String, Automobile> auto = new LinkedHashMap<String, Automobile>();
	
	////////// CONSTRUCTORS //////////
	
	public Fleet() {
		
	}
	
	////////// GETTERS AND SETTERS //////////
	
	public LinkedHashMap<String, Automobile> getLHM() {
		return this.auto;
	}
	
	public Automobile getElement(String key) {
		return auto.get(key);
	}
	
	public void setElement(String key, Automobile a1) {
		this.auto.put(key, a1);
		if (DEBUG) {
			if (auto.get(a1.getAutoKey()) != null) {
				System.out.println("New Automobile object added to collection");
			}
			else {
				System.out.println("Automobile object failed to be added to collection");
			}
		}
	}
	
	////////// INSTANCE METHODS //////////
	
	public void replaceElement(String key, Automobile a1) {
		this.auto.replace(key, a1);
		if (DEBUG) {
			if (auto.get(key) == a1) {
				System.out.println("Automobile object successfully replaced");
			}
			else {
				System.out.println("Automobile object failed to be replaced");
			}
		}
	}
	
	public void deleteElement(String key) {
		this.auto.remove(key);
		if (DEBUG) {
			if (auto.get(key) == null) {
				System.out.println("Automobile object successfully deleted from collection");
			}
			else {
				System.out.println("Automobile object failed to be deleted from collection");
			}
		}
	}
	
	public void clearAllElements() {
		this.auto.clear();
		System.out.println("Collection cleared. All Automobile objects successfully deleted");
	}
	
}
