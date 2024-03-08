package com.ingsoftware.handtalker;
import android.app.Application;

public class globalDireccionIp {
    private static globalDireccionIp instance;

    // Aquí guardaremos el valor de la variable compartida
    private String globalIP;

    // Constructor privado para prevenir la instanciación directa.
    private globalDireccionIp() {
        // Inicializa tu variable aquí
        globalIP = "192.168.8.11";
    }


    // Método estático 'getInstance' que devuelve la instancia de la clase
    public static synchronized globalDireccionIp getInstance() {
        if (instance == null) {
            instance = new globalDireccionIp();
        }
        return instance;
    }

    // Métodos para acceder y modificar tu variable global.
    public String getGlobalDireccionIp() {
        return globalIP;
    }

    public void setGlobalDireccionIp(String globalIP) {
        this.globalIP = globalIP;
    }
}
