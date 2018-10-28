
========== HOW TO RUN ==========
Pretext:
	This project has been configured to run on the web container Tomcat 7. For optimal results please install Tomcat 7 or newer.
--------------------------------------
Instructions:
	1. Place src-server and Car Config App Web Client folders in a directory on your computer and import src-server as a regular Eclipse project and Car Config App Web Client as a Web Project with IDE of your choice.
	2. Car Config App Web Client has been configured to deploy on Tomcat 7 within Eclipse. No further configuration required if Tomcat 7 has been installed in your version of Eclipse.
	3. To view server-side or client-side debugging process on console, change DEBUG property in adapter.Debuggable in both server and client projects respectively to true.
	4. You may change server socket port number to a number of your liking in line 12 of src-server.driver.Driver5_0_0_Server.
	5. Before running the client you must replace the IP address in these locations with the server machine's IP address and corresponding port number:
		i) line 10 of Web Client.src.driver.Driver5_0_0_Client 
		ii) line 36 of Web Client.src.coreservlets.ModelListServlet 
		iii) line 37 of Web Client.src.coreservlets.ModelAttributeSelection
	6. To find out the server machine's IP address,
		for Windows: open cmd and enter the command ipconfig and use the string of numbers labeled IPv4 Address
	7. Run (Ctrl + F11) Driver5_0_0_Server first, then Driver5_0_0_Client.
	8. Follow instructions on console to upload Automobile to server, configure an existing Automobile, or exit program.
	9. After uploading Automobiles, there are two ways to configure the Automobile through a Web GUI:
		With Tomcat 7 and Eclipse browser:
			i) Run (Ctrl + F11) ModelListServlet and choose to deploy on Tomcat instead of the regular Eclipse console in the pop-up.
			ii) Follow along the Graphical User Interface in the web page to configure an Automobile within the database.
		With Tomcat 7 and a browser of your choice:
			i) Deploy Tomcat within Eclipse.
			ii) Go to the URL http://localhost:8080/Car_Config_App_Web_Client/ModelListServlet
			iii) Follow along the Graphical User Interface in the web page to configure an Automobile within the database.
--------------------------------------
Warning:
	Do not shut down the server unless you are done, you will have to reupload the Automobile objects if you do.

========== DESIGN STRATEGY ==========
Stage 1:
	A driver package for driver file, util package to handle FileIO, and model package for objects.
	Program reads file to determine how many lines of data, then passes value to instantiate OptionSet and Option array.
	Values in Driver1.java are hard-coded.
	Ford_Focus_Wagon_ZTW.txt and Toyota_Prius.txt included as sample data for testing purposes.
--------------------------------------
Stage 2:
	An adapter package is added for interfaces, one abstract class, and one concrete class for external part of program.
	An exception package is added for handling exceptions. Two helper classes are included, one to handle exceptions in util package and another to handle exceptions in model package.
	FixAuto interface is implemented to be able to fix a null array in Automobile class.
	Values in Driver2.java are hard-coded.
	AutoException class and its helpers is able to fix 6 different exceptions in util package, namely
		1. FileNotFoundException
		2. IOException
		3. ClassNotFoundException
		4. NumberFormatException for type int
		5. NumberFormatException for type float
		6. NullPointerException for empty/missing Automobile object
	and also 5 different exceptions in model package, namely
		1. NullPointerException for empty/missing property 'make' in Automobile
		2. NullPointerException for empty/missing property 'model' in Automobile
		3. NullPointerException for empty/missing property 'basePrice' in Automobile
		4. NullPointerException for empty/missing property 'optSet' in Automobile
		5. ArrayIndexOutOfBoundsException for property 'optSet' in Automobile
	Included 3 text files for 3 different test case scenarios
	Included error log text file for reference
	Included class diagram for reference
--------------------------------------
Stage 3:
	Debuggable interface added into adapter package containing DEBUG flag.
	Refactored Option to be a top-level class.
	Refactored CreateAuto to be AutoCreatable, UpdateAuto to be AutoUpdatable, and FixAuto to be AutoFixable.
	Updated property option in OptionSet and optionset in Automobile to be of type ArrayList<Option> and ArrayList<OptionSet> respectively.
	Added property choice of type Option in OptionSet and property choice of type ArrayList<Option> in Automobile.
	Added a template Fleet<T extends Automobile> that contains a collection of Automobiles. LinkedHashMap<String, Automobile> is used to store values.
	Added two child classes extending Automobile, Hybrid and Truck, for added functionality.
	Added AutoConfigurable interface to allow users to configure a car via ProxyAutomobile.
	Updated AutoCreatable, AutoUpdatable, and AutoFixable to search Fleet.LinkedHashMap<String, Automobile> for a specific model before performing function.
	Modified parser to take in values for year of make and Automobile type, e.g. Hybrid, Truck, or default.
	Added exception handling for exception type NullPointerException for property 'year' in Automobile.
	Values in Driver3.java are hard-coded.
	Included 3 text files for 3 different test case scenarios.
	Included error log text file for reference.
	Included class diagram for reference.
