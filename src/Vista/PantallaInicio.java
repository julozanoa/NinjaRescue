/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controladores.Controlador2;
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
public class PantallaInicio extends AnimationTimer{

    private Scene escena;
    private GraphicsContext lapiz;
    private Image logo;
    private Image fondo;
    private Image play;
    private int secuencia = 0;
    private int secuenciaFondo = 0;
    private ArrayList<String> pulsacionTeclado = null;

    public PantallaInicio(Scene escena, GraphicsContext lapiz) {
        this.escena = escena;
        this.lapiz = lapiz;
        this.logo = new Image( "Imagenes/Inicio.png" );
        this.fondo = new Image( "Imagenes/fondoPInicio.png" );
        this.play = new Image( "Imagenes/Play.png" );
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
        lapiz.drawImage(logo,250,100);
        lapiz.drawImage(play,100,400);
        secuencia++;
        if (secuencia %3 == 0) {
            secuenciaFondo--;
        }
        
        if (pulsacionTeclado.contains("SPACE")) {
           Singleton singleton = Singleton.getSingleton();
           Stage stage = singleton.getStage();
           Controlador2 controlador = new Controlador2();
           Scene escena = controlador.getVista().getScene();
           stage.setScene(escena);
           stage.setTitle("Tutorial");
           stop();
        }
    }
    
    
    public Scene getScene() {
       return this.escena;     
    }
    
}
