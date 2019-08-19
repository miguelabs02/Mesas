package com.iChair.mesas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ImageView imageView;
    private List<Mesa> mesas = new ArrayList<>();
    private List<Long> id = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_sillas);
        imageView = (ImageView) findViewById(R.id.imageView);
        new FirebaseDatabaseHelper().readSillas(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataisLoaded(List<Silla> sillas, List<String> keys) {
                crearMesas(sillas);
                new RecyclerView_Config().setConfig(mRecyclerView,MainActivity.this,sillas,mesas);
                for(Silla silla:sillas){
                    if(silla.getEstado().equals("ocupado")){
                        for(Item item:RecyclerView_Config.items) {
                            if (silla.getMesa().equals(item.getId_item())){
                                Toast.makeText(MainActivity.this, String.valueOf(item.getId_item()), Toast.LENGTH_SHORT).show();
                                item.setImage_source(R.mipmap.ic_launcher);
                            }
                        }
                    }
                }
            }

            @Override
            public void DataisUpdated() {

            }
        });
    }

    public void crearMesas(List<Silla> sillas){
        List<Silla> sillasl = new ArrayList<>();
        for(Silla silla :sillas){
            if(!id.contains(silla.getMesa())){
                id.add(silla.getMesa());
                Mesa mesa = new Mesa(silla.getMesa());
                mesas.add(mesa);

            }
        }
        for(Long id_mesa:id){
            for(Silla silla: sillas){
                if(silla.getMesa().equals(id_mesa)){
                    sillasl.add(silla);
                }
            }
            mesas.get(id.indexOf(id_mesa)).setSilla(sillasl);
            sillasl.clear();
        }
    }
}
