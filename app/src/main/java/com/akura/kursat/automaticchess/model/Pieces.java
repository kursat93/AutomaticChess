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
public class Pieces {

    private String player_id;
    private String room_id;

    private String king;
    private String queen;
    private String bishop;
    private String knight;
    private String rook;
    private String pawnA;
    private String pawnB;
    private String pawnC;
    private String pawnD;
    private String pawnE;
    private String pawnF;
    private String pawnG;
    private String pawnH;

    public Pieces(){

    }
    public Pieces(String player_id,String room_id){
        this.player_id=player_id;
        this.room_id=room_id;

    }


    public String getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(String player_id) {
        this.player_id = player_id;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getKing() {
        return king;
    }

    public void setKing(String king) {
        this.king = king;
    }

    public String getQueen() {
        return queen;
    }

    public void setQueen(String queen) {
        this.queen = queen;
    }

    public String getBishop() {
        return bishop;
    }

    public void setBishop(String bishop) {
        this.bishop = bishop;
    }

    public String getKnight() {
        return knight;
    }

    public void setKnight(String knight) {
        this.knight = knight;
    }

    public String getRook() {
        return rook;
    }

    public void setRook(String rook) {
        this.rook = rook;
    }

    public String getPawnA() {
        return pawnA;
    }

    public void setPawnA(String pawnA) {
        this.pawnA = pawnA;
    }

    public String getPawnB() {
        return pawnB;
    }

    public void setPawnB(String pawnB) {
        this.pawnB = pawnB;
    }

    public String getPawnC() {
        return pawnC;
    }

    public void setPawnC(String pawnC) {
        this.pawnC = pawnC;
    }

    public String getPawnD() {
        return pawnD;
    }

    public void setPawnD(String pawnD) {
        this.pawnD = pawnD;
    }

    public String getPawnE() {
        return pawnE;
    }

    public void setPawnE(String pawnE) {
        this.pawnE = pawnE;
    }

    public String getPawnF() {
        return pawnF;
    }

    public void setPawnF(String pawnF) {
        this.pawnF = pawnF;
    }

    public String getPawnG() {
        return pawnG;
    }

    public void setPawnG(String pawnG) {
        this.pawnG = pawnG;
    }

    public String getPawnH() {
        return pawnH;
    }

    public void setPawnH(String pawnH) {
        this.pawnH = pawnH;
    }

    @Override
    public String toString() {
        return "Pieces{" +
                "player_id='" + player_id + '\'' +
                ", room_id='" + room_id + '\'' +
                ", king='" + king + '\'' +
                ", queen='" + queen + '\'' +
                ", bishop='" + bishop + '\'' +
                ", knight='" + knight + '\'' +
                ", rook='" + rook + '\'' +
                ", pawnA='" + pawnA + '\'' +
                ", pawnB='" + pawnB + '\'' +
                ", pawnC='" + pawnC + '\'' +
                ", pawnD='" + pawnD + '\'' +
                ", pawnE='" + pawnE + '\'' +
                ", pawnF='" + pawnF + '\'' +
                ", pawnG='" + pawnG + '\'' +
                ", pawnH='" + pawnH + '\'' +
                '}';
    }

  /**  public void getPieces(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("rooms").child(room_id).child(player_id);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //User user = dataSnapshot.getValue(User.class);
                Pieces pieces = dataSnapshot.getValue(Pieces.class);
                System.out.println(pieces);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }*/
}
