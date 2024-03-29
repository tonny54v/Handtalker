package com.ingsoftware.handtalker;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.ingsoftware.handtalker.R;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.view.Surface;
import android.view.TextureView;
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

import java.util.Arrays;

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
    String mensaje;

    // Variables para la cámara
    private TextureView textureView;
    private CameraDevice cameraDevice;
    private CameraCaptureSession cameraCaptureSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handtalker_camera);

        //Configuracion Global del tema
        String currentValue = globalTheme.getInstance().getGlobalTema();
        themes=currentValue;
        textureView = findViewById(R.id.textureView); // Asegúrate de tener un TextureView en tu layout con este ID
        textureView.setSurfaceTextureListener(surfaceTextureListener);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            themes = extras.getString(themes);
        }

        //Configuracion de aparicion de mensaje de inicio (Guia)
        String currentValue6 = globalMensaje.getInstance().getGlobalMensajeCamara();
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

        //Muestra el mensaje una sola vez al abrir la app
        if (mensaje.equals("1")){
            abrirInfoGuia();
            //Cambia el valor para no volver a mostrarlo en una sola sesion
            mensaje = "2";
            globalMensaje.getInstance().setGlobalMensajeCamara(mensaje);
        }

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
                    builder.setMessage("La funcion \""+ trad +"\" aun no esta disponible.")
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
                    String trad= "Traduccion mediante camara";
                    builder.setMessage("La funcion \""+ trad +"\" aun no esta disponible.")
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

    private void openCamera() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                // Mostrar diálogo de permisos si no se ha otorgado
                // Aquí deberías solicitar los permisos necesarios.
                return;
            }
            cameraManager.openCamera(cameraId, stateCallback, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }


    // Callbacks para el estado de la cámara
    private final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            cameraDevice = camera;
            createCameraPreviewSession();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            camera.close();
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            camera.close();
            cameraDevice = null;
        }
    };

    // Crear una sesión de captura para la vista previa
    private void createCameraPreviewSession() {
        try {
            SurfaceTexture texture = textureView.getSurfaceTexture();
            assert texture != null;
            texture.setDefaultBufferSize(textureView.getWidth(), textureView.getHeight());
            Surface surface = new Surface(texture);

            CaptureRequest.Builder builder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            builder.addTarget(surface);

            cameraDevice.createCaptureSession(Arrays.asList(surface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    if (cameraDevice == null) return;
                    cameraCaptureSession = session;
                    try {
                        builder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                        session.setRepeatingRequest(builder.build(), null, null);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                    Toast.makeText(Activity_handtalker_camera.this, "Configuración de la cámara fallida", Toast.LENGTH_SHORT).show();
                }
            }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    // Listener para el TextureView
    private final TextureView.SurfaceTextureListener surfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            Log.d("Camera", "SurfaceTexture is available, opening camera.");
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            // Configurar la transformación de la vista previa si es necesario
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        }


    };


    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Camera", "onResume is called.");
        if (textureView.isAvailable()) {
            Log.d("Camera", "TextureView is available, opening camera.");
            openCamera();
        } else {
            textureView.setSurfaceTextureListener(surfaceTextureListener);
        }
    }

    @Override
    protected void onPause() {
        // Liberar la cámara cuando la actividad está en pausa para permitir que otras aplicaciones la usen
        closeCamera();
        super.onPause();
    }

    private void closeCamera() {
        if (cameraCaptureSession != null) {
            cameraCaptureSession.close();
            cameraCaptureSession = null;
        }
        if (cameraDevice != null) {
            cameraDevice.close();
            cameraDevice = null;
        }
        // Si estás usando un CameraCaptureSession o algún otro recurso que deba ser liberado, asegúrate de hacerlo aquí
    }


    // Asegúrate de implementar los métodos para abrirInicio(), abrirTraduccion(), abrirPerfil(), abrirConfig(), y abrirInfoGuia() como antes


    private void abrirInfoGuia() {
        if (themes.equals("1")){
            // Crear el constructor del AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(Activity_handtalker_camera.this, R.style.AlertDialogCustom);

            // Configurar el mensaje y el botón del AlertDialog
            builder.setTitle("¿Cómo funciona?");
            String trad= "Traduccion mediante camara";
            builder.setMessage("La funcion \""+ trad +"\" aun no esta disponible.")
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
            String trad= "Traduccion mediante camara";
            builder.setMessage("La funcion \""+ trad +"\" aun no esta disponible.")
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