package com.example.practicaps;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practicaps.adaptadores.AdaptadorEventos;
import com.example.practicaps.utils.Informacion;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

public class EventosActivity extends AppCompatActivity {
    private RecyclerView recyclerEventos;
    private AdaptadorEventos adaptadorEventos;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference databaseReference;
    private DatabaseReference usuarioMensajeRef;

    private String date;
    private String evento;

    public EventosActivity() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);
        date = getIntent().getExtras().get("date").toString();
        date = date.replace("/","-");
        System.out.println("Funciona: " + date);

        instancias();
        acciones();
    }

    private void acciones() {
        recyclerEventos.setLayoutManager(linearLayoutManager);
        recyclerEventos.setAdapter(adaptadorEventos);
    }

    private void instancias() {
        recyclerEventos = findViewById(R.id.rv_eventos);

        database = FirebaseDatabase.getInstance("https://practicaps-d596b-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference = database.getReference();
        usuarioMensajeRef = databaseReference.child("usuarios").child(mAuth.
                getCurrentUser().getUid()).child("calendario");

        adaptadorEventos = new AdaptadorEventos(this);
        linearLayoutManager = new LinearLayoutManager(this);

        adaptadorEventos.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setScrollBar();
            }
        });

        usuarioMensajeRef.child(date).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                System.out.println(dataSnapshot.getValue().toString());
                Informacion i = dataSnapshot.getValue(Informacion.class);
                i.setFecha(date);
                String eventoArray = dataSnapshot.getValue().toString();
                String[] arrOfStr = eventoArray.split(",");
                evento = arrOfStr[0].substring(8);
                i.setInfo(evento);
                System.out.println(i.getInfo());
                adaptadorEventos.addEvento(i);
                adaptadorEventos.notifyDataSetChanged();
                System.out.println(i);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setScrollBar(){
        recyclerEventos.scrollToPosition(adaptadorEventos.getItemCount()-1);
    }
}

