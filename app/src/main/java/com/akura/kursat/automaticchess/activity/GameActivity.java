package com.akura.kursat.automaticchess.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.akura.kursat.automaticchess.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GameActivity extends AppCompatActivity {
    DatabaseReference ref;
    String roomID="";
    AlertDialog alert11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);




            Bundle extras = getIntent().getExtras();
            if(extras == null) {

            } else {
                roomID= extras.getString("room_id");
            }

        if(roomID!=""){
            ref = FirebaseDatabase.getInstance().getReference("rooms").child(roomID);
        }

    }

    @Override
    public void onBackPressed() {

        dialog();
    }

    @Override
    protected void onStop() {
        super.onStop();
        alert11.dismiss();
    }

    public void dialog(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(GameActivity.this);
        builder1.setMessage("Do you want to leave the game");
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent intent = new Intent(GameActivity.this, GameListActivity.class);
                        // String message = "abc";
                        // intent.putExtra(EXTRA_MESSAGE, message);
                        startActivity(intent);
                        if(roomID!=""){
                            ref.removeValue();
                        }

                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
         alert11 = builder1.create();
        alert11.show();

    }
}
