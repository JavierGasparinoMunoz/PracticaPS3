package com.example.practicaps.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practicaps.R;

public class MensajeHolder extends RecyclerView.ViewHolder {

    private TextView nombre,mensaje,hora;
    private ImageView fotoMensaje;

    public MensajeHolder(@NonNull View itemView) {
        super(itemView);
        nombre = itemView.findViewById(R.id.nombre_mensaje);
        mensaje = itemView.findViewById(R.id.mensaje);
        hora = itemView.findViewById(R.id.hora_mensaje);
        fotoMensaje = itemView.findViewById(R.id.foto_perfil_mensaje);
    }

    public TextView getNombre() {
        return nombre;
    }

    public void setNombre(TextView nombre) {
        this.nombre = nombre;
    }

    public TextView getMensaje() {
        return mensaje;
    }

    public void setMensaje(TextView mensaje) {
        this.mensaje = mensaje;
    }

    public TextView getHora() {
        return hora;
    }

    public void setHora(TextView hora) {
        this.hora = hora;
    }

    public ImageView getFotoMensaje() {
        return fotoMensaje;
    }

    public void setFotoMensaje(ImageView fotoMensaje) {
        this.fotoMensaje = fotoMensaje;
    }
}
