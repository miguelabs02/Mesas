package com.iChair.mesas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class Main2Activity extends AppCompatActivity {
    private Button btn_scanner1;
    private Button btn_scanner2;
    private Button btn_scanner3;
    private Button btn_mover;
    private EditText editTextDesde;
    private EditText editTextPara;
    private EditText editTextSilla;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btn_scanner1=findViewById(R.id.btn_scanner1);
        btn_scanner2=findViewById(R.id.btn_scanner2);
        btn_scanner3=findViewById(R.id.btn_scanner3);
        btn_mover=findViewById(R.id.btn_mover);
        editTextDesde=findViewById(R.id.editTextDesde);
        editTextPara=findViewById(R.id.editTextPara);
        editTextSilla=findViewById(R.id.editTextSilla);

        btn_scanner3.setOnClickListener(mOnClickListener);





    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null)
            if (result.getContents() != null){
                editTextSilla.setText( result.getContents());
            }
    }



    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_scanner3:
                    new IntentIntegrator(Main2Activity.this).initiateScan();
                    break;
            }
        }
    };




}
