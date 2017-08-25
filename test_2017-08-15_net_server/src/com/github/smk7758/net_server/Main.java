package com.github.smk7758.net_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
	public static Boolean thread = true;

	public static void main(String[] args) {
		ServerSocket server_socket = null;
		Socket socket = null;
		try {
			server_socket = new ServerSocket(25565);
			socket = server_socket.accept();
			System.out.println("Adress from: " + socket.getInetAddress());
			//Recieve
			RecieveThread rt = new RecieveThread(socket);
			rt.start();
			//Send
			OutputStream os = socket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			String input_string = null;
			BufferedReader sbir = new BufferedReader(new InputStreamReader(System.in));
//			BufferedWriter sbow = new BufferedWriter(new OutputStreamWriter(System.out));
			do {
				try {
					input_string = sbir.readLine();
					osw.write(input_string);
					osw.write("\r\n");
					osw.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} while (!input_string.equals("XX") || socket.isClosed());
			thread = false;
			System.out.println("Stop!");
			rt.interrupt();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
				server_socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean getThread() {
		return thread;
	}
}
