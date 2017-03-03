package philo;

import java.util.Arrays;

public class Test {
	public static void main (String []args)
	{
		fork fork = new fork();
		System.out.println("Now we have 5 professors and the state of forks is: "+Arrays.toString(fork.forks)+"\n");
		new philo("BANG",fork).start();
		new philo("QIANG",fork).start();
		new philo("TING",fork).start();
		new philo("NAN",fork).start();
		new philo("XIN",fork).start();
	}

}
