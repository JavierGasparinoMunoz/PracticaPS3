package com.example.practicaps;
import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.practicaps.fragments.CalendarFragment;
import com.example.practicaps.fragments.ForoFragment;
import com.example.practicaps.utils.Usuarios;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Inicializo las variables que vaya a necesitar
    String currentUser, currentEmail;
    TextView nombreEdit, emailEdit;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        // Instancio las variables
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("URJC App");
        //databaseReference = FirebaseDatabase.getInstance().getReference();
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Instancio las variables contenidas en la clase "nav_header_evaluaciones" llamando al
        // metodo getHeaderView desde el navigation creado
        View headView = navigationView.getHeaderView(0);
        nombreEdit = headView.findViewById(R.id.id_nombre_apellido_perfil);
        emailEdit = headView.findViewById(R.id.email_Perfil);

        // Llamo al metodo para cambiar el nombre del usuario y el email que estan en el navigation drawler
        cambiarNombre();
    }

    //Metodo para cargar el fragment
    private void cargarFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.contenedor_fragments, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // Metodo para cambiar el nombre y el email de la cabecera por los del usuario.
    private void cambiarNombre() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String userID = auth.getCurrentUser().getUid();
        DatabaseReference referenciaTipo = FirebaseDatabase.getInstance("https://practicaps-d596b-default-rtdb.europe-west1.firebasedatabase.app/").getReference("usuarios").child(userID);
        referenciaTipo.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuarios usuarios = dataSnapshot.getValue(Usuarios.class);
                System.out.println(currentUser);
                currentUser = usuarios.getNombre();
                currentEmail = usuarios.getEmail();

                nombreEdit.setText(currentUser);
                emailEdit.setText(currentEmail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cerrar_sesion, menu);
        return true;

    }

    //Menu de arriba
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.cerrar_sesion) {
            returnLogin();
        }

        return super.onOptionsItemSelected(item);
    }

    //Menu lateral
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_calendar){
            cargarFragment(new CalendarFragment());
        } else if (id == R.id.nav_chat) {
            cargarFragment(ForoFragment.newInstance(FirebaseAuth.getInstance().getCurrentUser().getUid()));
        }else if (id == R.id.nav_cerrarSesion) {
            returnLogin();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    // Cuando se llama a este metodo se regresa a MainActivity
    private void returnLogin() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }


}
