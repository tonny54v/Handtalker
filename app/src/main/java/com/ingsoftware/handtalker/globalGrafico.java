package com.ingsoftware.handtalker;

public class globalGrafico {
    private static globalGrafico instance;

    // Aquí guardaremos el valor de la variable compartida
    private String globalTamanoG;

    // Constructor privado para prevenir la instanciación directa.
    private globalGrafico() {
        // Inicializa tu variable aquí
        globalTamanoG = "3";
    }


    // Método estático 'getInstance' que devuelve la instancia de la clase
    public static synchronized globalGrafico getInstance() {
        if (instance == null) {
            instance = new globalGrafico();
        }
        return instance;
    }

    // Métodos para acceder y modificar tu variable global.
    public String getGlobalTamanoG() {
        return globalTamanoG;
    }

    public void setGlobalTamanoG(String globalTamanoG) {
        this.globalTamanoG = globalTamanoG;
    }
}
