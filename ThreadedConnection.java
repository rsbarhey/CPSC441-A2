import java.net.Socket;

/**
 * @author Ragheb Barheyan
 * @version 0.1, 22 Oct, 2015
 */
public class ThreadedConnection extends Thread{
	private Socket connectedSocket;
	public ThreadedConnection(Socket socket)
	{
		connectedSocket = socket;
	}
	
	public void run()
	{
		// read the http request
		// handle it properly
		// return an http response   
	}
}
