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
		else if(messageString.contains("0x")) {
			serverController.changeLightColor(messageString);
			String color = serverController.displayLightColor();
			sendToAllClients(color);
			
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
		//To handle messages from smart light automation
		else if (messageString.startsWith("scheduleLightOn-")) {
	        String time = messageString.split("-")[1];
	        // Schedule the light to turn on at the specified time
	        serverController.scheduleLightOn(time, client);
	    }
		else if (messageString.startsWith("scheduleLightOff-")) {
	        String time = messageString.split("-")[1];
	        // Schedule the light to turn off at the specified time
	        serverController.scheduleLightOff(time, client);
	    }
		else if (messageString.startsWith("scheduleLightColor-")) {
		    String[] parts = messageString.split("-");
		    String time = parts[1];
		    String color = parts[2];
		    serverController.scheduleLightColorChange(time, color, client);
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
		else if(messageString.equals("increase") && serverController.displayTemperature() < 35) {
			int temperature = serverController.increaseOrDecreaseThermostatTemperature("increase");
			String tempString = Integer.toString(temperature);
			sendToAllClients("temperature"+tempString);
		}
		else if(messageString.equals("decrease") && serverController.displayTemperature() > 15) {
			int temperature = serverController.increaseOrDecreaseThermostatTemperature("decrease");
			String tempString = Integer.toString(temperature);
			sendToAllClients("temperature"+tempString);
		}
		else if(messageString.equals("getthermostatstatus")) {
			boolean status = serverController.displayThermostatStatus();
			String modeStatus = serverController.displayThermostatMode();
			if(status == true) {
				try {
					client.sendToClient("thermostatstatuson:"+modeStatus);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(status == false) {
				try {
					client.sendToClient("thermostatstatusoff:"+modeStatus);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//To handle messages from thermostat automation
		else if (messageString.startsWith("scheduleThermostatOn-")) {
	        String time = messageString.split("-")[1];
	        // Schedule the thermostat to turn on at the specified time
	        serverController.scheduleThermostatOn(time, client);
	    }
		else if (messageString.startsWith("scheduleThermostatOff-")) {
	        String time = messageString.split("-")[1];
	        // Schedule the thermostat to turn off at the specified time
	        serverController.scheduleThermostatOff(time, client);
	    }
		else if (messageString.startsWith("scheduleModeCoolOn-")) {
	        String time = messageString.split("-")[1];
	        // Schedule the thermostat to turn off at the specified time
	        serverController.scheduleThermostatModeCool(time, client);
	    }
		else if (messageString.startsWith("scheduleModeHeatOn-")) {
	        String time = messageString.split("-")[1];
	        // Schedule the thermostat to turn off at the specified time
	        serverController.scheduleThermostatModeHeat(time, client);
	    }
		
		
		
		
		
		//To handle messages from smart lock
		else if(messageString.equals("lock")) {
			String lockUnlock = serverController.lockDoor("lock");
			sendToAllClients(lockUnlock);
		}
		else if(messageString.equals("unlock")) {
			String lockUnlock = serverController.unlockDoor("unlock");
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
		//To handle messages from smart lock automation
		else if (messageString.startsWith("scheduleLock-")) {
	        String time = messageString.split("-")[1];
	        // Schedule the smart lock to lock at the specified time
	        serverController.scheduleLockSmartLock(time, client);
	    }
		else if (messageString.startsWith("scheduleUnlock-")) {
	        String time = messageString.split("-")[1];
	     // Schedule the smart lock to unlock at the specified time
	        serverController.scheduleUnlockSmartLock(time, client);
	    }
		else if (messageString.startsWith("scheduleBreakInAlert-")) {
	        String time = messageString.split("-")[1];
	     // Schedule the smart lock to unlock at the specified time
	        serverController.scheduleBreakInAlertSmartLock(time, client);
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
		//To handle messages from vacuum robot automation
		else if (messageString.startsWith("schedulestartcleaningat-")) {
	        String time = messageString.split("-")[1];
	        // Schedule the light to turn on at the specified time
	        serverController.scheduleVacuumCleaning(time, client);
	    }
		
		
		
		
		//To handle messages from smart doorbell
		else if(messageString.equals("turnondoorbell")) {
			serverController.turnOnDoorbell("on");
			boolean printStatus = serverController.getStatus();
			sendToAllClients("doorbellon");
			System.out.println("Updated doorbell status: " + printStatus);
		}
		else if(messageString.equals("turnoffdoorbell")) {
			serverController.turnOffDoorbell("off");
			boolean printStatus = serverController.getStatus();
			sendToAllClients("doorbelloff");
			System.out.println("Updated doorbell status: " + printStatus);
		}
		else if(messageString.equals("activatenightmode")) {
			boolean nightModeStatus = serverController.activateNightModeDoorbell(true);
			if(nightModeStatus == true) {
				sendToAllClients("nightmodeon");
			}
		}
		else if(messageString.equals("deactivatenightmode")) {
			boolean nightModeStatus = serverController.activateNightModeDoorbell(false);
			if(nightModeStatus == false) {
				sendToAllClients("nightmodeoff");
			}
		}
		else if(messageString.equals("turnoncamera")) {
			boolean status = serverController.displayDoorbellStatus();
			if(status == true) {
				serverController.turnOnCameraDoorbell(status);
				sendToAllClients("cameraon");
			}
			else if(status == false){
				sendToAllClients("turnondoorbell");
			}
			
		}
		else if(messageString.equals("turnoffcamera")) {
			serverController.turnOnCameraDoorbell(false);
			sendToAllClients("cameraoff");
		}
		else if(messageString.equals("doorbellstatus")) {
			boolean status = serverController.displayDoorbellStatus();
			System.out.println(status);
			if(status == true) {
				try {
					client.sendToClient("doorbellstatuson");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(status == false) {
				try {
					client.sendToClient("doorbellstatusoff");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//To handle messages from smart doorbell automation
			else if (messageString.startsWith("scheduleDoorbellOn-")) {
		        String time = messageString.split("-")[1];
		        // Schedule the light to turn on at the specified time
		        serverController.scheduleDoorbellOn(time, client);
		    }
			else if (messageString.startsWith("scheduleDoorbellOff-")) {
		        String time = messageString.split("-")[1];
		        // Schedule the light to turn off at the specified time
		        serverController.scheduleDoorbellOff(time, client);
		    }
			else if (messageString.startsWith("scheduleNightModeAt-")) {
		        String time = messageString.split("-")[1];
		        // Schedule the light to turn off at the specified time
		        serverController.scheduleNightModeAt(time, client);
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
    
    @Override
    protected void clientConnected(ConnectionToClient client) {
	// To display the connected client(s)
	System.out.println("Client: " + client + " is connected");

    }
}
// End of SHServer Class