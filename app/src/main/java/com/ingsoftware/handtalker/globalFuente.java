package com.ingsoftware.handtalker;

public class globalFuente {
    private static globalFuente instance;

    // Aquí guardaremos el valor de la variable compartida
    private String globalTamanoF;

    // Constructor privado para prevenir la instanciación directa.
    private globalFuente() {
        // Inicializa tu variable aquí
        globalTamanoF = "2";
    }


    // Método estático 'getInstance' que devuelve la instancia de la clase
    public static synchronized globalFuente getInstance() {
        if (instance == null) {
            instance = new globalFuente();
        }
        return instance;
    }

    // Métodos para acceder y modificar tu variable global.
    public String getGlobalTamanoF() {
        return globalTamanoF;
    }

    public void setGlobalTamanoF(String globalTamanoF) {
        this.globalTamanoF = globalTamanoF;
    }
}
