package java_swing;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.*;
import javax.swing.*;

public class Myframe extends JFrame{
    private static final int WIDTH=600;
    private static final int HEIGHT=400;
    private JButton add1, add2, close;
    private JTextArea jta;
    private JMenuBar jmb;
    private JMenu jm;
    private JToolBar jtb;
    private JMenuItem m_add1, m_add2, t_add1, t_add2;

	public static void main(String[] args) {
		new Myframe();
	}

	public Myframe(){
		setTitle("test");
        setSize(WIDTH,HEIGHT);
        setJMenuBar(jmb = new JMenuBar());
        jmb.add(jm = new JMenu("Menu"));
        jmb.add(jtb = new JToolBar());
        jm.add(m_add1 = new JMenuItem("add1"));
        jm.add(m_add2 = new JMenuItem("add2"));
        jtb.add(t_add1 = new JMenuItem("add1"));
        jtb.add(t_add2 = new JMenuItem("add2"));

        Container con=getContentPane();
        Box hbox1=Box.createHorizontalBox();
        Box hbox2=Box.createHorizontalBox();
        Box hbox3=Box.createHorizontalBox();

		hbox1.add(add1 = new JButton("add1"));
		hbox1.add(add2 = new JButton("add2"));
		JScrollPane scroll = new JScrollPane(jta = new JTextArea());
		hbox2.add(scroll);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jta.setLineWrap(true);
		hbox3.add(close = new JButton("close")	);

        Box vbox=Box.createVerticalBox();
        vbox.add(hbox1);
        vbox.add(hbox2);
        vbox.add(hbox3);
        
        con.add(vbox,BorderLayout.CENTER);

		add1.addActionListener(new AddListener());
		add2.addActionListener(new AddListener());
		m_add1.addActionListener(new AddListener());
		m_add2.addActionListener(new AddListener());
		t_add1.addActionListener(new AddListener());
		t_add2.addActionListener(new AddListener());
		close.addActionListener(new CloseListener());

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	private class AddListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if (e.getSource() == add1){
				jta.append("add1 from button\n");
			}else if (e.getSource() == add2){
				jta.append("add2 from button\n");
			}else if (e.getSource() == m_add1){
				jta.append("add1 from Menu\n");
			}else if (e.getSource() == m_add2){
				jta.append("add2 from Menu\n");
			}else if (e.getSource() == t_add1){
				jta.append("add1 from ToolBar\n");
			}else if (e.getSource() == t_add2){
				jta.append("add2 from ToolBar\n");
			}
		}
	}
	private class  CloseListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.exit(0);
		}
	}
}