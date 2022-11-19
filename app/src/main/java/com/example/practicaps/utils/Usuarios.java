package com.example.practicaps.utils;

public class Usuarios {
    private String uid,email,nombre,apellido;

    public Usuarios() {
    }

    public Usuarios(String uid) {
        this.uid = uid;
    }


    public Usuarios(String nombre, String apellido, String ciclo) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Usuarios(String uid, String email, String nombre, String apellido) {
        this.uid = uid;
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }


    @Override
    public String toString() {
        return "Usuarios{" +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", nombre='" + nombre + '\'' +
                "uid='" + uid + '\'' +
                '}';
    }
}
