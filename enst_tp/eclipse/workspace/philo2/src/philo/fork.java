package philo;

import java.util.Arrays;

public class fork{
	public boolean[] forks = {false,false,false,false,false};
	
	public synchronized void takefork() 
	{
		philo p = (philo) Thread.currentThread();
		int i = p.id;
		while(forks[i]||forks[(i+1)%5])
		{
			try
			{			
				this.wait();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		forks[i]=true;
		forks[(i+1)%5]=true;
		System.out.println("philo"+p.id+" \""+p.name+"\"has taken his forks, and the state of forks is: "+Arrays.toString(forks));
	}
	
	public synchronized void putfork()
	{
		philo p = (philo) Thread.currentThread();
		int i = p.id;
		forks[i] = false;
		forks[(i+1)%5] = false;
		System.out.println("philo"+p.id+" \""+p.name+"\"has put his forks, and the state of forks is: "+Arrays.toString(forks));
		this.notifyAll();
	}
}
