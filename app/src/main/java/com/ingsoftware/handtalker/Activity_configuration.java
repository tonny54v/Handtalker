package com.ingsoftware.handtalker;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.ingsoftware.handtalker.R;

public class Activity_configuration extends AppCompatActivity {

    private ImageView atrasBoton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        atrasBoton = findViewById(R.id.atras);

        atrasBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regresarInicio();
            }
        });
    }

    private void regresarInicio() {
        Intent intent = new Intent(Activity_configuration.this, HandTalkerMainActivity.class);
        startActivity(intent);
    }
}