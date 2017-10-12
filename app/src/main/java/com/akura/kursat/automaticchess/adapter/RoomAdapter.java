package com.akura.kursat.automaticchess.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.akura.kursat.automaticchess.R;
import com.akura.kursat.automaticchess.model.Room;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by kursat on 26.09.2017.
 */

public class RoomAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private Activity activity;
    private ArrayList<Room> rooms;

    public RoomAdapter(Activity activity, ArrayList<Room> rooms){

        layoutInflater = (LayoutInflater) activity.getSystemService( Context.LAYOUT_INFLATER_SERVICE);
        this.rooms=rooms;

    }

    @Override
    public int getCount() {
        return rooms.size();
    }

    @Override
    public Object getItem(int i) {
        return rooms.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       View rootView = layoutInflater.inflate(R.layout.row_item,null);

        final TextView roomName =  (TextView) rootView.findViewById(R.id.txt_room_name);
        final TextView gameStatus = (TextView) rootView.findViewById(R.id.txt_game_status);
        final TextView hostPlayer =  (TextView)rootView.findViewById(R.id.txt_game_host);

        final Room room = rooms.get(i);


        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        database.child("rooms").child(room.getRoom_id()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

               Room r = dataSnapshot.getValue(Room.class);

                if(r!=null){
                    roomName.setText(r.getRoomName());
                    hostPlayer.setText(r.getHostName());
                  //  gameStatus.setText(r.getCurrentNumPlayer());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

      //  Room room =rooms.get(i);

       // roomName.setText(room.getRoomName());
      //  hostPlayer.setText(room.getHostName());




          //  System.out.println("ben Adapter bildigin adapter  "+room.getRoomName());
      //  System.out.println(room.getRoom_id());


        return rootView;
    }





}
