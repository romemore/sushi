package com.makalahq.sushidemo.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.makalahq.sushidemo.app.data.DataItem;
import com.makalahq.sushidemo.app.data.DataRefreshedEvent;
import com.makalahq.sushidemo.app.data.DataStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created 02/11/2014
 * for SushiDemo
 * by rme
 */
public class DataListAdapter extends BaseAdapter {
    public static final String TAG = "DataListAdapter";

    private Context context;
    private ArrayList<DataItem> items = new ArrayList<DataItem>();

    public DataListAdapter(Context context, List<DataItem> data) {
        super();
        this.context = context;
        this.items.addAll(data);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public DataItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TaskHolder holder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.data_item, parent, false);
            holder = new TaskHolder(row);
            row.setTag(holder);
        } else {
            holder = (TaskHolder)row.getTag();
        }
        holder.populateFrom(getItem(position));
        return (row);
    }

    public void addAll(List<DataItem> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    private static class TaskHolder {
        private TextView nameView = null;

        TaskHolder(View row) {
            nameView = (TextView) row.findViewById(R.id.item_title);
        }

        void populateFrom(DataItem r) {
            nameView.setText(r.getTitle());
        }
    }

    public void onEventMainThread(DataRefreshedEvent event) {
        items.clear();
        items.addAll(DataStore.get());
        notifyDataSetChanged();
    }

}