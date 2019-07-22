/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controladores.Controlador2;
import Controladores.Controlador4;
import Controladores.Controlador5;
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
import Modelos.Proyectil;
/**
 *
 * @author usuario1
 */
public class NivelFinal extends AnimationTimer{

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
    private Image princesa;
    private Image ninjaMaloSprite;
    private Image proyectil;
    private Image proyectilI;
    private Image proyectilD;
    private Ninja ninja;
    private Ninja ninjaMalo;
    private ArrayList<String> pulsacionTeclado = null;
    private ArrayList<Shape> superficies;
    private ArrayList<Shape> techos;
    private ArrayList<Shape> lateralesIzq;
    private ArrayList<Shape> lateralesDer;
    private ArrayList<Shape> obstaculos;
    private ArrayList<Proyectil> pMalos;
    private ArrayList<Proyectil> pBuenosI;
    private ArrayList<Proyectil> pBuenosD;
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
    private boolean disparo;
    private boolean izquierda;
    private boolean derecha;
    private boolean disparando;
    private boolean boss = true;
    private boolean tllave = false;
    private boolean tieneLlave = false;
    private boolean ePuerta = true;
    private int secuencia = 0;
    private int secuenciaF = 0;
    private int secuenciaNm = 0;
    private int numero ;
    private int contadorSalto = 0;
    private int contadorDisparo = 0;

