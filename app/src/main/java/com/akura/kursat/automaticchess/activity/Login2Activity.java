package com.akura.kursat.automaticchess.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.akura.kursat.automaticchess.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by lenovo on 28.9.2017.
 */

public class Login2Activity extends AppCompatActivity {

    private final String TAG = "Login2Activity";
    private AutoCompleteTextView EmailView;
    private EditText PasswordView;
    private ProgressDialog myProgressDialog;
    private FirebaseAuth Auth;
    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Auth = FirebaseAuth.getInstance();
        EmailView = (AutoCompleteTextView) findViewById(R.id.text_email);
        PasswordView = (EditText) findViewById(R.id.text_password);
        myProgressDialog = new ProgressDialog(this);
        myProgressDialog.setMessage(getString(R.string.loading_message));

        TextView but = (TextView) findViewById(R.id.btn_login2_register);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), RegisterActivity.class));
                finish();
            }
        });

        findViewById(R.id.btn_login2_signin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }

    private void attemptLogin(){

        String email = EmailView.getText().toString();
        String password = PasswordView.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        myProgressDialog.show();
        Auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(Login2Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        myProgressDialog.dismiss();

                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(Login2Activity.this, R.string.login_failed,
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                            if (firebaseUser != null) {

                                    DatabaseReference dbr = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid());

                                   // SharedPreferences.Editor editor = sharedpreferences.edit();
                                    //editor.putBoolean(getString(R.string.user_logged), true);
                                    //editor.apply();
                                    Intent intent = new Intent(Login2Activity.this, HomePageActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                            }
                        }
                    }
                });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getBaseContext(),LoginActivity.class);
        startActivity(intent);
        finish();
    }


}
