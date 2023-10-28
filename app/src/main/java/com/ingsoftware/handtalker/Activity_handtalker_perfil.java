package com.ingsoftware.handtalker;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.ingsoftware.handtalker.R;

public class Activity_handtalker_perfil extends AppCompatActivity {

    private ImageView home1;
    private ImageView traduccion1;
    private ImageView camara1;
    private ImageView perfil1;
    private ImageView config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handtalker_perfil);

        home1 = findViewById(R.id.inicio);
        traduccion1 = findViewById(R.id.traduccion);
        camara1 = findViewById(R.id.camara);
        perfil1 = findViewById(R.id.perfil);
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

        config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirVentanaBlanco();
            }
        });
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
}