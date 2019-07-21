/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controladores.Controlador2;
import Controladores.Singleton;
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
import javafx.stage.Stage;
/**
 *
 * @author usuario1
 */
public class Escena3 extends AnimationTimer{

    private Scene escena;
    private GraphicsContext lapiz;
    private Image fondo;
    private Image spriteNinja;
    private Image pisosprite;
    private Image obstaculo1;
    private Image obstaculo2; 
    private Image fuego;
    private Image llave;
    private Image puerta; 
    private Ninja ninja;
    private ArrayList<String> pulsacionTeclado = null;
    private ArrayList<Shape> superficies;
    private ArrayList<Shape> techos;
    private ArrayList<Shape> lateralesIzq;
    private ArrayList<Shape> lateralesDer;
    private ArrayList<Shape> obstaculos;
    private boolean UpIsPress = false;
    private boolean seMueveDerecha = false;
    private boolean seMueveIzquierda = false;
    private boolean hayllave = true;
    private boolean tienellave = false;
    private boolean gravedad;
    private boolean saltando;
    private boolean chocandoI;
    private boolean chocandoD;
    private boolean chocandoTecho;
    private int secuencia = 0;
    private int secuenciaF = 0;
    private int numero ;
    private int contadorSalto = 0;

    public Escena3(Scene escena, GraphicsContext lapiz) {
        this.escena = escena;
        this.lapiz = lapiz;
        this.fondo = new Image( "Imagenes/fondojuego2.png" );
        this.spriteNinja = new Image( "Imagenes/Ninja(0).png" );
        this.pisosprite = new Image( "Imagenes/Plataforma1.png" );
        this.obstaculo1 = new Image( "Imagenes/Obstaculo1.png" );
        this.obstaculo2 = new Image( "Imagenes/Obstaculo3.png" );
        this.fuego = new Image( "Imagenes/fuego0.png" );
        this.llave = new Image( "Imagenes/goldenkey.png");
        this.puerta = new Image( "Imagenes/Door.png" );
        this.ninja = new Ninja(0,472,40,60);
        pulsacionTeclado = new ArrayList<>();
        superficies = new ArrayList<>();
        techos = new ArrayList<>();
        lateralesIzq = new ArrayList<>();
        lateralesDer = new ArrayList<>();
        obstaculos = new ArrayList<>();
        
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
        lapiz.drawImage(fondo,0,0);
        //ninja
        lapiz.drawImage(spriteNinja,ninja.getXref(),ninja.getYref());
        Shape sNinja = new Rectangle(ninja.getXref()+10, ninja.getYref(), ninja.getAncho()-20, ninja.getAlto());
        Shape sNinjaPiso = new Rectangle(ninja.getXref()+10,ninja.getYref()+58,20,4);
        Shape sNinjaLateralI = new Rectangle(ninja.getXref()-1,ninja.getYref(),40,60);
        Shape sNinjaLateralD = new Rectangle(ninja.getXref()+1,ninja.getYref(),40,60);
        Shape sNinjaTecho = new Rectangle(ninja.getXref(),ninja.getYref()-1,40,3);
        
        //laterales mapa
        Shape paredI = new Rectangle(-39,0,40,576);
        Shape sParedI = SVGPath.intersect(sNinjaLateralI, paredI);
        lateralesIzq.add(sParedI);
        Shape paredD = new Rectangle(1024,0,40,576);
        Shape sParedD = SVGPath.intersect(sNinjaLateralI, paredD);
        lateralesDer.add(sParedD);
        
        int x = 0;
        int y1 = 356;
        for (int i = 0; i < 25; i++) {
            if (i == 0) {
                lapiz.drawImage(pisosprite,x,536);
                Shape p = new Rectangle(x,535,40,4);
                Shape sP = SVGPath.intersect(sNinjaPiso, p);
                superficies.add(sP);
                
                lapiz.drawImage(pisosprite,x,356);
                Shape p1 = new Rectangle(x,355,40,4);
                Shape sP1 = SVGPath.intersect(sNinjaPiso, p1);
                superficies.add(sP1);
                Shape pt = new Rectangle(x,393,40,3);
                Shape sPt = SVGPath.intersect(sNinjaTecho, pt);
                techos.add(sPt);
                
                lapiz.drawImage(pisosprite,x,186);
                Shape p2 = new Rectangle(x,345,40,4);
                Shape sP2 = SVGPath.intersect(sNinjaPiso, p1);
                superficies.add(sP2);
                Shape pt2 = new Rectangle(x,223,40,4);
                Shape sPt2 = SVGPath.intersect(sNinjaTecho, pt2);
                techos.add(sPt2);
            }
            if (i == 1) {
                lapiz.drawImage(pisosprite,x,536);
                Shape p = new Rectangle(x,535,40,4);
                Shape sP = SVGPath.intersect(sNinjaPiso, p);
                superficies.add(sP);
                
                lapiz.drawImage(pisosprite,x,356);
                Shape p1 = new Rectangle(x,355,40,4);
                Shape sP1 = SVGPath.intersect(sNinjaPiso, p1);
                superficies.add(sP1);
                Shape pt = new Rectangle(x,393,40,3);
                Shape sPt = SVGPath.intersect(sNinjaTecho, pt);
                techos.add(sPt);
                Shape pD = new Rectangle(x+38,359,4,37);
                Shape sD = SVGPath.intersect(sNinjaLateralI, pD);
                lateralesIzq.add(sD);
                
                lapiz.drawImage(pisosprite,x,186);
                Shape p2 = new Rectangle(x,185,40,4);
                Shape sP2 = SVGPath.intersect(sNinjaPiso, p2);
                superficies.add(sP2);
                Shape pt2 = new Rectangle(x,223,40,4);
                Shape sPt2 = SVGPath.intersect(sNinjaTecho, pt2);
                techos.add(sPt2);
                Shape pD1 = new Rectangle(x,189,40,3);
                Shape sD1 = SVGPath.intersect(sNinjaLateralI, pD1);
                lateralesIzq.add(sD1);
            }
            if (i == 2) {
                lapiz.drawImage(pisosprite,x,536);
                Shape p = new Rectangle(x,535,40,4);
                Shape sP = SVGPath.intersect(sNinjaPiso, p);
                superficies.add(sP);
            }
            if (i == 3) {
                lapiz.drawImage(pisosprite,x,536);
                
                lapiz.drawImage(obstaculo1,x,276);
                Shape p0 = new Rectangle(x,275,40,4);
                Shape sP0 = SVGPath.intersect(sNinjaPiso, p0);
                superficies.add(sP0);
                Shape pD10 = new Rectangle(x+10,279,4,40);
                Shape sD10 = SVGPath.intersect(sNinjaLateralI, pD10);
                lateralesDer.add(sD10);
                Shape pt2 = new Rectangle(x,313,40,4);
                Shape sPt2 = SVGPath.intersect(sNinjaTecho, pt2);
                techos.add(sPt2);
                
                lapiz.drawImage(pisosprite,x,496);
                Shape pD1 = new Rectangle(x,499,4,40);
                Shape sD1 = SVGPath.intersect(sNinjaLateralD, pD1);
                lateralesDer.add(sD1);
                Shape pI1 = new Rectangle(x+38,499,3,37);
                Shape sI1 = SVGPath.intersect(sNinjaLateralI, pI1);
                lateralesIzq.add(sI1);
                lapiz.drawImage(pisosprite,x,456);
                Shape p = new Rectangle(x,455,40,4);
                Shape sP = SVGPath.intersect(sNinjaPiso, p);
                superficies.add(sP);
                Shape pD2 = new Rectangle(x,459,4,40);
                Shape sD2 = SVGPath.intersect(sNinjaLateralD, pD2);
                lateralesDer.add(sD2);
                Shape pI2 = new Rectangle(x+38,459,3,37);
                Shape sI2 = SVGPath.intersect(sNinjaLateralI, pI2);
                lateralesIzq.add(sI2);
            }
            if (i == 4) {
                for (int j = 0; j < 7; j++) {
                    if (j==0) {
                        Shape pt2 = new Rectangle(x,y1+38,40,3);
                        Shape sPt2 = SVGPath.intersect(sNinjaTecho, pt2);
                        techos.add(sPt2);
                    }
                    if (j==6) {
                        Shape p = new Rectangle(x,y1-1,40,4);
                        Shape sP = SVGPath.intersect(sNinjaPiso, p);
                        superficies.add(sP);
                    }
                    lapiz.drawImage(pisosprite,x,y1);
                    Shape pD2 = new Rectangle(x,y1+3,4,37);
                    Shape sD2 = SVGPath.intersect(sNinjaLateralD, pD2);
                    lateralesDer.add(sD2);
                    Shape pI2 = new Rectangle(x+38,y1+3,3,37);
                    Shape sI2 = SVGPath.intersect(sNinjaLateralI, pI2);
                    lateralesIzq.add(sI2);
                    y1-=40;
                }
            }
            if (i == 5 || i == 6) {
                lapiz.drawImage(pisosprite,x,356);
                Shape p = new Rectangle(x,355,40,4);
                Shape sP = SVGPath.intersect(sNinjaPiso, p);
                superficies.add(sP);
                
                lapiz.drawImage(pisosprite,x,156);
                Shape p1 = new Rectangle(x,155,40,4);
                Shape sP1 = SVGPath.intersect(sNinjaPiso, p1);
                superficies.add(sP1);
            }
            if (i >= 4 && i <= 25) {
                lapiz.drawImage(fuego,x+10,536);
                Shape f = new Rectangle(x+10,536,40,40);
                Shape sF = SVGPath.intersect(sNinja, f);
                obstaculos.add(sF);
                
            }
            if (i >= 9 && i <= 20) {
                if (i == 9) {
                    Shape pD2 = new Rectangle(x,459,4,37);
                    Shape sD2 = SVGPath.intersect(sNinjaLateralD, pD2);
                    lateralesDer.add(sD2);
                }
                if (i == 20) {
                    Shape pI2 = new Rectangle(x+38,459,3,37);
                    Shape sI2 = SVGPath.intersect(sNinjaLateralI, pI2);
                    lateralesIzq.add(sI2);
                }
                lapiz.drawImage(pisosprite,x,456);
                Shape p1 = new Rectangle(x,455,40,4);
                Shape sP1 = SVGPath.intersect(sNinjaPiso, p1);
                superficies.add(sP1);
            }
            x+=40;
        }
        
        //Validando si el ninja esta en el piso
        for (int i = 0; i < superficies.size(); i++) {
            if ((superficies.get(i).getBoundsInLocal().getWidth()) != -1) {
                gravedad = false;
            }
        }
        
        //Validando si esta chocando por izquierda o derecha
        for (int i = 0; i < lateralesIzq.size(); i++) {
            if ((lateralesIzq.get(i).getBoundsInLocal().getWidth()) != -1) {
                chocandoI = true;
            }
        }
        for (int i = 0; i < lateralesDer.size(); i++) {
            if ((lateralesDer.get(i).getBoundsInLocal().getWidth()) != -1) {
                chocandoD = true;
            }
        }
        for (int i = 0; i < techos.size(); i++) {
            if ((techos.get(i).getBoundsInLocal().getWidth()) != -1) {
                chocandoTecho = true;
            }
        }
        if (pulsacionTeclado.contains("UP")&&!gravedad) {
            contadorSalto = 20;
            saltando = true;
        }

        if (saltando && contadorSalto <= 20) {
            if (!chocandoTecho) {
                ninja.moverArriba();
                ninja.moverArriba();
                ninja.moverArriba();
                ninja.moverArriba();
                ninja.moverArriba();
                ninja.moverArriba();
            }
                contadorSalto--;
                if (contadorSalto == 0) {
            saltando = false;
        }
            }
        if (gravedad && !saltando) {
            ninja.moverAbajo();
            ninja.moverAbajo();
            ninja.moverAbajo();
            ninja.moverAbajo();
            ninja.moverAbajo();
        }
        if (pulsacionTeclado.contains("LEFT") && !chocandoI){
            ninja.moverIzquierda();
            ninja.moverIzquierda();
            ninja.moverIzquierda();
            ninja.moverIzquierda();
            this.seMueveIzquierda = true;
        }if (pulsacionTeclado.contains("RIGHT") && !chocandoD){
            ninja.moverDerecha();
            ninja.moverDerecha();
            ninja.moverDerecha();
            ninja.moverDerecha();
            this.seMueveDerecha = true;
        }

        //Validando si se ha chocado con algun obstaculo
        for (int i = 0; i < obstaculos.size(); i++) {
            if ((obstaculos.get(i).getBoundsInLocal().getWidth()) != -1) {
                ninja.setXref(0);
                ninja.setYref(472);
                
            }
        }
        
        if(this.numero % 9 == 0  && this.seMueveDerecha ){
                if(this.secuencia == 9){
                  this.secuencia = 0;
                }else{
                    if (this.secuencia == 0) {
                        this.spriteNinja = new Image( "Imagenes/Ninja(0).png" ); 
                    }else if (this.secuencia == 1) {
                        this.spriteNinja= new Image( "Imagenes/Ninja(1).png" ); 
                    }else if (this.secuencia == 2) {
                        this.spriteNinja = new Image( "Imagenes/Ninja(2).png" ); 
                    }else if (this.secuencia == 3) {
                        this.spriteNinja= new Image( "Imagenes/Ninja(3).png" ); 
                    }else if (this.secuencia == 4) {
                        this.spriteNinja = new Image( "Imagenes/Ninja(4).png" ); 
                    }else if (this.secuencia == 5) {
                        this.spriteNinja = new Image( "Imagenes/Ninja(5).png" ); 
                    }else if (this.secuencia == 6) {
                        this.spriteNinja = new Image( "Imagenes/Ninja(6).png" ); 
                    }else if (this.secuencia == 7) {
                        this.spriteNinja = new Image( "Imagenes/Ninja(7).png" ); 
                    }else if (this.secuencia == 8) {
                        this.spriteNinja = new Image( "Imagenes/Ninja(8).png" ); 
                    }else if (this.secuencia == 9) {
                        this.spriteNinja = new Image( "Imagenes/Ninja(9).png" ); 
                    }
                    
                  this.secuencia++;
                  this.seMueveDerecha = false;
                }
          }
        
        if(this.numero % 9 == 0 && this.seMueveIzquierda){
                if(this.secuencia == 9){
                  this.secuencia = 0;
                }else{
                    if (this.secuencia == 0) {
                        this.spriteNinja = new Image( "Imagenes/Ninja(0) - copia.png" ); 
                    }else if (this.secuencia == 1) {
                        this.spriteNinja = new Image( "Imagenes/Ninja(1) - copia.png" ); 
                    }else if (this.secuencia == 2) {
                        this.spriteNinja = new Image( "Imagenes/Ninja(2) - copia.png" ); 
                    }else if (this.secuencia == 3) {
                        this.spriteNinja = new Image( "Imagenes/Ninja(3) - copia.png" ); 
                    }else if (this.secuencia == 4) {
                        this.spriteNinja= new Image( "Imagenes/Ninja(4) - copia.png" ); 
                    }else if (this.secuencia == 5) {
                        this.spriteNinja = new Image( "Imagenes/Ninja(5) - copia.png" ); 
                    }else if (this.secuencia == 6) {
                        this.spriteNinja = new Image( "Imagenes/Ninja(6) - copia.png" ); 
                    }else if (this.secuencia == 7) {
                        this.spriteNinja = new Image( "Imagenes/Ninja(7) - copia.png" ); 
                    }else if (this.secuencia == 8) {
                        this.spriteNinja = new Image( "Imagenes/Ninja(8) - copia.png" ); 
                    }else if (this.secuencia == 9) {
                        this.spriteNinja = new Image( "Imagenes/Ninja(9) - copia.png" ); 
                    }
                    
                  this.secuencia++;
                  this.seMueveIzquierda = false;
                }
          }
        if(this.numero % 17 == 0){
                if(this.secuenciaF == 17){
                  this.secuenciaF = 0;
                }else{
                    if (this.secuenciaF == 0 || this.secuenciaF == 1 || this.secuenciaF == 2) {
                        this.fuego = new Image( "Imagenes/fuego0.png" ); 
                    }else if (this.secuenciaF == 3 || this.secuenciaF == 4 || this.secuenciaF == 5) {
                        this.fuego = new Image( "Imagenes/fuego1.png" ); 
                    }else if (this.secuenciaF == 6 || this.secuenciaF == 7 || this.secuenciaF == 8) {
                        this.fuego = new Image( "Imagenes/fuego2.png" ); 
                    }else if (this.secuenciaF == 9 || this.secuenciaF == 10 || this.secuenciaF == 11) {
                        this.fuego = new Image( "Imagenes/fuego3.png" ); 
                    }else if (this.secuenciaF == 12 || this.secuenciaF == 13 || this.secuenciaF == 14) {
                        this.fuego = new Image( "Imagenes/fuego4.png" ); 
                    }else if (this.secuenciaF == 15 || this.secuenciaF == 16 || this.secuenciaF == 17) {
                        this.fuego = new Image( "Imagenes/fuego5.png" ); 
                    }
                    
                  this.secuenciaF++;
                }
         }
        this.gravedad = true;
        this.chocandoI = false;
        this.chocandoD = false;
        this.chocandoTecho = false;
        
        // 
        superficies = new ArrayList<>();
        techos = new ArrayList<>();
        lateralesIzq = new ArrayList<>();
        lateralesDer = new ArrayList<>();
        obstaculos = new ArrayList<>();
    }
    
    public Scene getScene() {
       return this.escena;     
    }



}
