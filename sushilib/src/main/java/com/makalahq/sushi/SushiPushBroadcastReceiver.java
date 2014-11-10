package com.makalahq.sushi;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.makalahq.sushi.events.SushiUpdateEvent;
import com.parse.ParsePushBroadcastReceiver;
import de.greenrobot.event.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created 17/10/14
 * for SushiDemo
 * by rme
 */
public class SushiPushBroadcastReceiver extends ParsePushBroadcastReceiver {

    public static final String TAG = "SushiPushBroadcastReceiver";

    @Override
    protected void onPushReceive(Context context, Intent intent) {
//        Log.v(TAG, "onPushReceive");
        boolean isSushiEvent = false;
        if (intent != null) {
            String channel = intent.getExtras().getString("com.parse.Channel");
            if(channel != null && channel.equalsIgnoreCase("sushi")) {
                isSushiEvent = true;
                onSushiEvent( intent.getExtras().getString("com.parse.Data") );
            }
        }
        if(!isSushiEvent)
            super.onPushReceive(context, intent);
    }

    /**
     *
     * @param data Json string with the class for updated data
     */
    protected void onSushiEvent(String data) {
//        Log.v(TAG, "onSushiEvent " + data);
        try {
            JSONObject json = new JSONObject(data);
            String updatedObject = json.getString("class");
            Log.i(TAG, "updated " + updatedObject);
            EventBus.getDefault().post(new SushiUpdateEvent(updatedObject));
        } catch (JSONException e) {
            Log.d(TAG, "JSONException: " + e.getMessage());
        }
    }



}