package connector;

import model.Status;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Connector {

    private final int TIMEOUT = 1000;
    private final String ip;
    private int[] RANGE;
    private int onePort;

    public Connector(String ip, int onePort) {
        this.ip = ip;
        this.onePort = onePort;

        onePortControl();
    }

    public Connector(String ip, int startPort, int endPort) {
        int MAX_RANGE = 65000;

        this.ip = ip;
        RANGE = IntStream.range(startPort, Math.min(endPort, MAX_RANGE)).toArray();

        portControl();
    }

    private Future<Status> portConnect(ExecutorService executorService, String ip, int port) {
        return executorService.submit(() -> {

            Status status = new Status();

            try {

                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(ip, port), TIMEOUT);
//                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                String result = reader.readLine();

//                reader.close();
                socket.close();

                status.setPort(port);
                status.setOpen(true);
                System.out.println("Connected:  " + port /*+ "\t\t" + result*/);
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
        long startMillis = System.currentTimeMillis();
        int THREAD_SIZE = 100;

        List<Future<Status>> futureList = new ArrayList<>();

        final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_SIZE);

        for (int port : RANGE) {
            futureList.add(portConnect(executorService, ip, port));
        }
        executorService.shutdown();

        List<Status> statusList = futureList.stream().map(i -> {
            try {
                return new Status(i.get().getPort(), i.get().isOpen());
            } catch (InterruptedException | ExecutionException e) {
                return null;
            }
        }).collect(Collectors.toList());

        statusList.forEach(i -> {
            if (i.isOpen()) System.out.println(i);
        });

        showTotalTime(System.currentTimeMillis() - startMillis);
    }

    private void onePortControl() {
        long startMillis = System.currentTimeMillis();

        final ExecutorService executorService = Executors.newFixedThreadPool(1);

        Future<Status> response = portConnect(executorService, ip, onePort);

        executorService.shutdown();

        try {
            System.out.println(response.get().getPort() + " : " + response.get().isOpen());
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e.getMessage());
            exitRun();
        }


        showTotalTime(System.currentTimeMillis() - startMillis);
    }

    private void showTotalTime(long milliseconds) {
        // formula for conversion for
        // milliseconds to minutes.
        long minutes = (milliseconds / 1000) / 60;

        // formula for conversion for
        // milliseconds to seconds
        long seconds = (milliseconds / 1000) % 60;

        // Print the output
        System.out.println(milliseconds + " Milliseconds = "
                + minutes + " minutes and "
                + seconds + " seconds.");
    }

    private void exitRun() {
        System.exit(0);
    }
}
