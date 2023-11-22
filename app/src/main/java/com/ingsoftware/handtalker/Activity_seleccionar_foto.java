package com.ingsoftware.handtalker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Activity_seleccionar_foto extends AppCompatActivity {
    private RelativeLayout backr;
    private RelativeLayout barraTop;
    private ImageView atras;
    private ImageView def1;
    private ImageView def2;
    private ImageView def3;
    private ImageView def4;
    private ImageView def5;
    private ImageView def6;
    private ImageView def7;
    private ImageView def8;

    String themes;
    String id;
    String idFotos;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_foto);

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

        // Cambiar el color de la barra de estado
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.azulInicio));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.azulInicio));
        }

        backr = findViewById(R.id.fondo);
        barraTop = findViewById(R.id.topBar);
        atras = findViewById(R.id.atras_edit);
        def1 = findViewById(R.id.defecto1);
        def2 = findViewById(R.id.defecto2);
        def3 = findViewById(R.id.defecto3);
        def4 = findViewById(R.id.defecto4);
        def5 = findViewById(R.id.defecto5);
        def6 = findViewById(R.id.defecto6);
        def7 = findViewById(R.id.defecto7);
        def8 = findViewById(R.id.defecto8);

        //Cambiar el tema
        //- Claro
        if (themes.equals("1")){
            //Color de elementos
            backr.setBackgroundColor(Color.WHITE);
            barraTop.setBackgroundColor(ContextCompat.getColor(this, R.color.azulInicio));

            //Color de barra de estado y de desplazamiento
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.azulInicio));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.white));
        }

        //- Oscuro
        if (themes.equals("2")){
            //Color de elementos
            backr.setBackgroundColor(Color.BLACK);
            barraTop.setBackgroundColor(ContextCompat.getColor(this, R.color.grisInterfaz));

            //Color de barra de estado y de desplazamiento
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.grisInterfaz));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.black));
        }

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirEdit();
            }
        });

        def1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idFotos = "1";
                globalFoto.getInstance().setGlobalFoto(idFotos);
                actualiza();
            }
        });

        def2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idFotos = "2";
                globalFoto.getInstance().setGlobalFoto(idFotos);
                actualiza();
            }
        });

        def3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idFotos = "3";
                globalFoto.getInstance().setGlobalFoto(idFotos);
                actualiza();
            }
        });

        def4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idFotos = "4";
                globalFoto.getInstance().setGlobalFoto(idFotos);
                actualiza();
            }
        });

        def5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idFotos = "5";
                globalFoto.getInstance().setGlobalFoto(idFotos);
                actualiza();
            }
        });

        def6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idFotos = "6";
                globalFoto.getInstance().setGlobalFoto(idFotos);
                actualiza();
            }
        });

        def7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idFotos = "7";
                globalFoto.getInstance().setGlobalFoto(idFotos);
                actualiza();
            }
        });

        def8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idFotos = "8";
                globalFoto.getInstance().setGlobalFoto(idFotos);
                actualiza();
            }
        });

    }

    private void actualiza(){
        Toast.makeText(Activity_seleccionar_foto.this,"Foto de perfil actualizada.", Toast.LENGTH_SHORT).show();
        abrirEdit();
    }

    private void abrirEdit() {
        Intent intent = new Intent(Activity_seleccionar_foto.this, Activity_editar_perfil.class);
        startActivity(intent);
    }
}