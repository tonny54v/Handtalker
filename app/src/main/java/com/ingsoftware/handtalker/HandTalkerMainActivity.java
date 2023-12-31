package com.ingsoftware.handtalker;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.ingsoftware.handtalker.R;

public class HandTalkerMainActivity extends AppCompatActivity {

    private static final int TIEMPO_ENTRE_PULSACIONES = 2000; // 2 segundos
    private long tiempoUltimaPulsacion;
    private Toast toast;

    private ImageView imagenCentro1;
    private ImageView home1;
    private ImageView traduccion1;
    private ImageView camara1;
    private ImageView perfil1;
    private ImageView config;

    private RelativeLayout backr;
    private LinearLayout BarraTop;
    private LinearLayout barraDown;
    private ImageView guiaR;

    String themes;
    String mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handtalker_main);

        //Configuracion Global del tema
        String currentValue = globalTheme.getInstance().getGlobalTema();
        themes=currentValue;

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            themes = extras.getString(themes);
        }

        //Configuracion de aparicion de mensaje de inicio (Guia)
        String currentValue6 = globalMensaje.getInstance().getGlobalMensajeInicio();
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

        imagenCentro1 = findViewById(R.id.imagen_centro1);
        home1 = findViewById(R.id.inicio);
        traduccion1 = findViewById(R.id.traduccion);
        camara1 = findViewById(R.id.camara);
        perfil1 = findViewById(R.id.perfil);
        config = findViewById(R.id.ajuste);
        backr = findViewById(R.id.fondo);
        BarraTop = findViewById(R.id.topBar);
        barraDown = findViewById(R.id.barraAbajo);
        guiaR = findViewById(R.id.guiaRapida);

        //Muestra el mensaje una sola vez al abrir la app
        if (mensaje.equals("1")){
            abrirInfoGuia();
            //Cambia el valor para no volver a mostrarlo en una sola sesion
            mensaje = "2";
            globalMensaje.getInstance().setGlobalMensajeInicio(mensaje);
        }

        //Cambiar el tema
        //- Claro
        if (themes.equals("1")){
            //Color de elementos
            backr.setBackgroundColor(Color.WHITE);
            BarraTop.setBackgroundColor(ContextCompat.getColor(this, R.color.azulInicio));
            barraDown.setBackgroundColor(ContextCompat.getColor(this, R.color.azulInicio));
            guiaR.setImageResource(R.drawable.guia_rapida_blanco);

            //Color de barra de estado y de desplazamiento
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.azulInicio));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.azulInicio));

            // Cambiar la imagen del centro basada en la hora
            int hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY);
            if (hour > 0 && hour <= 11) {
                imagenCentro1.setImageResource(R.drawable.goodmorning);
            } else if(hour >= 12 && hour <= 18){
                imagenCentro1.setImageResource(R.drawable.goodafternoon);
            } else if(hour >= 19 && hour < 23 ){
                imagenCentro1.setImageResource(R.drawable.goodnight);
            }else if (hour == 0){
                imagenCentro1.setImageResource(R.drawable.goodmorning);
            }
        }

        //- Oscuro
        if (themes.equals("2")){
            //Color de elementos
            backr.setBackgroundColor(Color.BLACK);
            BarraTop.setBackgroundColor(ContextCompat.getColor(this, R.color.grisInterfaz));
            barraDown.setBackgroundColor(ContextCompat.getColor(this, R.color.grisInterfaz));
            guiaR.setImageResource(R.drawable.guia_rapida_blanco);

            //Color de barra de estado y de desplazamiento
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.grisInterfaz));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.grisInterfaz));

            // Cambiar la imagen del centro basada en la hora
            int hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY);
            if (hour > 0 && hour <= 11) {
                imagenCentro1.setImageResource(R.drawable.goodmornigblack);
            } else if(hour >= 12 && hour <= 18){
                imagenCentro1.setImageResource(R.drawable.goodafternoonblack);
            } else if(hour >= 19 && hour < 23 ){
                imagenCentro1.setImageResource(R.drawable.goodnightblack);
            }else if (hour == 0){
                imagenCentro1.setImageResource(R.drawable.goodmornigblack);
            }
        }


        //Eventos de los botones
        traduccion1.setOnClickListener(new View.OnClickListener() {
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

        guiaR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (themes.equals("1")){
                    // Crear el constructor del AlertDialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(HandTalkerMainActivity.this, R.style.AlertDialogCustom);

                    // Configurar el mensaje y el botón del AlertDialog
                    builder.setTitle("Bienvenid@ a Handtalker");
                    builder.setMessage("* Traduce de texto a lenguaje de señas. \n\n" +
                                    "* Utiliza la camara para comprender el lenguaje. (No disponible aun) \n\n" +
                                    "* Personaliza la app a tu gusto y necesidades en el panel de configuracion.")
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(HandTalkerMainActivity.this, R.style.AlertDialogCustom_black);

                    // Configurar el mensaje y el botón del AlertDialog
                    builder.setTitle("Bienvenid@ a Handtalker");
                    builder.setMessage("* Traduce de texto a lenguaje de señas. \n\n" +
                                    "* Utiliza la camara para comprender el lenguaje. (No disponible aun) \n\n" +
                                    "* Personaliza la app a tu gusto y necesidades en el panel de configuracion.")
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
    }

    private void abrirInfoGuia(){
        if (themes.equals("1")){
            // Crear el constructor del AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(HandTalkerMainActivity.this, R.style.AlertDialogCustom);

            // Configurar el mensaje y el botón del AlertDialog
            builder.setTitle("Bienvenid@ a Handtalker");
            builder.setMessage("* Traduce de texto a lenguaje de señas. \n\n" +
                            "* Utiliza la camara para comprender el lenguaje. (No disponible aun) \n\n" +
                            "* Personaliza la app a tu gusto y necesidades en el panel de configuracion.")
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
            AlertDialog.Builder builder = new AlertDialog.Builder(HandTalkerMainActivity.this, R.style.AlertDialogCustom_black);

            // Configurar el mensaje y el botón del AlertDialog
            builder.setTitle("Bienvenid@ a Handtalker");
            builder.setMessage("* Traduce de texto a lenguaje de señas. \n\n" +
                            "* Utiliza la camara para comprender el lenguaje. (No disponible aun) \n\n" +
                            "* Personaliza la app a tu gusto y necesidades en el panel de configuracion.")
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

    private void abrirConfig() {
        Intent intent = new Intent(HandTalkerMainActivity.this, Activity_configuration.class);
        startActivity(intent);
    }

    private void abrirVentanaBlanco() {
        Intent intent = new Intent(HandTalkerMainActivity.this, VentanaBlancoActivity.class);
        startActivity(intent);
    }

    private void abrirPerfil() {
        Intent intent = new Intent(HandTalkerMainActivity.this, Activity_handtalker_perfil.class);
        startActivity(intent);
    }

    private void abrirTraduccion() {
        Intent intent = new Intent(HandTalkerMainActivity.this, Activity_handtalker_traduccion.class);
        startActivity(intent);
    }

    private void abrircamara() {
        Intent intent = new Intent(HandTalkerMainActivity.this, Activity_handtalker_camera.class);
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
}

