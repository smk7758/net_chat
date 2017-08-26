package com.github.smk7758.net_chat_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Main {
	public static Boolean thread = true;

	public static void main(String[] args) {
		try (Socket socket = new Socket("127.0.0.1", 25565)) {
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			// start RecieveThread.
			RecieveThread rt = new RecieveThread(is);
			rt.start();
			// Send
			sender(os);
			// stop RecieveThread.
			thread = false;
			rt.interrupt();
			os.close();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Stop!");
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
