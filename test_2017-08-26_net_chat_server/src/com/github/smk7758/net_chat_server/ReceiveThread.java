package com.github.smk7758.net_chat_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReceiveThread extends Thread {
	public volatile InputStream is = null;

	// Recieve
	@Override
	public void run() {
		BufferedReader bisr = new BufferedReader(new InputStreamReader(is));
		String received_string = null;
		try {
			while (!Thread.currentThread().isInterrupted() && Main.loop_sub && (received_string = bisr.readLine()) != null) {
				System.out.println(received_string);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Stop recieve loop.");
		Main.ct.os = null;
		Main.loop_sub = false;
		System.out.println("切断されました。");
	}
}
