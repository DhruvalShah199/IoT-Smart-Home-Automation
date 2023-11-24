import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.lloseng.ocsf.server.AbstractServer;
import com.lloseng.ocsf.server.ConnectionToClient;

public class SHServer extends AbstractServer {
	
	// List of objects of the client
	private List <ConnectionToClient> client;
	
	// Creating an object of SHServerController class to access its methods
	private SHServerController serverController;   

	public SHServer(int port) {
		super(port);
		this.client = new ArrayList<ConnectionToClient>();
		this.serverController =  new SHServerController();
		// TODO Auto-generated constructor stub
	}
	
	
	// Implement ways to handle messages from the client and also call the functions 
	// of SHServerController class
	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		String messageString = (String) msg;
		
		//To handle messages from smart light
		if(messageString.equals("turnonlight")) {
			serverController.turnOnLight("on");
			boolean printStatus = serverController.getStatus();
			sendToAllClients("lighton");
			System.out.println("Updated light status: " + printStatus);
		}
		else if(messageString.equals("turnofflight")) {
			serverController.turnOffLight("off");
			boolean printStatus = serverController.getStatus();
			sendToAllClients("lightoff");
			System.out.println("Updated light status: " + printStatus);
		}
		else if (messageString.startsWith("adjustBrightness")) {
	        int brightness = Integer.parseInt(messageString.split(":")[1]);
	        serverController.adjustLightBrightness(brightness);
	        sendToAllClients("brightness:" + brightness);
	    }
		else if(messageString.equals("getlightstatus")) {
			boolean status = serverController.displayLightStatus();
			System.out.println(status);
			if(status == true) {
				try {
					client.sendToClient("lightstatuson");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(status == false) {
				try {
					client.sendToClient("lightstatusoff");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		//To handle messages from smart thermostat
		else if(messageString.equals("thermostat")) {
			int temperature = serverController.getThermostatTemperature();
			sendToAllClients(temperature);
		}
		else if(messageString.equals("turnonthermostat")) {
			serverController.turnOnThermostat("on");
			boolean printStatus = serverController.getStatus();
			sendToAllClients("thermostaton");
			System.out.println("Updated thermostat status: " + printStatus);
		}
		else if(messageString.equals("turnoffthermostat")) {
			serverController.turnOffThermostat("off");
			boolean printStatus = serverController.getStatus();
			sendToAllClients("thermostatoff");
			System.out.println("Updated thermostat status: " + printStatus);
		}
		else if(messageString.equals("cool")) {
			String mode = serverController.changeThermostatMode("cool");
			sendToAllClients(mode);
		}
		else if(messageString.equals("heat")) {
			String mode = serverController.changeThermostatMode("heat");
			sendToAllClients(mode);
		}
		else if(messageString.equals("increase")) {
			int temperature = serverController.increaseOrDecreaseThermostatTemperature("increase");
			String tempString = Integer.toString(temperature);
			sendToAllClients("temperature"+tempString);
		}
		else if(messageString.equals("decrease")) {
			int temperature = serverController.increaseOrDecreaseThermostatTemperature("decrease");
			String tempString = Integer.toString(temperature);
			sendToAllClients("temperature"+tempString);
		}
		else if(messageString.equals("getthermostatstatus")) {
			boolean status = serverController.displayThermostatStatus();
			if(status == true) {
				try {
					client.sendToClient("thermostatstatuson");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(status == false) {
				try {
					client.sendToClient("thermostatstatusoff");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		//To handle messages from smart lock
		else if(messageString.equals("lock")) {
			String lockUnlock =serverController.lockDoor("lock");
			sendToAllClients(lockUnlock);
		}
		else if(messageString.equals("unlock")) {
			String lockUnlock =serverController.lockDoor("unlock");
			sendToAllClients(lockUnlock);
		}
		else if(messageString.equals("getbreakinalerttrue")) {
			boolean breakIn = serverController.getBreakInAlert(true);
			if (breakIn == true) {
				sendToAllClients("getbreakinalerton");
			}
		}
		else if(messageString.equals("getbreakinalertfalse")) {
			boolean breakIn = serverController.getBreakInAlert(false);
			if (breakIn == false) {
				sendToAllClients("getbreakinalertoff");
			}
		}
		else if(messageString.equals("getlockstatus")) {
			boolean status = serverController.displayLockStatus();
			if(status == true) {
				try {
					client.sendToClient("lockstatuson");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(status == false) {
				try {
					client.sendToClient("lockstatusoff");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		//To handle messages from vacuum robot
		else if(messageString.equals("startcleaning")) {
			String startCleaning = serverController.startCleaning();
			sendToAllClients(startCleaning);
		}
		else if(messageString.equals("stopcleaning")) {
			String stopCleaning = serverController.stopCleaning();
			sendToAllClients(stopCleaning);
		}
		else if(messageString.equals("dustsackalerton")) {
			String dustSackAlert = serverController.emptyDustSackVacuumRobotAlert(true);
			sendToAllClients(dustSackAlert);
		}
		else if(messageString.equals("dustsackalertoff")) {
			String dustSackAlert = serverController.emptyDustSackVacuumRobotAlert(false);
			sendToAllClients(dustSackAlert);
		}
		else if(messageString.equals("getvacuumrobotstatus")) {
			boolean status = serverController.displayVacuumRobotStatus();
			if(status == true) {
				try {
					client.sendToClient("vacuumcleaning");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(status == false) {
				try {
					client.sendToClient("vacuumnotcleaning");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		

		else {
			System.out.println("The message received from Client is invalid");
		}		
		System.out.println("Message recieved: " + msg + " from  " + client);
	}
	
	@Override
    public void serverStarted() {
        System.out.println("Server listening for connections on port " + getPort());
    }

    @Override
    public void serverStopped() {
        System.out.println("Server has stopped listening for connections.");
    }
}