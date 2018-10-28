
package server;

import adapter.*;

public class BuildCarModelOptions extends ProxyAutomobile {
	
	////////// PROPERTIES //////////
	
	private static final int WAITING = 0;
	private static final int REQUEST_BUILD_AUTO = 1;
	private static final int REQUEST_CONFIGURE_AUTO = 2;
	
	private int state = WAITING;
	
	////////// CONSTRUCTORS //////////
	
	public BuildCarModelOptions() {
		
	}
	
	////////// INSTANCE METHODS //////////
	
	public Object processRequest(Object obj) {
		Object toClient = null;
		
		if (state == REQUEST_BUILD_AUTO) {
			super.buildAuto(obj);
			toClient = "Automobile object successfully added to database\n"
					+ "Press any key to return to main menu";
		}
		else if (state == REQUEST_CONFIGURE_AUTO) {
			String marker = obj.toString() + ". ";
			int beginIndex = super.getAllModels().indexOf(marker) + marker.length();
			int endIndex = super.getAllModels().indexOf("\n", beginIndex);
			String model = super.getAllModels().substring(beginIndex, endIndex);
			toClient = super.findAuto(model);
			System.out.println(model);
			System.out.println(toClient);
		}
		else {
			
		}
		
		this.state = WAITING;
		
		return toClient;
	}
	
	public String setRequest(int i) {
		String output = null;
		
		if (i == 1) {
			this.state = REQUEST_BUILD_AUTO;
			output = "Upload a file to create an Automobile";
		}
		else if (i == 2) {
			this.state = REQUEST_CONFIGURE_AUTO;
			output = "Select an Automobile from the following list to configure: \n" +
					super.getAllModels();
		}
		else {
			output = "Invalid request";
		}
		
		return output;
	}
	
	

}
