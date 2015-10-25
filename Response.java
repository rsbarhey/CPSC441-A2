import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Response {
	
	final String HTTP_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz";
	private DateFormat dateFormatter = new SimpleDateFormat(HTTP_DATE_FORMAT, Locale.ENGLISH);
	
	private int httpStatusCode;
	private byte[] responseBody;
	File fileToTransmit;
	
	public Response(String objectPath, boolean validRequest)
	{
		if(!validRequest)
		{
			httpStatusCode = 400;
			try {
				String response = "HTTP/1.1 "+Integer.toString(httpStatusCode)+" Bad Request\r\n";
				response += "\r\n";
				
				response += "400 Bad Request\r\n";
				responseBody = response.getBytes("US-ASCII");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		else
		{
			System.out.println(System.getProperty("user.dir") + objectPath);
			fileToTransmit = new File(System.getProperty("user.dir") + objectPath);
			if(fileToTransmit.exists())
			{
				httpStatusCode = 200;
				String response = "HTTP/1.1 "+Integer.toString(httpStatusCode) + " OK\r\n";
				Date currentDate = new Date();
				response += "Date: " +dateFormatter.format(currentDate) + "\r\n";
				response += "Server: rsbarhey\r\n";
				try {
					FileInputStream fin = new FileInputStream(fileToTransmit);
					
					byte [] fileContents = new byte[(int) fileToTransmit.length()];
					fin.read(fileContents, 0, fileContents.length);
					
					response += "Last-Modified: "+ dateFormatter.format(fileToTransmit.lastModified()) + "\r\n";
					response += "Content-Length: " + Integer.toString(fileContents.length) + "\r\n";
					response += "Connection: close\r\n";
					response += "\r\n";
					
					responseBody = new byte[response.getBytes("US-ASCII").length + (int) fileToTransmit.length()];
					
					for(int i = 0; i<response.getBytes("US-ASCII").length; i++)
					{
						responseBody[i] = response.getBytes("US-ASCII")[i];
					}
					
					int offset = response.getBytes("US-ASCII").length;
					
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
