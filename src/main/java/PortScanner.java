import connector.Connector;

import java.net.*;
import java.util.Scanner;

public class PortScanner {
    private static Scanner scanner;

    public static void main(String[] args) {

        firstLoad();

    }

    private static void firstLoad() {
        scanner = new Scanner(System.in);
        System.out.println("Port Scanner");

        urlSetter();
    }

    private static void urlSetter() {
        System.out.println("Enter url: ");
        String url = scanner.next();

        if (!url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith("www."))
            url = "http://".concat(url);

        InetAddress address;

        try {
            address = InetAddress.getByName(new URL(url).getHost());

            setPort(address.getHostAddress());

        } catch (MalformedURLException | UnknownHostException e) {
            System.out.println("Invalid Url ");
            e.printStackTrace();
            urlSetter();
        }

    }

    private static void setPort(String url) {
        System.out.println("Enter port range start: ");
        int startPort = scanner.nextInt();

        System.out.println(" end ");
        int endPort = scanner.nextInt();

        Connector connector = new Connector(url, startPort, endPort);
        connector.portControl();
    }
}
