package com.github.smk7758.net_chat_client;

import java.io.BufferedReader;
import java.io.FileWriter;
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

		// make log_file.
		String log_file_path = null;
		// ユーザー名がsmk7758の時
		if (System.getProperty("user.name").equals("smk7758")) log_file_path = "F:\\users\\smk7758\\Desktop\\log_client.txt";
		else log_file_path = System.getProperty("user.home") + "\\Desktop\\log_client.txt";
		try (FileWriter fw = new FileWriter(log_file_path, true)) { //この制御文が良くない。
			while ((receivedString = bisr.readLine()) != null && Main.thread) {
				fw.write(receivedString + "\r\n");
				System.out.println(receivedString);
				fw.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("切断されました。");
		Main.thread = false;
	}
}
