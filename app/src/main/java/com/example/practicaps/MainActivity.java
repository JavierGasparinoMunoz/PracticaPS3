package com.example.practicaps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Iterator;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Inicializo las variables correspondientes al layout
    private EditText nombreLog, passLog;
    private Button btnLog,registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        instancias();
        admin();
        acciones();
    }

    // Metodo donde se le añade una accion a variables, ya sea al pasar por encima arrastrar escuchar un cambio, etc.
    private void acciones() {
        btnLog.setOnClickListener(this);
        registro.setOnClickListener(this);
    }

    private void instancias() {
        nombreLog = findViewById(R.id.edit_usuario_log);
        passLog = findViewById(R.id.edit_pass_log);
        btnLog = findViewById(R.id.button_log);
        registro = findViewById(R.id.button_registro);
    }

    private void admin(){
        if(nombreLog.getText().toString().equals("administrador@gmail.com") && passLog.getText()
                .toString().equals("administrador")){
            Intent intent = new Intent(getApplicationContext(),AdministradorActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void recuperar(){
        passLog.getText().toString();
        Toast.makeText(getApplicationContext(), "La ", Toast.LENGTH_SHORT).show();
    }

    public void onClick(View view) {
        switch (view.getId()){
            // Si se pulsa el boton de login se mete dentro de este caso y mediante la varaiable mAuth
            // de la clase de autetificacion de firebase se intenta logear al usuario mediante el email y la contraseña introducidos
            case R.id.button_log:
                Intent menu= new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(menu);
                break;

            //Si se pulsa el boton de crear cuenta se pasa a una actividad donde el usuario se registrara
            case R.id.button_registro:
                Intent reg = new Intent(getApplicationContext(),RegistroActivity.class);
                startActivity(reg);
                break;
        }
    }
}