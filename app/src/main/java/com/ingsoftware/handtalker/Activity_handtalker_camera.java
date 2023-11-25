package com.ingsoftware.handtalker;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.ingsoftware.handtalker.R;

public class Activity_handtalker_camera extends AppCompatActivity {

    private static final int TIEMPO_ENTRE_PULSACIONES = 2000; // 2 segundos
    private long tiempoUltimaPulsacion;
    private Toast toast;

    private ImageView home1;
    private ImageView traduccion1;
    private ImageView camara1;
    private ImageView perfil1;
    private ImageView config;
    private RelativeLayout backr;
    private LinearLayout barraTop;
    private FrameLayout cameraSec;
    private TextView traduc;
    private LinearLayout barraDown;
    private ImageView guiaR;
    String themes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handtalker_camera);

        //Configuracion Global del tema
        String currentValue = globalTheme.getInstance().getGlobalTema();
        themes=currentValue;

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            themes = extras.getString(themes);
        }

        // Cambiar el color de la barra de estado
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.azulInicio));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.azulInicio));
        }

        home1 = findViewById(R.id.inicio);
        traduccion1 = findViewById(R.id.traduccion);
        camara1 = findViewById(R.id.camara);
        perfil1 = findViewById(R.id.perfil);
        config = findViewById(R.id.ajuste);

        backr = findViewById(R.id.fondo);
        barraTop = findViewById(R.id.topBar);
        cameraSec = findViewById(R.id.camera_preview);
        traduc = findViewById(R.id.textTraduc);
        barraDown = findViewById(R.id.downBar);
        guiaR = findViewById(R.id.guiaRapida);

        //Cambiar el tema
        //- Claro
        if (themes.equals("1")){
            //Color de elementos
            backr.setBackgroundColor(Color.WHITE);
            barraTop.setBackgroundColor(ContextCompat.getColor(this, R.color.azulInicio));
            barraDown.setBackgroundColor(ContextCompat.getColor(this, R.color.azulInicio));
            cameraSec.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_border));
            traduc.setTextColor(Color.BLACK);
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
            cameraSec.setBackground(ContextCompat.getDrawable(this, R.drawable.edit_text_border_gray_black));
            traduc.setTextColor(Color.WHITE);
            guiaR.setImageResource(R.drawable.guia_rapida_blanco);

            //Color de barra de estado y de desplazamiento
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.grisInterfaz));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.grisInterfaz));
        }

        //Eventos de los botones
        home1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirInicio();
            }
        });

        traduccion1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTraduccion();
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_handtalker_camera.this, R.style.AlertDialogCustom);

                    // Configurar el mensaje y el botón del AlertDialog
                    builder.setTitle("¿Cómo funciona?");
                    String trad= "Traduccion mediante camara";
                    builder.setMessage("Funcion \""+ trad +"\" aun no esta disponible.")
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_handtalker_camera.this, R.style.AlertDialogCustom_black);

                    // Configurar el mensaje y el botón del AlertDialog
                    builder.setTitle("Traduccion mediante camara");
                    builder.setMessage("Describe lo que hace cada boton de la barra inferior")
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


    private void abrirConfig() {
        Intent intent = new Intent(Activity_handtalker_camera.this, Activity_configuration.class);
        startActivity(intent);
    }

    private void abrirVentanaBlanco() {
        Intent intent = new Intent(Activity_handtalker_camera.this, VentanaBlancoActivity.class);
        startActivity(intent);
    }

    private void abrirPerfil() {
        Intent intent = new Intent(Activity_handtalker_camera.this, Activity_handtalker_perfil.class);
        startActivity(intent);
    }

    private void abrirInicio() {
        Intent intent = new Intent(Activity_handtalker_camera.this, HandTalkerMainActivity.class);
        startActivity(intent);
    }

    private void abrirTraduccion() {
        Intent intent = new Intent(Activity_handtalker_camera.this, Activity_handtalker_traduccion.class);
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