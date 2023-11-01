package com.ingsoftware.handtalker;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.ingsoftware.handtalker.R;

public class HandTalkerMainActivity extends AppCompatActivity {

    private static final int TIEMPO_ENTRE_PULSACIONES = 2000; // 2 segundos
    private long tiempoUltimaPulsacion;
    private Toast toast;

    private ImageView imagenCentro1;
    private ImageView home1;
    private ImageView traduccion1;
    private ImageView camara1;
    private ImageView perfil1;
    private ImageView config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handtalker_main);

        // Cambiar el color de la barra de estado
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.azulInicio));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.azulInicio));
        }

        imagenCentro1 = findViewById(R.id.imagen_centro1);
        home1 = findViewById(R.id.inicio);
        traduccion1 = findViewById(R.id.traduccion);
        camara1 = findViewById(R.id.camara);
        perfil1 = findViewById(R.id.perfil);
        config = findViewById(R.id.ajuste);

        // Cambiar la imagen del centro basada en la hora
        int hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY);
        if (hour > 0 && hour <= 11) {
            imagenCentro1.setImageResource(R.drawable.goodmorning);
        } else if(hour >= 12 && hour <= 18){
            imagenCentro1.setImageResource(R.drawable.goodafternoon);
        } else if(hour >= 19 && hour < 23 ){
            imagenCentro1.setImageResource(R.drawable.goodnight);
        }else if (hour == 0){
            imagenCentro1.setImageResource(R.drawable.goodmorning);
        }

        //Eventos de los botones
        traduccion1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTraduccion();
            }
        });

        camara1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrircamara();
            }
        });

        perfil1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirPerfil();
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
        Intent intent = new Intent(HandTalkerMainActivity.this, Activity_configuration.class);
        startActivity(intent);
    }

    private void abrirVentanaBlanco() {
        Intent intent = new Intent(HandTalkerMainActivity.this, VentanaBlancoActivity.class);
        startActivity(intent);
    }

    private void abrirPerfil() {
        Intent intent = new Intent(HandTalkerMainActivity.this, Activity_handtalker_perfil.class);
        startActivity(intent);
    }

    private void abrirTraduccion() {
        Intent intent = new Intent(HandTalkerMainActivity.this, Activity_handtalker_traduccion.class);
        startActivity(intent);
    }

    private void abrircamara() {
        Intent intent = new Intent(HandTalkerMainActivity.this, Activity_handtalker_camera.class);
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
}