--------------------------------------
Stage 4:
	Added new package scale for multithreading capabilities.
	Added new class EditOptions in package scale to handle multithreaded CRUD operations.
	Added new interface ThreadEditable in package scale to be implemented in ProxyAutomobile for starting threaded CRUD operations.
	EditOptions extends ProxyAutomobile for access to Fleet<T extends Automobile> which contains LinkedHashMap.
	Added two drivers, Driver4_0_0 for testing non-synchronized operation and Driver4_0_1 for testing synchronized operation.
	Values in both drivers are hard-coded.
	Included sample text files for testing.
	Included error log text file for reference.
	Included class diagram for reference.
	Included below, test results and their explanation for reference and clarification.
--------------------------------------
Stage 5:
	Added new functionality in util.FileIO to be able to read properties files.
	Created two separate projects, one for server (src-server) and one for client (src-client).
	Client project contains client functionality in client package.
	Server project contains server functionality in server package.
	Package client contains DefaultSocketClient class to create client socket and handle connection with server and CarModelOptionsIO class and SelectCarOptions class as helpers to DefaultSocketClient.
	Package server contains AutoServable interface to launch server, DefaultServerSocket class to host the ServerSocket, DefaultSocketClient class to handle client connection, and BuildCarModelOptions class as a helper to DefaultSocketClient.
	Client driver is hard-coded to create socket and establish connection with server.
	Server driver launches server through AutoServable API implemented in ProxyAutomobile.
	Server-side class structure:
		API to launch server is implemented in ProxyAutomobile as method serve()
		DefaultServerSocket extends Thread for multithreading capabilities and implements Debuggable for debugging capabilities.
		When serve() is invoked, start() method for DefaultServerSocket is called.
		DefaultServerSocket will accept a client socket via the accept() method and pass the client to an instance of DefaultSocketClient in its own thread.
		DefaultSocketClient will handle client-server interaction with the help of BuildCarModelOptions that extends ProxyAutomobile for access to the LinkedHashMap.
	Client-side class structure:
		Driver instantiates an instance of DefaultSocketClient and establishes connection with server.
		CarModelOptionsIO acts as a file transfer helper for DefaultSocketClient, loading properties and text files before serializing to server.
		SelectCarOption acts as a configurator helper for DefaultSocketClient, setting user choice for automobile configuration.
	Included 2 sample text files and 3 sample properties file in client project for testing.
	Included error log text file for reference.
	Included class diagram for reference.
--------------------------------------
Stage 6:
	Added web functionality with usage of servlets in coreservlets package within Web Client project.
	Added web functionality with usage of JSPs in WebContent folder within Web Client project.
	Web Client is designed and structured to run best on Tomcat 7.
	Web Client project inherits all previous command-line server-client functionalities of Client project.
	Web Client project now holds only the bare minimum and necessary files for a client to operate.
	2 servlets are used for obtaining the list of available models and displaying the available options once a model is selected.
	1 JSP is used to display user choices for a particular Automobile after configuration.
	Driver files from previous units (Driver5_0_0_Server and Dirver5_0_0_Client) are reused for testing in this unit.
	Backward compatibility supported. Configuring an Automobile via console UI is still an option.
	Included 3 car models in properties and text file formats in web client project for testing.
	Included error log text file for reference.
	Included class diagram for reference.
	Included screenshots of web pages for reference.
--------------------------------------

