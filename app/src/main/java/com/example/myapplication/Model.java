package com.example.myapplication;

public class Model {
    private String tipo_cita,descripcion,medico,fecha_cita;
    private int id,img;

    public Model(String tipo_cita, String descripcion, String medico,String fecha_cita) {
        this.tipo_cita = tipo_cita;
        this.descripcion = descripcion;
        this.medico = medico;
        this.id = id;
        this.img = img;
        this.fecha_cita = fecha_cita;
    }


    public String getFecha_cita() {
        return fecha_cita;
    }

    public String getTipo_cita() {
        return tipo_cita;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getMedico() {
        return medico;
    }

    public int getId() {
        return id;
    }

    public int getImg() {
        return img;
    }
}
