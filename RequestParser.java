import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * 
 * @author Ragheb Barheyan
 * @version 1.0, Oct 24, 2014
 */
public class RequestParser {
	private boolean validRequest = false;
	private String GETline = "";
	private String objectPath = "";
	
	/**
	 * 
	 * @param in the input stream containing the request
	 */
	public RequestParser(InputStream in)
	{	
		try {
			//get the bytes available to read
			byte[] bytes = new byte[in.available()];
			//read them
			in.read(bytes, 0, bytes.length);
			//check if the first line is properly formatted
			checkValidRequest(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Parse out the object path if it's a valid request
		if(validRequest)
		{
			parseObjectPath();
		}
	}
	
	private void parseObjectPath()
	{
		String[] parsedGetLine = GETline.split(" ");
		for(int i = 0; i< parsedGetLine.length; i++)
		{
			if(parsedGetLine[i].startsWith("/"))
			{
				objectPath = parsedGetLine[i];
				break;
			}
		}
	}
	
	private void checkValidRequest(byte[] bytes)
	{
		String parsedBytes = "";
		for(int i =0; i < bytes.length; i++)
		{
			if (Character.toChars(bytes[i])[0] != '\n')
			{
				parsedBytes += Character.toChars(bytes[i])[0];
			}
			
			else if(Character.toChars(bytes[i])[0] != '\r')
			{
				if(parsedBytes.contains("GET") && (parsedBytes.contains("HTTP/1.0") || parsedBytes.contains("HTTP/1.1")))
				{
					validRequest = true;
					GETline = parsedBytes;
				}
				else
				{
					validRequest = false;
				}
				break;
			}
		}
	}
	
	/**
	 * 
	 * @return whether request is valid
	 */
	public boolean GetValidRequest()
	{
		return validRequest;
	}
	
	/**
	 * 
	 * @return the path to the object from the request
	 */
	public String GetObjectPath()
	{	 
		return objectPath;
	}
}