========== TESTING RESULTS ==========
--------------------------------------
---------- TESTING SCENARIO ----------
--------------------------------------
Driver5_0_0_Server.java
Server-side response
Hard-coded values
DEBUG flag on
--------------------------------------
/192.168.1.207
Creating server object streams ... 
Sending client interaction choices ... 
Reading client request ... 
1
Sending client request follow-up ... 
Waiting for client to upload file ... 
{OptionName4a=No Side Impact Air Bags, BasePrice=18445, OptionName4b=Side Impact Air Bags, OptionPrice3c=1625, OptionPrice3a=0, OptionPrice3b=400, OptionName3a=Standard, OptionName3b=ABS, OptionPrice4b=350, OptionPrice4a=0, Make=Ford, OptionName3c=ABS w/ Advanced Trac, OptionPrice1c=0, OptionPrice1d=0, OptionPrice1a=0, OptionName2a=Automatic, OptionPrice1b=0, OptionPrice5a=0, OptionPrice5b=595, OptionPrice1i=0, OptionPrice1j=0, OptionPrice1g=0, OptionPrice1h=0, OptionPrice1e=0, OptionPrice1f=0, OptionName2b=Manual, OptionName5a=No Power Moonroof, OptionName5b=Power Moonroof, OptionPrice2b=-815, OptionPrice2a=0, OptionName1i=Pitch Black Clearcoat, OptionSet1=Color, Year=2017, OptionSet2=Transmission, OptionName1j=Cloud 9 White Clearcoat, OptionSet3=Brakes, OptionSet4=Side Impact Air Bags, OptionSet5=Power Moonroof, OptionName1e=Sangria Red Clearcoat Metallic, Model=Focus Wagon ZTW, OptionName1f=French Blue Clearcoat Metallic, OptionName1g=Twilight Blue Clearcoat Metallic, OptionName1h=CD Silver Clearcoat Metallic, OptionName1a=Fort Knox Gold Clearcoat Metallic, OptionName1b=Liquid Grey Clearcoat Metallic, OptionName1c=Infra-Red Clearcoat, OptionName1d=Grabber Green Clearcoat Metallic}
Adding new Automobile to database ... 
Automotive object instantiated
Make: Ford
Model: Focus Wagon ZTW
Year: 2017
Base price: $18445.0
Number of config options: 5
Building OptionSets ... 
Number of Options detected: 10
Reading value of OptionSet1 ... Color
Building Options ... 
Reading OptionName1a ... Fort Knox Gold Clearcoat Metallic
Reading OptionPrice1a ... 0.0
Building Options ... 
Reading OptionName1b ... Liquid Grey Clearcoat Metallic
Reading OptionPrice1b ... 0.0
Building Options ... 
Reading OptionName1c ... Infra-Red Clearcoat
Reading OptionPrice1c ... 0.0
Building Options ... 
Reading OptionName1d ... Grabber Green Clearcoat Metallic
Reading OptionPrice1d ... 0.0
Building Options ... 
Reading OptionName1e ... Sangria Red Clearcoat Metallic
Reading OptionPrice1e ... 0.0
Building Options ... 
Reading OptionName1f ... French Blue Clearcoat Metallic
Reading OptionPrice1f ... 0.0
Building Options ... 
Reading OptionName1g ... Twilight Blue Clearcoat Metallic
Reading OptionPrice1g ... 0.0
Building Options ... 
Reading OptionName1h ... CD Silver Clearcoat Metallic
Reading OptionPrice1h ... 0.0
Building Options ... 
Reading OptionName1i ... Pitch Black Clearcoat
Reading OptionPrice1i ... 0.0
Building Options ... 
Reading OptionName1j ... Cloud 9 White Clearcoat
Reading OptionPrice1j ... 0.0
Building Options ... 
Number of Options detected: 2
Reading value of OptionSet2 ... Transmission
Building Options ... 
Reading OptionName2a ... Automatic
Reading OptionPrice2a ... 0.0
Building Options ... 
Reading OptionName2b ... Manual
Reading OptionPrice2b ... -815.0
Building Options ... 
Number of Options detected: 3
Reading value of OptionSet3 ... Brakes
Building Options ... 
Reading OptionName3a ... Standard
Reading OptionPrice3a ... 0.0
Building Options ... 
Reading OptionName3b ... ABS
Reading OptionPrice3b ... 400.0
Building Options ... 
Reading OptionName3c ... ABS w/ Advanced Trac
Reading OptionPrice3c ... 1625.0
Building Options ... 
Number of Options detected: 2
Reading value of OptionSet4 ... Side Impact Air Bags
Building Options ... 
Reading OptionName4a ... No Side Impact Air Bags
Reading OptionPrice4a ... 0.0
Building Options ... 
Reading OptionName4b ... Side Impact Air Bags
Reading OptionPrice4b ... 350.0
Building Options ... 
Number of Options detected: 2
Reading value of OptionSet5 ... Power Moonroof
Building Options ... 
Reading OptionName5a ... No Power Moonroof
Reading OptionPrice5a ... 0.0
Building Options ... 
Reading OptionName5b ... Power Moonroof
Reading OptionPrice5b ... 595.0
Building Options ... 
Ford_Focus_Wagon_ZTW_2017.dat created

--------------------
Ford Focus Wagon ZTW
Year: 2017
Base Price: $18445.0
Option Set: Color
1: Fort Knox Gold Clearcoat Metallic, $0.0
2: Liquid Grey Clearcoat Metallic, $0.0
3: Infra-Red Clearcoat, $0.0
4: Grabber Green Clearcoat Metallic, $0.0
5: Sangria Red Clearcoat Metallic, $0.0
6: French Blue Clearcoat Metallic, $0.0
7: Twilight Blue Clearcoat Metallic, $0.0
8: CD Silver Clearcoat Metallic, $0.0
9: Pitch Black Clearcoat, $0.0
10: Cloud 9 White Clearcoat, $0.0
Option Set: Transmission
1: Automatic, $0.0
2: Manual, $-815.0
Option Set: Brakes
1: Standard, $0.0
2: ABS, $400.0
3: ABS w/ Advanced Trac, $1625.0
Option Set: Side Impact Air Bags
1: No Side Impact Air Bags, $0.0
2: Side Impact Air Bags, $350.0
Option Set: Power Moonroof
1: No Power Moonroof, $0.0
2: Power Moonroof, $595.0
--------------------

