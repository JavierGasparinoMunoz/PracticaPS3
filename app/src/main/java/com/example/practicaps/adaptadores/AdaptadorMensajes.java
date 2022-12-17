package com.example.practicaps.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practicaps.R;
import com.example.practicaps.holders.MensajeHolder;
import com.example.practicaps.utils.Mensajes.MensajeRecibir;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdaptadorMensajes extends RecyclerView.Adapter<MensajeHolder> {
    private List<MensajeRecibir> listMensaje = new ArrayList();
    private Context c;

    public AdaptadorMensajes(Context c) {
        this.c = c;
    }

    public void addMensaje(MensajeRecibir m){
        listMensaje.add(m);
        notifyItemInserted(listMensaje.size());
    }

    @NonNull
    @Override
    public MensajeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.card_view_mensajes,parent,false);
        return new MensajeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MensajeHolder holder, int position) {
        holder.getNombre().setText(listMensaje.get(position).getNombre());
        holder.getMensaje().setText(listMensaje.get(position).getMensaje());

        if (listMensaje.get(position).getType_mensaje().equals("1")){
            holder.getMensaje().setVisibility(View.VISIBLE);
        }

        Long codHora = listMensaje.get(position).getHora();
        Date d = new Date(codHora);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
        holder.getHora().setText(sdf.format(d));

    }


    @Override
    public int getItemCount() {
        return listMensaje.size();
    }
}
