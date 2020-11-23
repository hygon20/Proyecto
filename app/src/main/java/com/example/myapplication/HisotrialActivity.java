package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.widget.ImageView;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HisotrialActivity extends AppCompatActivity {
    String nombre;
    MyAdapter myAdapter;
    Integer id_usuario,i;
    ImageView ivRegresar;

    RecyclerView mRecyclerView;
   List<Model> playerList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hisotrial);

        TraerDatosUsuario("http://pethealth001.000webhostapp.com/pet/get_citas.php");
        recuperarDatos();
        mRecyclerView= findViewById(R.id.recycler);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        playerList = new ArrayList<>();

        /*myAdapter = new MyAdapter(this,getMyList());
        mRecyclerView.setAdapter(myAdapter);*/

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
        id_usuario= preferences.getInt("id", Integer.parseInt("0"));
        //id = preferences.getString("id","0");
        //id =preferences.getInt("id", Integer.parseInt("0"));
        nombre = preferences.getString("nombres_usuario","micorreo@gmail.com");
    }

    private void TraerDatosUsuario(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {

                  @Override
            public void onResponse(String response) {
                      //Toast.makeText(HisotrialActivity.this,response,Toast.LENGTH_LONG).show();
                      Toast.makeText(HisotrialActivity.this,"Citas agendadas por el usuario :"+nombre,Toast.LENGTH_LONG).show();

                    try {
                        JSONArray array = new JSONArray(response);

                        for (i =0; i < array.length(); i++) {

                            JSONObject player = array.getJSONObject(i);
                            //Toast.makeText(HisotrialActivity.this, (CharSequence) player,Toast.LENGTH_LONG).show();
                            playerList.add(new Model(
                                    player.getString("tipo_cita"),
                                    player.getString("medico"),
                                    player.getString("descripcion"),
                                    player.getString("fecha_cita")


                            ));

                            /*Model m = new Model();
                             m.setTitle("New Feed");
                              m.setDescripcion("This is newsfeed descripcion...");
                              ble.ic_baseline_person_pin_24);ArrayList<Model> models = new ArrayList<>();
        models.add(m);*/

                        }
                        MyAdapter adapter= new MyAdapter(HisotrialActivity.this, (ArrayList<Model>) playerList);
                        mRecyclerView.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HisotrialActivity.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();

                parametros.put("id_usuario",id_usuario.toString());


                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


   /* private ArrayList<Model> getMyList() {
       /*Model m = new Model();
        m.setTitle("New Feed");
        m.setDescripcion("This is newsfeed descripcion...");
        m.setImg(R.drawable.ic_baseline_person_pin_24);
        ArrayList<Model> models = new ArrayList<>();
        models.add(m);

        return models;
    }*/
}