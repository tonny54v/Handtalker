package com.ingsoftware.handtalker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.ingsoftware.handtalker.R;

public class LoginActivity extends AppCompatActivity {

    private static final int TIEMPO_ENTRE_PULSACIONES = 2000; // 2 segundos
    private long tiempoUltimaPulsacion;
    private Toast toast;

    private Button olvidaste;
    private Button registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

        iniciarSesionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirVentanaInicio();
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
}

