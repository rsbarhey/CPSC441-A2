import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @author Ragheb Barheyan
 * @version 1.0, Oct 24, 2015 
 */
public class WebServer {

	private ServerSocket serverSocket;
	private MainThread mainThread;
	
	/**
	 * 
	 * @param serverPort the port number for the ServerSocket
	 */
	public WebServer(int serverPort) {
		try {
			serverSocket = new ServerSocket(serverPort);
			mainThread = new MainThread(serverSocket);
		} catch (IOException e) {
			System.out.println("Failed to initialize the server");
			e.printStackTrace();
		}
	}

	/**
	 * spawns the main thread of the server
	 */
	public void start() {
		mainThread.start();
	}

	/**
	 * terminate the main thread along with sub threads
	 */
	public void stop() {
		if(mainThread.isAlive())
		{
			mainThread.terminate();
			try {
				mainThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
