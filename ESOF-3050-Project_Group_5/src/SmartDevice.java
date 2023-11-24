public abstract class SmartDevice {
    // Common attributes for all smart devices
    private String id;
    private boolean isOn;
    
//    public abstract String getStatus();
    
    public SmartDevice(String id) {
        this.setId(id);
        this.setOn(false);
    }

    // Common methods
    public void turnOnOff(String checkOnOff) {
    	if(checkOnOff == "on") {
    		isOn = true;
    	}
    	else if (checkOnOff == "off") {
    		isOn = false;
    	}
    }
    
    public boolean displayStatus() {
    	boolean status = false;
    	if(isOn == true) {
    		status = true;
    	}
    	else if (isOn == false) {
    		status = false;
    	}
    	return status;
    }
    
    
    //Setters and getters for all the attributes
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isOn() {
		return isOn;
	}

	public void setOn(boolean isOn) {
		this.isOn = isOn;
	}
}
// End of SmartDevice class