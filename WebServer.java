import java.io.IOException;
import java.net.ServerSocket;

/**
 * 
 * @author Ragheb Barheyan
 * @version 0.1, Oct 22, 2015 
 */
public class WebServer {

	private ServerSocket serverSocket;
	public WebServer(int serverPort) {
		try {
			serverSocket = new ServerSocket(serverPort);
		} catch (IOException e) {
			System.out.println("Failed to initialize the server");
			e.printStackTrace();
		}
	}

	public void start() {

	}

	public void stop() {
		
	}

}