Data successfully written
Reading Ford_Focus_Wagon_ZTW_2017.dat

--------------------
Ford Focus Wagon ZTW
Year: 2017
Base Price: $18445.0
Option Set: Color
1: Fort Knox Gold Clearcoat Metallic, $0.0
2: Liquid Grey Clearcoat Metallic, $0.0
3: Infra-Red Clearcoat, $0.0
4: Grabber Green Clearcoat Metallic, $0.0
5: Sangria Red Clearcoat Metallic, $0.0
6: French Blue Clearcoat Metallic, $0.0
7: Twilight Blue Clearcoat Metallic, $0.0
8: CD Silver Clearcoat Metallic, $0.0
9: Pitch Black Clearcoat, $0.0
10: Cloud 9 White Clearcoat, $0.0
Option Set: Transmission
1: Automatic, $0.0
2: Manual, $-815.0
Option Set: Brakes
1: Standard, $0.0
2: ABS, $400.0
3: ABS w/ Advanced Trac, $1625.0
Option Set: Side Impact Air Bags
1: No Side Impact Air Bags, $0.0
2: Side Impact Air Bags, $350.0
Option Set: Power Moonroof
1: No Power Moonroof, $0.0
2: Power Moonroof, $595.0
--------------------

Data successfully read
New Automobile object added to collection
Sending client interaction choices ... 
Reading client request ... 
1
Sending client request follow-up ... 
Waiting for client to upload file ... 
{OptionName4a=No Side Impact Air Bags, BasePrice=23999, OptionName4b=Side Impact Air Bags, OptionPrice3c=1625, OptionPrice3a=0, OptionPrice3b=400, OptionPrice7c=2000, OptionName7c=Premium Racing Seats, Carbon Fiber Dashboard, OptionName3a=Standard, OptionName3b=ABS, OptionPrice4b=350, OptionName7a=Black Leather Seats, Stock Dashboard, OptionName7b=Motorized Two-Tone Leather Seats, Two-Tone Dashboard, OptionPrice4a=0, Make=Honda, OptionName3c=ABS w/ Advanced Trac, OptionName6b=Sports Body Kit, OptionPrice1c=0, OptionName6c=Track Ready Body Kit, OptionPrice1d=0, OptionPrice1a=0, OptionName2a=Manual, OptionPrice1b=0, OptionPrice5a=0, OptionName6a=Stock Body Kit, OptionPrice5b=595, OptionPrice1g=0, OptionPrice1e=0, OptionPrice1f=0, OptionPrice6a=0, OptionName2b=Automatic, OptionName2c=Dual Clutch Automatic, OptionName5a=No Power Moonroof, OptionName5b=Power Moonroof, OptionPrice2b=500, OptionPrice2c=1500, OptionPrice2a=0, OptionPrice6b=500, OptionPrice6c=1650, OptionSet1=Color, Year=2018, OptionSet2=Transmission, OptionSet3=Brakes, OptionSet4=Side Impact Air Bags, OptionPrice7a=0, OptionSet5=Power Moonroof, OptionName1e=Sangria Red Clearcoat Metallic, Model=Civic Type R, OptionPrice7b=600, OptionSet6=Body Kit, OptionName1f=Fort Knox Gold Clearcoat Metallic, OptionSet7=Interior, OptionName1g=Twilight Blue Clearcoat Metallic, OptionName1a=Carbon Black Clearcoat Metallic, OptionName1b=Liquid Grey Clearcoat Metallic, OptionName1c=CD Silver Clearcoat Metallic, OptionName1d=Pearl White Clearcoat}
Adding new Automobile to database ... 
Automotive object instantiated
Make: Honda
Model: Civic Type R
Year: 2018
Base price: $23999.0
Number of config options: 7
Building OptionSets ... 
Number of Options detected: 7
Reading value of OptionSet1 ... Color
Building Options ... 
Reading OptionName1a ... Carbon Black Clearcoat Metallic
Reading OptionPrice1a ... 0.0
Building Options ... 
Reading OptionName1b ... Liquid Grey Clearcoat Metallic
Reading OptionPrice1b ... 0.0
Building Options ... 
Reading OptionName1c ... CD Silver Clearcoat Metallic
Reading OptionPrice1c ... 0.0
Building Options ... 
Reading OptionName1d ... Pearl White Clearcoat
Reading OptionPrice1d ... 0.0
Building Options ... 
Reading OptionName1e ... Sangria Red Clearcoat Metallic
Reading OptionPrice1e ... 0.0
Building Options ... 
Reading OptionName1f ... Fort Knox Gold Clearcoat Metallic
Reading OptionPrice1f ... 0.0
Building Options ... 
Reading OptionName1g ... Twilight Blue Clearcoat Metallic
Reading OptionPrice1g ... 0.0
Building Options ... 
Number of Options detected: 3
Reading value of OptionSet2 ... Transmission
Building Options ... 
Reading OptionName2a ... Manual
Reading OptionPrice2a ... 0.0
Building Options ... 
Reading OptionName2b ... Automatic
Reading OptionPrice2b ... 500.0
Building Options ... 
Reading OptionName2c ... Dual Clutch Automatic
Reading OptionPrice2c ... 1500.0
Building Options ... 
Number of Options detected: 3
Reading value of OptionSet3 ... Brakes
Building Options ... 
Reading OptionName3a ... Standard
Reading OptionPrice3a ... 0.0
Building Options ... 
Reading OptionName3b ... ABS
Reading OptionPrice3b ... 400.0
Building Options ... 
Reading OptionName3c ... ABS w/ Advanced Trac
Reading OptionPrice3c ... 1625.0
Building Options ... 
Number of Options detected: 2
Reading value of OptionSet4 ... Side Impact Air Bags
Building Options ... 
Reading OptionName4a ... No Side Impact Air Bags
Reading OptionPrice4a ... 0.0
Building Options ... 
Reading OptionName4b ... Side Impact Air Bags
Reading OptionPrice4b ... 350.0
Building Options ... 
Number of Options detected: 2
Reading value of OptionSet5 ... Power Moonroof
Building Options ... 
Reading OptionName5a ... No Power Moonroof
Reading OptionPrice5a ... 0.0
Building Options ... 
Reading OptionName5b ... Power Moonroof
Reading OptionPrice5b ... 595.0
Building Options ... 
Number of Options detected: 3
Reading value of OptionSet6 ... Body Kit
Building Options ... 
Reading OptionName6a ... Stock Body Kit
Reading OptionPrice6a ... 0.0
Building Options ... 
Reading OptionName6b ... Sports Body Kit
Reading OptionPrice6b ... 500.0
Building Options ... 
Reading OptionName6c ... Track Ready Body Kit
Reading OptionPrice6c ... 1650.0
Building Options ... 
Number of Options detected: 3
Reading value of OptionSet7 ... Interior
Building Options ... 
Reading OptionName7a ... Black Leather Seats, Stock Dashboard
Reading OptionPrice7a ... 0.0
Building Options ... 
Reading OptionName7b ... Motorized Two-Tone Leather Seats, Two-Tone Dashboard
Reading OptionPrice7b ... 600.0
Building Options ... 
Reading OptionName7c ... Premium Racing Seats, Carbon Fiber Dashboard
Reading OptionPrice7c ... 2000.0
Building Options ... 
Honda_Civic_Type_R_2018.dat created

