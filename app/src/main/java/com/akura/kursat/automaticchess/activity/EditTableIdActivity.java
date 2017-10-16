package com.akura.kursat.automaticchess.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.akura.kursat.automaticchess.R;
import com.akura.kursat.automaticchess.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditTableIdActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase_tableId;
    private TextView oldTableId;
    private EditText newTableId;
    private Button editTableId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_table_id);
        oldTableId=(TextView)findViewById(R.id.twYourTableId);
        newTableId=(EditText)findViewById(R.id.etEditTableId);
        editTableId=(Button)findViewById(R.id.btnEditTableId);
        FirebaseUser fu= FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("users").child(fu.getUid());
        mDatabase_tableId=mDatabase.child("table_id");
        //mevcut table id yi ekranda yazdırıyor

        mDatabase_tableId.setValue(newTableId.getText().toString().trim());
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                oldTableId.setHint(user.getTable_id());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //butona tıklayınca table id si güncelleniyor


            editTableId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User user=dataSnapshot.getValue(User.class);
                            user.setTable_id(newTableId.getText().toString());
                            mDatabase_tableId.setValue(user.getTable_id());

                            Intent i=new Intent(getBaseContext(),HomePageActivity.class);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    Intent i=new Intent(EditTableIdActivity.this,HomePageActivity.class);
                    startActivity(i);
                }



            });






    }
}
