package com.ingsoftware.handtalker;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
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
import java.util.Map;

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
    private ImageView guiaR;
    String id;
    String themes;
    String ip;
    String tamGrafico;
    String mensaje;
    private HashMap<String, Integer> signLanguageMap;
    private String[] palabrasActuales;
    private int indicePalabraActual;
    private ImageView imagenTraduccion;
    private ImageView flechitaDerecha;
    private ImageView flechitaIzquierda;
    private Button agregar;
    private RelativeLayout backr;
    private LinearLayout barraTop;
    private TextView frasePred;
    private TextView traducec;
    private RelativeLayout resultadoCuad;
    private LinearLayout barraDown;
    // Variables para manejar la traducción letra por letra
    private boolean isTranslatingByLetter = false;
    private int currentLetterIndex = 0;
    // Asumiendo que tienes un arreglo para las letras de la palabra actual
    private String[] letrasActuales;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handtalker_frases_predet);

        requestQueue = Volley.newRequestQueue(this);

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

        //Configuracion Global del grafico
        String currentValue4 = globalGrafico.getInstance().getGlobalTamanoG();
        tamGrafico=currentValue4;

        Bundle extras4 = getIntent().getExtras();
        if (extras4 != null){
            tamGrafico = extras4.getString(tamGrafico);
        }

        //Configuracion Global de la direccion IP del dispositivo (Conexion con BD)
        String currentValue5 = globalDireccionIp.getInstance().getGlobalDireccionIp();
        ip=currentValue5;

        Bundle extras5 = getIntent().getExtras();
        if (extras5 != null){
            ip = extras5.getString(ip);
        }

        //Configuracion de aparicion de mensaje de inicio (Guia)
        String currentValue6 = globalMensaje.getInstance().getGlobalMensajeFrases();
        mensaje=currentValue6;

        Bundle extras6 = getIntent().getExtras();
        if (extras6 != null){
            mensaje = extras6.getString(mensaje);
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
        guiaR = findViewById(R.id.guiaRapida);

        backr = findViewById(R.id.fondo);
        barraTop = findViewById(R.id.topBar);
        frasePred = findViewById(R.id.TextFrase);
        traducec = findViewById(R.id.textTraducci);
        resultadoCuad = findViewById(R.id.cuadroResultado);
        barraDown = findViewById(R.id.downBar);

        //Muestra el mensaje una sola vez al abrir la app
        if (mensaje.equals("1")){
            abrirInfoGuia();
            //Cambia el valor para no volver a mostrarlo en una sola sesion
            mensaje = "2";
            globalMensaje.getInstance().setGlobalMensajeFrases(mensaje);
        }

        //Cambiar el tema
        //- Claro
        if (themes.equals("1")){
            //Color de elementos
            backr.setBackgroundColor(Color.WHITE);
            barraTop.setBackgroundColor(ContextCompat.getColor(this, R.color.azulInicio));
            barraDown.setBackgroundColor(ContextCompat.getColor(this, R.color.azulInicio));
            frasePred.setTextColor(Color.BLACK);
            traducec.setTextColor(Color.BLACK);
            agregar.setTextColor(Color.WHITE);
            listita.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_border));
            resultadoCuad.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_border));
            flechitaIzquierda.setImageResource(R.drawable.flecha_izquierda);
            flechitaDerecha.setImageResource(R.drawable.flecha_derecha);
            flechas.setImageResource(R.drawable.flechas_logo);
            guiaR.setImageResource(R.drawable.guia_rapida);

            //Color de barra de estado y de desplazamiento
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.azulInicio));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.azulInicio));
        }

        //- Oscuro
        if (themes.equals("2")){
            //Color de elementos
            backr.setBackgroundColor(Color.BLACK);
            barraTop.setBackgroundColor(ContextCompat.getColor(this, R.color.grisInterfaz));
            barraDown.setBackgroundColor(ContextCompat.getColor(this, R.color.grisInterfaz));
            frasePred.setTextColor(Color.WHITE);
            traducec.setTextColor(Color.WHITE);
            agregar.setTextColor(Color.WHITE);
            listita.setBackground(ContextCompat.getDrawable(this, R.drawable.edit_text_border_gray_black));
            resultadoCuad.setBackground(ContextCompat.getDrawable(this, R.drawable.edit_text_border_gray_black));
            flechitaIzquierda.setImageResource(R.drawable.flecha_izquierda_white);
            flechitaDerecha.setImageResource(R.drawable.flecha_derecha_white);
            flechas.setImageResource(R.drawable.flechas_logo_white);
            guiaR.setImageResource(R.drawable.guia_rapida_blanco);

            //Color de barra de estado y de desplazamiento
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.grisInterfaz));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.grisInterfaz));
        }
        initializeSignLanguageMap();
        imagenTraduccion = findViewById(R.id.imageView);

        //Configuracion de tamaño de foto de traduccion
        //Pequeno
        if(tamGrafico.equals("1")){
            ViewGroup.LayoutParams params = imagenTraduccion.getLayoutParams();
            params.width = 401; // Nueva anchura
            params.height = 436; // Nueva altura
            imagenTraduccion.setLayoutParams(params);
        }
        //Mediano
        if(tamGrafico.equals("2")){
            ViewGroup.LayoutParams params = imagenTraduccion.getLayoutParams();
            params.width = 512; // Nueva anchura
            params.height = 558; // Nueva altura
            imagenTraduccion.setLayoutParams(params);
        }
        //Grande
        if(tamGrafico.equals("3")){
            ViewGroup.LayoutParams params = imagenTraduccion.getLayoutParams();
            params.width = 650; // Nueva anchura
            params.height = 705; // Nueva altura
            imagenTraduccion.setLayoutParams(params);
        }

        String URL = "http://"+ip+":8080/handtalker/listar_frases.php?id="+id;
        recuperarDatos(URL);


        //Eventos de los botones
        home1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirInicio();
            }
        });

        guiaR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (themes.equals("1")){
                    // Crear el constructor del AlertDialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_handtalker_frases_predet.this, R.style.AlertDialogCustom);

                    // Configurar el mensaje y el botón del AlertDialog
                    builder.setTitle("¿Cómo funciona? (Beta)");
                    builder.setMessage("Para ahorrarte tiempo en algún caso de urgencia " +
                                    "agrega tus frases de uso rápido oprimiendo el botón agregar, " +
                                    "dentro del cuál se abrirá una ventana en donde introducirás tu frase, " +
                                    "una vez realizada oprime guardar y se te regresara a la pantalla de " +
                                    "traducción. \n\n" +
                                    "Oprime la frase que desees traducir y el sistema realizara la traduccion de " +
                                    "cada una de las palabras. \n\n" +
                                    "Utiliza los botones de desplazamiento para ver la traduccion de la palabra. \n\n" +
                                    "En algun caso que la palabra que ingreses no tenga traduccion, se traducira letra " +
                                    "por letra (Caso de los nombres). \n\n" +
                                    "Presiona el icono de las flechas para cambiar al modo Ingresar Texto. \n\n" +
                                    "Mantén presionada una frase para eliminarla de tu lista. \n\n" +
                                    "NOTA: No ingreses simbolos o caracteres especiales al momento de agregar la frase.")
                            .setPositiveButton("¡Lo tengo!", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Si se presiona el botón, simplemente cierra el diálogo
                                    dialog.dismiss();
                                }
                            });

                    // Crear y mostrar el AlertDialog
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    // Personalización de colores después de mostrar el diálogo
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.azulInicio));

                    // Para el título y el mensaje, tendrías que usar un TextView personalizado o buscar el TextView por defecto y cambiarle el color.
                    TextView messageView = (TextView) dialog.findViewById(android.R.id.message);
                    if (messageView != null) {
                        messageView.setTextColor(getResources().getColor(R.color.black));
                    }

                    TextView titleView = (TextView) dialog.findViewById(android.R.id.title);
                    if (titleView != null) {
                        titleView.setTextColor(getResources().getColor(R.color.black));
                    }
                }

                if (themes.equals("2")){
                    // Crear el constructor del AlertDialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_handtalker_frases_predet.this, R.style.AlertDialogCustom_black);

                    // Configurar el mensaje y el botón del AlertDialog
                    builder.setTitle("¿Como funciona? (Beta)");
                    builder.setMessage("Para ahorrarte tiempo en algún caso de urgencia " +
                                    "agrega tus frases de uso rápido oprimiendo el botón agregar, " +
                                    "dentro del cuál se abrirá una ventana en donde introducirás tu frase, " +
                                    "una vez realizada oprime guardar y se te regresara a la pantalla de " +
                                    "traducción. \n\n" +
                                    "Oprime la frase que desees traducir y el sistema realizara la traduccion de " +
                                    "cada una de las palabras. \n\n" +
                                    "Utiliza los botones de desplazamiento para ver la traduccion de la palabra. \n\n" +
                                    "En algun caso que la palabra que ingreses no tenga traduccion, se traducira letra " +
                                    "por letra (Caso de los nombres). \n\n" +
                                    "Presiona el icono de las flechas para cambiar al modo Ingresar Texto. \n\n" +
                                    "Mantén presionada una frase para eliminarla de tu lista. \n\n" +
                                    "NOTA: No ingreses simbolos o caracteres especiales al momento de agregar la frase.")
                            .setPositiveButton("¡Lo tengo!", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // Si se presiona el botón, simplemente cierra el diálogo
                                    dialog.dismiss();
                                }
                            });

                    // Crear y mostrar el AlertDialog
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    // Personalización de colores después de mostrar el diálogo
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.azulInicio));

                    // Para el título y el mensaje, tendrías que usar un TextView personalizado o buscar el TextView por defecto y cambiarle el color.
                    TextView messageView = (TextView) dialog.findViewById(android.R.id.message);
                    if (messageView != null) {
                        messageView.setTextColor(getResources().getColor(R.color.white));
                    }

                    TextView titleView = (TextView) dialog.findViewById(android.R.id.title);
                    if (titleView != null) {
                        titleView.setTextColor(getResources().getColor(R.color.white));
                    }
                }

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

        listita.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String frase = parent.getItemAtPosition(position).toString();

                if(themes.equals("1")){
                    // Crear el constructor del AlertDialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_handtalker_frases_predet.this, R.style.AlertDialogCustom);

                    // Configurar el mensaje y los botones del AlertDialog
                    builder.setTitle("Eliminar frase");
                    builder.setMessage("¿Estás seguro de que quieres eliminar \""+ frase +"\" de la lista de frases?");

                    // Botón para confirmar la eliminación
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            eliminaFras(frase); // Llamar al método para eliminar la frase
                            dialog.dismiss();
                        }
                    });

                    // Botón para cancelar la acción
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss(); // Simplemente cierra el diálogo
                        }
                    });

                    // Crear y mostrar el AlertDialog
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    // Personalización de colores después de mostrar el diálogo
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.azulInicio));
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.azulInicio));

                    // Para el título y el mensaje, tendrías que usar un TextView personalizado o buscar el TextView por defecto y cambiarle el color.
                    TextView messageView = (TextView) dialog.findViewById(android.R.id.message);
                    if (messageView != null) {
                        messageView.setTextColor(getResources().getColor(R.color.black));
                    }

                    TextView titleView = (TextView) dialog.findViewById(android.R.id.title);
                    if (titleView != null) {
                        titleView.setTextColor(getResources().getColor(R.color.black));
                    }
                }

                if(themes.equals("2")){
                    // Crear el constructor del AlertDialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_handtalker_frases_predet.this, R.style.AlertDialogCustom_black);

                    // Configurar el mensaje y los botones del AlertDialog
                    builder.setTitle("Eliminar frase");
                    builder.setMessage("¿Estás seguro de que quieres eliminar \""+ frase +"\" de la lista de frases?");

                    // Botón para confirmar la eliminación
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            eliminaFras(frase); // Llamar al método para eliminar la frase
                            dialog.dismiss();
                        }
                    });

                    // Botón para cancelar la acción
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss(); // Simplemente cierra el diálogo
                        }
                    });

                    // Crear y mostrar el AlertDialog
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    // Personalización de colores después de mostrar el diálogo
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.azulInicio));
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.azulInicio));

                    // Para el título y el mensaje, tendrías que usar un TextView personalizado o buscar el TextView por defecto y cambiarle el color.
                    TextView messageView = (TextView) dialog.findViewById(android.R.id.message);
                    if (messageView != null) {
                        messageView.setTextColor(getResources().getColor(R.color.white));
                    }

                    TextView titleView = (TextView) dialog.findViewById(android.R.id.title);
                    if (titleView != null) {
                        titleView.setTextColor(getResources().getColor(R.color.white));
                    }
                }


                return true;
            }
        });
    }

    private void eliminaFras(String descr) {
        String URL1 = "http://"+ip+":8080/handtalker/eliminarFrase.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Activity_handtalker_frases_predet.this,"Se elimino la frase correctamente "+descr, Toast.LENGTH_SHORT).show();
                        abrirFrasesPred();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Activity_handtalker_frases_predet.this,"¡Hubo un error inesperado!", Toast.LENGTH_SHORT).show();

                    }
                }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                if(id.equals("") || descr.equals("")){
                    Toast.makeText(Activity_handtalker_frases_predet.this,"Faltan datos", Toast.LENGTH_SHORT).show();
                }else{
                    params.put("id",id);
                    params.put("descripccion", descr);
                }
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void abrirFrasesPred() {
        Intent intent = new Intent(Activity_handtalker_frases_predet.this, Activity_handtalker_frases_predet.class);
        startActivity(intent);
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
                isTranslatingByLetter = false;
            } else {
                // Palabra no encontrada, cambiar a traducción por letras
                letrasActuales = palabraActual.split("");
                isTranslatingByLetter = true;
                currentLetterIndex = 0;
                mostrarImagenDeLetraActual();
            }
        }
    }

    private void mostrarImagenDeLetraActual() {
        if(currentLetterIndex >= 0 && currentLetterIndex < letrasActuales.length) {
            String letraActual = letrasActuales[currentLetterIndex];
            Integer imagenResId = signLanguageMap.get(letraActual.toUpperCase());
            if(imagenResId != null) {
                imagenTraduccion.setImageResource(imagenResId);
            } else {
                Toast.makeText(getApplicationContext(), "No hay imagen para \"" + letraActual + "\".", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onFlechaIzquierdaClick(View view) {
        try {
            if(isTranslatingByLetter) {
                if(currentLetterIndex > 0) {
                    currentLetterIndex--;
                    mostrarImagenDeLetraActual();
                }else{
                    if (indicePalabraActual > 0) {
                        indicePalabraActual--;
                        mostrarImagenDePalabraActual();
                    }
                }
            } else {
                if (indicePalabraActual > 0) {
                    indicePalabraActual--;
                    mostrarImagenDePalabraActual();
                }
            }

        }catch (Exception e){
            Toast.makeText(Activity_handtalker_frases_predet.this,"No hay imagenes para desplazar.", Toast.LENGTH_SHORT).show();
        }

    }

    public void onFlechaDerechaClick(View view) {
        try {
            if(isTranslatingByLetter) {
                if(currentLetterIndex < letrasActuales.length - 1) {
                    currentLetterIndex++;
                    mostrarImagenDeLetraActual();
                }else{
                    if (palabrasActuales != null && indicePalabraActual < palabrasActuales.length - 1) {
                        indicePalabraActual++;
                        mostrarImagenDePalabraActual();
                    }
                }
            } else {
                if (palabrasActuales != null && indicePalabraActual < palabrasActuales.length - 1) {
                    indicePalabraActual++;
                    mostrarImagenDePalabraActual();
                }
            }
        }catch (Exception e){
            Toast.makeText(Activity_handtalker_frases_predet.this,"No hay imagenes para desplazar.", Toast.LENGTH_SHORT).show();

        }

    }

    private void abrirInfoGuia(){
        if (themes.equals("1")){
            // Crear el constructor del AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(Activity_handtalker_frases_predet.this, R.style.AlertDialogCustom);

            // Configurar el mensaje y el botón del AlertDialog
            builder.setTitle("¿Cómo funciona? (Beta)");
            builder.setMessage("Para ahorrarte tiempo en algún caso de urgencia " +
                            "agrega tus frases de uso rápido oprimiendo el botón agregar, " +
                            "dentro del cuál se abrirá una ventana en donde introducirás tu frase, " +
                            "una vez realizada oprime guardar y se te regresara a la pantalla de " +
                            "traducción. \n\n" +
                            "Oprime la frase que desees traducir y el sistema realizara la traduccion de " +
                            "cada una de las palabras. \n\n" +
                            "Utiliza los botones de desplazamiento para ver la traduccion de la palabra. \n\n" +
                            "En algun caso que la palabra que ingreses no tenga traduccion, se traducira letra " +
                            "por letra (Caso de los nombres). \n\n" +
                            "Presiona el icono de las flechas para cambiar al modo Ingresar Texto. \n\n" +
                            "Mantén presionada una frase para eliminarla de tu lista. \n\n" +
                            "NOTA: No ingreses simbolos o caracteres especiales al momento de agregar la frase.")
                    .setPositiveButton("¡Lo tengo!", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Si se presiona el botón, simplemente cierra el diálogo
                            dialog.dismiss();
                        }
                    });

            // Crear y mostrar el AlertDialog
            AlertDialog dialog = builder.create();
            dialog.show();

            // Personalización de colores después de mostrar el diálogo
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.azulInicio));

            // Para el título y el mensaje, tendrías que usar un TextView personalizado o buscar el TextView por defecto y cambiarle el color.
            TextView messageView = (TextView) dialog.findViewById(android.R.id.message);
            if (messageView != null) {
                messageView.setTextColor(getResources().getColor(R.color.black));
            }

            TextView titleView = (TextView) dialog.findViewById(android.R.id.title);
            if (titleView != null) {
                titleView.setTextColor(getResources().getColor(R.color.black));
            }
        }

        if (themes.equals("2")){
            // Crear el constructor del AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(Activity_handtalker_frases_predet.this, R.style.AlertDialogCustom_black);

            // Configurar el mensaje y el botón del AlertDialog
            builder.setTitle("¿Como funciona? (Beta)");
            builder.setMessage("Para ahorrarte tiempo en algún caso de urgencia " +
                            "agrega tus frases de uso rápido oprimiendo el botón agregar, " +
                            "dentro del cuál se abrirá una ventana en donde introducirás tu frase, " +
                            "una vez realizada oprime guardar y se te regresara a la pantalla de " +
                            "traducción. \n\n" +
                            "Oprime la frase que desees traducir y el sistema realizara la traduccion de " +
                            "cada una de las palabras. \n\n" +
                            "Utiliza los botones de desplazamiento para ver la traduccion de la palabra. \n\n" +
                            "En algun caso que la palabra que ingreses no tenga traduccion, se traducira letra " +
                            "por letra (Caso de los nombres). \n\n" +
                            "Presiona el icono de las flechas para cambiar al modo Ingresar Texto. \n\n" +
                            "Mantén presionada una frase para eliminarla de tu lista. \n\n" +
                            "NOTA: No ingreses simbolos o caracteres especiales al momento de agregar la frase.")
                    .setPositiveButton("¡Lo tengo!", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Si se presiona el botón, simplemente cierra el diálogo
                            dialog.dismiss();
                        }
                    });

            // Crear y mostrar el AlertDialog
            AlertDialog dialog = builder.create();
            dialog.show();

            // Personalización de colores después de mostrar el diálogo
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.azulInicio));

            // Para el título y el mensaje, tendrías que usar un TextView personalizado o buscar el TextView por defecto y cambiarle el color.
            TextView messageView = (TextView) dialog.findViewById(android.R.id.message);
            if (messageView != null) {
                messageView.setTextColor(getResources().getColor(R.color.white));
            }

            TextView titleView = (TextView) dialog.findViewById(android.R.id.title);
            if (titleView != null) {
                titleView.setTextColor(getResources().getColor(R.color.white));
            }
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
        signLanguageMap.put("ABAJO", R.drawable.abajo);
        signLanguageMap.put("ABEJA", R.drawable.abeja);
        signLanguageMap.put("ABEJAS", R.drawable.abeja);
        signLanguageMap.put("ABRIL", R.drawable.abril);
        signLanguageMap.put("ABRIR", R.drawable.abrir);
        signLanguageMap.put("ABRE", R.drawable.abrir);
        signLanguageMap.put("ABRIRSE", R.drawable.abrir);
        signLanguageMap.put("ABUELA", R.drawable.abuela);
        signLanguageMap.put("ABUELAS", R.drawable.abuela);
        signLanguageMap.put("ABUELO", R.drawable.abuelo);
        signLanguageMap.put("ABUELOS", R.drawable.abuelo);
        signLanguageMap.put("ACEITE", R.drawable.aceite);
        signLanguageMap.put("ACEITES", R.drawable.aceite);
        signLanguageMap.put("ACOMPAÑADO", R.drawable.acompanado);
        signLanguageMap.put("ADENTRO", R.drawable.adentro);
        signLanguageMap.put("ADULTO", R.drawable.adulto);
        signLanguageMap.put("ADULTOS", R.drawable.adulto);
        signLanguageMap.put("AFUERA", R.drawable.afuera);
        signLanguageMap.put("AGARRAR", R.drawable.agarrar);
        signLanguageMap.put("AGARRA", R.drawable.agarrar);
        signLanguageMap.put("AGOSTO", R.drawable.agosto);
        signLanguageMap.put("AGRIO", R.drawable.agrio);
        signLanguageMap.put("AGUA", R.drawable.agua);
        signLanguageMap.put("AGUAS", R.drawable.agua);
        signLanguageMap.put("AGUILA", R.drawable.aguila);
        signLanguageMap.put("AGUILAS", R.drawable.aguila);
        signLanguageMap.put("AHIJADA", R.drawable.ahijada);
        signLanguageMap.put("AHIJADAS", R.drawable.ahijada);
        signLanguageMap.put("AHIJADO", R.drawable.ahijado);
        signLanguageMap.put("AHIJADOS", R.drawable.ahijado);
        signLanguageMap.put("ALA", R.drawable.ala);
        signLanguageMap.put("ALAS", R.drawable.ala);
        signLanguageMap.put("ALACRAN", R.drawable.alacran);
        signLanguageMap.put("ALACRANES", R.drawable.alacran);
        signLanguageMap.put("ALEGRE", R.drawable.alegre);
        signLanguageMap.put("FELIZ", R.drawable.alegre);
        signLanguageMap.put("ALGO", R.drawable.algo);
        signLanguageMap.put("ALIMENTO", R.drawable.alimento);
        signLanguageMap.put("ALIMENTOS", R.drawable.alimento);
        signLanguageMap.put("ALMEJA", R.drawable.almeja);
        signLanguageMap.put("ALMEJAS", R.drawable.almeja);
        signLanguageMap.put("ALMOHADA", R.drawable.almohada);
        signLanguageMap.put("ALMOHADAS", R.drawable.almohada);
        signLanguageMap.put("ALTO", R.drawable.alto);
        signLanguageMap.put("ALTOS", R.drawable.alto);
        signLanguageMap.put("AMANECER", R.drawable.amanecer);
        signLanguageMap.put("AMANECE", R.drawable.amanecer);
        signLanguageMap.put("AMANTE", R.drawable.amante);
        signLanguageMap.put("AMANTES", R.drawable.amante);
        signLanguageMap.put("AMARILLO", R.drawable.amarillo);
        signLanguageMap.put("AMARILLOS", R.drawable.amarillo);
        signLanguageMap.put("AMARILLA", R.drawable.amarillo);
        signLanguageMap.put("AMARILLAS", R.drawable.amarillo);
        signLanguageMap.put("AMIGA", R.drawable.amiga);
        signLanguageMap.put("AMIGAS", R.drawable.amiga);
        signLanguageMap.put("AMIGO", R.drawable.amigo);
        signLanguageMap.put("AMIGOS", R.drawable.amigo);
        signLanguageMap.put("AMISTAD", R.drawable.amistad);
        signLanguageMap.put("AMISTADES", R.drawable.amistad);
        signLanguageMap.put("ANARANJADO", R.drawable.anaranjado);
        signLanguageMap.put("ANCIANA", R.drawable.anciana);
        signLanguageMap.put("ANCIANAS", R.drawable.anciana);
        signLanguageMap.put("ANCIANO", R.drawable.anciano);
        signLanguageMap.put("ANCIANOS", R.drawable.anciano);
        signLanguageMap.put("ANIMAL", R.drawable.animal);
        signLanguageMap.put("ANIMALES", R.drawable.animal);
        signLanguageMap.put("ANTE", R.drawable.ante);
        signLanguageMap.put("ANTES", R.drawable.antes);
        signLanguageMap.put("ANTIGUO", R.drawable.antiguo);
        signLanguageMap.put("ANTIGUOS", R.drawable.antiguo);
        signLanguageMap.put("ANTIGUA", R.drawable.antiguo);
        signLanguageMap.put("ANTIGUAS", R.drawable.antiguo);
        signLanguageMap.put("ANTROPOLOGIA", R.drawable.antropologia);
        signLanguageMap.put("ANTROPOLOGIAS", R.drawable.antropologia);
        signLanguageMap.put("AÑO", R.drawable.ano);
        signLanguageMap.put("AÑOS", R.drawable.ano);
        signLanguageMap.put("APAGAR", R.drawable.apagar);
        signLanguageMap.put("APAGARSE", R.drawable.apagar);
        signLanguageMap.put("APAGADO", R.drawable.apagar);
        signLanguageMap.put("APAGARLO", R.drawable.apagar);
        signLanguageMap.put("APAGA", R.drawable.apagar);
        signLanguageMap.put("APARECER", R.drawable.aparecer);
        signLanguageMap.put("APARECERSE", R.drawable.aparecer);
        signLanguageMap.put("APARECE", R.drawable.aparecer);
        signLanguageMap.put("APARECETE", R.drawable.aparecer);
        signLanguageMap.put("APETITO", R.drawable.apetito);
        signLanguageMap.put("APLASTAR", R.drawable.aplastar);
        signLanguageMap.put("APLASTA", R.drawable.aplastar);
        signLanguageMap.put("ARAÑA", R.drawable.arana);
        signLanguageMap.put("ARAÑAS", R.drawable.arana);
        signLanguageMap.put("ARDILLA", R.drawable.ardilla);
        signLanguageMap.put("ARDILLAS", R.drawable.ardilla);
        signLanguageMap.put("ARRIBA", R.drawable.arriba);
        signLanguageMap.put("ARROZ", R.drawable.arroz);
        signLanguageMap.put("ARTICULO", R.drawable.articulo);
        signLanguageMap.put("ARTICULOS", R.drawable.articulo);
        signLanguageMap.put("ASAR", R.drawable.asar);
        signLanguageMap.put("ASARLO", R.drawable.asar);
        signLanguageMap.put("ASARLA", R.drawable.asar);
        signLanguageMap.put("ASNO", R.drawable.asno);
        signLanguageMap.put("ASNOS", R.drawable.asno);
        signLanguageMap.put("ATOLE", R.drawable.atole);
        signLanguageMap.put("ATOLES", R.drawable.atole);
        signLanguageMap.put("AVENA", R.drawable.avena);
        signLanguageMap.put("AVESTRUZ", R.drawable.avestruz);
        signLanguageMap.put("AVESTRUCES", R.drawable.avestruz);
        signLanguageMap.put("AVISPA", R.drawable.avispa);
        signLanguageMap.put("AVISPAS", R.drawable.avispa);
        signLanguageMap.put("AYER", R.drawable.ayer);
        signLanguageMap.put("AZOTEA", R.drawable.azotea);
        signLanguageMap.put("AZOTEAS", R.drawable.azotea);
        signLanguageMap.put("AZUCAR", R.drawable.azucar);
        signLanguageMap.put("AZUCARES", R.drawable.azucar);
        signLanguageMap.put("AZUL", R.drawable.azul);
        signLanguageMap.put("AZULES", R.drawable.azul);
        signLanguageMap.put("BACARDI", R.drawable.bacardi);
        signLanguageMap.put("BACARDIS", R.drawable.bacardi);
        signLanguageMap.put("BAJO", R.drawable.bajo);
        signLanguageMap.put("BAJOS", R.drawable.bajo);
        signLanguageMap.put("BAJA", R.drawable.bajo);
        signLanguageMap.put("BAJAS", R.drawable.bajo);
        signLanguageMap.put("BANDERA", R.drawable.bandera);
        signLanguageMap.put("BANDERAS", R.drawable.bandera);
        signLanguageMap.put("BAÑO", R.drawable.bano);
        signLanguageMap.put("BAÑOS", R.drawable.bano);
        signLanguageMap.put("BARBA", R.drawable.barba);
        signLanguageMap.put("BARBAS", R.drawable.barba);
        signLanguageMap.put("BARBILLA", R.drawable.barbilla);
        signLanguageMap.put("BARBILLAS", R.drawable.barbilla);
        signLanguageMap.put("BARRER", R.drawable.barrer);
        signLanguageMap.put("BARRE", R.drawable.barrer);
        signLanguageMap.put("BASURA", R.drawable.basura);
        signLanguageMap.put("BASURAS", R.drawable.basura);
        signLanguageMap.put("BASURITAS", R.drawable.basura);
        signLanguageMap.put("BASURITA", R.drawable.basura);
        signLanguageMap.put("BATIDORA", R.drawable.batidora);
        signLanguageMap.put("BATIDOR", R.drawable.batidora);
        signLanguageMap.put("BATIDORAS", R.drawable.batidora);
        signLanguageMap.put("BEBÉ", R.drawable.bebe);
        signLanguageMap.put("BEBÉS", R.drawable.bebe);
        signLanguageMap.put("BECERRO", R.drawable.becerro);
        signLanguageMap.put("BECERROS", R.drawable.becerro);
        signLanguageMap.put("BIEN", R.drawable.bien);
        signLanguageMap.put("BIGOTE", R.drawable.bigote);
        signLanguageMap.put("BIGOTES", R.drawable.bigote);
        signLanguageMap.put("BISTEC", R.drawable.bistec);
        signLanguageMap.put("BLANCO", R.drawable.blanco);
        signLanguageMap.put("BLANCOS", R.drawable.blanco);
        signLanguageMap.put("BOCA", R.drawable.boca);
        signLanguageMap.put("BOCAS", R.drawable.boca);
        signLanguageMap.put("BODA", R.drawable.boda);
        signLanguageMap.put("BODAS", R.drawable.boda);
        signLanguageMap.put("BONITO", R.drawable.bonito);
        signLanguageMap.put("BONITOS", R.drawable.bonito);
        signLanguageMap.put("BORREGO", R.drawable.borrego);
        signLanguageMap.put("BORREGOS", R.drawable.borrego);
        signLanguageMap.put("BOTE", R.drawable.bote);
        signLanguageMap.put("BOTES", R.drawable.bote);
        signLanguageMap.put("BRAZO", R.drawable.brazo);
        signLanguageMap.put("BRAZOS", R.drawable.brazo);
        signLanguageMap.put("BRILLANTE", R.drawable.brillante);
        signLanguageMap.put("BRILLANTES", R.drawable.brillante);
        signLanguageMap.put("BRONCE", R.drawable.bronce);
        signLanguageMap.put("BUENO", R.drawable.bueno);
        signLanguageMap.put("BUENOS", R.drawable.bueno);
        signLanguageMap.put("BUEY", R.drawable.buey);
        signLanguageMap.put("BUEYES", R.drawable.buey);
        signLanguageMap.put("BUFALO", R.drawable.bufalo);
        signLanguageMap.put("BUFALOS", R.drawable.bufalo);
        signLanguageMap.put("BUHO", R.drawable.buho);
        signLanguageMap.put("BUHOS", R.drawable.buho);
        signLanguageMap.put("BURRO", R.drawable.burro);
        signLanguageMap.put("BURROS", R.drawable.burro);
        signLanguageMap.put("BURRE", R.drawable.burro);
        signLanguageMap.put("BUZON", R.drawable.buzon);
        signLanguageMap.put("BUZONES", R.drawable.buzon);
        signLanguageMap.put("CABALLERO", R.drawable.caballero);
        signLanguageMap.put("CABALLEROS", R.drawable.caballero);
        signLanguageMap.put("CABALLO", R.drawable.caballo);
        signLanguageMap.put("CABALLOS", R.drawable.caballo);
        signLanguageMap.put("CABRA", R.drawable.cabra);
        signLanguageMap.put("CABRAS", R.drawable.cabra);
        signLanguageMap.put("CACEROLA", R.drawable.cacerola);
        signLanguageMap.put("CACEROLAS", R.drawable.cacerola);
        signLanguageMap.put("CADA", R.drawable.cada);
        signLanguageMap.put("CADERA", R.drawable.cadera);
        signLanguageMap.put("CADERAS", R.drawable.cadera);
        signLanguageMap.put("CAFE", R.drawable.cafe);
        signLanguageMap.put("CAFES", R.drawable.cafe);
        signLanguageMap.put("CAJON", R.drawable.cajon);
        signLanguageMap.put("CAJONES", R.drawable.cajon);
        signLanguageMap.put("CALAMAR", R.drawable.calamar);
        signLanguageMap.put("CALAMARES", R.drawable.calamar);
        signLanguageMap.put("CALDO", R.drawable.caldo);
        signLanguageMap.put("CALDOS", R.drawable.caldo);
        signLanguageMap.put("CALIFICACION", R.drawable.calificacion);
        signLanguageMap.put("CALIFICACIONES", R.drawable.calificacion);
        signLanguageMap.put("CALMA", R.drawable.calma);
        signLanguageMap.put("CALMARSE", R.drawable.calma);
        signLanguageMap.put("CALMAR", R.drawable.calma);
        signLanguageMap.put("CALMATE", R.drawable.calma);
        signLanguageMap.put("CAMA", R.drawable.cama);
        signLanguageMap.put("CAMAS", R.drawable.cama);
        signLanguageMap.put("CAMARON", R.drawable.camaron);
        signLanguageMap.put("CAMARONES", R.drawable.camaron);
        signLanguageMap.put("CAMELLO", R.drawable.camello);
        signLanguageMap.put("CAMELLOS", R.drawable.camello);
        signLanguageMap.put("CAMINAR", R.drawable.caminar);
        signLanguageMap.put("CAMINA", R.drawable.caminar);
        signLanguageMap.put("CAMPANA", R.drawable.campana);
        signLanguageMap.put("CAMPANAS", R.drawable.campana);
        signLanguageMap.put("CANDELERO", R.drawable.candelero);
        signLanguageMap.put("CANDELEROS", R.drawable.candelero);
        signLanguageMap.put("CANGURO", R.drawable.canguro);
        signLanguageMap.put("CANGUROS", R.drawable.canguro);
        signLanguageMap.put("CARA", R.drawable.cara);
        signLanguageMap.put("CARAS", R.drawable.cara);
        signLanguageMap.put("CARACOL", R.drawable.caracol);
        signLanguageMap.put("CARACOLES", R.drawable.caracol);
        signLanguageMap.put("CARIÑO", R.drawable.carino);
        signLanguageMap.put("CARIÑOS", R.drawable.carino);
        signLanguageMap.put("CARNE", R.drawable.carne);
        signLanguageMap.put("CARNES", R.drawable.carne);
        signLanguageMap.put("CARO", R.drawable.caro);
        signLanguageMap.put("CASA", R.drawable.casa);
        signLanguageMap.put("CASAS", R.drawable.casa);
        signLanguageMap.put("CASADA", R.drawable.casada);
        signLanguageMap.put("CASADO", R.drawable.casado);
        signLanguageMap.put("CATSUP", R.drawable.catsup);
        signLanguageMap.put("CAZUELA", R.drawable.cazuela);
        signLanguageMap.put("CAZUELAS", R.drawable.cazuela);
        signLanguageMap.put("CEBRA", R.drawable.cebra);
        signLanguageMap.put("CEBRAS", R.drawable.cebra);
        signLanguageMap.put("CEJA", R.drawable.ceja);
        signLanguageMap.put("CEJAS", R.drawable.ceja);
        signLanguageMap.put("CENA", R.drawable.cena);
        signLanguageMap.put("CENAR", R.drawable.cena);
        signLanguageMap.put("CENAS", R.drawable.cena);
        //signLanguageMap.put("CEPILLODIENTES", R.drawable.cepillo_dientes);
        signLanguageMap.put("CEPILLO", R.drawable.cepillo_de_cabello);
        signLanguageMap.put("CEPILLOS", R.drawable.cepillo_de_cabello);
        signLanguageMap.put("CEREAL", R.drawable.cereal);
        signLanguageMap.put("CEREALES", R.drawable.cereal);
        signLanguageMap.put("CERRAR", R.drawable.cerrar);
        signLanguageMap.put("CERRADO", R.drawable.cerrar);
        signLanguageMap.put("CERRADA", R.drawable.cerrar);
        signLanguageMap.put("CERTIFICADO", R.drawable.certificado);
        signLanguageMap.put("CERTIFICADOS", R.drawable.certificado);
        signLanguageMap.put("CERVEZA", R.drawable.cerveza);
        signLanguageMap.put("CERVEZAS", R.drawable.cerveza);
        signLanguageMap.put("CHAMPU", R.drawable.champu);
        signLanguageMap.put("SHAMPOO", R.drawable.champu);
        signLanguageMap.put("CHANGO", R.drawable.chango);
        signLanguageMap.put("CHANGOS", R.drawable.chango);
        signLanguageMap.put("CHAROLA", R.drawable.charola);
        signLanguageMap.put("CHAROLAS", R.drawable.charola);
        signLanguageMap.put("CHICLE", R.drawable.chicle);
        signLanguageMap.put("CHICLES", R.drawable.chicle);
        signLanguageMap.put("CHICO", R.drawable.chico);
        signLanguageMap.put("CHICOS", R.drawable.chico);
        signLanguageMap.put("CHIMENEA", R.drawable.chimenea);
        signLanguageMap.put("CHIMENEAS", R.drawable.chimenea);
        signLanguageMap.put("CHIVO", R.drawable.chivo);
        signLanguageMap.put("CHIVOS", R.drawable.chivo);
        signLanguageMap.put("CHIVAS", R.drawable.chivo);
        signLanguageMap.put("CHOCOLATE", R.drawable.chocolate);
        signLanguageMap.put("CHOCOLATES", R.drawable.chocolate);
        signLanguageMap.put("CHORIZO", R.drawable.chorizo);
        signLanguageMap.put("CHORIZOS", R.drawable.chorizo);
        signLanguageMap.put("CHUPON", R.drawable.chupon);
        signLanguageMap.put("CHUPONES", R.drawable.chupon);
        signLanguageMap.put("CIERTO", R.drawable.cierto);
        signLanguageMap.put("CISNE", R.drawable.cisne);
        signLanguageMap.put("CISNES", R.drawable.cisne);
        signLanguageMap.put("CLARO", R.drawable.claro);
        signLanguageMap.put("COBERTOR", R.drawable.cobertor);
        signLanguageMap.put("COBERTORES", R.drawable.cobertor);
        signLanguageMap.put("COCACOLA", R.drawable.coca_cola);
        signLanguageMap.put("COCACOLAS", R.drawable.coca_cola);
        signLanguageMap.put("COCA-COLA", R.drawable.coca_cola);
        signLanguageMap.put("COCA", R.drawable.coca_cola);
        signLanguageMap.put("COCAS", R.drawable.coca_cola);
        signLanguageMap.put("COCHINO", R.drawable.cochino);
        signLanguageMap.put("COCHINOS", R.drawable.cochino);
        signLanguageMap.put("COCHINA", R.drawable.cochino);
        signLanguageMap.put("COCINA", R.drawable.cocina);
        signLanguageMap.put("COCINAS", R.drawable.cocina);
        signLanguageMap.put("COCODRILO", R.drawable.cocodrilo);
        signLanguageMap.put("COCODRILOS", R.drawable.cocodrilo);
        signLanguageMap.put("CODO", R.drawable.codo);
        signLanguageMap.put("CODOS", R.drawable.codo);
        signLanguageMap.put("COLADERA", R.drawable.coladera);
        signLanguageMap.put("COLADERAS", R.drawable.coladera);
        signLanguageMap.put("COLADOR", R.drawable.colador);
        signLanguageMap.put("COLADORES", R.drawable.colador);
        signLanguageMap.put("COLCHA", R.drawable.colcha);
        signLanguageMap.put("COLCHAS", R.drawable.colcha);
        signLanguageMap.put("COLOR", R.drawable.color);
        signLanguageMap.put("COLORES", R.drawable.color);
        signLanguageMap.put("COMADRE", R.drawable.comadre);
        signLanguageMap.put("COMADRES", R.drawable.comadre);
        signLanguageMap.put("COMAL", R.drawable.comal);
        signLanguageMap.put("COMALES", R.drawable.comal);
        signLanguageMap.put("COMEDOR", R.drawable.comedor);
        signLanguageMap.put("COMEDORES", R.drawable.comedor);
        signLanguageMap.put("COMIDA", R.drawable.comida);
        signLanguageMap.put("COMIDAS", R.drawable.comida);
        signLanguageMap.put("COMODA", R.drawable.comoda);
        signLanguageMap.put("CÓMODA", R.drawable.comoda);
        signLanguageMap.put("COMODO", R.drawable.comoda);
        signLanguageMap.put("CÓMODO", R.drawable.comoda);
        signLanguageMap.put("CONDOMINIO", R.drawable.condominio);
        signLanguageMap.put("CONDOMINIOS", R.drawable.condominio);
        signLanguageMap.put("CONEJO", R.drawable.conejo);
        signLanguageMap.put("CONEJOS", R.drawable.conejo);
        signLanguageMap.put("CONEJA", R.drawable.conejo);
        signLanguageMap.put("CONEJAS", R.drawable.conejo);
        signLanguageMap.put("CONMIGO", R.drawable.conmigo);
        signLanguageMap.put("CONTESTAR", R.drawable.contestar);
        signLanguageMap.put("CONTESTA", R.drawable.contestar);
        signLanguageMap.put("CONTIGO", R.drawable.contigo);
        signLanguageMap.put("CONTRA", R.drawable.contra);
        signLanguageMap.put("CONTRAS", R.drawable.contra);
        signLanguageMap.put("COPA", R.drawable.copa);
        signLanguageMap.put("COPAS", R.drawable.copa);
        signLanguageMap.put("CORDERO", R.drawable.cordero);
        signLanguageMap.put("CORDEROS", R.drawable.cordero);
        signLanguageMap.put("CORREDOR", R.drawable.corredor);
        signLanguageMap.put("CORREDORES", R.drawable.corredor);
        signLanguageMap.put("CORREDORA", R.drawable.corredor);
        signLanguageMap.put("CORRIENTE", R.drawable.corriente);
        signLanguageMap.put("CORTINA", R.drawable.cortina);
        signLanguageMap.put("CORTINAS", R.drawable.cortina);
        signLanguageMap.put("CORTO", R.drawable.corto);
        signLanguageMap.put("CORTOS", R.drawable.corto);
        signLanguageMap.put("CUADERNO", R.drawable.cuaderno);
        signLanguageMap.put("CUADERNOS", R.drawable.cuaderno);
        signLanguageMap.put("LIBRETA", R.drawable.cuaderno);
        signLanguageMap.put("LIBRETAS", R.drawable.cuaderno);
        signLanguageMap.put("CUATES", R.drawable.cuates);
        signLanguageMap.put("CUATE", R.drawable.cuates);
        signLanguageMap.put("CUCHARA", R.drawable.cuchara);
        signLanguageMap.put("CUCHARAS", R.drawable.cuchara);
        signLanguageMap.put("CUCHILLO", R.drawable.cuchillo);
        signLanguageMap.put("CUCHILLOS", R.drawable.cuchillo);
        signLanguageMap.put("CUELLO", R.drawable.cuello);
        signLanguageMap.put("CUÑADA", R.drawable.cunada);
        signLanguageMap.put("CUÑADO", R.drawable.cunado);
        signLanguageMap.put("DAMA", R.drawable.dama);
        signLanguageMap.put("DAMAS", R.drawable.dama);
        signLanguageMap.put("DAR", R.drawable.dar);
        signLanguageMap.put("DA", R.drawable.dar);
        signLanguageMap.put("DE", R.drawable.de);
        signLanguageMap.put("DEL", R.drawable.de);
        signLanguageMap.put("DEBAJO", R.drawable.debajo);
        signLanguageMap.put("DEJAR", R.drawable.dejar);
        signLanguageMap.put("DEJA", R.drawable.dejar);
        signLanguageMap.put("DEJARLO", R.drawable.dejar);
        signLanguageMap.put("DEJARLA", R.drawable.dejar);
        signLanguageMap.put("DELANTE", R.drawable.delante);
        signLanguageMap.put("DELFIN", R.drawable.delfin);
        signLanguageMap.put("DELFINES", R.drawable.delfin);
        signLanguageMap.put("DELICIOSO", R.drawable.delicioso);
        signLanguageMap.put("DELICIOSOS", R.drawable.delicioso);
        signLanguageMap.put("DEPARTAMENTO", R.drawable.departamento);
        signLanguageMap.put("DEPARTAMENTOS", R.drawable.departamento);
        signLanguageMap.put("DERECHA", R.drawable.derecha);
        signLanguageMap.put("DERRUMBAR", R.drawable.derrumbar);
        signLanguageMap.put("DERRUMBA", R.drawable.derrumbar);
        signLanguageMap.put("DERRUMBARSE", R.drawable.derrumbar);
        signLanguageMap.put("DESAPARECER", R.drawable.desaparecer);
        signLanguageMap.put("DESAPARECE", R.drawable.desaparecer);
        signLanguageMap.put("DESAYUNO", R.drawable.desayuno);
        signLanguageMap.put("DESAYUNOS", R.drawable.desayuno);
        signLanguageMap.put("DESPUES", R.drawable.despues);
        signLanguageMap.put("DESTAPADOR", R.drawable.destapador);
        signLanguageMap.put("DETENER", R.drawable.detener);
        signLanguageMap.put("DETENERTE", R.drawable.detener);
        signLanguageMap.put("DETRAS", R.drawable.detras);
        signLanguageMap.put("DIA", R.drawable.dia);
        signLanguageMap.put("DIAS", R.drawable.dia);
        signLanguageMap.put("DIBUJO", R.drawable.dibujo);
        signLanguageMap.put("DIBUJA", R.drawable.dibujo);
        signLanguageMap.put("DIBUJAR", R.drawable.dibujo);
        signLanguageMap.put("DIBUJOS", R.drawable.dibujo);
        signLanguageMap.put("DICCIONARIO", R.drawable.diccionario);
        signLanguageMap.put("DICCIONARIOS", R.drawable.diccionario);
        signLanguageMap.put("DICIEMBRE", R.drawable.diciembre);
        signLanguageMap.put("DIENTE", R.drawable.diente);
        signLanguageMap.put("DIENTES", R.drawable.diente);
        signLanguageMap.put("DIFICIL", R.drawable.dificil);
        signLanguageMap.put("DIFICILES", R.drawable.dificil);
        signLanguageMap.put("DIRECCION", R.drawable.direccion);
        signLanguageMap.put("DIRECCIONES", R.drawable.direccion);
        signLanguageMap.put("DIVORCIADA", R.drawable.divorciada);
        signLanguageMap.put("DIVORCIADO", R.drawable.divorciado);
        signLanguageMap.put("DOMINGO", R.drawable.domingo);
        signLanguageMap.put("DONA", R.drawable.dona);
        signLanguageMap.put("DONAS", R.drawable.dona);
        signLanguageMap.put("DULCE", R.drawable.dulce);
        signLanguageMap.put("DULCES", R.drawable.dulce);
        signLanguageMap.put("DURO", R.drawable.duro);
        signLanguageMap.put("DUROS", R.drawable.duro);
        signLanguageMap.put("EDIFICIO", R.drawable.edificio);
        signLanguageMap.put("EDIFICIOS", R.drawable.edificio);
        signLanguageMap.put("EL", R.drawable.ele);
        signLanguageMap.put("LOS", R.drawable.ele);
        signLanguageMap.put("LAS", R.drawable.ele);
        signLanguageMap.put("LA", R.drawable.ele);
        signLanguageMap.put("LO", R.drawable.ele);
        signLanguageMap.put("ELLA", R.drawable.ele);
        signLanguageMap.put("ELECTRICIDAD", R.drawable.electricidad);
        signLanguageMap.put("ELEFANTE", R.drawable.elefante);
        signLanguageMap.put("ELEFANTES", R.drawable.elefante);
        signLanguageMap.put("ELLOS", R.drawable.ellos);
        signLanguageMap.put("ELLAS", R.drawable.ellos);
        signLanguageMap.put("EMPEZAR", R.drawable.empezar);
        signLanguageMap.put("EMPIEZA", R.drawable.empezar);
        signLanguageMap.put("INICIAR", R.drawable.empezar);
        signLanguageMap.put("INICIA", R.drawable.empezar);
        signLanguageMap.put("COMENZAR", R.drawable.empezar);
        signLanguageMap.put("COMIENZA", R.drawable.empezar);
        signLanguageMap.put("EN", R.drawable.en);
        signLanguageMap.put("ENANO", R.drawable.enano);
        signLanguageMap.put("ENANOS", R.drawable.enano);
        signLanguageMap.put("ENCENDER", R.drawable.encender);
        signLanguageMap.put("ENCIENDE", R.drawable.encender);
        signLanguageMap.put("ENCHILADA", R.drawable.enchiladas);
        signLanguageMap.put("ENCHILADAS", R.drawable.enchiladas);
        signLanguageMap.put("ENCIMA", R.drawable.encima);
        signLanguageMap.put("ENERO", R.drawable.enero);
        signLanguageMap.put("ENSALADA", R.drawable.ensalada);
        signLanguageMap.put("ENSALADAS", R.drawable.ensalada);
        signLanguageMap.put("ENTRAR", R.drawable.entrar);
        signLanguageMap.put("ENTRA", R.drawable.entrar);
        signLanguageMap.put("ENTRE", R.drawable.entre);
        signLanguageMap.put("ESCALA", R.drawable.escala);
        signLanguageMap.put("ESCALAS", R.drawable.escala);
        signLanguageMap.put("ESCALERA", R.drawable.escalera);
        signLanguageMap.put("ESCALERAS", R.drawable.escalera);
        signLanguageMap.put("ESCRITORIO", R.drawable.escritorio);
        signLanguageMap.put("ESCRITORIOS", R.drawable.escritorio);
        signLanguageMap.put("ESCUADRA", R.drawable.escuadra);
        signLanguageMap.put("ESCUADRAS", R.drawable.escuadra);
        signLanguageMap.put("ESCUELA", R.drawable.escuela);
        signLanguageMap.put("ESCUELAS", R.drawable.escuela);
        signLanguageMap.put("ESCUSADO", R.drawable.escusado);
        signLanguageMap.put("ESO", R.drawable.eso);
        signLanguageMap.put("ESOS", R.drawable.eso);
        signLanguageMap.put("ESA", R.drawable.eso);
        signLanguageMap.put("ESAS", R.drawable.eso);
        signLanguageMap.put("ESE", R.drawable.eso);
        signLanguageMap.put("ESPAGUETI", R.drawable.espaguetti);
        signLanguageMap.put("ESPAGUETIS", R.drawable.espaguetti);
        signLanguageMap.put("ESPAGUETTI", R.drawable.espaguetti);
        signLanguageMap.put("ESPAGUETTIS", R.drawable.espaguetti);
        signLanguageMap.put("ESPALDA", R.drawable.espalda);
        signLanguageMap.put("ESPEJO", R.drawable.espejo);
        signLanguageMap.put("ESPEJOS", R.drawable.espejo);
        signLanguageMap.put("ESPOSA", R.drawable.esposa);
        signLanguageMap.put("ESPOSAS", R.drawable.esposa);
        signLanguageMap.put("ESPOSO", R.drawable.esposo);
        signLanguageMap.put("ESPOSOS", R.drawable.esposo);
        signLanguageMap.put("ESTOMAGO", R.drawable.estomago);
        signLanguageMap.put("ESTUDIANTE", R.drawable.estudiante);
        signLanguageMap.put("ESTUDIANTES", R.drawable.estudiante);
        signLanguageMap.put("ESTUFA", R.drawable.estufa);
        signLanguageMap.put("ESTUFAS", R.drawable.estufa);
        signLanguageMap.put("EXALTACION", R.drawable.exaltacion);
        signLanguageMap.put("EXALTAR", R.drawable.exaltacion);
        signLanguageMap.put("EXPANDIR", R.drawable.expandir);
        signLanguageMap.put("EXPANDE", R.drawable.expandir);
        signLanguageMap.put("FACIL", R.drawable.facil);
        signLanguageMap.put("FACILES", R.drawable.facil);
        signLanguageMap.put("FALSO", R.drawable.falso);
        signLanguageMap.put("FALSOS", R.drawable.falso);
        signLanguageMap.put("FALSA", R.drawable.falso);
        signLanguageMap.put("FALSAS", R.drawable.falso);
        signLanguageMap.put("FAMILIA", R.drawable.familia);
        signLanguageMap.put("FAMILIAS", R.drawable.familia);
        signLanguageMap.put("FEBRERO", R.drawable.febrero);
        signLanguageMap.put("FELICITAR", R.drawable.felicitar);
        signLanguageMap.put("FELICITA", R.drawable.felicitar);
        signLanguageMap.put("FELICITO", R.drawable.felicitar);
        signLanguageMap.put("FELICITACION", R.drawable.felicitar);
        signLanguageMap.put("FELICITACIONES", R.drawable.felicitar);
        signLanguageMap.put("FEMENINO", R.drawable.femenino);
        signLanguageMap.put("FEO", R.drawable.feo);
        signLanguageMap.put("FEA", R.drawable.feo);
        signLanguageMap.put("FEOS", R.drawable.feo);
        signLanguageMap.put("FEAS", R.drawable.feo);
        signLanguageMap.put("FIDEO", R.drawable.fideo);
        signLanguageMap.put("FIDEOS", R.drawable.fideo);
        signLanguageMap.put("FLAMENCO", R.drawable.flamenco);
        signLanguageMap.put("FLAMENCOS", R.drawable.flamenco);
        signLanguageMap.put("FLORERO", R.drawable.florero);
        signLanguageMap.put("FLOREROS", R.drawable.florero);
        signLanguageMap.put("FOCA", R.drawable.foca);
        signLanguageMap.put("FOCAS", R.drawable.foca);
        signLanguageMap.put("FOCO", R.drawable.foco);
        signLanguageMap.put("FOCOS", R.drawable.foco);
        signLanguageMap.put("FOGATA", R.drawable.fogata);
        signLanguageMap.put("FOGATAS", R.drawable.fogata);
        signLanguageMap.put("HOGUERA", R.drawable.fogata);
        signLanguageMap.put("FRUTERO", R.drawable.frutero);
        signLanguageMap.put("FRUTEROS", R.drawable.frutero);
        signLanguageMap.put("FUEGO", R.drawable.fuego);
        signLanguageMap.put("FUEGOS", R.drawable.fuego);
        signLanguageMap.put("FUTURO", R.drawable.futuro);
        signLanguageMap.put("FUTUROS", R.drawable.futuro);
        signLanguageMap.put("FUTURA", R.drawable.futuro);
        signLanguageMap.put("FUTURAS", R.drawable.futuro);
        signLanguageMap.put("GALLETA", R.drawable.galleta);
        signLanguageMap.put("GALLETAS", R.drawable.galleta);
        signLanguageMap.put("GALLO", R.drawable.gallo);
        signLanguageMap.put("GALLOS", R.drawable.gallo);
        signLanguageMap.put("GANAR", R.drawable.ganar);
        signLanguageMap.put("GANA", R.drawable.ganar);
        signLanguageMap.put("GANAS", R.drawable.ganar);
        signLanguageMap.put("GANO", R.drawable.ganar);
        signLanguageMap.put("GANÓ", R.drawable.ganar);
        signLanguageMap.put("GARAGE", R.drawable.garage);
        signLanguageMap.put("GARGANTA", R.drawable.garganta);
        signLanguageMap.put("GATO", R.drawable.gato);
        signLanguageMap.put("GATOS", R.drawable.gato);
        signLanguageMap.put("GELATINA", R.drawable.gelatina);
        signLanguageMap.put("GELATINAS", R.drawable.gelatina);
        signLanguageMap.put("GEMELOS", R.drawable.gemelos);
        signLanguageMap.put("GEMELO", R.drawable.gemelos);
        signLanguageMap.put("GEMELAS", R.drawable.gemelos);
        signLanguageMap.put("GEMELA", R.drawable.gemelos);
        signLanguageMap.put("GENERACION", R.drawable.generacion);
        signLanguageMap.put("GENERACIONES", R.drawable.generacion);
        signLanguageMap.put("GIGANTE", R.drawable.gigante);
        signLanguageMap.put("GIGANTES", R.drawable.gigante);
        signLanguageMap.put("GOMA", R.drawable.goma);
        signLanguageMap.put("GOMAS", R.drawable.goma);
        signLanguageMap.put("GORILA", R.drawable.gorila);
        signLanguageMap.put("GORILAS", R.drawable.gorila);
        signLanguageMap.put("GRANDE", R.drawable.grande);
        signLanguageMap.put("GRANDES", R.drawable.grande);
        signLanguageMap.put("GRASA", R.drawable.grasa);
        signLanguageMap.put("GRASAS", R.drawable.grasa);
        signLanguageMap.put("GRATIS", R.drawable.gratis);
        signLanguageMap.put("GRIS", R.drawable.gris);
        signLanguageMap.put("GRISES", R.drawable.gris);
        signLanguageMap.put("GROSERO", R.drawable.grosero);
        signLanguageMap.put("GROSEROS", R.drawable.grosero);
        signLanguageMap.put("GROSERA", R.drawable.grosero);
        signLanguageMap.put("GROSERAS", R.drawable.grosero);
        signLanguageMap.put("GUAJOLOTE", R.drawable.guajolote);
        signLanguageMap.put("GUAJOLOTES", R.drawable.guajolote);
        signLanguageMap.put("GUAPO", R.drawable.guapo);
        signLanguageMap.put("GUAPOS", R.drawable.guapo);
        signLanguageMap.put("GUAPA", R.drawable.guapo);
        signLanguageMap.put("GUAPAS", R.drawable.guapo);
        signLanguageMap.put("GUSANO", R.drawable.gusano);
        signLanguageMap.put("GUSANOS", R.drawable.gusano);
        signLanguageMap.put("HAMBRE", R.drawable.hambre);
        signLanguageMap.put("HAMBURGUESA", R.drawable.hamburguesa);
        signLanguageMap.put("HAMBURGUESAS", R.drawable.hamburguesa);
        signLanguageMap.put("HARINA", R.drawable.harina);
        signLanguageMap.put("HELADO", R.drawable.helado);
        signLanguageMap.put("HELADOS", R.drawable.helado);
        signLanguageMap.put("HERENCIA", R.drawable.herencia);
        signLanguageMap.put("HERENCIAS", R.drawable.herencia);
        signLanguageMap.put("HERMANA", R.drawable.hermana);
        signLanguageMap.put("HERMANAS", R.drawable.hermana);
        signLanguageMap.put("HERMANO", R.drawable.hermano);
        signLanguageMap.put("HERMANOS", R.drawable.hermano);
        signLanguageMap.put("HIJA", R.drawable.hija);
        signLanguageMap.put("HIJAS", R.drawable.hija);
        signLanguageMap.put("HIJASTRA", R.drawable.hijastra);
        signLanguageMap.put("HIJASTRAS", R.drawable.hijastra);
        signLanguageMap.put("HIJO", R.drawable.hijo);
        signLanguageMap.put("HIJOS", R.drawable.hijo);
        signLanguageMap.put("HIPOPOTAMO", R.drawable.hipopotamo);
        signLanguageMap.put("HIPOPOTAMOS", R.drawable.hipopotamo);
        signLanguageMap.put("HOGAR", R.drawable.hogar);
        signLanguageMap.put("HOGARES", R.drawable.hogar);
        signLanguageMap.put("HOMBRE", R.drawable.hombre);
        signLanguageMap.put("HOMBRES", R.drawable.hombre);
        signLanguageMap.put("HOMBRO", R.drawable.hombro);
        signLanguageMap.put("HOMBROS", R.drawable.hombro);
        signLanguageMap.put("HORMIGA", R.drawable.hormiga);
        signLanguageMap.put("HORMIGAS", R.drawable.hormiga);
        signLanguageMap.put("HORNO", R.drawable.horno);
        signLanguageMap.put("HORNOS", R.drawable.horno);
        signLanguageMap.put("HOTDOG", R.drawable.hot_dog);
        signLanguageMap.put("HOTDOGS", R.drawable.hot_dog);
        signLanguageMap.put("HOY", R.drawable.hoy);
        signLanguageMap.put("HUERFANA", R.drawable.huerfana);
        signLanguageMap.put("HUERFANAS", R.drawable.huerfana);
        signLanguageMap.put("HUERFANO", R.drawable.huerfano);
        signLanguageMap.put("HUERFANOS", R.drawable.huerfano);
        signLanguageMap.put("HUESO", R.drawable.hueso);
        signLanguageMap.put("HUESOS", R.drawable.hueso);
        signLanguageMap.put("HUEVO", R.drawable.huevo);
        signLanguageMap.put("HUEVOS", R.drawable.huevo);
        signLanguageMap.put("IGUANA", R.drawable.iguana);
        signLanguageMap.put("IGUANAS", R.drawable.iguana);
        signLanguageMap.put("IMPOSIBLE", R.drawable.imposible);
        signLanguageMap.put("INVIERNO", R.drawable.invierno);
        signLanguageMap.put("INVIERNOS", R.drawable.invierno);
        signLanguageMap.put("IZQUIERDA", R.drawable.izquierda);
        signLanguageMap.put("JABALI", R.drawable.jabali);
        signLanguageMap.put("JABALIES", R.drawable.jabali);
        signLanguageMap.put("JABON", R.drawable.jabon);
        signLanguageMap.put("JABONES", R.drawable.jabon);
        signLanguageMap.put("JAMON", R.drawable.jamon);
        signLanguageMap.put("JARDIN", R.drawable.jardin);
        signLanguageMap.put("JARDINES", R.drawable.jardin);
        signLanguageMap.put("JERGA", R.drawable.jerga);
        signLanguageMap.put("JIRAFA", R.drawable.jirafa);
        signLanguageMap.put("JIRAFAS", R.drawable.jirafa);
        signLanguageMap.put("JOVEN", R.drawable.joven);
        signLanguageMap.put("JOVENES", R.drawable.joven);
        signLanguageMap.put("JUEVES", R.drawable.jueves);
        signLanguageMap.put("JULIO", R.drawable.julio);
        signLanguageMap.put("JUNIO", R.drawable.junio);
        signLanguageMap.put("JUVENIL", R.drawable.juvenil);
        signLanguageMap.put("JUVENTUD", R.drawable.juventud);
        signLanguageMap.put("LABIO", R.drawable.labios);
        signLanguageMap.put("LABIOS", R.drawable.labios);
        signLanguageMap.put("LAMPARA", R.drawable.lampara);
        signLanguageMap.put("LAMPARAS", R.drawable.lampara);
        signLanguageMap.put("LAPIZ", R.drawable.lapiz);
        signLanguageMap.put("LAPICES", R.drawable.lapiz);
        signLanguageMap.put("LARGO", R.drawable.largo);
        signLanguageMap.put("LARGA", R.drawable.largo);
        signLanguageMap.put("LARGOS", R.drawable.largo);
        signLanguageMap.put("LAVADO", R.drawable.lavado);
        signLanguageMap.put("LAVADOS", R.drawable.lavado);
        signLanguageMap.put("LAVADA", R.drawable.lavado);
        signLanguageMap.put("LAVADAS", R.drawable.lavado);
        signLanguageMap.put("LAVADORA", R.drawable.lavadora);
        signLanguageMap.put("LAVADORAS", R.drawable.lavadora);
        signLanguageMap.put("LECCION", R.drawable.leccion);
        signLanguageMap.put("LECCIONES", R.drawable.leccion);
        signLanguageMap.put("LECHE", R.drawable.leche);
        signLanguageMap.put("LECHES", R.drawable.leche);
        signLanguageMap.put("LEGUMBRES", R.drawable.legumbres);
        signLanguageMap.put("LEGUMBRE", R.drawable.legumbres);
        signLanguageMap.put("LENGUA", R.drawable.lengua);
        signLanguageMap.put("LENGUAS", R.drawable.lengua);
        signLanguageMap.put("LEON", R.drawable.leon);
        signLanguageMap.put("LEONES", R.drawable.leon);
        signLanguageMap.put("LICOR", R.drawable.licor);
        signLanguageMap.put("LICORES", R.drawable.licor);
        signLanguageMap.put("LICUADORA", R.drawable.licuadora);
        signLanguageMap.put("LICUADORAS", R.drawable.licuadora);
        signLanguageMap.put("LICUADOR", R.drawable.licuadora);
        signLanguageMap.put("LIEBRE", R.drawable.liebre);
        signLanguageMap.put("LIEBRES", R.drawable.liebre);
        signLanguageMap.put("LIMONADA", R.drawable.limonada);
        signLanguageMap.put("LIMONADAS", R.drawable.limonada);
        signLanguageMap.put("LIMPIO", R.drawable.limpio);
        signLanguageMap.put("LIMPIOS", R.drawable.limpio);
        signLanguageMap.put("LIMPIA", R.drawable.limpio);
        signLanguageMap.put("LIMPIAS", R.drawable.limpio);
        signLanguageMap.put("LLAVE", R.drawable.llave);
        signLanguageMap.put("LLAVES", R.drawable.llave);
        signLanguageMap.put("LLENO", R.drawable.lleno);
        signLanguageMap.put("LLENOS", R.drawable.lleno);
        signLanguageMap.put("LLENA", R.drawable.lleno);
        signLanguageMap.put("LLENAS", R.drawable.lleno);
        signLanguageMap.put("LLORAR", R.drawable.llorar);
        signLanguageMap.put("LLORA", R.drawable.llorar);
        signLanguageMap.put("LLORO", R.drawable.llorar);
        signLanguageMap.put("LOBO", R.drawable.lobo);
        signLanguageMap.put("LOBOS", R.drawable.lobo);
        signLanguageMap.put("LUNES", R.drawable.lunes);
        signLanguageMap.put("LUZ", R.drawable.luz);
        signLanguageMap.put("LUCES", R.drawable.luz);
        signLanguageMap.put("MADRASTRA", R.drawable.madrastra);
        signLanguageMap.put("MADRASTRAS", R.drawable.madrastra);
        signLanguageMap.put("MADRE", R.drawable.madre);
        signLanguageMap.put("MADRES", R.drawable.madre);
        signLanguageMap.put("MADRINA", R.drawable.madrina);
        signLanguageMap.put("MADRINAS", R.drawable.madrina);
        signLanguageMap.put("MAL", R.drawable.mal);
        signLanguageMap.put("MALES", R.drawable.mal);
        signLanguageMap.put("MALASUERTE", R.drawable.mala_suerte);
        signLanguageMap.put("MALO", R.drawable.malo);
        signLanguageMap.put("MALA", R.drawable.malo);
        signLanguageMap.put("MALOS", R.drawable.malo);
        signLanguageMap.put("MALAS", R.drawable.malo);
        signLanguageMap.put("MAMA", R.drawable.mama);
        signLanguageMap.put("MAMÁ", R.drawable.mama);
        signLanguageMap.put("MAMÁS", R.drawable.mama);
        signLanguageMap.put("MAMAS", R.drawable.mama);
        signLanguageMap.put("MAMILA", R.drawable.mamila);
        signLanguageMap.put("MAMILAS", R.drawable.mamila);
        signLanguageMap.put("MANO", R.drawable.mano);
        signLanguageMap.put("MANOS", R.drawable.mano);
        signLanguageMap.put("MANTEL", R.drawable.mantel);
        signLanguageMap.put("MANTELES", R.drawable.mantel);
        signLanguageMap.put("MANTEQUILLA", R.drawable.mantequilla);
        signLanguageMap.put("MANTEQUILLAS", R.drawable.mantequilla);
        signLanguageMap.put("MAÑANA", R.drawable.manana);
        signLanguageMap.put("MAÑANAS", R.drawable.manana);
        signLanguageMap.put("MAPA", R.drawable.mapa);
        signLanguageMap.put("MAPAS", R.drawable.mapa);
        signLanguageMap.put("MARIPOSA", R.drawable.mariposa);
        signLanguageMap.put("MARIPOSAS", R.drawable.mariposa);
        signLanguageMap.put("MARTES", R.drawable.martes);
        signLanguageMap.put("MARZO", R.drawable.marzo);
        signLanguageMap.put("MAS", R.drawable.mas);
        signLanguageMap.put("MASCULINO", R.drawable.masculino);
        signLanguageMap.put("MASCULINOS", R.drawable.masculino);
        signLanguageMap.put("MATRIMONIO", R.drawable.matrimonio);
        signLanguageMap.put("MATRIMONIOS", R.drawable.matrimonio);
        signLanguageMap.put("MAYO", R.drawable.mayo);
        signLanguageMap.put("MAYONESA", R.drawable.mayonesa);
        signLanguageMap.put("MEDIODIA", R.drawable.mediodia);
        signLanguageMap.put("MEJILLA", R.drawable.mejilla);
        signLanguageMap.put("MEJILLAS", R.drawable.mejilla);
        signLanguageMap.put("MEJOR", R.drawable.mejor);
        signLanguageMap.put("MEJORES", R.drawable.mejor);
        signLanguageMap.put("MENOS", R.drawable.menos);
        signLanguageMap.put("MENTIRA", R.drawable.mentira);
        signLanguageMap.put("MENTIRAS", R.drawable.mentira);
        signLanguageMap.put("MERMELADA", R.drawable.mermelada);
        signLanguageMap.put("MERMELADAS", R.drawable.mermelada);
        signLanguageMap.put("MES", R.drawable.mes);
        signLanguageMap.put("MESES", R.drawable.mes);
        signLanguageMap.put("MESA", R.drawable.mesa);
        signLanguageMap.put("MESAS", R.drawable.mesa);
        signLanguageMap.put("MICROSCOPIO", R.drawable.microscopio);
        signLanguageMap.put("MICROSCOPIOS", R.drawable.microscopio);
        signLanguageMap.put("MIEL", R.drawable.miel);
        signLanguageMap.put("MIELES", R.drawable.miel);
        signLanguageMap.put("MIERCOLES", R.drawable.miercoles);
        signLanguageMap.put("MIO", R.drawable.mio);
        signLanguageMap.put("MI", R.drawable.mio);
        signLanguageMap.put("MISMO", R.drawable.mismo);
        signLanguageMap.put("MISMOS", R.drawable.mismo);
        signLanguageMap.put("MOJADO", R.drawable.mojado);
        signLanguageMap.put("MOJADOS", R.drawable.mojado);
        signLanguageMap.put("MOJADA", R.drawable.mojado);
        signLanguageMap.put("MOJADAS", R.drawable.mojado);
        signLanguageMap.put("MOLDE", R.drawable.molde);
        signLanguageMap.put("MOLDES", R.drawable.molde);
        signLanguageMap.put("MOLE", R.drawable.mole);
        signLanguageMap.put("MOLES", R.drawable.mole);
        signLanguageMap.put("MONO", R.drawable.mono);
        signLanguageMap.put("MONOS", R.drawable.mono);
        signLanguageMap.put("MORADO", R.drawable.morado);
        signLanguageMap.put("MORADOS", R.drawable.morado);
        signLanguageMap.put("MORADA", R.drawable.morado);
        signLanguageMap.put("MORADAS", R.drawable.morado);
        signLanguageMap.put("MOSCA", R.drawable.mosca);
        signLanguageMap.put("MOSCAS", R.drawable.mosca);
        signLanguageMap.put("MOSQUITO", R.drawable.mosquito);
        signLanguageMap.put("MOSQUITOS", R.drawable.mosquito);
        signLanguageMap.put("MOSCOS", R.drawable.mosquito);
        signLanguageMap.put("MUCHO", R.drawable.mucho);
        signLanguageMap.put("MUCHOS", R.drawable.mucho);
        signLanguageMap.put("MULA", R.drawable.mula);
        signLanguageMap.put("MULAS", R.drawable.mula);
        signLanguageMap.put("MUÑECO", R.drawable.muneco);
        signLanguageMap.put("MUÑECOS", R.drawable.muneco);
        signLanguageMap.put("MUÑECA", R.drawable.muneco);
        signLanguageMap.put("MUÑECAS", R.drawable.muneco);
        signLanguageMap.put("MURCIELAGO", R.drawable.murcielago);
        signLanguageMap.put("MURCIELAGOS", R.drawable.murcielago);
        signLanguageMap.put("NARIZ", R.drawable.nariz);
        signLanguageMap.put("NARICES", R.drawable.nariz);
        signLanguageMap.put("NEGRO", R.drawable.negro);
        signLanguageMap.put("NEGROS", R.drawable.negro);
        signLanguageMap.put("NEGRA", R.drawable.negro);
        signLanguageMap.put("NEGRAS", R.drawable.negro);
        signLanguageMap.put("NI", R.drawable.ni);
        signLanguageMap.put("NO", R.drawable.no);
        signLanguageMap.put("NOCHE", R.drawable.noche);
        signLanguageMap.put("NOCHES", R.drawable.noche);
        signLanguageMap.put("NOVIEMBRE", R.drawable.noviembre);
        signLanguageMap.put("NUESTRO", R.drawable.nuestro);
        signLanguageMap.put("NUESTROS", R.drawable.nuestro);
        signLanguageMap.put("NUESTRA", R.drawable.nuestro);
        signLanguageMap.put("NUESTRAS", R.drawable.nuestro);
        signLanguageMap.put("NUNCA", R.drawable.nunca);
        signLanguageMap.put("NUNCAS", R.drawable.nunca);
        signLanguageMap.put("OCTUBRE", R.drawable.octubre);
        signLanguageMap.put("OJO", R.drawable.ojo);
        signLanguageMap.put("OJOS", R.drawable.ojo);
        signLanguageMap.put("OLLAEXPRES", R.drawable.olla_expres);
        signLanguageMap.put("OLLA", R.drawable.olla_expres);
        signLanguageMap.put("OLLAS", R.drawable.olla_expres);
        signLanguageMap.put("OLVIDAR", R.drawable.olvidar);
        signLanguageMap.put("OLVIDA", R.drawable.olvidar);
        signLanguageMap.put("OLVIDO", R.drawable.olvidar);
        signLanguageMap.put("OLVIDASTE", R.drawable.olvidar);
        signLanguageMap.put("OLVIDAS", R.drawable.olvidar);
        signLanguageMap.put("OREJA", R.drawable.oreja);
        signLanguageMap.put("OREJAS", R.drawable.oreja);
        signLanguageMap.put("ORO", R.drawable.oro);
        signLanguageMap.put("OROS", R.drawable.oro);
        signLanguageMap.put("OSCURIDAD", R.drawable.oscuridad);
        signLanguageMap.put("OSCURIDADES", R.drawable.oscuridad);
        signLanguageMap.put("OSCURO", R.drawable.oscuro);
        signLanguageMap.put("OSCURA", R.drawable.oscuro);
        signLanguageMap.put("OSCUROS", R.drawable.oscuro);
        signLanguageMap.put("OSCURAS", R.drawable.oscuro);
        signLanguageMap.put("OSO", R.drawable.oso);
        signLanguageMap.put("OSOS", R.drawable.oso);
        signLanguageMap.put("OSTION", R.drawable.ostion);
        signLanguageMap.put("OSTIONES", R.drawable.ostion);
        signLanguageMap.put("OTOÑO", R.drawable.otono);
        signLanguageMap.put("PAJARO", R.drawable.pajaro);
        signLanguageMap.put("PAJAROS", R.drawable.pajaro);
        signLanguageMap.put("PALETA", R.drawable.paleta);
        signLanguageMap.put("PALETAS", R.drawable.paleta);
        signLanguageMap.put("PALOMA", R.drawable.paloma);
        signLanguageMap.put("PALOMAS", R.drawable.paloma);
        signLanguageMap.put("PAN", R.drawable.pan);
        signLanguageMap.put("PANES", R.drawable.pan);
        signLanguageMap.put("PANTERA", R.drawable.pantera);
        signLanguageMap.put("PANTERAS", R.drawable.pantera);
        signLanguageMap.put("PAPEL", R.drawable.papel);
        signLanguageMap.put("PAPELES", R.drawable.papel);
        signLanguageMap.put("PARA", R.drawable.para);
        signLanguageMap.put("PARED", R.drawable.pared);
        signLanguageMap.put("PAREDES", R.drawable.pared);
        signLanguageMap.put("PARQUE", R.drawable.parque);
        signLanguageMap.put("PARQUES", R.drawable.parque);
        signLanguageMap.put("PARRAFO", R.drawable.parrafo);
        signLanguageMap.put("PARRAFOS", R.drawable.parrafo);
        signLanguageMap.put("PARRILLA", R.drawable.parilla);
        signLanguageMap.put("PARRILLAS", R.drawable.parilla);
        signLanguageMap.put("PASADO", R.drawable.pasado);
        signLanguageMap.put("PASADA", R.drawable.pasado);
        signLanguageMap.put("PASADAS", R.drawable.pasado);
        signLanguageMap.put("PASADOS", R.drawable.pasado);
        signLanguageMap.put("PASTA", R.drawable.pasta_de_dientes);
        signLanguageMap.put("PASTEL", R.drawable.pastel);
        signLanguageMap.put("PASTELES", R.drawable.pastel);
        signLanguageMap.put("PATIO", R.drawable.patio);
        signLanguageMap.put("PATIOS", R.drawable.patio);
        signLanguageMap.put("PATO", R.drawable.pato);
        signLanguageMap.put("PATOS", R.drawable.pato);
        signLanguageMap.put("PAVOREAL", R.drawable.pavo_real);
        signLanguageMap.put("PAY", R.drawable.pay);
        signLanguageMap.put("PAYS", R.drawable.pay);
        signLanguageMap.put("PECHO", R.drawable.pecho);
        signLanguageMap.put("PECHOS", R.drawable.pecho);
        signLanguageMap.put("PEDIR", R.drawable.pedir);
        signLanguageMap.put("PEDI", R.drawable.pedir);
        signLanguageMap.put("PELICANO", R.drawable.pelicano);
        signLanguageMap.put("PELICANOS", R.drawable.pelicano);
        signLanguageMap.put("PELO", R.drawable.pelo);
        signLanguageMap.put("PELOS", R.drawable.pelo);
        signLanguageMap.put("PEOR", R.drawable.peor);
        signLanguageMap.put("PEORES", R.drawable.peor);
        signLanguageMap.put("PERDER", R.drawable.perder);
        signLanguageMap.put("PIERDE", R.drawable.perder);
        signLanguageMap.put("PIERDES", R.drawable.perder);
        signLanguageMap.put("PERICO", R.drawable.perico);
        signLanguageMap.put("PERICOS", R.drawable.perico);
        signLanguageMap.put("PERRO", R.drawable.perro);
        signLanguageMap.put("PERROS", R.drawable.perro);
        signLanguageMap.put("PERSPECTIVA", R.drawable.perspectiva);
        signLanguageMap.put("PERSPECTIVAS", R.drawable.perspectiva);
        signLanguageMap.put("PESTAÑA", R.drawable.pestana);
        signLanguageMap.put("PESTAÑAS", R.drawable.pestana);
        signLanguageMap.put("PEZ", R.drawable.pez);
        signLanguageMap.put("PECES", R.drawable.pez);
        signLanguageMap.put("PICOSO", R.drawable.picoso);
        signLanguageMap.put("PICOSA", R.drawable.picoso);
        signLanguageMap.put("PICOSOS", R.drawable.picoso);
        signLanguageMap.put("PICOSAS", R.drawable.picoso);
        signLanguageMap.put("PILONCILLO", R.drawable.piloncillo);
        signLanguageMap.put("PILONCILLOS", R.drawable.piloncillo);
        signLanguageMap.put("PIMIENTA", R.drawable.pimienta);
        signLanguageMap.put("PIMIENTAS", R.drawable.pimienta);
        signLanguageMap.put("PINGUINO", R.drawable.pinguino);
        signLanguageMap.put("PINGUINOS", R.drawable.pinguino);
        signLanguageMap.put("PISO", R.drawable.piso);
        signLanguageMap.put("PISAR", R.drawable.piso);
        signLanguageMap.put("PISA", R.drawable.piso);
        signLanguageMap.put("PISASTE", R.drawable.piso);
        signLanguageMap.put("PISOS", R.drawable.piso);
        signLanguageMap.put("PIZARRON", R.drawable.pizarron);
        signLanguageMap.put("PIZARRONES", R.drawable.pizarron);
        signLanguageMap.put("PIZZA", R.drawable.pizza);
        signLanguageMap.put("PIZZAS", R.drawable.pizza);
        signLanguageMap.put("PLATA", R.drawable.plata);
        signLanguageMap.put("PLATAS", R.drawable.plata);
        signLanguageMap.put("PLATO", R.drawable.plato);
        signLanguageMap.put("PLATOS", R.drawable.plato);
        signLanguageMap.put("PLUMA", R.drawable.pluma);
        signLanguageMap.put("PLUMAS", R.drawable.pluma);
        signLanguageMap.put("POBRE", R.drawable.pobre);
        signLanguageMap.put("POBRES", R.drawable.pobre);
        signLanguageMap.put("POCO", R.drawable.poco);
        signLanguageMap.put("POCOS", R.drawable.poco);
        signLanguageMap.put("POCA", R.drawable.poco);
        signLanguageMap.put("POCAS", R.drawable.poco);
        signLanguageMap.put("POLITECNICO", R.drawable.politecnico);
        signLanguageMap.put("POLITECNICOS", R.drawable.politecnico);
        signLanguageMap.put("POLLO", R.drawable.pollo);
        signLanguageMap.put("POLLOS", R.drawable.pollo);
        signLanguageMap.put("POLVO", R.drawable.polvo);
        signLanguageMap.put("POLVOS", R.drawable.polvo);
        signLanguageMap.put("PONER", R.drawable.poner);
        signLanguageMap.put("PON", R.drawable.poner);
        signLanguageMap.put("PONTE", R.drawable.poner);
        signLanguageMap.put("PONERTE", R.drawable.poner);
        signLanguageMap.put("POR", R.drawable.por);
        signLanguageMap.put("POSIBLE", R.drawable.posible);
        signLanguageMap.put("POSIBLES", R.drawable.posible);
        signLanguageMap.put("POSTRE", R.drawable.postre);
        signLanguageMap.put("POSTRES", R.drawable.postre);
        signLanguageMap.put("PREGUNTAR", R.drawable.preguntar);
        signLanguageMap.put("PREGUNTA", R.drawable.preguntar);
        signLanguageMap.put("PREGUNTARSE", R.drawable.preguntar);
        signLanguageMap.put("PREMIO", R.drawable.premio);
        signLanguageMap.put("PREMIOS", R.drawable.premio);
        signLanguageMap.put("PRENDER", R.drawable.prender);
        signLanguageMap.put("PRENDERSE", R.drawable.prender);
        signLanguageMap.put("PRENDE", R.drawable.prender);
        signLanguageMap.put("PREPARATORIA", R.drawable.preparatoria);
        signLanguageMap.put("PREPARATORIAS", R.drawable.preparatoria);
        signLanguageMap.put("PRESENTE", R.drawable.presente);
        signLanguageMap.put("PRESENTES", R.drawable.presente);
        signLanguageMap.put("PRIMARIA", R.drawable.primaria);
        signLanguageMap.put("PRIMARIAS", R.drawable.primaria);
        signLanguageMap.put("PRIMAVERA", R.drawable.primavera);
        signLanguageMap.put("PRIMAVERAS", R.drawable.primavera);
        signLanguageMap.put("PRIMERO", R.drawable.primero);
        signLanguageMap.put("PRIMEROS", R.drawable.primero);
        signLanguageMap.put("PROPIO", R.drawable.propio);
        signLanguageMap.put("PROPIOS", R.drawable.propio);
        signLanguageMap.put("PRUEBA", R.drawable.prueba);
        signLanguageMap.put("PRUEBAS", R.drawable.prueba);
        signLanguageMap.put("ESPIN", R.drawable.puerco_espon);
        signLanguageMap.put("PUERCO", R.drawable.puerco);
        signLanguageMap.put("PUERCOS", R.drawable.puerco);
        signLanguageMap.put("PUERTA", R.drawable.puerta);
        signLanguageMap.put("PUERTAS", R.drawable.puerta);
        signLanguageMap.put("PULGAR", R.drawable.pulgar);
        signLanguageMap.put("PULGARES", R.drawable.pulgar);
        signLanguageMap.put("PULPO", R.drawable.pulpo);
        signLanguageMap.put("PULPOS", R.drawable.pulpo);
        signLanguageMap.put("PULQUE", R.drawable.pulque);
        signLanguageMap.put("QUESADILLA", R.drawable.quesadilla);
        signLanguageMap.put("QUESADILLAS", R.drawable.quesadilla);
        signLanguageMap.put("QUESO", R.drawable.queso);
        signLanguageMap.put("QUESOS", R.drawable.queso);
        signLanguageMap.put("QUIMICA", R.drawable.quimica);
        signLanguageMap.put("QUIMICO", R.drawable.quimica);
        signLanguageMap.put("QUITAR", R.drawable.quitar);
        signLanguageMap.put("QUITARSE", R.drawable.quitar);
        signLanguageMap.put("QUITARLO", R.drawable.quitar);
        signLanguageMap.put("QUITO", R.drawable.quitar);
        signLanguageMap.put("QUITA", R.drawable.quitar);
        signLanguageMap.put("QUITATE", R.drawable.quitar);
        signLanguageMap.put("RANA", R.drawable.rana);
        signLanguageMap.put("RANAS", R.drawable.rana);
        signLanguageMap.put("RAPIDO", R.drawable.rapido);
        signLanguageMap.put("RAPIDA", R.drawable.rapido);
        signLanguageMap.put("RAPIDOS", R.drawable.rapido);
        signLanguageMap.put("RAPIDAS", R.drawable.rapido);
        signLanguageMap.put("RASURAR", R.drawable.rasurar);
        signLanguageMap.put("RASURA", R.drawable.rasurar);
        signLanguageMap.put("RASURO", R.drawable.rasurar);
        signLanguageMap.put("RASURANDO", R.drawable.rasurar);
        signLanguageMap.put("RASURARSE", R.drawable.rasurar);
        signLanguageMap.put("RATON", R.drawable.raton);
        signLanguageMap.put("RATONES", R.drawable.raton);
        signLanguageMap.put("RECORDAR", R.drawable.recordar);
        signLanguageMap.put("RECORDARLO", R.drawable.recordar);
        signLanguageMap.put("RECORDARLA", R.drawable.recordar);
        signLanguageMap.put("RECUERDA", R.drawable.recordar);
        signLanguageMap.put("RECUERDAS", R.drawable.recordar);
        signLanguageMap.put("RECUERDO", R.drawable.recordar);
        signLanguageMap.put("RECUERDOS", R.drawable.recordar);
        signLanguageMap.put("REFRESCO", R.drawable.refresco);
        signLanguageMap.put("REFRESCOS", R.drawable.refresco);
        signLanguageMap.put("REFRIGERADOR", R.drawable.refrigerador);
        signLanguageMap.put("REFRIGERADORES", R.drawable.refrigerador);
        signLanguageMap.put("REGADERA", R.drawable.regadera);
        signLanguageMap.put("REGADERAS", R.drawable.regadera);
        signLanguageMap.put("REGAÑAR", R.drawable.reganar);
        signLanguageMap.put("REGAÑA", R.drawable.reganar);
        signLanguageMap.put("REGAÑO", R.drawable.reganar);
        signLanguageMap.put("REGAÑOS", R.drawable.reganar);
        signLanguageMap.put("REGAÑAS", R.drawable.reganar);
        signLanguageMap.put("REIR", R.drawable.reir);
        signLanguageMap.put("REIRSE", R.drawable.reir);
        signLanguageMap.put("RISA", R.drawable.reir);
        signLanguageMap.put("GRACIA", R.drawable.reir);
        signLanguageMap.put("RISAS", R.drawable.reir);
        signLanguageMap.put("REJA", R.drawable.reja);
        signLanguageMap.put("REJAS", R.drawable.reja);
        signLanguageMap.put("RELOJ", R.drawable.reloj);
        signLanguageMap.put("RELOJES", R.drawable.reloj);
        signLanguageMap.put("RESPONDER", R.drawable.responder);
        signLanguageMap.put("RESPONDE", R.drawable.responder);
        signLanguageMap.put("RESPONDES", R.drawable.responder);
        signLanguageMap.put("RICO", R.drawable.rico);
        signLanguageMap.put("RICA", R.drawable.rico);
        signLanguageMap.put("RICAS", R.drawable.rico);
        signLanguageMap.put("RICOS", R.drawable.rico);
        signLanguageMap.put("RINOCERONTE", R.drawable.rinoceronte);
        signLanguageMap.put("RINOCERONTES", R.drawable.rinoceronte);
        signLanguageMap.put("ROJO", R.drawable.rojo);
        signLanguageMap.put("ROJOS", R.drawable.rojo);
        signLanguageMap.put("ROPERO", R.drawable.ropero);
        signLanguageMap.put("ROPEROS", R.drawable.ropero);
        signLanguageMap.put("ROSA", R.drawable.rosa);
        signLanguageMap.put("ROSAS", R.drawable.rosa);
        signLanguageMap.put("ROSADO", R.drawable.rosa);
        signLanguageMap.put("ROSADA", R.drawable.rosa);
        signLanguageMap.put("SABADO", R.drawable.sabado);
        signLanguageMap.put("SABANA", R.drawable.sabana);
        signLanguageMap.put("SABANAS", R.drawable.sabana);
        signLanguageMap.put("SABROSO", R.drawable.sabroso);
        signLanguageMap.put("SABROSA", R.drawable.sabroso);
        signLanguageMap.put("SABROSAS", R.drawable.sabroso);
        signLanguageMap.put("SABROSOS", R.drawable.sabroso);
        signLanguageMap.put("SACAPUNTAS", R.drawable.sacapuntas);
        signLanguageMap.put("SAL", R.drawable.sal);
        signLanguageMap.put("SALES", R.drawable.sal);
        signLanguageMap.put("SALA", R.drawable.sala);
        signLanguageMap.put("SALAS", R.drawable.sala);
        signLanguageMap.put("SALCHICHA", R.drawable.salchicha);
        signLanguageMap.put("SALCHICHAS", R.drawable.salchicha);
        signLanguageMap.put("SALERO", R.drawable.salero);
        signLanguageMap.put("SALEROS", R.drawable.salero);
        signLanguageMap.put("SALIR", R.drawable.salir);
        signLanguageMap.put("SALIRSE", R.drawable.salir);
        signLanguageMap.put("SALIENDO", R.drawable.salir);
        signLanguageMap.put("SALSA", R.drawable.salsa);
        signLanguageMap.put("SALSAS", R.drawable.salsa);
        signLanguageMap.put("SAPO", R.drawable.sapo);
        signLanguageMap.put("SAPOS", R.drawable.sapo);
        signLanguageMap.put("SARTEN", R.drawable.sarten);
        signLanguageMap.put("SARTENES", R.drawable.sarten);
        signLanguageMap.put("SATISFECHO", R.drawable.satisfecho);
        signLanguageMap.put("SATISFECHOS", R.drawable.satisfecho);
        signLanguageMap.put("SATISFECHA", R.drawable.satisfecho);
        signLanguageMap.put("SATISFECHAS", R.drawable.satisfecho);
        signLanguageMap.put("SECADORA", R.drawable.secadora);
        signLanguageMap.put("SECADOR", R.drawable.secadora);
        signLanguageMap.put("SECADORES", R.drawable.secadora);
        signLanguageMap.put("SECO", R.drawable.seco);
        signLanguageMap.put("SECOS", R.drawable.seco);
        signLanguageMap.put("SECA", R.drawable.seco);
        signLanguageMap.put("SECAS", R.drawable.seco);
        signLanguageMap.put("SECAR", R.drawable.seco);
        signLanguageMap.put("SECUNDARIA", R.drawable.secundaria);
        signLanguageMap.put("SECUNDARIAS", R.drawable.secundaria);
        signLanguageMap.put("SEMANA", R.drawable.semana);
        signLanguageMap.put("SEMANAS", R.drawable.semana);
        signLanguageMap.put("SEPTIEMBRE", R.drawable.septiembre);
        signLanguageMap.put("SERVILLETA", R.drawable.servilleta);
        signLanguageMap.put("SERVILLETAS", R.drawable.servilleta);
        signLanguageMap.put("SI", R.drawable.si);
        signLanguageMap.put("SIEMPRE", R.drawable.siempre);
        signLanguageMap.put("SILLA", R.drawable.silla);
        signLanguageMap.put("SILLAS", R.drawable.silla);
        signLanguageMap.put("SILLON", R.drawable.sillon);
        signLanguageMap.put("SILLONES", R.drawable.sillon);
        signLanguageMap.put("SIN", R.drawable.sin);
        signLanguageMap.put("SOFA", R.drawable.sofa);
        signLanguageMap.put("SOFAS", R.drawable.sofa);
        signLanguageMap.put("SOLO", R.drawable.solo);
        signLanguageMap.put("SOLOS", R.drawable.solo);
        signLanguageMap.put("SOLA", R.drawable.solo);
        signLanguageMap.put("SOLAS", R.drawable.solo);
        signLanguageMap.put("SOPA", R.drawable.sopa);
        signLanguageMap.put("SOPAS", R.drawable.sopa);
        signLanguageMap.put("SOPE", R.drawable.sope);
        signLanguageMap.put("SOPES", R.drawable.sope);
        signLanguageMap.put("SOTANO", R.drawable.sotano);
        signLanguageMap.put("SUAVE", R.drawable.suave);
        signLanguageMap.put("SUAVES", R.drawable.suave);
        signLanguageMap.put("SUCIO", R.drawable.sucio);
        signLanguageMap.put("SUCIOS", R.drawable.sucio);
        signLanguageMap.put("SUYO", R.drawable.suyo);
        signLanguageMap.put("SUYA", R.drawable.suyo);
        signLanguageMap.put("SUYOS", R.drawable.suyo);
        signLanguageMap.put("SUYAS", R.drawable.suyo);
        signLanguageMap.put("TACO", R.drawable.taco);
        signLanguageMap.put("TACOS", R.drawable.taco);
        signLanguageMap.put("TAMAL", R.drawable.tamal);
        signLanguageMap.put("TAMALES", R.drawable.tamal);
        signLanguageMap.put("TAMBIEN", R.drawable.tambien);
        signLanguageMap.put("TAPADERA", R.drawable.tapadera);
        signLanguageMap.put("TAPADERAS", R.drawable.tapadera);
        signLanguageMap.put("TAPETE", R.drawable.tapete);
        signLanguageMap.put("TAPETES", R.drawable.tapete);
        signLanguageMap.put("TAPIZ", R.drawable.tapiz);
        signLanguageMap.put("TAPICES", R.drawable.tapiz);
        signLanguageMap.put("TARDE", R.drawable.tarde);
        signLanguageMap.put("TARDES", R.drawable.tarde);
        signLanguageMap.put("TAZA", R.drawable.taza);
        signLanguageMap.put("TAZAS", R.drawable.taza);
        signLanguageMap.put("TÉ", R.drawable.te);
        signLanguageMap.put("TÉS", R.drawable.te);
        signLanguageMap.put("TEMPRANO", R.drawable.temprano);
        signLanguageMap.put("TENDEDERO", R.drawable.tendedero);
        signLanguageMap.put("TENDEDEROS", R.drawable.tendedero);
        signLanguageMap.put("TENEDOR", R.drawable.tenedor);
        signLanguageMap.put("TENEDORES", R.drawable.tenedor);
        signLanguageMap.put("TERMINAR", R.drawable.terminar);
        signLanguageMap.put("TERMINARLO", R.drawable.terminar);
        signLanguageMap.put("TERMINARLOS", R.drawable.terminar);
        signLanguageMap.put("TERMINA", R.drawable.terminar);
        signLanguageMap.put("TERMINO", R.drawable.terminar);
        signLanguageMap.put("TERMINALO", R.drawable.terminar);
        signLanguageMap.put("TERMINALA", R.drawable.terminar);
        signLanguageMap.put("TERMINALAS", R.drawable.terminar);
        signLanguageMap.put("TERMINALOS", R.drawable.terminar);
        signLanguageMap.put("TERMINARLA", R.drawable.terminar);
        signLanguageMap.put("TERMINARLAS", R.drawable.terminar);
        signLanguageMap.put("TIBURON", R.drawable.tiburon);
        signLanguageMap.put("TIBURONES", R.drawable.tiburon);
        signLanguageMap.put("TIGRE", R.drawable.tigre);
        signLanguageMap.put("TIGRES", R.drawable.tigre);
        signLanguageMap.put("TIJERAS", R.drawable.tijeras);
        signLanguageMap.put("TIJERA", R.drawable.tijeras);
        signLanguageMap.put("TIMBRE", R.drawable.timbre);
        signLanguageMap.put("TIMBRES", R.drawable.timbre);
        signLanguageMap.put("TINA", R.drawable.tina);
        signLanguageMap.put("TINAS", R.drawable.tina);
        signLanguageMap.put("TOALLA", R.drawable.toalla);
        signLanguageMap.put("TOALLAS", R.drawable.toalla);
        signLanguageMap.put("TOCADOR", R.drawable.tocador);
        signLanguageMap.put("TOCADORES", R.drawable.tocador);
        signLanguageMap.put("TODAVIA", R.drawable.todavia);
        signLanguageMap.put("TODO", R.drawable.todo);
        signLanguageMap.put("TODOS", R.drawable.todo);
        signLanguageMap.put("TODA", R.drawable.todo);
        signLanguageMap.put("TODAS", R.drawable.todo);
        signLanguageMap.put("TORO", R.drawable.toro);
        signLanguageMap.put("TOROS", R.drawable.toro);
        signLanguageMap.put("TORTA", R.drawable.torta);
        signLanguageMap.put("TORTAS", R.drawable.torta);
        signLanguageMap.put("TORTILLA", R.drawable.tortilla);
        signLanguageMap.put("TORTILLAS", R.drawable.tortilla);
        signLanguageMap.put("TORTURA", R.drawable.tortura);
        signLanguageMap.put("TORTURAS", R.drawable.tortura);
        signLanguageMap.put("TORTURAR", R.drawable.tortura);
        signLanguageMap.put("TOSTADOR", R.drawable.tostador);
        signLanguageMap.put("TOSTADORA", R.drawable.tostador);
        signLanguageMap.put("TOSTADORES", R.drawable.tostador);
        signLanguageMap.put("TOSTADORAS", R.drawable.tostador);
        signLanguageMap.put("TRAPEADOR", R.drawable.trapeador);
        signLanguageMap.put("TRAPEADORES", R.drawable.trapeador);
        signLanguageMap.put("TRAPO", R.drawable.trapo);
        signLanguageMap.put("TRAPOS", R.drawable.trapo);
        signLanguageMap.put("TRASTES", R.drawable.trastes);
        signLanguageMap.put("TRASTE", R.drawable.trastes);
        signLanguageMap.put("TRISTE", R.drawable.triste);
        signLanguageMap.put("TRISTES", R.drawable.triste);
        signLanguageMap.put("TU", R.drawable.tu);
        signLanguageMap.put("TUS", R.drawable.tu);
        signLanguageMap.put("TE", R.drawable.tu);
        signLanguageMap.put("TUBO", R.drawable.tubo);
        signLanguageMap.put("TUBOS", R.drawable.tubo);
        signLanguageMap.put("TUYO", R.drawable.tuyo);
        signLanguageMap.put("TUYOS", R.drawable.tuyo);
        signLanguageMap.put("TUYA", R.drawable.tuyo);
        signLanguageMap.put("TUYAS", R.drawable.tuyo);
        signLanguageMap.put("UÑA", R.drawable.unia);
        signLanguageMap.put("UÑAS", R.drawable.unia);
        signLanguageMap.put("USTED", R.drawable.usted);
        signLanguageMap.put("USTEDES", R.drawable.usted);
        signLanguageMap.put("VACA", R.drawable.vaca);
        signLanguageMap.put("VACAS", R.drawable.vaca);
        signLanguageMap.put("VACIO", R.drawable.vacio);
        signLanguageMap.put("VACIA", R.drawable.vacio);
        signLanguageMap.put("VACIAS", R.drawable.vacio);
        signLanguageMap.put("VACIOS", R.drawable.vacio);
        signLanguageMap.put("VAMPIRO", R.drawable.vampiro);
        signLanguageMap.put("VAMPIROS", R.drawable.vampiro);
        signLanguageMap.put("VASO", R.drawable.vaso);
        signLanguageMap.put("VASOS", R.drawable.vaso);
        signLanguageMap.put("VELA", R.drawable.vela);
        signLanguageMap.put("VELAS", R.drawable.vela);
        signLanguageMap.put("VENADO", R.drawable.venado);
        signLanguageMap.put("VENCIDAD", R.drawable.vencidad);
        signLanguageMap.put("VENTANA", R.drawable.ventana);
        signLanguageMap.put("VENTANAS", R.drawable.ventana);
        signLanguageMap.put("VERANO", R.drawable.verano);
        signLanguageMap.put("VERANOS", R.drawable.verano);
        signLanguageMap.put("VERDAD", R.drawable.verdad);
        signLanguageMap.put("VERDADES", R.drawable.verdad);
        signLanguageMap.put("MENTIR", R.drawable.verdad);
        signLanguageMap.put("VERDE", R.drawable.verde);
        signLanguageMap.put("VERDES", R.drawable.verde);
        signLanguageMap.put("VIBORA", R.drawable.vibora);
        signLanguageMap.put("VIBORAS", R.drawable.vibora);
        signLanguageMap.put("VIDRIO", R.drawable.vidrio);
        signLanguageMap.put("VIDRIOS", R.drawable.vidrio);
        signLanguageMap.put("VIERNES", R.drawable.viernes);
        signLanguageMap.put("VINAGRE", R.drawable.vinagre);
        signLanguageMap.put("VINAGRES", R.drawable.vinagre);
        signLanguageMap.put("VINO", R.drawable.vino);
        signLanguageMap.put("VINOS", R.drawable.vino);
        signLanguageMap.put("VITRINA", R.drawable.vitrina);
        signLanguageMap.put("VITRINAS", R.drawable.vitrina);
        signLanguageMap.put("WHISKY", R.drawable.whisky);
        signLanguageMap.put("YO", R.drawable.yo);
        signLanguageMap.put("YOGUR", R.drawable.yogur);
        signLanguageMap.put("YOGURES", R.drawable.yogur);
        signLanguageMap.put("ZORILLO", R.drawable.zorillo);
        signLanguageMap.put("ZORILLOS", R.drawable.zorillo);
        signLanguageMap.put("ZORRO", R.drawable.zorro);
        signLanguageMap.put("ZORROS", R.drawable.zorro);
        signLanguageMap.put("ZORRA", R.drawable.zorro);
        signLanguageMap.put("ZORRAS", R.drawable.zorro);

        //Frases comunes 2
        signLanguageMap.put("ABANDONAR", R.drawable.abandonar);
        signLanguageMap.put("ABRAZAR", R.drawable.abrazar);
        signLanguageMap.put("ABRAZARSE", R.drawable.abrazar);
        signLanguageMap.put("ACAPULCO", R.drawable.acapulco);
        signLanguageMap.put("ACCESO", R.drawable.acceso);
        signLanguageMap.put("ACEITUNA", R.drawable.aceituna);
        signLanguageMap.put("ACEITUNAS", R.drawable.aceituna);
        signLanguageMap.put("ACELGA", R.drawable.acelga);
        signLanguageMap.put("ACEPTAR", R.drawable.aceptar);
        signLanguageMap.put("ACEPTARSE", R.drawable.aceptar);
        signLanguageMap.put("ACOSTUMBRAR", R.drawable.acostumbrar);
        signLanguageMap.put("ACOSTUMBRARSE", R.drawable.acostumbrar);
        signLanguageMap.put("ADIOS", R.drawable.adios);
        signLanguageMap.put("AGUACATE", R.drawable.aguacate);
        signLanguageMap.put("AGUACATES", R.drawable.aguacate);
        signLanguageMap.put("AGUASCALIENTES", R.drawable.aguascalientes);
        signLanguageMap.put("AHI", R.drawable.ahi);
        signLanguageMap.put("AHORA", R.drawable.ahora);
        signLanguageMap.put("AJO", R.drawable.ajo);
        signLanguageMap.put("ALABAR", R.drawable.alabar);
        signLanguageMap.put("ALABANZA", R.drawable.alabar);
        signLanguageMap.put("ALCACHOFA", R.drawable.alcachofa);
        signLanguageMap.put("ALCACHOFAS", R.drawable.alcachofa);
        signLanguageMap.put("ALLA", R.drawable.alla);
        signLanguageMap.put("ALMENDRA", R.drawable.almendra);
        signLanguageMap.put("ALMENDRAS", R.drawable.almendra);
        signLanguageMap.put("AMOLADO", R.drawable.amolado);
        signLanguageMap.put("ANTIFAZ", R.drawable.antifaz);
        signLanguageMap.put("ANTIFACES", R.drawable.antifaz);
        signLanguageMap.put("APESTOSO", R.drawable.apestoso);
        signLanguageMap.put("APESTOSA", R.drawable.apestoso);
        signLanguageMap.put("APESTOSOS", R.drawable.apestoso);
        signLanguageMap.put("APRENDER", R.drawable.aprender);
        signLanguageMap.put("APRENDE", R.drawable.aprender);
        signLanguageMap.put("APRISA", R.drawable.aprisa);
        signLanguageMap.put("APROBADO", R.drawable.aprobado);
        signLanguageMap.put("APROBADOS", R.drawable.aprobado);
        signLanguageMap.put("APROBADA", R.drawable.aprobado);
        signLanguageMap.put("APROBADAS", R.drawable.aprobado);
        signLanguageMap.put("APROBAR", R.drawable.aprobado);
        signLanguageMap.put("AQUI", R.drawable.aqui);
        signLanguageMap.put("ARETE", R.drawable.arete);
        signLanguageMap.put("ARETES", R.drawable.arete);
        signLanguageMap.put("ASOMAR", R.drawable.asomar);
        signLanguageMap.put("ASOMARSE", R.drawable.asomar);
        signLanguageMap.put("ASOMARTE", R.drawable.asomar);
        signLanguageMap.put("ASOMATE", R.drawable.asomar);
        signLanguageMap.put("AYUDAR", R.drawable.ayudar);
        signLanguageMap.put("AYUDA", R.drawable.ayudar);
        signLanguageMap.put("AYUDARSE", R.drawable.ayudar);
        signLanguageMap.put("BAILAR", R.drawable.bailar);
        signLanguageMap.put("BAILA", R.drawable.bailar);
        signLanguageMap.put("BAILAS", R.drawable.bailar);
        signLanguageMap.put("BALON", R.drawable.balon);
        signLanguageMap.put("BALONES", R.drawable.balon);
        signLanguageMap.put("BARRERAS", R.drawable.barreras);
        signLanguageMap.put("BARRERA", R.drawable.barreras);
        signLanguageMap.put("BERENJENA", R.drawable.berenjena);
        signLanguageMap.put("BERENJENAS", R.drawable.berenjena);
        signLanguageMap.put("BESARSE", R.drawable.besarse);
        signLanguageMap.put("BESAR", R.drawable.besarse);
        signLanguageMap.put("BETABEL", R.drawable.betabel);
        signLanguageMap.put("BILINGUE", R.drawable.bilingue);
        signLanguageMap.put("BOMBERO", R.drawable.bombero);
        signLanguageMap.put("BOMBEROS", R.drawable.bombero);
        signLanguageMap.put("BONDAD", R.drawable.bondad);
        signLanguageMap.put("BONDADES", R.drawable.bondad);
        signLanguageMap.put("BOSTEZAR", R.drawable.bostezar);
        signLanguageMap.put("BOSTEZA", R.drawable.bostezar);
        signLanguageMap.put("BREVE", R.drawable.breve);
        signLanguageMap.put("BREVES", R.drawable.breve);
        signLanguageMap.put("BREVEDAD", R.drawable.breve);
        signLanguageMap.put("CACAHUATE", R.drawable.cacahuate);
        signLanguageMap.put("CACAHUATES", R.drawable.cacahuate);
        signLanguageMap.put("CALABACITA", R.drawable.calabacita);
        signLanguageMap.put("CALABACITAS", R.drawable.calabacita);
        signLanguageMap.put("CALABAZA", R.drawable.calabaza);
        signLanguageMap.put("CALABAZAS", R.drawable.calabaza);
        signLanguageMap.put("CALAVERA", R.drawable.calavera);
        signLanguageMap.put("CALAVERAS", R.drawable.calavera);
        signLanguageMap.put("CALENDARIO", R.drawable.calendario);
        signLanguageMap.put("CALENDARIOS", R.drawable.calendario);
        signLanguageMap.put("CAMOTE", R.drawable.camote);
        signLanguageMap.put("CAMOTES", R.drawable.camote);
        signLanguageMap.put("CAMPECHE", R.drawable.campeche);
        signLanguageMap.put("CASTIGAR", R.drawable.castigar);
        signLanguageMap.put("CASTIGA", R.drawable.castigar);
        signLanguageMap.put("CASTIGO", R.drawable.castigar);
        signLanguageMap.put("CATORCE", R.drawable.catorce);
        signLanguageMap.put("CAÑA", R.drawable.cana);
        signLanguageMap.put("CAÑAS", R.drawable.cana);
        signLanguageMap.put("CEBOLLA", R.drawable.cebolla);
        signLanguageMap.put("CEBOLLAS", R.drawable.cebolla);
        signLanguageMap.put("CENTRO", R.drawable.centro);
        signLanguageMap.put("CEREZA", R.drawable.cereza);
        signLanguageMap.put("CEREZAS", R.drawable.cereza);
        signLanguageMap.put("CHAMPIÑON", R.drawable.champinon);
        signLanguageMap.put("CHAMPIÑONES", R.drawable.champinon);
        signLanguageMap.put("CHAYOTE", R.drawable.chayote);
        signLanguageMap.put("CHAYOTES", R.drawable.chayote);
        signLanguageMap.put("CHETUMAL", R.drawable.chetumal);
        signLanguageMap.put("CHIAPAS", R.drawable.chiapas);
        signLanguageMap.put("CHICHARO", R.drawable.chicharo);
        signLanguageMap.put("CHICHAROS", R.drawable.chicharo);
        signLanguageMap.put("CHIHUAHUA", R.drawable.chihuahua);
        signLanguageMap.put("CHILE", R.drawable.chile);
        signLanguageMap.put("CHILES", R.drawable.chile);
        signLanguageMap.put("CIEN", R.drawable.cien);
        signLanguageMap.put("CILANTRO", R.drawable.cilantro);
        signLanguageMap.put("CINCO", R.drawable.cinco);
        signLanguageMap.put("CINCUENTA", R.drawable.cincuenta);
        signLanguageMap.put("CIRCULO", R.drawable.circulo);
        signLanguageMap.put("CIRCULOS", R.drawable.circulo);
        signLanguageMap.put("CIRUELA", R.drawable.ciruela);
        signLanguageMap.put("CIRUELAS", R.drawable.ciruela);
        signLanguageMap.put("CIUDAD", R.drawable.ciudad);
        signLanguageMap.put("CIUDADES", R.drawable.ciudad);
        signLanguageMap.put("COAHUILA", R.drawable.coahuila);
        signLanguageMap.put("COCO", R.drawable.coco);
        signLanguageMap.put("COCOS", R.drawable.coco);
        signLanguageMap.put("COGER", R.drawable.coger);
        signLanguageMap.put("COL", R.drawable.col);
        signLanguageMap.put("COLES", R.drawable.col);
        signLanguageMap.put("COLIFLOR", R.drawable.coliflor);
        signLanguageMap.put("COLIMA", R.drawable.colima);
        signLanguageMap.put("COMA", R.drawable.coma);
        signLanguageMap.put("CONDE", R.drawable.conde);
        signLanguageMap.put("CONJUNTO", R.drawable.conjunto);
        signLanguageMap.put("CONJUNTOS", R.drawable.conjunto);
        signLanguageMap.put("CONOCER", R.drawable.conocer);
        signLanguageMap.put("CONOCERLO", R.drawable.conocer);
        signLanguageMap.put("CONOCERLA", R.drawable.conocer);
        signLanguageMap.put("CONOCERSE", R.drawable.conocer);
        signLanguageMap.put("CONOCE", R.drawable.conocer);
        signLanguageMap.put("CONTAR", R.drawable.contar);
        signLanguageMap.put("CONVERTIR", R.drawable.convertir);
        signLanguageMap.put("CONVERTIRSE", R.drawable.convertir);
        signLanguageMap.put("COQUETO", R.drawable.coqueto);
        signLanguageMap.put("CORAJE", R.drawable.coraje);
        signLanguageMap.put("CORAJES", R.drawable.coraje);
        signLanguageMap.put("CORTAR", R.drawable.cortar);
        signLanguageMap.put("CORTA", R.drawable.cortar);
        signLanguageMap.put("CRITICAR", R.drawable.criticar);
        signLanguageMap.put("CRITICA", R.drawable.criticar);
        signLanguageMap.put("CUADRADO", R.drawable.cuadrado);
        signLanguageMap.put("CUADRADA", R.drawable.cuadrado);
        signLanguageMap.put("CUADRADOS", R.drawable.cuadrado);
        signLanguageMap.put("CUAL", R.drawable.cual);
        signLanguageMap.put("CUALES", R.drawable.cual);
        signLanguageMap.put("CUARENTA", R.drawable.cuarenta);
        signLanguageMap.put("CUARTO", R.drawable.cuarto);
        signLanguageMap.put("CUATRO", R.drawable.cuatro);
        signLanguageMap.put("CUATROCIENTOS", R.drawable.cuatrocientos);
        signLanguageMap.put("CUERNAVACA", R.drawable.cuernavaca);
        signLanguageMap.put("CUIDADO", R.drawable.cuidado);
        signLanguageMap.put("CULIACAN", R.drawable.culiacan);
        signLanguageMap.put("CURIOSO", R.drawable.curioso);
        signLanguageMap.put("CURIOSA", R.drawable.curioso);
        signLanguageMap.put("CURIOSOS", R.drawable.curioso);
        signLanguageMap.put("CURIOSAS", R.drawable.curioso);
        signLanguageMap.put("DEFENDER", R.drawable.defender);
        signLanguageMap.put("DEFENDERSE", R.drawable.defender);
        signLanguageMap.put("DEFIENDE", R.drawable.defender);
        signLanguageMap.put("DEPENDER", R.drawable.depender);
        signLanguageMap.put("DESCANSAR", R.drawable.descansar);
        signLanguageMap.put("DIABLO", R.drawable.diablo);
        signLanguageMap.put("DIABLOS", R.drawable.diablo);
        signLanguageMap.put("DIAGONAL", R.drawable.diagonal);
        signLanguageMap.put("DIECINUEVE", R.drawable.diecinueve);
        signLanguageMap.put("DIECIOCHO", R.drawable.dieciocho);
        signLanguageMap.put("DIECISEIS", R.drawable.dieciseis);
        signLanguageMap.put("DIECISIETE", R.drawable.diecisiete);
        signLanguageMap.put("DIEZ", R.drawable.diez);
        signLanguageMap.put("DIFERENTE", R.drawable.diferente);
        signLanguageMap.put("DIFERENTES", R.drawable.diferente);
        signLanguageMap.put("DINERO", R.drawable.diner);
        signLanguageMap.put("DIOS", R.drawable.dios);
        signLanguageMap.put("DISCRIMINAR", R.drawable.discriminar);
        signLanguageMap.put("DIVISION", R.drawable.division);
        signLanguageMap.put("DIVISIONES", R.drawable.division);
        signLanguageMap.put("DOBLE", R.drawable.doble);
        signLanguageMap.put("DOCE", R.drawable.doce);
        signLanguageMap.put("DOLAR", R.drawable.dolar);
        signLanguageMap.put("DOLARES", R.drawable.dolar);
        signLanguageMap.put("DOS", R.drawable.dos);
        signLanguageMap.put("DOSCIENTOS", R.drawable.doscientos);
        signLanguageMap.put("DOS_MIL", R.drawable.dos_mil);
        signLanguageMap.put("DUDA", R.drawable.duda);
        signLanguageMap.put("DUDAS", R.drawable.duda);
        signLanguageMap.put("DUEÑO", R.drawable.dueno);
        signLanguageMap.put("DUEÑOS", R.drawable.dueno);
        signLanguageMap.put("DURANGO", R.drawable.durango);
        signLanguageMap.put("DURAZNO", R.drawable.durazno);
        signLanguageMap.put("DURAZNOS", R.drawable.durazno);
        signLanguageMap.put("EJOTE", R.drawable.ejote);
        signLanguageMap.put("EJOTES", R.drawable.ejote);
        signLanguageMap.put("ELOTE", R.drawable.elote);
        signLanguageMap.put("ELOTES", R.drawable.elote);
        signLanguageMap.put("ENMEDIO", R.drawable.en_medio);
        signLanguageMap.put("ENFRENTE", R.drawable.enfrente);
        signLanguageMap.put("ENOJADO", R.drawable.enojado);
        signLanguageMap.put("ENOJADA", R.drawable.enojado);
        signLanguageMap.put("ENROLLAR", R.drawable.enrollar);
        signLanguageMap.put("ENTREGAR", R.drawable.entregar);
        signLanguageMap.put("ENTREGA", R.drawable.entregar);
        signLanguageMap.put("ENVIDIA", R.drawable.envidia);
        signLanguageMap.put("ESCOGER", R.drawable.escoger);
        signLanguageMap.put("ESCOGE", R.drawable.escoger);
        signLanguageMap.put("ESFERA", R.drawable.esfera);
        signLanguageMap.put("ESFERAS", R.drawable.esfera);
        signLanguageMap.put("ESPINACA", R.drawable.espinaca);
        signLanguageMap.put("ESPINACAS", R.drawable.espinaca);
        signLanguageMap.put("ESPIRITU", R.drawable.espiritu);
        signLanguageMap.put("ESPIRITUS", R.drawable.espiritu);
        signLanguageMap.put("ESQUINA", R.drawable.esquina);
        signLanguageMap.put("ESTACIONAR", R.drawable.estacionar);
        signLanguageMap.put("ESTACIONARSE", R.drawable.estacionar);
        signLanguageMap.put("ESTADO", R.drawable.estado);
        signLanguageMap.put("ESTADOS", R.drawable.estado);
        signLanguageMap.put("ESTAR", R.drawable.estar);
        signLanguageMap.put("ETERNO", R.drawable.eterno);
        signLanguageMap.put("FABRICA", R.drawable.fabrica);
        signLanguageMap.put("FABRICAS", R.drawable.fabrica);
        signLanguageMap.put("FAVOR", R.drawable.favor);
        signLanguageMap.put("FLOR", R.drawable.flor);
        signLanguageMap.put("FLORES", R.drawable.flor);
        signLanguageMap.put("FORMA", R.drawable.forma);
        signLanguageMap.put("FORMAS", R.drawable.forma);
        signLanguageMap.put("FRENTE", R.drawable.frente);
        signLanguageMap.put("FRESA", R.drawable.fresa);
        signLanguageMap.put("FRESAS", R.drawable.fresa);
        signLanguageMap.put("FRIJOL", R.drawable.frijol);
        signLanguageMap.put("FRIJOLES", R.drawable.frijol);
        signLanguageMap.put("FRIO", R.drawable.frio);
        signLanguageMap.put("FRONTERA", R.drawable.frontera);
        signLanguageMap.put("FRONTERAS", R.drawable.frontera);
        signLanguageMap.put("FRUTA", R.drawable.fruta);
        signLanguageMap.put("FRUTAS", R.drawable.fruta);
        signLanguageMap.put("GERRERO", R.drawable.gerrero);
        signLanguageMap.put("GOLPEAR", R.drawable.golpear);
        signLanguageMap.put("GRANADA", R.drawable.granada);
        signLanguageMap.put("GRANADAS", R.drawable.granada);
        signLanguageMap.put("GRUPO", R.drawable.grupo);
        signLanguageMap.put("GUADALAJARA", R.drawable.guadalajara);
        signLanguageMap.put("GUANAJUATO", R.drawable.guanajuato);
        signLanguageMap.put("GUARDAR", R.drawable.guardar);
        signLanguageMap.put("GUARDALO", R.drawable.guardar);
        signLanguageMap.put("GUARDARLO", R.drawable.guardar);
        signLanguageMap.put("GUAYABA", R.drawable.guayaba);
        signLanguageMap.put("GUAYABAS", R.drawable.guayaba);
        signLanguageMap.put("GUERRERO", R.drawable.guerrero);
        signLanguageMap.put("HABA", R.drawable.haba);
        signLanguageMap.put("HABAS", R.drawable.haba);
        signLanguageMap.put("HACER", R.drawable.hacer);
        signLanguageMap.put("HACE", R.drawable.hacer);
        signLanguageMap.put("HACES", R.drawable.hacer);
        signLanguageMap.put("HACERLO", R.drawable.hacer);
        signLanguageMap.put("HERMOSILLO", R.drawable.hermosillo);
        signLanguageMap.put("HIDALGO", R.drawable.hidalgo);
        signLanguageMap.put("HIGO", R.drawable.higo);
        signLanguageMap.put("HIGOS", R.drawable.higo);
        signLanguageMap.put("HIPOCRITA", R.drawable.hipocrita);
        signLanguageMap.put("HONGO", R.drawable.hongo);
        signLanguageMap.put("HONGOS", R.drawable.hongo);
        signLanguageMap.put("HORIZONTAL", R.drawable.horizontal);
        signLanguageMap.put("IGLESIA", R.drawable.iglesia);
        signLanguageMap.put("IGLESIAS", R.drawable.iglesia);
        signLanguageMap.put("INFECCION", R.drawable.infeccion);
        signLanguageMap.put("INFECCIONES", R.drawable.infeccion);
        signLanguageMap.put("INVITAR", R.drawable.invitar);
        signLanguageMap.put("INVITA", R.drawable.invitar);
        signLanguageMap.put("INVITO", R.drawable.invitar);
        signLanguageMap.put("JALISCO", R.drawable.jalisco);
        signLanguageMap.put("JAMAICA", R.drawable.jamaica);
        signLanguageMap.put("JICAMA", R.drawable.jicama);
        signLanguageMap.put("JICAMAS", R.drawable.jicama);
        signLanguageMap.put("JITOMATE", R.drawable.jitomate);
        signLanguageMap.put("JITOMATES", R.drawable.jitomate);
        signLanguageMap.put("JUDIO", R.drawable.judio);
        signLanguageMap.put("CANICAS", R.drawable.jugar_canicas);
        signLanguageMap.put("JUGAR", R.drawable.jugar);
        signLanguageMap.put("JUEGO", R.drawable.jugar);
        signLanguageMap.put("LADO", R.drawable.lado);
        signLanguageMap.put("LADOS", R.drawable.lado);
        signLanguageMap.put("LECHUGA", R.drawable.lechuga);
        signLanguageMap.put("LECHUGAS", R.drawable.lechuga);
        signLanguageMap.put("LIBERTAD", R.drawable.libertad);
        signLanguageMap.put("LIBRE", R.drawable.libre);
        signLanguageMap.put("LIBRES", R.drawable.libre);
        signLanguageMap.put("LIBRO", R.drawable.libro);
        signLanguageMap.put("LIBROS", R.drawable.libro);
        signLanguageMap.put("LIMA", R.drawable.lima);
        signLanguageMap.put("LIMAS", R.drawable.lima);
        signLanguageMap.put("LIMON", R.drawable.limon);
        signLanguageMap.put("LIMONES", R.drawable.limon);
        signLanguageMap.put("LINEA", R.drawable.linea);
        signLanguageMap.put("LINEAS", R.drawable.linea);
        signLanguageMap.put("LISO", R.drawable.liso);
        signLanguageMap.put("LUNA", R.drawable.luna);
        signLanguageMap.put("LUNAS", R.drawable.luna);
        signLanguageMap.put("LUNITA", R.drawable.luna);
        signLanguageMap.put("MAIZ", R.drawable.maiz);
        signLanguageMap.put("MAMEY", R.drawable.mamey);
        signLanguageMap.put("MANEJAR", R.drawable.manejar);
        signLanguageMap.put("MANEJA", R.drawable.manejar);
        signLanguageMap.put("MANGO", R.drawable.mango);
        signLanguageMap.put("MANGOS", R.drawable.mango);
        signLanguageMap.put("MANZANA", R.drawable.manzana);
        signLanguageMap.put("MANZANAS", R.drawable.manzana);
        signLanguageMap.put("MATEMATICAS", R.drawable.matematicas);
        signLanguageMap.put("MELON", R.drawable.melon);
        signLanguageMap.put("MELONES", R.drawable.melon);
        signLanguageMap.put("MERIDA", R.drawable.merida);
        signLanguageMap.put("MEXICALI", R.drawable.mexicali);
        signLanguageMap.put("MEXICO", R.drawable.mexico);
        signLanguageMap.put("MICHOACAN", R.drawable.michoacan);
        signLanguageMap.put("MICROFONO", R.drawable.microfono);
        signLanguageMap.put("MICROFONOS", R.drawable.microfono);
        signLanguageMap.put("MIL", R.drawable.mil);
        signLanguageMap.put("MILES", R.drawable.mil);
        signLanguageMap.put("MILLON", R.drawable.millon);
        signLanguageMap.put("MILLONES", R.drawable.millon);
        signLanguageMap.put("MILLONARIO", R.drawable.millonario);
        signLanguageMap.put("MITAD", R.drawable.mitad);
        signLanguageMap.put("MITADES", R.drawable.mitad);
        signLanguageMap.put("MODA", R.drawable.moda);
        signLanguageMap.put("MODAS", R.drawable.moda);
        signLanguageMap.put("MOMENTITO", R.drawable.momentito);
        signLanguageMap.put("MONTERREY", R.drawable.monterrey);
        signLanguageMap.put("MORELIA", R.drawable.morelia);
        signLanguageMap.put("MORELOS", R.drawable.morelos);
        signLanguageMap.put("MOTOCICLETA", R.drawable.motocicleta);
        signLanguageMap.put("MOTOCICLETAS", R.drawable.motocicleta);
        signLanguageMap.put("MOTOR", R.drawable.motor);
        signLanguageMap.put("MOTORES", R.drawable.motor);
        signLanguageMap.put("MULTIPLICACION", R.drawable.multiplicacion);
        signLanguageMap.put("MULTIPLICACIONES", R.drawable.multiplicacion);
        signLanguageMap.put("NADA", R.drawable.nada);
        signLanguageMap.put("NADAR", R.drawable.nadar);
        signLanguageMap.put("NARANJA", R.drawable.naranja);
        signLanguageMap.put("NARANJAS", R.drawable.naranja);
        signLanguageMap.put("NAYARIT", R.drawable.nayarit);
        signLanguageMap.put("NECESITAR", R.drawable.necesitar);
        signLanguageMap.put("NECESITA", R.drawable.necesitar);
        signLanguageMap.put("NECESITO", R.drawable.necesitar);
        signLanguageMap.put("NEGOCIO", R.drawable.negocio);
        signLanguageMap.put("NEGOCIOS", R.drawable.negocio);
        signLanguageMap.put("NIETO", R.drawable.nieto);
        signLanguageMap.put("NIETOS", R.drawable.nieto);
        signLanguageMap.put("NIETA", R.drawable.nieto);
        signLanguageMap.put("NIVEL", R.drawable.nivel);
        signLanguageMap.put("NIVELES", R.drawable.nivel);
        signLanguageMap.put("NIÑA", R.drawable.nina);
        signLanguageMap.put("NIÑAS", R.drawable.nina);
        signLanguageMap.put("NIÑO", R.drawable.nino);
        signLanguageMap.put("NIÑOS", R.drawable.nino);
        signLanguageMap.put("INDISPONIBLE", R.drawable.no_hay);
        signLanguageMap.put("INSERVIBLE", R.drawable.no_sirve);
        signLanguageMap.put("NOMBRE", R.drawable.nombre);
        signLanguageMap.put("NOMBRES", R.drawable.nombre);
        signLanguageMap.put("NOPAL", R.drawable.nopal);
        signLanguageMap.put("NOPALES", R.drawable.nopal);
        signLanguageMap.put("NOVECIENTOS", R.drawable.novecientos);
        signLanguageMap.put("NOVENTA", R.drawable.noventa);
        signLanguageMap.put("NOVENTAS", R.drawable.noventa);
        signLanguageMap.put("NOVIA", R.drawable.novia);
        signLanguageMap.put("NOVIAS", R.drawable.novia);
        signLanguageMap.put("NOVIO", R.drawable.novio);
        signLanguageMap.put("NOVIOS", R.drawable.novio);
        signLanguageMap.put("NUERA", R.drawable.nuera);
        signLanguageMap.put("NUEVE", R.drawable.nueve);
        signLanguageMap.put("NUEZ", R.drawable.nuez);
        signLanguageMap.put("NUECES", R.drawable.nuez);
        signLanguageMap.put("NUMERO", R.drawable.numero);
        signLanguageMap.put("NUMEROS", R.drawable.numero);
        signLanguageMap.put("OAXACA", R.drawable.oaxaca);
        signLanguageMap.put("OCHENTA", R.drawable.ochenta);
        signLanguageMap.put("OCHO", R.drawable.ocho);
        signLanguageMap.put("OCHOCIENTOS", R.drawable.ochocientos);
        signLanguageMap.put("OIR", R.drawable.oir);
        signLanguageMap.put("ESCUCHAR", R.drawable.oir);
        signLanguageMap.put("OJALA", R.drawable.ojala);
        signLanguageMap.put("ONCE", R.drawable.once);
        signLanguageMap.put("OTRO", R.drawable.otro);
        signLanguageMap.put("OTRA", R.drawable.otro);
        signLanguageMap.put("PACHUCA", R.drawable.pachuca);
        signLanguageMap.put("PADRASTRO", R.drawable.padrastro);
        signLanguageMap.put("PADRE", R.drawable.padre);
        signLanguageMap.put("PADRES", R.drawable.padres);
        signLanguageMap.put("PADRINO", R.drawable.padrino);
        signLanguageMap.put("PALA", R.drawable.pala);
        signLanguageMap.put("PALAS", R.drawable.pala);
        signLanguageMap.put("PALABRA", R.drawable.palabra);
        signLanguageMap.put("PALABRAS", R.drawable.palabra);
        signLanguageMap.put("PANTEON", R.drawable.panteon);
        signLanguageMap.put("PAPA", R.drawable.papa);
        signLanguageMap.put("PAPAYA", R.drawable.papaya);
        signLanguageMap.put("PAPAYAS", R.drawable.papaya);
        signLanguageMap.put("PAPÁ", R.drawable.papa_a);
        signLanguageMap.put("PAPÁS", R.drawable.papa_a);
        signLanguageMap.put("PAREJA", R.drawable.pareja);
        signLanguageMap.put("PARTIR", R.drawable.partir);
        signLanguageMap.put("PAZ", R.drawable.pas);
        signLanguageMap.put("PASA", R.drawable.pasa);
        signLanguageMap.put("PASOS", R.drawable.pasos);
        signLanguageMap.put("PASO", R.drawable.pasos);
        signLanguageMap.put("PELOTA", R.drawable.pelota);
        signLanguageMap.put("PELOTAS", R.drawable.pelota);
        signLanguageMap.put("PENA", R.drawable.pena);
        signLanguageMap.put("VERGÜENZA", R.drawable.pena);
        signLanguageMap.put("PENSAR", R.drawable.pensar);
        signLanguageMap.put("PIENSA", R.drawable.pensar);
        signLanguageMap.put("PEPINO", R.drawable.pepino);
        signLanguageMap.put("PEPINOS", R.drawable.pepino);
        signLanguageMap.put("PERA", R.drawable.pera);
        signLanguageMap.put("PERAS", R.drawable.pera);
        signLanguageMap.put("PEREJIL", R.drawable.perejil);
        signLanguageMap.put("PERFUME", R.drawable.perfume);
        signLanguageMap.put("PERFUMES", R.drawable.perfume);
        signLanguageMap.put("PERMANECER", R.drawable.permanecer);
        signLanguageMap.put("QUEDARSE", R.drawable.permanecer);
        signLanguageMap.put("PERO", R.drawable.pero);
        signLanguageMap.put("PERSONA", R.drawable.persona);
        signLanguageMap.put("PERSONAS", R.drawable.persona);
        signLanguageMap.put("PESOS", R.drawable.pesos);
        signLanguageMap.put("PESO", R.drawable.pesos);
        signLanguageMap.put("PIANO", R.drawable.piano);
        signLanguageMap.put("PIANOS", R.drawable.piano);
        signLanguageMap.put("PISTOLA", R.drawable.pistola);
        signLanguageMap.put("PISTOLAS", R.drawable.pistola);
        signLanguageMap.put("PIÑA", R.drawable.pina);
        signLanguageMap.put("PIÑAS", R.drawable.pina);
        signLanguageMap.put("PIÑON", R.drawable.pinon);
        signLanguageMap.put("PIÑONES", R.drawable.pinon);
        signLanguageMap.put("PLATANO", R.drawable.platano);
        signLanguageMap.put("PLATANOS", R.drawable.platano);
        signLanguageMap.put("PLURAL", R.drawable.plural);
        signLanguageMap.put("POPOTE", R.drawable.popote);
        signLanguageMap.put("POPOTES", R.drawable.popote);
        signLanguageMap.put("PRESIDENTE", R.drawable.presidente);
        signLanguageMap.put("PRESIDENTES", R.drawable.presidente);
        signLanguageMap.put("PRIMA", R.drawable.prima);
        signLanguageMap.put("PRIMAS", R.drawable.prima);
        signLanguageMap.put("PRIMO", R.drawable.primo);
        signLanguageMap.put("PRIMOS", R.drawable.primo);
        signLanguageMap.put("PRINCIPE", R.drawable.principe);
        signLanguageMap.put("PRINCIPES", R.drawable.principe);
        signLanguageMap.put("PRONTO", R.drawable.pronto);
        signLanguageMap.put("PROTEGER", R.drawable.proteger);
        signLanguageMap.put("PROTEGERSE", R.drawable.proteger);
        signLanguageMap.put("PROTEGE", R.drawable.proteger);
        signLanguageMap.put("PROTEJO", R.drawable.proteger);
        signLanguageMap.put("PUEBLA", R.drawable.puebla);
        signLanguageMap.put("PUEBLO", R.drawable.pueblo);
        signLanguageMap.put("PUEBLOS", R.drawable.pueblo);
        signLanguageMap.put("PUNTO", R.drawable.punto);
        signLanguageMap.put("PUNTOS", R.drawable.punto);
        signLanguageMap.put("QUERER", R.drawable.querer);
        signLanguageMap.put("QUIERO", R.drawable.querer);
        signLanguageMap.put("QUERETARO", R.drawable.queretaro);
        signLanguageMap.put("QUINCE", R.drawable.quince);
        signLanguageMap.put("QUINIENTOS", R.drawable.quinientos);
        signLanguageMap.put("QUINTO", R.drawable.quinto);
        signLanguageMap.put("RADIO", R.drawable.radio);
        signLanguageMap.put("RADIOS", R.drawable.radio);
        signLanguageMap.put("RAIZ", R.drawable.raiz_cuadrada);
        signLanguageMap.put("REBANAR", R.drawable.rebanar);
        signLanguageMap.put("RECTANGULO", R.drawable.rectangulo);
        signLanguageMap.put("RECTANGULOS", R.drawable.rectangulo);
        signLanguageMap.put("REGRESAR", R.drawable.regresar);
        signLanguageMap.put("REGRESA", R.drawable.regresar);
        signLanguageMap.put("RESTA", R.drawable.resta);
        signLanguageMap.put("RESTAS", R.drawable.resta);
        signLanguageMap.put("RESTAR", R.drawable.resta);
        signLanguageMap.put("RESUMEN", R.drawable.resumen);
        signLanguageMap.put("RESUMENES", R.drawable.resumen);
        signLanguageMap.put("REY", R.drawable.rey);
        signLanguageMap.put("REYES", R.drawable.rey);
        signLanguageMap.put("SALTILLO", R.drawable.saltillo);
        signLanguageMap.put("SALUDAR", R.drawable.saludar);
        signLanguageMap.put("SALUDA", R.drawable.saludar);
        signLanguageMap.put("SALUDO", R.drawable.saludar);
        signLanguageMap.put("SALUDOS", R.drawable.saludar);
        signLanguageMap.put("SALUDARSE", R.drawable.saludar);
        signLanguageMap.put("SANDIA", R.drawable.sandia);
        signLanguageMap.put("SANDIAS", R.drawable.sandia);
        signLanguageMap.put("SANGRE", R.drawable.sangre);
        signLanguageMap.put("SEGUIR", R.drawable.seguir);
        signLanguageMap.put("SIGUE", R.drawable.seguir);
        signLanguageMap.put("CONTINUA", R.drawable.seguir);
        signLanguageMap.put("CONTINUAR", R.drawable.seguir);
        signLanguageMap.put("SEGUNDO", R.drawable.segundo);
        signLanguageMap.put("SEGUNDOS", R.drawable.segundo);
        signLanguageMap.put("SEIS", R.drawable.seis);
        signLanguageMap.put("SEISCIENTOS", R.drawable.seiscientos);
        signLanguageMap.put("SENTAR", R.drawable.sentar);
        signLanguageMap.put("SENTARSE", R.drawable.sentar);
        signLanguageMap.put("SEPARADA", R.drawable.separada);
        signLanguageMap.put("SEPARADAS", R.drawable.separada);
        signLanguageMap.put("SEPARADO", R.drawable.separado);
        signLanguageMap.put("SEPARADOS", R.drawable.separado);
        signLanguageMap.put("SEPARARSE", R.drawable.separado);
        signLanguageMap.put("SER", R.drawable.ser);
        signLanguageMap.put("SESENTA", R.drawable.sesenta);
        signLanguageMap.put("SETECIENTOS", R.drawable.setecientos);
        signLanguageMap.put("SETENTA", R.drawable.setenta);
        signLanguageMap.put("SEXO", R.drawable.sexo);
        signLanguageMap.put("SEXTO", R.drawable.sexto);
        signLanguageMap.put("SEÑOR", R.drawable.senor);
        signLanguageMap.put("SEÑORES", R.drawable.senor);
        signLanguageMap.put("SEÑORA", R.drawable.senora);
        signLanguageMap.put("SEÑORAS", R.drawable.senora);
        signLanguageMap.put("SEÑORITA", R.drawable.senorita);
        signLanguageMap.put("SEÑORITAS", R.drawable.senorita);
        signLanguageMap.put("SIETE", R.drawable.siete);
        signLanguageMap.put("SINALOA", R.drawable.sinaloa);
        signLanguageMap.put("SINTESIS", R.drawable.sintesis);
        signLanguageMap.put("SOBRINA", R.drawable.sobrina);
        signLanguageMap.put("SOBRINAS", R.drawable.sobrina);
        signLanguageMap.put("SOBRINO", R.drawable.sobrino);
        signLanguageMap.put("SOBRINOS", R.drawable.sobrino);
        signLanguageMap.put("SOCIO", R.drawable.socio);
        signLanguageMap.put("SOCIOS", R.drawable.socio);
        signLanguageMap.put("SOCIA", R.drawable.socio);
        signLanguageMap.put("SOCIAS", R.drawable.socio);
        signLanguageMap.put("SOLTAR", R.drawable.soltar);
        signLanguageMap.put("SOLTERA", R.drawable.soltera);
        signLanguageMap.put("SOLTERAS", R.drawable.soltera);
        signLanguageMap.put("SOLTERO", R.drawable.soltero);
        signLanguageMap.put("SOLTEROS", R.drawable.soltero);
        signLanguageMap.put("SONORA", R.drawable.sonora);
        signLanguageMap.put("SOPLAR", R.drawable.soplar);
        signLanguageMap.put("SOPLA", R.drawable.soplar);
        signLanguageMap.put("SOPLARLE", R.drawable.soplar);
        signLanguageMap.put("SOPLALE", R.drawable.soplar);
        signLanguageMap.put("SOSTENER", R.drawable.sostener);
        signLanguageMap.put("SOSTENERSE", R.drawable.sostener);
        signLanguageMap.put("SOÑAR", R.drawable.soniar);
        signLanguageMap.put("SUEÑO", R.drawable.soniar);
        signLanguageMap.put("SUDAR", R.drawable.sudar);
        signLanguageMap.put("SUDOR", R.drawable.sudar);
        signLanguageMap.put("SUEGRA", R.drawable.suegra);
        signLanguageMap.put("SUEGRO", R.drawable.suegro);
        signLanguageMap.put("SUEGROS", R.drawable.suegro);
        signLanguageMap.put("SUMA", R.drawable.suma);
        signLanguageMap.put("SUMAR", R.drawable.suma);
        signLanguageMap.put("SUMARSE", R.drawable.suma);
        signLanguageMap.put("SUMATE", R.drawable.suma);
        signLanguageMap.put("SUMAS", R.drawable.suma);
        signLanguageMap.put("SUPERAR", R.drawable.superar);
        signLanguageMap.put("SUPERARSE", R.drawable.superar);
        signLanguageMap.put("TABASCO", R.drawable.tabasco);
        signLanguageMap.put("TAMARINDO", R.drawable.tamarindo);
        signLanguageMap.put("TAMARINDOS", R.drawable.tamarindo);
        signLanguageMap.put("TAMPICO", R.drawable.tampico);
        signLanguageMap.put("TAMUALIPAS", R.drawable.tamualipas);
        signLanguageMap.put("TATARABUELA", R.drawable.tatarabuela);
        signLanguageMap.put("TATARABUELO", R.drawable.tatarabuelo);
        signLanguageMap.put("TATARABUELOS", R.drawable.tatarabuelo);
        signLanguageMap.put("TAXI", R.drawable.taxi);
        signLanguageMap.put("TAXIS", R.drawable.taxi);
        signLanguageMap.put("TEATRO", R.drawable.teatro);
        signLanguageMap.put("TEATROS", R.drawable.teatro);
        signLanguageMap.put("TEJOCOTE", R.drawable.tejocote);
        signLanguageMap.put("TEJOCOTES", R.drawable.tejocote);
        signLanguageMap.put("TEMPERATURA", R.drawable.temperatura);
        signLanguageMap.put("TENER", R.drawable.tener);
        signLanguageMap.put("TENGO", R.drawable.tener);
        signLanguageMap.put("TEPIC", R.drawable.tepic);
        signLanguageMap.put("TERCERO", R.drawable.tercero);
        signLanguageMap.put("TIA", R.drawable.tia);
        signLanguageMap.put("TIAS", R.drawable.tia);
        signLanguageMap.put("TIENDA", R.drawable.tienda);
        signLanguageMap.put("TIENDAS", R.drawable.tienda);
        signLanguageMap.put("TIJUANA", R.drawable.tijuana);
        signLanguageMap.put("TIO", R.drawable.tio);
        signLanguageMap.put("TIOS", R.drawable.tio);
        signLanguageMap.put("TLAXCALA", R.drawable.tlaxcala);
        signLanguageMap.put("TOBOGAN", R.drawable.tobogan);
        signLanguageMap.put("TOBOGANES", R.drawable.tobogan);
        signLanguageMap.put("TOCAR", R.drawable.tocar);
        signLanguageMap.put("TOCA", R.drawable.tocar);
        signLanguageMap.put("TOCO", R.drawable.tocar);
        signLanguageMap.put("TOLUCA", R.drawable.toluca);
        signLanguageMap.put("TOMAR", R.drawable.tomar);
        signLanguageMap.put("BEBER", R.drawable.tomar);
        signLanguageMap.put("BEBERSE", R.drawable.tomar);
        signLanguageMap.put("TOMA", R.drawable.tomar);
        signLanguageMap.put("TOMARSE", R.drawable.tomar);
        signLanguageMap.put("TOMATE", R.drawable.tomate);
        signLanguageMap.put("TOMATES", R.drawable.tomate);
        signLanguageMap.put("TORONJA", R.drawable.toronja);
        signLanguageMap.put("TORONJAS", R.drawable.toronja);
        signLanguageMap.put("TRADUCIR", R.drawable.traducir);
        signLanguageMap.put("TRADUCE", R.drawable.traducir);
        signLanguageMap.put("TRANSFORMAR", R.drawable.transformar);
        signLanguageMap.put("TRANSFORMA", R.drawable.transformar);
        signLanguageMap.put("TRECE", R.drawable.trece);
        signLanguageMap.put("TREINTA", R.drawable.treinta);
        signLanguageMap.put("TRES", R.drawable.tres);
        signLanguageMap.put("TRESCIENTOS", R.drawable.trescientos);
        signLanguageMap.put("TRIANGULO", R.drawable.triangulo);
        signLanguageMap.put("TRIANGULOS", R.drawable.triangulo);
        signLanguageMap.put("TRIANGULAR", R.drawable.triangulo);
        signLanguageMap.put("TUNA", R.drawable.tuna);
        signLanguageMap.put("TUNAS", R.drawable.tuna);
        signLanguageMap.put("TUTOR", R.drawable.tutor);
        signLanguageMap.put("TUTORES", R.drawable.tutor);
        signLanguageMap.put("ULTIMO", R.drawable.ultimo);
        signLanguageMap.put("ULTIMA", R.drawable.ultimo);
        signLanguageMap.put("ULTIMOS", R.drawable.ultimo);
        signLanguageMap.put("UNIDO", R.drawable.unido);
        signLanguageMap.put("UNION", R.drawable.unido);
        signLanguageMap.put("UNIDOS", R.drawable.unido);
        signLanguageMap.put("UNION_LIBRE", R.drawable.union_libre);
        signLanguageMap.put("UNO", R.drawable.uno);
        signLanguageMap.put("UNTAR", R.drawable.untar);
        signLanguageMap.put("UNTARSE", R.drawable.untar);
        signLanguageMap.put("URGENTE", R.drawable.urgente);
        signLanguageMap.put("URGENTES", R.drawable.urgente);
        signLanguageMap.put("USAR", R.drawable.usar);
        signLanguageMap.put("USA", R.drawable.usar);
        signLanguageMap.put("USARSE", R.drawable.usar);
        signLanguageMap.put("UTIL", R.drawable.util);
        signLanguageMap.put("UTILES", R.drawable.util);
        signLanguageMap.put("UVA", R.drawable.uva);
        signLanguageMap.put("UVAS", R.drawable.uva);
        signLanguageMap.put("VAGO", R.drawable.vago);
        signLanguageMap.put("VAGOS", R.drawable.vago);
        signLanguageMap.put("VALIOSO", R.drawable.valioso);
        signLanguageMap.put("VALIOSA", R.drawable.valioso);
        signLanguageMap.put("VEINTE", R.drawable.veinte);
        signLanguageMap.put("VEINTICINCO", R.drawable.veinticinco);
        signLanguageMap.put("VER", R.drawable.ver);
        signLanguageMap.put("MIRA", R.drawable.ver);
        signLanguageMap.put("MIRAR", R.drawable.ver);
        signLanguageMap.put("OBSERVA", R.drawable.ver);
        signLanguageMap.put("OBSERVAR", R.drawable.ver);
        signLanguageMap.put("VERACRUZ", R.drawable.veracruz);
        signLanguageMap.put("VERDURA", R.drawable.verdura);
        signLanguageMap.put("VERTICAL", R.drawable.vertical);
        signLanguageMap.put("VEZ", R.drawable.vez);
        signLanguageMap.put("VECES", R.drawable.vez);
        signLanguageMap.put("VIEJA", R.drawable.vieja);
        signLanguageMap.put("VIEJAS", R.drawable.vieja);
        signLanguageMap.put("VIEJO", R.drawable.viejo);
        signLanguageMap.put("VIEJOS", R.drawable.viejo);
        signLanguageMap.put("VIRGENSITA", R.drawable.virgen_maria);
        signLanguageMap.put("VIUDA", R.drawable.viuda);
        signLanguageMap.put("VIUDAS", R.drawable.viuda);
        signLanguageMap.put("VIUDO", R.drawable.viudo);
        signLanguageMap.put("VIUDOS", R.drawable.viudo);
        signLanguageMap.put("VOLAR", R.drawable.volar);
        signLanguageMap.put("VUELAN", R.drawable.volar);
        signLanguageMap.put("VUELO", R.drawable.volar);
        signLanguageMap.put("VUELA", R.drawable.volar);
        signLanguageMap.put("VOLEIBOL", R.drawable.voleibol);
        signLanguageMap.put("VOTAR", R.drawable.votar);
        signLanguageMap.put("VOTO", R.drawable.votar);
        signLanguageMap.put("VOZ", R.drawable.voz);
        signLanguageMap.put("VOCES", R.drawable.voz);
        signLanguageMap.put("XALAPA", R.drawable.xalapa);
        signLanguageMap.put("YERNO", R.drawable.yerno);
        signLanguageMap.put("YERNOS", R.drawable.yerno);
        signLanguageMap.put("YUCATAN", R.drawable.yucatan);
        signLanguageMap.put("ZACATECAS", R.drawable.zacatecas);
        signLanguageMap.put("ZANAHORIA", R.drawable.zanahoria);
        signLanguageMap.put("ZANAHORIAS", R.drawable.zanahoria);
        signLanguageMap.put("ZAPATO", R.drawable.zapato);
        signLanguageMap.put("ZAPATOS", R.drawable.zapato);
        signLanguageMap.put("ZAPOTE", R.drawable.zapote);
        signLanguageMap.put("ZAPOTES", R.drawable.zapote);

        //Numeros
        signLanguageMap.put("1", R.drawable.uno);
        signLanguageMap.put("UNA", R.drawable.uno);
        signLanguageMap.put("UNAS", R.drawable.uno);
        signLanguageMap.put("UN", R.drawable.uno);
        signLanguageMap.put("UNOS", R.drawable.uno);
        signLanguageMap.put("2", R.drawable.dos);
        signLanguageMap.put("3", R.drawable.tres);
        signLanguageMap.put("4", R.drawable.cuatro);
        signLanguageMap.put("5", R.drawable.cinco);
        signLanguageMap.put("6", R.drawable.seis);
        signLanguageMap.put("7", R.drawable.siete);
        signLanguageMap.put("8", R.drawable.ocho);
        signLanguageMap.put("9", R.drawable.nueve);
        signLanguageMap.put("10", R.drawable.diez);

        //Adicionales
        signLanguageMap.put("BUENAS", R.drawable.bueno);
        signLanguageMap.put("HOLA", R.drawable.hola);
        signLanguageMap.put("GRACIAS", R.drawable.gracias);
        signLanguageMap.put("QUE", R.drawable.que);
        signLanguageMap.put("COMO", R.drawable.como);
        signLanguageMap.put("CUANDO", R.drawable.cuando);
        signLanguageMap.put("DONDE", R.drawable.donde);
        signLanguageMap.put("QUIEN", R.drawable.quien);
        signLanguageMap.put("QUIENES", R.drawable.quien);
        signLanguageMap.put("CUANTOS", R.drawable.cuantos);
        signLanguageMap.put("CUANTO", R.drawable.cuantos);
        signLanguageMap.put("YA", R.drawable.ya);
        signLanguageMap.put("ES", R.drawable.es);
        signLanguageMap.put("CON", R.drawable.con);
        signLanguageMap.put("PERDON", R.drawable.perdon);
        signLanguageMap.put("PERDONES", R.drawable.perdon);
        signLanguageMap.put("PERDONA", R.drawable.perdon);
        signLanguageMap.put("PERDONAR", R.drawable.perdon);
        signLanguageMap.put("PERDONAME", R.drawable.perdon);
        signLanguageMap.put("PERDONARSE", R.drawable.perdon);
        signLanguageMap.put("ESTA", R.drawable.estar);
        signLanguageMap.put("ESTAS", R.drawable.estar);
        signLanguageMap.put("BUENA", R.drawable.bueno);
        signLanguageMap.put("NOSOTROS", R.drawable.nosotros);
        signLanguageMap.put("NOS", R.drawable.nosotros);
        signLanguageMap.put("NOSOTRAS", R.drawable.nosotros);
        signLanguageMap.put("PASEO", R.drawable.paseo);
        signLanguageMap.put("PASEOS", R.drawable.paseo);
        signLanguageMap.put("PASEANDO", R.drawable.paseo);
        signLanguageMap.put("PASEA", R.drawable.paseo);
        signLanguageMap.put("PASEAS", R.drawable.paseo);
        signLanguageMap.put("PASEAR", R.drawable.paseo);
        signLanguageMap.put("IR", R.drawable.ir);
        signLanguageMap.put("IRSE", R.drawable.ir);
        signLanguageMap.put("VOY", R.drawable.ir);
        signLanguageMap.put("AIRE", R.drawable.aire);
        signLanguageMap.put("AIRES", R.drawable.aire);
        signLanguageMap.put("CASI", R.drawable.casi);
        signLanguageMap.put("HORA", R.drawable.hora);
        signLanguageMap.put("HORAS", R.drawable.hora);
        signLanguageMap.put("PELICULA", R.drawable.pelicula);
        signLanguageMap.put("PELICULAS", R.drawable.pelicula);
        signLanguageMap.put("ESPERAR", R.drawable.esperar);
        signLanguageMap.put("ESPERA", R.drawable.esperar);
        signLanguageMap.put("ESPERO", R.drawable.esperar);
        signLanguageMap.put("ESPERANDO", R.drawable.esperar);
        signLanguageMap.put("ESPERARSE", R.drawable.esperar);
        signLanguageMap.put("ESPERARTE", R.drawable.esperar);
        signLanguageMap.put("VERSE", R.drawable.verse);
        signLanguageMap.put("VEMOS", R.drawable.verse);
        signLanguageMap.put("VEREMOS", R.drawable.verse);
        signLanguageMap.put("VERTE", R.drawable.esperar);

        signLanguageMap.put("MIS", R.drawable.mio);
        signLanguageMap.put("PIERNA", R.drawable.pierna);
        signLanguageMap.put("PIERNAS", R.drawable.pierna);
        signLanguageMap.put("GOLPE", R.drawable.golpear);
        signLanguageMap.put("NECESITAMOS", R.drawable.necesitar);
        signLanguageMap.put("SOY", R.drawable.soy);
        signLanguageMap.put("SOMOS", R.drawable.soy);
        signLanguageMap.put("GENTE", R.drawable.persona);
        signLanguageMap.put("FUERTE", R.drawable.fuerte);
        signLanguageMap.put("MUCHA", R.drawable.mucho);
        signLanguageMap.put("MUCHAS", R.drawable.mucho);
        signLanguageMap.put("CREER", R.drawable.creer);
        signLanguageMap.put("CREE", R.drawable.creer);
        signLanguageMap.put("CREES", R.drawable.creer);
        signLanguageMap.put("CREENCIA", R.drawable.creer);
        signLanguageMap.put("CREENCIAS", R.drawable.creer);
        signLanguageMap.put("PERFECTO", R.drawable.perfecto);
        signLanguageMap.put("PERFECTOS", R.drawable.perfecto);
        signLanguageMap.put("PERFECTA", R.drawable.perfecto);
        signLanguageMap.put("PERFECTAS", R.drawable.perfecto);
        signLanguageMap.put("NUEVO", R.drawable.nuevo);
        signLanguageMap.put("NUEVOS", R.drawable.nuevo);
        signLanguageMap.put("NUEVAS", R.drawable.nuevo);
        signLanguageMap.put("NUEVA", R.drawable.nuevo);
        signLanguageMap.put("ESTABA", R.drawable.estar);
        signLanguageMap.put("ESTABAS", R.drawable.estar);
        signLanguageMap.put("ENCANTADO", R.drawable.encantado);
        signLanguageMap.put("ENCANTADOS", R.drawable.encantado);
        signLanguageMap.put("ENCANTADA", R.drawable.encantado);
        signLanguageMap.put("ENCANTADAS", R.drawable.encantado);
        signLanguageMap.put("ME", R.drawable.yo);
        signLanguageMap.put("SENTIR", R.drawable.sentir);
        signLanguageMap.put("SIENTO", R.drawable.sentir);
        signLanguageMap.put("SENTIRSE", R.drawable.sentir);
        signLanguageMap.put("QUIERES", R.drawable.querer);
        signLanguageMap.put("ALEGRARSE", R.drawable.alegre);
        signLanguageMap.put("ALEGRATE", R.drawable.alegre);
        signLanguageMap.put("MUY", R.drawable.mucho);
        signLanguageMap.put("SALADO", R.drawable.sal);
        signLanguageMap.put("VA", R.drawable.ir);
        signLanguageMap.put("VE", R.drawable.ver);
        signLanguageMap.put("VERNOS", R.drawable.ver);
        signLanguageMap.put("PODER", R.drawable.poder);
        signLanguageMap.put("PUEDO", R.drawable.poder);
        signLanguageMap.put("MINUTO", R.drawable.minuto);
        signLanguageMap.put("MINUTOS", R.drawable.minuto);



    }
}