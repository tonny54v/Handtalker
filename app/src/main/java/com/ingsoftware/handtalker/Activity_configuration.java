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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.ingsoftware.handtalker.R;

public class Activity_configuration extends AppCompatActivity {

    private ImageView atrasBoton;
    private LinearLayout cerrarSesion;
    private RelativeLayout backr;
    private LinearLayout barraTop;
    private LinearLayout marcoTamFuente;
    private  ImageView tamImg;
    private TextView textTam;
    private LinearLayout marcoTema;
    private  ImageView temaImg;
    private TextView textTemas;
    private LinearLayout marcoTamGraf;
    private TextView textgraf;
    private  ImageView cerrImg;
    private TextView textCerr;
    String themes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        //Configuracion Global del tema
        String currentValue = globalTheme.getInstance().getGlobalTema();
        themes=currentValue;

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            themes = extras.getString(themes);
        }

        // Cambiar el color de la barra de estado
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.azulInicio));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.white));
        }

        atrasBoton = findViewById(R.id.atras);
        cerrarSesion = findViewById(R.id.cerrarSesion);
        backr = findViewById(R.id.fondo);
        barraTop = findViewById(R.id.topBar);
        marcoTamFuente = findViewById(R.id.tamanofuente);
        tamImg = findViewById(R.id.img_tamano_text);
        textTam = findViewById(R.id.tamText);
        marcoTema = findViewById(R.id.tema);
        temaImg = findViewById(R.id.temaimg);
        textTemas = findViewById(R.id.textTema);
        marcoTamGraf = findViewById(R.id.tamanografico);
        textgraf = findViewById(R.id.textGraf);
        cerrImg = findViewById(R.id.cerrarImg);
        textCerr = findViewById(R.id.textCierra);

        //Cambiar el tema
        //- Claro
        if (themes.equals("1")){
            //Color de elementos
            backr.setBackgroundColor(Color.WHITE);
            barraTop.setBackgroundColor(ContextCompat.getColor(this, R.color.azulInicio));
            tamImg.setImageResource(R.drawable.tamanotexto);
            temaImg.setImageResource(R.drawable.temaimg);
            cerrImg.setImageResource(R.drawable.cerrarses);
            textTam.setTextColor(Color.BLACK);
            textTemas.setTextColor(Color.BLACK);
            textgraf.setTextColor(Color.BLACK);
            textCerr.setTextColor(Color.BLACK);


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
            tamImg.setImageResource(R.drawable.tamanotexto_white);
            temaImg.setImageResource(R.drawable.temaimg_white);
            cerrImg.setImageResource(R.drawable.cerrarses_white);
            textTam.setTextColor(Color.WHITE);
            textTemas.setTextColor(Color.WHITE);
            textgraf.setTextColor(Color.WHITE);
            textCerr.setTextColor(Color.WHITE);

            //Color de barra de estado y de desplazamiento
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.grisInterfaz));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.black));
        }

        atrasBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regresarInicio();
            }
        });

        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regresarLogin();
            }
        });
    }

    private void regresarInicio() {
        Intent intent = new Intent(Activity_configuration.this, HandTalkerMainActivity.class);
        startActivity(intent);
    }

    private void regresarLogin() {
        Intent intent = new Intent(Activity_configuration.this, LoginActivity.class);
        startActivity(intent);
    }


}