--------------------
Honda Civic Type R
Year: 2018
Base Price: $23999.0
Option Set: Color
1: Carbon Black Clearcoat Metallic, $0.0
2: Liquid Grey Clearcoat Metallic, $0.0
3: CD Silver Clearcoat Metallic, $0.0
4: Pearl White Clearcoat, $0.0
5: Sangria Red Clearcoat Metallic, $0.0
6: Fort Knox Gold Clearcoat Metallic, $0.0
7: Twilight Blue Clearcoat Metallic, $0.0
Option Set: Transmission
1: Manual, $0.0
2: Automatic, $500.0
3: Dual Clutch Automatic, $1500.0
Option Set: Brakes
1: Standard, $0.0
2: ABS, $400.0
3: ABS w/ Advanced Trac, $1625.0
Option Set: Side Impact Air Bags
1: No Side Impact Air Bags, $0.0
2: Side Impact Air Bags, $350.0
Option Set: Power Moonroof
1: No Power Moonroof, $0.0
2: Power Moonroof, $595.0
Option Set: Body Kit
1: Stock Body Kit, $0.0
2: Sports Body Kit, $500.0
3: Track Ready Body Kit, $1650.0
Option Set: Interior
1: Black Leather Seats, Stock Dashboard, $0.0
2: Motorized Two-Tone Leather Seats, Two-Tone Dashboard, $600.0
3: Premium Racing Seats, Carbon Fiber Dashboard, $2000.0
--------------------

Data successfully written
Reading Honda_Civic_Type_R_2018.dat

