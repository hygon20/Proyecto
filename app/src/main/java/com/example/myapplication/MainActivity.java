package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
EditText edtUsuario,edtPassword;
Button btnLogin;
String nombres_usuario,apellidos_usuario,usuario,password,sexo;
Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtUsuario = findViewById(R.id.edtUsuario);
        edtPassword= findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        recuperarDatos();
        btnLogin.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                usuario= edtUsuario.getText().toString();
                password=edtPassword.getText().toString();

                if (!usuario.isEmpty() && !password.isEmpty()){
                    validarUsuario("http://pethealth001.000webhostapp.com/pet/validar_usuario.php");
                }else {
                    Toast.makeText(MainActivity.this,"Los campos usuario y contraseña no pueden estar vacios",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void validarUsuario(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            if (!response.isEmpty()){

                //Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
                try {

                    JSONObject  jsonObject = new JSONObject(response);
                     nombres_usuario = jsonObject.getString("usu_nombres");
                     apellidos_usuario = jsonObject.getString("usu_apellidos");
                    sexo = jsonObject.getString("sexo");
                    id = jsonObject.getInt("id");
                     if (sexo==("Femenino")){
                         Toast.makeText(MainActivity.this,"Bienvenida "+nombres_usuario,Toast.LENGTH_LONG).show();
                     }else{
                         Toast.makeText(MainActivity.this,"Bienvenido "+nombres_usuario,Toast.LENGTH_LONG).show();
                     }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                guardarPreferencias();
                Intent miIntent = new Intent(getApplicationContext(),PresentacionActivity.class);

                startActivity(miIntent);
                finish();
            }else{
                Toast.makeText(MainActivity.this,"Usuario o contraseña incorrectos",Toast.LENGTH_LONG).show();
            }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("usuario",usuario);
                parametros.put("password",password);
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    public void clicRegister(View view){

        Intent miIntent = new Intent(MainActivity.this,RegisterActivity.class);
        startActivity(miIntent);
    }
    private void guardarPreferencias(){
        SharedPreferences preferences= getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();
        editor.putString("usuario",usuario);
        editor.putString("password",password);
        editor.putString("nombres_usuario",nombres_usuario);
        editor.putString("apellidos_usuario",apellidos_usuario);
        editor.putInt("id",id);
        editor.putBoolean("sesion",true);
        editor.commit();
    }

    private void recuperarDatos(){
        SharedPreferences preferences= getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        edtUsuario.setText(preferences.getString("usuario",""));
        edtPassword.setText(preferences.getString("password",""));
    }
}