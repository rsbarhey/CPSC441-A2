import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author Ragheb Barheyan
 * @version 1.0, 22 Oct, 2015
 */
public class ThreadedConnection extends Thread{
	private Socket connectedSocket;
	/**
	 * 
	 * @param socket The client's socket
	 */
	public ThreadedConnection(Socket socket)
	{
		connectedSocket = socket;
	}
	
	/**
	 * Runs the thread responsible to send back a response
	 */
	public void run()
	{
		try {
			//Get the input stream of the socket
			InputStream in = connectedSocket.getInputStream();
			//Create the requestParser to parse the object
			RequestParser requestParser = new RequestParser(in);
			
			//Create a response object, sets up an http response
			Response response = new Response(requestParser.GetObjectPath(), requestParser.GetValidRequest());
			
			//Get the output stream of the socket
			OutputStream out = connectedSocket.getOutputStream();
			//Send the response over to the client
			response.SendResponse(out);
			
			//Preparing to close the socket
			connectedSocket.shutdownInput();
			connectedSocket.shutdownOutput();
			connectedSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
