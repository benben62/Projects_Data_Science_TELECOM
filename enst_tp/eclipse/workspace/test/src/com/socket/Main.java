package com.socket;

public class Main {

	public static void main(String[] args) {
		WebServer server = new WebServer();
		Client client1 = new Client(1);
		Client client2 = new Client(2);
		Client client3 = new Client(3);

		client1.start();
		client2.start();
		client3.start();
		server.start();

		// TODO Auto-generated method stub

	}

}
