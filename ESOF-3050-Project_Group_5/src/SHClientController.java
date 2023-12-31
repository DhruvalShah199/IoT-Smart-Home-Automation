/**
* The SHClientController contains all the methods necassary to
* set up all the FXML files for the client GUI and also invoke
* methods in SHClient to send it to the server side
* 
* @author Dhruval Harshilkumar Shah
* @author Ebube Orlukwu
* @author Amir Dawood
* @version December 2023
*/


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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.control.TextField;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.scene.control.Slider;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.control.ChoiceBox;

import javafx.scene.control.ComboBox;
import java.util.prefs.Preferences;

import javafx.scene.control.ComboBox;

public class SHClientController {
	private SHClient client;	//Instantiating object for SHClient class
	
	private Map<String, Scene> sceneCache = new HashMap<>();
	
	public synchronized void setClientConnection(SHClient client) {
	    if (client != null) {
	        this.client = client;
	        System.out.println("Client connection set: " + client);
	    } else {
	        System.out.println("Error: Client connection is null.");
	    }
	}
	
	public String userAdminLogin;
	 public void setUserInfo() {
		 
	 }
	public String getUserInfo() {
		return this.userAdminLogin;
	}	
	
	public String userAdmin;
	
	public void userAdminLogin(String userOrAdmin) {
		userAdmin = userOrAdmin;
	}
	
    public void initialize() {
    	try {
	    	
	    	setUpBrightness();
	    	
	    	setUpMediaSize();
	    	
	    	setupChoiceBoxesSmartLight();
	    	setupChoiceBoxesSmartDoorbell();
	    	setupChoiceBoxesSmartThermostat();
	    	setupChoiceBoxesSmartLock();
	    	setupComboBoxesVacuumRobot();
	    }
    	catch (Exception e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
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
        } 
        catch (Exception e) {
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
    	   String username = usernameTextFieldLogin.getText().strip();
           String password = passwordTextFieldLogin.getText().strip();

          String userType = UserDB.signIn(username, password);
          
          userAdminLogin(userType);
           

          if (userType != null) {
              if (userType.equals("user")) {
                   // Redirect to regular user page
            	  switchSceneLogInPage("ViewDevices.fxml");  	   
                  
               } else if (userType.equals("admin")) {
                   // Redirect to admin page
            	   switchSceneLogInPage("AdminPage.fxml");
                
               }
          } else {
               // Show login failed message
              showAlert("Login Failed! Please enter valid username/password.");
           }
    }
    
    @FXML
    void signUpButtonPressedLoginPage(ActionEvent event) {
    	// Handle signup button click
    	// Switch scene to the welcome page here
    	switchSceneLogInPage("Signup.fxml");
    }
    
    
    
    //-----------------------------Admin Page-------------------------------------------

    @FXML
    private Label adminPageLabel;

    @FXML
    private Button automationRulesButtonAdminPage;

    @FXML
    private Button goBackButtonAdminPage;

    @FXML
    private ImageView imageViewAdminPage;

    @FXML
    private Pane mainPagePane;

    @FXML
    private Button viewDevicesButtonAdminPage;

    
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
    
    private boolean pageCheck = false;
    
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
    	vacuumRobotAutomationGridPane.setVisible(false); 
    	pageCheck = true;
    }
    
    
    
    //-----------------------------Smart Light Page-------------------------------------------
    
    private boolean lightIsOn = false;
    
    @FXML
    private Label adjustBrightnessLabel;

    @FXML 
    public Slider adjustBrightnessSlider;

    @FXML 
    public Label brightnessLabelLightPage;

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
    private Label smartLightTitleLabel;

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
    
