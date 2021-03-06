package com.mercurys;

import com.mercurys.handlers.ServerSocketHandler;

import java.io.IOException;
import java.util.Scanner;

public class MercurysServer {

    private ServerSocketHandler serverSocketHandler;

    private MercurysServer(String hostAddress, int port) {
        try {
            serverSocketHandler = new ServerSocketHandler(hostAddress, port);
        } catch (final IOException e) {
            System.out.println("Exception occurred!");
            e.printStackTrace();
        }
    }

    public static void main(final String[] args) {
        final MercurysServer server = new MercurysServer(getIPAddress(), 4444);
        server.startTalking();
        System.out.println("Thank you for using Project Mercurys!");
    }

    private static String getIPAddress() {
        System.out.println("Enter your device's LAN IP address: ");
        return new Scanner(System.in).nextLine();
    }

    private void startTalking() {
        try {
            serverSocketHandler.talkToClient();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
