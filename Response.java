import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 
 * @author Ragheb Barheyan
 * @version 1.0, Oct 24, 2015
 */
public class Response {
	// the Date format
	final String HTTP_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz";
	private DateFormat dateFormatter = new SimpleDateFormat(HTTP_DATE_FORMAT, Locale.ENGLISH);
	
	private int httpStatusCode;
	private byte[] responseBody;
	File fileToTransmit;
	
	/**
	 * 
	 * @param objectPath The path to the object
	 * @param validRequest The validity of the request
	 */
	public Response(String objectPath, boolean validRequest)
	{
		//If not valid set 400 bad request
		if(!validRequest)
		{
			httpStatusCode = 400;
			try {
				String responseHeaders = "HTTP/1.1 "+Integer.toString(httpStatusCode)+" Bad Request\r\n";
				responseHeaders += "\r\n";
				
				//Send response body as a simple string to show on the browser
				responseHeaders += "400 Bad Request\r\n";
				//convert the string to bytes
				responseBody = responseHeaders.getBytes("US-ASCII");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		else
		{
			// Initialize the file
			fileToTransmit = new File(System.getProperty("user.dir") + objectPath);
			//Check if the file exists
			if(fileToTransmit.exists())
			{
				httpStatusCode = 200;
				try {
					//Convert the file into byte[]
					FileInputStream fin = new FileInputStream(fileToTransmit);
					byte [] fileContents = new byte[(int) fileToTransmit.length()];
					fin.read(fileContents, 0, fileContents.length);
					
					//Build the response headers
					String responseHeaders = "HTTP/1.1 "+Integer.toString(httpStatusCode) + " OK\r\n";
					Date currentDate = new Date();
					responseHeaders += "Date: " +dateFormatter.format(currentDate) + "\r\n";
					responseHeaders += "Server: rsbarhey\r\n";
					responseHeaders += "Last-Modified: "+ dateFormatter.format(fileToTransmit.lastModified()) + "\r\n";
					responseHeaders += "Content-Length: " + Integer.toString(fileContents.length) + "\r\n";
					responseHeaders += "Connection: close\r\n";
					responseHeaders += "\r\n";
					
					// Initialize the body to be the length of headers + file length
					responseBody = new byte[responseHeaders.getBytes("US-ASCII").length + (int) fileToTransmit.length()];
					
					//Copy the headers into the response body
					for(int i = 0; i<responseHeaders.getBytes("US-ASCII").length; i++)
					{
						responseBody[i] = responseHeaders.getBytes("US-ASCII")[i];
					}
					
					//Offset the response body by the headers length 
					int offset = responseHeaders.getBytes("US-ASCII").length;
					
					//Copy the file into response body at the offset
					for(int i = offset; i < responseBody.length; i++)
					{
						responseBody[i] = fileContents[i - offset];
					}
					
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else
			{
				//The file doesn't exist
				httpStatusCode = 404;
				String response = "HTTP/1.1 "+Integer.toString(httpStatusCode)+" Not Found\r\n";
				response += "\r\n";
				response += "404 Not Found\r\n";
				try {
					responseBody = response.getBytes("US-ASCII");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Sends the response over the output stream 
	 * @param out The output stream of the socket
	 */
	public void SendResponse(OutputStream out)
	{
		try {
			out.write(responseBody);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
