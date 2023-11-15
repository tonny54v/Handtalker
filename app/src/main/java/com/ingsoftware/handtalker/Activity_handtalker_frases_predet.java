package com.ingsoftware.handtalker;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ingsoftware.handtalker.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

public class Activity_handtalker_frases_predet extends AppCompatActivity {

    private static final int TIEMPO_ENTRE_PULSACIONES = 2000; // 2 segundos
    private long tiempoUltimaPulsacion;
    private Toast toast;

    private ImageView home1;
    private ImageView flechas;
    private ImageView traduccion1;
    private Button traduce;
    private ImageView camara1;
    private ImageView perfil1;
    private ImageView config;
    private ListView listita;
    String id;
    private HashMap<String, Integer> signLanguageMap;
    private String[] palabrasActuales;
    private int indicePalabraActual;
    private ImageView imagenTraduccion;
    private ImageView flechitaDerecha;
    private ImageView flechitaIzquierda;
    private Button agregar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handtalker_frases_predet);

        String currentValue = globalVariable.getInstance().getGlobalString();
        id=currentValue;

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            id = extras.getString(id);
        }

        // Cambiar el color de la barra de estado
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.azulInicio));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.azulInicio));
        }

        flechas = findViewById(R.id.fechita1);
        home1 = findViewById(R.id.inicio);
        camara1 = findViewById(R.id.camara);
        perfil1 = findViewById(R.id.perfil);
        config = findViewById(R.id.ajuste);
        listita = findViewById(R.id.frameFrases);
        flechitaDerecha = findViewById(R.id.flechitaDerecha);
        flechitaIzquierda = findViewById(R.id.flechitaIzquierda);
        agregar = findViewById(R.id.BotonAgrega1);

        initializeSignLanguageMap();
        imagenTraduccion = findViewById(R.id.imageView);

        String URL = "http://192.168.1.11:8080/handtalker/listar_frases.php?id="+id;
        recuperarDatos(URL);


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
                abrirTraduccion();
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

        flechitaDerecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {onFlechaDerechaClick(v);}
        });

        flechitaIzquierda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFlechaIzquierdaClick(v);
            }
        });

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirAgrega();
            }
        });
    }

    private void recuperarDatos(String url) {
        //Toast.makeText(getApplicationContext(), ""+URL, Toast.LENGTH_SHORT).

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("][",",");
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        CargarListView(ja);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);
    }

    private void CargarListView(JSONArray ja) {
        ArrayList<String> lista = new ArrayList<>();
        for (int i=0; i<ja.length(); i+=1){
             try {
                 lista.add(ja.getString(i));
             }catch (JSONException e){
                 e.printStackTrace();
             }
        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista);
        listita.setAdapter(adaptador);

        // Establecer un nuevo OnItemClickListener para la ListView
        listita.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String frase = parent.getItemAtPosition(position).toString();
                mostrarTraduccion(frase);
            }
        });
    }

    private void mostrarTraduccion(String frase) {
        palabrasActuales = frase.split("\\s+");
        if(palabrasActuales.length > 0) {
            indicePalabraActual = 0;
            mostrarImagenDePalabraActual();
        }
    }

    private void mostrarImagenDePalabraActual() {
        if(indicePalabraActual >= 0 && indicePalabraActual < palabrasActuales.length) {
            String palabraActual = palabrasActuales[indicePalabraActual];
            Integer imagenResId = signLanguageMap.get(palabraActual.toUpperCase()); // Asumiendo que tu HashMap se llama palabraImagenMap
            if(imagenResId != null) {
                imagenTraduccion.setImageResource(imagenResId);
            } else {
                Toast.makeText(Activity_handtalker_frases_predet.this,"No hay imagen para \"" + palabraActual + "\".", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onFlechaIzquierdaClick(View view) {
        if (indicePalabraActual > 0) {
            indicePalabraActual--;
            mostrarImagenDePalabraActual();
        }
    }

    public void onFlechaDerechaClick(View view) {
        if (palabrasActuales != null && indicePalabraActual < palabrasActuales.length - 1) {
            indicePalabraActual++;
            mostrarImagenDePalabraActual();
        }
    }

    private void abrirAgrega() {
        Intent intent = new Intent(Activity_handtalker_frases_predet.this, Activity_agregar.class);
        startActivity(intent);
    }

    private void abrirConfig() {
        Intent intent = new Intent(Activity_handtalker_frases_predet.this, Activity_configuration.class);
        startActivity(intent);
    }

    private void abrirVentanaBlanco() {
        Intent intent = new Intent(Activity_handtalker_frases_predet.this, VentanaBlancoActivity.class);
        startActivity(intent);
    }

    private void abrirPerfil() {
        Intent intent = new Intent(Activity_handtalker_frases_predet.this, Activity_handtalker_perfil.class);
        startActivity(intent);
    }

    private void abrirInicio() {
        Intent intent = new Intent(Activity_handtalker_frases_predet.this, HandTalkerMainActivity.class);
        startActivity(intent);
    }

    private void abrircamara() {
        Intent intent = new Intent(Activity_handtalker_frases_predet.this, Activity_handtalker_camera.class);
        startActivity(intent);
    }

    private void abrirTraduccion() {
        Intent intent = new Intent(Activity_handtalker_frases_predet.this, Activity_handtalker_traduccion.class);
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

        //Frases comunes



    }
}