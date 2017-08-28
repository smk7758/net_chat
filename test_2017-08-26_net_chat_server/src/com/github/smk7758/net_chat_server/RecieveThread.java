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
	@Override
	public void run() {
		BufferedReader bisr = new BufferedReader(new InputStreamReader(is));
		String receivedString = null;
		try {
			while (!Thread.currentThread().isInterrupted() && Main.loop_sub && (receivedString = bisr.readLine()) != null) {
				System.out.println(receivedString);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Stop recieve loop.");
		Main.ct.close();
		Main.loop_sub = false;
		System.out.println("切断されました。");
	}
}
