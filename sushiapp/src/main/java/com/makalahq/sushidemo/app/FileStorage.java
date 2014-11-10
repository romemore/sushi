package com.makalahq.sushidemo.app;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created 24/10/2014
 * for SushiDemo
 * by rme
 */
public class FileStorage {
    public static final String TAG = "FileStorage";

    File dir;

    public FileStorage(Context context) {
        dir = new File(context.getCacheDir(), "store");
        if(!dir.exists()) {
            dir.mkdirs();
            try {
                fillSampleDatas();
            } catch (IOException ex) {
                Log.e(TAG, "can't create sample datas", ex);
                dir.delete();
            }
        }
        Log.v(TAG, "datas : " + Arrays.toString(dir.listFiles()));
    }

    private void fillSampleDatas() throws IOException{
        Log.v(TAG, "Creating sample datas...");
        for(int i=0; i<10; i++) {
            String name = "sample"+i;
            File f = new File(dir, name+".txt");
            FileWriter fw = new FileWriter(f);
            fw.write("hello" + name);
            fw.close();
        }
        Log.v(TAG, "... created");
    }


}