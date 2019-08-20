package com.iChair.mesas;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase fireBaseDatabase;
    private DatabaseReference databaseReference;
    private List<Silla> sillas = new ArrayList<>();

    public FirebaseDatabaseHelper(){
        fireBaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = fireBaseDatabase.getReference();
    }
    public interface DataStatus{
        void DataisLoaded(List<Silla> sillas,List<String> keys);
        void DataisUpdated();
    }
    public void readSillas(final DataStatus dataStatus){
        databaseReference.child("Silla").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                sillas.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode: dataSnapshot.getChildren()){
                   keys.add(keyNode.getKey());
                   Silla silla = keyNode.getValue(Silla.class);
                   sillas.add(silla);
               }
                dataStatus.DataisLoaded(sillas,keys);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
