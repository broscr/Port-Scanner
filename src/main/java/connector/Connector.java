package connector;

import model.Status;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Connector {

    private final int TIMEOUT = 1000;
    private final int THREAD_SIZE = 100;
    private final String ip;
    private final int[] RANGE;

    public Connector(String ip, int startPort, int endPort) {
        this.ip = ip;
        RANGE = IntStream.range(startPort, endPort).toArray();
    }

    private void portConnect(ExecutorService executorService, String ip, int port) {
        executorService.submit(() -> {

            Status status = new Status();
            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(ip, port), TIMEOUT);

                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String result = reader.readLine();

                reader.close();
                socket.close();

                status.setPort(port);
                status.setOpen(true);
                System.out.println("Connected:  " + port + "\t\t" + result);
                return status;
            } catch (Exception e) {
//                System.out.println("Error : " + port + " : " + e.getMessage());
                status.setOpen(false);
                status.setPort(port);
                return status;
            }
        });


    }

    public void portControl() {
        final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_SIZE);
        for (int port : RANGE) {
            portConnect(executorService, ip, port);
        }
        executorService.shutdown();
    }
}
