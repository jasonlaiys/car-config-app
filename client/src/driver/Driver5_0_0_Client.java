
package driver;

import client.*;

public class Driver5_0_0_Client {
	
	public static void main(String[] args) {
		DefaultSocketClient client = new DefaultSocketClient("192.168.1.207", 6666);
		client.start();
	}

}
