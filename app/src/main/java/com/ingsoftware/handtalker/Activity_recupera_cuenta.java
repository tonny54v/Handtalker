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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.ingsoftware.handtalker.R;

public class Activity_recupera_cuenta extends AppCompatActivity {

    private ImageView atrasB;
    private EditText correos;
    private Button recupera;
    private TextView textIngre;
    private TextView textComprue;
    private RelativeLayout backr;
    String themes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recupera_cuenta);

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

        backr = findViewById(R.id.fondo);
        correos = findViewById(R.id.correoEditText);
        atrasB = findViewById(R.id.atrasButton);
        recupera = findViewById(R.id.recuperarCuenta);
        textIngre = findViewById(R.id.textIngresa);
        textComprue = findViewById(R.id.textComprueba);

        //Cambiar el tema
        //- Claro
        if (themes.equals("1")){
            //Color de elementos
            backr.setBackgroundColor(Color.WHITE);
            textIngre.setTextColor(Color.BLACK);
            textComprue.setTextColor(Color.BLACK);
            recupera.setTextColor(Color.WHITE);
            correos.setHintTextColor(Color.GRAY);
            correos.setTextColor(Color.BLACK);

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
            textIngre.setTextColor(Color.WHITE);
            textComprue.setTextColor(Color.WHITE);
            recupera.setTextColor(Color.WHITE);
            correos.setHintTextColor(Color.GRAY);
            correos.setTextColor(Color.WHITE);

            //Color de barra de estado y de desplazamiento
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.black));
        }

        recupera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirApp();
            }
        });

        atrasB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirApp();
            }
        });
    }

    private void abrirApp() {
        Intent intent = new Intent(Activity_recupera_cuenta.this, LoginActivity.class);
        startActivity(intent);
    }
}