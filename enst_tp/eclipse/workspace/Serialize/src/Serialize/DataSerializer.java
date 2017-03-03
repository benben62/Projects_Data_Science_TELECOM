package Serialize;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class DataSerializer extends Thread{
	public final String ADDR = "localhost";
	public final int PORT = 55000 ;

	ObjectOutputStream wr = null;
	HelloData data = new HelloData();
	
	public DataSerializer(HelloData data)
	{
		this.data = data;
	}
	
	public void run(){
		//while(true)
		{
			Socket socket = null;
			try {
				
				socket = new Socket(ADDR,PORT);
				
				wr = new ObjectOutputStream(socket.getOutputStream());	
				wr.writeObject(data);
				System.out.println("The DataSerializer has sended the HelloData_object "
				+ "which contains a message: \""+data.message+"\" and a transientMessage: \""
				+data.transientMessage+"\"");
				
				wr.flush();
				
				wr = new ObjectOutputStream(new FileOutputStream("serialize.txt"));
				wr.writeObject(data);
				System.out.println("The DataSerializer has saved the HelloData_object "
				+ "into serialize.txt which contains a message: \""+data.message
				+"\" and a transientMessage: \""+data.transientMessage+"\"");

				wr.close();
				
	
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
