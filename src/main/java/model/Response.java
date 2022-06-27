package model;

public class Response {

    private int responseCode;
    private String responseMsg;
    private boolean isOpen;

    public Response() {
    }

    public Response(int responseCode, String responseMsg, boolean isOpen) {
        this.responseCode = responseCode;
        this.responseMsg = responseMsg;
        this.isOpen = isOpen;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    @Override
    public String toString() {
        return "Response{" +
                "responseCode=" + responseCode +
                ", responseMsg='" + responseMsg + '\'' +
                ", isOpen=" + isOpen +
                '}';
    }
}
