package com.iChair.mesas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Login extends AppCompatActivity {
    EditText usuario;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_login);
        usuario = findViewById(R.id.editText2);
        password = findViewById(R.id.editText);
    }

    public void ingresar(View view) {
        String user = usuario.getText().toString();
        String pass = password.getText().toString();
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}
