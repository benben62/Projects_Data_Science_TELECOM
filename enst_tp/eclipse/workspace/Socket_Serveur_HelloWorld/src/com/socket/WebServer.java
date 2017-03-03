package com.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer extends Thread
{
	private final int PORT = 55000;
	
	public void run(){
		
		while(true)
		{
			ServerSocket serverSocket = null; 
			Socket socket = null;
			try {	
				//sleep(100);
				serverSocket = new ServerSocket(PORT);
				socket = serverSocket.accept();
				RequestProcessor rp = new RequestProcessor(socket); 
				rp.start();
				rp.join();
	        } catch (Exception e){
	         	e.printStackTrace();
	        }finally{	
						try {
							socket.close();
							serverSocket.close();
						} catch (IOException e) {
							e.printStackTrace();
						} 
				
	        	}
	            
	        }
		}
	
}