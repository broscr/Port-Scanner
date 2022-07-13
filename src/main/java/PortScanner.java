import connector.Connector;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

public class PortScanner {
    public static void main(String[] args) {

        for (String arg : args) {
            System.out.println(arg);
        }

        if (args.length < 2) {
            System.out.println("If you need help ? use -h");
            exitRun();
        }

        if (args[0].equals("-h")) {
            System.out.println("Example: \n" +
                    "???Input Ip or Domain Name??? " +
                    "- Only one port ---> PortScanner example.com 80 \n" +
                    "- Range port  ---> PortScanner 192.168.1.1 1-6999");
            exitRun();
        }

        urlSetter(args);

    }

    private static void urlSetter(String[] args) {

        String url = args[0];

        if (!url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith("www."))
            url = "http://".concat(url);

        InetAddress address;

        try {
            address = InetAddress.getByName(new URL(url).getHost());

            String ports = args[1];

            if (ports.contains("-")) {
                String[] startPorts = ports.split("-");

                if (startPorts.length < 2) {

                    int port = checkPortIsValid(startPorts[0]);

                    System.out.println("Port detected only one : " + port);

                    setPort(address.getHostAddress(), -1, port);
                    //go port
                } else {

                    int portStart = checkPortIsValid(startPorts[0]);
                    int portEnd = checkPortIsValid(startPorts[1]);

                    setPort(address.getHostAddress(), portStart, portEnd);
                }
            } else {
                int port = checkPortIsValid(ports);

                setPort(address.getHostAddress(), -1, port);
            }


        } catch (MalformedURLException | UnknownHostException e) {
            System.out.println("Invalid Ip or Domain Name ");
            exitRun();
        }
    }

    private static void setPort(String url, int startPort, int endPort) {
        if (startPort == -1)
            new Connector(url, endPort);
        else
            new Connector(url, startPort, endPort);
    }

    private static int checkPortIsValid(String portString) {

        try {
            return Integer.parseInt(portString);
        } catch (NumberFormatException e) {
            System.out.println("Invalid Port");
            exitRun();
        }

        return -1;
    }

    private static void exitRun() {
        System.exit(0);
    }
}
