package com.mercurys.handlers;

import com.mercurys.readers.MediaReaderThread;

import java.io.*;
import java.net.Socket;
import java.text.MessageFormat;
import java.util.Scanner;

public class ClientSocketHandler {

    private Socket socket;
    private DataOutputStream outputStream;
    private MediaReaderThread incomingReaderThread;
    private MessageHandler messageHandler;

    public ClientSocketHandler(String address, int port) throws IOException {
        connectToServer(address, port);
        initialiseIO();
    }

    private void connectToServer(String address, int port) throws IOException {
        this.socket = new Socket(address, port);
        System.out.println(MessageFormat.format("Connected to server [{0}] !", address));
    }

    private void initialiseIO() throws IOException {
        this.outputStream = new DataOutputStream(this.socket.getOutputStream());
        PrintWriter writer = new PrintWriter(this.outputStream, true);
        messageHandler = new MessageHandler(new Scanner(System.in), writer, outputStream);
    }

    public void talkToServer() throws IOException {
        initialiseReaderThread();
        messageHandler.sendMessagesToOtherUser();
        closeConnection();
    }

    private void initialiseReaderThread() throws IOException {
        incomingReaderThread = new MediaReaderThread(this.socket.getInputStream(),
                "[User00]");
        incomingReaderThread.start();
    }

    private void closeConnection() throws IOException {
        incomingReaderThread.exitThread();
        this.socket.close();
        this.outputStream.close();
        System.out.println("Closing connection... Goodbye!");
    }
}
