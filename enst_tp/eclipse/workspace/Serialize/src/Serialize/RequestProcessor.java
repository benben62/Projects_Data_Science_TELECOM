package Serialize;



import java.io.*;
import java.net.Socket;



public class RequestProcessor extends Thread {
	private Socket socket = null ;
	ObjectInputStream rd = null;
	ObjectInputStream rd_txt = null;

	HelloData data = null;
	public RequestProcessor (Socket socket)
	{
		this.socket = socket;
	}
	public void run()
	{
		
			try{ 
			
				//Receive the message of socket
				rd= new ObjectInputStream(socket.getInputStream());
				data = (HelloData) rd.readObject();
				System.out.println("The DataUnserializer has received the message from a DataSerializer:"+data.message);
				System.out.println("The DataUnserializer has received the transientMessage from a DataSerializer:"+data.transientMessage);
				rd.close();

				rd_txt = new ObjectInputStream(new FileInputStream("serialize.txt"));
				data = (HelloData) rd_txt.readObject();
				System.out.println("The DataUnserializer has received the message from txt:"+data.message);
				System.out.println("The DataUnserializer has received the transientMessage from txt:"+data.transientMessage);
				rd_txt.close();
				
				//close the i/o stream

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
