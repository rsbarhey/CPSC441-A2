import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class MainThread extends Thread{
	private ServerSocket serverSocket;
	private boolean terminated = false;
	private final int SOCKET_TIMEOUT = 1000;
	
	public MainThread(ServerSocket socket)
	{
		serverSocket = socket;
		try {
			serverSocket.setSoTimeout(SOCKET_TIMEOUT);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
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
	
	public void terminate()
	{
		terminated = true;
	}
}
