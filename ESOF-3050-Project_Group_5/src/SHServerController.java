import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import com.lloseng.ocsf.server.ConnectionToClient;

public class SHServerController {
	private Map<Integer, SmartDevice> deviceMap;
	private SmartDevice light;
    private SmartDevice lock;
    private SmartDevice thermostat;
    private SmartDevice vacuumRobot;
    private SmartDevice doorbell;

    public SHServerController() {
        // Initialize each device
    	deviceMap = new HashMap<>();
        
    	light = new SmartLight(0);
        lock = new SmartLock(1);
        thermostat = new Thermostat(2);
        vacuumRobot = new VacuumRobot(3);
        doorbell = new SmartDoorbell(4);
        
        deviceMap.put(light.getId(), light);
        deviceMap.put(lock.getId(), lock);
        deviceMap.put(thermostat.getId(), thermostat);
        deviceMap.put(vacuumRobot.getId(), vacuumRobot);
        deviceMap.put(doorbell.getId(), doorbell);
    }
    
    private long calculateDelay(int hour, int minute) {
        // Calculate the delay until the specified time today in milliseconds
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        long delay = calendar.getTimeInMillis() - System.currentTimeMillis();
        // If the time has already passed for today, schedule for tomorrow
        if (delay < 0) {
            delay += TimeUnit.DAYS.toMillis(1);
        }
        return delay;
    }
    
    
    
//-----------------------------------Methods for Smart Light-----------------------------------
    // Method to turn on the light
    public void turnOnLight(String onOrOff) {
        if (deviceMap.containsKey(0)) {
            ((SmartLight) deviceMap.get(0)).turnOnOff(onOrOff);
        }
    }
    
    // Method to get light status
    public boolean getStatus() {
    	return ((SmartLight) deviceMap.get(0)).isOn();
	}
    
    // Method to turn off the light
    public void turnOffLight(String onOrOff) {
        if (deviceMap.containsKey(0)) {
        	((SmartLight) deviceMap.get(0)).turnOnOff(onOrOff);
        }
    }
    
    // Method to adjust light brightness
    public void adjustLightBrightness(int brightness) {
        if (deviceMap.containsKey(0)) {
        	((SmartLight) deviceMap.get(0)).adjustBrightness(brightness);
        }
    }

    // Method to get the current brightness of the light
    public int getLightBrightness() {
        return ((SmartLight) deviceMap.get(0)).getBrightness();
    }

    // Method to change light color
    public void changeLightColor(String color) {
    	if (deviceMap.containsKey(0)) {
    		((SmartLight) deviceMap.get(0)).changeColor(color);
    	}
    }
    
    public String displayLightColor() {
    	String color = "";
    	if (deviceMap.containsKey(0)) {
    		color = ((SmartLight) deviceMap.get(0)).getColor();
    	}
    	return color;
    }
    
    public boolean displayLightStatus() {
    	if (deviceMap.containsKey(0)) {
    		return ((SmartLight) deviceMap.get(0)).isOn();
    	}
    	return false;
    }
    
    // Method to schedule turning on the light
    public void scheduleLightOn(String time, ConnectionToClient client) {
        Timer timer = new Timer();
        String[] timeParts = time.split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);

        // Calculate delay until the event
        long delay = calculateDelay(hour, minute);

        // Schedule the timer task
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Turn on the light
                turnOnLight("on");
                try {
                    client.sendToClient("lightscheduledon" + time);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, delay);
    }
    
    // Method to schedule turning off the light
    public void scheduleLightOff(String time, ConnectionToClient client) {
        Timer timer = new Timer();
        String[] timeParts = time.split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);

        // Calculate delay until the event
        long delay = calculateDelay(hour, minute);

        // Schedule the timer task
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Turn off the light
                turnOffLight("off");
                try {
                    client.sendToClient("lightscheduledoff" + time);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, delay);
    }
    
    // Method to schedule changing light color
    public void scheduleLightColorChange(String time, String color, ConnectionToClient client) {
        Timer timer = new Timer();
        String[] timeParts = time.split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);

        // Calculate delay until the event
        long delay = calculateDelay(hour, minute);

        // Schedule the timer task
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Change the color of the light
                changeLightColor(color);
                try {
                    client.sendToClient("lightcolorscheduledto" + displayLightColor());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, delay);
    }
    
    
    
