package com.socket;

public class Main {

	public static void main(String[] args) {
		WebServer server = new WebServer();
		Client client = new Client();
		client.start();
		server.start();

		// TODO Auto-generated method stub

	}

}
