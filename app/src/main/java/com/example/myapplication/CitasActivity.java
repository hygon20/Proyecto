package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.content.Intent;
import android.widget.ImageView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CitasActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnFecha,btnHora;
    Button btnAgendar;
    TextView fechaseleccionada,horaselecionada;
    Spinner spinner_cita,spinner_doc,spinner_sexo;
    EditText descripcion,tipo_animal,raza_animal,edad_animal,acompa_animal;
    String fecha_cita;
    String spiner_cita;
    String spiner_sexo;
    String spiner_doc;
    String descrip;
    String respuesta;
    String fecha_cita_selec;
    String tipo_animal_t;
    String raza_animal_T;
    String edad_animal_t;
    String acompa_animal_t;
    int hora;
    int minutos;
    final Calendar c= Calendar.getInstance();
    Integer id;
    ImageView ivRegresar;
    private int dia,mes,ano;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas);

        descripcion   = findViewById(R.id.descripcion);
        tipo_animal   = findViewById(R.id.tipo_animal);
        raza_animal   = findViewById(R.id.raza_animal);
        edad_animal   = findViewById(R.id.edad_animal);
        acompa_animal = findViewById(R.id.acompa);

        btnFecha = findViewById(R.id.btncalendar);
        btnHora=findViewById(R.id.btnhora);
        fechaseleccionada= findViewById(R.id.fechaSeleccionada);
        horaselecionada=findViewById(R.id.horaseleccionda);

        spinner_cita= findViewById(R.id.cita_spiner);
        spinner_sexo= findViewById(R.id.sexo_spiner);
        spinner_doc = findViewById(R.id.medico_spiner);
        btnAgendar = findViewById(R.id.btnAgendar);

        btnFecha.setOnClickListener(this);
        btnHora.setOnClickListener(this);
        ArrayAdapter<CharSequence> adapter_sexo = ArrayAdapter.createFromResource(this,R.array.opciones, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter_doc = ArrayAdapter.createFromResource(this,R.array.medico, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter_cita = ArrayAdapter.createFromResource(this,R.array.tipo_cita, android.R.layout.simple_spinner_item);
        spinner_cita.setAdapter(adapter_cita);
        spinner_doc.setAdapter(adapter_doc);
        spinner_sexo.setAdapter(adapter_sexo);

        ivRegresar = (ImageView) findViewById(R.id.ivRegresar);
        ivRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();  //Para regresar a la vista anterior

                // Intent i = new Intent(principal.this, principal.class);  //Para ir a una vista específica
                // startActivity(i);
            }
        });

        recuperarDatos();
        btnAgendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RegistrarCita("http://pethealth001.000webhostapp.com/pet/guardar_cita.php");


            }
        });




    }





    private void recuperarDatos(){
        SharedPreferences preferences= getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        //id= preferences.getInt("id", Integer.parseInt("0"));
        id =preferences.getInt("id", Integer.parseInt("0"));
    }

    private void RegistrarCita(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ///Toast.makeText(CitasActivity.this,response,Toast.LENGTH_LONG).show();
                //descripcion.setText(response);
                if (!response.isEmpty()){

                    try {
                        JSONObject jsonObjectAc = new JSONObject(response);
                        respuesta  = jsonObjectAc.getString("respuesta");


                            if (respuesta.isEmpty()){
                                Toast.makeText(CitasActivity.this,"No se permiten campos vacios",Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(CitasActivity.this,respuesta,Toast.LENGTH_LONG).show();

                                /*Intent miIntent = new Intent(getApplicationContext(),PresentacionActivity.class);
                                startActivity(miIntent);*/

                                Intent intent = new Intent(Intent.ACTION_EDIT);
                                intent.setType("vnd.android.cursor.item/event");

                                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, c.get(Calendar.DAY_OF_MONTH));
                                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, c.get(Calendar.DAY_OF_MONTH));

                                intent.putExtra(CalendarContract.Events.ALL_DAY,true);
                                intent.putExtra(CalendarContract.Events.TITLE,spiner_cita);
                                intent.putExtra(CalendarContract.Events.DESCRIPTION ,descrip);


                                startActivity(intent);
                               // spinner_cita.setText().toString();
                                 //spinner_doc.getSelectedItem().toString();
                                descripcion.setText("");
                                 fechaseleccionada.setText("");

                                tipo_animal.setText("");
                                 raza_animal.setText("");
                                 edad_animal.setText("");
                                 acompa_animal.setText("");
                                 //spinner_sexo.setText("");

                            }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                }else{


                    Intent miIntent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(miIntent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CitasActivity.this,error.toString(),Toast.LENGTH_LONG).show();
        }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();


                spiner_cita      = spinner_cita.getSelectedItem().toString();
                spiner_doc       = spinner_doc.getSelectedItem().toString();
                descrip          = descripcion.getText().toString();
                fecha_cita_selec = fechaseleccionada.getText().toString();

                tipo_animal_t    = tipo_animal.getText().toString();
                raza_animal_T    = raza_animal.getText().toString();
                edad_animal_t    = edad_animal.getText().toString();
                acompa_animal_t  = acompa_animal.getText().toString();
                spiner_sexo      = spinner_sexo.getSelectedItem().toString();

                parametros.put("tipo_cita",spiner_cita);
                parametros.put("medico_cita",spiner_doc);
                parametros.put("fecha_cita",fecha_cita_selec);
                parametros.put("descripcion_problema",descrip);
                parametros.put("id",id.toString());

                parametros.put("tipo_animal_t",tipo_animal_t);
                parametros.put("raza_animal_T",raza_animal_T);
                parametros.put("edad_animal_t",edad_animal_t);
                parametros.put("acompa_animal_t",acompa_animal_t);
                parametros.put("spiner_sexo",spiner_sexo);

                return parametros;


            }

        };


        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @Override
    public void onClick(View v) {
        if(v==btnFecha){
            final Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH);
            ano = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    fecha_cita= dayOfMonth+"/"+(month+1)+"/"+year;
                    fechaseleccionada.setText(fecha_cita);
                }
            },ano,mes,dia);
            datePickerDialog.show();

        }
        if (v==btnHora){


            hora = c.get(Calendar.HOUR_OF_DAY);
            minutos = c.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    horaselecionada.setText(hourOfDay+":"+minute);
                }
            },hora,minutos,false);
            timePickerDialog.show();

        }

         }

            }


