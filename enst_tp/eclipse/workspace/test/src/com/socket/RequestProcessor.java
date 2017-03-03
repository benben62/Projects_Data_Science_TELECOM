package com.socket;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;



public class RequestProcessor extends Thread {
	private Socket socket = null ;
	private String str_send = null;
	private String str_receive = null;
	DataInputStream rd = null;
	DataOutputStream wr = null;
	public RequestProcessor (Socket socket)
	{
		this.socket = socket;
	}
	public void run()
	{
		
			try{ 
			
				//Receive the message of socket
				rd= new DataInputStream(socket.getInputStream());
				str_receive = rd.readUTF();
				System.out.println("The server has received the message from a client: "+str_receive);

				//handle the message_receive
				str_send = "Hello "+str_receive+"!";
				
				//respond the message handled
				wr = new DataOutputStream(socket.getOutputStream());
				wr.writeUTF(str_send);
				System.out.println("The server has reponded: "+str_send);
				
				//close the i/o stream
				rd.close();
				wr.close();

			}catch (Exception e){
				e.printStackTrace();
			}
			finally{
				if(socket!=null){
					try {
						socket.close();    //close the socket
					} catch (IOException e) {
						socket = null;
						e.printStackTrace();
					}
				}
			}

		
	}
}
