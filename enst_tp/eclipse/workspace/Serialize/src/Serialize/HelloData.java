package Serialize;

import java.io.*;

public class HelloData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4881748354403960569L;
	String message = "message";
	transient String transientMessage = "transientMessage";
	
	public void set(String message, String transientMessage)
	{
		this.message = message;
		this.transientMessage = transientMessage;
	}
	
}
