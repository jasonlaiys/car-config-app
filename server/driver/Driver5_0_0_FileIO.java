
package driver;

import adapter.*;

public class Driver5_0_0_FileIO {
	public static void main(String[] args) {
		AutoCreatable auto = new BuildAuto();
		auto.buildAuto("C:\\Users\\lyshe\\Documents\\De Anza\\Winter 2018\\CIS 35B 62Y\\Assignments\\Car Config App Prototype\\src\\Ford_Focus_Wagon_ZTW.prop");
		auto.printAuto("Focus Wagon ZTW");
	}
}
