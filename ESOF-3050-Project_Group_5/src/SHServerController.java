public class SHServerController {
	
	private SmartLight light;
    private SmartLock lock;
    private Thermostat thermostat;
    private VacuumRobot vacuumRobot;
    private SmartDoorbell doorbell;

    public SHServerController() {
        // Initialize each device
        light = new SmartLight("light1");
        lock = new SmartLock("lock1");
        thermostat = new Thermostat("thermostat1");
        vacuumRobot = new VacuumRobot("vacuum1");
        doorbell = new SmartDoorbell("doorbell1");
    }

    //Methods for Smart Light
    public void turnOnLight(String onOrOff) {
        if (light != null) {
            light.turnOnOff(onOrOff);
        }
    }
    
    public boolean getStatus() {
    	return light.isOn();
	}
    
    public void turnOffLight(String onOrOff) {
        if (light != null) {
            light.turnOnOff(onOrOff);
        }
    }
    
    // Method to adjust light brightness
    public void adjustLightBrightness(int brightness) {
        if (light != null) {
            light.adjustBrightness(brightness);
        }
    }

    // Method to get the current brightness of the light
    public int getLightBrightness() {
        return light.getBrightness();
    }

    
    public void changeLightColor(String color) {
    	if (light != null) {
    		light.changeColor(color);
    	}
    }
    
    public boolean displayLightStatus() {
    	if (light != null) {
    		return light.isOn();
    	}
    	return false;
    }

    
    //Methods for Smart Lock    
    public String lockDoor(String isLockedUnlocked) {
        if (lock != null) {
        	lock.turnOnOff("on");
            lock.lockUnlock(isLockedUnlocked);
        }
        return lock.getLockedOrUnlocked();
    }

    public String unlockDoor(String isLockedUnlocked) {
        if (lock != null) {
        	lock.turnOnOff("on");
            lock.lockUnlock(isLockedUnlocked);
        }
        return lock.getLockedOrUnlocked();
    }
    
    public boolean getBreakInAlert(boolean breakIn) {
        if (lock != null) {
            lock.setBreakInAlert(breakIn);
        }
        boolean setBreakIn = lock.isBreakInAlert();
        return setBreakIn;
    }
    public boolean displayLockStatus() {
    	if (lock != null) {
    		if(lock.getLockedOrUnlocked() == "lock")
    		{
    			return true;
    		}
    	}
    	return false;
	}
    
    
    
    //Methods for Thermostat
    public int getThermostatTemperature() {
    	int temperature = 0;
    	if (thermostat != null) {
    		temperature = thermostat.getTemperature();
        }
		return temperature;
    }
    
    public void turnOnThermostat(String onOrOff) {
        if (thermostat != null) {
            thermostat.turnOnOff(onOrOff);
        }
    }
    
    public void turnOffThermostat(String onOrOff) {
        if (thermostat != null) {
            thermostat.turnOnOff(onOrOff);
        }
    }
    
    public String changeThermostatMode(String mode) {
        if (thermostat != null) {
            thermostat.setMode(mode);
        }
        return thermostat.getMode();
    }
    
    public int increaseOrDecreaseThermostatTemperature(String increaseOrDecrease) {
        if (thermostat != null) {
            thermostat.setTemperature(increaseOrDecrease);
        }
        return thermostat.getTemperature();
    }
    
    public boolean displayThermostatStatus() {
    	if (thermostat != null) {
    		return thermostat.isOn();
    	}
    	return false;
	}

    
    //Methods for Vacuum Robot
    public String startCleaning() {
    	String isCleaning = "";
        if (vacuumRobot != null) {
            isCleaning = vacuumRobot.startCleaning();
        }
        return isCleaning;
    }

    public String stopCleaning() {
    	String isCleaning = "";
        if (vacuumRobot != null) {
        	isCleaning = vacuumRobot.stopCleaning();
        }
        return isCleaning;
    }
    
    public String emptyDustSackVacuumRobotAlert(boolean emptyDustSack) {
    	String alert = "";
        if (vacuumRobot != null) {
        	alert =vacuumRobot.setDustSackAlert(emptyDustSack);
        }
        return alert;
    }
    
    public String cleaningCycleComplete() {
    	String complete = "";
    	if (vacuumRobot != null) {
    		complete = vacuumRobot.cleaningCycleCompleted();
    	}
    	return complete;
    }
    
    public boolean displayVacuumRobotStatus() {
    	if (vacuumRobot != null) {
    		return vacuumRobot.isCleaning();
    	}
    	return false;
	}
    
    
    
    //Methods for Smart Doorbell
    public void turnOnDoorbell(String onOrOff) {
        if (doorbell != null) {
            doorbell.turnOnOff(onOrOff);
        }
    }
    
    public void turnOffDoorbell(String onOrOff) {
        if (doorbell != null) {
            doorbell.turnOnOff(onOrOff);
        }
    }
    
    public void turnOnCameraDoorbell(boolean isCameraOn) {
        if (doorbell != null) {
            doorbell.turnOnCamera(isCameraOn);
        }
    }
    
    public void activateNightModeDoorbell(boolean isNightModeOn) {
    	if (doorbell != null) {
    		doorbell.activateNightMode(isNightModeOn);
    	}
    }

}

//End of SHServerController Class