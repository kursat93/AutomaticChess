package com.akura.kursat.automaticchess.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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

public class HomePageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button play;
    private TextView name;
    private ImageButton imageButtonEdit;
    private TextView getTableId;


    private AlertDialog dialog;
    private EditText edittext;

    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name=(TextView) findViewById(R.id.twEditName);
        imageButtonEdit=(ImageButton)findViewById(R.id.ibEdit);
        getTableId=(TextView)findViewById(R.id.twGetTableId);

        FirebaseUser fu= FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("users").child(fu.getUid());
        mDatabase_name=mDatabase.child("name");


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                name.setText(user.getName());
                if(user.getTable_id()==null){
                    getTableId.setHint("null");}

                 else{
                getTableId.setText(user.getTable_id());
            }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        dialog=new AlertDialog.Builder(this).create();
        edittext=new EditText(this);

        //dialog open
        imageButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                edittext.setText(name.getText());
                dialog.show();


            }
        });

        //dialogdan edit text yaptım
        dialog.setTitle("edit the text");
        dialog.setView(edittext);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "save text", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                name.setText(edittext.getText());

                //değiştirilen text database e kaydoldu
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user=dataSnapshot.getValue(User.class);
                        user.setName(name.getText().toString());
                        mDatabase_name.setValue(user.getName());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });






        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //navigationdan sonrası

        play=(Button)findViewById(R.id.btnPlay);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getBaseContext(),GameListActivity.class);
                startActivity(i);
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id==R.id.itemTableId){
            Intent i=new Intent(getBaseContext(),EditTableIdActivity.class);
            this.startActivity(i);
            return true;
        }


        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id==R.id.itemProfile){

        }
        else if (id == R.id.itemTableId) {
            Intent i=new Intent(HomePageActivity.this,EditTableIdActivity.class);
            startActivity(i);


        } else if (id == R.id.itemHelp) {

        } else if (id == R.id.itemExit) {

            //shared preference den logout yap

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
