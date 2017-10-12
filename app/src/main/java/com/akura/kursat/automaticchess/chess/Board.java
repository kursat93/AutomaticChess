package com.akura.kursat.automaticchess.chess;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Documentation auto generated
 */

public class Board {
    /*A standard chess board is 8x8, but we need an extra column and row for the labels.*/
    public static Tile[][] tiles = new Tile[9][9];

    /**
     * This method initializes the board.
     * 8x8 board with the 32 pieces of chess all aligned.
     */
    public static void initializeBoard() {
        for (int i = 8; i > -1; --i) {
            for (int j = 8; j > -1; --j) {
                tiles[i][j] = new Tile();

                //an ugly method for determining the tile file (column)
                //TODO: make this elegant
                char file = 96;
                switch (j) {
                    case 8:
                        file += 1;
                        break;
                    case 7:
                        file += 2;
                        break;
                    case 6:
                        file += 3;
                        break;
                    case 5:
                        file += 4;
                        break;
                    case 4:
                        file += 5;
                        break;
                    case 3:
                        file += 6;
                        break;
                    case 2:
                        file += 7;
                        break;
                    case 1:
                        file += 8;
                        break;
                    case 0:
                        file = '|';
                        break;
                }
                tiles[i][j].label = Character.toString(file) + Integer.toString(i);

				/* White tiles have the property of both i and j having the same parity (odd or even)
				 *  so if i + j is divisible by 2, then this is a white tile. Else it is a black tile (which has been set up by default).*/
                if ((i + j) % 2 == 0)
                    tiles[i][j].defaultColor = "     ";
            }
        }

        tiles[1][1].currentPiece = "wR";
        tiles[1][8].currentPiece = "wR";
        tiles[8][1].currentPiece = "bR";
        tiles[8][8].currentPiece = "bR";

        tiles[1][2].currentPiece = "wN";
        tiles[1][7].currentPiece = "wN";
        tiles[8][2].currentPiece = "bN";
        tiles[8][7].currentPiece = "bN";

        tiles[1][3].currentPiece = "wB";
        tiles[1][6].currentPiece = "wB";
        tiles[8][3].currentPiece = "bB";
        tiles[8][6].currentPiece = "bB";

        tiles[1][5].currentPiece = "wQ";
        tiles[1][4].currentPiece = "wK";
        tiles[8][5].currentPiece = "bQ";
        tiles[8][4].currentPiece = "bK";

        for (int x = 1; x < 9; x++) {
            tiles[2][x].currentPiece = "wp";
        }
        for (int x = 1; x < 9; x++) {
            tiles[7][x].currentPiece = "bp";
        }
    }

    public static void initilaizeFirebase(String roomID,String playerColor){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("rooms").child(roomID).child(playerColor);

        Map pieceslist = new HashMap<>();
        if(pieceslist.isEmpty()){

            if(playerColor.equals("White")){
                pieceslist.put("wKing","e1");
                pieceslist.put("wQueen","d1");
                pieceslist.put("wBishop1","c1");
                pieceslist.put("wBishop2","f1");
                pieceslist.put("wKnight1","b1");
                pieceslist.put("wKnight2","g1");
                pieceslist.put("wRook1","a1");
                pieceslist.put("wRook2","h1");
                pieceslist.put("wPawnA","a2");
                pieceslist.put("wPawnB","b2");
                pieceslist.put("wPawnC","c2");
                pieceslist.put("wPawnD","d2");
                pieceslist.put("wPawnE","e2");
                pieceslist.put("wPawnF","f2");
                pieceslist.put("wPawnG","g2");
                pieceslist.put("wPawnH","h2");

            }else {

                pieceslist.put("bKing","e8");
                pieceslist.put("bQueen","d8");
                pieceslist.put("bBishop1","c8");
                pieceslist.put("bBishop2","f8");
                pieceslist.put("bKnight1","b8");
                pieceslist.put("bKnight2","g8");
                pieceslist.put("bRook1","a8");
                pieceslist.put("bRook2","h8");
                pieceslist.put("bPawnA","a7");
                pieceslist.put("bPawnB","b7");
                pieceslist.put("bPawnC","c7");
                pieceslist.put("bPawnD","d7");
                pieceslist.put("bPawnE","e7");
                pieceslist.put("bPawnF","f7");
                pieceslist.put("bPawnG","g7");
                pieceslist.put("bPawnH","h7");
            }



            ref.updateChildren(pieceslist);

        }
    }
}
