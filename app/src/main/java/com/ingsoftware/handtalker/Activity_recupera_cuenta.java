package com.ingsoftware.handtalker;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.ingsoftware.handtalker.R;

public class Activity_recupera_cuenta extends AppCompatActivity {

    private ImageView atrasB;
    private Button recupera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recupera_cuenta);

        atrasB = findViewById(R.id.atrasButton);
        recupera = findViewById(R.id.recuperarCuenta);

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