package com.example.practicaps.utils.Mensajes;

public class Mensaje {
    private String nombre;
    private String mensaje;
    private String type_mensaje;

    public Mensaje() {
    }

    public Mensaje(String nombre, String mensaje, String type_mensaje) {
        this.nombre = nombre;
        this.mensaje = mensaje;
        this.type_mensaje = type_mensaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }


    public String getType_mensaje() {
        return type_mensaje;
    }

    public void setType_mensaje(String type_mensaje) {
        this.type_mensaje = type_mensaje;
    }
}
