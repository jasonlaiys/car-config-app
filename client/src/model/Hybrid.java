
package model;

public class Hybrid extends Automobile {
	
	////////// PROPERTIES //////////
	
	private String batteryCapacity;
	
	////////// CONSTRUCTORS //////////
	
	public Hybrid() {
		super();
	}
	
	public Hybrid(String make, String model, int year, double x, int y, String batteryCapacity) {
		super(make, model, year, x, y);
		this.batteryCapacity = batteryCapacity;
	}

	////////// GETTERS AND SETTERS //////////
	
	public String getBatteryCapacity() {
		return batteryCapacity;
	}

	public void setBatteryCapacity(String batteryCapacity) {
		this.batteryCapacity = batteryCapacity;
	}
	
	////////// PRINT METHODS //////////
	
	public void printData() {
		super.printData();
		System.out.println("Battery capacity: " + batteryCapacity);
	}
	
}