    // Method to initialize the brightness slider and set it to 100 by default
    private void setUpBrightness(){
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
    void colorPickerChangeValue(ActionEvent event) {
    	String color = String.valueOf(colorPickerSmartLight.getValue());
    	if (this.client != null) {
            this.client.changeColor(color);
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
    	Platform.runLater(() -> {
    		if(this.smartLightLabel != null) {
    			smartLightLabel.setText(lbl);
    		}
    	});
    }
    
    public void changeTitleColor(String col) {
    	Platform.runLater(() -> {
    		Color colorValue = Color.web(col);
	    	if(this.smartLightTitleLabel != null) {
	    		smartLightTitleLabel.setTextFill(colorValue);
	    	}
    	});
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
        } 
        catch (Exception e) {
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
    		showTemperature(true);
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
    		showTemperature(true);
        } else {
            System.out.println("Client connection is not initialized.");
            // Consider re-attempting to connect or notify the user
        }
    }

    @FXML
    void turnOffThermostatButtonPressed(ActionEvent event) {
    	if (this.client != null) {
    		client.turnOffThermostat();
    		showTemperature(false);
        } else {
            System.out.println("Client connection is not initialized.");
            // Consider re-attempting to connect or notify the user
        }
    }

    @FXML
    void turnOnThermostatButtonPressed(ActionEvent event) {
    	if (this.client != null) {
    		client.turnOnThermostat();
    		showTemperature(true);
        } else {
            System.out.println("Client connection is not initialized.");
            // Consider re-attempting to connect or notify the user
        }
    }
    
    public void showTemperature(boolean show) {
        Platform.runLater(() -> temperatureLabelThermostat.setVisible(show));
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
    	Platform.runLater(() -> smartThermostatLabel.setText(lbl));
    }
    
    public void setTempeartureLabelThermostat(String lbl) {
    	Platform.runLater(() -> temperatureLabelThermostat.setText(lbl));
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
    
    void setBreakInAlertToggleButtonPressed(boolean selected) {
    	getBreakInAlertToggleButton.setSelected(selected);
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
    	Platform.runLater(() -> smartLockLabelHidden.setText(lbl));
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
        } 
        catch (Exception e) {
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
    	if(pageCheck == false){
	    	if(userAdmin != null && userAdmin.equals("admin")) {
	    		switchSceneVacuumRobotPage("AutomationRules.fxml");
	    	}
	    } 
    	else{
    		switchSceneVacuumRobotPage("ViewDevices.fxml");
    	}
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
    	Platform.runLater(() -> vacuumRobotLabel.setText(lbl));
    }

    
    
    //-----------------------------Smart Doorbell Page-------------------------------------------
    
    @FXML
    private ToggleButton activateNightModeToggleButton;

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
    
    private boolean isNightModeOn;
    
    public boolean isNightModeOn() {
		return isNightModeOn;
	}

	public void setNightModeOn(boolean isNightModeOn) {
		this.isNightModeOn = isNightModeOn;
	}
    
	public void setActivateNightModeToggleButton(boolean selected) {
		activateNightModeToggleButton.setSelected(selected);
	}
    
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
	void activateNightModeToggleButtonPressed(ActionEvent event) {
		if (this.client != null && activateNightModeToggleButton.isSelected() == true) {
    		client.activateNightMode(true);
        } else if (activateNightModeToggleButton.isSelected() == false) {
        	client.activateNightMode(false);
        }
    	else {
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
		Platform.runLater(() -> smartDoorbellLabelHidden.setText(lbl));
    }
    
	
	
	//-----------------------------Doorbell Camera Normal Video Page-------------------------------------------
	
	@FXML
    private MediaView doorbellCameraMediaView;

    @FXML
    private Pane doorbellCameraNormalPane;
    
    public void switchSceneDoorbellCameraNormalPage(String fxmlFileName) {
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

            Stage stage = (Stage) doorbellCameraNormalPane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void playDoorbellCameraVideo(boolean isNightMode) {
    	//To switch to doorbell camera video page
    	switchSceneSmartDoorbellPage("DoorbellCameraVideoPage.fxml");
    	
        // Path to video files
        String pathToNormalVideo = "Doorbell_Camera_Normal.mp4";
        String pathToNightVideo = "Doorbell_Camera_Night_View.mp4";

        // Determine which video path to use
        String videoPath = getClass().getResource(isNightMode ? pathToNightVideo : pathToNormalVideo).toExternalForm();

        // Create and set the media player
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        doorbellCameraMediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setAutoPlay(true); // Play automatically
    }
    
    private void setUpMediaSize() {
    	if(doorbellCameraMediaView != null) {
	        // Bind the MediaView size to the Pane size
	        doorbellCameraMediaView.fitWidthProperty().bind(doorbellCameraNormalPane.widthProperty());
	        doorbellCameraMediaView.fitHeightProperty().bind(doorbellCameraNormalPane.heightProperty());
	        doorbellCameraMediaView.setPreserveRatio(true);
        }
    }
    
    

    //-----------------------------Automation Rules Page---------------------------------------------
    
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
    	pageCheck = false;
    	switchSceneSetAutomationRulePage("VacuumRobot.fxml");
        vacuumRobotAutomationGridPane.setVisible(true);
    }
    
    private String convertTo24HourFormat(String hour, String minute, String amPm) {
	    // Add logic to convert 12-hour time to 24-hour time format
	    // Return the time in "HH:mm" format
	    int hourInt = Integer.parseInt(hour);
	    if ("PM".equals(amPm) && hourInt < 12) {
	        hourInt += 12;
	    }
	    if ("AM".equals(amPm) && hourInt == 12) {
	        hourInt = 0;
	    }
	    String convertedHour = String.format("%02d", hourInt);
	    return convertedHour + ":" + minute;
    }
    
    
    
    //-----------------------------Smart Light Automation Rules Page-------------------------------------------
    
    @FXML
    private ComboBox<String> changeColorLightAmPmComboBox;

    @FXML
    private Button changeColorLightAtButton;

    @FXML
    private TextField changeColorLightHH;

    @FXML
    private TextField changeColorLightMM;

    @FXML
    private ColorPicker changeColorPickerSmartLight;

    @FXML
    private Button goBackButtonSmartLightAutomationPage;

    @FXML
    private Label smartLightAutomationLabel;
    
    @FXML
    private Label smartLightAutomationLabelHidden;

    @FXML
    private Pane smartLightPaneAutomation;

    @FXML
    private ComboBox<String> turnOffLightAmPmComboBox;

    @FXML
    private Button turnOffLightAtButton;

    @FXML
    private TextField turnOffLightHH;

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

    @FXML
    void changeColorLightAtButtonPressed(ActionEvent event) {
    	String hour = changeColorLightHH.getText();
        String minute = changeColorLightMM.getText();
        String amPm = changeColorLightAmPmComboBox.getValue();
        Color colorValue = changeColorPickerSmartLight.getValue();
        String changeColor = String.format("#%02X%02X%02X",
            (int) (colorValue.getRed() * 255),
            (int) (colorValue.getGreen() * 255),
            (int) (colorValue.getBlue() * 255));

        // Construct a message with the desired schedule time in 24-hour format
        String time = convertTo24HourFormat(hour, minute, amPm);
        
        client.changeColorLightAt(time, changeColor);
        
        setLightLabelAutomation("Light is scheduled to change color to " + changeColor + " at: " + time);
    }

    @FXML
    void turnOffLightAtButtonPressed(ActionEvent event) {
    	String hour = turnOffLightHH.getText();
        String minute = turnOffLightMM.getText();
        String amPm = turnOffLightAmPmComboBox.getValue();

        // Construct a message with the desired schedule time in 24-hour format
        String time = convertTo24HourFormat(hour, minute, amPm);
        
        client.turnOffLightAt(time);
        
        setLightLabelAutomation("Light is scheduled to turn off at: " + time);
    }

    @FXML
    void turnOnLightAtButtonPressed(ActionEvent event) {
    	String hour = turnOnLightHH.getText();
        String minute = turnOnLightMM.getText();
        String amPm = turnOnLightAmPmComboBox.getValue();

        // Construct a message with the desired schedule time in 24-hour format
        String time = convertTo24HourFormat(hour, minute, amPm);
        
        client.turnOnLightAt(time);
        
        setLightLabelAutomation("Light is scheduled to turn on at: " + time);
    }
       
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
    
   private void setupChoiceBoxesSmartLight() {
	   if (changeColorLightAmPmComboBox != null && turnOnLightAmPmComboBox != null && turnOffLightAmPmComboBox != null) {
		    // Populating the AM/PM ComboBoxes
		    ObservableList<String> amPmOptions = FXCollections.observableArrayList("AM", "PM");
		    turnOnLightAmPmComboBox.setItems(amPmOptions);
		    turnOffLightAmPmComboBox.setItems(amPmOptions);
		    changeColorLightAmPmComboBox.setItems(amPmOptions);
	   }
   }
	    
    @FXML
    void goBackButtonPressedSmartLightAutomation(ActionEvent event) {
    	switchSceneSmartLightAutomationPage("AutomationRules.fxml");
    }
    
    public void setLightLabelAutomation (String lbl) {
    	Platform.runLater(() -> smartLightAutomationLabelHidden.setText(lbl));
    }
    
    public void changeTitleColorAutomation(String col) {
    	Platform.runLater(() -> {
    		Color color = Color.web(col);
    		if(this.smartLightAutomationLabel != null) {
    			smartLightAutomationLabel.setTextFill(color);
    		}
    	});
    }
    
    
    
    //-----------------------------Smart Thermostat Automation Rules Page-------------------------------------------
    
    @FXML
    private ComboBox<String> changeModeThermostatAmPmComboBox;

    @FXML
    private Button changeModeThermostatButton;

    @FXML
    private TextField changeModeThermostatHH;

    @FXML
    private TextField changeModeThermostatMM;

    @FXML
    private ToggleButton coolToggleButtonSmartThermostatAutomation;

    @FXML
    private Button goBackButtonSmartThermostatAutomation;

    @FXML
    private ToggleButton heatToggleButtonSmartThermostatAutomation;

    @FXML
    private Button setTurnOffThermostat;

    @FXML
    private Button setTurnOnThermostatButton;

    @FXML
    private Label smartThermostatAutomationLabelHidden;

    @FXML
    private Label smartThermostatLabelAutomationPage;
    
    @FXML
    private Label modeLabel;

    @FXML
    private Pane smartThermostatPaneAutomation;

    @FXML
    private ComboBox<String> turnOffThermostatAmPmComboBox;

    @FXML
    private TextField turnOffThermostatHH;

    @FXML
    private TextField turnOffThermostatMM;

    @FXML
    private ComboBox<String> turnOnThermostatAmPmComboBox;

    @FXML
    private TextField turnOnThermostatHH;

    @FXML
    private TextField turnOnThermostatMM;
    
    private boolean modeFlag = false;
    
    @FXML
    void coolToggleButtonSmartThermostatPressed(ActionEvent event) {

    	modeLabel.setText("Cool Mode Selected");
    	modeLabel.setTextFill(Color.rgb(135,206,235));
    	modeFlag = true;
    }
    
    @FXML
    void heatToggleButtonSmartThermostatPressed(ActionEvent event) {
    	modeLabel.setText("Heat Mode Selected");
    	modeLabel.setTextFill(Color.RED);
    	modeFlag = false;
    }

    @FXML
    void changeModeThermostatAutomationButtonPressed(ActionEvent event) {
    	String hour = changeModeThermostatHH.getText();
        String minute = changeModeThermostatMM.getText();
        String amPm = changeModeThermostatAmPmComboBox.getValue();

        // Construct a message with the desired schedule time in 24-hour format
        String time = convertTo24HourFormat(hour, minute, amPm);
        
        if(modeFlag == true) {
        
        	client.modeCoolThermostatAt(time);
        	setThermostatLabelAutomation("Thermostat is scheduled to change mode to COOL at: " + time);
        }
        else if(modeFlag == false) {
        	client.modeHeatThermostatAt(time);
        	setThermostatLabelAutomation("Thermostat is scheduled to change mode to HEAT at: " + time);
        }
    }
  
    @FXML
    void setTurnOffThermostatAutomationButtonPressed(ActionEvent event) {
    	String hour = turnOffThermostatHH.getText();
        String minute = turnOffThermostatMM.getText();
        String amPm = turnOffThermostatAmPmComboBox.getValue();

        // Construct a message with the desired schedule time in 24-hour format
        String time = convertTo24HourFormat(hour, minute, amPm);
        
        client.turnOffThermostatAt(time);
        
        setThermostatLabelAutomation("Thermostat is scheduled to turn off at: " + time);
    }

    @FXML
    void setTurnOnThermostatAutomationButtonPressed(ActionEvent event) {
    	String hour = turnOnThermostatHH.getText();
        String minute = turnOnThermostatMM.getText();
        String amPm = turnOnThermostatAmPmComboBox.getValue();

        // Construct a message with the desired schedule time in 24-hour format
        String time = convertTo24HourFormat(hour, minute, amPm);
        
        client.turnOnThermostatAt(time);
        
        setThermostatLabelAutomation("Thermostat is scheduled to turn on at: " + time);
    }
    
    public void setThermostatLabelAutomation (String lbl) {
    	Platform.runLater(() -> smartThermostatAutomationLabelHidden.setText(lbl));
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
    
    private void setupChoiceBoxesSmartThermostat() {
  	   if (this.turnOffThermostatAmPmComboBox != null && this.turnOnThermostatAmPmComboBox != null && this.changeModeThermostatAmPmComboBox != null) {
  		   
  		    // Populating the AM/PM ComboBoxes
  		    ObservableList<String> amPmOptions = FXCollections.observableArrayList("AM", "PM");
  		    turnOffThermostatAmPmComboBox.setItems(amPmOptions);
  		    turnOnThermostatAmPmComboBox.setItems(amPmOptions);
  		    changeModeThermostatAmPmComboBox.setItems(amPmOptions);
  	   }
     }

    @FXML
    void goBackButtonPressedSmartThermostatAutomation(ActionEvent event) {
    	switchSceneSmartThermostatAutomationPage("AutomationRules.fxml");
    }
   
    
    
    //-----------------------------Smart Lock Automation Rules Page-------------------------------------------
    
    @FXML
    private Button breakInAlertButtonAutomation;

    @FXML
    private TextField breakInAlertHH;

    @FXML
    private TextField breakInAlertMM;

    @FXML
    private ComboBox<String> breakInAlertSmartLockAmPmComboBox;

    @FXML
    private Button goBackButtonSmartLockAutomationPage;

    @FXML
    private Button lockDoorButtonAutomationPage;

    @FXML
    private TextField lockDoorHH;

    @FXML
    private TextField lockDoorMM;

    @FXML
    private ComboBox<String> lockSmartLockAmPmComboBox;

    @FXML
    private Label smartLockAutomationLabelHidden;

    @FXML
    private Pane smartLockAutomationPane;

    @FXML
    private Label smartLockLabelAutomation;

    @FXML
    private Button unlockDoorButtonAutomation;

    @FXML
    private TextField unlockDoorHH;

    @FXML
    private TextField unlockDoorMM;

    @FXML
    private ComboBox<String> unlockSmartLockAmPmComboBox;

  
    @FXML
    void lockDoorButtonPressedAutomationPage(ActionEvent event) {
    	String hour = lockDoorHH.getText();
        String minute = lockDoorMM.getText();
        String amPm = lockSmartLockAmPmComboBox.getValue();

        // Construct a message with the desired schedule time in 24-hour format
        String time = convertTo24HourFormat(hour, minute, amPm);
        
        client.lockSmartLockAt(time);
        
        setSmartLockLabelAutomation("Smart Lock is scheduled to lock at: " + time);
    }

    @FXML
    void unlockDoorButtonPressedAutomationPage(ActionEvent event) {
    	String hour = unlockDoorHH.getText();
        String minute = unlockDoorMM.getText();
        String amPm = unlockSmartLockAmPmComboBox.getValue();

        // Construct a message with the desired schedule time in 24-hour format
        String time = convertTo24HourFormat(hour, minute, amPm);
        
        client.unlockSmartLockAt(time);
        
        setSmartLockLabelAutomation("Smart Lock is scheduled to unlock at: " + time);
    }
    
    @FXML
    void breakInAlertButtonPressedAutomationPage(ActionEvent event) {
    	String hour = breakInAlertHH.getText();
        String minute = breakInAlertMM.getText();
        String amPm = breakInAlertSmartLockAmPmComboBox.getValue();

        // Construct a message with the desired schedule time in 24-hour format
        String time = convertTo24HourFormat(hour, minute, amPm);
        
        client.breakInAlertSmartLockAt(time);
        
        setSmartLockLabelAutomation("Smart Lock is scheduled to turn on break-in alert at: " + time);
    }
    
    public void setSmartLockLabelAutomation (String lbl) {
    	Platform.runLater(() -> {
    		if(this.smartLockAutomationLabelHidden != null) {
    			smartLockAutomationLabelHidden.setText(lbl);
    		}
    	});
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
    
    private void setupChoiceBoxesSmartLock() {
   	   if (lockSmartLockAmPmComboBox != null && unlockSmartLockAmPmComboBox != null && breakInAlertSmartLockAmPmComboBox != null) {
   		    // Populating the AM/PM ComboBoxes
   		    ObservableList<String> amPmOptions = FXCollections.observableArrayList("AM", "PM");
   		    lockSmartLockAmPmComboBox.setItems(amPmOptions);
   		    unlockSmartLockAmPmComboBox.setItems(amPmOptions);
   		    breakInAlertSmartLockAmPmComboBox.setItems(amPmOptions);
   	   }
      }

    @FXML
    void goBackButtonPressedSmartLockAutomationPage(ActionEvent event) {
    	 switchSceneSmartLockAutomationPage("AutomationRules.fxml");
    }
    
    
    
    //-----------------------------Vacuum Robot Automation Rules Page-------------------------------------------
    
    @FXML
    private TextField startCleainingHH;

    @FXML
    private ComboBox<String> startCleaningAmPmComboBox;

    @FXML
    private Button startCleaningButtonVacuumAutomation;

    @FXML
    private TextField startCleaningMM;

    @FXML
    private GridPane vacuumRobotAutomationGridPane;

    @FXML
    void startCleaningButtonPressedVacuumAutomation(ActionEvent event) {
    	String hour = startCleainingHH.getText();
        String minute = startCleaningMM.getText();
        String amPm = startCleaningAmPmComboBox.getValue();

        // Construct a message with the desired schedule time in 24-hour format
        String time = convertTo24HourFormat(hour, minute, amPm);
        
        client.startCleaningAt(time);
        
        setLabelVacuumRobot("The Vacuum Robot is going to START CLEANING at: " + time);
    }
    
    private void setupComboBoxesVacuumRobot() {
    	   if (startCleaningAmPmComboBox != null) {
    			// Populating the AM/PM ComboBoxes
    		    ObservableList<String> amPmOptions = FXCollections.observableArrayList("AM", "PM");
    		    startCleaningAmPmComboBox.setItems(amPmOptions);
    	   }
       }
    
    
    
    //-----------------------------Smart Doorbell Automation Rules Page-------------------------------------------
    
    @FXML
    private Button activateNightModeAtButton;

    @FXML
    private ComboBox<String> activateNightModeDoorbellAmPmComboBox;

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
    private ComboBox<String> turnOffDoorbellAmPmComboBox;

    @FXML
    private Button turnOffDoorbellAtButton;

    @FXML
    private TextField turnOffDoorbellHH;

    @FXML
    private TextField turnOffDoorbellMM;

    @FXML
    private ComboBox<String> turnOnDoorbellAmPmComboBox;

    @FXML
    private Button turnOnDoorbellAtButton;

    @FXML
    private TextField turnOnDoorbellHH;

    @FXML
    private TextField turnOnDoorbellMM;
    
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
    
    private void setupChoiceBoxesSmartDoorbell() {
    	if(this.activateNightModeDoorbellAmPmComboBox != null && this.turnOffDoorbellAmPmComboBox != null && this.turnOnDoorbellAmPmComboBox != null) {
    		ObservableList<String> amPmOptions = FXCollections.observableArrayList("AM", "PM");
    		activateNightModeDoorbellAmPmComboBox.setItems(amPmOptions);
    		turnOnDoorbellAmPmComboBox.setItems(amPmOptions);
    		turnOffDoorbellAmPmComboBox.setItems(amPmOptions);
    	}
    }

    @FXML
    void activateNightModeAtButtonPressed(ActionEvent event) {
    	String hour = activateNightModeHH.getText();
        String minute = activateNightModeMM.getText();
        String amPm = activateNightModeDoorbellAmPmComboBox.getValue();

        // Construct a message with the desired schedule time in 24-hour format
        String time = convertTo24HourFormat(hour, minute, amPm);
        
        client.activateNightModeAt(time);
        
        setSmartDoorbellLabelAutomation("Smart Doorbell is scheduled to activate night mode at: " + time);
    }

    @FXML
    void goBackButtonPressedSmartLDoorbellAutomation(ActionEvent event) {
    	switchSceneSmartDoorbellAutomationPage("AutomationRules.fxml");
    }

    @FXML
    void turnOnDoorbellAtButtonPressed(ActionEvent event) {
    	String hour = turnOnDoorbellHH.getText();
        String minute = turnOnDoorbellMM.getText();
        String amPm = turnOnDoorbellAmPmComboBox.getValue();

        // Construct a message with the desired schedule time in 24-hour format
        String time = convertTo24HourFormat(hour, minute, amPm);
        
        client.turnOnDoorbellAt(time);
        
        setSmartDoorbellLabelAutomation("Smart Doorbell is scheduled turn on at: " + time);
    }
    
    @FXML
    void turnOffDoorbellAtButtonPressed(ActionEvent event) {
    	String hour = turnOffDoorbellHH.getText();
        String minute = turnOffDoorbellMM.getText();
        String amPm = turnOffDoorbellAmPmComboBox.getValue();

        // Construct a message with the desired schedule time in 24-hour format
        String time = convertTo24HourFormat(hour, minute, amPm);
        
        client.turnOffDoorbellAt(time);
        
        setSmartDoorbellLabelAutomation("Smart Doorbell is scheduled turn off at: " + time);
    }
    
    public void setSmartDoorbellLabelAutomation (String lbl) {
    	Platform.runLater(() -> {
    		if(this.smartDoorbellAutomationLabelHidden != null) {
    			smartDoorbellAutomationLabelHidden.setText(lbl);
    		}
    	});
    }
}
//end of SHClientController.java