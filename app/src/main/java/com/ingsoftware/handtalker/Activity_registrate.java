package com.ingsoftware.handtalker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.ingsoftware.handtalker.R;

public class Activity_registrate extends AppCompatActivity {

    private Button registrarte;
    private ImageView cerrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrate);

        cerrar = findViewById(R.id.atrasx);
        registrarte = findViewById(R.id.botonRegistra);

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirLogin();
            }
        });

        registrarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirLogin();
            }
        });

    }

    private void abrirLogin() {
        Intent intent = new Intent(Activity_registrate.this, LoginActivity.class);
        startActivity(intent);
    }
}