package com.aczchef.irctest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
 
public class Test implements Runnable {
 
    public static void main(String args[]) {
        try {
            new Test().start();
        } catch (java.io.IOException e) {
	    e.printStackTrace();
        }
    }
 
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
 
    protected void start() throws java.io.IOException {
        this.socket = new Socket("irc.esper.net", 6667);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
 
        if (socket.isConnected()) {
            out.write("NICK SocPuppet2\r\n");
            out.write("USER SocPuppet2 \"\" \"\" :My Real Name\r\n");
            out.flush();
            new Thread(this).start();
        }
    }
 
    @Override
    public void run() {
        String buffer;
	System.out.println("Bot Started");
        while (true) {
            try {
                while ((buffer = in.readLine()) != null) {
                    System.out.println(buffer);
                    if (buffer.startsWith("PING")) {
                        out.write("PONG " + buffer.substring(5) + "\r\n");
                        out.flush();
                    }
                }
            } catch (java.io.IOException e) {
		e.printStackTrace();
            }
        }
    }
}