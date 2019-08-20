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

    /**
     * Constructor para el gestor de base de datos de Firebase.
     * Nos permite instanciar las variables creadas para gestionar las claves y valores de los nodos
     * en la base de datos de Firebase.
     */
    public FirebaseDatabaseHelper(){
        fireBaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = fireBaseDatabase.getReference();
    }

    /**
     * Interfaz para la sincronización entre el main Thread y la actividad asincronica del Listener
     * que escucha los cambios en la base de datos.
     */
    public interface DataStatus{
        void DataisLoaded(List<Silla> sillas,List<String> keys);
        void DataisUpdated();
    }

    /**
     * Metodo que lee las sillas del nodo respectivo "Silla" del RealtimeDatabase de Firebase que
     * recorre las claves y valores de cada objeto, guardandola en una lista.
     * @param dataStatus Interfaz a implementar para sincronización en tiempo real.
     */
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
