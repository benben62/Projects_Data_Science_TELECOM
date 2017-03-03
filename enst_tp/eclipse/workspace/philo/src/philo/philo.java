package philo;
import java.util.Random;

public class philo extends Thread {
	int id;
	static int NUM = 0;
	Random rand = new Random();
	int time =rand.nextInt(256);
	private  fork fork;
	private int thinkingtimes = 0;
	private int eatingtimes = 0;
	String name = getName();
	public philo (String name,fork fork)
	{
		super(name);
		id = NUM;
		NUM++;
		this.fork = fork;
	}
	
	public void run()
	{
		while(true)
		{
			thinking();
			starving();
			fork.takefork();
			eating();
			fork.putfork();

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

