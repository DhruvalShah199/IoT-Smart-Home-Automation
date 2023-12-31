/**
* The SmartLight contains all the methods necassary for 
* the functions of that device.
* 
* @author Dhruval Harshilkumar Shah
* @version December 2023
*/


public class SmartLight extends SmartDevice {
    // Light-specific attributes
    private int brightness;
    private String color = "0xffffff";

    public SmartLight(int id) {
        super(id);
        this.brightness = 100;
        this.color = "0xffffff";
        // Initialize attributes
    }
    
    public String getStatus() {
        return isOn() ? "on:" + getBrightness() : "off";
    }

    public int getBrightness() {
        return brightness;
    }

    public void adjustBrightness(int brightness) {
        // Set the brightness, assuming values are between 0 and 100
        if (brightness >= 0 && brightness <= 100) {
            this.brightness = brightness;
        }
    }
    
    public String getColor() {
        return color;
    }

    public void changeColor(String color) {
        this.color = color;
    }

}
// End of SmartLight class