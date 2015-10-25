import java.io.IOException;
import java.io.InputStream;
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
			InputStream in = connectedSocket.getInputStream();
			RequestParser requestParser = new RequestParser(in);
			
			Response response = new Response(requestParser.GetObjectPath(), requestParser.GetValidRequest());
			response.SendResponse(connectedSocket.getOutputStream());
			
			connectedSocket.shutdownInput();
			connectedSocket.shutdownOutput();
			connectedSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
