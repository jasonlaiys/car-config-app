
package client;

import java.net.*;
import java.io.*;
import adapter.Debuggable;
import model.*;

public class DefaultSocketClient extends Thread implements Debuggable {
	
	////////// PROPERTIES //////////
	
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private BufferedReader stdIn;
	private Socket sock;
	private String strHost;
	private int iPort;
	private CarModelOptionsIO clientFTP;
	private SelectCarOptions clientProtocol;
	
	////////// CONSTRUCTORS //////////
	
	public DefaultSocketClient(String strHost, int iPort) {
		this.strHost = strHost;
		this.iPort = iPort;
	}
	
	////////// INSTANCE METHODS //////////
	
	public void establishConnection() {
		try {
			if (DEBUG)
				System.out.println("Connecting to host ... ");
			this.sock = new Socket(strHost, iPort);
			
			if (DEBUG)
				System.out.println("Connected to host, creating object streams ... ");
			out = new ObjectOutputStream(sock.getOutputStream());
			in = new ObjectInputStream(sock.getInputStream());
			stdIn = new BufferedReader(new InputStreamReader (System.in));
			
			clientFTP = new CarModelOptionsIO();
			clientProtocol = new SelectCarOptions();
		}
		catch (IOException e) {
			System.err.println("Error obtaining I/O for connection to host ... ");
			System.exit(1);
		}
	}
	
	public void handleConnection() {
		Object fromServer, toServer = null;
		
		try {
			if (DEBUG)
				System.out.println("Communicating with host ... ");
			
			while ((fromServer = in.readObject()) != null) {
				if (DEBUG)
					System.out.println("Received server response ... ");
				System.out.println(fromServer.toString());
				
				if (clientProtocol.isAutomobile(fromServer))
					clientProtocol.configureAuto(fromServer);
				
				System.out.println("Response to server: ");
				toServer = stdIn.readLine();
				if (toServer.toString().contains(".prop")) {
					toServer = clientFTP.loadPropsFile(toServer.toString());
				}
				if (toServer.toString().contains(".txt")) {
					toServer = clientFTP.loadTextFile(toServer.toString());
				}
				if (DEBUG)
					System.out.println("Sending " + toServer + " to server ... ");
				sendOutput(toServer);
				System.out.println("");
				
				if (toServer.equals("0")) {
					break;
				}
			}
			
			if (DEBUG)
				System.out.println("Closing client input stream ... ");
			in.close();
			
		}
		catch (ClassNotFoundException e) {
			System.err.println("Error in downloaded object, object may be corrupted ... ");
			System.exit(1);
		}
		catch (IOException e) {
			System.err.println("Error in I/O stream ... ");
			System.exit(1);
		}
		
	}
	
	//Method for web server to get a list of automobiles
	public String[] getModels() {
		Object fromServer, toServer = null;
		String[] models = null;
		
		establishConnection();
		
		try {
			if (DEBUG)
				System.out.println("Obtaining car models from host ... ");
			
			fromServer = in.readObject();
			toServer = "2";
			sendOutput(toServer);
			fromServer = in.readObject();
			String autoList = fromServer.toString();
			int count = 0;
			
			if (DEBUG)
				System.out.println("Counting number of models ... ");
			for (int i = 1; i > 0; i++) {
				String marker = i + ". ";
				int beginIndex = autoList.indexOf(marker) + marker.length();
				int endIndex = autoList.indexOf("\n", beginIndex);
				if ((beginIndex - marker.length()) == -1)
					break;
				count++;
			}
			
			if (DEBUG)
				System.out.println("Parsing models into array ... ");
			models = new String[count];
			for (int i = 0; i < count; i++) {
				String marker = (i + 1) + ". ";
				int beginIndex = autoList.indexOf(marker) + marker.length();
				int endIndex = autoList.indexOf("\n", beginIndex);
				models[i] = autoList.substring(beginIndex, endIndex);
			}
			
			sendOutput("0");
			fromServer = in.readObject();
			sendOutput(" ");
			fromServer = in.readObject();
			sendOutput("0");
			
			if (DEBUG)
				System.out.println("Closing client input stream ... ");
			in.close();
			out.close();
			stdIn.close();
			sock.close();
		}
		catch (ClassNotFoundException e) {
			System.err.println("Error in downloaded object, object may be corrupted ... ");
			System.exit(1);
		}
		catch (IOException e) {
			System.err.println("Error in I/O stream ... ");
			System.exit(1);
		}
		
		return models;
	}
	
	//Method for web server to get automobile
	public Automobile getAutomobile(String choice) {
		if (DEBUG)
			System.out.println(choice);
		
		Automobile a1 = null;
		
		Object fromServer, toServer = null;
		
		establishConnection();
		
		try {
			fromServer = in.readObject();
			toServer = "2";
			sendOutput(toServer);
			
			fromServer = in.readObject();
			toServer = choice;
			sendOutput(toServer);
			
			fromServer = in.readObject();
			a1 = (Automobile) fromServer;
			toServer = " ";
			sendOutput(toServer);
			
			fromServer = in.readObject();
			toServer = "0";
			sendOutput(toServer);
			
			if (DEBUG)
				System.out.println("Closing client input stream ... ");
			in.close();
			out.close();
			stdIn.close();
			sock.close();
		}
		catch (ClassNotFoundException e) {
			System.err.println("Error in downloaded object, object may be corrupted ... ");
			System.exit(1);
		}
		catch (IOException e) {
			System.err.println("Error in I/O stream ... ");
			System.exit(1);
		}
		
		return a1;
	}
	
	public void sendOutput(Object obj) {
		try {
			out.writeObject(obj);
		}
		catch (IOException e) {
			System.err.println("Error in I/O stream while sending object to host ... ");
			System.exit(1);
		}
	}
	
	@Override
	public void run() {
		establishConnection();
		handleConnection();
		try {
			if (DEBUG)
				System.out.println("Closing client output stream ... ");
			out.close();
			
			if (DEBUG)
				System.out.println("Closing client console input stream ... ");
			stdIn.close();
			
			if (DEBUG)
				System.out.println("Closing client socket ... ");
			sock.close();
		}
		catch (IOException e) {
			System.err.println("Error ending client connection ... ");
			System.exit(1);
		}
	}

}
