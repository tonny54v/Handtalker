package com.ingsoftware.handtalker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.ingsoftware.handtalker.R;

public class LoginActivity extends AppCompatActivity {

    private Button olvidaste;
    private Button registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button iniciarSesionButton = findViewById(R.id.iniciarSesionButton);
        olvidaste = findViewById(R.id.olvidasteText);
        registro = findViewById(R.id.registroText);

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
                abrirVentanaBlanco();
            }
        });
    }

    //Permite hacer pruebas con los botones
    private void abrirVentanaBlanco() {
        Intent intent = new Intent(LoginActivity.this, VentanaBlancoActivity.class);
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
}

