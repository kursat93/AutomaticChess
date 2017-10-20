package com.akura.kursat.automaticchess.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.akura.kursat.automaticchess.R;
import com.akura.kursat.automaticchess.adapter.RoomAdapter;
import com.akura.kursat.automaticchess.chess.*;
import com.akura.kursat.automaticchess.model.Room;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class GameListActivity extends AppCompatActivity {

    ListView listView;
    RoomAdapter roomAdapter;
    ArrayList<Room> rooms;
    ArrayList<String> roomIdList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);
        rooms =new ArrayList<>();
        listView =(ListView)findViewById(R.id.list_room);
        Button create = (Button)findViewById(R.id.btn_create_room);

        /**
         * oda oluşturmaya gir
         */
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CreateGameActivity.class);
                startActivity(intent);
                finish();
            }
        });
/**
 * ***************** **************** ************** **    *************
 */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

               final Room room = (Room) listView.getItemAtPosition(i);
               // System.out.println(room);


                AlertDialog.Builder builder1 = new AlertDialog.Builder(GameListActivity.this);
                builder1.setMessage("Do you want to join the game");
                builder1.setCancelable(false);




                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Intent intent = new Intent(GameListActivity.this, com.akura.kursat.automaticchess.chess.GameActivity.class);


                                String choosenColor = "Black";
                                intent.putExtra("roomID",room.getRoom_id());
                                intent.putExtra("color",choosenColor);
                                intent.putExtra("creator",false); // creator flag
                                startActivity(intent);
                                finish();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();


            }
        });
        /**
         * ********** ********************* **************** ***************
         */


        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        database.child("rooms").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                rooms.clear();
                roomIdList=new ArrayList<>();
                for (DataSnapshot snap : dataSnapshot.getChildren()) {
                    String room = snap.getKey();
                    roomIdList.add(room);
                    Room ssRoom = new Room(room);
                    rooms.add(ssRoom);

                  //  System.out.println("room ıd budur artık yeter"+room);
                    //updateRoomList(room);
                }

                adapterset();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

/**
        for(String rID:roomIdList){



        }

*/






    }

    private void adapterset() {
        roomAdapter = new RoomAdapter(GameListActivity.this,rooms);
        listView.setAdapter(roomAdapter);
    }







    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),HomePageActivity.class);
        startActivity(intent);
        finish();

    }
}
