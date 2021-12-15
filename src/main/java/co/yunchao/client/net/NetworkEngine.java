package co.yunchao.client.net;

import com.almasb.fxgl.core.EngineService;

public class NetworkEngine extends EngineService {
    private static Interface network;

    public static void setNetwork(Interface n) {
        network = n;
    }

    @Override
    public void onExit() {
        if (network != null) {
            try {
                network.shutdown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
