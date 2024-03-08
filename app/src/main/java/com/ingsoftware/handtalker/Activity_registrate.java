package com.ingsoftware.handtalker;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ingsoftware.handtalker.R;

import java.util.HashMap;
import java.util.Map;

public class Activity_registrate extends AppCompatActivity {

    EditText e4, e5; // Correo y contraseña
    TextView name, ape, tel, corr, contra;
    RelativeLayout fond;
    LinearLayout barra, cajaName, cajaApe, cajaTel, cajaCorr, cajaContra;
    private Button registrarte;
    private ImageView cerrar;
    String themes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrate);

        // Inicialización de componentes de la UI
        e4 = (EditText) findViewById(R.id.correo1);
        e5 = (EditText) findViewById(R.id.contrasena1);
        name = findViewById(R.id.textNombre);
        ape = findViewById(R.id.textApellido);
        tel = findViewById(R.id.textTelefono);
        corr = findViewById(R.id.textCorreo);
        contra = findViewById(R.id.textContra);
        fond = findViewById(R.id.fondo);
        barra = findViewById(R.id.topBar);
        cajaName = findViewById(R.id.name1);
        cajaApe = findViewById(R.id.apellido11);
        cajaTel = findViewById(R.id.telefono11);
        cajaCorr = findViewById(R.id.correo11);
        cajaContra = findViewById(R.id.contra1);

        registrarte = findViewById(R.id.botonRegistra);
        cerrar = findViewById(R.id.atrasx);

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
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.white));
        }

        //Cambiar el tema
        //- Claro
        if (themes.equals("1")){
            //Color de elementos
            barra.setBackgroundColor(ContextCompat.getColor(this, R.color.azulInicio));
            fond.setBackgroundColor(Color.WHITE);
            name.setTextColor(Color.BLACK);
            ape.setTextColor(Color.BLACK);
            tel.setTextColor(Color.BLACK);
            corr.setTextColor(Color.BLACK);
            contra.setTextColor(Color.BLACK);
            registrarte.setTextColor(Color.WHITE);


            //Color de barra de estado y de desplazamiento
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.azulInicio));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.white));
        }

        //- Oscuro
        if (themes.equals("2")){
            //Color de elementos
            barra.setBackgroundColor(ContextCompat.getColor(this, R.color.grisInterfaz));
            fond.setBackgroundColor(Color.BLACK);
            name.setTextColor(Color.WHITE);
            ape.setTextColor(Color.WHITE);
            tel.setTextColor(Color.WHITE);
            corr.setTextColor(Color.WHITE);
            contra.setTextColor(Color.WHITE);
            registrarte.setTextColor(Color.WHITE);
            cajaName.setBackground(ContextCompat.getDrawable(this, R.drawable.edit_text_border_gray_black));
            cajaApe.setBackground(ContextCompat.getDrawable(this, R.drawable.edit_text_border_gray_black));
            cajaTel.setBackground(ContextCompat.getDrawable(this, R.drawable.edit_text_border_gray_black));
            cajaCorr.setBackground(ContextCompat.getDrawable(this, R.drawable.edit_text_border_gray_black));
            cajaContra.setBackground(ContextCompat.getDrawable(this, R.drawable.edit_text_border_gray_black));
            //Color de barra de estado y de desplazamiento
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.grisInterfaz));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.black));
        }

        // Listeners para los botones
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirLogin();
            }
        });

        registrarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });
    }

    private void abrirLogin() {
        Intent intent = new Intent(Activity_registrate.this, LoginActivity.class);
        startActivity(intent);
    }

    private void registrarUsuario() {
        String correo = e4.getText().toString().trim();
        String contrasena = e5.getText().toString().trim();

        if (TextUtils.isEmpty(correo)) {
            Toast.makeText(getApplicationContext(), "Ingresa tu correo", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(contrasena)) {
            Toast.makeText(getApplicationContext(), "Ingresa tu contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        // Firebase Authentication para registrar el usuario
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(correo, contrasena)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Registro exitoso, obtiene el usuario actual
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            // Asegúrate de que el usuario no sea nulo
                            if (user != null) {
                                // Aquí es donde creas el documento en Firestore
                                Map<String, Object> datos = new HashMap<>();
                                // Puedes agregar aquí más campos a la colección 'datos' si es necesario

                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                // Crea un documento con el UID y una subcolección 'datos'
                                db.collection("users").document(user.getUid()).collection("datos").document("datosUsuario")
                                        .set(datos)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(Activity_registrate.this, "Datos de Firestore creados con éxito.", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(Activity_registrate.this, "Error al crear datos de Firestore.", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }

                            Toast.makeText(Activity_registrate.this, "Registro exitoso.", Toast.LENGTH_SHORT).show();
                            abrirLogin(); // O abrir otra actividad según sea necesario
                        } else {
                            // Si el registro falla, muestra un mensaje al usuario.
                            Toast.makeText(Activity_registrate.this, "Fallo en el registro.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}