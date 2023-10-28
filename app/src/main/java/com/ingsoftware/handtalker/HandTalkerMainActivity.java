package com.ingsoftware.handtalker;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.ingsoftware.handtalker.R;

public class HandTalkerMainActivity extends AppCompatActivity {

    private ImageView imagenCentro1;
    private ImageView home1;
    private ImageView traduccion1;
    private ImageView camara1;
    private ImageView perfil1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handtalker_main);

        imagenCentro1 = findViewById(R.id.imagen_centro1);
        home1 = findViewById(R.id.inicio);
        traduccion1 = findViewById(R.id.traduccion);
        camara1 = findViewById(R.id.camara);
        perfil1 = findViewById(R.id.perfil);

        // Cambiar la imagen del centro basada en la hora
        int hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY);
        if (hour > 0 && hour <= 11) {
            imagenCentro1.setImageResource(R.drawable.goodmorning);
        } else if(hour >= 12 && hour <= 19){
            imagenCentro1.setImageResource(R.drawable.goodafternoon);
        } else if(hour >= 20 && hour < 23 ){
            imagenCentro1.setImageResource(R.drawable.goodnight);
        }else if (hour == 0){
            imagenCentro1.setImageResource(R.drawable.goodmorning);
        }

        //Eventos de los botones
        home1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirVentanaBlanco();
            }
        });

        traduccion1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirVentanaBlanco();
            }
        });

        camara1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirVentanaBlanco();
            }
        });

        perfil1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirVentanaBlanco();
            }
        });
    }

    private void abrirVentanaBlanco() {
        Intent intent = new Intent(HandTalkerMainActivity.this, VentanaBlancoActivity.class);
        startActivity(intent);
    }
}

