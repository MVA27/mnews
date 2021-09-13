package com.android.mnews.administrator;

public class Holder {
    private int apiAccess; //If value = 1 allow api call, if value = 0 don't allow api call
    private String key;    //Key value which will be passed by admin for mediastack api access

    public Holder(int apiAccess, String key) {
        this.apiAccess = apiAccess;
        this.key = key;
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
}
