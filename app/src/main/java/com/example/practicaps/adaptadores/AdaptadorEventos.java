package com.example.practicaps.adaptadores;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practicaps.EventosActivity;
import com.example.practicaps.R;
import com.example.practicaps.holders.EventosHolder;
import com.example.practicaps.utils.Informacion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.List;


public class AdaptadorEventos extends RecyclerView.Adapter<EventosHolder> implements View.OnClickListener {
    private List<Informacion> listMensaje = new ArrayList();
    private Context c;
    private Informacion i;

    public AdaptadorEventos(Context c) {
        this.c = c;
    }

    public void addEvento(Informacion i) {
        listMensaje.add(i);
        notifyItemInserted(listMensaje.size());
    }

    public void deleteEvento(Informacion i) {
        this.i = i;
        listMensaje.remove(i);
        notifyItemInserted(listMensaje.size());
    }

    @NonNull
    @Override
    public EventosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.card_view_eventos, parent, false);
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


        ImageButton button = holder.getButtonBorrarEvento();
        button.setOnClickListener(this);
    }


    @Override
    public int getItemCount() {
        return listMensaje.size();
    }

    @Override
    public void onClick(View view) { //parte logica para boton eleminar evento del calendario
        switch (view.getId()) {
            case R.id.borrarEvento:

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference().child(i.getInfo());
                ref.removeValue();
                break;

                //VIDEO DE DONDE LO HE SACADO: https://www.youtube.com/watch?v=On7b3WImMAM&ab_channel=ElArtedelaProgramaci%C3%B3n

        }
    }
}