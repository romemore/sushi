package com.makalahq.sushidemo.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.makalahq.sushi.Sushi;
import com.makalahq.sushidemo.app.data.DataStore;
import com.parse.ParseObject;
import de.greenrobot.event.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends Activity {

    public static final String TAG = "MainActivity";

    DataListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new DataListAdapter(this, DataStore.get());
        ListView list = (ListView)findViewById(R.id.listView);
        list.setAdapter(adapter);

//        FileStorage fs = new FileStorage(getApplicationContext());
        EventBus.getDefault().register(adapter);

        findViewById(R.id.createButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseObject myFirstObject = new ParseObject("MyClassOfObject");
                myFirstObject.put("title", "My test object " + new SimpleDateFormat().format(new Date()));
                // replace Parse method by Sushi one
        //        myFirstObject.saveEventually();
                Sushi.saveEventually(myFirstObject);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(adapter);
        super.onDestroy();
    }

}
