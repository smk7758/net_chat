package com.github.smk7758.net_chat_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class ConsoleThread extends Thread {
	OutputStream os = null;

	public ConsoleThread(OutputStream os) {
		this.os = os;
	}

	@Override
	public void run() {
		try {
			String input_string = null;
			BufferedReader sbir = new BufferedReader(new InputStreamReader(System.in));
			while (Main.loop_sub && !(input_string = sbir.readLine()).equals("XX")) {// コンソール待ち
				Main.sender(os, input_string);
			}
			System.out.println("Stop send loop.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