--------------------
Honda Civic Type R
Year: 2018
Base Price: $23999.0
Option Set: Color
1: Carbon Black Clearcoat Metallic, $0.0
2: Liquid Grey Clearcoat Metallic, $0.0
3: CD Silver Clearcoat Metallic, $0.0
4: Pearl White Clearcoat, $0.0
5: Sangria Red Clearcoat Metallic, $0.0
6: Fort Knox Gold Clearcoat Metallic, $0.0
7: Twilight Blue Clearcoat Metallic, $0.0
Option Set: Transmission
1: Manual, $0.0
2: Automatic, $500.0
3: Dual Clutch Automatic, $1500.0
Option Set: Brakes
1: Standard, $0.0
2: ABS, $400.0
3: ABS w/ Advanced Trac, $1625.0
Option Set: Side Impact Air Bags
1: No Side Impact Air Bags, $0.0
2: Side Impact Air Bags, $350.0
Option Set: Power Moonroof
1: No Power Moonroof, $0.0
2: Power Moonroof, $595.0
Option Set: Body Kit
1: Stock Body Kit, $0.0
2: Sports Body Kit, $500.0
3: Track Ready Body Kit, $1650.0
Option Set: Interior
1: Black Leather Seats, Stock Dashboard, $0.0
2: Motorized Two-Tone Leather Seats, Two-Tone Dashboard, $600.0
3: Premium Racing Seats, Carbon Fiber Dashboard, $2000.0
--------------------

Data successfully read
New Automobile object added to collection
Sending client interaction choices ... 
Reading client request ... 
1
Sending client request follow-up ... 
Waiting for client to upload file ... 
{OptionPrice1c=0, OptionName4a=No Side Impact Air Bags, OptionPrice1d=0, BasePrice=15559, OptionName4b=Side Impact Air Bags, OptionPrice1a=0, OptionName2a=Automatic, OptionPrice1b=0, OptionPrice3a=0, OptionPrice3b=400, OptionPrice1e=0, OptionPrice1f=0, OptionName2b=Manual, OptionName3a=Standard, OptionPrice2b=-815, OptionName3b=ABS, OptionPrice4b=350, OptionPrice2a=0, OptionPrice4a=0, Make=Toyota, Type=Hybrid, OptionSet1=Color, Year=2018, OptionSet2=Transmission, OptionSet3=Brakes, OptionSet4=Side Impact Air Bags, OptionName1e=Infra-Red Clearcoat, Model=Prius, OptionName1f=French Blue Clearcoat Metallic, OptionName1a=Pitch Black Clearcoat, OptionName1b=Liquid Grey Clearcoat Metallic, OptionName1c=Cloud 9 White Clearcoat, OptionName1d=CD Silver Clearcoat Metallic}
Adding new Automobile to database ... 
Automotive object instantiated
Make: Toyota
Model: Prius
Year: 2018
Base price: $15559.0
Number of config options: 4
Building OptionSets ... 
Number of Options detected: 6
Reading value of OptionSet1 ... Color
Building Options ... 
Reading OptionName1a ... Pitch Black Clearcoat
Reading OptionPrice1a ... 0.0
Building Options ... 
Reading OptionName1b ... Liquid Grey Clearcoat Metallic
Reading OptionPrice1b ... 0.0
Building Options ... 
Reading OptionName1c ... Cloud 9 White Clearcoat
Reading OptionPrice1c ... 0.0
Building Options ... 
Reading OptionName1d ... CD Silver Clearcoat Metallic
Reading OptionPrice1d ... 0.0
Building Options ... 
Reading OptionName1e ... Infra-Red Clearcoat
Reading OptionPrice1e ... 0.0
Building Options ... 
Reading OptionName1f ... French Blue Clearcoat Metallic
Reading OptionPrice1f ... 0.0
Building Options ... 
Number of Options detected: 2
Reading value of OptionSet2 ... Transmission
Building Options ... 
Reading OptionName2a ... Automatic
Reading OptionPrice2a ... 0.0
Building Options ... 
Reading OptionName2b ... Manual
Reading OptionPrice2b ... -815.0
Building Options ... 
Number of Options detected: 2
Reading value of OptionSet3 ... Brakes
Building Options ... 
Reading OptionName3a ... Standard
Reading OptionPrice3a ... 0.0
Building Options ... 
Reading OptionName3b ... ABS
Reading OptionPrice3b ... 400.0
Building Options ... 
Number of Options detected: 2
Reading value of OptionSet4 ... Side Impact Air Bags
Building Options ... 
Reading OptionName4a ... No Side Impact Air Bags
Reading OptionPrice4a ... 0.0
Building Options ... 
Reading OptionName4b ... Side Impact Air Bags
Reading OptionPrice4b ... 350.0
Building Options ... 
Toyota_Prius_2018.dat created