    public NivelFinal(Scene escena, GraphicsContext lapiz) {
        this.escena = escena;
        this.lapiz = lapiz;
        this.fondo = new Image( "Imagenes/fondojuego2.png" );
        this.spriteNinja = new Image( "Imagenes/Ninja(0).png" );
        this.pisosprite = new Image( "Imagenes/Plataforma1.png" );
        this.obstaculo1 = new Image( "Imagenes/Obstaculo1.png" );
        this.obstaculo2 = new Image( "Imagenes/Obstaculo3.png" );
        this.fuego = new Image( "Imagenes/fuego0.png" );
        this.llave = new Image( "Imagenes/goldenkey.png");
        this.puerta = new Image( "Imagenes/Door2.png" );
        this.princesa = new Image( "Imagenes/princesa.png" );
        this.ninjaMaloSprite = new Image( "Imagenes/Boss(0).png" );
        this.proyectil = new Image ( "Imagenes/proyectil.png" );
        this.proyectilI = new Image ( "Imagenes/proyectilNinjaI.png" );
        this.proyectilD = new Image ( "Imagenes/proyectilNinjaD.png" );
        this.ninja = new Ninja(0,472,40,60);
        this.ninjaMalo = new Ninja(944,395,80,61);
        pulsacionTeclado = new ArrayList<>();
        superficies = new ArrayList<>();
        techos = new ArrayList<>();
        lateralesIzq = new ArrayList<>();
        lateralesDer = new ArrayList<>();
        obstaculos = new ArrayList<>();
        pMalos = new ArrayList<>();
        pBuenosI = new ArrayList<>();
        pBuenosD = new ArrayList<>();
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
        
        lapiz.drawImage(pisosprite,944,33);    
        lapiz.drawImage(pisosprite,984,33);
        if (ePuerta) {
        lapiz.drawImage(puerta,944,65);
        }
        //princesa
        lapiz.drawImage(princesa,984,96);
        //ninja
        lapiz.drawImage(spriteNinja,ninja.getXref(),ninja.getYref());
        Shape sNinja = new Rectangle(ninja.getXref()+10, ninja.getYref(), ninja.getAncho()-20, ninja.getAlto());
        Shape sNinjaPiso = new Rectangle(ninja.getXref()+10,ninja.getYref()+58,20,4);
        Shape sNinjaLateralI = new Rectangle(ninja.getXref()-1,ninja.getYref(),40,60);
        Shape sNinjaLateralD = new Rectangle(ninja.getXref()+1,ninja.getYref(),40,60);
        Shape sNinjaTecho = new Rectangle(ninja.getXref(),ninja.getYref()-1,40,3);
        
        Shape sPrin = new Rectangle(984,96,40,60);
        Shape win = SVGPath.intersect(sNinja,sPrin);
        
        if (boss) {
        lapiz.drawImage(ninjaMaloSprite,ninjaMalo.getXref(),ninjaMalo.getYref());
        }
        if (tllave) {
        lapiz.drawImage(llave,964,395);
        }
        Shape ll = new Rectangle(964,395,40,30);
        Shape sLl = SVGPath.intersect(sNinja, ll);
        
        if (sLl.getBoundsInLocal().getWidth() != -1) {
            tllave = false;
            tieneLlave = true;
        }
        Shape nM = new Rectangle(964,395,80,61);
        
        //hitbox puerta
        Shape dR = new Rectangle(944,65,40,91);
        Shape sDr = SVGPath.intersect(sNinja,dR);
        if (sDr.getBoundsInLocal().getWidth() != -1 && !tieneLlave) {
            ninja.setXref(0);
            ninja.setYref(472);
        }
        if (sDr.getBoundsInLocal().getWidth() != -1 && tieneLlave) {
            ePuerta = false;
        }
        
        
        Shape t = new Rectangle(944,36,3,38);
        Shape st = SVGPath.intersect(sNinjaLateralI, t);
        lateralesDer.add(st);
       
        Shape d0 = new Rectangle(944,70,40,4);
        Shape sD0 = SVGPath.intersect(sNinjaTecho, d0);
        techos.add(sD0);
        Shape t00 = new Rectangle(984,70,40,4);
        Shape st00 = SVGPath.intersect(sNinjaTecho,t00);
        techos.add(st00);
        
        //laterales mapa
        Shape paredI = new Rectangle(-39,0,40,576);
        Shape sParedI = SVGPath.intersect(sNinjaLateralI, paredI);
        lateralesIzq.add(sParedI);
        Shape paredD = new Rectangle(1024,0,40,576);
        Shape sParedD = SVGPath.intersect(sNinjaLateralI, paredD);
        lateralesDer.add(sParedD);
        
        int x = 0;
        int y0 = 496;
        int y1 = 356;
        for (int i = 0; i < 25; i++) {
            int c = 0;
            for (int j = 0; j < 4; j++) {
                if (j == 0) {
                    lapiz.drawImage(obstaculo1,744+c,196);
                    Shape p1 = new Rectangle(744+c,195,40,4);
                    Shape sP1 = SVGPath.intersect(sNinjaPiso, p1);
                    superficies.add(sP1);
                    Shape pD = new Rectangle((744+c)+38,199,3,37);
                    Shape sD = SVGPath.intersect(sNinjaLateralI, pD);
                    lateralesIzq.add(sD);
                    lapiz.drawImage(obstaculo2,744+c,184);
                    Shape pin = new Rectangle(744+c,184,40,22);
                    Shape sPin = SVGPath.intersect(sNinja, pin);
                    obstaculos.add(sPin);
                }
                if (j == 1 || j == 2) {
                    lapiz.drawImage(pisosprite,784+c,236);
                    Shape p1 = new Rectangle(784+c,235,40,4);
                    Shape sP1 = SVGPath.intersect(sNinjaPiso, p1);
                    superficies.add(sP1);
                    
                    lapiz.drawImage(pisosprite,944+c,156);
                    Shape p10 = new Rectangle(944+c,156,40,4);
                    Shape sP10 = SVGPath.intersect(sNinjaPiso, p10);
                    superficies.add(sP10);
                    Shape pD10 = new Rectangle(943+c,159,3,37);
                    Shape sD10 = SVGPath.intersect(sNinjaLateralI, pD10);
                    lateralesDer.add(sD10);
                    
                    lapiz.drawImage(pisosprite,944+c,456);
                    Shape p100 = new Rectangle(944+c,456,40,4);
                    Shape sP100 = SVGPath.intersect(sNinjaPiso, p100);
                    superficies.add(sP100);
                    Shape pD100 = new Rectangle(943+c,459,3,37);
                    Shape sD100 = SVGPath.intersect(sNinjaLateralI, pD100);
                    lateralesDer.add(sD100);
                    c+=40;
                    
                }
                if (j == 3) {
                    lapiz.drawImage(obstaculo1,784+c,196);
                    Shape p1 = new Rectangle(784+c,195,40,4);
                    Shape sP1 = SVGPath.intersect(sNinjaPiso, p1);
                    superficies.add(sP1);
                    Shape pD10 = new Rectangle(783+c,199,3,37);
                    Shape sD10 = SVGPath.intersect(sNinjaLateralI, pD10);
                    lateralesDer.add(sD10);
                }
            }
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
                lapiz.drawImage(obstaculo2,x,334);
                Shape pin = new Rectangle(x,334,40,22);
                Shape sPin = SVGPath.intersect(sNinja, pin);
                obstaculos.add(sPin);
                
                lapiz.drawImage(pisosprite,x,186);
                Shape p2 = new Rectangle(x,345,40,4);
                Shape sP2 = SVGPath.intersect(sNinjaPiso, p1);
                superficies.add(sP2);
                Shape pt2 = new Rectangle(x,223,40,4);
                Shape sPt2 = SVGPath.intersect(sNinjaTecho, pt2);
                techos.add(sPt2);
                lapiz.drawImage(obstaculo2,x,164);
                Shape pin1 = new Rectangle(x,164,40,22);
                Shape sPin1 = SVGPath.intersect(sNinja, pin1);
                obstaculos.add(sPin1);
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
            if (i == 5) {
                lapiz.drawImage(obstaculo2,x,334);
                Shape pin = new Rectangle(x,334,40,22);
                Shape sPin = SVGPath.intersect(sNinja, pin);
                obstaculos.add(sPin);
                lapiz.drawImage(obstaculo2,x,134);
                Shape pin1 = new Rectangle(x,134,40,22);
                Shape sPin1 = SVGPath.intersect(sNinja, pin1);
                obstaculos.add(sPin1);
            }
            if (i == 5 || i == 6) {
                lapiz.drawImage(pisosprite,x,356);
                Shape p = new Rectangle(x,355,40,4);
                Shape sP = SVGPath.intersect(sNinjaPiso, p);
                superficies.add(sP);
                Shape pI2 = new Rectangle(x+38,359,3,37);
                Shape sI2 = SVGPath.intersect(sNinjaLateralI, pI2);
                lateralesIzq.add(sI2);
                
                lapiz.drawImage(pisosprite,x,156);
                Shape p1 = new Rectangle(x,155,40,4);
                Shape sP1 = SVGPath.intersect(sNinjaPiso, p1);
                superficies.add(sP1);
                Shape pI2d = new Rectangle(x+38,159,3,37);
                Shape sI2d = SVGPath.intersect(sNinjaLateralI, pI2d);
                lateralesIzq.add(sI2d);
            }
            if (i == 9) {
                int a = 0;
                lapiz.drawImage(obstaculo2,x+a+40,231);
                Shape pin1 = new Rectangle(x+a+40,231,40,22);
                Shape sPin1 = SVGPath.intersect(sNinja, pin1);
                obstaculos.add(sPin1);
                lapiz.drawImage(obstaculo2,x+a+40,434);
                Shape pin10 = new Rectangle(x+a+40,434,40,22);
                Shape sPin10 = SVGPath.intersect(sNinja, pin10);
                obstaculos.add(sPin10);
                lapiz.drawImage(obstaculo2,x+a+240,434);
                Shape pin101 = new Rectangle(x+a+240,434,40,22);
                Shape sPin101 = SVGPath.intersect(sNinja, pin101);
                obstaculos.add(sPin101);
                lapiz.drawImage(obstaculo2,x+a+400,434);
                Shape pin1010 = new Rectangle(x+a+400,434,40,22);
                Shape sPin1010 = SVGPath.intersect(sNinja, pin1010);
                obstaculos.add(sPin1010);
                for (int j = 0; j < 3; j++) {
                lapiz.drawImage(pisosprite,x+a,253);
                Shape p1 = new Rectangle(x+a,252,40,4);
                Shape sP1 = SVGPath.intersect(sNinjaPiso, p1);
                superficies.add(sP1);
                    if (j == 0) {
                        Shape pD2 = new Rectangle((x+a)-1,257,3,37);
                        Shape sD2 = SVGPath.intersect(sNinjaLateralD, pD2);
                        lateralesDer.add(sD2);
                    }
                    if (j == 2) {
                        Shape pI2 = new Rectangle((x+a)+38,256,3,37);
                        Shape sI2 = SVGPath.intersect(sNinjaLateralI, pI2);
                        lateralesIzq.add(sI2);
                    }
                a += 40;
                }
            }
            if (i >= 13 && i <= 17) {
                int b = 0;
                lapiz.drawImage(pisosprite,x+b,156);
                Shape p1 = new Rectangle(x+b,155,40,4);
                Shape sP1 = SVGPath.intersect(sNinjaPiso, p1);
                superficies.add(sP1);
                if (i == 13) {
                    Shape pD2 = new Rectangle((x+b)-1,160,3,37);
                    Shape sD2 = SVGPath.intersect(sNinjaLateralD, pD2);
                    lateralesDer.add(sD2);
                }
                if (i == 17) {
                    Shape pI2 = new Rectangle((x+b)+38,160,3,37);
                    Shape sI2 = SVGPath.intersect(sNinjaLateralI, pI2);
                    lateralesIzq.add(sI2);
                }
                b+=40;
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
            }else{
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

        
        if(this.numero % 9 == 0  && this.seMueveDerecha ){
            derecha = true;
            izquierda = false;
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
                }
          }
        
        if(this.numero % 9 == 0 && this.seMueveIzquierda){
            derecha = false;
            izquierda = true;
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
        
        if(this.numero % 49 == 0){
                if(this.secuenciaNm == 49){
                  this.secuenciaNm = 0;
                }else{
                    if (this.secuenciaNm >= 0 && this.secuenciaNm <= 4) {
                        this.ninjaMaloSprite = new Image( "Imagenes/Boss(0).png" ); 
                    }else if (this.secuenciaNm >= 5 && this.secuenciaNm <= 9) {
                        this.ninjaMaloSprite = new Image( "Imagenes/Boss(1).png" ); 
                    }else if (this.secuenciaNm >= 10 && this.secuenciaNm <= 14) {
                        this.ninjaMaloSprite = new Image( "Imagenes/Boss(2).png" ); 
                    }else if (this.secuenciaNm >= 15 && this.secuenciaNm <= 19) {
                        this.ninjaMaloSprite = new Image( "Imagenes/Boss(3).png" ); 
                    }else if (this.secuenciaNm >= 20 && this.secuenciaNm <= 24) {
                        this.ninjaMaloSprite = new Image( "Imagenes/Boss(4).png" ); 
                    }else if (this.secuenciaNm >= 25 && this.secuenciaNm <= 29) {
                        this.ninjaMaloSprite = new Image( "Imagenes/Boss(5).png" ); 
                    }else if (this.secuenciaNm >= 30 && this.secuenciaNm <= 34) {
                        this.ninjaMaloSprite = new Image( "Imagenes/Boss(6).png" ); 
                    }else if (this.secuenciaNm >= 35 && this.secuenciaNm <= 39) {
                        this.ninjaMaloSprite = new Image( "Imagenes/Boss(7).png" );
                        if (boss) {
                        Proyectil p = new Proyectil(924,415,20,20);
                        pMalos.add(p);
                        }
                    }else if (this.secuenciaNm >= 40 && this.secuenciaNm <= 44) {
                        this.ninjaMaloSprite = new Image( "Imagenes/Boss(8).png" ); 
                    }else if (this.secuenciaNm >= 45 && this.secuenciaNm <= 49) {
                        this.ninjaMaloSprite = new Image( "Imagenes/Boss(9).png" ); 
                    }
                  this.secuenciaNm++;
                }
          }
        for (int i = 0; i < pMalos.size(); i++) {
            lapiz.drawImage(proyectil,pMalos.get(i).getXref(),pMalos.get(i).getYref());
            Shape p = new Rectangle(pMalos.get(i).getXref(),pMalos.get(i).getYref(),20,20);
            Shape p0 = SVGPath.intersect(sNinja,p);
            obstaculos.add(p0);
        }
        
        //Validando si se ha chocado con algun obstaculo
        for (int i = 0; i < obstaculos.size(); i++) {
            if ((obstaculos.get(i).getBoundsInLocal().getWidth()) != -1) {
                ninja.setXref(0);
                ninja.setYref(472);
                
            }
        }
        for (int i = 0; i < pMalos.size(); i++) {
            pMalos.get(i).moverIzquierda(15);
        }
        if (pulsacionTeclado.contains("SPACE") && izquierda && !disparando) {
            Proyectil p = new Proyectil(ninja.getXref()-20,ninja.getYref()+30,20,20);
            pBuenosI.add(p);
            contadorDisparo = 20;
            disparando = true;
        }
        if (pulsacionTeclado.contains("SPACE") && derecha && !disparando) {
            Proyectil p = new Proyectil(ninja.getXref()+40,ninja.getYref()+30,20,20);
            pBuenosD.add(p);
            contadorDisparo = 20;
            disparando = true;
        }
        Shape z = null;
        if (disparando && contadorDisparo <= 20) {
            for (int i = 0; i < pBuenosI.size(); i++) {
            lapiz.drawImage(proyectilI,pBuenosI.get(i).getXref(),pBuenosI.get(i).getYref());
            Shape pi = new Rectangle(pBuenosI.get(i).getXref(),pBuenosI.get(i).getYref(),20,20);
            Shape snM = SVGPath.intersect(nM, pi);
            z = snM;
            pBuenosI.get(i).moverIzquierda(15);
            }
            for (int i = 0; i < pBuenosD.size(); i++) {
            lapiz.drawImage(proyectilD,pBuenosD.get(i).getXref(),pBuenosD.get(i).getYref());
            Shape pi = new Rectangle(pBuenosD.get(i).getXref(),pBuenosD.get(i).getYref(),20,20);
            Shape snM = SVGPath.intersect(nM, pi);
            z = snM;
            pBuenosD.get(i).moverDerecha(15);
            }
            contadorDisparo--;
            
            if (contadorDisparo == 0) {
                disparando = false;
            }
        }
        if (z != null && z.getBoundsInLocal().getWidth() != -1) {
            boss = false;
            tllave = true;
        }
        
        if (win.getBoundsInLocal().getWidth() != -1 && pulsacionTeclado.contains("SPACE")) {
           Singleton singleton = Singleton.getSingleton();
           Stage stage = singleton.getStage();
           Controlador5 controlador = new Controlador5();
           Scene escena = controlador.getVista().getScene();
           stage.setScene(escena);
           stage.setTitle("Final");
           stop();
        }
        this.seMueveDerecha = false;
        this.seMueveIzquierda = false;
        
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
        if (!disparando) {
            pBuenosI = new ArrayList<>();
            pBuenosD = new ArrayList<>();
        }
    }
    
    public Scene getScene() {
       return this.escena;     
    }



}
