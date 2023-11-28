import java.util.Timer;
import java.util.TimerTask;

public class VacuumRobot extends SmartDevice {

	// Vacuum Robot-specific attributes
	private static final long CLEANING_CYCLE_TIME = 30000; // 30 seconds in milliseconds
    private static final int MAX_CLEANING_CYCLES_BEFORE_EMPTYING = 2;
    private Timer timer = new Timer();
    private boolean isCleaning = false;
    private int cleaningCyclesCompleted = 0;
    private boolean dustSackFull = false;
    private boolean dustSackAlertOn = false;

    public VacuumRobot(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

    
    public synchronized String startCleaning() {
        // Logic to tell vacuum to start cleaning
    	String cleaning;
    	if (isCleaning) {
            cleaning = "alreadycleaning";
        } else if (dustSackFull && dustSackAlertOn) {
            cleaning = "dusksackfull";
        } else {
            isCleaning = true;
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    cleaningCycleCompleted();
                }
            };
            timer.schedule(task, CLEANING_CYCLE_TIME);
            cleaning = "startedcleaning";
        }
    	return cleaning;
    }

    
    public synchronized String stopCleaning() {
    	// Logic to tell vacuum to stop cleaning
    	String cleaning;
    	if (isCleaning) {
            isCleaning = false;
            timer.cancel(); // Stop the cleaning immediately
            timer = new Timer(); // Timer needs to be reset after cancellation
            cleaning = "stopedcleaning";
        } else {
            cleaning = "nocleaning";
        }
    	return cleaning;
    }
    
    public void cleaningCycleCompleted() {
        isCleaning = false;
        cleaningCyclesCompleted++;
        if (cleaningCyclesCompleted < MAX_CLEANING_CYCLES_BEFORE_EMPTYING) {
        	System.out.println("cleaning cycle completed");
        }
        else if (cleaningCyclesCompleted >= MAX_CLEANING_CYCLES_BEFORE_EMPTYING) {
            dustSackFull = true;
        }
    }

//    public synchronized void emptyDustSack() {
//        if (dustSackFull) {
//            dustSackFull = false;
//            cleaningCyclesCompleted = 0;
//            System.out.println("Dust sack has been emptied.");
//            // Send a confirmation message to the server/client if needed
//        }
//    }

	public String setDustSackAlert(boolean alertOn) {
		// Logic to set the empty dust sack alert
		String dustSackAlert;
        this.dustSackAlertOn = alertOn;
        dustSackAlert = "dustsackalert" + (alertOn ? "on" : "off");
        return dustSackAlert;
    }
	
	// Method to check if the dust sack alert is on
    public boolean isDustSackAlertOn() {
        return dustSackAlertOn;
    }

    public boolean isCleaning() {
        return isCleaning;
    }
    
    public boolean isDustSackFull() {
        return dustSackFull;
    }
}
// End of VacuumRobot class