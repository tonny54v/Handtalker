package com.ingsoftware.handtalker;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private ImageView fotoPerfil;
    private RelativeLayout backr;
    private LinearLayout barraTop;
    private TextView textNoms;
    private LinearLayout marcoNom;
    private TextView textApes;
    private LinearLayout marcoApes;
    private TextView textTels;
    private LinearLayout marcoTel;
    private TextView textCorr;
    private LinearLayout marcoCorr;
    private LinearLayout barraDown;
    String id;
    String themes;
    String idFotos;
    String ip;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handtalker_perfil);

        requestQueue = Volley.newRequestQueue(this);

        String currentValue = globalVariable.getInstance().getGlobalString();
        id=currentValue;

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            id = extras.getString(id);
        }

        //Configuracion Global del tema
        String currentValue2 = globalTheme.getInstance().getGlobalTema();
        themes=currentValue2;

        Bundle extras2 = getIntent().getExtras();
        if (extras2 != null){
            themes = extras2.getString(themes);
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
        fotoPerfil = findViewById(R.id.foto);

        backr = findViewById(R.id.fondo);
        barraTop = findViewById(R.id.topBar);
        textNoms = findViewById(R.id.textName);
        marcoNom = findViewById(R.id.name);
        textApes = findViewById(R.id.textApe);
        marcoApes = findViewById(R.id.apellido);
        textTels = findViewById(R.id.textTel);
        marcoTel = findViewById(R.id.telefono);
        textCorr = findViewById(R.id.textCorre);
        marcoCorr = findViewById(R.id.correo);
        barraDown = findViewById(R.id.downBar);

        //Cambiar el tema
        //- Claro
        if (themes.equals("1")){
            //Color de elementos
            backr.setBackgroundColor(Color.WHITE);
            barraTop.setBackgroundColor(ContextCompat.getColor(this, R.color.azulInicio));
            barraDown.setBackgroundColor(ContextCompat.getColor(this, R.color.azulInicio));
            textNoms.setTextColor(Color.BLACK);
            textApes.setTextColor(Color.BLACK);
            textTels.setTextColor(Color.BLACK);
            textCorr.setTextColor(Color.BLACK);
            marcoNom.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_border));
            marcoApes.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_border));
            marcoTel.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_border));
            marcoCorr.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_border));
            etname.setTextColor(Color.BLACK);
            etApellido.setTextColor(Color.BLACK);
            etCorreos.setTextColor(Color.BLACK);
            etTelefono.setTextColor(Color.BLACK);

            //Color de barra de estado y de desplazamiento
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.azulInicio));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.azulInicio));
        }

        //- Oscuro
        if (themes.equals("2")){
            //Color de elementos
            backr.setBackgroundColor(Color.BLACK);
            barraTop.setBackgroundColor(ContextCompat.getColor(this, R.color.grisInterfaz));
            barraDown.setBackgroundColor(ContextCompat.getColor(this, R.color.grisInterfaz));
            textNoms.setTextColor(Color.WHITE);
            textApes.setTextColor(Color.WHITE);
            textTels.setTextColor(Color.WHITE);
            textCorr.setTextColor(Color.WHITE);
            marcoNom.setBackground(ContextCompat.getDrawable(this, R.drawable.edit_text_border_gray_black));
            marcoApes.setBackground(ContextCompat.getDrawable(this, R.drawable.edit_text_border_gray_black));
            marcoTel.setBackground(ContextCompat.getDrawable(this, R.drawable.edit_text_border_gray_black));
            marcoCorr.setBackground(ContextCompat.getDrawable(this, R.drawable.edit_text_border_gray_black));
            etname.setTextColor(Color.WHITE);
            etApellido.setTextColor(Color.WHITE);
            etCorreos.setTextColor(Color.WHITE);
            etTelefono.setTextColor(Color.WHITE);

            //Color de barra de estado y de desplazamiento
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.grisInterfaz));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.grisInterfaz));
        }

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
                abrirEditar();
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

    private void abrirEditar() {
        Intent intent = new Intent(Activity_handtalker_perfil.this, Activity_editar_perfil.class);
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
        String URL = "http://"+ip+":8080/handtalker/fetch.php?id="+id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String nombre, apellido, telefono, correo, idFoto;
                        try {
                            nombre = response.getString("nombre");
                            apellido = response.getString("apellido");
                            telefono = response.getString("telefono");
                            correo = response.getString("correo");
                            idFoto = idFotos;

                            etname.setText(nombre);
                            etApellido.setText(apellido);
                            etTelefono.setText(telefono);
                            etCorreos.setText(correo);

                            //Selecciona la foto de perfil de acuerdo al idFoto
                            if (idFoto.equals("1")){
                                fotoPerfil.setImageResource(R.drawable.perfilicon);
                            }
                            if (idFoto.equals("2")){
                                fotoPerfil.setImageResource(R.drawable.foto_defecto2);
                            }
                            if (idFoto.equals("3")){
                                fotoPerfil.setImageResource(R.drawable.foto_defecto3);
                            }
                            if (idFoto.equals("4")){
                                fotoPerfil.setImageResource(R.drawable.foto_defecto4);
                            }
                            if (idFoto.equals("5")){
                                fotoPerfil.setImageResource(R.drawable.foto_defecto5);
                            }
                            if (idFoto.equals("6")){
                                fotoPerfil.setImageResource(R.drawable.foto_defecto6);
                            }
                            if (idFoto.equals("7")){
                                fotoPerfil.setImageResource(R.drawable.foto_defecto7);
                            }
                            if (idFoto.equals("8")){
                                fotoPerfil.setImageResource(R.drawable.foto_edicion_taylor);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        fotoPerfil.setImageResource(R.drawable.perfilicon);
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }
}