package com.ingsoftware.handtalker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {

    private static final int TIEMPO_ENTRE_PULSACIONES = 2000; // 2 segundos
    private long tiempoUltimaPulsacion;
    private Toast toast;

    private Button olvidaste;
    private Button registro;

    private EditText textoCorreo;
    private EditText textoContrasena;
    private Button modoAdmin;

    int boleano=0;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        requestQueue = Volley.newRequestQueue(this);


        // Cambiar el color de la barra de estado
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.azulInicio));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.white));
        }

        Button iniciarSesionButton = findViewById(R.id.iniciarSesionButton);
        olvidaste = findViewById(R.id.olvidasteCuenta);
        registro = findViewById(R.id.registrate);

        textoCorreo = findViewById(R.id.correoEditText);
        textoContrasena = findViewById(R.id.contrasenaEditText);
        modoAdmin = findViewById(R.id.administrator);

        iniciarSesionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readUser();
            }
        });

        olvidaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirRecupera();
            }
        });

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirRegistrarse();
            }
        });

        modoAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this,"Modo ulta secreto activado (Admin).", Toast.LENGTH_SHORT).show();
                abrirVentanaInicio();
            }
        });
    }

    //Permite hacer pruebas con los botones
    private void abrirVentanaBlanco() {
        Intent intent = new Intent(LoginActivity.this, VentanaBlancoActivity.class);
        startActivity(intent);
    }

    private void abrirRegistrarse() {
        Intent intent = new Intent(LoginActivity.this, Activity_registrate.class);
        startActivity(intent);
    }

    private void abrirVentanaInicio() {
        Intent intent = new Intent(LoginActivity.this, HandTalkerMainActivity.class);
        startActivity(intent);
    }

    private void abrirRecupera() {
        Intent intent = new Intent(LoginActivity.this, Activity_recupera_cuenta.class);
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
        String correoT = textoCorreo.getText().toString();
        String contrasenaT = textoContrasena.getText().toString();

        String URL = "http://192.168.1.11:8080/handtalker/iniciarSesion.php?correo="+correoT+"&contrasena="+contrasenaT;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String correoQ,contrasenaQ,nombreQ,idQ;
                        try {
                            correoQ = response.getString("correo");
                            contrasenaQ = response.getString("contrasena");
                            nombreQ = response.getString("nombre");
                            idQ = response.getString("idusuario");

                            if (correoT.equals(correoQ) && contrasenaT.equals(contrasenaQ)){
                                Toast.makeText(LoginActivity.this,"¡Bienvenido "+nombreQ+"!", Toast.LENGTH_SHORT).show();
                                String currentValue = globalVariable.getInstance().getGlobalString();
                                globalVariable.getInstance().setGlobalString(idQ);
                                boleano=1;
                                abrirVentanaInicio();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this,"Datos incorrectos, intenta de nuevo.", Toast.LENGTH_SHORT).show();

                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }
}

