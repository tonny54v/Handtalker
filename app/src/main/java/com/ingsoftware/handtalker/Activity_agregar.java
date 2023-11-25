package com.ingsoftware.handtalker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Activity_agregar extends AppCompatActivity {
    EditText frase;
    private Button agregar;
    private ImageView atras;
    private RelativeLayout backr;
    private RelativeLayout barraTop;
    private TextView textIns;
    private RelativeLayout marcoTexts;

    RequestQueue requestQueue;

    String id;
    String themes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_frases);

        String currentValue = globalVariable.getInstance().getGlobalString();
        id=currentValue;

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            id = extras.getString(id);
        }

        //Configuracion Global del tema
        String currentValue2 = globalTheme.getInstance().getGlobalTema();
        themes=currentValue2;

        Bundle extras2 = getIntent().getExtras();
        if (extras2 != null){
            themes = extras2.getString(themes);
        }

        // Cambiar el color de la barra de estado
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.azulInicio));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.white));
        }

        requestQueue = Volley.newRequestQueue(this);

        frase = findViewById(R.id.fraseText);
        agregar = findViewById(R.id.botonGuardar);
        atras = findViewById(R.id.atras_agrega);

        backr = findViewById(R.id.fondo);
        barraTop = findViewById(R.id.topBar);
        textIns = findViewById(R.id.textInsert);
        marcoTexts = findViewById(R.id.marcoText);

        //Cambiar el tema
        //- Claro
        if (themes.equals("1")){
            //Color de elementos
            backr.setBackgroundColor(Color.WHITE);
            barraTop.setBackgroundColor(ContextCompat.getColor(this, R.color.azulInicio));
            textIns.setTextColor(Color.BLACK);
            marcoTexts.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_border));
            frase.setTextColor(Color.BLACK);
            frase.setHintTextColor(Color.GRAY);
            agregar.setTextColor(Color.WHITE);

            //Color de barra de estado y de desplazamiento
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.azulInicio));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.white));
        }

        //- Oscuro
        if (themes.equals("2")){
            //Color de elementos
            backr.setBackgroundColor(Color.BLACK);
            barraTop.setBackgroundColor(ContextCompat.getColor(this, R.color.grisInterfaz));
            textIns.setTextColor(Color.WHITE);
            marcoTexts.setBackground(ContextCompat.getDrawable(this, R.drawable.edit_text_border_gray_black));
            frase.setTextColor(Color.WHITE);
            frase.setHintTextColor(Color.GRAY);
            agregar.setTextColor(Color.WHITE);

            //Color de barra de estado y de desplazamiento
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.grisInterfaz));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.black));
        }



        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar(v);
            }
        });

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirFrasesPred();
            }
        });


    }

    private void abrirFrasesPred() {
        Intent intent = new Intent(Activity_agregar.this, Activity_handtalker_frases_predet.class);
        startActivity(intent);
    }

    public void validar(View v){
        final String frases = frase.getText().toString();
        createFrase(frases);
    }

    private void createFrase(String frasesita) {
        String URL1 = "http://192.168.8.11:8080/handtalker/agregaFrase.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Activity_agregar.this,"Frase registrada correctamente", Toast.LENGTH_SHORT).show();
                        abrirFrasesPred();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Activity_agregar.this,"Ingresa los datos faltantes", Toast.LENGTH_SHORT).show();

                    }
                }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                if(frasesita.equals("")){
                    Toast.makeText(Activity_agregar.this,"Faltan datos", Toast.LENGTH_SHORT).show();
                }else{
                    params.put("idfrases", id);
                    params.put("descripccion", frasesita);
                }
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}