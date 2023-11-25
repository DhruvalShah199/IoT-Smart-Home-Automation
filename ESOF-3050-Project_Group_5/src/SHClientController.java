import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.control.TextField;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.scene.control.Slider;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;

public class SHClientController {
	private SHClient client;
	private Map<String, Scene> sceneCache = new HashMap<>();
	
	public synchronized void setClientConnection(SHClient client) {
	    if (client != null) {
	        this.client = client;
	        System.out.println("Client connection set: " + client);
	    } else {
	        System.out.println("Error: Client connection is null.");
	    }
	}
	//
	
//	public String userAdminLogin;
//	 public void setUserInfo() {
//		 
//	 }
//	public String getUserInfo() {
//		return this.userAdminLogin;
//	}
	
	
	public String userAdmin;
	
	public void userAdminLogin(String userOrAdmin) {
		userAdmin = userOrAdmin;
	}
	
	//-----------------------------Smart Home Automation Page-------------------------------------------
   
	@FXML
    private Button connectButtonSmartHomeAutomationPage;

    @FXML
    private TextField iPTextField1;

    @FXML
    private TextField iPTextField2;

    @FXML
    private TextField iPTextField3;

    @FXML
    private TextField iPTextField4;

    @FXML
    private TextField portTextField;

    @FXML
    private Label smartHomeAutomationLabelHidden;

    @FXML
    private Pane smartHomeAutomationPane;

    @FXML
    void connectButtonSmartHomeAutomationPage(ActionEvent event) {
        String ipAddress = iPTextField1.getText().trim() + "." + iPTextField2.getText().trim() + "." + iPTextField3.getText().trim() + "." + iPTextField4.getText().trim();
        String portString = portTextField.getText().trim();
        
        if(ipAddress.equals("172.17.6.174") && portString.equals("2010")) {
        	int port = Integer.parseInt(portString);
	        try {
	            SHClient client = new SHClient(ipAddress, port, this);
	
	            if (client.connectionToServer()) {
	                // Connection is successful, set the client connection
	            	System.out.println("Connection Successful! Connected to the server.");
	                setClientConnection(client);
	                
	                // Load the next scene
	                FXMLLoader loader = new FXMLLoader(getClass().getResource("Welcome.fxml"));
	                loader.setController(this); // Maintain the same controller instance
	                Parent root = loader.load();
	                
	                Scene scene = new Scene(root); 
	                Stage stage = (Stage) smartHomeAutomationPane.getScene().getWindow();
	                stage.setScene(scene);
	            } 
	            else {
	                // Handle unsuccessful connection
	                System.out.println("Could not connect to server. Check if the server is running and the IP address and port are correct.");
	                showAlert(Alert.AlertType.ERROR, "Connection Failed", "Could not connect to server. Check if the server is running and the IP address and port are correct.");
	            }
	        } 
	        catch (NumberFormatException e) {
	        	showAlert(Alert.AlertType.ERROR, "Invalid Port", "Please enter a valid port number.");
	        }
	        catch (IOException e) {
	            showAlert(Alert.AlertType.ERROR, "Connection Failed", "Cannot connect to server: " + e.getMessage());
	        }
	        catch (Exception e) {
	            System.out.println("An error occurred: " + e.getMessage());
	            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred: " + e.getMessage());
	        }
        }
    	else {
    		System.out.println("Enter correct IP Address or Port Number");
    		showAlert(Alert.AlertType.ERROR, "Invalid Port or IP Address", "Please enter correct IP address or a valid port number.");
    	}
    }
    
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


	
    //-----------------------------Welcome Page-------------------------------------------
	
    @FXML
    private Pane WelcomePane;

    @FXML
    private Button logInButtonWelcomePage;

    @FXML
    private Button signUpButtonWelcomePage;

    @FXML
    private Label welcomeTextField;
    
