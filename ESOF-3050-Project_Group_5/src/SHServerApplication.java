import java.io.IOException;

public class SHServerApplication {

    public static void main(String[] args) {
        int port = 2010; // Port to connect the client.
        SHServer server = new SHServer(port);
        
        try {
            server.listen(); // Start listening for connections
        } catch (IOException e) {
            System.err.println("Error starting the server: " + e);
            // Handle the error as needed
            server.serverStopped();
        }
    }
}