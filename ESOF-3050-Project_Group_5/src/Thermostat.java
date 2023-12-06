/**
* The Thermostat contains all the methods necassary for 
* the functions of that device.
* 
* @author Dhruval Harshilkumar Shah
* @version December 2023
*/


public class Thermostat extends SmartDevice {

	// Thermostat-specific attributes
	private String mode;
	private Integer temperature;

    public Thermostat(int id) {
		super(id);
		this.temperature = 25;
		// TODO Auto-generated constructor stub
	}
    
    public void setTemperature(String temperature) {
    	// Logic to set temperature of the thermostat
    	if(temperature.equals("increase") && this.temperature <= 35) {
    			this.temperature++;
    	}
    	else if (temperature.equals("decrease") && this.temperature >= 15) {
    		this.temperature--;
    	}
    }
    
    public int getTemperature() {
    	return this.temperature;
    }
    
    public void setMode(String mode) {
    	this.mode = mode;
    }
    
    public String getMode() {
    	return this.mode;
    }

}
//End of Thermostat class