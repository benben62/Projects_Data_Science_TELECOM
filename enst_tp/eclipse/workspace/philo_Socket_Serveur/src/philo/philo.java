package philo;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

public class philo extends Thread{
	int id = 0;
	static int NUM = 0;                            //set the current id
	static boolean JUDGE_NULL = false;

	String name = getName();
	private Random rand = new Random();
	private int time =rand.nextInt(256);           //give a random time of eating and thinking
	private int FINITIMES = 5+rand.nextInt(5);     //give the random total times of eating
	int thinkingtimes = 0;
	int eatingtimes = 0;	


	//parameters for connection
	public final String ADDR = "localhost";
	public final int PORT = 55000 ;
	private final String APPLY_TAKEFORK = "take";
	private final String APPLY_PUTFORK = "put";
	
	public philo (String name)
	{
		super(name);
		id = NUM;
		NUM++;
	}
	
	public void run()
	{
		while(eatingtimes<FINITIMES)
		{		
			thinking();
			starving();
			takefork();
			eating();
			putfork();
		}

	}
	
	public void takefork() 
	{
		Socket socket = null;
		DataOutputStream wr = null;
		DataInputStream  rd = null;
		String reponse = null;
		try
		{
			socket = new Socket(ADDR,PORT);
			wr = new DataOutputStream(socket.getOutputStream());	
			wr.writeUTF(APPLY_TAKEFORK+";"+id+";"+name);
			
			rd = new DataInputStream(socket.getInputStream());
			reponse= rd.readUTF();
			if(reponse == null)
			{
				System.out.println("apply failed");
				reponse = null;
			}
			else{

			}

			
		} catch(IOException e){
			e.printStackTrace();
		}
		finally{
			try {
				rd.close();
				wr.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void putfork()
	{
		Socket socket = null;
		DataOutputStream wr = null;
		DataInputStream  rd = null;
		String reponse = null;
		try
		{
			socket = new Socket(ADDR,PORT);
			wr = new DataOutputStream(socket.getOutputStream());	
			wr.writeUTF(APPLY_PUTFORK+";"+id+";"+name);
			
			rd = new DataInputStream(socket.getInputStream());
			reponse= rd.readUTF();
			if(reponse == null)
			{
				System.out.println("apply failed");
				reponse = null;
			}
			else{

			}
		} catch(IOException e){
			e.printStackTrace();
		}
		finally{
			try {
				rd.close();
				wr.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void ChooseShow(int show) throws IOException //show choose function
	//choose by the parametre 0 means printing into the txt;1 means printing on the console
	{
		if(show ==0)
		{
				PhiloPrint();	
		}
		else if(show ==1)
		{	
			System.out.println("philo"+id+"\""+name+"\" has eaten for"+eatingtimes+" times.");

		}
		else{}
	}
	public void PhiloPrint()throws IOException
	{
		FileWriter outputstream = null;
		try{
			if(JUDGE_NULL == false)
			{
				outputstream = new FileWriter("philo.txt");
				JUDGE_NULL = true;
			}
			else
			{
				outputstream = new FileWriter("philo.txt",true);
			}
			outputstream.write("philo"+id+" \""+name+"\""+" has eaten for "+eatingtimes+"times"+"\r\n");
		}
		finally
		{
			if(outputstream != null)
			{
				outputstream.close();
			}
		}
		
	}
	public void PhiloPrint(String p)throws IOException
	{
		FileWriter outputstream = null;
		try{
			if(JUDGE_NULL == false)
			{
				outputstream = new FileWriter("philo.txt");
				JUDGE_NULL = true;
			}
			else
			{
				outputstream = new FileWriter("philo.txt",true);
			}
			outputstream.write(p);
		}
		finally
		{
			if(outputstream != null)
			{
				outputstream.close();
			}
		}
		
	}
	public void thinking()
	{	
		System.out.println("philo"+id+" \""+name+"\" is thinking"+", and his thinkingtimes is: "+thinkingtimes);
		thinkingtimes++;
		try
		{
			time = rand.nextInt(256);
			sleep(time);
			System.out.println("philo"+id+" \""+name+"\" has finished thinking which lasted for "+time+"ms");

		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		
	}
	public void eating()
	{
		System.out.println("philo"+id+" \""+name+"\" is eating"+", and his eatingtimes is: "+eatingtimes);
		eatingtimes++;
		try
		{
			time = rand.nextInt(256);
			sleep(time);
			System.out.println("philo"+id+" \""+name+"\" has finished eating which lasted for "+time+"ms");

		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	public void starving()
	{
		System.out.println("philo"+id+" \""+name+"\" is starving");
	}

}

