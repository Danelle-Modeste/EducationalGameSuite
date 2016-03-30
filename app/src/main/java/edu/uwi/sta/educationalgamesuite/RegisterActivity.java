package edu.uwi.sta.educationalgamesuite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText em,pword,user,Age;
    String email,password,name,resp;
    int age;
    Firebase firebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Firebase.setAndroidContext(this);
        firebaseRef = new Firebase("https://educationalgamesuite.firebaseio.com/");

        em =(EditText)findViewById(R.id.txt_email);
        pword=(EditText)findViewById(R.id.txt_pass);
        user=(EditText)findViewById(R.id.txt_name);
        Age= (EditText)findViewById(R.id.txt_age);

    }

    public void signUp(final View view) {
        email = em.getText().toString();
        password = pword.getText().toString();
        name=user.getText().toString();
        if(!Age.getText().toString().trim().equals(""))
            age=Integer.parseInt(Age.getText().toString());

        if (email.trim().equals("") || password.trim().equals("")) {
            resp="PLEASE ENTER A VALID EMAIL ADDRESS AND PASSWORD";
            Toast.makeText(RegisterActivity.this, resp, Toast.LENGTH_LONG).show();
        }
        else {
            firebaseRef.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
                @Override
                public void onSuccess(Map<String, Object> result) {
                    System.out.println("Successfully created user account with uid: " + result.get("uid"));
                    resp = "Successfully created user account...Logging in";
                    Toast.makeText(RegisterActivity.this, resp, Toast.LENGTH_LONG).show();

                    Map<String, String> map = new HashMap<String, String>();
                    map.put("uid",result.get("uid").toString());
                    map.put("email", email);
                    map.put("name", name);
                    map.put("age", String.valueOf(age));
                    firebaseRef.child("users").child(result.get("uid").toString()).setValue(map);
                    login(email,password);
                }
                @Override
                public void onError(FirebaseError firebaseError) {
                    System.out.println(email + " " + password + " there was an error" + firebaseError);
                    resp = "Create User Account Error: " + firebaseError.toString().substring(14);
                    Toast.makeText(RegisterActivity.this, resp, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void login(String email,String password){
        if (email.trim().equals("") || password.trim().equals("")) {
            resp="PLEASE ENTER A VALID EMAIL ADDRESS AND PASSWORD";
            Toast.makeText(getApplicationContext(),resp,Toast.LENGTH_LONG).show();
        }
        else {
            firebaseRef.authWithPassword(email, password, new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {
                    System.out.println("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
                    em.setText("");
                    pword.setText("");
                    user.setText("");
                    Age.setText("");

                    Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(i);
                }

                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    resp = "Login Error Occured: " + firebaseError.toString().substring(14);
                    Toast.makeText(RegisterActivity.this, resp, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}
