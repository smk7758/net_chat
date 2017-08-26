package com.github.smk7758.net_chat_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RecieveThread extends Thread {
	InputStream is = null;

	public RecieveThread(InputStream is) {
		this.is = is;
	}

	// Recieve
	public void run() {
		BufferedReader bisr = new BufferedReader(new InputStreamReader(is));
		String receivedString = null;
//		FileWriter fw = Main.getLogFile();
		try {
			while ((receivedString = bisr.readLine()) != null && Main.thread) {
				System.out.println(receivedString);
//				if (fw != null) {
//					fw.write(receivedString + "\r\n");
//				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		Main.thread = false;
		System.out.println("Stop recieve loop.");
		System.out.println("切断されました。");
	}
}
