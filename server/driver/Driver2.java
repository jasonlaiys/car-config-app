
package driver;

import adapter.*;

public class Driver2 {
	public static void main(String[] args) {
		AutoCreatable auto = new BuildAuto();
		auto.buildAuto("random.txt");
		//auto.printAuto();
		AutoUpdatable change = new BuildAuto();
		//change.updateOptionPrice("Transmission", "Manual", -900);
		//auto.printAuto();
		//change.updateOptionPrice("Transmission", "Dual Clutch", 500);
		//change.updateOptionPrice("Gear", "Manual", -900);
		//change.updateOptionSetName("Transmission", "Gearing");
		AutoFixable fix = new BuildAuto();
		//fix.fix(104);
	}
}
