public class SmartDoorbell extends SmartDevice {

	// Doorbell-specific attributes
	private boolean isCameraOn;
	private boolean isNightModeOn;

    public SmartDoorbell(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	public boolean isCameraOn() {
		return isCameraOn;
	}


	public void setCameraOn(boolean isCameraOn) {
		this.isCameraOn = isCameraOn;
	}

	public boolean isNightModeOn() {
		return isNightModeOn;
	}


	public void setNightModeOn(boolean isNightModeOn) {
		this.isNightModeOn = isNightModeOn;
	}

}
// End of SmartDoorbell class