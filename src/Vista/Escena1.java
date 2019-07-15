/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;
import Modelos.*;
import javafx.scene.Scene;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
/**
 *
 * @author usuario1
 */
public class Escena1 extends AnimationTimer{

    private Scene escena;
    private GraphicsContext lapiz;
    private Ninja ninja;
    private NinjaMalo ninjamalo;
    private Plataforma1 piso;
    private Plataforma2 plataforma;
    private Image fondo ;   
    private Image ninjasprite;
    private Image pisosprite;
    private int secuencia = 0;
    private int numero ;
    private boolean UpIsPress = false;
    private boolean seMueveDerecha = false;
    private boolean seMueveIzquierda = false;
    private ArrayList<Pixel> pixelesSuperioresNinja;
    private ArrayList<Pixel> pixelesInferioresNinja;
    private ArrayList<Pixel> pixelesLateralesINinja;
    private ArrayList<Pixel> pixelesLateralesDNinja;
    private ArrayList<Pixel> pixelesSuperioresPlataformas;
    private ArrayList<Pixel> pixelesInferioresPlataformas;
    private ArrayList<Pixel> pixelesLateralesIParedes;
    private ArrayList<Pixel> pixelesLateralesDParedes;
    private ArrayList<String> pulsacionTeclado = null;
    private boolean gravedad = true;
    
