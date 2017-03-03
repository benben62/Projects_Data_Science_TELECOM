package com.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer extends Thread
{
	private final int PORT = 55000;
	ServerSocket serverSocket = null; 
	Socket socket = null;
	
	public void run(){
		try {
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(true)
		{

			try {	
				//sleep(100);
				socket = serverSocket.accept();
				RequestProcessor rp = new RequestProcessor(socket); 
				rp.start();
				//rp.join();
	        } catch (Exception e){
	         	e.printStackTrace();
	        }
	            
	        }
		}
	
}