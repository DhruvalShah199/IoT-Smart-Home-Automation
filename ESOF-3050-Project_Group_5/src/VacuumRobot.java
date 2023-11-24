import java.util.Timer;
import java.util.TimerTask;

public class VacuumRobot extends SmartDevice {

	// Vacuum Robot-specific attributes
	private static final long CLEANING_CYCLE_TIME = 120000; // 2 minutes in milliseconds
    private static final int MAX_CLEANING_CYCLES_BEFORE_EMPTYING = 2;
    private Timer timer = new Timer();
    private boolean isCleaning = false;
    private int cleaningCyclesCompleted = 0;
    private boolean dustSackFull = false;
    private boolean dustSackAlertOn = false;

    public VacuumRobot(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

    
    public synchronized void startCleaning() {
        // Logic to tell vacuum to start cleaning
    	if (isCleaning) {
            System.out.println("Cleaning is already in progress.");
        } else if (dustSackFull) {
            System.out.println("Dust sack is full. Please empty the dust sack before starting a new cleaning cycle.");
            if (dustSackAlertOn) {
                sendAlert("Dust sack full. Please empty to continue cleaning.");
            }
        } else {
            isCleaning = true;
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    cleaningCycleCompleted();
                }
            };
            timer.schedule(task, CLEANING_CYCLE_TIME);
            System.out.println("Cleaning cycle started.");
        }
    }

    
    public synchronized void stopCleaning() {
    	// Logic to tell vacuum to stop cleaning
    	if (isCleaning) {
            isCleaning = false;
            timer.cancel(); // Stop the cleaning immediately
            timer = new Timer(); // Timer needs to be reset after cancellation
            System.out.println("Cleaning cycle stopped.");
        } else {
            System.out.println("No cleaning cycle is in progress to stop.");
        }
    }
    
    private void cleaningCycleCompleted() {
        isCleaning = false;
        cleaningCyclesCompleted++;
        System.out.println("Cleaning cycle completed.");
        if (cleaningCyclesCompleted >= MAX_CLEANING_CYCLES_BEFORE_EMPTYING) {
            dustSackFull = true;
            if (dustSackAlertOn) {
                sendAlert("Dust sack full. Please empty to continue cleaning.");
            }
        }
    }

	public void emptyDustSackAlert() {
		// Logic to send alert to empty dust sack
		dustSackFull = false;
        cleaningCyclesCompleted = 0;
        System.out.println("Dust sack has been emptied.");
	}

	public void setDustSackAlert(boolean alertOn) {
		// Logic to set the empty dust sack alert
        this.dustSackAlertOn = alertOn;
        System.out.println("Dust sack alert is " + (alertOn ? "on" : "off"));
    }

    public boolean isCleaning() {
        return isCleaning;
    }
}
// End of VacuumRobot class