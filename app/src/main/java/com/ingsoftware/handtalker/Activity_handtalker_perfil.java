package com.ingsoftware.handtalker;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.ingsoftware.handtalker.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handtalker_perfil);

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
}