//----------------------------------Methods for Thermostat------------------------------
    public int getThermostatTemperature() {
    	int temperature = 0;
    	if (deviceMap.containsKey(2)) {
    		temperature = ((Thermostat) deviceMap.get(2)).getTemperature();
        }
		return temperature;
    }
    
    public void turnOnThermostat(String onOrOff) {
        if (deviceMap.containsKey(2)) {
        	((Thermostat) deviceMap.get(2)).turnOnOff(onOrOff);
        }
    }
    
    public void turnOffThermostat(String onOrOff) {
        if (deviceMap.containsKey(2)) {
        	((Thermostat) deviceMap.get(2)).turnOnOff(onOrOff);
        }
    }
    
    public String changeThermostatMode(String mode) {
        if (deviceMap.containsKey(2)) {
        	((Thermostat) deviceMap.get(2)).setMode(mode);
        }
        return ((Thermostat) deviceMap.get(2)).getMode();
    }
    
    public int increaseOrDecreaseThermostatTemperature(String increaseOrDecrease) {
        if (deviceMap.containsKey(2)) {
        	((Thermostat) deviceMap.get(2)).setTemperature(increaseOrDecrease);
        }
        return ((Thermostat) deviceMap.get(2)).getTemperature();
    }
    
    public boolean displayThermostatStatus() {
    	if (deviceMap.containsKey(2)) {
    		return ((Thermostat) deviceMap.get(2)).isOn();
    	}
    	return false;
	}
    
    public String displayThermostatMode() {
    	String mode = "";
    	if (deviceMap.containsKey(2)) {
    		mode = ((Thermostat) deviceMap.get(2)).getMode();
    	}
    	return mode;
	}
    
    public int displayTemperature() {
    	int temperature = 0;
    	if (deviceMap.containsKey(2)) {
    		temperature =  ((Thermostat) deviceMap.get(2)).getTemperature();
    	}
    	return temperature;
    }
    
    // Method to schedule turning on the thermostat
    public void scheduleThermostatOn(String time, ConnectionToClient client) {
        Timer timer = new Timer();
        String[] timeParts = time.split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);

        // Calculate delay until the event
        long delay = calculateDelay(hour, minute);

        // Schedule the timer task
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Turn on the thermostat
            	turnOnThermostat("on");
                try {
                    client.sendToClient("thermostatscheduledon" + time);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, delay);
    }
    
    // Method to schedule turning off the thermostat
    public void scheduleThermostatOff(String time, ConnectionToClient client) {
        Timer timer = new Timer();
        String[] timeParts = time.split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);

        // Calculate delay until the event
        long delay = calculateDelay(hour, minute);

        // Schedule the timer task
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Turn off the thermostat
            	turnOffThermostat("off");
                try {
                    client.sendToClient("thermostatscheduledoff" + time);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, delay);
    }
    
    // Method to schedule changing mode to cool for the thermostat
    public void scheduleThermostatModeCool(String time, ConnectionToClient client) {
        Timer timer = new Timer();
        String[] timeParts = time.split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);

        // Calculate delay until the event
        long delay = calculateDelay(hour, minute);

        // Schedule the timer task
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Change the thermostat mode to cool
            	changeThermostatMode("cool");
                try {
                    client.sendToClient("thermostatscheduledcool" + time);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, delay);
    }
    
 // Method to schedule changing mode to heat for the thermostat
    public void scheduleThermostatModeHeat(String time, ConnectionToClient client) {
        Timer timer = new Timer();
        String[] timeParts = time.split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);

        // Calculate delay until the event
        long delay = calculateDelay(hour, minute);

        // Schedule the timer task
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Change the thermostat mode to heat
            	changeThermostatMode("heat");
                try {
                    client.sendToClient("thermostatscheduledheat" + time);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, delay);
    }

    
    
//-----------------------------------Methods for Smart Lock-------------------------------------    
    public String lockDoor(String isLockedUnlocked) {
        if (deviceMap.containsKey(1)) {
        	((SmartLock) deviceMap.get(1)).turnOnOff("on");
        	((SmartLock) deviceMap.get(1)).lockUnlock(isLockedUnlocked);
        }
        return ((SmartLock) deviceMap.get(1)).getLockedOrUnlocked();
    }

    public String unlockDoor(String isLockedUnlocked) {
        if (deviceMap.containsKey(1)) {
        	((SmartLock) deviceMap.get(1)).turnOnOff("on");
        	((SmartLock) deviceMap.get(1)).lockUnlock(isLockedUnlocked);
        }
        return ((SmartLock) deviceMap.get(1)).getLockedOrUnlocked();
    }
    
    public boolean getBreakInAlert(boolean breakIn) {
        if (deviceMap.containsKey(1)) {
        	((SmartLock) deviceMap.get(1)).setBreakInAlert(breakIn);
        }
        boolean setBreakIn = ((SmartLock) deviceMap.get(1)).isBreakInAlert();
        return setBreakIn;
    }
    public boolean displayLockStatus() {
    	if (deviceMap.containsKey(1)) {
    		if(((SmartLock) deviceMap.get(1)).getLockedOrUnlocked() == "lock")
    		{
    			return true;
    		}
    	}
    	return false;
	}
    // Method to schedule lock on the smart lock
    public void scheduleLockSmartLock(String time, ConnectionToClient client) {
        Timer timer = new Timer();
        String[] timeParts = time.split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);

        // Calculate delay until the event
        long delay = calculateDelay(hour, minute);

        // Schedule the timer task
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Lock the smart lock
            	lockDoor("lock");
                try {
                    client.sendToClient("smartlockscheduledlock" + time);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, delay);
    }
    
    // Method to schedule unlock on the smart lock
    public void scheduleUnlockSmartLock(String time, ConnectionToClient client) {
    	Timer timer = new Timer();
        String[] timeParts = time.split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);

        // Calculate delay until the event
        long delay = calculateDelay(hour, minute);

        // Schedule the timer task
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Unlock the smart lock
            	unlockDoor("unlock");
                try {
                    client.sendToClient("smartlockscheduledunlock" + time);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, delay);
    }
    
    // Method to schedule set break-in alert on the smart lock
    public void scheduleBreakInAlertSmartLock(String time, ConnectionToClient client) {
    	Timer timer = new Timer();
        String[] timeParts = time.split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);

        // Calculate delay until the event
        long delay = calculateDelay(hour, minute);

        // Schedule the timer task
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Set break-in alert for smart lock
            	getBreakInAlert(true);
                try {
                    client.sendToClient("smartlockscheduledbreakinalert" + time);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, delay);
    }
    
    

