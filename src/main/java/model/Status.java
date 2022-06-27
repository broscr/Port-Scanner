package model;

public class Status {

    private int port;
    private boolean isOpen;

    public Status() {
    }

    public Status(int port, boolean isOpen) {
        this.port = port;
        this.isOpen = isOpen;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    @Override
    public String toString() {
        return "Status{" +
                "port=" + port +
                ", isOpen=" + isOpen +
                '}';
    }
}
