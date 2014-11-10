package com.makalahq.sushidemo.app.data;

import android.content.Context;
import android.util.Log;
import com.parse.ParseObject;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;
import de.greenrobot.event.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created 02/11/2014
 * for SushiDemo
 * by rme
 */
public class DataStore {
    public static final String TAG = "DataStore";

    static private DB db;

    static public void init(Context context) throws SnappydbException {
        db = DBFactory.open(context, "sushis");
    }

    static public void close() {
        try {
            db.close();
        } catch (SnappydbException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    static public List<DataItem> get() {
        List<DataItem> results = new ArrayList<DataItem>();
        try {
            results.addAll(Arrays.asList(db.getArray("items", DataItem.class)) );
        } catch (SnappydbException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return results;
    }

    static public void set(List<DataItem> items) {
        try {
            db.put("items", items.toArray());
        } catch (SnappydbException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    //Parse helpers
    static public void setFromParse(List<ParseObject> objects) {
        List<DataItem> items = new ArrayList<DataItem>();
        for(ParseObject po:objects) {
            if(po.has("title")) items.add(new DataItem(po.getString("title")));
        }
        set(items);
        EventBus.getDefault().post(new DataRefreshedEvent());
    }

}