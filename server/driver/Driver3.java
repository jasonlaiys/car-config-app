
package driver;

import adapter.*;

public class Driver3 {
	public static void main(String[] args) {
		AutoCreatable auto = new BuildAuto();
		auto.buildAuto("C:\\Users\\lyshe\\Documents\\De Anza\\Winter 2018\\CIS 35B 62Y\\Assignments\\Car Config App Prototype\\src\\Ford_Focus_Wagon_ZTW.txt");
		auto.buildAuto("C:\\Users\\lyshe\\Documents\\De Anza\\Winter 2018\\CIS 35B 62Y\\Assignments\\Car Config App Prototype\\src\\Toyota_Prius.txt");
		auto.printAuto("Focus Wagon ZTW");
		auto.printAuto("Prius");
		AutoUpdatable change = new BuildAuto();
		change.updateOptionPrice("Focus Wagon", "Transmission", "Manual", -900);
		auto.printAuto("Focus Wagon");
		change.updateOptionPrice("Focus Wagon", "Transmission", "Dual Clutch", 500);
		AutoConfigurable config = new BuildAuto();
		config.chooseOption("Focus", "Brakes", "ABS w/ Advanced Trac");
		config.chooseOption("Prius", "Color", "Red");
		config.chooseOption("Focus", "Power Moonroof", "Power Moonroof");
		config.calculateOptionTotal("Focus");
		config.calculateOptionTotal("Prius");
		change.updateOptionPrice("Civic", "Transmission", "Dual Clutch", 1200);
	}
}
