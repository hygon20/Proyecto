package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import static java.lang.Integer.*;

public class principal extends AppCompatActivity {
TextView txtNombre;
String nombre,apellido,url;
Integer id;
Button cerrarSesion;
ImageButton perfilBtn,agendarCita,btnHistorial,btnlink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        cerrarSesion= findViewById(R.id.btnCerrar);
        txtNombre= findViewById(R.id.txtNombre);
        perfilBtn = findViewById(R.id.imgPerfil);
        agendarCita = findViewById(R.id.ImgAgendar);
        btnHistorial = findViewById(R.id.historico);
        btnlink = findViewById(R.id.redes);
        recuperarDatos();

        btnlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                url="https://www.instagram.com/donperro_/?igshid=v1ys4gazzd5t";
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);

            }
        });
        perfilBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(principal.this,PerfilActivity.class);
                startActivity(i);
            }
        });

        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences= getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
                preferences.edit().clear().commit();
                Intent miIntent = new Intent(principal.this,MainActivity.class);
                startActivity(miIntent);
                finish();
            }
        });

        agendarCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(principal.this,CitasActivity.class);
                startActivity(intent);
            }
        });

        btnHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent miIntent = new Intent(principal.this,HisotrialActivity.class);
                startActivity(miIntent);
            }
        });
    }
    private void recuperarDatos(){
        SharedPreferences preferences= getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        nombre = preferences.getString("nombres_usuario","micorreo@gmail.com");
        apellido = preferences.getString("apellidos_usuario","micorreo@gmail.com");
        id =preferences.getInt("id", Integer.parseInt("0"));
        txtNombre.setText(nombre+" "+apellido);
    }


//ve+VvYAGt1PGNz6Q
}