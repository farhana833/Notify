package com.example.notify;

public class messages {
    String title, sendBy, sendTo, descp;
    static boolean expandable;

    messages() {

    }

    public messages(String title, String sendBy, String sendTo, String descp) {
        this.title = title;
        this.sendBy = sendBy;
        this.sendTo = sendTo;
        this.descp = descp;
        this.expandable  = false;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    public String getTitle() {
        return title;
    }

    public String getSendBy() {
        return sendBy;
    }

    public String getSendTo() {
        return sendTo;
    }

    public String getDescp() {
        return descp;
    }

}