--------------------
Toyota Prius
Year: 2018
Base Price: $15559.0
Option Set: Color
1: Pitch Black Clearcoat, $0.0
2: Liquid Grey Clearcoat Metallic, $0.0
3: Cloud 9 White Clearcoat, $0.0
4: CD Silver Clearcoat Metallic, $0.0
5: Infra-Red Clearcoat, $0.0
6: French Blue Clearcoat Metallic, $0.0
Option Set: Transmission
1: Automatic, $0.0
2: Manual, $-815.0
Option Set: Brakes
1: Standard, $0.0
2: ABS, $400.0
Option Set: Side Impact Air Bags
1: No Side Impact Air Bags, $0.0
2: Side Impact Air Bags, $350.0
--------------------

Data successfully written
Reading Toyota_Prius_2018.dat

--------------------
Toyota Prius
Year: 2018
Base Price: $15559.0
Option Set: Color
1: Pitch Black Clearcoat, $0.0
2: Liquid Grey Clearcoat Metallic, $0.0
3: Cloud 9 White Clearcoat, $0.0
4: CD Silver Clearcoat Metallic, $0.0
5: Infra-Red Clearcoat, $0.0
6: French Blue Clearcoat Metallic, $0.0
Option Set: Transmission
1: Automatic, $0.0
2: Manual, $-815.0
Option Set: Brakes
1: Standard, $0.0
2: ABS, $400.0
Option Set: Side Impact Air Bags
1: No Side Impact Air Bags, $0.0
2: Side Impact Air Bags, $350.0
--------------------

Data successfully read
New Automobile object added to collection
Sending client interaction choices ... 
Reading client request ... 
0
Closing server input stream for client /192.168.1.207 ... 
Closing server output stream for client /192.168.1.207 ... 
Closing client socket /192.168.1.207 ... 
/192.168.1.207
Creating server object streams ... 
Sending client interaction choices ... 
Reading client request ... 
2
Sending client request follow-up ... 
Waiting for client to select Automobile ... 
Sending client interaction choices ... 
Reading client request ... 
0
Closing server input stream for client /192.168.1.207 ... 
Closing server output stream for client /192.168.1.207 ... 
Closing client socket /192.168.1.207 ... 
/192.168.1.207
Creating server object streams ... 
Sending client interaction choices ... 
Reading client request ... 
2
Sending client request follow-up ... 
Waiting for client to select Automobile ... 
Sending Automobile object to client ... 
Honda Civic Type R 2018
model.Automobile@5dd34b86
Sending client interaction choices ... 
Reading client request ... 
0
Closing server input stream for client /192.168.1.207 ... 
Closing server output stream for client /192.168.1.207 ... 
Closing client socket /192.168.1.207 ... 

--------------------------------------
---------- TESTING SCENARIO ----------
--------------------------------------
Driver5_0_0_Client.java 
Client-side response
Hard-coded values
Only used for uploading Automobiles
DEBUG flag on
--------------------------------------
Connecting to host ... 
Connected to host, creating object streams ... 
Communicating with host ... 
Received server response ... 

Enter 1 to upload a new Automobile
Enter 2 to configure an Automobile
Enter 0 to terminate connection

Response to server: 
1
Sending 1 to server ... 

Received server response ... 
Upload a file to create an Automobile
Response to server: 
C:\Users\lyshe\Documents\De Anza\Winter 2018\CIS 35B 62Y\Assignments\Car Config App Web Client\src\Ford_Focus_Wagon_ZTW.prop
Sending {OptionPrice1c=0, BasePrice=18445, OptionPrice1d=0, OptionName4a=No Side Impact Air Bags, OptionPrice1a=0, OptionPrice3c=1625, OptionName4b=Side Impact Air Bags, OptionPrice1b=0, OptionName2a=Automatic, OptionPrice3a=0, OptionPrice3b=400, OptionPrice5a=0, OptionPrice5b=595, OptionPrice1i=0, OptionPrice1j=0, OptionPrice1g=0, OptionPrice1h=0, OptionPrice1e=0, OptionPrice1f=0, OptionName2b=Manual, OptionName5a=No Power Moonroof, OptionName5b=Power Moonroof, OptionPrice2b=-815, OptionName3a=Standard, OptionName3b=ABS, OptionPrice4b=350, OptionPrice2a=0, OptionPrice4a=0, Make=Ford, Year=2017, OptionSet1=Color, OptionName1i=Pitch Black Clearcoat, OptionName1j=Cloud 9 White Clearcoat, OptionSet2=Transmission, OptionSet3=Brakes, OptionSet4=Side Impact Air Bags, Model=Focus Wagon ZTW, OptionName1e=Sangria Red Clearcoat Metallic, OptionSet5=Power Moonroof, OptionName1f=French Blue Clearcoat Metallic, OptionName1g=Twilight Blue Clearcoat Metallic, OptionName1h=CD Silver Clearcoat Metallic, OptionName1a=Fort Knox Gold Clearcoat Metallic, OptionName3c=ABS w/ Advanced Trac, OptionName1b=Liquid Grey Clearcoat Metallic, OptionName1c=Infra-Red Clearcoat, OptionName1d=Grabber Green Clearcoat Metallic} to server ... 