    private void switchSceneWelcomePage(String fxmlFileName) {
        try {
            Scene scene = sceneCache.computeIfAbsent(fxmlFileName, fxml -> {
                try {
                    // Create a loader for the FXML
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
                    
                    // Set the current instance as the controller
                    loader.setController(this);
                    
                    // Load the FXML file
                    Parent root = loader.load();
                    
                    // Return the created scene
                    return new Scene(root);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            });

            Stage stage = (Stage) WelcomePane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void logInButtonPressedWelcomePage(ActionEvent event) {
    	// Handle login button click
    	// Switch scene to the login page here
    	switchSceneWelcomePage("LoginPage.fxml");
    }

    @FXML
    void signUpButtonPressedWelcomePage(ActionEvent event) {
    	// Handle signup button click
    	// Switch scene to the signup page here
    	switchSceneWelcomePage("Signup.fxml");
    }
    
    
    //-----------------------------Sign Up Page-------------------------------------------
    
    @FXML
    private Pane SignUpPane;

    @FXML
    private Label confirmPasswordLabelSignUp;

    @FXML
    private TextField confirmPasswordTextFieldSignUp;

    @FXML
    private Label createNewPasswordLabel;

    @FXML
    private Label createPasswordLabelSignUp;

    @FXML
    private TextField createPasswordTextFieldSignUp;

    @FXML
    private Button loginButtonSignUp;

    @FXML
    private Button signUpButtonSignupPage;

    @FXML
    private Polygon signUpPolygon;

    @FXML
    private Label usernameLabelSignUp;

    @FXML
    private TextField usernameTextFieldSignUp;
    
    private void switchSceneSignUpPage(String fxmlFileName) {
        try {
            Scene scene = sceneCache.computeIfAbsent(fxmlFileName, fxml -> {
                try {
                	// Create a loader for the FXML
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
                    
                    // Set the current instance as the controller
                    loader.setController(this);
                    
                    // Load the FXML file
                    Parent root = loader.load();
                    
                    // Return the created scene
                    return new Scene(root);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            });

            Stage stage = (Stage) SignUpPane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void loginButtonPressedSignUp(ActionEvent event) {
    	// Handle signup button click
    	// Switch scene to the login page here
    	switchSceneSignUpPage("LoginPage.fxml");
    }

    @FXML
    void signUpButtonPressedSignupPage(ActionEvent event) {
    	 String username = usernameTextFieldSignUp.getText().strip();
         String password = createPasswordTextFieldSignUp.getText().strip();
         String confirmPassword = confirmPasswordTextFieldSignUp.getText().strip();
         
         if(username != null || password != null || confirmPassword != null) {
         
	         if(confirmPassword.equals(password) == true) {
	         
		         if (UserDB.signUp(username, password)) {
		             showAlert("Sign Up Successful");
		             switchSceneSignUpPage("LoginPage.fxml");
		         } 
		         else {
		             showAlert("Sign Up Failed! Username already exists. Please Log In");
		         }
	         }
	         else {
	        	 showAlert("Sign Up Failed! Passwords donot match. Please try again");
	         }
         }
         else {
        	 showAlert("Textfield(s) is empty!");
         }
     }
     
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    
    
    //-----------------------------Log In Page-------------------------------------------
    
    @FXML
    private Pane LogInPane;

    @FXML
    private Label forgotPasswordLabel;

    @FXML
    private Button logInButtonLoginPage;

    @FXML
    private Polygon logInPolygon;

    @FXML
    private Label loginLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField passwordTextFieldLogin;

    @FXML
    private Button signUpButtonLoginPage;

    @FXML
    private Label usernameLabel;

    @FXML
    private TextField usernameTextFieldLogin;
    
    private void switchSceneLogInPage(String fxmlFileName) {
        try {
            Scene scene = sceneCache.computeIfAbsent(fxmlFileName, fxml -> {
                try {
                	// Create a loader for the FXML
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
                    
                    // Set the current instance as the controller
                    loader.setController(this);
                    
                    // Load the FXML file
                    Parent root = loader.load();
                    
                    // Return the created scene
                    return new Scene(root);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            });

            Stage stage = (Stage) LogInPane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void logInButtonPressedLoginPage(ActionEvent event) {
//    	   String username = usernameTextFieldLogin.getText().strip();
//           String password = passwordTextFieldLogin.getText().strip();
//
//           String userType = UserDB.signIn(username, password);
//           
//           userAdminLogin(userType);
//           
//
//           if (userType != null) {
//               if (userType.equals("user")) {
//                   // Redirect to regular user page
//            	   switchSceneLogInPage("ViewDevices.fxml");  	   
//                   
//               } else if (userType.equals("admin")) {
//                   // Redirect to admin page
            	   switchSceneLogInPage("AdminPage.fxml");
//                 
//               }
//           } else {
//               // Show login failed message
//               showAlert("Login Failed! Please enter valid username/password.");
//           }
    }
    
   
    @FXML
    void signUpButtonPressedLoginPage(ActionEvent event) {
    	// Handle signup button click
    	// Switch scene to the welcome page here
    	switchSceneLogInPage("Signup.fxml");
    }
    
    
    
    //-----------------------------Admin Page-------------------------------------------

    @FXML
    private Button addNewDevicesButtonAdminPage1;

    @FXML
    private Label adminPageLabel;

    @FXML
    private Button automationRulesButtonAdminPage;

    @FXML
    private Button goBackButtonAdminPage;

    @FXML
    private ListView<?> listViewAdminPage;

    @FXML
    private Pane mainPagePane;

    @FXML
    private Button viewDevicesButtonAdminPage;

    @FXML
    void addNewDevicesButtonPressedAdminPage(ActionEvent event) {

    }

    @FXML
    void automationRulesButtonPressedAdminPage(ActionEvent event) {
    	switchSceneAdminPage("AutomationRules.fxml");

    }

    @FXML
    void goBackButtonPressedAdminPage(ActionEvent event) {
    	switchSceneAdminPage("LoginPage.fxml");
    }

    @FXML
    void viewDevicesButtonPressedAdminPage(ActionEvent event) {
    	switchSceneAdminPage("ViewDevices.fxml");
    }

    
    private void switchSceneAdminPage(String fxmlFileName) {
        try {
            Scene scene = sceneCache.computeIfAbsent(fxmlFileName, fxml -> {
                try {
                	// Create a loader for the FXML
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
                    
                    // Set the current instance as the controller
                    loader.setController(this);
                    
                    // Load the FXML file
                    Parent root = loader.load();
                    
                    // Return the created scene
                    return new Scene(root);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            });

            Stage stage = (Stage) mainPagePane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    //-----------------------------View Devices Page-------------------------------------------
    
    @FXML
    private Button doorbellButtonViewDevices;

    @FXML
    private Button goBackButtonViewDevices;

    @FXML
    private Button lightButtonViewDevices;

    @FXML
    private Button lockButtonViewDevices;

    @FXML
    private Button thermostatButttonViewDevices;

    @FXML
    private Button vacuumButtonViewDevices;

    @FXML
    private Label viewDevicesLabel;

    @FXML
    private Pane viewDevicesPane;
    
    private void switchSceneViewDevicesPage(String fxmlFileName) {
        try {
            Scene scene = sceneCache.computeIfAbsent(fxmlFileName, fxml -> {
                try {
                	// Create a loader for the FXML
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
                    
                    // Set the current instance as the controller
                    loader.setController(this);
                    
                    // Load the FXML file
                    Parent root = loader.load();
                    
                    // Return the created scene
                    return new Scene(root);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            });

            Stage stage = (Stage) viewDevicesPane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goBackButtonPressedViewDevices(ActionEvent event) {
    	System.out.println(userAdmin);
    	if(userAdmin != null && userAdmin.equals("admin")) {
    		switchSceneViewDevicesPage("AdminPage.fxml");
    	}
    	else {
    		switchSceneViewDevicesPage("LoginPage.fxml");
    	}
    	
    }
    
    @FXML
    void doorbellButtonPressedViewDevices(ActionEvent event) {
    	switchSceneViewDevicesPage("SmartDoorbell.fxml");
    }

    @FXML
    void lightButtonPressedViewDevices(ActionEvent event) {
    	switchSceneViewDevicesPage("SmartLight.fxml");
    }

    @FXML
    void lockButtonPressedViewDevices(ActionEvent event) {
    	switchSceneViewDevicesPage("SmartLock.fxml");
    }

    @FXML
    void thermostatButttonPressedViewDevices(ActionEvent event) {
    	client.thermostatButtonPressed();
    	switchSceneViewDevicesPage("SmartThermostat.fxml");
    }

    @FXML
    void vacuumButtonPressedViewDevices(ActionEvent event) {
    	switchSceneViewDevicesPage("VacuumRobot.fxml");
    }
    
    
    
    //-----------------------------Smart Light Page-------------------------------------------
    
    private boolean lightIsOn = false;
    
    @FXML
    private Label adjustBrightnessLabel;

    @FXML Slider adjustBrightnessSlider;

    @FXML Label brightnessLabelLightPage;

    @FXML
    private Label changeColorLabel;

    @FXML
    private ColorPicker colorPickerSmartLight;

    @FXML
    private Button getStatusButtonSmartLight;

    @FXML
    private Button goBackButtonSmartLightPage;

    @FXML
    private Label smartLightLabel;

    @FXML
    private Pane smartLightPane;

    @FXML
    private Button turnOffLightButton;

    @FXML
    private Button turnOnLightButton;
    
    BigDecimal brightness = new BigDecimal(100); //Brightness is set to 50% by default
    
    private void switchSceneSmartLightPage(String fxmlFileName) {
        try {
            Scene scene = sceneCache.computeIfAbsent(fxmlFileName, fxml -> {
                try {
                	// Create a loader for the FXML
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
                    
                    // Set the current instance as the controller
                    loader.setController(this);
                    
                    // Load the FXML file
                    Parent root = loader.load();
                    
                    // Return the created scene
                    return new Scene(root);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            });

            Stage stage = (Stage) smartLightPane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 // Methods to update light status and brightness visibility
    public void setLightStatus(boolean isOn) {
        lightIsOn = isOn;
        showBrightness(isOn);
    }

    public void showBrightness(boolean show) {
       Platform.runLater(() -> {
            //adjustBrightnessSlider.setVisible(show);
            brightnessLabelLightPage.setVisible(show);
        });
    }
    
    @FXML
    void goBackButtonPressedSmartLightPage(ActionEvent event) {
    	switchSceneSmartLightPage("ViewDevices.fxml");
    }

    @FXML
    void turnOffLightButtonPressed(ActionEvent event) {
    	if (this.client != null) {
            this.client.turnOffLight();
            brightnessLabelLightPage.setVisible(false);
            setLightStatus(false); // Light is off
        }
    	else {
            System.out.println("Client connection is not initialized.");
            // Consider re-attempting to connect or notify the user
        	}
    }

    @FXML
    void turnOnLightButtonPressed(ActionEvent event) {
    	if (this.client != null) {
            this.client.turnOnLight();
            brightnessLabelLightPage.setVisible(true);
            setLightStatus(true); // Light is on
        }
    	else {
            System.out.println("Client connection is not initialized.");
            // Consider re-attempting to connect or notify the user
        	}
    }
    
    @FXML
    void getStatusButtonPressedSmartLight(ActionEvent event) {
    	if (this.client != null) {
    	client.getStatusSmartLight();
    	} else {
        System.out.println("Client connection is not initialized.");
        // Consider re-attempting to connect or notify the user
    	}
    }
    
    public void setLabelSmartLight(String lbl) {
    	smartLightLabel.setText(lbl);
    }
    
    public void initialize() {
    	if (adjustBrightnessSlider != null) {
	    	adjustBrightnessSlider.setValue(100); // Set default slider value
	        brightnessLabelLightPage.setText("100%"); // Set default label text
	        adjustBrightnessSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
	            if (client != null && lightIsOn) {
	                try {
	                    client.sendToServer("adjustBrightness:" + newVal.intValue());
	                    brightnessLabelLightPage.setText(newVal.intValue() + "%");
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        });
    	}
//    	else {
//    		System.out.println("adjustBrightnessSlider is null. Check your FXML file 'fx:id' tags.");
//    	}
    }
    
    
    //-----------------------------Smart Lock Page-------------------------------------------
    
    @FXML
    private ToggleButton getBreakInAlertToggleButton;

    @FXML
    private Button goBackButtonSmartLockPage;

    @FXML
    private Button lockButton;

    @FXML
    private Label smartLockLabel;

    @FXML
    private Label smartLockLabelHidden;
    
    @FXML
    private Button getStatusButtonSmartLock;

    @FXML
    private Pane smartLockPane;

    @FXML
    private Button unlockButton;
    
    private void switchSceneSmartLockPage(String fxmlFileName) {
        try {
            Scene scene = sceneCache.computeIfAbsent(fxmlFileName, fxml -> {
                try {
                	// Create a loader for the FXML
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
                    
                    // Set the current instance as the controller
                    loader.setController(this);
                    
                    // Load the FXML file
                    Parent root = loader.load();
                    
                    // Return the created scene
                    return new Scene(root);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            });

            Stage stage = (Stage) smartLockPane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void getBreakInAlertToggleButtonPressed(ActionEvent event) {
    	if (this.client != null && getBreakInAlertToggleButton.isSelected() == true) {
    		client.getBreakIAlert(true);
        } else if (getBreakInAlertToggleButton.isSelected() == false) {
        	client.getBreakIAlert(false);
        }
    	else {
            System.out.println("Client connection is not initialized.");
            // Consider re-attempting to connect or notify the user
        }
    }

    @FXML
    void goBackButtonPressedSmartLockPage(ActionEvent event) {
    	switchSceneSmartLockPage("ViewDevices.fxml");
    }

    @FXML
    void lockButtonPressedSmartLock(ActionEvent event) {
    	if (this.client != null) {
    		client.lock();
        } else {
            System.out.println("Client connection is not initialized.");
            // Consider re-attempting to connect or notify the user
        }
    	
    }

    @FXML
    void unlockButtonPressedSmartLock(ActionEvent event) {
    	if (this.client != null) {
    		client.unlock();
        } else {
            System.out.println("Client connection is not initialized.");
            // Consider re-attempting to connect or notify the user
        }
    }
    
    @FXML
    void getStatusButtonPressedSmartLock(ActionEvent event) {
    	if (this.client != null) {
    		client.getStatusSmartLock();
        } else {
            System.out.println("Client connection is not initialized.");
            // Consider re-attempting to connect or notify the user
        }
    }
    
    public void setLabelSmartLock(String lbl) {
    	smartLockLabelHidden.setText(lbl);
    }

    
    
    //-----------------------------Thermostat Page-------------------------------------------
    
    @FXML
    private Button coolButton;

    @FXML
    private Button decreaseTemperature;

    @FXML
    private Label degreeCelciusLabel;

    @FXML
    private Button goBackButtonThermostatPage;

    @FXML
    private Button getStatusButtonThermostat;
    
    @FXML
    private Button heatButton;

    @FXML
    private Button increaseTemperatureButton;

    @FXML
    private Label smartThermostatLabel;

    @FXML
    private Pane smartThermostatPane;

    @FXML
    private Label temperatureLabelThermostat;

    @FXML
    private Button thermostatOffButton;

    @FXML
    private Button thermostatOnButton;
    
    private void switchSceneSmartThermostatPage(String fxmlFileName) {
        try {
            Scene scene = sceneCache.computeIfAbsent(fxmlFileName, fxml -> {
                try {
                	// Create a loader for the FXML
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
                    
                    // Set the current instance as the controller
                    loader.setController(this);
                    
                    // Load the FXML file
                    Parent root = loader.load();
                    
                    // Return the created scene
                    return new Scene(root);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            });

            Stage stage = (Stage) smartThermostatPane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void coolButtonPressed(ActionEvent event) {
    	if (this.client != null) {
    		client.cool();
        } else {
            System.out.println("Client connection is not initialized.");
            // Consider re-attempting to connect or notify the user
        }
    }

    @FXML
    void decreaseTemperaturePressed(ActionEvent event) {
    	if (this.client != null) {
    		client.decreaseTemperature();
        } else {
            System.out.println("Client connection is not initialized.");
            // Consider re-attempting to connect or notify the user
        }
    }

    @FXML
    void goBackButtonPressedThermostatPage(ActionEvent event) {
    	switchSceneSmartThermostatPage("ViewDevices.fxml");
    }

    @FXML
    void heatButtonPressed(ActionEvent event) {
    	if (this.client != null) {
    		client.heat();
        } else {
            System.out.println("Client connection is not initialized.");
            // Consider re-attempting to connect or notify the user
        }
    }

    @FXML
    void increaseTemperatureButtonPressed(ActionEvent event) {
    	if (this.client != null) {
    		client.increaseTemperature();
        } else {
            System.out.println("Client connection is not initialized.");
            // Consider re-attempting to connect or notify the user
        }
    }

    @FXML
    void turnOffThermostatButtonPressed(ActionEvent event) {
    	if (this.client != null) {
    		client.turnOffThermostat();
        } else {
            System.out.println("Client connection is not initialized.");
            // Consider re-attempting to connect or notify the user
        }
    }

    @FXML
    void turnOnThermostatButtonPressed(ActionEvent event) {
    	if (this.client != null) {
    		client.turnOnThermostat();
        } else {
            System.out.println("Client connection is not initialized.");
            // Consider re-attempting to connect or notify the user
        }
    }
    
    @FXML
    void getStatusButtonPressedThermostat(ActionEvent event) {
    	if (this.client != null) {
    		client.getStatusThermostat();
        } else {
            System.out.println("Client connection is not initialized.");
            // Consider re-attempting to connect or notify the user
        }
    }
    
    public void setLabelThermostat(String lbl) {
    	smartThermostatLabel.setText(lbl);
    }
    
    public void setTempeartureLabelThermostat(String lbl) {
    	temperatureLabelThermostat.setText(lbl);
    }

    

    //-----------------------------Vacuum Robot Page-------------------------------------------
    
    @FXML
    private ToggleButton emptyDustSackAlertToggleButton;
    
    @FXML
    private Button getStatusButtonVacuumRobot;

    @FXML
    private Button goBackButtonVacuumRobotPage;

    @FXML
    private Button startCleaningButton;

    @FXML
    private Button stopCleaningButton;

    @FXML
    private Label vacuumRobotLabel;

    @FXML
    private Pane vacuumRobotPane;
    
    private void switchSceneVacuumRobotPage(String fxmlFileName) {
        try {
            Scene scene = sceneCache.computeIfAbsent(fxmlFileName, fxml -> {
                try {
                	// Create a loader for the FXML
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
                    
                    // Set the current instance as the controller
                    loader.setController(this);
                    
                    // Load the FXML file
                    Parent root = loader.load();
                    
                    // Return the created scene
                    return new Scene(root);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            });

            Stage stage = (Stage) vacuumRobotPane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void emptyDustSackAlertToggleButtonPressed(ActionEvent event) {
    	if (this.client != null && emptyDustSackAlertToggleButton.isSelected() == true) {
    		client.emptyDustSackAlert(true);
        } else if (emptyDustSackAlertToggleButton.isSelected() == false) {
        	client.emptyDustSackAlert(false);
        }
    	else {
            System.out.println("Client connection is not initialized.");
            // Consider re-attempting to connect or notify the user
        }
    }

    @FXML
    void goBackButtonPressedVacuumRobotPage(ActionEvent event) {
    	switchSceneVacuumRobotPage("ViewDevices.fxml");
    }

    @FXML
    void startCleaningButtonPressed(ActionEvent event) {
    	if (this.client != null) {
    		client.startCleaning();
        } else {
            System.out.println("Client connection is not initialized.");
            // Consider re-attempting to connect or notify the user
        }
    }

    @FXML
    void stopCleaningButtonPressed(ActionEvent event) {
    	if (this.client != null) {
    		client.stopCleaning();
        } else {
            System.out.println("Client connection is not initialized.");
            // Consider re-attempting to connect or notify the user
        }
    }
    
    @FXML
    void getStatusButtonPressedVacuumRobot(ActionEvent event) {
    	if (this.client != null) {
    		client.getStatusVacuumRobot();
        } else {
            System.out.println("Client connection is not initialized.");
            // Consider re-attempting to connect or notify the user
        }
    }
    
    public void setLabelVacuumRobot(String lbl) {
    	vacuumRobotLabel.setText(lbl);
    }

    
    
    //-----------------------------Smart Doorbell Page-------------------------------------------
    
    @FXML
    private Button activateNightModeButton;

    @FXML
    private Button goBackButtonSmartDoorbellPage;

    @FXML
    private Label smartDoorbellLabel;

    @FXML
    private Label smartDoorbellLabelHidden;

    @FXML
    private Pane smartDoorbellPane;
    
    @FXML
    private Button getStatusButtonSmartDoorbell;

    @FXML
    private Button turnOffDoorbellButton;

    @FXML
    private Button turnOnCameraButton;

    @FXML
    private Button turnOnDoorbellButton;
    
    
    public void switchSceneSmartDoorbellPage(String fxmlFileName) {
        try {
            Scene scene = sceneCache.computeIfAbsent(fxmlFileName, fxml -> {
                try {
                	// Create a loader for the FXML
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
                    
                    // Set the current instance as the controller
                    loader.setController(this);
                    
                    // Load the FXML file
                    Parent root = loader.load();
                    
                    // Return the created scene
                    return new Scene(root);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            });

            Stage stage = (Stage) smartDoorbellPane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@FXML
	void activateNightModeButtonPressed(ActionEvent event) {
		if (this.client != null) {
			client.activateNightMode();
        } else {
            System.out.println("Client connection is not initialized.");
            // Consider re-attempting to connect or notify the user
        }
	}
	
	@FXML
	void goBackButtonPressedSmartDoorbellPage(ActionEvent event) {
		switchSceneSmartDoorbellPage("ViewDevices.fxml");
	}
	
	@FXML
	void turnOffDoorbellButtonPressed(ActionEvent event) {
		if (this.client != null) {
			client.turnOffDoorbell();
        } else {
            System.out.println("Client connection is not initialized.");
            // Consider re-attempting to connect or notify the user
        }
	}
	
	@FXML
	void turnOnCameraButtonPressed(ActionEvent event) {
		if (this.client != null) {
			client.turnOnCameraDoorbell();
        } else {
            System.out.println("Client connection is not initialized.");
            // Consider re-attempting to connect or notify the user
        }
	}
	
	@FXML
	void turnOnDoorbellButtonPressed(ActionEvent event) {
		if (this.client != null) {
			client.turnOnDoorbell();
        } else {
            System.out.println("Client connection is not initialized.");
            // Consider re-attempting to connect or notify the user
        }
	}
	
	@FXML
    void getStatusButtonPressedSmartDoorbell(ActionEvent event) {
		if (this.client != null) {
			client.getStatusSmartDoorbell();
        } else {
            System.out.println("Client connection is not initialized.");
            // Consider re-attempting to connect or notify the user
        }
    }
	
	public void setLabelSmartDoorbell(String lbl) {
		smartDoorbellLabelHidden.setText(lbl);
    }
    
	
	//-----------------------------DoorbellCamera Page-------------------------------------------
	@FXML
    private ImageView cameraView;

    @FXML
    private Pane doorbellCameraPane;
    
    public void switchSceneDoorbellCameraPage(String fxmlFileName) {
        try {
            Scene scene = sceneCache.computeIfAbsent(fxmlFileName, fxml -> {
                try {
                	// Create a loader for the FXML
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
                    
                    // Set the current instance as the controller
                    loader.setController(this);
                    
                    // Load the FXML file
                    Parent root = loader.load();
                    
                    // Return the created scene
                    return new Scene(root);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            });

            Stage stage = (Stage) doorbellCameraPane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    //-----------------------------Automation Rules Page-------------------------------------------
    
	@FXML
    private Pane automationRulePane;

    @FXML
    private Button doorbellButtonAutomationRule;

    @FXML
    private Button goBackButtonAutomationRule;

    @FXML
    private Button lightButtonAutomationRules;

    @FXML
    private Button lockButtonAutomationRules;

    @FXML
    private Label setAutomationRulesLabel;

    @FXML
    private Button thermostatButttonAutomationRules;

    @FXML
    private Button vacuumButtonAutomationRules;
    
    public void switchSceneSetAutomationRulePage(String fxmlFileName) {
        try {
            Scene scene = sceneCache.computeIfAbsent(fxmlFileName, fxml -> {
                try {
                	// Create a loader for the FXML
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
                    
                    // Set the current instance as the controller
                    loader.setController(this);
                    
                    // Load the FXML file
                    Parent root = loader.load();
                    
                    // Return the created scene
                    return new Scene(root);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            });

            Stage stage = (Stage) automationRulePane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void doorbellButtonPressedAutomationRule(ActionEvent event) {
    	switchSceneSetAutomationRulePage("SmartDoorbellAutomation.fxml");
    }

    @FXML
    void goBackButtonPressedAutomationRule(ActionEvent event) {
    	switchSceneSetAutomationRulePage("AdminPage.fxml");
    }

    @FXML
    void lightButtonPressedAutomationRule(ActionEvent event) {
    	switchSceneSetAutomationRulePage("SmartLightAutomation.fxml");
    }

    @FXML
    void lockButtonPressedAutomationRules(ActionEvent event) {
    	switchSceneSetAutomationRulePage("SmartLockAutomation.fxml");
    }

    @FXML
    void thermostatButttonPressedAutomationRules(ActionEvent event) {
    	switchSceneSetAutomationRulePage("SmartThermostatAutomation.fxml");
    }

    @FXML
    void vacuumButtonPressedAutomationRules(ActionEvent event) {
    	switchSceneSetAutomationRulePage("VacuumRobotAutomation.fxml");
    }
    
    
  //-----------------------------Smart Light Automation Rules Page-------------------------------------------
    @FXML
    private ChoiceBox<String> changeBrightnessChoiceBoxSmartLightAutomation;

    @FXML
    private TextField changeBrightnessTextFieldSmartLight;

    @FXML
    private ColorPicker changeColorPickerSmartLight;

    @FXML
    private ChoiceBox<String> colorChoiceBoxSmartLightAutomation;

    @FXML
    private Button goBackButtonSmartLightAutomationPage;

    @FXML
    private Label smartLightAutomationLabel;

    @FXML
    private Pane smartLightPaneAutomation;

    @FXML
    private ComboBox<String> turnOffLightAmPmComboBox;

    @FXML
    private Button turnOffLightAtButton;

    @FXML
    private TextField turnOffLightMM;

    @FXML
    private ComboBox<String> turnOnLightAmPmComboBox;

    @FXML
    private Button turnOnLightAtButton;

    @FXML
    private TextField turnOnLightHH;

    @FXML
    private TextField turnOnLightMM;

    
   private void switchSceneSmartLightAutomationPage(String fxmlFileName) {
        try {
            Scene scene = sceneCache.computeIfAbsent(fxmlFileName, fxml -> {
                try {
                	// Create a loader for the FXML
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
                    
                    // Set the current instance as the controller
                    loader.setController(this);
                    
                    // Load the FXML file
                    Parent root = loader.load();
                    
                    
                    // Manually call your custom initialization method here
                    initializeSmartLight(); 
                    
                    // Return the created scene
                    return new Scene(root);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            });

            Stage stage = (Stage) smartLightPaneAutomation.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
   

   private void initializeSmartLight() {
       populateChangeBrightnessChoiceBox();
       
   }

   private void populateChangeBrightnessChoiceBox() {
       // Populate the choice box with the time options
	   ObservableList<String> options = FXCollections.observableArrayList(
		   "1 minute",
           "2 minutes",
           "5 minutes",
           "10 minutes",
           "15 minutes",
           "30 minutes",
           "1 hour",
           "2 hours",
           "5 hours",
           "8 hours",
           "12 hours"
       );
	   changeBrightnessChoiceBoxSmartLightAutomation.setItems(options);
       changeBrightnessChoiceBoxSmartLightAutomation.setValue("1 minute"); // Set default value
   }
   
    
    @FXML
    void goBackButtonPressedSmartLightAutomation(ActionEvent event) {
    	switchSceneSmartLightAutomationPage("AutomationRules.fxml");
    }
    

    @FXML
    void turnOffLightAtButtonPressed(ActionEvent event) {

    }

    @FXML
    void turnOnLightAtButtonPressed(ActionEvent event) {

    }
    
    
    
    
    
  //-----------------------------Smart Thermostat Automation Rules Page-------------------------------------------
    @FXML
    private ChoiceBox<?> changeModeChoiceBoxSmartThermostatAutomation;

    @FXML
    private ToggleButton coolToggleButtonSmartThermostatAutomation;

    @FXML
    private Button goBackButtonSmartThermostatAutomation;

    @FXML
    private ToggleButton heatToggleButtonSmartThermostatAutomation1;

    @FXML
    private Button setTurnOffThermostat;

    @FXML
    private Button setTurnOnThermostatButton;

    @FXML
    private Label smartThermostatAutomationLabelHidden;

    @FXML
    private Label smartThermostatLabelAutomationPage;

    @FXML
    private Pane smartThermostatPaneAutomation;

    @FXML
    private TextField turnOfThermstatMM;

    @FXML
    private ComboBox<?> turnOffThermostatAmPmComboBox;

    @FXML
    private TextField turnOffThermostatHH;

    @FXML
    private ComboBox<?> turnOnThermostatAmPmComboBox;

    @FXML
    private TextField turnOnThermostatHH;

    @FXML
    private TextField turnOnThermostatMM;

  
    @FXML
    void setTurnOffThermostatAutomationButtonPressed(ActionEvent event) {

    }

    @FXML
    void setTurnOnThermostatAutomationButtonPressed(ActionEvent event) {

    }
    private void switchSceneSmartThermostatAutomationPage(String fxmlFileName) {
        try {
            Scene scene = sceneCache.computeIfAbsent(fxmlFileName, fxml -> {
                try {
                	// Create a loader for the FXML
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
                    
                    // Set the current instance as the controller
                    loader.setController(this);
                    
                    // Load the FXML file
                    Parent root = loader.load();
                    
                    // Return the created scene
                    return new Scene(root);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            });

            Stage stage = (Stage) smartThermostatPaneAutomation.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goBackButtonPressedSmartThermostatAutomation(ActionEvent event) {
    	switchSceneSmartThermostatAutomationPage("AutomationRules.fxml");
    }

   
    
  //-----------------------------Smart Lock Automation Rules Page-------------------------------------------
    @FXML
    private TextField LockDoorMM;

    @FXML
    private ChoiceBox<?> breakInChoiceBoxSmartLock;

    @FXML
    private TextField getBreakInHH;

    @FXML
    private TextField getBreakInMM;

    @FXML
    private ComboBox<?> getBreakInSmartLockAmPmComboBox;

    @FXML
    private Button goBackButtonSmartLockAutomationPage;

    @FXML
    private Button lockDoorButtonAutomationPage;

    @FXML
    private ChoiceBox<?> lockDoorChoiceBoxSmartLock;

    @FXML
    private TextField lockDoorHH;

    @FXML
    private ComboBox<?> lockSmartLockAmPmComboBox;

    @FXML
    private Button setBreakInAlertAutomationButton;

    @FXML
    private Label smartLockAutomationLabelHidden;

    @FXML
    private Pane smartLockAutomationPane;

    @FXML
    private Label smartLockLabelAutomation;

    @FXML
    private Button unlockDoorButtonAutomation;

    @FXML
    private ChoiceBox<?> unlockDoorChoiceBoxSmartLock;

    @FXML
    private TextField unlockDoorHH;

    @FXML
    private TextField unlockDoorMM;

    @FXML
    private ComboBox<?> unlockSmartLockAmPmComboBox;

  
    @FXML
    void lockDoorButtonPressedAutomationPage(ActionEvent event) {

    }

    @FXML
    void setBreakInALertAutomationButtonPressed(ActionEvent event) {

    }

    @FXML
    void unlockDoorButtonPressedAutomationPage(ActionEvent event) {

    }
    private void switchSceneSmartLockAutomationPage(String fxmlFileName) {
        try {
            Scene scene = sceneCache.computeIfAbsent(fxmlFileName, fxml -> {
                try {
                	// Create a loader for the FXML
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
                    
                    // Set the current instance as the controller
                    loader.setController(this);
                    
                    // Load the FXML file
                    Parent root = loader.load();
                    
                    // Return the created scene
                    return new Scene(root);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            });

            Stage stage = (Stage) smartLockAutomationPane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goBackButtonPressedSmartLockAutomationPage(ActionEvent event) {
    	 switchSceneSmartLockAutomationPage("AutomationRules.fxml");
    }
    
  //-----------------------------Vacuum Robot Automation Rules Page-------------------------------------------
    @FXML
    private Button goBackButtonVacuumRobotAutomationPage;

    @FXML
    private TextField startCleainingHH;

    @FXML
    private ComboBox<?> startCleaningAmPmComboBox;

    @FXML
    private Button startCleaningButtonVacuumAutomation;

    @FXML
    private ComboBox<?> startCleaningComboBoxVacuumRobotAutomation;

    @FXML
    private TextField startCleaningMM;

    @FXML
    private ComboBox<?> stopCleaningAmPmComboBox;

    @FXML
    private Button stopCleaningButtonVacuumAutomation;

    @FXML
    private ComboBox<?> stopCleaningComboBoxVacuumRobotAutomation;

    @FXML
    private TextField stopCleaningHH;

    @FXML
    private TextField stopCleaningMM;

    @FXML
    private Label vacuumRobotAutomationLabel;

    @FXML
    private Label vacuumRobotAutomationLabelHidden;

    @FXML
    private Pane vacuumRobotPaneAutomation;

    @FXML
    void startCleaningButtonPressedVacuumAutomation(ActionEvent event) {

    }

    @FXML
    void stopCleaningButtonPressedVacuumAutomation(ActionEvent event) {

    }
    
    private void switchSceneVacuumRobotAutomationPage(String fxmlFileName) {
        try {
            Scene scene = sceneCache.computeIfAbsent(fxmlFileName, fxml -> {
                try {
                	// Create a loader for the FXML
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
                    
                    // Set the current instance as the controller
                    loader.setController(this);
                    
                    // Load the FXML file
                    Parent root = loader.load();
                    
                    // Return the created scene
                    return new Scene(root);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            });

            Stage stage = (Stage) vacuumRobotPaneAutomation.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goBackButtonPressedVacuumRobotAutomation(ActionEvent event) {
    	switchSceneVacuumRobotAutomationPage("AutomationRules.fxml");
    }

    
    
    
  //-----------------------------Smart Doorbell Automation Rules Page-------------------------------------------
    @FXML
    private Button activateNightModeAtButton;

    @FXML
    private ComboBox<?> activateNightModeComboBoxSmartDoorbellAutomation;

    @FXML
    private ComboBox<?> activateNightModeDoorbellAmPmComboBox;

    @FXML
    private TextField activateNightModeHH;

    @FXML
    private TextField activateNightModeMM;

    @FXML
    private Button goBackButtonSmartDoorbellAutomationPage;

    @FXML
    private Label smartDoorbellAutomationLabelHidden;

    @FXML
    private Label smartDoorbellLabelAutomationPage;

    @FXML
    private Pane smartDoorbellPaneAutomation;

    @FXML
    private Button turnOnCameraAtButton;

    @FXML
    private ComboBox<?> turnOnCameraDoorbellAmPmComboBox;

    @FXML
    private TextField turnOnCameraHH;

    @FXML
    private TextField turnOnCameraMM;

    @FXML
    void activateNightModeAutomationButtonPressed(ActionEvent event) {

    }

    @FXML
    void turnOnCameraAtButtonPressed(ActionEvent event) {

    }
    
   private void switchSceneSmartDoorbellAutomationPage(String fxmlFileName) {
        try {
            Scene scene = sceneCache.computeIfAbsent(fxmlFileName, fxml -> {
                try {
                	// Create a loader for the FXML
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
                    
                    // Set the current instance as the controller
                    loader.setController(this);
                    
                    // Load the FXML file
                    Parent root = loader.load();
                    
                    // Return the created scene
                    return new Scene(root);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            });

            Stage stage = (Stage) smartDoorbellPaneAutomation.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   
    @FXML
    void goBackButtonPressedSmartLDoorbellAutomation(ActionEvent event) {
    	switchSceneSmartDoorbellAutomationPage("AutomationRules.fxml");
    }
    
}
//end of SHClientController.java