//---------------------------------Methods for Vacuum Robot----------------------------
    public String startCleaning() {
    	String isCleaning = "";
        if (deviceMap.containsKey(3)) {
            isCleaning = ((VacuumRobot) deviceMap.get(3)).startCleaning();
        }
        return isCleaning;
    }

    public String stopCleaning() {
    	String isCleaning = "";
        if (deviceMap.containsKey(3)) {
        	isCleaning = ((VacuumRobot) deviceMap.get(3)).stopCleaning();
        }
        return isCleaning;
    }
    
    public String emptyDustSackVacuumRobotAlert(boolean emptyDustSack) {
    	String alert = "";
        if (deviceMap.containsKey(3)) {
        	alert = ((VacuumRobot) deviceMap.get(3)).setDustSackAlert(emptyDustSack);
        }
        return alert;
    }
    
    public boolean displayVacuumRobotStatus() {
    	if (deviceMap.containsKey(3)) {
    		return ((VacuumRobot) deviceMap.get(3)).isCleaning();
    	}
    	return false;
	}
    
 // Method to schedule start cleaning for the vacuum robot
    public void scheduleVacuumCleaning(String time, ConnectionToClient client) {
        Timer timer = new Timer();
        String[] timeParts = time.split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);

        // Calculate delay until the event
        long delay = calculateDelay(hour, minute);

        // Schedule the timer task
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Start cleaning on the vacuum robot
            	startCleaning();
                try {
                    client.sendToClient("startcleaningscheduledat" + time);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, delay);
    }
    
    
    
//--------------------------------Methods for Smart Doorbell-----------------------------
    public void turnOnDoorbell(String onOrOff) {
        if (deviceMap.containsKey(4)) {
        	((SmartDoorbell) deviceMap.get(4)).turnOnOff(onOrOff);
        }
    }
    
    public void turnOffDoorbell(String onOrOff) {
        if (deviceMap.containsKey(4)) {
        	((SmartDoorbell) deviceMap.get(4)).turnOnOff(onOrOff);
        }
    }
    
    public void turnOnCameraDoorbell(boolean isCameraOn) {
        if (deviceMap.containsKey(4)) {
        	((SmartDoorbell) deviceMap.get(4)).setCameraOn(isCameraOn);
        }
    }
    
    public boolean activateNightModeDoorbell(boolean isNightModeOn) {
    	if (deviceMap.containsKey(4)) {
    		((SmartDoorbell) deviceMap.get(4)).setNightModeOn(isNightModeOn);
    	}
    	return ((SmartDoorbell) deviceMap.get(4)).isNightModeOn();
    }
    
    public boolean displayDoorbellStatus() {
    	if (deviceMap.containsKey(4)) {
    		return ((SmartDoorbell) deviceMap.get(4)).isOn();
    	}
    	return false;
    }
    
 // Method to schedule turning on the doorbell
    public void scheduleDoorbellOn(String time, ConnectionToClient client) {
        Timer timer = new Timer();
        String[] timeParts = time.split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);

        // Calculate delay until the event
        long delay = calculateDelay(hour, minute);

        // Schedule the timer task
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Turn on the doorbell
            	turnOnDoorbell("on");
                try {
                    client.sendToClient("doorbellscheduledon" + time);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, delay);
    }
    
 // Method to schedule turning off the doorbell
    public void scheduleDoorbellOff(String time, ConnectionToClient client) {
        Timer timer = new Timer();
        String[] timeParts = time.split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);

        // Calculate delay until the event
        long delay = calculateDelay(hour, minute);

        // Schedule the timer task
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Turn off the doorbell
            	turnOffDoorbell("off");
                try {
                    client.sendToClient("doorbellscheduledoff" + time);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, delay);
    }
    
 // Method to schedule turning on the doorbell night mode
    public void scheduleNightModeAt(String time, ConnectionToClient client) {
        Timer timer = new Timer();
        String[] timeParts = time.split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);

        // Calculate delay until the event
        long delay = calculateDelay(hour, minute);

        // Schedule the timer task
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Activate the doorbell night mode
            	activateNightModeDoorbell(true);
                try {
                    client.sendToClient("nightmodescheduledon" + time);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, delay);
    }
}

//End of SHServerController Class