import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 * 
 * @author Ragheb Barheyan
 * @version 1.0, Oct 24, 2015
 */
public class MainThread extends Thread{
	private ServerSocket serverSocket;
	private boolean terminated = false;
	private final int SOCKET_TIMEOUT = 1000;
	
	/**
	 * 
	 * @param socket the server socket on which the server will be listing on
	 */
	public MainThread(ServerSocket socket)
	{
		serverSocket = socket;
		try {
			serverSocket.setSoTimeout(SOCKET_TIMEOUT);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	@Override
	/**
	 * Spawns child thread whenever we get new connections
	 */
	public void run()
	{
		System.out.println("Server running");
		while (!terminated)
		{
			try 
			{
				Socket socket = serverSocket.accept();				
				
				ThreadedConnection tConn = new ThreadedConnection(socket);
				System.out.println("Accepted a connection");
				tConn.start();
				
			}
			catch (SocketTimeoutException e)
			{
				
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Terminates the main thread
	 */
	public void terminate()
	{
		terminated = true;
	}
}
