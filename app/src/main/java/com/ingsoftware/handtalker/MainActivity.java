package com.ingsoftware.handtalker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText e1,e2,e3,e4,e5;
    Button registrar1;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relacionarVistas();
    }

    public void relacionarVistas(){
        e1=(EditText)findViewById(R.id.nombre1);
        e2=(EditText)findViewById(R.id.apellido1);
        e3=(EditText)findViewById(R.id.telefono1);
        e4=(EditText)findViewById(R.id.correo1);
        e5=(EditText)findViewById(R.id.contrasena1);
        registrar1=(Button)findViewById(R.id.botonRegistra);
    }

    public void validar(View v){
        final String nombre = e1.getText().toString();
        final String Apellido =e2.getText().toString();
        final String Telefono =e3.getText().toString();
        final String Correo = e4.getText().toString();
        final String contrasena =e5.getText().toString();

        String url ="http://localhost:8080/insertarUsuario.php?nombre=" +nombre+ "&Apellido" + Apellido+ "&Telefono" + Telefono + "$Correo" + Correo + "&Contrasena" + contrasena;
        RequestQueue servicio = Volley.newRequestQueue(this);
        StringRequest respuesta = new StringRequest(
                Request.Method.GET, url,
                response -> {
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                },
                error -> {
                    Toast.makeText(getApplicationContext(), "Error comunicación", Toast.LENGTH_SHORT).show();
                }
        );
        servicio.add(respuesta);


    }
}