package com.ingsoftware.handtalker;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ingsoftware.handtalker.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Activity_handtalker_perfil extends AppCompatActivity {

    private static final int TIEMPO_ENTRE_PULSACIONES = 2000; // 2 segundos
    private long tiempoUltimaPulsacion;
    private Toast toast;


    private ImageView home1;
    private ImageView traduccion1;
    private ImageView camara1;
    private ImageView perfil1;
    private ImageView config;
    private ImageView editarPerfil;

    private TextView etname;
    private TextView etApellido;
    private TextView etTelefono;
    private TextView etCorreos;

    String id="5";

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handtalker_perfil);

        requestQueue = Volley.newRequestQueue(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            id = extras.getString(id);
        }

        // Cambiar el color de la barra de estado
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.azulInicio));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.azulInicio));
        }

        home1 = findViewById(R.id.inicio);
        traduccion1 = findViewById(R.id.traduccion);
        camara1 = findViewById(R.id.camara);
        perfil1 = findViewById(R.id.perfil);
        editarPerfil = findViewById(R.id.editperfil);
        config = findViewById(R.id.ajuste);

        etname = findViewById(R.id.nombres);
        etApellido = findViewById(R.id.apellidos);
        etTelefono = findViewById(R.id.telefonos);
        etCorreos = findViewById(R.id.correos);

        readUser();



        //Eventos de los botones
        home1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirInicio();
            }
        });

        camara1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrircamara();
            }
        });

        traduccion1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTraduccion();
            }
        });

        editarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirVentanaBlanco();
            }
        });

        config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirConfig();
            }
        });
    }

    private void abrirConfig() {
        Intent intent = new Intent(Activity_handtalker_perfil.this, Activity_configuration.class);
        startActivity(intent);
    }

    private void abrirVentanaBlanco() {
        Intent intent = new Intent(Activity_handtalker_perfil.this, VentanaBlancoActivity.class);
        startActivity(intent);
    }

    private void abrirInicio() {
        Intent intent = new Intent(Activity_handtalker_perfil.this, HandTalkerMainActivity.class);
        startActivity(intent);
    }

    private void abrirTraduccion() {
        Intent intent = new Intent(Activity_handtalker_perfil.this, Activity_handtalker_traduccion.class);
        startActivity(intent);
    }

    private void abrircamara() {
        Intent intent = new Intent(Activity_handtalker_perfil.this, Activity_handtalker_camera.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() - tiempoUltimaPulsacion < TIEMPO_ENTRE_PULSACIONES) {
            if (toast != null) {
                toast.cancel();
            }
            super.onBackPressed();
            finishAffinity();  // Finaliza todas las actividades
            System.exit(0);  // Termina el proceso de la aplicación
            return;
        }

        tiempoUltimaPulsacion = System.currentTimeMillis();
        toast = Toast.makeText(this, "Pulsa de nuevo para salir", Toast.LENGTH_SHORT);
        toast.show();
    }
    private void readUser(){
        String URL = "http://192.168.8.11:8080/handtalker/fetch.php?id="+id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String nombre, apellido, telefono, correo;
                        try {
                            nombre = response.getString("nombre");
                            apellido = response.getString("apellido");
                            telefono = response.getString("telefono");
                            correo = response.getString("correo");

                            etname.setText(nombre);
                            etApellido.setText(apellido);
                            etTelefono.setText(telefono);
                            etCorreos.setText(correo);


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
}