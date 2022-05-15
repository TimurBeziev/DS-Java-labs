package com.BezievTG;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class ServerThread extends Thread {

    private PrintStream out;
    private BufferedReader in;
    private int number;
    private boolean isListen = true;

    public ServerThread(Socket socket, int number) throws IOException {
        this.number = number;
        out = new PrintStream(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out.println(number);
        print("New connection");
    }

    public void run() {
        while (isListen) {
            try {
                String message = in.readLine();
                if (message.equals(Server.GET_CODE)) {
                    String path = in.readLine();
                    print("GET " + path);
                    out.println(Server.OK_CODE);

                    print("Start sending file");
                    try (Stream<String> stream = Files.lines(Paths.get(path))) {
                        stream.forEach((string) -> {
                            out.println(string);
                            try {
                                TimeUnit.SECONDS.sleep(1);
                            } catch (InterruptedException e) {
                            }
                        });
                    }
                    out.println(Server.END_CODE);
                    print("File send!");
                } else if (message.equals(Server.CLIENT_DISCONNECT_CODE)) {
                    disconnect();
                }
            } catch (IOException err) {
                System.out.println(Server.getErrorMessage(err.toString()));
                out.println(Server.NOT_FOUND_CODE);
            }
        }
    }

    private void print(String message) {
        System.out.println(message +" -> " + "(Client#" + number + ")");
    }

    void disconnect() {
        isListen = false;
        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        } catch (IOException err) {
            System.out.println(Server.getErrorMessage(err.toString()));
        } finally {
            this.interrupt();
            print("Disconnect from server");
        }
    }

    void close() {
        print("Disconnect from server");
        out.println(Server.CLOSE_CODE);
    }
}