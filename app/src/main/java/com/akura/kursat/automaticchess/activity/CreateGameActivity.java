package com.akura.kursat.automaticchess.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.akura.kursat.automaticchess.R;
import com.akura.kursat.automaticchess.chess.*;
import com.akura.kursat.automaticchess.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CreateGameActivity extends AppCompatActivity {

    Spinner color;
    EditText roomNameTxt;
    ArrayAdapter<String> adapter;

    String mRoomId="";

    private String choosenColor;
    private String roomName;
    private String host;
    private FirebaseUser user;
    int flag=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);
        user =FirebaseAuth.getInstance().getCurrentUser();
      ArrayList<String> colorList = new ArrayList<>();
        colorList.add("White");
        colorList.add("Wlack");

        roomNameTxt = (EditText)findViewById(R.id.name_room);

        Button create = (Button)findViewById(R.id.btn_create);


        adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,colorList);


      //   user = FirebaseAuth.getInstance().getCurrentUser();




/**
 *  spinner

        color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if(position==0){
                   choosenColor="White";
                }
                else{
                    choosenColor="Black";

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }


        });


 *  spinner son
 */



        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createRoom();




            }
        });
    }


    /**
     *  Oda oluştur
     */
    private void createRoom() {
        roomName = roomNameTxt.getText().toString().trim();

       DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(flag==1){
                    flag=0;
                    User u = dataSnapshot.getValue(User.class);
                    host=u.getName();

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("rooms");
                    mRoomId =reference.push().getKey();

                    // if(choosenColor.equals("White")){
                    reference.child(mRoomId).child("playerWhite").setValue(user.getUid());// user ID
                    reference.child(mRoomId).child("playerBlack").setValue("");// oponent boş ID

                    /** }else{
                     reference.child(mRoomId).child("playerBlack").setValue(user.getUid()); // User ID
                     reference.child(mRoomId).child("playerWhite").setValue("");//  oponenet boş ID

                     }*/
                    reference.child(mRoomId).child("room_id").setValue(mRoomId);
                    reference.child(mRoomId).child("roomName").setValue(roomName);
                 //   System.out.println("name name name "+ host);
                    reference.child(mRoomId).child("hostName").setValue(host); // user Name

                    Intent intent = new Intent(CreateGameActivity.this, com.akura.kursat.automaticchess.chess.GameActivity.class);


                    intent.putExtra("roomID",mRoomId);
                    intent.putExtra("color","White"); //choosenColor
                    intent.putExtra("creator",true);// creator flag
                    startActivity(intent);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        // taşlar için metod yaz kordinatları olsun

        // mGroupRef.child(mGroupId).setValue(new ChatGroup());

        /**
         *  private String room_id;
         private String playerWhite;
         private String playerBlack;
         private Pieces piecesBlack;
         private Pieces piecesWhite;

         private String roomName; // yeni eklendi
         private String hostName;
         */


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


        Intent intent = new Intent(getApplicationContext(),GameListActivity.class);
        startActivity(intent);
        finish();

    }


}
