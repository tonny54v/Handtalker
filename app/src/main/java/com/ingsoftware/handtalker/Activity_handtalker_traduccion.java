package com.ingsoftware.handtalker;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.ingsoftware.handtalker.R;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity_handtalker_traduccion extends AppCompatActivity {

    private static final int TIEMPO_ENTRE_PULSACIONES = 2000; // 2 segundos
    private long tiempoUltimaPulsacion;
    private Toast toast;

    private ImageView home1;
    private ImageView flechas;
    private ImageView traduccion1;
    private ImageView camara1;
    private ImageView perfil1;
    private ImageView config;
    private ImageView derechaFlecha;
    private ImageView izquierdaFlecha;
    private Map<String, Integer> signLanguageMap;
    private List<String> words;
    private int currentWordIndex;
    private ImageView translationImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handtalker_traduccion);

        // Cambiar el color de la barra de estado
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.azulInicio));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.azulInicio));
        }

        // Inicializar el mapa de imágenes
        initializeSignLanguageMap();

        Button translateButton = findViewById(R.id.BotonTraduce);
        translationImage = findViewById(R.id.imageView);
        EditText userInput = findViewById(R.id.editTextUser);

        home1 = findViewById(R.id.inicio);
        flechas = findViewById(R.id.flechita2);
        traduccion1 = findViewById(R.id.traduccion);
        camara1 = findViewById(R.id.camara);
        perfil1 = findViewById(R.id.perfil);
        config = findViewById(R.id.ajuste);
        izquierdaFlecha = findViewById(R.id.flechitaIzquierda);
        derechaFlecha = findViewById(R.id.flechitaDerecha);


        //Eventos de los botones
        home1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirInicio();
            }
        });

        flechas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTraduccionPredet();
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

        translateButton.setOnClickListener(v -> {
            String inputText = userInput.getText().toString().trim().toUpperCase();
            words = Arrays.asList(inputText.split("\\s+")); // Divide la entrada del usuario en palabras
            currentWordIndex = 0; // Comienza con la primera palabra
            showCurrentWord();

            /*
            String inputText = userInput.getText().toString().trim().toUpperCase();
            if (signLanguageMap.containsKey(inputText)) { // Verifica si la letra está en el mapa
                translationImage.setImageResource(signLanguageMap.get(inputText));
            } else {
                // Si la letra no tiene una imagen correspondiente, muestra un Toast
                Toast.makeText(getApplicationContext(), "No hay traduccion para esa palabra en especifico.", Toast.LENGTH_SHORT).show();
            }
            */
        });

        derechaFlecha.setOnClickListener(v -> {
            if (currentWordIndex < words.size() - 1) {
                currentWordIndex++;
                showCurrentWord();
            }
        });

        izquierdaFlecha.setOnClickListener(v -> {
            if (currentWordIndex > 0) {
                currentWordIndex--;
                showCurrentWord();
            }
        });


    }

    private void showCurrentWord() {
        String currentWord = words.get(currentWordIndex).toUpperCase();
        if (signLanguageMap.containsKey(currentWord)) {
            translationImage.setImageResource(signLanguageMap.get(currentWord));
        } else {
            Toast.makeText(getApplicationContext(), "No hay imagen para \"" + currentWord + "\".", Toast.LENGTH_SHORT).show();
        }
    }

    private void abrirConfig() {
        Intent intent = new Intent(Activity_handtalker_traduccion.this, Activity_configuration.class);
        startActivity(intent);
    }

    private void abrirVentanaBlanco() {
        Intent intent = new Intent(Activity_handtalker_traduccion.this, VentanaBlancoActivity.class);
        startActivity(intent);
    }

    private void abrirPerfil() {
        Intent intent = new Intent(Activity_handtalker_traduccion.this, Activity_handtalker_perfil.class);
        startActivity(intent);
    }

    private void abrirInicio() {
        Intent intent = new Intent(Activity_handtalker_traduccion.this, HandTalkerMainActivity.class);
        startActivity(intent);
    }

    private void abrircamara() {
        Intent intent = new Intent(Activity_handtalker_traduccion.this, Activity_handtalker_camera.class);
        startActivity(intent);
    }

    private void abrirTraduccionPredet() {
        Intent intent = new Intent(Activity_handtalker_traduccion.this, Activity_handtalker_frases_predet.class);
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

    private void initializeSignLanguageMap() {
        signLanguageMap = new HashMap<>();
        // Abecedario
        signLanguageMap.put("A", R.drawable.sign_a);
        signLanguageMap.put("B", R.drawable.sign_b);
        signLanguageMap.put("C", R.drawable.sign_c);
        signLanguageMap.put("D", R.drawable.sign_d);
        signLanguageMap.put("E", R.drawable.sign_e);
        signLanguageMap.put("F", R.drawable.sign_f);
        signLanguageMap.put("G", R.drawable.sign_g);
        signLanguageMap.put("H", R.drawable.sign_h);
        signLanguageMap.put("I", R.drawable.sign_i);
        signLanguageMap.put("J", R.drawable.sign_j);
        signLanguageMap.put("K", R.drawable.sign_k);
        signLanguageMap.put("L", R.drawable.sign_l);
        signLanguageMap.put("M", R.drawable.sign_m);
        signLanguageMap.put("N", R.drawable.sign_n);
        signLanguageMap.put("Ñ", R.drawable.sign_enie);
        signLanguageMap.put("O", R.drawable.sign_o);
        signLanguageMap.put("P", R.drawable.sign_p);
        signLanguageMap.put("Q", R.drawable.sign_q);
        signLanguageMap.put("R", R.drawable.sign_r);
        signLanguageMap.put("S", R.drawable.sign_s);
        signLanguageMap.put("T", R.drawable.sign_t);
        signLanguageMap.put("U", R.drawable.sign_u);
        signLanguageMap.put("V", R.drawable.sign_v);
        signLanguageMap.put("W", R.drawable.sign_w);
        signLanguageMap.put("X", R.drawable.sign_x);
        signLanguageMap.put("Y", R.drawable.sign_y);
        signLanguageMap.put("Z", R.drawable.sign_z);
        // fin abecedario


    }
}