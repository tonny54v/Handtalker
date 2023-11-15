package com.ingsoftware.handtalker;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.ingsoftware.handtalker.R;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity_handtalker_traduccion extends AppCompatActivity {

    private static final int TIEMPO_ENTRE_PULSACIONES = 2000; // 2 segundos
    private long tiempoUltimaPulsacion;
    private Toast toast;

    private ImageView home1;
    private ImageView flechas;
    private ImageView traduccion1;
    private ImageView camara1;
    private ImageView perfil1;
    private ImageView config;
    private ImageView derechaFlecha;
    private ImageView izquierdaFlecha;
    private Map<String, Integer> signLanguageMap;
    private List<String> words;
    private int currentWordIndex;
    private ImageView translationImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handtalker_traduccion);

        // Cambiar el color de la barra de estado
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.azulInicio));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.azulInicio));
        }

        // Inicializar el mapa de imágenes
        initializeSignLanguageMap();

        Button translateButton = findViewById(R.id.BotonTraduce);
        translationImage = findViewById(R.id.imageView);
        EditText userInput = findViewById(R.id.editTextUser);

        home1 = findViewById(R.id.inicio);
        flechas = findViewById(R.id.flechita2);
        traduccion1 = findViewById(R.id.traduccion);
        camara1 = findViewById(R.id.camara);
        perfil1 = findViewById(R.id.perfil);
        config = findViewById(R.id.ajuste);
        izquierdaFlecha = findViewById(R.id.flechitaIzquierda);
        derechaFlecha = findViewById(R.id.flechitaDerecha);


        //Eventos de los botones
        home1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirInicio();
            }
        });

        flechas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTraduccionPredet();
            }
        });

        camara1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrircamara();
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

        translateButton.setOnClickListener(v -> {
            String inputText = userInput.getText().toString().trim().toUpperCase();
            words = Arrays.asList(inputText.split("\\s+")); // Divide la entrada del usuario en palabras
            currentWordIndex = 0; // Comienza con la primera palabra
            showCurrentWord();
        });

        derechaFlecha.setOnClickListener(v -> {
            if (currentWordIndex < words.size() - 1) {
                currentWordIndex++;
                showCurrentWord();
            }
        });

        izquierdaFlecha.setOnClickListener(v -> {
            if (currentWordIndex > 0) {
                currentWordIndex--;
                showCurrentWord();
            }
        });


    }

    private void showCurrentWord() {
        String currentWord = words.get(currentWordIndex).toUpperCase();
        if (signLanguageMap.containsKey(currentWord)) {
            translationImage.setImageResource(signLanguageMap.get(currentWord));
        } else {
            Toast.makeText(getApplicationContext(), "No hay imagen para \"" + currentWord + "\".", Toast.LENGTH_SHORT).show();
        }
    }

    private void abrirConfig() {
        Intent intent = new Intent(Activity_handtalker_traduccion.this, Activity_configuration.class);
        startActivity(intent);
    }

    private void abrirVentanaBlanco() {
        Intent intent = new Intent(Activity_handtalker_traduccion.this, VentanaBlancoActivity.class);
        startActivity(intent);
    }

    private void abrirPerfil() {
        Intent intent = new Intent(Activity_handtalker_traduccion.this, Activity_handtalker_perfil.class);
        startActivity(intent);
    }

    private void abrirInicio() {
        Intent intent = new Intent(Activity_handtalker_traduccion.this, HandTalkerMainActivity.class);
        startActivity(intent);
    }

    private void abrircamara() {
        Intent intent = new Intent(Activity_handtalker_traduccion.this, Activity_handtalker_camera.class);
        startActivity(intent);
    }

    private void abrirTraduccionPredet() {
        Intent intent = new Intent(Activity_handtalker_traduccion.this, Activity_handtalker_frases_predet.class);
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

    private void initializeSignLanguageMap() {
        signLanguageMap = new HashMap<>();
        // Abecedario
        signLanguageMap.put("A", R.drawable.sign_a);
        signLanguageMap.put("B", R.drawable.sign_b);
        signLanguageMap.put("C", R.drawable.sign_c);
        signLanguageMap.put("D", R.drawable.sign_d);
        signLanguageMap.put("E", R.drawable.sign_e);
        signLanguageMap.put("F", R.drawable.sign_f);
        signLanguageMap.put("G", R.drawable.sign_g);
        signLanguageMap.put("H", R.drawable.sign_h);
        signLanguageMap.put("I", R.drawable.sign_i);
        signLanguageMap.put("J", R.drawable.sign_j);
        signLanguageMap.put("K", R.drawable.sign_k);
        signLanguageMap.put("L", R.drawable.sign_l);
        signLanguageMap.put("M", R.drawable.sign_m);
        signLanguageMap.put("N", R.drawable.sign_n);
        signLanguageMap.put("Ñ", R.drawable.sign_enie);
        signLanguageMap.put("O", R.drawable.sign_o);
        signLanguageMap.put("P", R.drawable.sign_p);
        signLanguageMap.put("Q", R.drawable.sign_q);
        signLanguageMap.put("R", R.drawable.sign_r);
        signLanguageMap.put("S", R.drawable.sign_s);
        signLanguageMap.put("T", R.drawable.sign_t);
        signLanguageMap.put("U", R.drawable.sign_u);
        signLanguageMap.put("V", R.drawable.sign_v);
        signLanguageMap.put("W", R.drawable.sign_w);
        signLanguageMap.put("X", R.drawable.sign_x);
        signLanguageMap.put("Y", R.drawable.sign_y);
        signLanguageMap.put("Z", R.drawable.sign_z);
        // fin abecedario


        //Frases comunes
        signLanguageMap.put("ABAJO", R.drawable.abajo);
        signLanguageMap.put("ABEJA", R.drawable.abeja);
        signLanguageMap.put("ABRIL", R.drawable.abril);
        signLanguageMap.put("ABRIR", R.drawable.abrir);
        signLanguageMap.put("ABUELA", R.drawable.abuela);
        signLanguageMap.put("ABUELO", R.drawable.abuelo);
        signLanguageMap.put("ACEITE", R.drawable.aceite);
        signLanguageMap.put("ACOMPAÑADO", R.drawable.acompanado);
        signLanguageMap.put("ADENTRO", R.drawable.adentro);
        signLanguageMap.put("ADULTO", R.drawable.adulto);
        signLanguageMap.put("AFUERA", R.drawable.afuera);
        signLanguageMap.put("AGARRAR", R.drawable.agarrar);
        signLanguageMap.put("AGOSTO", R.drawable.agosto);
        signLanguageMap.put("AGRIO", R.drawable.agrio);
        signLanguageMap.put("AGUA", R.drawable.agua);
        signLanguageMap.put("AGUILA", R.drawable.aguila);
        signLanguageMap.put("AHIJADA", R.drawable.ahijada);
        signLanguageMap.put("AHIJADO", R.drawable.ahijado);
        signLanguageMap.put("ALA", R.drawable.ala);
        signLanguageMap.put("ALACRAN", R.drawable.alacran);
        signLanguageMap.put("ALEGRE", R.drawable.alegre);
        signLanguageMap.put("ALGO", R.drawable.algo);
        signLanguageMap.put("ALIMENTO", R.drawable.alimento);
        signLanguageMap.put("ALMEJA", R.drawable.almeja);
        signLanguageMap.put("ALMOHADA", R.drawable.almohada);
        signLanguageMap.put("ALTO", R.drawable.alto);
        signLanguageMap.put("AMANECER", R.drawable.amanecer);
        signLanguageMap.put("AMANTE", R.drawable.amante);
        signLanguageMap.put("AMARILLO", R.drawable.amarillo);
        signLanguageMap.put("AMARILLA", R.drawable.amarillo);
        signLanguageMap.put("AMIGA", R.drawable.amiga);
        signLanguageMap.put("AMIGO", R.drawable.amigo);
        signLanguageMap.put("AMISTAD", R.drawable.amistad);
        signLanguageMap.put("ANARANJADO", R.drawable.anaranjado);
        signLanguageMap.put("ANCIANA", R.drawable.anciana);
        signLanguageMap.put("ANCIANO", R.drawable.anciano);
        signLanguageMap.put("ANIMAL", R.drawable.animal);
        signLanguageMap.put("ANTE", R.drawable.ante);
        signLanguageMap.put("ANTES", R.drawable.antes);
        signLanguageMap.put("ANTIGUO", R.drawable.antiguo);
        signLanguageMap.put("ANTROPOLOGIA", R.drawable.antropologia);
        signLanguageMap.put("AÑO", R.drawable.ano);
        signLanguageMap.put("AÑOS", R.drawable.ano);
        signLanguageMap.put("APAGAR", R.drawable.apagar);
        signLanguageMap.put("APARECER", R.drawable.aparecer);
        signLanguageMap.put("APETITO", R.drawable.apetito);
        signLanguageMap.put("APLASTAR", R.drawable.aplastar);
        signLanguageMap.put("ARAÑA", R.drawable.arana);
        signLanguageMap.put("ARAÑAS", R.drawable.arana);
        signLanguageMap.put("ARDILLA", R.drawable.ardilla);
        signLanguageMap.put("ARDILLAS", R.drawable.ardilla);
        signLanguageMap.put("ARRIBA", R.drawable.arriba);
        signLanguageMap.put("ARROZ", R.drawable.arroz);
        signLanguageMap.put("ARTICULO", R.drawable.articulo);
        signLanguageMap.put("ARTICULOS", R.drawable.articulo);
        signLanguageMap.put("ASAR", R.drawable.asar);
        signLanguageMap.put("ASNO", R.drawable.asno);
        signLanguageMap.put("BURRE", R.drawable.asno);
        signLanguageMap.put("ATOLE", R.drawable.atole);
        signLanguageMap.put("AVENA", R.drawable.avena);
        signLanguageMap.put("AVESTRUZ", R.drawable.avestruz);
        signLanguageMap.put("AVESTRUCES", R.drawable.avestruz);
        signLanguageMap.put("AVISPA", R.drawable.avispa);
        signLanguageMap.put("AVISPAS", R.drawable.avispa);
        signLanguageMap.put("AYER", R.drawable.ayer);
        signLanguageMap.put("AZOTEA", R.drawable.azotea);
        signLanguageMap.put("AZUCAR", R.drawable.azucar);
        signLanguageMap.put("AZUL", R.drawable.azul);
        signLanguageMap.put("BACARDI", R.drawable.bacardi);
        signLanguageMap.put("BAJO", R.drawable.bajo);
        signLanguageMap.put("BANDERA", R.drawable.bandera);
        signLanguageMap.put("BAÑO", R.drawable.bano);
        signLanguageMap.put("BARBA", R.drawable.barba);
        signLanguageMap.put("BARBILLA", R.drawable.barbilla);
        signLanguageMap.put("BARRER", R.drawable.barrer);
        signLanguageMap.put("BASURA", R.drawable.basura);
        signLanguageMap.put("BATIDORA", R.drawable.batidora);
        signLanguageMap.put("BEBE", R.drawable.bebe);
        signLanguageMap.put("BECERRO", R.drawable.becerro);
        signLanguageMap.put("BECERROS", R.drawable.becerro);
        signLanguageMap.put("BIEN", R.drawable.bien);
        signLanguageMap.put("BIGOTE", R.drawable.bigote);
        signLanguageMap.put("BIGOTES", R.drawable.bigote);
        signLanguageMap.put("BISTEC", R.drawable.bistec);
        signLanguageMap.put("BLANCO", R.drawable.blanco);
        signLanguageMap.put("BOCA", R.drawable.boca);
        signLanguageMap.put("BODA", R.drawable.boda);
        signLanguageMap.put("BONITO", R.drawable.bonito);
        signLanguageMap.put("BORREGO", R.drawable.borrego);
        signLanguageMap.put("BOTE", R.drawable.bote);
        signLanguageMap.put("BRAZO", R.drawable.brazo);
        signLanguageMap.put("BRILLANTE", R.drawable.brillante);
        signLanguageMap.put("BRONCE", R.drawable.bronce);
        signLanguageMap.put("BUENASUERTE", R.drawable.buena_suerte);
        signLanguageMap.put("BUENASNOCHE", R.drawable.buenas_noches);
        signLanguageMap.put("BUENASTARDES", R.drawable.buenas_tardes);
        signLanguageMap.put("BUENO", R.drawable.bueno);
        signLanguageMap.put("BUENOSDIAS", R.drawable.buenos_dias);
        signLanguageMap.put("BUEY", R.drawable.buey);
        signLanguageMap.put("BUFALO", R.drawable.bufalo);
        signLanguageMap.put("BUHO", R.drawable.buho);
        signLanguageMap.put("BURRO", R.drawable.burro);
        signLanguageMap.put("BUZON", R.drawable.buzon);
        signLanguageMap.put("CABALLERO", R.drawable.caballero);
        signLanguageMap.put("CABALLO", R.drawable.caballo);
        signLanguageMap.put("CABRA", R.drawable.cabra);
        signLanguageMap.put("CACEROLA", R.drawable.cacerola);
        signLanguageMap.put("CADA", R.drawable.cada);
        signLanguageMap.put("CADERA", R.drawable.cadera);
        signLanguageMap.put("CAFE", R.drawable.cafe);
        signLanguageMap.put("CAJON", R.drawable.cajon);
        signLanguageMap.put("CALAMAR", R.drawable.calamar);
        signLanguageMap.put("CALDO", R.drawable.caldo);
        signLanguageMap.put("CALIFICACION", R.drawable.calificacion);
        signLanguageMap.put("CALMA", R.drawable.calma);
        signLanguageMap.put("CAMA", R.drawable.cama);
        signLanguageMap.put("CAMARON", R.drawable.camaron);
        signLanguageMap.put("CAMELLO", R.drawable.camello);
        signLanguageMap.put("CAMINAR", R.drawable.caminar);
        signLanguageMap.put("CAMPANA", R.drawable.campana);
        signLanguageMap.put("CANDELERO", R.drawable.candelero);
        signLanguageMap.put("CANGURO", R.drawable.canguro);
        signLanguageMap.put("CARA", R.drawable.cara);
        signLanguageMap.put("CARACOL", R.drawable.caracol);
        signLanguageMap.put("CARIÑO", R.drawable.carino);
        signLanguageMap.put("CARNE", R.drawable.carne);
        signLanguageMap.put("CARO", R.drawable.caro);
        signLanguageMap.put("CASA", R.drawable.casa);
        signLanguageMap.put("CASADA", R.drawable.casada);
        signLanguageMap.put("CASADO", R.drawable.casado);
        signLanguageMap.put("CATSUP", R.drawable.catsup);
        signLanguageMap.put("CAZUELA", R.drawable.cazuela);
        signLanguageMap.put("CEBRA", R.drawable.cebra);
        signLanguageMap.put("CEJA", R.drawable.ceja);
        signLanguageMap.put("CENA", R.drawable.cena);
        //signLanguageMap.put("CEPILLODIENTES", R.drawable.cepillo_dientes);
        signLanguageMap.put("CEPILLO", R.drawable.cepillo_de_cabello);
        signLanguageMap.put("CEREAL", R.drawable.cereal);
        signLanguageMap.put("CERRAR", R.drawable.cerrar);
        signLanguageMap.put("CERTIFICADO", R.drawable.certificado);
        signLanguageMap.put("CERVEZA", R.drawable.cerveza);
        signLanguageMap.put("CHAMPU", R.drawable.champu);
        signLanguageMap.put("SHAMPOO", R.drawable.champu);
        signLanguageMap.put("CHANGO", R.drawable.chango);
        signLanguageMap.put("CHAROLA", R.drawable.charola);
        signLanguageMap.put("CHICLE", R.drawable.chicle);
        signLanguageMap.put("CHICO", R.drawable.chico);
        signLanguageMap.put("CHIMENEA", R.drawable.chimenea);
        signLanguageMap.put("CHIVO", R.drawable.chivo);
        signLanguageMap.put("CHOCOLATE", R.drawable.chocolate);
        signLanguageMap.put("CHORIZO", R.drawable.chorizo);
        signLanguageMap.put("CHUPON", R.drawable.chupon);
        signLanguageMap.put("CIERTO", R.drawable.cierto);
        signLanguageMap.put("CISNE", R.drawable.cisne);
        signLanguageMap.put("CLARO", R.drawable.claro);
        signLanguageMap.put("COBERTOR", R.drawable.cobertor);
        signLanguageMap.put("COCACOLA", R.drawable.coca_cola);
        signLanguageMap.put("COCA-COLA", R.drawable.coca_cola);
        signLanguageMap.put("COCA", R.drawable.coca_cola);
        signLanguageMap.put("COCHINO", R.drawable.cochino);
        signLanguageMap.put("COCINA", R.drawable.cocina);
        signLanguageMap.put("COCODRILO", R.drawable.cocodrilo);
        signLanguageMap.put("CODO", R.drawable.codo);
        signLanguageMap.put("COLADERA", R.drawable.coladera);
        signLanguageMap.put("COLADOR", R.drawable.colador);
        signLanguageMap.put("COLCHA", R.drawable.colcha);
        signLanguageMap.put("COLOR", R.drawable.color);
        signLanguageMap.put("COLORES", R.drawable.color);
        signLanguageMap.put("COMADRE", R.drawable.comadre);
        signLanguageMap.put("COMAL", R.drawable.comal);
        signLanguageMap.put("COMEDOR", R.drawable.comedor);
        signLanguageMap.put("COMIDA", R.drawable.comida);
        signLanguageMap.put("COMODA", R.drawable.comoda);
        signLanguageMap.put("CONDOMINIO", R.drawable.condominio);
        signLanguageMap.put("CONEJO", R.drawable.conejo);
        signLanguageMap.put("CONMIGO", R.drawable.conmigo);
        signLanguageMap.put("CONTESTAR", R.drawable.contestar);
        signLanguageMap.put("CONTIGO", R.drawable.contigo);
        signLanguageMap.put("CONTRA", R.drawable.contra);
        signLanguageMap.put("COPA", R.drawable.copa);
        signLanguageMap.put("CORDERO", R.drawable.cordero);
        signLanguageMap.put("CORREDOR", R.drawable.corredor);
        signLanguageMap.put("CORRIENTE", R.drawable.corriente);
        signLanguageMap.put("CORTINA", R.drawable.cortina);
        signLanguageMap.put("CORTO", R.drawable.corto);
        signLanguageMap.put("CUADERNO", R.drawable.cuaderno);
        signLanguageMap.put("CUARTO", R.drawable.cuarto);
        signLanguageMap.put("CUATES", R.drawable.cuates);
        signLanguageMap.put("CUCHARA", R.drawable.cuchara);
        signLanguageMap.put("CUCHILLO", R.drawable.cuchillo);
        signLanguageMap.put("CUELLO", R.drawable.cuello);
        signLanguageMap.put("CUÑADA", R.drawable.cunada);
        signLanguageMap.put("CUÑADO", R.drawable.cunado);
        signLanguageMap.put("DAMA", R.drawable.dama);
        signLanguageMap.put("DAR", R.drawable.dar);
        signLanguageMap.put("DE", R.drawable.de);
        signLanguageMap.put("DEBAJO", R.drawable.debajo);
        signLanguageMap.put("DEJAR", R.drawable.dejar);
        signLanguageMap.put("DELANTE", R.drawable.delante);
        signLanguageMap.put("DELFIN", R.drawable.delfin);
        signLanguageMap.put("DELICIOSO", R.drawable.delicioso);
        signLanguageMap.put("DEPARTAMENTO", R.drawable.departamento);
        signLanguageMap.put("DERECHA", R.drawable.derecha);
        signLanguageMap.put("DERRUMBAR", R.drawable.derrumbar);
        signLanguageMap.put("DESAPARECER", R.drawable.desaparecer);
        signLanguageMap.put("DESAYUNO", R.drawable.desayuno);
        signLanguageMap.put("DESPUES", R.drawable.despues);
        signLanguageMap.put("DESTAPADOR", R.drawable.destapador);
        signLanguageMap.put("DETENER", R.drawable.detener);
        signLanguageMap.put("DETRAS", R.drawable.detras);
        signLanguageMap.put("DIA", R.drawable.dia);
        signLanguageMap.put("DIAS", R.drawable.dia);
        signLanguageMap.put("DIBUJO", R.drawable.dibujo);
        signLanguageMap.put("DICCIONARIO", R.drawable.diccionario);
        signLanguageMap.put("DICIEMBRE", R.drawable.diciembre);
        signLanguageMap.put("DIENTE", R.drawable.diente);
        signLanguageMap.put("DIFICIL", R.drawable.dificil);
        signLanguageMap.put("DIRECCION", R.drawable.direccion);
        signLanguageMap.put("DIVORCIADA", R.drawable.divorciada);
        signLanguageMap.put("DIVORCIADO", R.drawable.divorciado);
        signLanguageMap.put("DOMINGO", R.drawable.domingo);
        signLanguageMap.put("DONA", R.drawable.dona);
        signLanguageMap.put("DULCE", R.drawable.dulce);
        signLanguageMap.put("DURO", R.drawable.duro);
        signLanguageMap.put("EDIFICIO", R.drawable.edificio);
        signLanguageMap.put("EL", R.drawable.ele);
        signLanguageMap.put("ELLA", R.drawable.ele);
        signLanguageMap.put("ELECTRICIDAD", R.drawable.electricidad);
        signLanguageMap.put("ELEFANTE", R.drawable.elefante);
        signLanguageMap.put("ELLOS", R.drawable.ellos);
        signLanguageMap.put("EMPEZAR", R.drawable.empezar);
        signLanguageMap.put("EN", R.drawable.en);
        signLanguageMap.put("ENANO", R.drawable.enano);
        signLanguageMap.put("ENCENDER", R.drawable.encender);
        signLanguageMap.put("ENCHILADAS", R.drawable.enchiladas);
        signLanguageMap.put("ENCIMA", R.drawable.encima);
        signLanguageMap.put("ENERO", R.drawable.enero);
        signLanguageMap.put("ENSALADA", R.drawable.ensalada);
        signLanguageMap.put("ENTRAR", R.drawable.entrar);
        signLanguageMap.put("ENTRE", R.drawable.entre);
        signLanguageMap.put("ESCALA", R.drawable.escala);
        signLanguageMap.put("ESCALERA", R.drawable.escalera);
        signLanguageMap.put("ESCRITORIO", R.drawable.escritorio);
        signLanguageMap.put("ESCUADRA", R.drawable.escuadra);
        signLanguageMap.put("ESCUELA", R.drawable.escuela);
        signLanguageMap.put("ESCUSADO", R.drawable.escusado);
        signLanguageMap.put("ESO", R.drawable.eso);
        signLanguageMap.put("ESPAGUETI", R.drawable.espaguetti);
        signLanguageMap.put("ESPALDA", R.drawable.espalda);
        signLanguageMap.put("ESPEJO", R.drawable.espejo);
        signLanguageMap.put("ESPOSA", R.drawable.esposa);
        signLanguageMap.put("ESPOSO", R.drawable.esposo);
        signLanguageMap.put("ESTOMAGO", R.drawable.estomago);
        signLanguageMap.put("ESTUDIANTE", R.drawable.estudiante);
        signLanguageMap.put("ESTUFA", R.drawable.estufa);
        signLanguageMap.put("EXALTACION", R.drawable.exaltacion);
        signLanguageMap.put("EXPANDIR", R.drawable.expandir);
        signLanguageMap.put("FACIL", R.drawable.facil);
        signLanguageMap.put("FALSO", R.drawable.falso);
        signLanguageMap.put("FAMILIA", R.drawable.familia);
        signLanguageMap.put("FEBRERO", R.drawable.febrero);
        signLanguageMap.put("FELICITAR", R.drawable.felicitar);
        signLanguageMap.put("FEMENIMO", R.drawable.femenino);
        signLanguageMap.put("FEO", R.drawable.feo);
        signLanguageMap.put("FIDEO", R.drawable.fideo);
        signLanguageMap.put("FLAMENCO", R.drawable.flamenco);
        signLanguageMap.put("FLORERO", R.drawable.florero);
        signLanguageMap.put("FOCA", R.drawable.foca);
        signLanguageMap.put("FOCO", R.drawable.foco);
        signLanguageMap.put("FOGATA", R.drawable.fogata);
        signLanguageMap.put("FRENTE", R.drawable.frente);
        signLanguageMap.put("FRUTERO", R.drawable.frutero);
        signLanguageMap.put("FUEGO", R.drawable.fuego);
        signLanguageMap.put("FUTURO", R.drawable.futuro);
        signLanguageMap.put("GALLETA", R.drawable.galleta);
        signLanguageMap.put("GALLO", R.drawable.gallo);
        signLanguageMap.put("GANAR", R.drawable.ganar);
        signLanguageMap.put("GARAGE", R.drawable.garage);
        signLanguageMap.put("GARGANTA", R.drawable.garganta);
        signLanguageMap.put("GATO", R.drawable.gato);
        signLanguageMap.put("GELATINA", R.drawable.gelatina);
        signLanguageMap.put("GEMELOS", R.drawable.gemelos);
        signLanguageMap.put("GENERACION", R.drawable.generacion);
        signLanguageMap.put("GIGANTE", R.drawable.gigante);
        signLanguageMap.put("GOMA", R.drawable.goma);
        signLanguageMap.put("GORILA", R.drawable.gorila);
        signLanguageMap.put("GRANDE", R.drawable.grande);
        signLanguageMap.put("GRASA", R.drawable.grasa);
        signLanguageMap.put("GRATIS", R.drawable.gratis);
        signLanguageMap.put("GRIS", R.drawable.gris);
        signLanguageMap.put("GROSERO", R.drawable.grosero);
        signLanguageMap.put("GUAJOLOTE", R.drawable.guajolote);
        signLanguageMap.put("GUAPO", R.drawable.guapo);
        signLanguageMap.put("GUSANO", R.drawable.gusano);
        signLanguageMap.put("HAMBRE", R.drawable.hambre);
        signLanguageMap.put("HAMBURGUESA", R.drawable.hamburguesa);
        signLanguageMap.put("HARINA", R.drawable.harina);
        signLanguageMap.put("HELADO", R.drawable.helado);
        signLanguageMap.put("HERENCIA", R.drawable.herencia);
        signLanguageMap.put("HERMANA", R.drawable.hermana);
        signLanguageMap.put("HERMANO", R.drawable.hermano);
        signLanguageMap.put("HIJA", R.drawable.hija);
        signLanguageMap.put("HIJASTRA", R.drawable.hijastra);
        signLanguageMap.put("HIJO", R.drawable.hijo);
        signLanguageMap.put("HIPOPOTAMO", R.drawable.hipopotamo);
        signLanguageMap.put("HOGAR", R.drawable.hogar);
        signLanguageMap.put("HOMBRE", R.drawable.hombre);
        signLanguageMap.put("HOMBRO", R.drawable.hombro);
        signLanguageMap.put("HORMIGA", R.drawable.hormiga);
        signLanguageMap.put("HORNO", R.drawable.horno);
        signLanguageMap.put("HOTDOG", R.drawable.hot_dog);
        signLanguageMap.put("HOY", R.drawable.hoy);
        signLanguageMap.put("HUERFANA", R.drawable.huerfana);
        signLanguageMap.put("HUERFANO", R.drawable.huerfano);
        signLanguageMap.put("HUESO", R.drawable.hueso);
        signLanguageMap.put("HUEVO", R.drawable.huevo);
        signLanguageMap.put("IGUANA", R.drawable.iguana);
        signLanguageMap.put("IMPOSIBLE", R.drawable.imposible);
        signLanguageMap.put("INVIERNO", R.drawable.invierno);
        signLanguageMap.put("IZQUIERDA", R.drawable.izquierda);
        signLanguageMap.put("JABALI", R.drawable.jabali);
        signLanguageMap.put("JABON", R.drawable.jabon);
        signLanguageMap.put("JAMON", R.drawable.jamon);
        signLanguageMap.put("JARDIN", R.drawable.jardin);
        signLanguageMap.put("JERGA", R.drawable.jerga);
        signLanguageMap.put("JIRAFA", R.drawable.jirafa);
        signLanguageMap.put("JOVEN", R.drawable.joven);
        signLanguageMap.put("JUEVES", R.drawable.jueves);
        signLanguageMap.put("JULIO", R.drawable.julio);
        signLanguageMap.put("JUNIO", R.drawable.junio);
        signLanguageMap.put("JUVENIL", R.drawable.juvenil);
        signLanguageMap.put("JUVENTUD", R.drawable.juventud);
        signLanguageMap.put("LABIOS", R.drawable.labios);
        signLanguageMap.put("LAMPARA", R.drawable.lampara);
        signLanguageMap.put("LAPIZ", R.drawable.lapiz);
        signLanguageMap.put("LARGO", R.drawable.largo);
        signLanguageMap.put("LAVADO", R.drawable.lavado);
        signLanguageMap.put("LAVADORA", R.drawable.lavadora);
        signLanguageMap.put("LECCION", R.drawable.leccion);
        signLanguageMap.put("LECHE", R.drawable.leche);
        signLanguageMap.put("LEGUMBRES", R.drawable.legumbres);
        signLanguageMap.put("LENGUA", R.drawable.lengua);
        signLanguageMap.put("LEON", R.drawable.leon);
        signLanguageMap.put("LICOR", R.drawable.licor);
        signLanguageMap.put("LICUADORA", R.drawable.licuadora);
        signLanguageMap.put("LIEBRE", R.drawable.liebre);
        signLanguageMap.put("LIMONADA", R.drawable.limonada);
        signLanguageMap.put("LIMPIO", R.drawable.limpio);
        signLanguageMap.put("LLAVE", R.drawable.llave);
        signLanguageMap.put("LLENO", R.drawable.lleno);
        signLanguageMap.put("LLORAR", R.drawable.llorar);
        signLanguageMap.put("LOBO", R.drawable.lobo);
        signLanguageMap.put("LUNES", R.drawable.lunes);
        signLanguageMap.put("LUZ", R.drawable.luz);
        signLanguageMap.put("MADRASTRA", R.drawable.madrastra);
        signLanguageMap.put("MADRE", R.drawable.madre);
        signLanguageMap.put("MADRINA", R.drawable.madrina);
        signLanguageMap.put("MAL", R.drawable.mal);
        signLanguageMap.put("MALASUERTE", R.drawable.mala_suerte);
        signLanguageMap.put("MALO", R.drawable.malo);
        signLanguageMap.put("MAMA", R.drawable.mama);
        signLanguageMap.put("MAMILA", R.drawable.mamila);
        signLanguageMap.put("MANO", R.drawable.mano);
        signLanguageMap.put("MANTEL", R.drawable.mantel);
        signLanguageMap.put("MANTEQUILLA", R.drawable.mantequilla);
        signLanguageMap.put("MAÑANA", R.drawable.manana);
        signLanguageMap.put("MAPA", R.drawable.mapa);
        signLanguageMap.put("MAQUINADECOSER", R.drawable.maquina_de_coser);
        signLanguageMap.put("MAQUINADEESCRIBIR", R.drawable.maquina_de_escribir);
        signLanguageMap.put("MARIPOSA", R.drawable.mariposa);
        signLanguageMap.put("MARTES", R.drawable.martes);
        signLanguageMap.put("MARZO", R.drawable.marzo);
        signLanguageMap.put("MAS", R.drawable.mas);
        signLanguageMap.put("MASCULINO", R.drawable.masculino);
        signLanguageMap.put("MATRIMONIO", R.drawable.matrimonio);
        signLanguageMap.put("MAYO", R.drawable.mayo);
        signLanguageMap.put("MAYONESA", R.drawable.mayonesa);
        signLanguageMap.put("MEDIODIA", R.drawable.mediodia);
        signLanguageMap.put("MEJILLA", R.drawable.mejilla);
        signLanguageMap.put("MEJOR", R.drawable.mejor);
        signLanguageMap.put("MENOS", R.drawable.menos);
        signLanguageMap.put("MENTIRA", R.drawable.mentira);
        signLanguageMap.put("MERMELADA", R.drawable.mermelada);
        signLanguageMap.put("MES", R.drawable.mes);
        signLanguageMap.put("MESA", R.drawable.mesa);
        signLanguageMap.put("MICROSCOPIO", R.drawable.microscopio);
        signLanguageMap.put("MIEL", R.drawable.miel);
        signLanguageMap.put("MIERCOLES", R.drawable.miercoles);
        signLanguageMap.put("MIO", R.drawable.mio);
        signLanguageMap.put("MISMO", R.drawable.mismo);
        signLanguageMap.put("MOJADO", R.drawable.mojado);
        signLanguageMap.put("MOLDE", R.drawable.molde);
        signLanguageMap.put("MOLE", R.drawable.mole);
        signLanguageMap.put("MONO", R.drawable.mono);
        signLanguageMap.put("MORADO", R.drawable.morado);
        signLanguageMap.put("MOSCA", R.drawable.mosca);
        signLanguageMap.put("MOSQUITO", R.drawable.mosquito);
        signLanguageMap.put("MUCHO", R.drawable.mucho);
        signLanguageMap.put("MULA", R.drawable.mula);
        signLanguageMap.put("MUÑECO", R.drawable.muneco);
        signLanguageMap.put("MURCIELAGO", R.drawable.murcielago);
        signLanguageMap.put("NARIZ", R.drawable.nariz);
        signLanguageMap.put("NEGRO", R.drawable.negro);
        signLanguageMap.put("NI", R.drawable.ni);
        signLanguageMap.put("NO", R.drawable.no);
        signLanguageMap.put("NOCHE", R.drawable.noche);
        signLanguageMap.put("NOVIEMBRE", R.drawable.noviembre);
        signLanguageMap.put("NUESTRO", R.drawable.nuestro);
        signLanguageMap.put("NUNCA", R.drawable.nunca);
        signLanguageMap.put("OCTUBRE", R.drawable.octubre);
        signLanguageMap.put("OJO", R.drawable.ojo);
        signLanguageMap.put("OLLAEXPRES", R.drawable.olla_expres);
        signLanguageMap.put("OLVIDAR", R.drawable.olvidar);
        signLanguageMap.put("OREJA", R.drawable.oreja);
        signLanguageMap.put("ORO", R.drawable.oro);
        signLanguageMap.put("OSCURIDAD", R.drawable.oscuridad);
        signLanguageMap.put("OSCURO", R.drawable.oscuro);
        signLanguageMap.put("OSO", R.drawable.oso);
        signLanguageMap.put("OSTION", R.drawable.ostion);
        signLanguageMap.put("OTOÑO", R.drawable.otono);
        signLanguageMap.put("PAJARO", R.drawable.pajaro);
        signLanguageMap.put("PALETA", R.drawable.paleta);
        signLanguageMap.put("PALOMA", R.drawable.paloma);
        signLanguageMap.put("PAN", R.drawable.pan);
        signLanguageMap.put("PANTERA", R.drawable.pantera);
        signLanguageMap.put("PAPEL", R.drawable.papel);
        signLanguageMap.put("PARA", R.drawable.para);
        signLanguageMap.put("PARED", R.drawable.pared);
        signLanguageMap.put("PARQUE", R.drawable.parque);
        signLanguageMap.put("PARRAFO", R.drawable.parrafo);
        signLanguageMap.put("PARRILLA", R.drawable.parilla);
        signLanguageMap.put("PASADO", R.drawable.pasado);
        signLanguageMap.put("PASTADEDIENTES", R.drawable.pasta_de_dientes);
        signLanguageMap.put("PASTEL", R.drawable.pastel);
        signLanguageMap.put("PATIO", R.drawable.patio);
        signLanguageMap.put("PATO", R.drawable.pato);
        signLanguageMap.put("PAVOREAL", R.drawable.pavo_real);
        signLanguageMap.put("PAY", R.drawable.pay);
        signLanguageMap.put("PECHO", R.drawable.pecho);
        signLanguageMap.put("PEDIR", R.drawable.pedir);
        signLanguageMap.put("PELICANO", R.drawable.pelicano);
        signLanguageMap.put("PELO", R.drawable.pelo);
        signLanguageMap.put("PEOR", R.drawable.peor);
        signLanguageMap.put("PERDER", R.drawable.perder);
        signLanguageMap.put("PERICO", R.drawable.perico);
        signLanguageMap.put("PERRO", R.drawable.perro);
        signLanguageMap.put("PERSPECTIVA", R.drawable.perspectiva);
        signLanguageMap.put("PESTAÑA", R.drawable.pestana);
        signLanguageMap.put("PEZ", R.drawable.pez);
        signLanguageMap.put("PICOSO", R.drawable.picoso);
        signLanguageMap.put("PILONCILLO", R.drawable.piloncillo);
        signLanguageMap.put("PIMIENTA", R.drawable.pimienta);
        signLanguageMap.put("PINGUINO", R.drawable.pinguino);
        signLanguageMap.put("PISO", R.drawable.piso);
        signLanguageMap.put("PIZARRON", R.drawable.pizarron);
        signLanguageMap.put("PIZZA", R.drawable.pizza);
        signLanguageMap.put("PLATA", R.drawable.plata);
        signLanguageMap.put("PLATO", R.drawable.plato);
        signLanguageMap.put("PLUMA", R.drawable.pluma);
        signLanguageMap.put("POBRE", R.drawable.pobre);
        signLanguageMap.put("POCO", R.drawable.poco);
        signLanguageMap.put("POLITECNICO", R.drawable.politecnico);
        signLanguageMap.put("POLLO", R.drawable.pollo);
        signLanguageMap.put("POLVO", R.drawable.polvo);
        signLanguageMap.put("PONER", R.drawable.poner);
        signLanguageMap.put("POR", R.drawable.por);
        signLanguageMap.put("POSIBLE", R.drawable.posible);
        signLanguageMap.put("POSTRE", R.drawable.postre);
        signLanguageMap.put("PREGUNTAR", R.drawable.preguntar);
        signLanguageMap.put("PREMIO", R.drawable.premio);
        signLanguageMap.put("PRENDER", R.drawable.prender);
        signLanguageMap.put("PREPARATORIA", R.drawable.preparatoria);
        signLanguageMap.put("PRESENTE", R.drawable.presente);
        signLanguageMap.put("PRIMARIA", R.drawable.primaria);
        signLanguageMap.put("PRIMAVERA", R.drawable.primavera);
        signLanguageMap.put("PRIMERO", R.drawable.primero);
        signLanguageMap.put("PROPIO", R.drawable.propio);
        signLanguageMap.put("PRUEBA", R.drawable.prueba);
        signLanguageMap.put("PUERCOESPIN", R.drawable.puerco_espon);
        signLanguageMap.put("PUERCO", R.drawable.puerco);
        signLanguageMap.put("PUERTA", R.drawable.puerta);
        signLanguageMap.put("PULGAR", R.drawable.pulgar);
        signLanguageMap.put("PULPO", R.drawable.pulpo);
        signLanguageMap.put("PULQUE", R.drawable.pulque);
        signLanguageMap.put("QUESADILLA", R.drawable.quesadilla);
        signLanguageMap.put("QUESO", R.drawable.queso);
        signLanguageMap.put("QUIMICA", R.drawable.quimica);
        signLanguageMap.put("QUITAR", R.drawable.quitar);
        signLanguageMap.put("RANA", R.drawable.rana);
        signLanguageMap.put("RAPIDO", R.drawable.rapido);
        signLanguageMap.put("RASURAR", R.drawable.rasurar);
        signLanguageMap.put("RATON", R.drawable.raton);
        signLanguageMap.put("RECORDAR", R.drawable.recordar);
        signLanguageMap.put("REFRESCO", R.drawable.refresco);
        signLanguageMap.put("REFRIGERADOR", R.drawable.refrigerador);
        signLanguageMap.put("REGADERA", R.drawable.regadera);
        signLanguageMap.put("REGAÑAR", R.drawable.reganar);
        signLanguageMap.put("REIR", R.drawable.reir);
        signLanguageMap.put("REJA", R.drawable.reja);
        signLanguageMap.put("RELOJ", R.drawable.reloj);
        signLanguageMap.put("RESPONDER", R.drawable.responder);
        signLanguageMap.put("RICO", R.drawable.rico);
        signLanguageMap.put("RINOCERONTE", R.drawable.rinoceronte);
        signLanguageMap.put("ROJO", R.drawable.rojo);
        signLanguageMap.put("ROPERO", R.drawable.ropero);
        signLanguageMap.put("ROSA", R.drawable.rosa);
        signLanguageMap.put("SABADO", R.drawable.sabado);
        signLanguageMap.put("SABANA", R.drawable.sabana);
        signLanguageMap.put("SABROSO", R.drawable.sabroso);
        signLanguageMap.put("SACAPUNTAS", R.drawable.sacapuntas);
        signLanguageMap.put("SAL", R.drawable.sal);
        signLanguageMap.put("SALA", R.drawable.sala);
        signLanguageMap.put("SALCHICHA", R.drawable.salchicha);
        signLanguageMap.put("SALERO", R.drawable.salero);
        signLanguageMap.put("SALIR", R.drawable.salir);
        signLanguageMap.put("SALSA", R.drawable.salsa);
        signLanguageMap.put("SAPO", R.drawable.sapo);
        signLanguageMap.put("SARTEN", R.drawable.sarten);
        signLanguageMap.put("SATISFECHO", R.drawable.satisfecho);
        signLanguageMap.put("SECADORA", R.drawable.secadora);
        signLanguageMap.put("SECO", R.drawable.seco);
        signLanguageMap.put("SECUNDARIA", R.drawable.secundaria);
        signLanguageMap.put("SEMANA", R.drawable.semana);
        signLanguageMap.put("SEPTIEMBRE", R.drawable.septiembre);
        signLanguageMap.put("SERVILLETA", R.drawable.servilleta);
        signLanguageMap.put("SI", R.drawable.si);
        signLanguageMap.put("SIEMPRE", R.drawable.siempre);
        signLanguageMap.put("SILLA", R.drawable.silla);
        signLanguageMap.put("SILLON", R.drawable.sillon);
        signLanguageMap.put("SIN", R.drawable.sin);
        signLanguageMap.put("SOFA", R.drawable.sofa);
        signLanguageMap.put("SOLO", R.drawable.solo);
        signLanguageMap.put("SOPA", R.drawable.sopa);
        signLanguageMap.put("SOPE", R.drawable.sope);
        signLanguageMap.put("SOTANO", R.drawable.sotano);
        signLanguageMap.put("SUAVE", R.drawable.suave);
        signLanguageMap.put("SUCIO", R.drawable.sucio);
        signLanguageMap.put("SUYO", R.drawable.suyo);
        signLanguageMap.put("TACO", R.drawable.taco);
        signLanguageMap.put("TAMAL", R.drawable.tamal);
        signLanguageMap.put("TAMBIEN", R.drawable.tambien);
        signLanguageMap.put("TAPADERA", R.drawable.tapadera);
        signLanguageMap.put("TAPETE", R.drawable.tapete);
        signLanguageMap.put("TAPIZ", R.drawable.tapiz);
        signLanguageMap.put("TARDE", R.drawable.tarde);
        signLanguageMap.put("TAZA", R.drawable.taza);
        signLanguageMap.put("TE", R.drawable.te);
        signLanguageMap.put("TEMPRANO", R.drawable.temprano);
        signLanguageMap.put("TENDEDERO", R.drawable.tendedero);
        signLanguageMap.put("TENEDOR", R.drawable.tenedor);
        signLanguageMap.put("TERMINAR", R.drawable.terminar);
        signLanguageMap.put("TIBURON", R.drawable.tiburon);
        signLanguageMap.put("TIGRE", R.drawable.tigre);
        signLanguageMap.put("TIJERAS", R.drawable.tijeras);
        signLanguageMap.put("TIMBRE", R.drawable.timbre);
        signLanguageMap.put("TINA", R.drawable.tina);
        signLanguageMap.put("TOALLA", R.drawable.toalla);
        signLanguageMap.put("TOCADOR", R.drawable.tocador);
        signLanguageMap.put("TODAVIA", R.drawable.todavia);
        signLanguageMap.put("TODO", R.drawable.todo);
        signLanguageMap.put("TORO", R.drawable.toro);
        signLanguageMap.put("TORTA", R.drawable.torta);
        signLanguageMap.put("TORTILLA", R.drawable.tortilla);
        signLanguageMap.put("TORTURA", R.drawable.tortura);
        signLanguageMap.put("TOSTADOR", R.drawable.tostador);
        signLanguageMap.put("TRAPEADOR", R.drawable.trapeador);
        signLanguageMap.put("TRAPO", R.drawable.trapo);
        signLanguageMap.put("TRASTES", R.drawable.trastes);
        signLanguageMap.put("TRISTE", R.drawable.triste);
        signLanguageMap.put("TU", R.drawable.tu);
        signLanguageMap.put("TUBO", R.drawable.tubo);
        signLanguageMap.put("TUYO", R.drawable.tuyo);
        signLanguageMap.put("UÑA", R.drawable.unia);
        signLanguageMap.put("USTED", R.drawable.usted);
        signLanguageMap.put("VACA", R.drawable.vaca);
        signLanguageMap.put("VACIO", R.drawable.vacio);
        signLanguageMap.put("VAMPIRO", R.drawable.vampiro);
        signLanguageMap.put("VASO", R.drawable.vaso);
        signLanguageMap.put("VELA", R.drawable.vela);
        signLanguageMap.put("VENADO", R.drawable.venado);
        signLanguageMap.put("VENCIDAD", R.drawable.vencidad);
        signLanguageMap.put("VENTANA", R.drawable.ventana);
        signLanguageMap.put("VERANO", R.drawable.verano);
        signLanguageMap.put("VERDAD", R.drawable.verdad);
        signLanguageMap.put("VERDE", R.drawable.verde);
        signLanguageMap.put("VIBORA", R.drawable.vibora);
        signLanguageMap.put("VIDRIO", R.drawable.vidrio);
        signLanguageMap.put("VIERNES", R.drawable.viernes);
        signLanguageMap.put("VINAGRE", R.drawable.vinagre);
        signLanguageMap.put("VINO", R.drawable.vino);
        signLanguageMap.put("VITRINA", R.drawable.vitrina);
        signLanguageMap.put("WHISKY", R.drawable.whisky);
        signLanguageMap.put("YO", R.drawable.yo);
        signLanguageMap.put("YOGUR", R.drawable.yogur);
        signLanguageMap.put("ZORILLO", R.drawable.zorillo);
        signLanguageMap.put("ZORRO", R.drawable.zorro);


    }
}