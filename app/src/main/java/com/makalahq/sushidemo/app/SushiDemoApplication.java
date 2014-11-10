package com.makalahq.sushidemo.app;

import android.app.Application;
import android.util.Log;
import com.makalahq.sushi.Sushi;
import com.makalahq.sushi.events.SushiUpdateEvent;
import com.makalahq.sushidemo.app.data.DataStore;
import com.parse.*;
import com.snappydb.SnappydbException;
import de.greenrobot.event.EventBus;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Created 17/10/14
 * for SushiDemo
 * by rme
 */
public class SushiDemoApplication extends Application {
    public static final String TAG = "SushiDemoApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            DataStore.init(getApplicationContext());
        } catch (SnappydbException e) {
            Log.e(TAG, e.getMessage(), e);
        }

        // init Parse
        Properties p = new Properties();
        try {
            p.load(getResources().openRawResource(R.raw.credentials));
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        }

        Parse.initialize(this, p.getProperty("parse.appid"), p.getProperty("parse.clientkey"));

        // extras
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
        try {
            // save eventually will not work if your automatic user is not saved
            ParseUser.getCurrentUser().save();
        } catch (ParseException e) {
            Log.e(TAG, e.getMessage(), e);
        }

        //needed for push registration
        ParseInstallation.getCurrentInstallation().saveInBackground();

        // separate channel for Sushi
        ParsePush.subscribeInBackground("Sushi");

        // Dynamically register to class of data (can register many)
        Sushi.register("MyClassOfObject");

        // use EventBus to propagate changes in the app
        EventBus.getDefault().register(this);


        //init data
        ParseQuery<ParseObject> query = ParseQuery.getQuery("MyClassOfObject");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> resultsList, ParseException e) {
                if (e == null) {
                    DataStore.setFromParse(resultsList);
                } else {
                    Log.d(TAG, "Error: " + e.getMessage());
                }
            }
        });
    }

    @Override
    public void onTerminate() {
        EventBus.getDefault().unregister(this);
        super.onTerminate();
    }

    public void onEvent(SushiUpdateEvent event) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("MyClassOfObject");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> resultsList, ParseException e) {
                if (e == null) {
                    DataStore.setFromParse(resultsList);
                } else {
                    Log.d(TAG, "Error: " + e.getMessage());
                }
            }
        });
    }

}