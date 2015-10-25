import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class RequestParser {
	private boolean validRequest = false;
	private String GETline = "";
	private String objectPath = "";
	
	public RequestParser(InputStream in)
	{	
		try {
			byte[] bytes = new byte[in.available()];
			in.read(bytes, 0, bytes.length);
			checkValidRequest(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
	
	public boolean GetValidRequest()
	{
		return validRequest;
	}
	
	public String GetObjectPath()
	{	 
		return objectPath;
	}
}
