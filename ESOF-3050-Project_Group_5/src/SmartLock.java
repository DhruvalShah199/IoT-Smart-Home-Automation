/**
* The SmartLock contains all the methods necassary for 
* the functions of that device.
* 
* @author Dhruval Harshilkumar Shah
* @version December 2023
*/


public class SmartLock extends SmartDevice {

	// Lock-specific attributes
	private String lockedOrUnlocked;
	private boolean breakInAlert;

    public SmartLock(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

    
    public void lockUnlock(String lockedOrUnlocked) {
        // Logic to lock or unlock the smart lock
    	if(lockedOrUnlocked == "lock") {
    		this.setLockedOrUnlocked("lock");
    	}
    	else if(lockedOrUnlocked == "unlock") {
    		this.setLockedOrUnlocked("unlock");
    	}
    }


	public String getLockedOrUnlocked() {
		return lockedOrUnlocked;
	}


	public void setLockedOrUnlocked(String lockedOrUnlocked) {
		this.lockedOrUnlocked = lockedOrUnlocked;
	}


	public boolean isBreakInAlert() {
		return breakInAlert;
	}


	public void setBreakInAlert(boolean breakInAlert) {
		this.breakInAlert = breakInAlert;
	}

}
// End of SmartLock class