import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @author Ragheb Barheyan
 * @version 0.1, Oct 22, 2015 
 */
public class WebServer {

	private ServerSocket serverSocket;
	private MainThread mainThread;
	
	public WebServer(int serverPort) {
		try {
			serverSocket = new ServerSocket(serverPort);
			mainThread = new MainThread(serverSocket);
		} catch (IOException e) {
			System.out.println("Failed to initialize the server");
			e.printStackTrace();
		}
	}

	public void start() {
		mainThread.start();
	}

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