    public Escena1(Scene escena, GraphicsContext lapiz) {
        this.lapiz = lapiz;
        this.escena = escena;
        this.ninja = new Ninja(0, 400, 40, 60);
        this.fondo = new Image( "Imagenes/fondojuego.png" );
        this.pisosprite = new Image( "Imagenes/Plataforma2.png" );
        this.ninjasprite = new Image( "Imagenes/Ninja(0).png" );
        pixelesSuperioresNinja = new ArrayList<>();
        pixelesInferioresNinja = new ArrayList<>();
        pixelesLateralesINinja = new ArrayList<>();
        pixelesLateralesDNinja = new ArrayList<>();
        pixelesSuperioresPlataformas = new ArrayList<>();
        pixelesInferioresPlataformas = new ArrayList<>();
        pixelesLateralesIParedes = new ArrayList<>();
        pixelesLateralesDParedes = new ArrayList<>();
        pulsacionTeclado = new ArrayList<>();
                
        escena.setOnKeyPressed(new EventHandler<KeyEvent>(){
                public void handle(KeyEvent e){
                    String code = e.getCode().toString();
                    if ( !pulsacionTeclado.contains(code) )
                        pulsacionTeclado.add( code );
                }
        });
        escena.setOnKeyReleased(new EventHandler<KeyEvent>(){
                public void handle(KeyEvent e){
                    String code = e.getCode().toString();
                    pulsacionTeclado.remove( code );
                }
        });  
    }
    
    
    @Override
    public void handle(long now) {

        lapiz.clearRect(0, 0, 1024, 576);
        lapiz.drawImage(this.fondo, 0, 0);
        
        //Dibujando sprite del ninja
        lapiz.drawImage(ninjasprite,ninja.getXref(),ninja.getYref());
        //registrando los bordes del ninja
        for (int i = 0; i < ninja.getAncho(); i++) {
            Pixel pixUp = new Pixel(ninja.getXref()+i,ninja.getYref());
            pixelesSuperioresNinja.add(pixUp);
            Pixel pixDwn = new Pixel((ninja.getXref()+i),(ninja.getYref()+ninja.getAlto()));
            pixelesInferioresNinja.add(pixDwn);
        }
        for (int i = 0; i < ninja.getAlto(); i++) {
            Pixel pixDer = new Pixel(ninja.getXref(),ninja.getYref()+i);
            pixelesLateralesDNinja.add(pixDer);
            Pixel pixIzq = new Pixel((ninja.getXref()+ninja.getAncho()),(ninja.getYref()+i));
            pixelesLateralesINinja.add(pixIzq);
        }
        //creando la hitbox del ninja
        Shape sNinja = new Rectangle(ninja.getXref()+10, ninja.getYref(), ninja.getAncho()-20, ninja.getAlto());
        
        
        //creando piso y registrando los pixeles
        int x = 0;
        lapiz.drawImage(pisosprite,0, 100);
        for (int i = 0; i < 40; i++) {
            Pixel p = new Pixel(0+i,100);
            pixelesSuperioresPlataformas.add(p);
        }
        for (int i = 0; i < 26; i++) {
            if (x != 200 && x != 240) {
               lapiz.drawImage(pisosprite,x,536);
               for (int j = 0; j < 40; j++) {
                   Pixel pixSupP =  new Pixel(x+j,536);
                   pixelesSuperioresPlataformas.add(pixSupP);
                   
                   Pixel pixLatIP = new Pixel(x,536+j);
                   pixelesLateralesIParedes.add(pixLatIP);
                   
                   Pixel pixLatDP = new Pixel(x+40,536+j);
                   pixelesLateralesDParedes.add(pixLatDP);
               }
            }
            x+=40;
        }

        //Validando si el ninja esta en el piso
        for (int i = 0; i < pixelesInferioresNinja.size(); i++) {
            for (int j = 0; j < pixelesSuperioresPlataformas.size(); j++) {
                if (pixelesInferioresNinja.get(i).getxRef() == pixelesSuperioresPlataformas.get(j).getxRef()) {
                    if (pixelesInferioresNinja.get(i).getyRef()+1 == pixelesSuperioresPlataformas.get(j).getyRef()) {
                        this.gravedad = false;
                        System.out.println("1");
                    }
                }
            }
        }
        
        
        
        if (this.gravedad) {
            ninja.moverAbajo();
            ninja.moverAbajo();
        }
        
        if (pulsacionTeclado.contains("LEFT")){
            ninja.moverIzquierda();
            this.seMueveIzquierda = true;
        }if (pulsacionTeclado.contains("RIGHT")){
            ninja.moverDerecha();
            this.seMueveDerecha = true;
        }
        
        if(this.numero % 9 == 0  && this.seMueveDerecha ){
                if(this.secuencia == 9){
                  this.secuencia = 0;
                }else{
                    if (this.secuencia == 0) {
                        this.ninjasprite = new Image( "Imagenes/Ninja(0).png" ); 
                    }else if (this.secuencia == 1) {
                        this.ninjasprite = new Image( "Imagenes/Ninja(1).png" ); 
                    }else if (this.secuencia == 2) {
                        this.ninjasprite = new Image( "Imagenes/Ninja(2).png" ); 
                    }else if (this.secuencia == 3) {
                        this.ninjasprite = new Image( "Imagenes/Ninja(3).png" ); 
                    }else if (this.secuencia == 4) {
                        this.ninjasprite = new Image( "Imagenes/Ninja(4).png" ); 
                    }else if (this.secuencia == 5) {
                        this.ninjasprite = new Image( "Imagenes/Ninja(5).png" ); 
                    }else if (this.secuencia == 6) {
                        this.ninjasprite = new Image( "Imagenes/Ninja(6).png" ); 
                    }else if (this.secuencia == 7) {
                        this.ninjasprite = new Image( "Imagenes/Ninja(7).png" ); 
                    }else if (this.secuencia == 8) {
                        this.ninjasprite = new Image( "Imagenes/Ninja(8).png" ); 
                    }else if (this.secuencia == 9) {
                        this.ninjasprite = new Image( "Imagenes/Ninja(9).png" ); 
                    }
                    
                  this.secuencia++;
                  this.seMueveDerecha = false;
                }
          }
        if(this.numero % 9 == 0  && this.seMueveIzquierda ){
                if(this.secuencia == 9){
                  this.secuencia = 0;
                }else{
                    if (this.secuencia == 0) {
                        this.ninjasprite = new Image( "Imagenes/Ninja(0) - copia.png" ); 
                    }else if (this.secuencia == 1) {
                        this.ninjasprite = new Image( "Imagenes/Ninja(1) - copia.png" ); 
                    }else if (this.secuencia == 2) {
                        this.ninjasprite = new Image( "Imagenes/Ninja(2) - copia.png" ); 
                    }else if (this.secuencia == 3) {
                        this.ninjasprite = new Image( "Imagenes/Ninja(3) - copia.png" ); 
                    }else if (this.secuencia == 4) {
                        this.ninjasprite = new Image( "Imagenes/Ninja(4) - copia.png" ); 
                    }else if (this.secuencia == 5) {
                        this.ninjasprite = new Image( "Imagenes/Ninja(5) - copia.png" ); 
                    }else if (this.secuencia == 6) {
                        this.ninjasprite = new Image( "Imagenes/Ninja(6) - copia.png" ); 
                    }else if (this.secuencia == 7) {
                        this.ninjasprite = new Image( "Imagenes/Ninja(7) - copia.png" ); 
                    }else if (this.secuencia == 8) {
                        this.ninjasprite = new Image( "Imagenes/Ninja(8) - copia.png" ); 
                    }else if (this.secuencia == 9) {
                        this.ninjasprite = new Image( "Imagenes/Ninja(9) - copia.png" ); 
                    }
                    
                  this.secuencia++;
                  this.seMueveIzquierda = false;
                }
          }
        
        pixelesSuperioresNinja = new ArrayList<>();
        pixelesInferioresNinja = new ArrayList<>();
        pixelesLateralesINinja = new ArrayList<>();
        pixelesLateralesDNinja = new ArrayList<>();
        pixelesSuperioresPlataformas = new ArrayList<>();
        pixelesInferioresPlataformas = new ArrayList<>();
        pixelesLateralesIParedes = new ArrayList<>();
        pixelesLateralesDParedes = new ArrayList<>();
        this.gravedad = true;
    }
    
}
