package co.yunchao.client.net;

import com.almasb.fxgl.core.EngineService;

public class NetworkEngine extends EngineService {
    private static Network network;

    public static void setNetwork(Network n) {
        network = n;
    }

    @Override
    public void onExit() {
        if (network != null) {
            try {
                network.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
