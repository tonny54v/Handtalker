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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Activity_editar_perfil extends AppCompatActivity {

    private ImageView atras;
    private ImageView guardar;
    private EditText etname;
    private EditText etApellido;
    private EditText etTelefono;
    private EditText etCorreos;
    private EditText etContrasena;
    private RelativeLayout backr;
    private RelativeLayout barraTop;
    private TextView textNoms;
    private LinearLayout marcoNom;
    private TextView textApes;
    private LinearLayout marcoApes;
    private TextView textTels;
    private LinearLayout marcoTel;
    private TextView textCorr;
    private LinearLayout marcoCorr;
    private TextView textContr;
    private LinearLayout marcoContr;
    private ImageView fotoEdit;

    String id;
    String themes;
    String idFotos;

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

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

        //Configuracion Global del la foto de perfil
        String currentValue3 = globalFoto.getInstance().getGlobalFoto();
        idFotos=currentValue3;

        Bundle extras3 = getIntent().getExtras();
        if (extras3 != null){
            idFotos = extras3.getString(idFotos);
        }

        // Cambiar el color de la barra de estado
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.azulInicio));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.white));
        }

        atras = findViewById(R.id.atras_edit);
        guardar = findViewById(R.id.guardarCambios);

        etname = findViewById(R.id.nombres);
        etApellido = findViewById(R.id.apellidos);
        etTelefono = findViewById(R.id.telefonos);
        etCorreos = findViewById(R.id.correos);
        etContrasena = findViewById(R.id.contrasenas);

        backr = findViewById(R.id.fondo);
        barraTop = findViewById(R.id.topBar);
        textNoms = findViewById(R.id.textName);
        marcoNom = findViewById(R.id.name);
        textApes = findViewById(R.id.textApe);
        marcoApes = findViewById(R.id.apellido);
        textTels = findViewById(R.id.textTel);
        marcoTel = findViewById(R.id.telefono);
        textCorr = findViewById(R.id.textCorre);
        marcoCorr = findViewById(R.id.correo);
        textContr = findViewById(R.id.textContras);
        marcoContr = findViewById(R.id.infoContrasena2);
        fotoEdit = findViewById(R.id.foto);

        //Cambiar el tema
        //- Claro
        if (themes.equals("1")){
            //Color de elementos
            backr.setBackgroundColor(Color.WHITE);
            barraTop.setBackgroundColor(ContextCompat.getColor(this, R.color.azulInicio));
            textNoms.setTextColor(Color.BLACK);
            textApes.setTextColor(Color.BLACK);
            textTels.setTextColor(Color.BLACK);
            textCorr.setTextColor(Color.BLACK);
            textContr.setTextColor(Color.BLACK);
            marcoNom.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_border));
            marcoApes.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_border));
            marcoTel.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_border));
            marcoCorr.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_border));
            marcoContr.setBackground(ContextCompat.getDrawable(this, R.drawable.edittext_border));
            etname.setTextColor(Color.BLACK);
            etApellido.setTextColor(Color.BLACK);
            etCorreos.setTextColor(Color.BLACK);
            etTelefono.setTextColor(Color.BLACK);
            etContrasena.setTextColor(Color.BLACK);

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
            textNoms.setTextColor(Color.WHITE);
            textApes.setTextColor(Color.WHITE);
            textTels.setTextColor(Color.WHITE);
            textCorr.setTextColor(Color.WHITE);
            textContr.setTextColor(Color.WHITE);
            marcoNom.setBackground(ContextCompat.getDrawable(this, R.drawable.edit_text_border_gray_black));
            marcoApes.setBackground(ContextCompat.getDrawable(this, R.drawable.edit_text_border_gray_black));
            marcoTel.setBackground(ContextCompat.getDrawable(this, R.drawable.edit_text_border_gray_black));
            marcoCorr.setBackground(ContextCompat.getDrawable(this, R.drawable.edit_text_border_gray_black));
            marcoContr.setBackground(ContextCompat.getDrawable(this, R.drawable.edit_text_border_gray_black));
            etname.setTextColor(Color.WHITE);
            etApellido.setTextColor(Color.WHITE);
            etCorreos.setTextColor(Color.WHITE);
            etTelefono.setTextColor(Color.WHITE);
            etContrasena.setTextColor(Color.WHITE);

            //Color de barra de estado y de desplazamiento
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.grisInterfaz));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.black));
        }

        readUser();

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {abrirPerfil();}
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {validar(v);}
        });

        fotoEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {abrirEditFoto(v);}
        });
    }

    private void abrirEditFoto(View v) {
        Intent intent = new Intent(Activity_editar_perfil.this, Activity_seleccionar_foto.class);
        startActivity(intent);
    }

    private void abrirPerfil() {
        Intent intent = new Intent(Activity_editar_perfil.this, Activity_handtalker_perfil.class);
        startActivity(intent);
    }

    private void readUser(){
        String URL = "http://192.168.8.11:8080/handtalker/fetch.php?id="+id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String nombre, apellido, telefono, correo, contrasena, idFoto;
                        try {
                            nombre = response.getString("nombre");
                            apellido = response.getString("apellido");
                            telefono = response.getString("telefono");
                            correo = response.getString("correo");
                            contrasena = response.getString("contrasena");
                            idFoto = idFotos;

                            etname.setText(nombre);
                            etApellido.setText(apellido);
                            etTelefono.setText(telefono);
                            etCorreos.setText(correo);
                            etContrasena.setText(contrasena);

                            //Selecciona la foto de perfil de acuerdo al idFoto
                            if (idFoto.equals("1")){
                                fotoEdit.setImageResource(R.drawable.perfilicon);
                            }
                            if (idFoto.equals("2")){
                                fotoEdit.setImageResource(R.drawable.foto_defecto2);
                            }
                            if (idFoto.equals("3")){
                                fotoEdit.setImageResource(R.drawable.foto_defecto3);
                            }
                            if (idFoto.equals("4")){
                                fotoEdit.setImageResource(R.drawable.foto_defecto4);
                            }
                            if (idFoto.equals("5")){
                                fotoEdit.setImageResource(R.drawable.foto_defecto5);
                            }
                            if (idFoto.equals("6")){
                                fotoEdit.setImageResource(R.drawable.foto_defecto6);
                            }
                            if (idFoto.equals("7")){
                                fotoEdit.setImageResource(R.drawable.foto_defecto7);
                            }
                            if (idFoto.equals("8")){
                                fotoEdit.setImageResource(R.drawable.foto_edicion_taylor);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        fotoEdit.setImageResource(R.drawable.perfilicon);
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    public void validar(View v){
        final String nombre = etname.getText().toString();
        final String apellido =etApellido.getText().toString();
        final String telefono =etTelefono.getText().toString();
        final String correo = etCorreos.getText().toString();
        final String contrasena =etContrasena.getText().toString();

        actualizarUser(nombre,apellido,telefono,correo,contrasena);
    }

    private void actualizarUser(String nombre, String apellido, String telefono, String correo, String contrasena) {
        String URL1 = "http://192.168.8.11:8080/handtalker/actualizar.php";
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URL1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Activity_editar_perfil.this,"Se actualizaron los datos de usuario", Toast.LENGTH_SHORT).show();
                        abrirPerfil();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Activity_editar_perfil.this,"Ingresa los datos faltantes", Toast.LENGTH_SHORT).show();

                    }
                }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                if(nombre.equals("") || apellido.equals("") || telefono.equals("") || correo.equals("") || contrasena.equals("")){
                    Toast.makeText(Activity_editar_perfil.this,"Faltan datos", Toast.LENGTH_SHORT).show();
                }else{
                    params.put("id",id);
                    params.put("nombre", nombre);
                    params.put("apellido", apellido);
                    params.put("telefono", telefono);
                    params.put("correo", correo);
                    params.put("contrasena", contrasena);
                    params.put("idFoto", idFotos);
                }
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}