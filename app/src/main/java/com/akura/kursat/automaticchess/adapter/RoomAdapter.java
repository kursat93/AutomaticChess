package com.akura.kursat.automaticchess.adapter;

import android.app.Activity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;



import com.akura.kursat.automaticchess.model.Room;

import java.util.ArrayList;

/**
 * Created by kursat on 26.09.2017.
 */

public class RoomAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private Activity activity;
    private ArrayList<Room> rooms;

    public RoomAdapter(Activity activity, ArrayList<Room> rooms){

        this.activity=activity;
        this.rooms=rooms;

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

       // RelativeLayout item = (RelativeLayout)findViewById(R.id.item);

       // view = layoutInflater.inflate(R.layout.row_item, null);

        return null;
    }
}
