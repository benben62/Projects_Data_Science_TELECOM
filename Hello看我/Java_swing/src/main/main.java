package main;

import javax.swing.*;

import java.awt.FlowLayout;
import java.awt.event.*;

public class main extends JFrame{
	JButton doIt, close;
	JLabel label = new JLabel();

	public static void main(String[] args) {
		new main();
	}

	public main(){
		setTitle("test");
		setLayout(new FlowLayout());
		add(doIt = new JButton("Do It"));
		add(close = new JButton("Close"));
		add(label);

		doIt.addActionListener(new DoItListener());
		close.addActionListener(new CloseListener());

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	public class DoItListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			label.setText("Done!");
			pack();
		}
	}
	public class  CloseListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.exit(0);
		}
	}
}