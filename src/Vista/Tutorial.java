/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controladores.Controlador1;
import Controladores.Controlador2;
import Controladores.Controlador3;
import Controladores.Singleton;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 *
 * @author JUANPABLO
 */
public class Tutorial extends AnimationTimer{

    private Scene escena;
    private GraphicsContext lapiz;
    private Image nube1;
    private Image nube2;
    private Image nube3;
    private Image nube4;
    private Image play;
    private Image fondo;
    private int secuencia = 0;
    private int secuenciaFondo = 0;
    private ArrayList<String> pulsacionTeclado = null;

    public Tutorial(Scene escena, GraphicsContext lapiz) {
        this.escena = escena;
        this.lapiz = lapiz;
        this.fondo = new Image( "Imagenes/fondoPInicio.png" );
        this.nube1 = new Image( "Imagenes/saltar.png" );
        this.nube2 = new Image( "Imagenes/izquierda.png" );
        this.nube3 = new Image( "Imagenes/derecha.png" );
        this.nube4 = new Image( "Imagenes/pasarNivel.png");
        this.play = new Image( "Imagenes/Play.png");
        pulsacionTeclado = new ArrayList<>();
        
        escena.setOnKeyPressed(
            new EventHandler<KeyEvent>()
            {
                public void handle(KeyEvent e)
                {
                    String code = e.getCode().toString();
                    if ( !pulsacionTeclado.contains(code) )
                        pulsacionTeclado.add( code );
                }
            });

        escena.setOnKeyReleased(
            new EventHandler<KeyEvent>()
            {
                public void handle(KeyEvent e)
                {
                    String code = e.getCode().toString();
                    pulsacionTeclado.remove( code );
                }
            });
    }
    
    
    @Override
    public void handle(long now) {
        lapiz.drawImage(this.fondo,secuenciaFondo, 0);
        lapiz.drawImage(nube1,0,0);
        lapiz.drawImage(nube2,224,100);
        lapiz.drawImage(nube3,0,200);
        lapiz.drawImage(nube4,-100,300);
        lapiz.drawImage(play,300,476);
        secuencia++;
        if (secuencia %3 == 0) {
            secuenciaFondo--;
        }
        
        if (pulsacionTeclado.contains("SPACE")) {
           Singleton singleton = Singleton.getSingleton();
           Stage stage = singleton.getStage();
           Controlador3 controlador = new Controlador3();
           Scene escena = controlador.getVista().getScene();
           stage.setScene(escena);
           stage.setTitle("Nivel 1");
           stop();
        }
    }
    
    
    public Scene getScene() {
       return this.escena;     
    }
    
}
