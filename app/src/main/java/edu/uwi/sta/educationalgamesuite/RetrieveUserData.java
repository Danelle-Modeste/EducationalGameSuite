package edu.uwi.sta.educationalgamesuite;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class RetrieveUserData implements ChildEventListener {
    AuthData authData ;
    Map<String,Object> user;

    public RetrieveUserData(AuthData authData ){
        this.authData=authData;
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String previousChild) {
        user = (Map<String, Object>) dataSnapshot.getValue();
    }
    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {}
    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
    @Override
    public void onCancelled(FirebaseError firebaseError) {}

    public Map<String,Object> returnData(){
        return user;
    }
}
