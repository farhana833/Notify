package com.example.notify;

public class messagesStaff {
    String title, sendBy, sendTo, descp;

    messagesStaff() {

    }

    public messagesStaff(String title, String sendBy, String sendTo, String descp) {
        this.title = title;
        this.sendBy = sendBy;
        this.sendTo = sendTo;
        this.descp = descp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSendBy() {
        return sendBy;
    }

    public void setSendBy(String sendBy) {
        this.sendBy = sendBy;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public String getDescp() {
        return descp;
    }

    public void setDescp(String descp) {
        this.descp = descp;
    }
}
