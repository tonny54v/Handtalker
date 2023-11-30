package com.ingsoftware.handtalker;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
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
    private RelativeLayout backr;
    private TextView textoWelcome;
    String themes;
    String idFotos;
    String ip;

    int boleano=0;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        requestQueue = Volley.newRequestQueue(this);

        //Configuracion Global del tema
        String currentValue = globalTheme.getInstance().getGlobalTema();
        themes=currentValue;

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            themes = extras.getString(themes);
        }

        //Configuracion Global del la foto de perfil
        String currentValue3 = globalFoto.getInstance().getGlobalFoto();
        idFotos=currentValue3;

        Bundle extras3 = getIntent().getExtras();
        if (extras3 != null){
            idFotos = extras3.getString(idFotos);
        }

        //Configuracion Global de la direccion IP del dispositivo (Conexion con BD)
        String currentValue5 = globalDireccionIp.getInstance().getGlobalDireccionIp();
        ip=currentValue5;

        Bundle extras5 = getIntent().getExtras();
        if (extras5 != null){
            ip = extras5.getString(ip);
        }

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
        backr = findViewById(R.id.fondo);


        //Cambiar el tema
        //- Claro
        if (themes.equals("1")){
            //Color de elementos
            backr.setBackgroundColor(Color.WHITE);
            registro.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            modoAdmin.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            olvidaste.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            iniciarSesionButton.setTextColor(Color.WHITE);
            olvidaste.setTextColor(Color.BLACK);
            textoCorreo.setTextColor(Color.BLACK);
            textoContrasena.setTextColor(Color.BLACK);
            textoCorreo.setHintTextColor(Color.GRAY);
            textoContrasena.setHintTextColor(Color.GRAY);

            //Color de barra de estado y de desplazamiento
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.white));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.white));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                View decorView = getWindow().getDecorView();
                decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }

        }
        //- Oscuro
        if (themes.equals("2")){
            //Color de elementos
            backr.setBackgroundColor(Color.BLACK);
            registro.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
            modoAdmin.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
            olvidaste.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
            iniciarSesionButton.setTextColor(Color.WHITE);
            olvidaste.setTextColor(Color.WHITE);
            textoCorreo.setTextColor(Color.WHITE);
            textoContrasena.setTextColor(Color.WHITE);
            textoCorreo.setHintTextColor(Color.GRAY);
            textoContrasena.setHintTextColor(Color.GRAY);

            //Color de barra de estado y de desplazamiento
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.black));
        }

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

        String URL = "http://"+ip+":8080/handtalker/iniciarSesion.php?correo="+correoT+"&contrasena="+contrasenaT;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String correoQ,contrasenaQ,nombreQ,idQ,idF;
                        try {
                            correoQ = response.getString("correo");
                            contrasenaQ = response.getString("contrasena");
                            nombreQ = response.getString("nombre");
                            idQ = response.getString("idusuario");
                            idF = response.getString("idFoto");

                            if (correoT.equals(correoQ) && contrasenaT.equals(contrasenaQ)){
                                idFotos = idF;
                                globalFoto.getInstance().setGlobalFoto(idFotos);
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

