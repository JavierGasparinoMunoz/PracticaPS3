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

    //Inicializo una clase propia de firebase para la autetificacion mediante ella
    private FirebaseAuth mAuth;

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
        mAuth = FirebaseAuth.getInstance();
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

    /*
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
    */

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            // Si se pulsa el boton de login se mete dentro de este caso y mediante la varaiable mAuth
            // de la clase de autetificacion de firebase se intenta logear al usuario mediante el email y la contraseña introducidos
            case R.id.button_log:

                mAuth.signInWithEmailAndPassword(nombreLog.getText().toString(), passLog.getText().toString())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful() && !nombreLog.getText().toString().isEmpty()
                                        && !passLog.getText().toString().isEmpty()) {
                                    FirebaseUser currentUser = mAuth.getCurrentUser();
                                    String uid = currentUser.getUid();
                                    Log.d("login", "signInWithEmail:success");

                                    startActivity(new Intent(MainActivity.this, MenuActivity.class));
                                    finish();


                                    //Si se ha introducido mal la contraseña o el email o estan vacios o
                                    // no existe ese usaurio salta un aviso de que la autetificacion ha fallado
                                } else {
                                    Log.w("login", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(getApplicationContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;

            //Si se pulsa el boton de crear cuenta se pasa a una actividad donde el usuario se registrara
            case R.id.button_registro:
                Intent i = new Intent(getApplicationContext(),RegistroActivity.class);
                startActivity(i);
                break;
        }
    }
}