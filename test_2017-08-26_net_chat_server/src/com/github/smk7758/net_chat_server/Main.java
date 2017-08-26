package com.github.smk7758.net_chat_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
	public static boolean thread = true; // ループ処理についてのもの
	public static boolean loop = true;

	public static void main(String[] args) {
		System.out.println("Start server!");
		RecieveThread rt = null;
		ServerSocket server_socket = null;
		try {
			server_socket = new ServerSocket(25565);
		} catch (IOException e) {
			System.out.println("Error: Can't bind server socket.");
			e.printStackTrace();
		}
		do {
			thread = true;
			try (Socket socket = server_socket.accept(); InputStream is = socket.getInputStream();
					OutputStream os = socket.getOutputStream();) { // 接続待ち
				System.out.println("Adress from: " + socket.getInetAddress());
				// start RecieveThread.
				rt = new RecieveThread(is);
				rt.start();
				// Send
				sender(os);
				System.out.println("In Main Loop Last.");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				thread = false; // RecieveThreadの停止。
				if (rt != null) rt.interrupt(); // 念のためのRecieveThreadの停止。
			}
		} while (loop);
		System.out.println("Finish Main Loop.");
		try {
			server_socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Stop server!");
	}

	public static void sender(OutputStream os) throws IOException {
		OutputStreamWriter osw = new OutputStreamWriter(os);
		String input_string = null;
		BufferedReader sbir = new BufferedReader(new InputStreamReader(System.in));
		while (!(input_string = sbir.readLine()).equals("XX") && thread) {// コンソール待ち
			System.out.println("|");
			osw.write(input_string);
			osw.write("\r\n");
			osw.flush();
			System.out.println("=");
		}
		System.out.println("Stop send loop.");
		if (input_string.equals("XX")) {
			thread = false;
			loop = false;
			System.out.println("すとぱ");
		}
	}

	// public static FileWriter getLogFile() {
	// String log_file_path = null;
	// if (System.getProperty("user.name").equals("smk7758")) log_file_path = "F:\\users\\smk7758\\Desktop\\log_client.txt"; // ユーザー名がsmk7758の時
	// else log_file_path = System.getProperty("user.home") + "\\Desktop\\log_client.txt";
	// try {
	// return new FileWriter(log_file_path, true);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return null;
	// }
}
