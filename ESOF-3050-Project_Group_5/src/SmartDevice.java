/**
* The SmartDevice contains all the common methods 
* necassary for all the devices and also to set 
* the devices' statuses and ids.
*  
* 
* @author Dhruval Harshilkumar Shah
* @version December 2023
*/


public abstract class SmartDevice {
    // Common attributes for all smart devices
    private int id;
    private boolean isOn;
    
//    public abstract String getStatus();
    
    public SmartDevice(Integer id) {
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
	public int getId() {
		return id;
	}

	public void setId(int id) {
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