package co.yunchao.server;

import co.yunchao.server.net.Interface;

import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) {
        try {
            final InetSocketAddress localhost = new InetSocketAddress("localhost", 31747);

            Interface inf = new Interface(localhost);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
