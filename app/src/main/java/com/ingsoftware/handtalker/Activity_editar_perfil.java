package com.ingsoftware.handtalker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class Activity_editar_perfil extends AppCompatActivity {

    private ImageView atras;
    private ImageView guardar;
    private EditText etname;
    private EditText etApellido;
    private EditText etTelefono;
    private EditText etCorreos;
    private EditText etContrasena;

    String id;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        requestQueue = Volley.newRequestQueue(this);

        String currentValue = globalVariable.getInstance().getGlobalString();
        id=currentValue;

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            id = extras.getString(id);
        }

        // Cambiar el color de la barra de estado
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.azulInicio));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.white));
        }

        atras = findViewById(R.id.atras_edit);
        guardar = findViewById(R.id.guardarCambios);

        etname = findViewById(R.id.nombres);
        etApellido = findViewById(R.id.apellidos);
        etTelefono = findViewById(R.id.telefonos);
        etCorreos = findViewById(R.id.correos);
        etContrasena = findViewById(R.id.contrasenas);

        readUser();

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {abrirPerfil();}
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {validar(v);}
        });
    }

    private void abrirPerfil() {
        Intent intent = new Intent(Activity_editar_perfil.this, Activity_handtalker_perfil.class);
        startActivity(intent);
    }

    private void readUser(){
        String URL = "http://192.168.1.11:8080/handtalker/fetch.php?id="+id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String nombre, apellido, telefono, correo, contrasena;
                        try {
                            nombre = response.getString("nombre");
                            apellido = response.getString("apellido");
                            telefono = response.getString("telefono");
                            correo = response.getString("correo");
                            contrasena = response.getString("contrasena");

                            etname.setText(nombre);
                            etApellido.setText(apellido);
                            etTelefono.setText(telefono);
                            etCorreos.setText(correo);
                            etContrasena.setText(contrasena);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    public void validar(View v){
        final String nombre = etname.getText().toString();
        final String apellido =etApellido.getText().toString();
        final String telefono =etTelefono.getText().toString();
        final String correo = etCorreos.getText().toString();
        final String contrasena =etContrasena.getText().toString();

        actualizarUser(nombre,apellido,telefono,correo,contrasena);
    }

    private void actualizarUser(String nombre, String apellido, String telefono, String correo, String contrasena) {
        String URL1 = "http://192.168.1.11:8080/handtalker/actualizar.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Activity_editar_perfil.this,"Se actualizaron los datos de usuario", Toast.LENGTH_SHORT).show();
                        abrirPerfil();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Activity_editar_perfil.this,"Ingresa los datos faltantes", Toast.LENGTH_SHORT).show();

                    }
                }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                if(nombre.equals("") || apellido.equals("") || telefono.equals("") || correo.equals("") || contrasena.equals("")){
                    Toast.makeText(Activity_editar_perfil.this,"Faltan datos", Toast.LENGTH_SHORT).show();
                }else{
                    params.put("id",id);
                    params.put("nombre", nombre);
                    params.put("apellido", apellido);
                    params.put("telefono", telefono);
                    params.put("correo", correo);
                    params.put("contrasena", contrasena);
                }
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}