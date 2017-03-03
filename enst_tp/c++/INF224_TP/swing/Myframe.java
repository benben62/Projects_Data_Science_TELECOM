import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

public class Myframe extends JFrame{
	static final String DEFAULT_HOST = "localhost";
	static final int DEFAULT_PORT = 3331;
    private static final int WIDTH=600;
    private static final int HEIGHT=500;
    private Client myClient = null;
    private JButton add1, add2, send, close;
    private JTextArea jta;
    private JMenuBar jmb;
    private JMenu jm;
    private JToolBar jtb;
    private JMenuItem m_add1, m_add2, t_add1, t_add2;
    JComboBox<String> comboBox;
    private JLabel function, group, nom_F, nom_M, latit, longit, duree, chap, warning;
    private JTextField t_nom_F, t_nom_M, t_duree, t_latit, t_longit, t_chap, t_group;

	public static void main(String[] args) {
		new Myframe();
	}
	/**
	* @brief Myframe
	* @param 
	* @return 
	* @detail Construct the class Myframe. initialize the client
	*/
	public Myframe(){
		init_layout();
	    try {
	        myClient = new Client(DEFAULT_HOST, DEFAULT_PORT);
	      }
	      catch (Exception e) {
	        jta.append("Client: Couldn't connect to "+DEFAULT_HOST+":"+DEFAULT_PORT+"\n");
	        System.exit(1);
	      }
	    jta.append("Client connected to "+DEFAULT_HOST+":"+DEFAULT_PORT+"\n");
	}
	/**
	* @brief get_request
	* @param 
	* @return request
	* @detail According to the input of user, set the value of request and return it.
	*/
	public String get_request(){
		String request = null;
		int func = comboBox.getSelectedIndex();
		String f = t_nom_F.getText();
		String m = t_nom_M.getText();
		String g = t_group.getText();
		String d = t_duree.getText();
		String la = t_latit.getText();
		String lo = t_longit.getText();
		String c = t_chap.getText();
		if (func == 0){
			request = "createPhoto;" + f + ";" + m + ";" + la +";" +lo;
		}else if (func == 1){
			request = "createVideo;" + f + ";" + m + ";" + d;
		}else if (func == 2){
			String[] table = c.split(";");
			request = "createFilm;" + f + ";" + m + ";" + d + ";" + table.length + ";" +c;
		}else if (func == 3){
			request = "createList;" + g;
		}else if (func == 4){
			request = "displayMediaByName;" + m;
		}else if (func == 5){
			request = "displayGroupByName;" + g;
		}else if(func == 6){
			request = "playByName;" + m;	
		}else if (func == 7){
			request = "save";
		}else if (func == 8){
			request = "add;" + g + ";" + m;
		}else if (func == 9){
			request = "displayAllMedia";
		}
		return request;
	}
	/**
	* @brief send
	* @param request
	* @return 
	* @detail According to the input of user, set the value of request and return it.
	*/
	public void send(String request){
	    String response = myClient.send(request);
	    response = "\n" + response.replace(';', '\n');
	    jta.append(response);
	}
	/**
	* @brief init_layout
	* @param
	* @return 
	* @detail initialize the layout. The total layout is BoxLayout. The part of input uses GridLayout.
	*/
	public void init_layout(){
		setTitle("MyClient");
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
        Box hbox4=Box.createHorizontalBox();


		hbox1.add(add1 = new JButton("add1"));
		hbox1.add(add2 = new JButton("add2"));
		
		JScrollPane scroll = new JScrollPane(jta = new JTextArea());
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		DefaultCaret caret = (DefaultCaret) jta.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		jta.setLineWrap(true);
		jta.setRows(20);
		hbox2.add(scroll);
		
        JPanel contentPane=new JPanel();
        GridLayout gird=new GridLayout(4,4);
        contentPane.setLayout(gird);

        comboBox=new JComboBox<String>();  
        comboBox.addItem("createPhoto");
        comboBox.addItem("createVideo");
        comboBox.addItem("createFilm");
        comboBox.addItem("createList");
        comboBox.addItem("displayMediaByName");
        comboBox.addItem("displayGroupByName");
        comboBox.addItem("playByName");
        comboBox.addItem("save");
        comboBox.addItem("add");
        comboBox.addItem("displayAllMedia");
        comboBox.setMaximumSize(comboBox.getPreferredSize());
        comboBox.addActionListener(new ComboListener());
        contentPane.add(function = new JLabel("Fonction:"));
        contentPane.add(comboBox);
        contentPane.add(group = new JLabel("Nom de groupe:"));
        contentPane.add(t_group = new JTextField(10));
		t_group.setMaximumSize(t_group.getPreferredSize());
		
		contentPane.add(nom_F = new JLabel("*Nom de fichier:"));
		contentPane.add(t_nom_F = new JTextField());
		t_nom_F.setMaximumSize(t_nom_F.getPreferredSize());
		contentPane.add(nom_M = new JLabel("*Nom de média:"));
		contentPane.add(t_nom_M = new JTextField());
		t_nom_M.setMaximumSize(t_nom_M.getPreferredSize());	
		contentPane.add(latit = new JLabel("*Latitude:"));
		contentPane.add(t_latit = new JTextField());
		t_latit.setMaximumSize(t_latit.getPreferredSize());
		contentPane.add(longit = new JLabel("*Longitude:"));
		contentPane.add(t_longit = new JTextField());
		t_longit.setMaximumSize(t_longit.getPreferredSize());
		contentPane.add(duree = new JLabel("Durée:"));
		contentPane.add(t_duree = new JTextField());
		t_duree.setMaximumSize(t_duree.getPreferredSize());
		contentPane.add(chap = new JLabel("Chaptre:"));
		contentPane.add(t_chap = new JTextField());
		t_chap.setMaximumSize(t_chap.getPreferredSize());
		
		hbox3.add(send = new JButton("send"));
		send.addActionListener(new SendListener());
		hbox3.add(close = new JButton("close"));

		hbox4.add(warning = new JLabel("---------Les labels avec * sont obligatoire à remplir pour realiser cette fonction!---------"));
        Box vbox=Box.createVerticalBox();
        vbox.add(hbox1);
        vbox.add(hbox2);
        vbox.add(hbox4);
        vbox.add(contentPane);
        vbox.add(hbox3);

        con.add(vbox, BorderLayout.CENTER);

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

	private class ComboListener implements ActionListener{
		/**
		* @brief actionPerformed
		* @param e
		* @return 
		* @detail set the Actionlistener of the comboBox, so that if the user choose the 
		*         function, it will change the labels. The labels which are necessary to input
		* 		  have the labels with "*".
		*/
		public void actionPerformed(ActionEvent e){
			int func = ((JComboBox)e.getSource()).getSelectedIndex();
			if (func == 0){
				init_text();
				nom_F.setText("*Nom de fichier:");
				nom_M.setText("*Nom de média:");
				latit.setText("*Latitude:");
				longit.setText("*Longitude:");
			}else if (func == 1){
				init_text();
				nom_F.setText("*Nom de fichier:");
				nom_M.setText("*Nom de média:");
				duree.setText("*Durée:");
			}else if (func ==2){
				init_text();
				nom_F.setText("*Nom de fichier:");
				nom_M.setText("*Nom de média:");
				duree.setText("*Durée:");
				chap.setText("*Chaptre:");
			}else if (func == 3){
				init_text();
				group.setText("*Nom de groupe:");
			}else if (func == 4){
				init_text();
				nom_M.setText("*Nom de média:");
			}else if (func == 5){
				init_text();
				group.setText("*Nom de groupe:");
			}else if (func == 6){
				init_text();
				nom_M.setText("*Nom de média:");
			}else if (func == 7){
				init_text();
			}else if (func == 8){
				init_text();
				nom_M.setText("*Nom de média:");
				group.setText("*Nom de groupe:");
			}else if (func == 9){
				init_text();
			}
		}
		/**
		* @brief init_text
		* @param 
		* @return 
		* @detail initialize all the labels without "*"
		*/
		public void init_text(){
			nom_F.setText("Nom de fichier:");
			nom_M.setText("Nom de média:");
			latit.setText("Latitude:");
			longit.setText("Longitude:");
			duree.setText("Duree:");
			group.setText("Nom de groupe:");
			chap.setText("Chaptre:");

		}
	}

	private class SendListener implements ActionListener{
		/**
		* @brief actionPerformed
		* @param e
		* @return 
		* @detail send the request to server.
		*/
		public void actionPerformed(ActionEvent e){
			send(get_request());
		}
	}
	private class AddListener implements ActionListener{
		/**
		* @brief actionPerformed
		* @param e
		* @return 
		* @detail add actionlistener of add buttons.
		*/
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
		/**
		* @brief actionPerformed
		* @param e
		* @return 
		* @detail actionlistener exit.
		*/
		public void actionPerformed(ActionEvent e){
			System.exit(0);
		}
	}
}
