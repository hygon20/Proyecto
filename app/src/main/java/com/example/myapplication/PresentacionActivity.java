package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

public class PresentacionActivity extends AppCompatActivity {

    ProgressBar progressBar;
    Integer id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentacion);

        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences preferences= getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
                boolean sesion= preferences.getBoolean("sesion",false);
                /*String usuario = preferences.getString("usuario","");
                String password = preferences.getString("password","");*/

                if (sesion){
                    //recibirDatos();
                    Intent i = new Intent(getApplicationContext(),principal.class);
                    //i.putExtra("usuario",usuario);
                    //i.putExtra("apellidos",apellidos);
                    //i.putExtra("id",id);
                    startActivity(i);
                    finish();
                }else{
                    Intent j = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(j);
                    finish();
                }
            }
        },2000);


    }

}