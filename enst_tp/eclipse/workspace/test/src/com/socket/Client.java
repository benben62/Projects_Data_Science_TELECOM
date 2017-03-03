package com.socket;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread{
	public final String ADDR = "localhost";
	public final int PORT = 55000 ;
	private String message_send= "This is the first try of connect";
	private String message_receive = null;
	DataInputStream rd = null;
	DataOutputStream wr = null;
	private int id = 0;
	
	public Client(int id)
	{
		this.id = id;
	}
	public void run(){
		while(true)
		{
			Socket socket = null;
			try {
				
				socket = new Socket(ADDR,PORT);
				
				wr = new DataOutputStream(socket.getOutputStream());
				message_send +=id;
				wr.writeUTF(message_send);
				System.out.println("The client has sended the message: "+message_send);

				
				rd= new DataInputStream(socket.getInputStream());
				message_receive = rd.readUTF();
				System.out.println("The client has received the message: "+message_receive);

				
				rd.close();
				wr.close();
				
				if(message_receive!=null) //if the client get the received message, stop to connect
				{
					break;

				}
	
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			} finally{
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

}
