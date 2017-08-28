package com.github.smk7758.net_chat_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class ConsoleThread extends Thread {
	Main main = new Main();
	public volatile OutputStream os = null;
	BufferedReader sbir = null;

	@Override
	public void run() {
		try {
			String input_string = null;
			sbir = new BufferedReader(new InputStreamReader(System.in));
			while (!Thread.currentThread().isInterrupted() && Main.loop_sub) {
				// if (sbir.ready()) {
				if (os != null) {
					input_string = sbir.readLine();
					if (input_string != null) {
						main.sender(os, input_string);
						if (input_string.equals("XX")) {
							Main.loop_sub = false;
							Main.loop_main = false;
							break;
						}
					}
				}
				// }
			}
			System.out.println("Stop send loop.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			sbir.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
