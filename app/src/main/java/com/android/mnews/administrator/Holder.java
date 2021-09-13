package com.android.mnews.administrator;

public class Holder {
    private String type;
    private int flag;

    public Holder(String type, int flag) {
        this.type = type;
        this.flag = flag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
