package com.github.smk7758.net_chat_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Main {
	public static boolean thread = true; // ループ処理についてのもの

	public static void main(String[] args) {
		System.out.println("Start client!");
		RecieveThread rt = null;
		try (Socket socket = new Socket("127.0.0.1", 25565); InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();) {
			// start RecieveThread.
			rt = new RecieveThread(is);
			rt.start();
			// Send
			sender(os);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// stop RecieveThread.
			thread = false;
			if (rt != null) rt.interrupt();
		}
		System.out.println("Stop client!");
	}

	public static void sender(OutputStream os) throws IOException {
		OutputStreamWriter osw = new OutputStreamWriter(os);
		String input_string = null;
		BufferedReader sbir = new BufferedReader(new InputStreamReader(System.in));
		do {
			input_string = sbir.readLine();
			osw.write(input_string);
			osw.write("\r\n");
			osw.flush();
		} while (!input_string.equals("XX") || thread == false);
	}
}
