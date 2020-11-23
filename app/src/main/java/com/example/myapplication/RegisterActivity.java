package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
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

public class RegisterActivity extends AppCompatActivity {

    EditText edtNombres,edtApellidos,edtCorreo,edtClave,edtTelefono,edtNumeroDoc,psw2;
    Button btnGuardar;
    Spinner spinner_sexo,spinner_doc;
    String respuesta,clave1,clave2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        edtNombres = findViewById(R.id.edtNombres);
        edtApellidos= findViewById(R.id.edtApellidos);
        edtCorreo= findViewById(R.id.edtCorreo);
        edtClave= findViewById(R.id.edtpsw);
        edtTelefono= findViewById(R.id.edtTelefono);
        edtNumeroDoc= findViewById(R.id.edtdocumento);
        psw2 = findViewById(R.id.edtpsw2);

        spinner_sexo= findViewById(R.id.sexo_spinner);
        spinner_doc=findViewById(R.id.doc_spinner);
        btnGuardar =findViewById(R.id.btnGuardarRegistro);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clave1 = edtClave.getText().toString();
                clave2 = psw2.getText().toString();
                if (clave1.equals(clave2)){
                    RegistrarUsuario("http://pethealth001.000webhostapp.com/pet/registrar_usuario.php");


                }else{
                    Toast.makeText(RegisterActivity.this,"clave1 = "+clave1 +" clave2 = "+clave2,Toast.LENGTH_LONG).show();
                }


            }
        });

        ArrayAdapter<CharSequence> adapter_doc = ArrayAdapter.createFromResource(this,R.array.tipo_doc, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.opciones, android.R.layout.simple_spinner_item);
        spinner_sexo.setAdapter(adapter);
        spinner_doc.setAdapter(adapter_doc);
    }
    private void RegistrarUsuario(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()){
                    Toast.makeText(RegisterActivity.this,response,Toast.LENGTH_LONG).show();

                    try {
                        JSONObject jsonObjectAc = new JSONObject(response);
                        respuesta  = jsonObjectAc.getString("respuesta");
                        Toast.makeText(RegisterActivity.this,respuesta,Toast.LENGTH_LONG).show();
                        if (respuesta.isEmpty()){
                            Toast.makeText(RegisterActivity.this,"No se permiten campos vacios, favor verificar la información",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(RegisterActivity.this,"Usuario registrado con exito !",Toast.LENGTH_LONG).show();

                            Intent miIntent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(miIntent);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();


                parametros.put("usu_nombres",edtNombres.getText().toString());
                parametros.put("usu_apellidos",edtApellidos.getText().toString());
                parametros.put("usu_usuario",edtCorreo.getText().toString());
                parametros.put("usu_password",edtClave.getText().toString());

                parametros.put("usu_telefono",edtTelefono.getText().toString());
                parametros.put("usu_documento",edtNumeroDoc.getText().toString());

                parametros.put("usu_sexo",spinner_sexo.getSelectedItem().toString());
                parametros.put("usu_tipoDoc",spinner_doc.getSelectedItem().toString());

                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


}