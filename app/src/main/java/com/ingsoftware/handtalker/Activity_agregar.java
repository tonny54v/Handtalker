package com.ingsoftware.handtalker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Activity_agregar extends AppCompatActivity {
    EditText frase;
    private Button agregar;
    private static final String URL1 = "http://10.31.11.132:8080/handtalker/agregaFrase.php";

    RequestQueue requestQueue;

    String id;

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

        frase = findViewById(R.id.fraseText);
        agregar = findViewById(R.id.botonGuardar);

        // Cambiar el color de la barra de estado
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.azulInicio));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.white));
        }

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar(v);
            }
        });


    }

    private void abrirFrasesPred() {
        Intent intent = new Intent(Activity_agregar.this, Activity_handtalker_frases_predet.class);
        startActivity(intent);
    }

    public void validar(View v){
        final String frasesita = frase.getText().toString();
        createFrase(frasesita);
    }

    private void createFrase(String frasesita) {
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