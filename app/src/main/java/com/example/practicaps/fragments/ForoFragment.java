package com.example.practicaps.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practicaps.EventosActivity;
import com.example.practicaps.MainActivity;
import com.example.practicaps.R;
import com.example.practicaps.adaptadores.AdaptadorMensajes;
import com.example.practicaps.dialogos.DialogoCalendar;
import com.example.practicaps.utils.Informacion;
import com.example.practicaps.utils.Mensajes.MensajeEnviar;
import com.example.practicaps.utils.Mensajes.MensajeRecibir;
import com.example.practicaps.utils.Usuarios;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ForoFragment extends Fragment {

    private TextView textNombre;
    private Button btnEnviar;
    private EditText editMensaje;
    private RecyclerView recyclerMensaje;
    private AdaptadorMensajes adaptadorMensajes;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseDatabase database;
    String currentUser;
    private DatabaseReference databaseReference;

    private String nombreUsuarioLog;

    public ForoFragment() {
    }

    public static ForoFragment newInstance(String user) {

        Bundle args = new Bundle();
        args.putString("user",user);

        ForoFragment fragment = new ForoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        currentUser =  this.getArguments().getString("user");
        Log.v("llega",currentUser);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_foro,container,false);
        textNombre = view.findViewById(R.id.id_nombre);
        btnEnviar = view.findViewById(R.id.btn_enviar);
        editMensaje = view.findViewById(R.id.txt_mensajes);
        recyclerMensaje = view.findViewById(R.id.rv_mensajes);

        database = FirebaseDatabase.getInstance("https://practicaps-d596b-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference = database.getReference("chat");

        adaptadorMensajes = new AdaptadorMensajes(getContext());
        linearLayoutManager = new LinearLayoutManager(getContext());

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Se da un valor al recycler seteandole un layout y un adaptador
        recyclerMensaje.setLayoutManager(linearLayoutManager);
        recyclerMensaje.setAdapter(adaptadorMensajes);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editMensaje.getText().length() > 1) {
                    databaseReference.push().setValue(new MensajeEnviar(nombreUsuarioLog, editMensaje.getText().toString(), "1", ServerValue.TIMESTAMP));
                    editMensaje.setText("");
                }
            }
        });

        adaptadorMensajes.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setScrollBar();
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                MensajeRecibir m = dataSnapshot.getValue(MensajeRecibir.class);
                adaptadorMensajes.addMensaje(m);
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
        recyclerMensaje.scrollToPosition(adaptadorMensajes.getItemCount()-1);
    }

    // Con este metodo se recoge el nombre del usuario que habla en el chat
    @Override
    public void onResume() {
        if(currentUser != null){
            btnEnviar.setEnabled(false);
            DatabaseReference referenciaTipo =  FirebaseDatabase.getInstance("https://practicaps-d596b-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("usuarios").child(currentUser);
            referenciaTipo.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Usuarios usuarios = dataSnapshot.getValue(Usuarios.class);
                    nombreUsuarioLog = usuarios.getNombre();
                    textNombre.setText(nombreUsuarioLog);
                    btnEnviar.setEnabled(true);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else{
            returnLogin();
        }
        super.onResume();
    }

    // Cuando se llama a este metodo se regresa a MainActivity
    private void returnLogin(){
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        getFragmentManager().beginTransaction().remove(this).commit();
    }
}
