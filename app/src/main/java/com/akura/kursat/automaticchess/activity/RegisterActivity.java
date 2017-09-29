package com.akura.kursat.automaticchess.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
 * Created by lenovo on 27.9.2017.
 */

public class RegisterActivity extends AppCompatActivity {

    private final String TAG = "RegisterActivity";
    private EditText inputEmail, inputName, inputPassword;
    private ProgressDialog myProgressDialog;
    private FirebaseAuth Auth;
    private SharedPreferences sharedpreferences;
    private Button registerbutton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Auth = FirebaseAuth.getInstance();
        sharedpreferences = getSharedPreferences(getString(R.string.key_pref_name), Context.MODE_PRIVATE);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        inputName = (EditText) findViewById(R.id.text_user_name);
        inputEmail = (EditText) findViewById(R.id.text_register_email);
        inputPassword = (EditText) findViewById(R.id.text_register_password);
        registerbutton = (Button) findViewById(R.id.btn_register_register);

        myProgressDialog = new ProgressDialog(this);
        myProgressDialog.setMessage(getString(R.string.loading_message));
        myProgressDialog.setCancelable(false);

        registerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Button clicked..!!");
                attemptRegister();
            }
        });
    }

    private void attemptRegister(){

        inputName.setError(null);
        inputPassword.setError(null);
        inputEmail.setError(null);

        final String displayName = inputName.getText().toString();
        final String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(displayName)) {
            inputName.setError(getString(R.string.error_field_required));
            focusView = inputName;
            cancel = true;
        } else if (TextUtils.isEmpty(email)) {
            inputEmail.setError(getString(R.string.error_field_required));
            focusView = inputEmail;
            cancel = true;
        }  else if (TextUtils.isEmpty(password)) {
            inputPassword.setError(getString(R.string.error_field_required));
            focusView = inputPassword;
            cancel = true;
        } else if (password.length() < 6) {
            inputPassword.setError(getString(R.string.error_invalid_password));
            focusView = inputPassword;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        }
        else {
            myProgressDialog.show();
            Auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                            Toast.makeText(RegisterActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                            if (!task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Authentication failed." + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                            } else {

                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                DatabaseReference dbr = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
                                dbr.child("name").setValue(displayName);
                                dbr.child("email").setValue(email);

                                startActivity(new Intent(RegisterActivity.this, HomePageActivity.class));
                                finish();
                            }

                        }
                    });
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myProgressDialog.dismiss();
    }
}
