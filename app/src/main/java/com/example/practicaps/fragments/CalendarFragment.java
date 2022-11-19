package com.example.practicaps.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.practicaps.Dialogos.DialogoCalendar;
import com.example.practicaps.R;
import com.example.practicaps.utils.Informacion;

public class CalendarFragment  extends Fragment {
    // Se instancian las variables
    CalendarView calendarView;

    String  date;

    public CalendarFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    // Metodo donde se crea la vista del fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendario,container,false);

        //Se instancian los valores gracias a la vista creada
        calendarView = view.findViewById(R.id.calendarView);
        return view;
    }

    // Metodo donde una vez se haya creado la vista se establece que la variable date sera el dia,
    // el mes y el año seleccionados y se inicia el dialogo donde se pueden crear eventos
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = dayOfMonth + "/" + (month + 1) + "/" + year;
                Informacion informacion = new Informacion(date);
                DialogoCalendar dialogoCalendario = new DialogoCalendar();
                Bundle args = new Bundle();
                args.putString("date",date);
                dialogoCalendario.setArguments(args);
                dialogoCalendario.show(getFragmentManager(), "perso");
            }
        });
    }
}
