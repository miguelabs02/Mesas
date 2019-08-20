package com.iChair.mesas;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ImageView imageView;
    private List<Mesa> mesas = new ArrayList<>();
    private List<Long> id = new ArrayList<>();
    //PROGRESS BAR DE LAS SILLAS DISPONIBLES
    private ProgressBar Progressbar_SD;
    private ProgressBar Progressbar_MS;
    //Numero de sillas disponibles
    private TextView num_sillas;
    //Numero de mesas disponibles
    private TextView num_mesas;
    //PROGRESS BAR DE LAS SILLAS EN USO
    private Handler mHandler= new Handler();
    //PROGRESS BAR DE LAS MESAS DISPONIBLES
    private int MSStatus;
    private int SDStatus;
    private int MSStatusfin;
    private int SDStatusfin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_sillas);
        imageView = (ImageView) findViewById(R.id.imageView);
        new FirebaseDatabaseHelper().readSillas(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataisLoaded(List<Silla> sillas, List<String> keys) {
                MSStatus=0;
                SDStatus=0;
                MSStatusfin=0;
                SDStatusfin=0;
                crearMesas(sillas);
                new RecyclerView_Config().setConfig(mRecyclerView,MainActivity.this,sillas,mesas);
                estadoMesas(mesas,RecyclerView_Config.items);
                /**
                 * Se actualiza la cantidad de sillas y mesas disponibles a medida que existen cambios
                 * en la base de datos.
                 * Se setea los valores maximos correspondientes de mesa y silla disponible.
                 */
                Progressbar_MS= findViewById(R.id.progress_mesas);
                //decimos cual es el maximo numero de mesas
                Progressbar_MS.setMax(RecyclerView_Config.items.size());
                Progressbar_SD=(ProgressBar) findViewById(R.id.progress_sillas);
                //decimos cual es el maximo de la barra de sillas
                Progressbar_SD.setMax(sillas.size());
                num_sillas=findViewById(R.id.num_sillas);
                num_mesas=findViewById(R.id.num_mesas);
                //numero de sillas disponibles
                //Setea el numero de sillas disponibles
                num_sillas.setText(String.valueOf(SDStatusfin));
                //Setea el numero de mesas disponibles
                num_mesas.setText(String.valueOf(MSStatusfin));

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(SDStatus<SDStatusfin){
                            SDStatus++;
                            android.os.SystemClock.sleep(50);
                            mHandler.post(new Runnable() {
                                @RequiresApi(api = Build.VERSION_CODES.N)
                                @Override
                                public void run() {
                                    Progressbar_SD.setProgress(SDStatus,true);
                                }
                            });
                        }
                    }
                }).start();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(MSStatus<MSStatusfin){
                            MSStatus++;
                            android.os.SystemClock.sleep(50);
                            mHandler.post(new Runnable() {
                                @RequiresApi(api = Build.VERSION_CODES.N)
                                @Override
                                public void run() {
                                    Progressbar_MS.setProgress(MSStatus,true);
                                }
                            });
                        }
                    }
                }).start();
            }
            @Override
            public void DataisUpdated() {
            }
        });
    }

    /**
     * Crea los objetos mesas de acuerdo al numéro diferentes de id que se obtienen de las sillas y
     * se setea una lista de sillas en el objeto mesa correspondiente al mismo.
     * @param sillas
     */
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

    /**
     * Analisis en tiempo real del estado de silla de las correspodientes mesas para establecer el
     * estado de una mesa y asignarle una respectiva imagen que represente visualmente si esta
     * ocupada.
     * @param mesas
     * @param items
     */
    public void estadoMesas(List<Mesa> mesas,List<Item> items){
        for (Mesa mesa: mesas) {
            int estado=0;
            for (Silla silla : items.get(mesas.indexOf(mesa)).getSilla()) {
                if (silla.getEstado().equals("ocupado")) {
                    estado++;
                } else{
                    SDStatusfin ++;
                }
            }
            if(estado==0){
                MSStatusfin ++;
                for (Item item : items) {
                    if (mesa.getMesa().equals(item.getId_item())) {
                        item.setImage_source(R.mipmap.mesa_desocupada);
                    }
                }
            }
            else if (estado==items.get(mesas.indexOf(mesa)).getSilla().size()){
                for (Item item : items) {
                    if (mesa.getMesa().equals(item.getId_item())) {
                        item.setImage_source(R.mipmap.mesa_ocupada);
                    }
                }
            }
            else {
                for (Item item : items) {
                    if (mesa.getMesa().equals(item.getId_item())) {
                        item.setImage_source(R.mipmap.mesa_semiocupada);
                    }
                }

            }
        }

    }
}

