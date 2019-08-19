package com.iChair.mesas;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class RecyclerView_Config {
    private Context mContext;
    private SillaAdapter sillaAdapter;
    private ListAdapter listAdapter;
    private Item item;
    public static List<Item> items = new ArrayList<>();

    public void setConfig(RecyclerView recyclerView, Context context, List<Silla> sillas, List<Mesa> mesas){
        mContext = context;
        createItems(mesas,sillas);
        sillaAdapter = new SillaAdapter(items);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(sillaAdapter);
    }

    public void createItems(List<Mesa> mesas,List<Silla> sillas){
        items.clear();
        for(Mesa mesa:mesas){
            Item item = new Item();
            item.setId_item(mesa.getMesa());
            item.setImage_source(R.mipmap.ic_logo);
            List<Silla> sillaList = new ArrayList<>();
            for(Silla silla: sillas){
                if(silla.getMesa().equals(item.getId_item())){
                    sillaList.add(silla);
                }
            }
            item.setSilla(sillaList);
            items.add(item);
        }

    }

    class SillaView extends RecyclerView.ViewHolder{
        private TextView mMesa;
        private ListView listView;
        private ImageView imageView;
        private String key;

        public SillaView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.silla_list_item,parent,false));
            mMesa = (TextView) itemView.findViewById(R.id.txt_mesa);
            listView = (ListView) itemView.findViewById(R.id.list_silla);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);

        }

        public void bind(Long mesa,int image,String key,List<Silla> sillas){
            String id = "Mesa " + mesa + " :";
            imageView.setImageResource(image);
            listView.setAdapter(new ListAdapter(sillas));
            mMesa.setText(id);
            this.key = key;
        }
    }

    class SillaAdapter extends RecyclerView.Adapter<SillaView>{
        private List<Mesa> Mesas;
        private List<Silla> Sillas;
        private List<Item> items;

        public SillaAdapter(List<Mesa> Mesas,List<Silla> Sillas) {
            this.Mesas = Mesas;
            this.Sillas = Sillas;
        }

        public SillaAdapter(List<Item> items) {
            this.items = items;
        }


        @NonNull
        @Override
        public SillaView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new SillaView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull SillaView holder, int position) {
            holder.bind(items.get(position).getId_item(),items.get(position).getImage_source(),String.valueOf(items.get(position).getId_item()),items.get(position).getSilla());
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    class ListAdapter extends BaseAdapter{
        private List<Silla> sillas;

        public ListAdapter(List<Silla> sillas) {
            this.sillas = sillas;
        }

        @Override
        public int getCount() {
            return sillas.size();
        }

        @Override
        public Object getItem(int i) {
            return sillas.get(i);
        }

        @Override
        public long getItemId(int i) {return 0;}

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Silla silla = (Silla) getItem(i);
            view = LayoutInflater.from(mContext).inflate(R.layout.list_view,null);
            TextView txt_estado = (TextView) view.findViewById(R.id.txt_silla_estado);
            String estado = "Silla: " + silla.getEstado();
            txt_estado.setText(estado);

            return view;
        }
    }
}
