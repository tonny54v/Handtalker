package com.ingsoftware.handtalker;
import android.app.Application;

public class globalMensaje {
    private static globalMensaje instance;

    // Aquí guardaremos el valor de la variable compartida
    private String globalMensajeInicio;
    private String globalMensajeTraduccion;
    private String globalMensajeFrases;
    private String globalMensajeCamara;

    // Constructor privado para prevenir la instanciación directa.
    private globalMensaje() {
        // Inicializa tu variable aquí
        globalMensajeInicio = "1";
        globalMensajeTraduccion = "1";
        globalMensajeFrases = "1";
        globalMensajeCamara = "1";
    }


    // Método estático 'getInstance' que devuelve la instancia de la clase
    public static synchronized globalMensaje getInstance() {
        if (instance == null) {
            instance = new globalMensaje();
        }
        return instance;
    }

    // Métodos para acceder y modificar tus variables globales.
    public String getGlobalMensajeInicio() {
        return globalMensajeInicio;
    }
    public void setGlobalMensajeInicio(String globalMensajeInicio) {
        this.globalMensajeInicio = globalMensajeInicio;
    }

    public String getGlobalMensajeTraduccion() {
        return globalMensajeTraduccion;
    }
    public void setGlobalMensajeTraduccion(String globalMensajeTraduccion) {
        this.globalMensajeTraduccion = globalMensajeTraduccion;
    }

    public String getGlobalMensajeFrases() {
        return globalMensajeFrases;
    }
    public void setGlobalMensajeFrases(String globalMensajeFrases) {
        this.globalMensajeFrases = globalMensajeFrases;
    }

    public String getGlobalMensajeCamara() {
        return globalMensajeCamara;
    }
    public void setGlobalMensajeCamara(String globalMensajeCamara) {
        this.globalMensajeCamara = globalMensajeCamara;
    }
}
