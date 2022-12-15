package com.example.practicaps.dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.practicaps.R;
import com.example.practicaps.utils.Informacion;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;

public class DialogoCalendar extends DialogFragment {

    private EditText editText;
    private Button botonInfo;
    private ImageButton fecha;
    TextView horas;
    private View vista;
    private Informacion informacion;
    private OnDialogoPersoListener listener;
    String date;
    private int hora,mins;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://practicaps-d596b-default-rtdb.europe-west1.firebasedatabase.app/");

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnDialogoPersoListener) context;
        } catch (ClassCastException e){
            Log.v("cast","No se puede castear");
        }


        vista = LayoutInflater.from(context).inflate(R.layout.dialogo_calendario,null);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        instancias();
        acciones();
        AlertDialog.Builder dialogo = new AlertDialog.Builder(getContext());
        dialogo.setView(vista);
        return dialogo.create();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        date = getArguments().getString("date");
        date = date.replace("/","-");
        System.out.println(date);

    }

    private void acciones() {
        botonInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                informacion = new Informacion(editText.getText().toString(),hora,mins);
                informacion.setFecha(date);
                System.out.println(informacion);
                guardarDataBase();
                dismiss();
            }
        });

        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                hora = c.get(Calendar.HOUR_OF_DAY);
                mins = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        horas.setText(hourOfDay + ":" + minute);
                        hora = hourOfDay;
                        mins = minute;
                    }
                },hora,mins,false);
                timePickerDialog.show();
            }
        });
    }

    private void guardarDataBase(){
        DatabaseReference RootRef = database.getReference();
        DatabaseReference usuarioMensajeRef = RootRef.child("usuarios").child(mAuth.
                getCurrentUser().getUid()).child("calendario").child(date).push();
        String mensajePushID = usuarioMensajeRef.getKey();
        String calEventRef = "usuarios/" + mAuth.getCurrentUser().getUid() + "/calendario/" + date +  "/" + mensajePushID;
        Map eventoTxt = new HashMap();
        eventoTxt.put("hora", hora);
        eventoTxt.put("min",mins);
        eventoTxt.put("evento", informacion.getInfo());

        Map calendarAdder = new HashMap();
        calendarAdder.put(calEventRef, eventoTxt);

        RootRef.updateChildren(calendarAdder).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    System.out.println("Done");
                } else {
                    System.out.println("Error");
                }
            }
        });
    }

    private void instancias() {
        editText =  vista.findViewById(R.id.edit_evento);
        botonInfo = vista.findViewById(R.id.btn_añadir_evento);
        horas = vista.findViewById(R.id.time_event);
        fecha = vista.findViewById(R.id.set_time);
    }

    public interface OnDialogoPersoListener{
        void onDilagoloSelected(String informacion);
    }
}

