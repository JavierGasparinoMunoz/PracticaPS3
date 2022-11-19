package com.example.practicaps.utils;

import java.io.Serializable;
import java.util.Date;

public class Informacion implements Serializable {
    String info;
    String fecha;
    int hora,min;


    public Informacion(String info, int hora, int min) {
        this.info = info;
        this.hora = hora;
        this.min = min;
    }

    public Informacion(String info, String fecha, int hora, int min) {
        this.info = info;
        this.fecha = fecha;
        this.hora = hora;
        this.min = min;
    }

    public Informacion(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "Informacion{" +
                "info='" + info + '\'' +
                ", fecha='" + fecha + '\'' +
                ", hora=" + hora +
                ", min=" + min +
                '}';
    }
}
