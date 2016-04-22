package edu.uwi.sta.educationalgamesuite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Firebase firebaseRef,userRef;
    AuthData authData ;
    Map<String,Object> user;
    public static int[] gameIcons={R.drawable.sudoku,R.drawable.another_game,R.drawable.another_game,R.drawable.another_game,R.drawable.another_game,R.drawable.another_game};
    public static String[] gameStrings = {"Sudoku","Trivia","Word Mole","Thinkers","Mathopia", "Brainiacs"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Firebase.setAndroidContext(this);
        firebaseRef = new Firebase("https://educationalgamesuite.firebaseio.com/");
        userRef=new Firebase("https://educationalgamesuite.firebaseio.com/users");//HAS ALL DATA FROM USERS TABLE
        authData=firebaseRef.getAuth();

        final TextView tv = (TextView)findViewById(R.id.txt_welc);

        Query queryRef = userRef.orderByChild("uid").equalTo(authData.getUid());
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChild) {
                user = (Map<String, Object>) dataSnapshot.getValue();

                if (user.get("uid").equals(authData.getUid())) {
                    String name = user.get("name").toString();
                    if (name!=null)
                        tv.setText(name);
                } else {
                    tv.setText("WELCOME");
                    user = null;
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
        //CURRENT USER LOGGED IN DATA IS NOW STORED IN user and can be manipulated
        //manipulate the map data with user data and to update firebase use
        //userRef.child(user.get("uid")).setValue(map);

        //set on click listener for image button to go to profile when clicked
        (findViewById(R.id.imgBtnProfile)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));
            }
        });

        //get the textview and assign the custom adapter
        //((ListView) findViewById(R.id.lvGameList)).setAdapter(new CustomAdapter(this,gameStrings,gameIcons));

        ((GridView) findViewById(R.id.lvGameList)).setAdapter(new CustomAdapter(this,gameStrings,gameIcons));
    }


}