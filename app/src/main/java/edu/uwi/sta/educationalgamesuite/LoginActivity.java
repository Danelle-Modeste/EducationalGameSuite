package edu.uwi.sta.educationalgamesuite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class LoginActivity extends AppCompatActivity {
    EditText em,pword;
    String email,password,resp;
    Firebase firebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Firebase.setAndroidContext(this);
        firebaseRef = new Firebase("https://educationalgamesuite.firebaseio.com/");

        em =(EditText)findViewById(R.id.txt_email);
        pword=(EditText)findViewById(R.id.txt_pword);

        email=em.getText().toString();
        password=pword.getText().toString();
    }


    public void login(View view){
        email=em.getText().toString();
        password=pword.getText().toString();
        if (email.trim().equals("") || password.trim().equals("")) {
            resp="PLEASE ENTER A VALID EMAIL ADDRESS AND PASSWORD";
            Toast.makeText(getApplicationContext(),resp,Toast.LENGTH_LONG).show();
        }
        else {
            firebaseRef.authWithPassword(email, password, new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {
                    System.out.println("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
                    resp = "Successfully authenicated...Please wait";

                    Toast.makeText(LoginActivity.this, resp, Toast.LENGTH_LONG).show();
                    em.setText("");
                    pword.setText("");

                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                }

                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    resp = "Login Error Occured: " + firebaseError.toString().substring(14);
                    Toast.makeText(LoginActivity.this, resp, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void register(View view){
        Intent i= new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(i);
    }

    public void passwordReset(final View view){
        email = em.getText().toString();
        if (email.trim().equals("")){
            resp="PLEASE ENTER A VALID EMAIL ADDRESS";
            Toast.makeText(LoginActivity.this,resp,Toast.LENGTH_LONG).show();
        }
        else {
            firebaseRef.resetPassword(email, new Firebase.ResultHandler() {
                @Override
                public void onSuccess() {
                    resp="Password Successfully reset.\nCheck Email for further instructions";
                    Toast.makeText(LoginActivity.this, resp, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(FirebaseError firebaseError) {
                    resp="Password Reset Error: "+firebaseError.toString().substring(14);
                    Toast.makeText(LoginActivity.this, resp, Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
