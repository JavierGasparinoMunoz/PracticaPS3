package com.example.practicaps.utils;

import java.io.Serializable;
import java.util.Date;

public class Informacion implements Serializable {
    String info;
    String fecha;
    int hora,min;

    public Informacion() {
    }

    public Informacion(String info, int hora, int min) {
        this.info = info;
        this.hora = hora;
        this.min = min;
    }

    /*public Informacion(String info, String fecha, int hora, int min) {
        this.info = info;
        this.fecha = fecha;
        this.hora = hora;
        this.min = min;
    }*/


    public Informacion(String date) {
        this.fecha = date;
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
                ", info='" + info + '\'' +
                ", hora=" + hora +
                ", min=" + min +
                '}';
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }
}
