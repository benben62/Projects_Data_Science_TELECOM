package philo;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;



public class RequestProcessor extends Thread {
	public static boolean[] forks = {false,false,false,false,false};
	private Socket socket = null ;
	private String str_send = "ok";
	private String str_receive = null;
	private String apply =null;
	private int id = 0;
	private String name = null;
	DataInputStream rd = null;
	DataOutputStream wr = null;
	public RequestProcessor (Socket socket)
	{
		this.socket = socket;
	}
	public void run()
	{
		
		try{ 
		
			rd= new DataInputStream(socket.getInputStream());
			str_receive = rd.readUTF();
			//
			String[] str_r = str_receive.split(";");
			apply = str_r[0]; 
			id =Integer.valueOf(str_r[1]);
			name = str_r[2];
			
			judge();

			
			//respond the message handled
			wr = new DataOutputStream(socket.getOutputStream());
			wr.writeUTF(str_send);
			
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
	public void judge() throws IOException
	{
		if(apply!=null)
		{
			if(apply.equalsIgnoreCase("take"))
			{
				takefork();
			}
			else if(apply.equalsIgnoreCase("put"))
			{
				putfork();
			}
			else
			{
				System.out.println("Illegal Apply of"+id+name);
			}
		}
		else{
			
		}

	}
	
	public void takefork() throws IOException
	{
		synchronized(forks)
		{
			while(forks[id]||forks[(id+1)%5])
			{
				try
				{			
					forks.wait();
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			forks[id]=true;
			forks[(id+1)%5]=true;
			System.out.println("philo"+id+" \""+name+"\"has taken his forks, and the state of forks is: "+Arrays.toString(forks));
	
		}
	}
	public void putfork() throws IOException
	{
		synchronized(forks)
		{
			forks[id] = false;
			forks[(id+1)%5] = false;
			forks.notifyAll();
			System.out.println("philo"+id+" \""+name+"\"has put his forks, and the state of forks is: "+Arrays.toString(forks));
		}
	}
	
	
}
