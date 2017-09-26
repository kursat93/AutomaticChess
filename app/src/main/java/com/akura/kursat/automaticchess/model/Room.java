package com.akura.kursat.automaticchess.model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by kursat on 26.09.2017.
 */

@IgnoreExtraProperties
public class Room {


    private String room_id;
    private String playerWhite;
    private String playerBlack;
    private Pieces piecesBlack;
    private Pieces piecesWhite;

    public Room(){

    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getPlayerWhite() {
        return playerWhite;
    }

    public void setPlayerWhite(String playerWhite) {
        this.playerWhite = playerWhite;
    }

    public String getPlayerBlack() {
        return playerBlack;
    }

    public void setPlayerBlack(String playerBlack) {
        this.playerBlack = playerBlack;
    }

    public Pieces getPiecesBlack() {
        Pieces pieces =new Pieces(playerBlack,room_id);
        /// kullanılacağı zman get piecees denilecek

        return pieces;
    }

    public void setPiecesBlack(Pieces piecesBlack) {
        this.piecesBlack = piecesBlack;
    }

    public Pieces getPiecesWhite() {
        Pieces pieces =new Pieces(playerWhite,room_id);
        /// kullanılacağı zman get piecees denilecek

        return pieces;
    }

    public void setPiecesWhite(Pieces piecesWhite) {
        this.piecesWhite = piecesWhite;
    }

    @Override
    public String toString() {
        return "Room{" +
                "room_id='" + room_id + '\'' +
                ", playerWhite='" + playerWhite + '\'' +
                ", playerBlack='" + playerBlack + '\'' +
                ", piecesBlack=" + piecesBlack +
                ", piecesWhite=" + piecesWhite +
                '}';
    }

    public void getRoom(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("rooms").child(room_id);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //User user = dataSnapshot.getValue(User.class);
                Room room = dataSnapshot.getValue(Room.class);
                System.out.println(room);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



}
