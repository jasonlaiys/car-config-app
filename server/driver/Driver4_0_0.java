
package driver;

import adapter.*;
import scale.*;

//Driver to test non-synchronized operation 
public class Driver4_0_0 {
	public static void main(String[] args) {
		AutoCreatable auto = new BuildAuto();
		auto.buildAuto("C:\\Users\\lyshe\\Documents\\De Anza\\Winter 2018\\CIS 35B 62Y\\Assignments\\Car Config App Prototype\\src\\Ford_Focus_Wagon_ZTW.txt");
		ThreadEditable thread0 = new BuildAuto();
		ThreadEditable thread1 = new BuildAuto();
		thread0.startThread(0, 0, "Focus Wagon", "Transmission", "Gearing");
		thread1.startThread(1, 0, "Focus Wagon", "Transmission", "Gear Type");
	}
}
