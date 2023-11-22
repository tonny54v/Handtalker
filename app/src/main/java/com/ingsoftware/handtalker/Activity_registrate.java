package com.ingsoftware.handtalker;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
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

import java.util.HashMap;
import java.util.Map;

public class Activity_registrate extends AppCompatActivity{

    EditText e1,e2,e3,e4,e5;
    RequestQueue requestQueue;

    int bole=0;

    private static final String URL1 = "http://192.168.1.3:8080/handtalker/save.php";

    private Button registrarte;
    private ImageView cerrar;
    private RelativeLayout backr;
    private LinearLayout barraTop;
    private TextView nombreT;
    private TextView apellidoT;
    private TextView telefonoT;
    private TextView correoT;
    private TextView contrasenaT;
    private LinearLayout marcoName;
    private LinearLayout marcoApe;
    private LinearLayout marcoTel;
    private LinearLayout marcoCorr;
    private LinearLayout marcoContr;
    String themes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrate);

        requestQueue = Volley.newRequestQueue(this);

        //Configuracion Global del tema
        String currentValue = globalTheme.getInstance().getGlobalTema();
        themes=currentValue;

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            themes = extras.getString(themes);
        }

        e1=(EditText)findViewById(R.id.nombre1);
        e2=(EditText)findViewById(R.id.apellido1);
        e3=(EditText)findViewById(R.id.telefono1);
        e4=(EditText)findViewById(R.id.correo1);
        e5=(EditText)findViewById(R.id.contrasena1);

        barraTop = findViewById(R.id.topBar);
        backr = findViewById(R.id.fondo);
        nombreT = findViewById(R.id.textNombre);
        apellidoT = findViewById(R.id.textApellido);
        telefonoT = findViewById(R.id.textTelefono);
        correoT = findViewById(R.id.textCorreo);
        contrasenaT = findViewById(R.id.textContra);

        marcoName = findViewById(R.id.name1);
        marcoApe = findViewById(R.id.apellido11);
        marcoTel = findViewById(R.id.telefono11);
        marcoCorr = findViewById(R.id.correo11);
        marcoContr = findViewById(R.id.contra1);

        // Cambiar el color de la barra de estado
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.azulInicio));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.white));
        }

        cerrar = findViewById(R.id.atrasx);
        registrarte = findViewById(R.id.botonRegistra);

        //Cambiar el tema
        //- Claro
        if (themes.equals("1")){
            //Color de elementos
            barraTop.setBackgroundColor(ContextCompat.getColor(this, R.color.azulInicio));
            backr.setBackgroundColor(Color.WHITE);
            nombreT.setTextColor(Color.BLACK);
            apellidoT.setTextColor(Color.BLACK);
            telefonoT.setTextColor(Color.BLACK);
            correoT.setTextColor(Color.BLACK);
            contrasenaT.setTextColor(Color.BLACK);
            registrarte.setTextColor(Color.WHITE);
            marcoName.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_border));
            marcoApe.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_border));
            marcoTel.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_border));
            marcoCorr.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_border));
            marcoContr.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_border));
            e1.setTextColor(Color.BLACK);
            e2.setTextColor(Color.BLACK);
            e3.setTextColor(Color.BLACK);
            e4.setTextColor(Color.BLACK);
            e5.setTextColor(Color.BLACK);


            //Color de barra de estado y de desplazamiento
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.azulInicio));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.white));
        }
        //- Oscuro
        if (themes.equals("2")){
            //Color de elementos
            barraTop.setBackgroundColor(ContextCompat.getColor(this, R.color.grisInterfaz));
            backr.setBackgroundColor(Color.BLACK);
            nombreT.setTextColor(Color.WHITE);
            apellidoT.setTextColor(Color.WHITE);
            telefonoT.setTextColor(Color.WHITE);
            correoT.setTextColor(Color.WHITE);
            contrasenaT.setTextColor(Color.WHITE);
            registrarte.setTextColor(Color.WHITE);
            marcoName.setBackground(ContextCompat.getDrawable(this, R.drawable.edit_text_border_gray_black));
            marcoApe.setBackground(ContextCompat.getDrawable(this, R.drawable.edit_text_border_gray_black));
            marcoTel.setBackground(ContextCompat.getDrawable(this, R.drawable.edit_text_border_gray_black));
            marcoCorr.setBackground(ContextCompat.getDrawable(this, R.drawable.edit_text_border_gray_black));
            marcoContr.setBackground(ContextCompat.getDrawable(this, R.drawable.edit_text_border_gray_black));
            e1.setTextColor(Color.WHITE);
            e2.setTextColor(Color.WHITE);
            e3.setTextColor(Color.WHITE);
            e4.setTextColor(Color.WHITE);
            e5.setTextColor(Color.WHITE);

            //Color de barra de estado y de desplazamiento
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.grisInterfaz));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.black));
        }

        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirLogin();
            }
        });

        registrarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar(v);
            }
        });

    }

    private void abrirLogin() {
        Intent intent = new Intent(Activity_registrate.this, LoginActivity.class);
        startActivity(intent);
    }

    public void validar(View v){
        final String nombre = e1.getText().toString();
        final String apellido =e2.getText().toString();
        final String telefono =e3.getText().toString();
        final String correo = e4.getText().toString();
        final String contrasena =e5.getText().toString();

        createUser(nombre,apellido,telefono,correo,contrasena);
    }

    private void createUser(String nombre, String apellido, String telefono, String correo, String contrasena) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Activity_registrate.this,"Se registró correctamente", Toast.LENGTH_SHORT).show();
                        abrirLogin();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Activity_registrate.this,"Ingresa los datos faltantes", Toast.LENGTH_SHORT).show();

                    }
                }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                if(nombre.equals("") || apellido.equals("") || telefono.equals("") || correo.equals("") || contrasena.equals("")){
                    Toast.makeText(Activity_registrate.this,"Faltan datos", Toast.LENGTH_SHORT).show();
                }else{
                    params.put("nombre", nombre);
                    params.put("apellido", apellido);
                    params.put("telefono", telefono);
                    params.put("correo", correo);
                    params.put("contrasena", contrasena);
                }
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }


}