package com.github.smk7758.net_chat_server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
	public static volatile boolean loop_sub = true;
	public static volatile boolean loop_main = true;
	public OutputStream os = null;
	public static RecieveThread rt = null;
	public static ConsoleThread ct = null;

	public static void main(String[] args) {
		System.out.println("Start server!");
//		RecieveThread rt = null;
//		ConsoleThread ct = null;
		ServerSocket server_socket = null;
		Socket socket = null;
		InputStream is = null;
		OutputStream os = null;
		try {
			server_socket = new ServerSocket(25565);
		} catch (IOException e) {
			System.out.println("Error: Can't bind server socket.");
			e.printStackTrace();
			return;
		}
		do {
			loop_sub = true;
			try {
				socket = server_socket.accept(); // 接続待ち
				is = socket.getInputStream();
				os = socket.getOutputStream();
				System.out.println("Adress from: " + socket.getInetAddress());
				// start RecieveThread.
				rt = new RecieveThread(is);
				rt.start();
				// start CT
				ct = new ConsoleThread(os);
				ct.start();
				System.out.println("In Main Loop Last.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} while (loop_main);
		System.out.println("Finish Main Loop.");
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			server_socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Stop server!");
	}

	public void sender(OutputStream os, String input_string) throws IOException {
		OutputStreamWriter osw = new OutputStreamWriter(os);
		osw.write(input_string + "\r\n");
		osw.flush();
		if (input_string.equals("XX")) {
			loop_sub = false;
			loop_main = false;
			System.out.println("すとぱ");
		}
	}

//	public OutputStream getOutputStream() {
//		return os;
//	}
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
