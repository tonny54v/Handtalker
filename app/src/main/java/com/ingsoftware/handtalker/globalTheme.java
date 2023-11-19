package com.ingsoftware.handtalker;
import android.app.Application;

public class globalTheme {
    private static globalTheme instance;

    // Aquí guardaremos el valor de la variable compartida
    private String globalTema;

    // Constructor privado para prevenir la instanciación directa.
    private globalTheme() {
        // Inicializa tu variable aquí
        globalTema = "2";
    }


    // Método estático 'getInstance' que devuelve la instancia de la clase
    public static synchronized globalTheme getInstance() {
        if (instance == null) {
            instance = new globalTheme();
        }
        return instance;
    }

    // Métodos para acceder y modificar tu variable global.
    public String getGlobalTema() {
        return globalTema;
    }

    public void setGlobalTema(String globalTema) {
        this.globalTema = globalTema;
    }
}

