package com.ingsoftware.handtalker;

public class globalFoto {
    private static globalFoto instance;

    // Aquí guardaremos el valor de la variable compartida
    private String globalPhoto;

    // Constructor privado para prevenir la instanciación directa.
    private globalFoto() {
        // Inicializa tu variable aquí
        globalPhoto = "1";
    }


    // Método estático 'getInstance' que devuelve la instancia de la clase
    public static synchronized globalFoto getInstance() {
        if (instance == null) {
            instance = new globalFoto();
        }
        return instance;
    }

    // Métodos para acceder y modificar tu variable global.
    public String getGlobalFoto() {
        return globalPhoto;
    }

    public void setGlobalFoto(String globalPhoto) {
        this.globalPhoto = globalPhoto;
    }
}
