package com.example.practicaps.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practicaps.R;
import com.example.practicaps.holders.EventosHolder;
import com.example.practicaps.utils.Informacion;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorEventos extends RecyclerView.Adapter<EventosHolder> {
    private List<Informacion> listMensaje = new ArrayList();
    private Context c;

    public AdaptadorEventos(Context c) {
        this.c = c;
    }

    public void addEvento(Informacion i){
        listMensaje.add(i);
        notifyItemInserted(listMensaje.size());
    }

    @NonNull
    @Override
    public EventosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.card_view_eventos,parent,false);
        return new EventosHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventosHolder holder, int position) {
        holder.getNombre().setText(listMensaje.get(position).getInfo());
        holder.getFecha().setText(listMensaje.get(position).getFecha());

        int hora = listMensaje.get(position).getHora();
        int min = listMensaje.get(position).getMin();
        StringBuilder sb = new StringBuilder();
        sb.append(hora);
        sb.append(":");
        sb.append(min);
        holder.getHora().setText(sb);
    }


    @Override
    public int getItemCount() {
        return listMensaje.size();
    }
}
