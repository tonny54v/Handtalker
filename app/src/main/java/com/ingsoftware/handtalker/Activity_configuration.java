package com.ingsoftware.handtalker;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.ingsoftware.handtalker.R;

public class Activity_configuration extends AppCompatActivity {

    private ImageView atrasBoton;
    private LinearLayout cerrarSesion;
    private RelativeLayout backr;
    private LinearLayout barraTop;
    private LinearLayout marcoTamFuente;
    private  ImageView tamImg;
    private TextView textTam;
    private LinearLayout marcoTema;
    private  ImageView temaImg;
    private TextView textTemas;
    private LinearLayout marcoTamGraf;
    private TextView textgraf;
    private  ImageView cerrImg;
    private TextView textCerr;
    String themes;

    // Variable para mantener la selección del usuario
    private String temaSeleccionado;
    private String tamanoSeleccionadoFuent;
    private String tamanoSeleccionadoGraf;
    String tamFuente;
    String tamGrafico;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        //Configuracion Global del tema
        String currentValue = globalTheme.getInstance().getGlobalTema();
        themes=currentValue;

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            themes = extras.getString(themes);
        }

        //Configuracion Global del la fuente
        String currentValue3 = globalFuente.getInstance().getGlobalTamanoF();
        tamFuente=currentValue3;

        Bundle extras3 = getIntent().getExtras();
        if (extras3 != null){
            tamFuente = extras3.getString(tamFuente);
        }

        //Configuracion Global del grafico
        String currentValue4 = globalGrafico.getInstance().getGlobalTamanoG();
        tamGrafico=currentValue4;

        Bundle extras4 = getIntent().getExtras();
        if (extras4 != null){
            tamGrafico = extras4.getString(tamGrafico);
        }

        // Cambiar el color de la barra de estado
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.azulInicio));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.white));
        }

        atrasBoton = findViewById(R.id.atras);
        cerrarSesion = findViewById(R.id.cerrarSesion);
        backr = findViewById(R.id.fondo);
        barraTop = findViewById(R.id.topBar);
        marcoTamFuente = findViewById(R.id.tamanofuente);
        tamImg = findViewById(R.id.img_tamano_text);
        textTam = findViewById(R.id.tamText);
        marcoTema = findViewById(R.id.tema);
        temaImg = findViewById(R.id.temaimg);
        textTemas = findViewById(R.id.textTema);
        marcoTamGraf = findViewById(R.id.tamanografico);
        textgraf = findViewById(R.id.textGraf);
        cerrImg = findViewById(R.id.cerrarImg);
        textCerr = findViewById(R.id.textCierra);

        //Cambiar el tema
        //- Claro
        if (themes.equals("1")){
            //Color de elementos
            backr.setBackgroundColor(Color.WHITE);
            barraTop.setBackgroundColor(ContextCompat.getColor(this, R.color.azulInicio));
            tamImg.setImageResource(R.drawable.tamanotexto);
            temaImg.setImageResource(R.drawable.temaimg);
            cerrImg.setImageResource(R.drawable.cerrarses);
            textTam.setTextColor(Color.BLACK);
            textTemas.setTextColor(Color.BLACK);
            textgraf.setTextColor(Color.BLACK);
            textCerr.setTextColor(Color.BLACK);

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
            tamImg.setImageResource(R.drawable.tamanotexto_white);
            temaImg.setImageResource(R.drawable.temaimg_white);
            cerrImg.setImageResource(R.drawable.cerrarses_white);
            textTam.setTextColor(Color.WHITE);
            textTemas.setTextColor(Color.WHITE);
            textgraf.setTextColor(Color.WHITE);
            textCerr.setTextColor(Color.WHITE);

            //Color de barra de estado y de desplazamiento
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.grisInterfaz));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.black));
        }


        atrasBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regresarInicio();
            }
        });

        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regresarLogin();
            }
        });

        marcoTema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoSeleccionTema();
            }
        });

        marcoTamFuente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoSeleccionTamanoFuent();
            }
        });

        marcoTamGraf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoSeleccionTamanoGraf();
            }
        });
    }

    //Muestra el dialogo al presionar (Tema)
    private void mostrarDialogoSeleccionTema() {
        // Crear el builder del diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_configuration.this, R.style.AlertDialogCustom);
        builder.setTitle("Elige un tema");

        // Opciones para el diálogo
        final CharSequence[] items = {"Claro", "Oscuro"};

        // Valor predeterminado (ninguno seleccionado)
        temaSeleccionado = "Claro";

        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // 'which' es el índice del elemento seleccionado.
                switch (which) {
                    case 0: // Claro
                        temaSeleccionado = "Claro";
                        themes = "1";
                        globalTheme.getInstance().setGlobalTema(themes);
                        break;
                    case 1: // Oscuro
                        temaSeleccionado = "Oscuro";
                        themes = "2";
                        globalTheme.getInstance().setGlobalTema(themes);
                        break;
                    default:
                        temaSeleccionado = "Ningun tema seleccionado"; // Ninguno seleccionado
                        break;
                }

                // Si se ha seleccionado una opción, habilitamos el botón OK
                if (dialog instanceof AlertDialog) {
                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                }
            }
        });

        // Añadir botones de acción
        builder.setPositiveButton("Aplicar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // El usuario confirmó su selección.
                // Aquí puedes hacer algo con la variable 'temaSeleccionado'.
                Toast.makeText(Activity_configuration.this, "Tema aplicado.", Toast.LENGTH_SHORT).show();
                regresarConfig();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // Si presionan cancelar, simplemente cierras el diálogo.
                dialog.dismiss();
            }
        });

        // Crear y mostrar el AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
        // Inicialmente, deshabilita el botón OK si no se ha seleccionado ninguna opción
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
        // Personalización de colores después de mostrar el diálogo
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.azulInicio));
        // Personalización de colores después de mostrar el diálogo
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.azulInicio));


    }

    //Muestra el dialogo al presionar (Tamano Fuente)
    private void mostrarDialogoSeleccionTamanoFuent() {
        // Crear el builder del diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_configuration.this, R.style.AlertDialogCustom);
        builder.setTitle("Tamaño de fuente (Traduccion)");

        // Opciones para el diálogo
        final CharSequence[] items = {"Pequeño", "Mediano", "Grande"};

        // Valor predeterminado (ninguno seleccionado)
        tamanoSeleccionadoFuent = "Pequeño";

        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // 'which' es el índice del elemento seleccionado.
                switch (which) {
                    case 0: // pequeño
                        tamanoSeleccionadoFuent = "Pequeño";
                        tamFuente = "1";
                        globalFuente.getInstance().setGlobalTamanoF(tamFuente);
                        break;
                    case 1: // mediano
                        tamanoSeleccionadoFuent = "Mediano";
                        tamFuente = "2";
                        globalFuente.getInstance().setGlobalTamanoF(tamFuente);
                        break;
                    case 2: //Grande
                        tamanoSeleccionadoFuent = "Grande";
                        tamFuente = "3";
                        globalFuente.getInstance().setGlobalTamanoF(tamFuente);
                        break;
                    default:
                        tamanoSeleccionadoFuent = "Ningun tamaño seleccionado"; // Ninguno seleccionado
                        break;
                }

                // Si se ha seleccionado una opción, habilitamos el botón OK
                if (dialog instanceof AlertDialog) {
                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                }
            }
        });

        // Añadir botones de acción
        builder.setPositiveButton("Aplicar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // El usuario confirmó su selección.
                // Aquí puedes hacer algo con la variable 'temaSeleccionado'.
                Toast.makeText(Activity_configuration.this, "Tamaño aplicado", Toast.LENGTH_SHORT).show();
                regresarConfig();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // Si presionan cancelar, simplemente cierras el diálogo.
                dialog.dismiss();
            }
        });

        // Crear y mostrar el AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
        // Inicialmente, deshabilita el botón OK si no se ha seleccionado ninguna opción
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
        // Personalización de colores después de mostrar el diálogo
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.azulInicio));
        // Personalización de colores después de mostrar el diálogo
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.azulInicio));
    }

    //Muestra el dialogo al presionar (Tamano grafico)
    private void mostrarDialogoSeleccionTamanoGraf() {
        // Crear el builder del diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_configuration.this, R.style.AlertDialogCustom);
        builder.setTitle("Tamaño de imagenes (Traduccion)");

        // Opciones para el diálogo
        final CharSequence[] items = {"Pequeño", "Mediano", "Grande"};

        // Valor predeterminado (ninguno seleccionado)
        tamanoSeleccionadoGraf = "Pequeño";

        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // 'which' es el índice del elemento seleccionado.
                switch (which) {
                    case 0: // pequeño
                        tamanoSeleccionadoGraf = "Pequeño";
                        tamGrafico = "1";
                        globalGrafico.getInstance().setGlobalTamanoG(tamGrafico);
                        break;
                    case 1: // mediano
                        tamanoSeleccionadoGraf = "Mediano";
                        tamGrafico = "2";
                        globalGrafico.getInstance().setGlobalTamanoG(tamGrafico);
                        break;
                    case 2: //Grande
                        tamanoSeleccionadoGraf = "Grande";
                        tamGrafico = "3";
                        globalGrafico.getInstance().setGlobalTamanoG(tamGrafico);
                        break;
                    default:
                        tamanoSeleccionadoGraf = "Ningun tamaño seleccionado"; // Ninguno seleccionado
                        break;
                }

                // Si se ha seleccionado una opción, habilitamos el botón OK
                if (dialog instanceof AlertDialog) {
                    ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                }
            }
        });

        // Añadir botones de acción
        builder.setPositiveButton("Aplicar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // El usuario confirmó su selección.
                // Aquí puedes hacer algo con la variable 'temaSeleccionado'.
                Toast.makeText(Activity_configuration.this, "Tamaño aplicado", Toast.LENGTH_SHORT).show();
                regresarConfig();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // Si presionan cancelar, simplemente cierras el diálogo.
                dialog.dismiss();
            }
        });

        // Crear y mostrar el AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
        // Inicialmente, deshabilita el botón OK si no se ha seleccionado ninguna opción
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
        // Personalización de colores después de mostrar el diálogo
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.azulInicio));
        // Personalización de colores después de mostrar el diálogo
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.azulInicio));
    }

    private void regresarInicio() {
        Intent intent = new Intent(Activity_configuration.this, HandTalkerMainActivity.class);
        startActivity(intent);
    }

    private void regresarLogin() {
        Intent intent = new Intent(Activity_configuration.this, LoginActivity.class);
        startActivity(intent);
    }

    private void regresarConfig() {
        Intent intent = new Intent(Activity_configuration.this, Activity_configuration.class);
        startActivity(intent);
    }




}