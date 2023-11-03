package com.ingsoftware.handtalker;
import android.app.Application;

public class globalVariable {
    private static globalVariable instance;

    // Aquí guardaremos el valor de la variable compartida
    private String globalString;

    // Constructor privado para prevenir la instanciación directa.
    private globalVariable() {
        // Inicializa tu variable aquí
        globalString = "5";
    }

    // Método estático 'getInstance' que devuelve la instancia de la clase
    public static synchronized globalVariable getInstance() {
        if (instance == null) {
            instance = new globalVariable();
        }
        return instance;
    }

    // Métodos para acceder y modificar tu variable global.
    public String getGlobalString() {
        return globalString;
    }

    public void setGlobalString(String globalString) {
        this.globalString = globalString;
    }
}
