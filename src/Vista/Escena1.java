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
    private Image obstaculo1;
    private Image obstaculo2;
    private Image fuego;
    private int secuencia = 0;
    private int secuenciaF = 0;
    private int numero ;
    private boolean UpIsPress = false;
    private boolean seMueveDerecha = false;
    private boolean seMueveIzquierda = false;
    private boolean gravedad;
    private boolean saltando;
    private boolean chocandoI;
    private boolean chocandoD;
    private boolean chocandoTecho;
    private ArrayList<String> pulsacionTeclado = null;
    private int contadorSalto = 0;
    private ArrayList<Shape> superficies;
    private ArrayList<Shape> techos;
    private ArrayList<Shape> lateralesIzq;
    private ArrayList<Shape> lateralesDer;
    private ArrayList<Shape> obstaculos;
    
    public Escena1(Scene escena, GraphicsContext lapiz) {
        this.lapiz = lapiz;
        this.escena = escena;
        this.ninja = new Ninja(800,475, 40, 60);
        this.fondo = new Image( "Imagenes/fondojuego2.png" );
        this.pisosprite = new Image( "Imagenes/Plataforma1.png" );
        this.ninjasprite = new Image( "Imagenes/Ninja(0).png" );
        this.obstaculo1 = new Image( "Imagenes/Obstaculo1.png" );
        this.obstaculo2 = new Image( "Imagenes/Obstaculo3.png" );
        this.fuego = new Image( "Imagenes/fuego0.png" );
        superficies = new ArrayList<>();
        techos = new ArrayList<>();
        lateralesIzq = new ArrayList<>();
        lateralesDer = new ArrayList<>();
        pulsacionTeclado = new ArrayList<>();
        obstaculos = new ArrayList<>();
            
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
        //creando la hitbox del ninja
        Shape sNinja = new Rectangle(ninja.getXref()+10, ninja.getYref(), ninja.getAncho()-20, ninja.getAlto());
        Shape sNinjaPiso = new Rectangle(ninja.getXref()+10,ninja.getYref()+58,20,4);
        Shape sNinjaLateralI = new Rectangle(ninja.getXref()-1,ninja.getYref(),40,60);
        Shape sNinjaLateralD = new Rectangle(ninja.getXref()+1,ninja.getYref(),40,60);
        Shape sNinjaTecho = new Rectangle(ninja.getXref(),ninja.getYref()-1,40,3);
        
        
        //creando piso y registrando los pixeles


               //piso
               
        int x1 = 0;
        int y1 = 0;
        lapiz.drawImage(obstaculo1,532,420);
        Shape techoBi = new Rectangle(532,419,40,4);
        Shape intrsPbi = SVGPath.intersect(sNinjaPiso, techoBi);
        superficies.add(intrsPbi);
        Shape lateralBiD = new Rectangle(568,420,4,40);
        Shape intrsBiD = SVGPath.intersect(sNinjaLateralI, lateralBiD);
        lateralesIzq.add(intrsBiD);
        Shape lateralBiI = new Rectangle(532,420,4,40);
        Shape intrsBiI = SVGPath.intersect(sNinjaLateralI, lateralBiI);
        lateralesDer.add(intrsBiI);
        //fuego
        for (int i = 0; i < 7; i++) {
            lapiz.drawImage(fuego,y1+442,546);
            Shape fuego = new Rectangle(y1+442,546,30,30);
            Shape obsFuego = SVGPath.intersect(sNinja, fuego);
            obstaculos.add(obsFuego);
            y1 += 30;
        }
        //piso inicio
        for (int i = 0; i < 4; i++) {
            
            lapiz.drawImage(pisosprite,x1, 536);
            lapiz.drawImage(pisosprite,x1+280, 536);
            //piso de la puerta
            lapiz.drawImage(pisosprite,x1,80);
               Shape s1lT = new Rectangle(x1,121,40,4);
               Shape intrsp = SVGPath.intersect(sNinjaTecho, s1lT);
               techos.add(intrsp);
            //plataformas
               Shape techo = new Rectangle(x1,535,40,4);
               Shape intrsP = SVGPath.intersect(sNinjaPiso, techo);
               superficies.add(intrsP);
               Shape techo2 = new Rectangle(x1+280,535,40,4);
               Shape intrsP1 = SVGPath.intersect(sNinjaPiso, techo2);
               superficies.add(intrsP1);
            if (x1 == 120) {
               Shape lateralD = new Rectangle(x1+37,536,4,40);
               Shape intrsD = SVGPath.intersect(sNinjaLateralI, lateralD);
               lateralesIzq.add(intrsD);
               Shape lateralDp = new Rectangle(x1+37,80,4,40);
               Shape intrsDp = SVGPath.intersect(sNinjaLateralI, lateralDp);
               lateralesIzq.add(intrsDp);
               
            }
            if (x1+280 == 280) {
               Shape lateralI = new Rectangle(x1+279,536,4,40);
               Shape intrsI = SVGPath.intersect(sNinjaLateralD, lateralI);
               lateralesDer.add(intrsI);
            }
            //barril inermedio
            
            if (x1+280 == 400) {
               lapiz.drawImage(obstaculo1,x1+280,496);
               Shape techoB = new Rectangle(x1+280,495,40,4);
               Shape intrsB = SVGPath.intersect(sNinjaPiso, techoB);
               superficies.add(intrsB);
               Shape lateralIB = new Rectangle(x1+285,497,4,40);
               Shape intrsIB = SVGPath.intersect(sNinjaLateralD, lateralIB);
               lateralesDer.add(intrsIB);
               lapiz.drawImage(obstaculo2,x1+240,514);
               Shape obs = new Rectangle(x1+240,514,40,22);
               Shape intrsObs = SVGPath.intersect(sNinja, obs);
               obstaculos.add(intrsObs);
            }
            if (x1+160 == 160 || x1+160 == 200 || x1+160 == 240) {
               lapiz.drawImage(obstaculo2,x1+160,554);
               Shape obs = new Rectangle(x1+160,554,40,22);
               Shape intrsObs = SVGPath.intersect(sNinja, obs);
               obstaculos.add(intrsObs);
            }
            x1+=40;
        }
        for (int i = 0; i < 9; i++) {
            lapiz.drawImage(pisosprite,x1+504, 536);
            Shape techo = new Rectangle(x1+504,535,40,4);
               Shape intrsP = SVGPath.intersect(sNinjaPiso, techo);
               superficies.add(intrsP);
            //piso llave
            lapiz.drawImage(pisosprite,x1+504, 80);
            Shape s1lT = new Rectangle(x1+504,121,40,4);
               Shape intrsp = SVGPath.intersect(sNinjaTecho, s1lT);
               techos.add(intrsp);
            if (i == 0) {
               lapiz.drawImage(obstaculo1,x1+504,496);
               Shape techoB = new Rectangle(x1+504,495,40,4);
               Shape intrsB = SVGPath.intersect(sNinjaPiso, techoB);
               superficies.add(intrsB);
               Shape lateralIB = new Rectangle(x1+509,497,4,40);
               Shape intrsIB = SVGPath.intersect(sNinjaLateralD, lateralIB);
               lateralesDer.add(intrsIB);
            }
            if (i == 1) {
               lapiz.drawImage(obstaculo2,x1+504,514);
               Shape obs = new Rectangle(x1+504,514,40,22);
               Shape intrsObs = SVGPath.intersect(sNinja, obs);
               obstaculos.add(intrsObs);
            }
            x1 += 40;
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
        
        //Validando si se ha chocado con algun obstaculo
        for (int i = 0; i < obstaculos.size(); i++) {
            if ((obstaculos.get(i).getBoundsInLocal().getWidth()) != -1) {
                ninja.setXref(0);
                ninja.setYref(470);
                break;
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
        System.out.println(this.chocandoTecho);
        System.out.println(this.gravedad);
        System.out.println(this.saltando);
        if (saltando && contadorSalto <= 20) {
            if (!chocandoTecho) {
                ninja.moverArriba();
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
            this.seMueveIzquierda = true;
        }if (pulsacionTeclado.contains("RIGHT") && !chocandoD){
            ninja.moverDerecha();
            ninja.moverDerecha();
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
        
        if(this.numero % 9 == 0 && this.seMueveIzquierda){
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
        superficies = new ArrayList<>();
        techos = new ArrayList<>();
        lateralesIzq = new ArrayList<>();
        lateralesDer = new ArrayList<>();
        obstaculos = new ArrayList<>();
    }
    
}
