package com.android.mnews.administrator;

public class Holder {
    private int apiAccess; //If value = 1 allow api call, if value = 0 don't allow api call
    private String key;    //Key value which will be passed by admin for mediastack api access
    private String message;//Tells why the application is down

    public Holder(int apiAccess, String key, String message) {
        this.apiAccess = apiAccess;
        this.key = key;
        this.message = message;
    }

    public int getApiAccess() {
        return apiAccess;
    }

    public void setApiAccess(int apiAccess) {
        this.apiAccess = apiAccess;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
