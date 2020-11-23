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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.widget.ImageView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PerfilActivity extends AppCompatActivity {
Integer id;
    EditText edtNombre, edtApellidos,edtTelefono, edtDocumento, edtCorreo, edtClave;
    String nombres_usuario,apellidos_usurio,telefono,documento,clave,correo,sexo,respuesta;
    Spinner spinner_sexo,spinner_doc;
    Button btnActualizar;
    TextView consu;
    ImageView ivRegresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        edtNombre=findViewById(R.id.edtNombres);
        edtApellidos=findViewById(R.id.edtApellidos);
        edtTelefono=findViewById(R.id.edtTelefono);
        edtDocumento= findViewById(R.id.edtdocumento);
        edtCorreo= findViewById(R.id.edtCorreo);
        edtClave=findViewById(R.id.edtpsw);
        spinner_sexo= findViewById(R.id.sexo_spinner);
        spinner_doc=findViewById(R.id.doc_spinner);

        consu= findViewById(R.id.consulta);
        btnActualizar= findViewById(R.id.btnActualizarRegistro);




        ArrayAdapter<CharSequence> adapter_doc = ArrayAdapter.createFromResource(this,R.array.tipo_doc, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.opciones, android.R.layout.simple_spinner_item);
        spinner_sexo.setAdapter(adapter);
        spinner_doc.setAdapter(adapter_doc);

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActualizarUsuario("http://pethealth001.000webhostapp.com/pet/actualizar_datos.php");
                //Toast.makeText(PerfilActivity.this,"clic en actualizar",Toast.LENGTH_LONG).show();

            }
        });
        //txtId= findViewById(R.id.textId);
        recuperarDatos();
        TraerDatosUsuario("http://pethealth001.000webhostapp.com/pet/get_usuario.php");

        ivRegresar = (ImageView) findViewById(R.id.ivRegresar);
        ivRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();  //Para regresar a la vista anterior

                // Intent i = new Intent(principal.this, principal.class);  //Para ir a una vista específica
                // startActivity(i);
            }
        });


    }

    private void recuperarDatos(){
        SharedPreferences preferences= getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        //id= preferences.getInt("id", Integer.parseInt("0"));
        id =preferences.getInt("id", Integer.parseInt("0"));


    }


    /*Traer info del usuario en un json */

    private void TraerDatosUsuario(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()){

                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        nombres_usuario  = jsonObject.getString("usu_nombres");
                        apellidos_usurio = jsonObject.getString("usu_apellidos");
                        telefono         = jsonObject.getString("usu_telefono");
                        documento        = jsonObject.getString("usu_documento");
                        correo           = jsonObject.getString("usu_usuario");
                        clave            = jsonObject.getString("usu_password");
                        sexo             = jsonObject.getString("sexo");

                        edtNombre.setText(nombres_usuario);
                        edtApellidos.setText(apellidos_usurio);
                        edtTelefono.setText(telefono);
                        edtDocumento.setText(documento);
                        edtCorreo.setText(correo);
                        edtClave.setText(clave);
                        spinner_sexo.setTag(sexo);

                        //txtId.setText(nombres_usuario);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{

                    Toast.makeText(PerfilActivity.this,"Usuario registrado con exito",Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PerfilActivity.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();

                parametros.put("id_usuario",id.toString());


                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void ActualizarUsuario(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(PerfilActivity.this,response,Toast.LENGTH_LONG).show();
                try {
                    JSONObject jsonObjectAc = new JSONObject(response);
                     respuesta  = jsonObjectAc.getString("respuesta");
                    Toast.makeText(PerfilActivity.this,respuesta,Toast.LENGTH_LONG).show();
                     if(respuesta.isEmpty()){
                         Toast.makeText(PerfilActivity.this,"No se permiten campos vacios , por favor valide la información",Toast.LENGTH_LONG).show();

                     }else{
                         Toast.makeText(PerfilActivity.this,"Datos actualizados con exito!",Toast.LENGTH_LONG).show();
                         Intent miIntent = new Intent(getApplicationContext(),PresentacionActivity.class);
                         startActivity(miIntent);
                     }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PerfilActivity.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("usu_password",edtClave.getText().toString());
                parametros.put("usu_nombres",edtNombre.getText().toString());
                parametros.put("usu_apellidos",edtApellidos.getText().toString());
                parametros.put("usu_usuario",edtCorreo.getText().toString());
                parametros.put("usu_telefono",edtTelefono.getText().toString());
                parametros.put("usu_documento",edtDocumento.getText().toString());
                parametros.put("usu_sexo",spinner_sexo.getSelectedItem().toString());
                parametros.put("usu_tipoDoc",spinner_doc.getSelectedItem().toString());
                parametros.put("id_usuario",id.toString());
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}