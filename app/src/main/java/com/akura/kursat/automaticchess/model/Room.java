package com.akura.kursat.automaticchess.model;

import android.app.Activity;
import android.app.ProgressDialog;

import com.akura.kursat.automaticchess.activity.GameListActivity;
import com.akura.kursat.automaticchess.adapter.RoomAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by kursat on 26.09.2017.
 */

@IgnoreExtraProperties
public class Room {

    Room nRoom;
    private String room_id;
    private String playerWhite;
    private String playerBlack;
    private Pieces piecesBlack;
    private Pieces piecesWhite;

    private String roomName; // yeni eklendi
    private String hostName;
    private int currentNumPlayer;

    public Room(){

    }

    public Room(String room_id){
        this.room_id =room_id;

       // getRoom(room_id);
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getCurrentNumPlayer() {

        if(playerWhite==null||playerBlack==null){ //equals =""  vardı
            setCurrentNumPlayer(1);
        }else {
            setCurrentNumPlayer(2);
        }

        return currentNumPlayer;
    }

    public void setCurrentNumPlayer(int currentNumPlayer) {
        this.currentNumPlayer = currentNumPlayer;
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
                ", roomName='" + roomName + '\'' +
                ", hostName='" + hostName + '\'' +
                ", currentNumPlayer=" + currentNumPlayer +
                '}';
    }

   /** public Room getRoom(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("rooms").child(room_id);


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //User user = dataSnapshot.getValue(User.class);
               // System.out.println(" ilk önce oda adı " +roomName);

                Room room = dataSnapshot.getValue(Room.class);
              /**  setHostName(room.getHostName());
                setRoomName(room.getRoomName());
                setRoom_id(room.getRoom_id());
                setPiecesBlack(room.getPiecesBlack());
                setPiecesWhite(room.getPiecesWhite());
                setPlayerBlack(room.getPlayerBlack());
                setPlayerWhite(room.getPlayerWhite());
              //  System.out.println(" sonra oda adı " +roomName);

                    nRoom=room;

                System.out.println("odalarımız  "+nRoom);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //System.out.println(nRoom);
        System.out.println("odalarımız ikifirebase d  "+nRoom);
            return nRoom;
    }*/



}
