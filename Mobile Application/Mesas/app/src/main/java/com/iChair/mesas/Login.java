package com.iChair.mesas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {
    EditText usuario;
    EditText password;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<String> users;
    List<String> passw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_login);
        users = new ArrayList<>();
        passw = new ArrayList<>();
        usuario = findViewById(R.id.editText2);
        password = findViewById(R.id.editText);
        obtenerUsuarios();
    }

    /**
     * Metodo que guarda los usuarios y clave en un arrayList del todos los nodos en la tabla "nodos".
     */
    public void obtenerUsuarios(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Usuario").child("0926698440");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users.add(dataSnapshot.child("usuario").getValue().toString());
                passw.add(dataSnapshot.child("clave").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * Metodo del Botón ingresar para comparar las credenciales obtenidas en el meotod obtenerUsuarios()
     * y los ingresados por el usuario a traves de los cuadros edit text.
     * @param view
     */
    public void ingresar(View view) {
        String user = usuario.getText().toString();
        String pass = password.getText().toString();
        if(user.equals(users.get(0)) && pass.equals(passw.get(0))){
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
        }
     }
}