Received server response ... 
Automobile object successfully added to database
Press any key to return to main menu
Response to server: 

Sending  to server ... 

Received server response ... 

Enter 1 to upload a new Automobile
Enter 2 to configure an Automobile
Enter 0 to terminate connection

Response to server: 
1
Sending 1 to server ... 

Received server response ... 
Upload a file to create an Automobile
Response to server: 
C:\Users\lyshe\Documents\De Anza\Winter 2018\CIS 35B 62Y\Assignments\Car Config App Web Client\src\Honda_Civic_Type_R.prop
Sending {BasePrice=23999, OptionName4a=No Side Impact Air Bags, OptionPrice3c=1625, OptionName4b=Side Impact Air Bags, OptionPrice3a=0, OptionPrice3b=400, OptionPrice7c=2000, OptionName7c=Premium Racing Seats, Carbon Fiber Dashboard, OptionName3a=Standard, OptionName3b=ABS, OptionPrice4b=350, OptionName7a=Black Leather Seats, Stock Dashboard, OptionPrice4a=0, OptionName7b=Motorized Two-Tone Leather Seats, Two-Tone Dashboard, Make=Honda, OptionName3c=ABS w/ Advanced Trac, OptionPrice1c=0, OptionName6b=Sports Body Kit, OptionPrice1d=0, OptionName6c=Track Ready Body Kit, OptionPrice1a=0, OptionPrice1b=0, OptionName2a=Manual, OptionPrice5a=0, OptionPrice5b=595, OptionName6a=Stock Body Kit, OptionPrice1g=0, OptionPrice1e=0, OptionPrice1f=0, OptionPrice6a=0, OptionName2b=Automatic, OptionName2c=Dual Clutch Automatic, OptionName5a=No Power Moonroof, OptionName5b=Power Moonroof, OptionPrice2b=500, OptionPrice2c=1500, OptionPrice2a=0, OptionPrice6b=500, OptionPrice6c=1650, Year=2018, OptionSet1=Color, OptionSet2=Transmission, OptionSet3=Brakes, OptionSet4=Side Impact Air Bags, Model=Civic Type R, OptionName1e=Sangria Red Clearcoat Metallic, OptionSet5=Power Moonroof, OptionPrice7a=0, OptionName1f=Fort Knox Gold Clearcoat Metallic, OptionSet6=Body Kit, OptionPrice7b=600, OptionName1g=Twilight Blue Clearcoat Metallic, OptionSet7=Interior, OptionName1a=Carbon Black Clearcoat Metallic, OptionName1b=Liquid Grey Clearcoat Metallic, OptionName1c=CD Silver Clearcoat Metallic, OptionName1d=Pearl White Clearcoat} to server ... 

Received server response ... 
Automobile object successfully added to database
Press any key to return to main menu
Response to server: 

Sending  to server ... 

Received server response ... 

Enter 1 to upload a new Automobile
Enter 2 to configure an Automobile
Enter 0 to terminate connection

Response to server: 
1
Sending 1 to server ... 

Received server response ... 
Upload a file to create an Automobile
Response to server: 
C:\Users\lyshe\Documents\De Anza\Winter 2018\CIS 35B 62Y\Assignments\Car Config App Web Client\src\Toyota_Prius.prop
Sending {OptionPrice1c=0, BasePrice=15559, OptionPrice1d=0, OptionName4a=No Side Impact Air Bags, OptionPrice1a=0, OptionName4b=Side Impact Air Bags, OptionPrice1b=0, OptionName2a=Automatic, OptionPrice3a=0, OptionPrice3b=400, OptionPrice1e=0, OptionPrice1f=0, OptionName2b=Manual, OptionPrice2b=-815, OptionName3a=Standard, OptionName3b=ABS, OptionPrice4b=350, OptionPrice2a=0, OptionPrice4a=0, Make=Toyota, Type=Hybrid, Year=2018, OptionSet1=Color, OptionSet2=Transmission, OptionSet3=Brakes, OptionSet4=Side Impact Air Bags, Model=Prius, OptionName1e=Infra-Red Clearcoat, OptionName1f=French Blue Clearcoat Metallic, OptionName1a=Pitch Black Clearcoat, OptionName1b=Liquid Grey Clearcoat Metallic, OptionName1c=Cloud 9 White Clearcoat, OptionName1d=CD Silver Clearcoat Metallic} to server ... 

Received server response ... 
Automobile object successfully added to database
Press any key to return to main menu
Response to server: 

Sending  to server ... 

Received server response ... 

Enter 1 to upload a new Automobile
Enter 2 to configure an Automobile
Enter 0 to terminate connection

Response to server: 
0
Sending 0 to server ... 

Closing client input stream ... 
Closing client output stream ... 
Closing client console input stream ... 
Closing client socket ... 
--------------------------------------