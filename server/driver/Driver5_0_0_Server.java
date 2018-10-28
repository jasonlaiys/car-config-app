
package driver;

import adapter.*;
import server.*;

public class Driver5_0_0_Server {
	
	public static void main(String[] args) {
		AutoServable host = new BuildAuto();
		host.serve(6666);
	}
	
}
