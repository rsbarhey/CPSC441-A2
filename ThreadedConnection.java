import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

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
		try {
			Scanner in = new Scanner(new InputStreamReader(connectedSocket.getInputStream()));
			System.out.println(in.nextLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
