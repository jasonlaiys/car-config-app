
package scale;

import model.*;
import adapter.*;

public class EditOptions extends ProxyAutomobile implements Runnable {

	////////// PROPERTIES //////////
	private Thread t;
	private int threadno;
	private int opnumber;
	private static boolean isUpdating = false;
	private String model;
	private String optSetName;
	private String newSetName;
	
	////////// CONSTRUCTORS //////////
	
	public EditOptions() {
		
	}
	
	public EditOptions(int x, int y) {
		t = new Thread(this);
		this.threadno = x;
		this.opnumber = y;
	}
	
	public EditOptions(int x, int y, String model, String optSetName, String newSetName) {
		t = new Thread(this);
		this.threadno = x;
		this.opnumber = y;
		this.model = model;
		this.optSetName = optSetName;
		this.newSetName = newSetName;
	}
	
	////////// INSTANCE METHODS //////////
	
	@Override
	public void updateOptionSetName(String model, String optSetName, String newSetName) {
		OptionSet optSet = super.findAuto(model).findOptionSet(optSetName);
		synchronized (optSet) {
			while (isUpdating) {
				try {
					System.out.println("Thread " + threadno + " waiting ... ");
					wait();
					
				}
				catch (InterruptedException e) {
					System.out.println("Thread " + threadno + " interrupted!");
				}
			}
			
			isUpdating = true;
			System.out.println("Thread " + threadno + " updating OptionSet name ... ");
			super.updateOptionSetName(model, optSetName, newSetName);
			
			isUpdating = false;
			System.out.println("Thread " + threadno + " finished updating OptionSet name ... ");
			
			notifyAll();
			System.out.println("All other threads notified");
		}	
	}
	
	public void run() {
		System.out.println("Starting thread " + threadno + " ... ");
		System.out.println("Thread " + threadno + " executing operation " + opnumber + " ... ");
		switch (opnumber) {
			case 0:
				super.updateOptionSetName(model, optSetName, newSetName);
				break;				
			case 1:
				updateOptionSetName(model, optSetName, newSetName);
				break;
			default:
				System.out.println("Invalid option number");
				break;
		}
		System.out.println("Thread " + threadno + " finishing operation " + opnumber + " ... ");
		System.out.println("Stopping thread " + threadno + " ... ");
		
		t.interrupt();
		return;
	}
	
	public void start() {
		t.start();
	}

}
