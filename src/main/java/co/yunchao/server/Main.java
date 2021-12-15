package co.yunchao.server;

import co.yunchao.server.controllers.Server;
import co.yunchao.server.net.Interface;

import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) {
        Server server = new Server();
        try {
            final InetSocketAddress localhost = new InetSocketAddress("localhost", 31747);

            Interface inf = new Interface(localhost, server);
        } catch (Exception e) {
            e.printStackTrace();
        }
        server.run();
    }
}
