package com.github.smk7758.net_client;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class RecieveThread extends Thread {
	Socket socket = null;

	public RecieveThread(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			InputStream is = socket.getInputStream();
			BufferedReader bisr = new BufferedReader(new InputStreamReader(is));
			String receivedString = null;
			// log_file
			FileWriter fw = null;
			if (System.getProperty("user.name").equals("smk7758")) fw = new FileWriter("F:\\users\\smk7758\\Desktop\\log_client.txt", true);
			else fw = new FileWriter(System.getProperty("user.home") + "\\Desktop\\log_client.txt", true);
			// log_file
			while ((receivedString = bisr.readLine()) != null && Main.getThread()) {
				fw.write(receivedString + "\r\n");
				System.out.println(receivedString);
			}
			System.out.println("切断されました。");
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
