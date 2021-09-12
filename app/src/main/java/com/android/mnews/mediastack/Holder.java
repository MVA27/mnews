package com.android.mnews.mediastack;

import java.util.List;

public class Holder {
    private Pagination pagination;
    private List<Data> data;

    @Override
    public String toString() {
        return "Holder{" + "pagination=" + pagination + ", data=" + data + '}';
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}