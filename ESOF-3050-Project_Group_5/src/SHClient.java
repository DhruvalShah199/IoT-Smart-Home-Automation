/**
* The SHClient contains all the methods necassary to
* make connection to the server, send messages to the 
* server-side and also overrides the handlemessagesfromserver 
* method to handle specific messages from the server and 
* do different functions on the GUI.
* 
* @author Dhruval Harshilkumar Shah
* @author Amir Dawood
* @version December 2023
*/


import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import com.lloseng.ocsf.client.AbstractClient;
import javafx.application.Platform;

public class SHClient extends AbstractClient{
	
	private SHClientController clientController; // Object for SHCLientContoller class
	
	private Timer timer = new Timer();
	
	public SHClient(String host, int port, SHClientController clientController	) {
		super(host, port);
		this.clientController = clientController;
	}
	
	public boolean connectionToServer() {
	    try {
	        openConnection();
	        return true;
	    } catch (IOException e) {
	        System.out.println("Could not connect to the server: " + e.getMessage());
	     // Handle reconnection here or notify the user
	        return false;
	    }
	}
	
	
	
	//----------------------- Methods to send messages for Smart Light --------------------
	public void turnOnLight() {
		// sends turn on light message to server
		try {
			sendToServer("turnonlight");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void turnOffLight() {
		// sends turn off light message to server
		try {
			sendToServer("turnofflight");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void changeColor(String colorValue) {
		// sends message to change smart light color status to server
		try {
			sendToServer(colorValue);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void getStatusSmartLight() {
		// sends message to get smart light status to server
		try {
			sendToServer("getlightstatus");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Methods for Automation
	public void turnOnLightAt(String time) {
		try {
			sendToServer("scheduleLightOn-" + time);
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void turnOffLightAt(String time) {
		try {
			sendToServer("scheduleLightOff-" + time);
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void changeColorLightAt(String time, String color) {
		try {
			sendToServer("scheduleLightColor-" + time + "-" + color);
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	//----------------------- Methods to send messages for Thermostat -----------------------
	public void thermostatButtonPressed() {
		try {
			sendToServer("thermostat");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void cool() {
		// sends cool mode message to server
		try {
			sendToServer("cool");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void decreaseTemperature() {
		// sends decrease temperature message to server
		try {
			sendToServer("decrease");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void heat() {
		// sends heat mode message to server
		try {
			sendToServer("heat");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void increaseTemperature() {
		// sends increase temperature message to server
		try {
			sendToServer("increase");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void turnOffThermostat() {
		// sends turn off thermostat message to server
		try {
			sendToServer("turnoffthermostat");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void turnOnThermostat() {
		// sends turn on thermostat message to server
		try {
			sendToServer("turnonthermostat");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getStatusThermostat() {
		// sends message to get thermostat status to server
		try {
			sendToServer("getthermostatstatus");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Methods for Automation
	public void turnOnThermostatAt(String time) {
		try {
			sendToServer("scheduleThermostatOn-" + time);
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void turnOffThermostatAt(String time) {
		try {
			sendToServer("scheduleThermostatOff-" + time);
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void modeCoolThermostatAt(String time) {
		try {
			sendToServer("scheduleModeCoolOn-" + time);
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void modeHeatThermostatAt(String time) {
		try {
			sendToServer("scheduleModeHeatOn-" + time);
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	//-----------------------  Methods to send messages for Smart Lock -----------------------
	public void getBreakIAlert(boolean breakInAlert) {
		// sends get break in alert message to server
		if(breakInAlert == true) {
			try {
				sendToServer("getbreakinalerttrue");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (breakInAlert == false) {
			try {
				sendToServer("getbreakinalertfalse");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void lock() {
		// sends lock message to server
		try {
			sendToServer("lock");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void unlock() {
		// sends unlock message to server
		try {
			sendToServer("unlock");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getStatusSmartLock() {
		// sends message to get smart lock status to server
		try {
			sendToServer("getlockstatus");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Methods for Automation
		public void lockSmartLockAt(String time) {
			try {
				sendToServer("scheduleLock-" + time);
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		public void unlockSmartLockAt(String time) {
			try {
				sendToServer("scheduleUnlock-" + time);
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		public void breakInAlertSmartLockAt (String time) {
			try {
				sendToServer("scheduleBreakInAlert-" + time);
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	
	
	//----------------------- Methods to send messages for Vacuum Robot -----------------------
	public void startCleaning() {
		// Sends start cleaning message to server
		try {
			sendToServer("startcleaning");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void stopCleaning() {
		// Sends stop cleaning message to server
		try {
			sendToServer("stopcleaning");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void emptyDustSackAlert(boolean alert) {
		try {
			if(alert == true) {
				sendToServer("dustsackalerton");
			}
			else if(alert == false) {
				sendToServer("dustsackalertoff");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void getStatusVacuumRobot() {
		// sends message to get vacuum robot status to server
		try {
			sendToServer("getvacuumrobotstatus");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Methods for Automation
	public void startCleaningAt(String time) {
		try {
			sendToServer("schedulestartcleaningat-" + time);
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	
	
	//----------------------- Methods to send messages for Smart Doorbell -----------------------
	private boolean doorbellOn = false;
	
	public boolean isDoorbellOn() {
		return doorbellOn;
	}

	public boolean setDoorbellOn(boolean doorbellOn) {
		this.doorbellOn = doorbellOn;
		return doorbellOn;
	}
	
	public void activateNightMode(boolean nightMode) {
		// Sends message activate/deactivate night mode to server
		if (nightMode == true) {
			try {
				sendToServer("activatenightmode");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (nightMode == false) {
			try {
				sendToServer("deactivatenightmode");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void turnOffDoorbell() {
		// Sends turn off doorbell message
		try {
			sendToServer("turnoffdoorbell");
			setDoorbellOn(false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void turnOnCameraDoorbell() {
		// Sends turn on doorbell camera message
		if(isDoorbellOn() == true) {
			try {
				sendToServer("turnoncamera");
				TimerTask task = new TimerTask() {
	                @Override
	                public void run() {
	                    try {
							sendToServer("turnoffcamera");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	                }
	            };
	            timer.schedule(task, 10000);
			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (isDoorbellOn() == false) {
			try {
				sendToServer("turnoncamera");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public void turnOnDoorbell() {
		// Sends turn on doorbell message
		try {
			sendToServer("turnondoorbell");
			setDoorbellOn(true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void getStatusSmartDoorbell() {
		// sends message to get smart doorbell status to server
		try {
			sendToServer("doorbellstatus");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Methods for Automation
		public void turnOnDoorbellAt(String time) {
			try {
				sendToServer("scheduleDoorbellOn-" + time);
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		public void turnOffDoorbellAt(String time) {
			try {
				sendToServer("scheduleDoorbellOff-" + time);
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		public void activateNightModeAt(String time) {
			try {
				sendToServer("scheduleNightModeAt-" + time);
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	
	//----------------------- Handle Message From Server Method -----------------------
	@Override
	protected void handleMessageFromServer(Object msg) {
		System.out.println("Message received: " + msg);
		// Check if the object is a String
		if (msg instanceof String) { 
	        String messageString = (String) msg;

	        // Now compare messageString with messages for smart light functions
	        
	        if (messageString.equals("lighton")) {
	        	Platform.runLater(()-> {
	        		clientController.setLabelSmartLight("The Light is Turned ON");
	        		if(clientController.brightnessLabelLightPage != null) {
	        			clientController.brightnessLabelLightPage.setVisible(true);
	        		}
	        	});
	        } 
	        else if (messageString.equals("lightoff")){
	        	Platform.runLater(()-> {
	        		clientController.setLabelSmartLight("The Light is Turned OFF");
	        		if(clientController.brightnessLabelLightPage != null) {
	        			clientController.brightnessLabelLightPage.setVisible(false);
	        		}
	        	});
	        }
	        else if (messageString.equals("lightstatuson")){
	        	Platform.runLater(()->clientController.setLabelSmartLight("The Status of Smart Light is: ON"));
	        }
	        else if (messageString.equals("lightstatusoff")){
	        	Platform.runLater(()->clientController.setLabelSmartLight("The Status of Smart Light is: OFF"));
	        }
	        else if (messageString.contains("0x")){
	        	Platform.runLater(()-> {
	        		clientController.setLabelSmartLight("The Smart Light Color changes to: " + messageString);
	        		clientController.changeTitleColor(messageString);
	        	});
	        }
	        else if (messageString.startsWith("brightness")) {
	            String brightness = messageString.split(":")[1];
	            Platform.runLater(() -> {
	                clientController.adjustBrightnessSlider.setValue(Integer.parseInt(brightness));
	                clientController.brightnessLabelLightPage.setText(brightness + "%");
	            });
	        }
	        else if (messageString.startsWith("lightscheduledon")) {
	        	Platform.runLater(()-> {
	        		clientController.setLightLabelAutomation("The Light is Turned ON");
	        		clientController.brightnessLabelLightPage.setVisible(true);
	        	});
	        }
	        else if (messageString.startsWith("lightscheduledoff")) {
	        	Platform.runLater(()-> {
	        		clientController.setLightLabelAutomation("The Light is Turned OFF");
	        		clientController.brightnessLabelLightPage.setVisible(false);
	        	});
	        }
	        else if (messageString.startsWith("lightcolorscheduledto")) {
	            String color = messageString.substring("lightcolorscheduledto".length());
	            Platform.runLater(() -> {
	            	clientController.setLightLabelAutomation("Light color changed to: " + color);
	                clientController.changeTitleColorAutomation(color);
	                clientController.changeTitleColor(color);
	            });
	        }
	        
	        
	        // Now compare messageString with messages for smart thermostat functions
	        
	        else if (messageString.equals("thermostaton")){
	        	Platform.runLater(()-> {
	        		clientController.setLabelThermostat("The Thermostat is Turned ON");
	        		clientController.showTemperature(true);
	        	});
	        }
	        else if (messageString.equals("thermostatoff")){
	        	Platform.runLater(()-> {
	        		clientController.setLabelThermostat("The Thermostat is Turned OFF");
	        		clientController.showTemperature(false);
	        	});
	        }
	        else if (messageString.equals("cool")){
	        	Platform.runLater(()->clientController.setLabelThermostat("The Mode set for Thermostat is: COOL"));
	        }
	        else if (messageString.equals("heat")){
	        	Platform.runLater(()->clientController.setLabelThermostat("The Mode set for Thermostat is: HEAT"));
	        }
	        else if (messageString.startsWith("thermostatstatuson")){
	        	String modeStatus = messageString.split(":")[1];
	        	Platform.runLater(()->clientController.setLabelThermostat("The Smart Thermostat is ON and the Mode is set to : " + modeStatus));
	        }
	        else if (messageString.startsWith("thermostatstatusoff")){
	        	String modeStatus = messageString.split(":")[1];
	        	Platform.runLater(()->clientController.setLabelThermostat("The Smart Thermostat is OFF and the Mode is set to : " + modeStatus));
	        }
	        else if (messageString.startsWith("temperature")){
	        	String temperature = messageString.replaceAll("[^\\d]", "");
	        	Platform.runLater(()->clientController.setTempeartureLabelThermostat(temperature));
	        }
	        else if (messageString.startsWith("thermostatscheduledon")) {
	        	Platform.runLater(()-> {
	        		clientController.setThermostatLabelAutomation("The Thermostat is Turned ON");
	        		clientController.showTemperature(true);
	        	});
	        }
	        else if (messageString.startsWith("thermostatscheduledoff")) {
	        	Platform.runLater(()-> {
	        		clientController.setThermostatLabelAutomation("The Thermostat is Turned OFF");
	        		clientController.showTemperature(false);
	        	});;
	        }
	        else if (messageString.startsWith("thermostatscheduledcool")) {
	        	Platform.runLater(()-> clientController.setThermostatLabelAutomation("The Thermostat is set to COOL mode"));
	        }
	        else if (messageString.startsWith("thermostatscheduledheat")) {
	        	Platform.runLater(()->clientController.setThermostatLabelAutomation("The Thermostat is set to HEAT mode"));
	        }
	        
	        
	        
	        // Now compare messageString with messages for different lock functions
	        
	        // Compare messageString with messages for break-in alerts
	        else if (messageString.equals("getbreakinalerton")) {
	        	// Perform action when 
	        	Platform.runLater(() -> clientController.setLabelSmartLock("Break-In Alert Set ON"));
	        } 
	        else if (messageString.equals("getbreakinalertoff")) {
	        	// Perform action when 
	        	Platform.runLater(() -> clientController.setLabelSmartLock("Break-In Alert Set OFF"));
	        } 
	        // Compare messageString with messages for break-in
	        else if (messageString.equals("breakin")) {
	        	// Action when the smart lock is in great break-in alert status
	        	Platform.runLater(() -> clientController.setLabelSmartLock("BREAK IN ALERT. There is a possible break-in"));
	        }
	        // Compare messageString with messages for lock
	        else if (messageString.equals("lock")) {
	        	// Action when the smart lock is in lock status
	        	Platform.runLater(() -> clientController.setLabelSmartLock("Smart Lock is LOCKED"));
	        } 
	        // Compare messageString with messages for unlock
	        else if (messageString.equals("unlock")) {
	        	// Action when the smart lock is in locked status
	        	Platform.runLater(() -> clientController.setLabelSmartLock("Smart Lock is UNLOCKED"));
	        }
	        // Compare messageString with messages for status
	        else if (messageString.equals("lockstatuson")) {
	        	// Action when the check the status of the SmartLock
	        	Platform.runLater(() -> clientController.setLabelSmartLock("Status of the Smart Lock: LOCKED"));
        
	        }
	        else if (messageString.equals("lockstatusoff")) {
	        	// Action when the check the status of the SmartLock
	        	Platform.runLater(() -> clientController.setLabelSmartLock("Status of the Smart Lock: UNLOCKED"));
        
	        }
	        else if (messageString.startsWith("smartlockscheduledlock")) {
	        	Platform.runLater(()-> clientController.setSmartLockLabelAutomation("The Smart Lock is LOCKED"));
	        }
	        else if (messageString.startsWith("smartlockscheduledunlock")) {
	        	Platform.runLater(()-> clientController.setSmartLockLabelAutomation("The Smart Lock is UNLOCKED"));
	        }
	        else if (messageString.startsWith("smartlockscheduledbreakinalert")) {
	        	Platform.runLater(()-> {
	        		clientController.setSmartLockLabelAutomation("The Smart Lock Break-In Alert Set ON");
	        		clientController.setBreakInAlertToggleButtonPressed(true);
	        	});
	        }
	        
	        
	        
	        // Now compare messageString with messages for different vacuum robot functions
	        
	        else if (messageString.equals("startedcleaning")) {
	        	// Perform action when 
	        	Platform.runLater(() -> clientController.setLabelVacuumRobot("Vacuum Robot STARTED CLEANING"));
	        } 
	        else if (messageString.equals("alreadycleaning")) {
	        	// Perform action when 
	        	Platform.runLater(() -> clientController.setLabelVacuumRobot("Cleaning Cycle Already in Progress"));
	        } 
	        else if (messageString.equals("dusksackfull")) {
	        	// Perform action when 
	        	Platform.runLater(() -> clientController.setLabelVacuumRobot("Dust Sack Full"));
	        	//Set Alert Here
	        } 
	        else if (messageString.equals("stopedcleaning")) {
	        	// Perform action when 
	        	Platform.runLater(() -> clientController.setLabelVacuumRobot("Vacuum Robot STOPED CLEANING"));
	        } 
	        else if (messageString.equals("nocleaning")) {
	        	// Perform action when 
	        	Platform.runLater(() -> clientController.setLabelVacuumRobot("No Cleaning Cycle To Stop"));
	        }
	        else if (messageString.equals("dustsackalerton")) {
	        	// Perform action when 
	        	Platform.runLater(() -> clientController.setLabelVacuumRobot("Empty Dust Sack Alert ON"));
	        }
	        else if (messageString.equals("dustsackalertoff")) {
	        	// Perform action when 
	        	Platform.runLater(() -> clientController.setLabelVacuumRobot("Empty Dust Sack Alert OFF"));
	        }
	        else if (messageString.equals("vacuumcleaning")) {
	        	// Perform action when 
	        	Platform.runLater(() -> clientController.setLabelVacuumRobot("Vacuum Robot Cleaning"));
	        }
	        else if (messageString.equals("vacuumnotcleaning")) {
	        	// Perform action when 
	        	Platform.runLater(() -> clientController.setLabelVacuumRobot("Vacuum Robot Not Cleaning"));
	        }
	        else if (messageString.startsWith("startcleaningscheduledat")) {
	        	Platform.runLater(()-> clientController.setLabelVacuumRobot("Vacuum Robot STARTED CLEANING"));
	        }
	        
	        
	        
	        // Now compare messageString with messages for different doorbell functions
	        
	        // Compare messageString with messages turn on doorbell
	        else if (messageString.equals("doorbellon")) {
	            // Perform action when the doorbell is on
	        	Platform.runLater(() -> clientController.setLabelSmartDoorbell("Doorbell is ON"));
	        }
	        // Compare messageString with messages turn off doorbell
	        else if (messageString.equals("doorbelloff")) {
	            // Action when doorbell is off
	            Platform.runLater(() -> clientController.setLabelSmartDoorbell("Doorbell is OFF"));
	        } 
	        else if (messageString.equals("cameraon")) {
	            // Action when camera is on and night mode is activated
	        	if(clientController.isNightModeOn() == true) {
		            Platform.runLater(() -> {
		            clientController.setLabelSmartDoorbell("Camera is ON");
		            clientController.playDoorbellCameraVideo(true);
		            });
	        	}
	        	// Action when camera is on and night mode is not activated
	        	else if(clientController.isNightModeOn() == false) {
		            Platform.runLater(() -> {
		            clientController.setLabelSmartDoorbell("Camera is ON");
		            clientController.playDoorbellCameraVideo(false);
		            });
	        	}
	        }
	        else if (messageString.equals("turnondoorbell")) {
	            // Perform action when the doorbell is off
	        	Platform.runLater(() -> clientController.setLabelSmartDoorbell("Turn ON Doorbell to Turn On the Camera"));
	        }
	        else if (messageString.equals("cameraoff")) {
	            // Action when camera is on
	        	Platform.runLater(() -> {
	        		clientController.setLabelSmartDoorbell("Camera is OFF");
	        		clientController.switchSceneDoorbellCameraNormalPage("SmartDoorbell.fxml");
	        	});
	        }
	        else if (messageString.equals("nightmodeon")){
	        	Platform.runLater(()-> {
	        		clientController.setLabelSmartDoorbell("Night Mode ACTIVATED");
	        		clientController.setNightModeOn(true);
	        	
	        	});
	        }
	        else if (messageString.equals("nightmodeoff")){
	        	Platform.runLater(()-> {
	        		clientController.setLabelSmartDoorbell("Night Mode DE-ACTIVATED");
	        		clientController.setNightModeOn(false);
	        	});
	        }
	        else if (messageString.equals("doorbellstatuson")){
	        	Platform.runLater(()->clientController.setLabelSmartDoorbell("The Status of Smart Doorbell is: ON"));
	        }
	        else if (messageString.equals("doorbellstatusoff")){
	        	Platform.runLater(()->clientController.setLabelSmartDoorbell("The Status of Smart Doorbell is: OFF"));
	        }
	        else if (messageString.startsWith("doorbellscheduledon")) {
	        	Platform.runLater(()-> clientController.setSmartDoorbellLabelAutomation("The Smart Doorbell is ON"));
	        }
	        else if (messageString.startsWith("doorbellscheduledoff")) {
	        	Platform.runLater(()-> clientController.setSmartDoorbellLabelAutomation("The Smart Doorbell is OFF"));
	        }
	        else if (messageString.startsWith("nightmodescheduledon")) {
	        	Platform.runLater(()-> {
	        		clientController.setSmartDoorbellLabelAutomation("The Smart Doorbell Night Mode is ACTIVATED");
	        		clientController.setActivateNightModeToggleButton(true);
	        	});
	        }
		}
		else if (msg instanceof Integer) {
			//To set thermostat temperature
			Integer messageInt = (Integer) msg;
			String temperature = messageInt.toString();
			Platform.runLater(()->clientController.setTempeartureLabelThermostat(temperature));
		 }
		else {
			// Message received is not a string
			System.out.println("Message received is not of String or Integer type: " + msg);
		}
	
	}
}
// End of SHClient Class