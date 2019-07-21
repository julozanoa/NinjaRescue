/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author JUANPABLO
 */
public class Escena2 extends AnimationTimer{

    private Scene escena;
    private GraphicsContext lapiz;
    private Image ninja;
    private Image fondo;
    private int secuencia = 0;
    private int secuenciaFondo = 0;
    private ArrayList<String> pulsacionTeclado = null;

    public Escena2(Scene escena, GraphicsContext lapiz) {
        this.escena = escena;
        this.lapiz = lapiz;
        this.ninja = new Image( "Imagenes/Ninja(0).png" );
        this.fondo = new Image( "Imagenes/fondoPInicio.png" );
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
        
        secuencia++;
        if (secuencia %3 == 0) {
            secuenciaFondo--;
        }
    }
    
    public Scene getScene() {
       return this.escena;     
    }
    
}
