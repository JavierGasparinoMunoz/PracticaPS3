package com.example.practicaps;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.practicaps.utils.Usuarios;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener {

    //Inizalizo las variables necesarias del layout
    private EditText emailSign, passSign, nameSign, surnameSign,confirmPassSign;
    private Button btnSign,btnLog;


    // Inicializo las varaiables necesarias de firebase, esta es para recoger los usuarios
    private FirebaseAuth mAuth;
    Usuarios usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        instancias();
        acciones();
    }

    // Metodo donde se le añade una accion a variables, ya sea al pasar por encima arrastrar escuchar un cambio, etc.
    private void acciones() {
        btnSign.setOnClickListener(this);
        btnLog.setOnClickListener(this);
    }

    // Metodo donde se instancian las variables, (se les da valor)
    private void instancias() {
        mAuth = FirebaseAuth.getInstance();
        emailSign = findViewById(R.id.edit_email_sig);
        passSign = findViewById(R.id.edit_pass_sig);
        nameSign = findViewById(R.id.edit_name_sig);
        surnameSign = findViewById(R.id.edit_lastnames_sig);
        confirmPassSign = findViewById(R.id.edit_confirm_pass_sig);
        btnSign = findViewById(R.id.button_sign);
        btnLog = findViewById(R.id.button_log_sign);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    // Metodo que comprueba el usuario
    private void comprobarUsuario(final String uid){
        usuario = new Usuarios(uid,emailSign.getText().toString(),
                nameSign.getText().toString(), surnameSign.getText().toString());
        //DatabaseReference referenciaTipo =  FirebaseDatabase.getInstance().getReference().child("usuarios")
                //.child(usuario.getUid());
       FirebaseDatabase database = FirebaseDatabase.getInstance("https://practicaps-d596b-default-rtdb.europe-west1.firebasedatabase.app/");
       DatabaseReference reference = database.getReference("usuarios").child(usuario.getUid());
       reference.setValue(usuario);
        //usuario = new Usuarios(uid,)
        //referenciaTipo.child("usuarios").child(usuario.getUid());
    }

    // Metodo para vaciar los editText
    private void vaciarTexto(){
        emailSign.setText("");
        passSign.setText("");
        nameSign.setText("");
        surnameSign.setText("");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_sign:

                if(passSign.getText().toString().equals(confirmPassSign.getText().toString())) {

                    mAuth.createUserWithEmailAndPassword(emailSign.getText().toString(), passSign.getText()
                                    .toString())
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful() && !emailSign.getText().toString().isEmpty()
                                            && !passSign.getText().toString().isEmpty()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d("login", "createUserWithEmail:success");
                                        //Toast.makeText(getApplicationContext(), "Registro satisfactorio", Toast.LENGTH_SHORT);
                                        FirebaseUser currentUser = mAuth.getCurrentUser();;
                                        String uid = currentUser.getUid();
                                        comprobarUsuario(uid);
                                        System.out.println(currentUser);

                                        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                                        builder.setTitle("Dialogo de registro");
                                        builder.setMessage("Registro creado correctamente");

                                        vaciarTexto();

                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w("login", "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(getApplicationContext(), "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(getApplicationContext(),"Las contaseñas no coinciden",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.button_log_sign:
                finish();
                break;
        }
    }
}

