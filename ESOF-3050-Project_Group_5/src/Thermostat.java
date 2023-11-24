public class Thermostat extends SmartDevice {

	// Thermostat-specific attributes
	private String mode;
	private Integer temperature;

    public Thermostat(String id) {
		super(id);
		this.temperature = 25;
		// TODO Auto-generated constructor stub
	}
    
    public void setTemperature(String temperature) {
    	// Logic to set temperature of the thermostat
    	if(temperature.equals("increase")) {
    		this.temperature++;
    	}
    	else if (temperature.equals("decrease")) {
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