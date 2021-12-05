package co.yunchao.client;

import co.yunchao.client.net.Interface;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) {
        try {
            final InetSocketAddress localhost = new InetSocketAddress("localhost", 31747);

            Interface inf = new Interface(localhost);

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
