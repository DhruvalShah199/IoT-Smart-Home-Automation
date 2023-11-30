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
    
    
    
    //-----------------------------------Methods for Smart Light-----------------------------------
    public void turnOnLight(String onOrOff) {
        if (deviceMap.containsKey(0)) {
            ((SmartLight) deviceMap.get(0)).turnOnOff(onOrOff);
        }
    }
    
    public boolean getStatus() {
    	return ((SmartLight) deviceMap.get(0)).isOn();
	}
    
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

//    // Method to turn on the light, modified to only set the status
//    public void turnOnLight() {
//        if (deviceMap.containsKey(0)) {
//        	turnOnLight("on");
//        }
//    }

    
    
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
    
    public int displayTemperature() {
    	int temperature = 0;
    	if (deviceMap.containsKey(2)) {
    		temperature =  ((Thermostat) deviceMap.get(2)).getTemperature();
    	}
    	return temperature;
    }

    
    //---------------------------------Methods for Vacuum Robot-------------------------
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

}

//End of SHServerController Class