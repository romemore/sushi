package com.makalahq.sushidemo.app.data;

import java.io.Serializable;

/**
 * Created 02/11/2014
 * for SushiDemo
 * by rme
 */
public class DataItem implements Serializable {

    private String title;

    public DataItem() {}

    public DataItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataItem)) return false;

        DataItem dataItem = (DataItem) o;

        if (title != null ? !title.equals(dataItem.title) : dataItem.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return title != null ? title.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "DataItem{" +
                "title='" + title + '\'' +
                '}';
    }

}