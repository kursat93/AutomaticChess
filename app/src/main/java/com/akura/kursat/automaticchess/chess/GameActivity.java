package com.akura.kursat.automaticchess.chess;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.akura.kursat.automaticchess.R;
import com.akura.kursat.automaticchess.model.Pieces;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class GameActivity extends AppCompatActivity {

    public static HashMap<String,String> pieceOldCordinate = new HashMap<>();
    public static int turn;
    public static boolean gameOver;
    public static boolean drawAvailable = false;
    public static String player = "";
    public static boolean[] blackCastle = new boolean[]{true,true};
    public static boolean[] whiteCastle = new boolean[]{true,true};
    public static String blackKing[] = new String[]{"e8","safe"};
    public static String whiteKing[] = new String[]{"e1","safe"};
    public static boolean stalemate = false;
    public static String passant = "";
    String currentLabel;
    ArrayList<String> validMoves = new ArrayList<String>();
    ArrayList<String> list = new ArrayList<String>();

    boolean firstSelection = true;
    TileView source = null;
    TileView target = null;

    String oldLocation="";
    String newLocation="";
    String pieceName="";
    String roomID="";
    FirebaseUser curUser;
    DatabaseReference ref;
    String userColor="";
    String curPiece="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hide();
        setContentView(R.layout.game_activity);

       Bundle extras =getIntent().getExtras();
        roomID= (String) extras.get("roomID");
        userColor= (String) extras.get("color");


        //burda eski kordinatları tutuyoruz
        /**
         *  TODO: buraya gelirken oda ID ve renk getir
         */
        loadMap();
        Board.initilaizeFirebase(roomID,userColor);

        curUser = FirebaseAuth.getInstance().getCurrentUser();
        ref= FirebaseDatabase.getInstance().getReference("rooms").child(roomID);
        // get from prev activity  ? or getfrom database
        turn = 1;
        gameOver = false;
        player="";
        Board.initializeBoard();


        String opponent;

        if(userColor.equals("White")){
            opponent = "Black";
        }else{
            opponent="Black";
        }



        ref.child(opponent).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String,String> list = (Map<String, String>) dataSnapshot.getValue();

                if(list!= null){

                    for(Map.Entry<String,String> pieceOfBoard:pieceOldCordinate.entrySet()){
                        for(Map.Entry<String,String> pieceOfFire:list.entrySet()){
                            if(pieceOfBoard.getKey().equals(pieceOfFire.getKey())){
                                if(!pieceOfBoard.getValue().equals(pieceOfFire.getValue())){
                                    updateFromFirebase(pieceOfFire.getKey(),pieceOfFire.getValue());
                                }
                            }

                        }

                    }
                    System.out.println("buralar  doluuu");
                }else{
                    System.out.println("burarlar null");
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

    protected void loadMap(){
        if(pieceOldCordinate.isEmpty()){

                pieceOldCordinate.put("wKing","e1");
                pieceOldCordinate.put("wQueen","d1");
                pieceOldCordinate.put("wBishop1","c1");
                pieceOldCordinate.put("wBishop2","f1");
                pieceOldCordinate.put("wKnight1","b1");
                pieceOldCordinate.put("wKnight2","g1");
                pieceOldCordinate.put("wRook1","a1");
                pieceOldCordinate.put("wRook2","h1");
                pieceOldCordinate.put("wPawnA","a2");
                pieceOldCordinate.put("wPawnB","b2");
                pieceOldCordinate.put("wPawnC","c2");
                pieceOldCordinate.put("wPawnD","d2");
                pieceOldCordinate.put("wPawnE","e2");
                pieceOldCordinate.put("wPawnF","f2");
                pieceOldCordinate.put("wPawnG","g2");
                pieceOldCordinate.put("wPawnH","h2");


                pieceOldCordinate.put("bKing","e8");
                pieceOldCordinate.put("bQueen","d8");
                pieceOldCordinate.put("bBishop1","c8");
                pieceOldCordinate.put("bBishop2","f8");
                pieceOldCordinate.put("bKnight1","b8");
                pieceOldCordinate.put("bKnight2","g8");
                pieceOldCordinate.put("bRook1","a8");
                pieceOldCordinate.put("bRook2","h8");
                pieceOldCordinate.put("bPawnA","a7");
                pieceOldCordinate.put("bPawnB","b7");
                pieceOldCordinate.put("bPawnC","c7");
                pieceOldCordinate.put("bPawnD","d7");
                pieceOldCordinate.put("bPawnE","e7");
                pieceOldCordinate.put("bPawnF","f7");
                pieceOldCordinate.put("bPawnG","g7");
                pieceOldCordinate.put("bPawnH","h7");


        }

    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }









    public void updateFromFirebase( String pieceName,String newCordinate){

       String oldCordinate="";
        Set keySet;
        keySet =pieceOldCordinate.keySet();
        for(Object piece:keySet){
            if(pieceName.equals(piece)){
                oldCordinate=pieceOldCordinate.get(piece);
            }
        }

        int oid = getResources().getIdentifier(oldCordinate,"id",this.getPackageName());
        int nid = getResources().getIdentifier(newCordinate,"id",this.getPackageName());

        source = (TileView) findViewById(oid);
         target = (TileView) findViewById(nid);

        String originPoints =  Mapping.convert(oldCordinate);

        int originFile = Character.getNumericValue(originPoints.charAt(0));
        int originRank = Character.getNumericValue(originPoints.charAt(1));

        System.out.println("ben neyim "+originFile);
        System.out.println("ben neyim "+originRank);

        pieceName = Board.tiles[originFile][originRank].currentPiece;
        source.currentPiece=pieceName;

        System.out.println("ilk tas nedir "+source.currentPiece);


        target.setImageDrawable(source.getDrawable());
        target.currentPiece = source.currentPiece;

         originPoints =  Mapping.convert(newCordinate);

        originFile = Character.getNumericValue(originPoints.charAt(0));
         originRank = Character.getNumericValue(originPoints.charAt(1));
        Board.tiles[originFile][originRank].currentPiece=target.currentPiece;


        System.out.println(source.currentPiece);

        source.setImageDrawable(null);
        source.currentPiece = "empty";


        /**
         *  TODO: karşıdan hamle geldiğinde şahmat olup olmadığını kontrol et ?  198. satır ?? şahmetla ilgili sanırım
         */

        //String s = getResources().getResourceName(id);
          //  System.out.println("budur hocam= "+s);
       // getResources().

        ++turn;

    }

    public void handleInput(View touchedTile){
        if(gameOver)
            return;
        if(turn % 2 == 0)// oyuncuyu gelen oyuncu yap turu geldiğinde oynasın
            player = "Black";
        else
            player = "White";

        if(firstSelection){
            source = (TileView) touchedTile;
            currentLabel = getResources().getResourceName(source.getId()); // ID si kare kordinatı
            if(source.getDrawable() == null) // karede taş yok
                return;
          //  System.out.println("cur labp   "+currentLabel);
            String originPoints =  Mapping.convert(currentLabel.substring(currentLabel.length()-2)); // örnek a5 den a yı gönderiyor
          //  System.out.println("after map   "+originPoints);
          //  System.out.println("current label   "+currentLabel);

            for(Map.Entry<String,String> piece:pieceOldCordinate.entrySet()){

                       if(piece.getValue().equals(currentLabel.substring(currentLabel.length()-2))){
                           System.out.println(piece.getKey());
                           curPiece=piece.getKey();
                       }
                }


            int originFile = Character.getNumericValue(originPoints.charAt(0));
            int originRank = Character.getNumericValue(originPoints.charAt(1));
           // System.out.println("origin file  "+originFile);
           // System.out.println("orşgşn rank  "+originRank);

            oldLocation = currentLabel.substring(currentLabel.length()-2);   // sonradan eklendi for firebase
            pieceName = Board.tiles[originFile][originRank].currentPiece;

          //  System.out.println(" bu nedir bu "+Board.tiles[originFile][originRank].currentPiece.charAt(0));
           // System.out.println(" bu nedir bu "+Board.tiles[originFile][originRank].currentPiece);
           // System.out.println(" bben kimim "+Character.toLowerCase(player.charAt(0)));

            if(Board.tiles[originFile][originRank].currentPiece.charAt(0) != Character.toLowerCase(player.charAt(0))) {
                Toast.makeText(GameActivity.this, "That is not your piece!",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            validMoves = Rules.findPossibleMoves( currentLabel.substring(currentLabel.length()-2) );
            if(validMoves.isEmpty()) {
                Toast.makeText(GameActivity.this, "Nothing is possible with that piece\nTry again...",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            //at this point it is safe to act on this piece
            firstSelection = false;
        }
        else{
            target = (TileView) touchedTile;
            String startingLabel = currentLabel.substring(currentLabel.length()-2);
            currentLabel = getResources().getResourceName(target.getId());

            if(target != source){
                boolean foundMatch = false;
                for(String z: validMoves){
                    if (z.equals( currentLabel.substring(currentLabel.length()-2) ) ){
                        boolean leftKingInCheck;
                        newLocation=currentLabel.substring(currentLabel.length()-2);
                        leftKingInCheck = !executeMove(startingLabel, currentLabel.substring(currentLabel.length()-2), "", true,0);
                        if(leftKingInCheck){
                            Toast.makeText(GameActivity.this, "Don't leave your king in check!\nTry again...",
                                    Toast.LENGTH_SHORT).show();
                            firstSelection = true;
                            return;
                        }
                        foundMatch = true;
                        break;
                    }
                }
                if(!foundMatch){
                    Toast.makeText(GameActivity.this, "Illegal move.\nTry again...",
                            Toast.LENGTH_SHORT).show();
                    firstSelection = true;
                    return;
                }



                /**
                 *  hamle tamamlanınca  eski kareyi boşalt yeni kareye  görseli yerleştir ve taşın ynei konumu
                 *  TODO: Burda Firebase'i güncelle
                 */
            
                ref.child(userColor).child(curPiece).setValue(newLocation);

                    System.out.println(" pice = " + pieceName+ "  source loacation=  "+ oldLocation+"  new Location = "+newLocation);

                target.setImageDrawable(source.getDrawable());
                target.currentPiece = source.currentPiece;
                source.setImageDrawable(null);
                source.currentPiece = "empty";

                //add to list
                list.add(startingLabel + " " + currentLabel.substring(currentLabel.length()-2));

                /*TODO: Need to incorporate some UI elements that indicate check and checkmate!*/
                boolean putEnemyInCheck;
                if(player.equals("White")){
                    putEnemyInCheck = Rules.leavesKingInCheck(blackKing[0], "b");
                    if(putEnemyInCheck){
                        blackKing[1] = "check";
                        gameOver = Rules.determineCheckmate(turn+1);
                        if(gameOver){
                            Toast.makeText(GameActivity.this, "Checkmate! White wins!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(GameActivity.this, "Check!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                        blackKing[1] = "safe";
                }
                else{
                    putEnemyInCheck = Rules.leavesKingInCheck(whiteKing[0], "w");
                    if(putEnemyInCheck){
                        whiteKing[1] = "check";
                        gameOver = Rules.determineCheckmate(turn+1);
                        if(gameOver){
                            Toast.makeText(GameActivity.this, "Checkmate! Black wins!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(GameActivity.this, "Check!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                        whiteKing[1] = "safe";
                }

                //support for our castling implementation?

                /*TODO: Might be possible to implement undo button here*/
                ++turn;
                firstSelection = true;

                if(gameOver){

                    String fullList = "Here are the recorded moves: \n";
                    for(String x : list){
                        fullList += x;
                        fullList += ",\t";
                    }
                    Toast.makeText(GameActivity.this, fullList,
                            Toast.LENGTH_LONG).show();

                    /*TODO: Show button the lets user save game. Give it an onclicklistener and have
                    that function start a dialog that asks for a name. Then do serialization and write
                    the file to system memory
                    My tip is to just implement the serialization first and then add buttons and
                    dialogs later*/
                    //Serialize
                    try {
                        FileOutputStream fos = new FileOutputStream("List of moves");
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeObject(list);
                        File f = new File(".");
                        Toast.makeText(GameActivity.this, "Moves have been saved.\nFile path = " + f.getAbsolutePath(),
                                Toast.LENGTH_LONG).show();
                        oos.close();
                        fos.close();

                    } catch (Exception ioe) {
                        ioe.printStackTrace();
                        Toast.makeText(GameActivity.this, "failed to write game data",
                                Toast.LENGTH_LONG).show();
                    }

                }
                return;
            }
            // Here the player must have touched the starting piece again
            Toast.makeText(GameActivity.this, "Move canceled...",
                    Toast.LENGTH_SHORT).show();
            firstSelection = true;
        }
    }

    /**
     * This method executes the move.
     * @param origin, the original location of the piece.
     * @param destination, where the piece wants to move.
     * @param promo, if pawn promotion is an option, then the type of piece
     * @param keepIt, x
     * that the player wishes in exchange for a pawn
     */
    public static boolean executeMove(String origin, String destination, String promo, boolean keepIt, int castling){
        origin = Mapping.convert(origin);
        int originFile = Character.getNumericValue(origin.charAt(0));
        int originRank = Character.getNumericValue(origin.charAt(1));
        String originPiece = Board.tiles[originFile][originRank].currentPiece;


        destination = Mapping.convert(destination);
        int destinationFile = Character.getNumericValue(destination.charAt(0));
        int destinationRank = Character.getNumericValue(destination.charAt(1));
        String destinationPiece = Board.tiles[destinationFile][destinationRank].currentPiece;

        Board.tiles[destinationFile][destinationRank].currentPiece = originPiece;
        Board.tiles[originFile][originRank].currentPiece = "empty";

        String previousWhiteKing = whiteKing[0];
        String previousBlackKing = blackKing[0];

        if(originPiece.equals("wK"))
            whiteKing[0] = Board.tiles[destinationFile][destinationRank].label;
        if(originPiece.equals("bK"))
            blackKing[0] = Board.tiles[destinationFile][destinationRank].label;

        int rookOriginFile, rookOriginRank, rookDestFile, rookDestRank;
        switch(castling){
            case 4:
                rookOriginFile = 1;
                rookOriginRank = 1;
                rookDestFile = 1;
                rookDestRank = 3;
                break;
            case 3:
                rookOriginFile = 1;
                rookOriginRank = 8;
                rookDestFile = 1;
                rookDestRank = 5;
                break;
            case 2:
                rookOriginFile = 8;
                rookOriginRank = 1;
                rookDestFile = 8;
                rookDestRank = 3;
                break;
            case 1:
                rookOriginFile = 8;
                rookOriginRank = 8;
                rookDestFile = 8;
                rookDestRank = 5;
                break;
            default:
                rookOriginFile = 0;
                rookOriginRank = 0;
                rookDestFile = 0;
                rookDestRank = 0;
        }

        if(rookOriginFile+rookOriginRank+rookDestFile+rookDestRank > 0){
            Board.tiles[rookDestFile][rookDestRank].currentPiece = Board.tiles[rookOriginFile][rookOriginRank].currentPiece;
            Board.tiles[rookOriginFile][rookOriginRank].currentPiece = "empty";
        }

        String thisKing = "";
        if(originPiece.substring(0,1).equalsIgnoreCase("b"))
            thisKing = blackKing[0];
        else
            thisKing = whiteKing[0];

        if(Rules.leavesKingInCheck(thisKing, originPiece.substring(0,1) ) ){
            Board.tiles[destinationFile][destinationRank].currentPiece = destinationPiece;
            Board.tiles[originFile][originRank].currentPiece = originPiece;
            if(rookOriginFile+rookOriginRank+rookDestFile+rookDestRank > 0){
                Board.tiles[rookOriginFile][rookOriginRank].currentPiece = Board.tiles[rookDestFile][rookDestRank].currentPiece;
                Board.tiles[rookDestFile][rookDestRank].currentPiece = "empty";
            }
            whiteKing[0] = previousWhiteKing;
            blackKing[0] = previousBlackKing;
            return false;
        }
        if(!keepIt){
            Board.tiles[destinationFile][destinationRank].currentPiece = destinationPiece;
            Board.tiles[originFile][originRank].currentPiece = originPiece;
            if(rookOriginFile+rookOriginRank+rookDestFile+rookDestRank > 0){
                Board.tiles[rookOriginFile][rookOriginRank].currentPiece = Board.tiles[rookDestFile][rookDestRank].currentPiece;
                Board.tiles[rookDestFile][rookDestRank].currentPiece = "empty";
            }
            whiteKing[0] = previousWhiteKing;
            blackKing[0] = previousBlackKing;
            return true;
        }

        switch(castling){
            case 4:
                whiteCastle[0] = false;
                whiteCastle[1] = false;
                break;
            case 3:
                whiteCastle[1] = false;
                whiteCastle[0] = false;
                break;
            case 2:
                blackCastle[1] = false;
                blackCastle[0] = false;
                break;
            case 1:
                blackCastle[1] = false;
                blackCastle[0] = false;
                break;
        }

        if(originPiece.equals("bp") && (destinationFile == 1 || destinationFile == '1')){
            //pawn promotion
            if(promo != ""){
                Board.tiles[destinationFile][destinationRank].currentPiece = "b" + promo;
            }
            else
                Board.tiles[destinationFile][destinationRank].currentPiece = "bQ";
        }
        if(originPiece.equals("wp") && (destinationFile == 8 || destinationFile == '8')){
            //pawn promotion
            if(promo != ""){
                Board.tiles[destinationFile][destinationRank].currentPiece = "w" + promo;
            }
            else
                Board.tiles[destinationFile][destinationRank].currentPiece = "wQ";
        }

        if(originPiece.charAt(1) == 'p' && Math.abs(destinationFile - originFile) == 2)
            passant = destination;
        else
            passant = "";

        return true;
    }
}