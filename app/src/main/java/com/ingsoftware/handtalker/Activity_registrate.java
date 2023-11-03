package com.ingsoftware.handtalker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ingsoftware.handtalker.R;

import java.util.HashMap;
import java.util.Map;

public class Activity_registrate extends AppCompatActivity{

    EditText e1,e2,e3,e4,e5;
    RequestQueue requestQueue;

    private static final String URL1 = "http://192.168.8.11:8080/handtalker/save.php";

    private Button registrarte;
    private ImageView cerrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrate);

        requestQueue = Volley.newRequestQueue(this);

        e1=(EditText)findViewById(R.id.nombre1);
        e2=(EditText)findViewById(R.id.apellido1);
        e3=(EditText)findViewById(R.id.telefono1);
        e4=(EditText)findViewById(R.id.correo1);
        e5=(EditText)findViewById(R.id.contrasena1);

        // Cambiar el color de la barra de estado
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.azulInicio));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.white));
        }

        cerrar = findViewById(R.id.atrasx);
        registrarte = findViewById(R.id.botonRegistra);

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirLogin();
            }
        });

        registrarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar(v);
                abrirLogin();
            }
        });

    }

    private void abrirLogin() {
        Intent intent = new Intent(Activity_registrate.this, LoginActivity.class);
        startActivity(intent);
    }

    public void validar(View v){
        final String nombre = e1.getText().toString();
        final String apellido =e2.getText().toString();
        final String telefono =e3.getText().toString();
        final String correo = e4.getText().toString();
        final String contrasena =e5.getText().toString();

        createUser(nombre,apellido,telefono,correo,contrasena);
    }

    private void createUser(String nombre, String apellido, String telefono, String correo, String contrasena) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Activity_registrate.this,"Se registró correctamente", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Activity_registrate.this,"Error del sistema", Toast.LENGTH_SHORT).show();

                    }
                }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("nombre", nombre);
                params.put("apellido", apellido);
                params.put("telefono", telefono);
                params.put("correo", correo);
                params.put("contrasena", contrasena);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }


}