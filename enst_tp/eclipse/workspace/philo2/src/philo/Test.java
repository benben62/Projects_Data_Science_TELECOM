package philo;

import java.io.IOException;
import java.util.Arrays;

public class Test {
	public static void main (String []args) throws IOException, InterruptedException
	{
		fork fork = new fork();
		System.out.println("Now we have 5 professors and the state of forks is: "+Arrays.toString(fork.forks)+"\n");
		int CHOOSE = 1;   //0 means txt 1 means console
		
		int num_philo = 5;
		philo[] p = new philo[num_philo];
		p[0] = new philo("TING",fork);
		p[1] = new philo("XIN",fork);
		p[2] = new philo("BANG",fork);	
		p[3] = new philo("GUO",fork);
		p[4] = new philo("NAN",fork);
				
		for(int i=0;i<num_philo;i++){   
			p[i].start();                 //start all the philos
		}
		
		for(int i=0;i<num_philo;i++){
			p[i].join();                  //wait for the end of all the philos
		}
		
		for(int i=0;i<num_philo;i++){
			p[i].ChooseShow(CHOOSE);      //choose the ways to show the eatingtimes of all the philos
		}

	}

}
