package com.example.practicaps.holders;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practicaps.R;

public class EventosHolder extends RecyclerView.ViewHolder {

    private TextView nombre,hora,fecha;

    public EventosHolder(@NonNull View itemView) {
        super(itemView);
        nombre = itemView.findViewById(R.id.evento);
        hora = itemView.findViewById(R.id.hora);
        fecha = itemView.findViewById(R.id.fecha);
    }

    public TextView getNombre() {
        return nombre;
    }

    public void setNombre(TextView nombre) {
        this.nombre = nombre;
    }

    public TextView getHora() {
        return hora;
    }

    public void setHora(TextView hora) {
        this.hora = hora;
    }

    public TextView getFecha() {
        return fecha;
    }

    public void setFecha(TextView fecha) {
        this.fecha = fecha;
    }
}
