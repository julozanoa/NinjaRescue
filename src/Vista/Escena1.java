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
    private Image llave;
    private Image puerta;
    private int secuencia = 0;
    private int secuenciaF = 0;
    private int numero ;
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
    private ArrayList<String> pulsacionTeclado = null;
    private int contadorSalto = 0;
    private ArrayList<Shape> superficies;
    private ArrayList<Shape> techos;
    private ArrayList<Shape> lateralesIzq;
    private ArrayList<Shape> lateralesDer;
    private ArrayList<Shape> obstaculos;
    private boolean nivelSuperado;
    
    public Escena1(Scene escena, GraphicsContext lapiz) {
        this.lapiz = lapiz;
        this.escena = escena;
        this.ninja = new Ninja(800,475, 40, 60);
        this.fondo = new Image( "Imagenes/fondojuego3.png" );
        this.pisosprite = new Image( "Imagenes/Plataforma1.png" );
        this.ninjasprite = new Image( "Imagenes/Ninja(0).png" );
        this.obstaculo1 = new Image( "Imagenes/Obstaculo1.png" );
        this.obstaculo2 = new Image( "Imagenes/Obstaculo3.png" );
        this.fuego = new Image( "Imagenes/fuego0.png" );
        this.llave = new Image( "Imagenes/goldenkey.png");
        this.puerta = new Image( "Imagenes/Door.png" );
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
        lapiz.drawImage(puerta,0,10);
        
        //Dibujando sprite del ninja
        lapiz.drawImage(ninjasprite,ninja.getXref(),ninja.getYref());
        //creando la hitbox del ninja
        Shape sNinja = new Rectangle(ninja.getXref()+10, ninja.getYref(), ninja.getAncho()-20, ninja.getAlto());
        Shape sNinjaPiso = new Rectangle(ninja.getXref()+10,ninja.getYref()+58,20,4);
        Shape sNinjaLateralI = new Rectangle(ninja.getXref()-1,ninja.getYref(),40,60);
        Shape sNinjaLateralD = new Rectangle(ninja.getXref()+1,ninja.getYref(),40,60);
        Shape sNinjaTecho = new Rectangle(ninja.getXref(),ninja.getYref()-1,40,3);
        
        //laterales del mapa
        Shape paredI = new Rectangle(-39,0,40,576);
        Shape sParedI = SVGPath.intersect(sNinjaLateralI, paredI);
        lateralesIzq.add(sParedI);
        Shape paredD = new Rectangle(1024,0,40,576);
        Shape sParedD = SVGPath.intersect(sNinjaLateralI, paredD);
        lateralesDer.add(sParedD);
        
        
        //creando piso y registrando los pixeles

               //piso
               
        int x1 = 0;
        int y1 = 0;
        int y = 430;
        int x2 = 0;
        //puerta
        
        Shape puerta = new Rectangle(0,10,50,70);
        Shape sPuerta = SVGPath.intersect(sNinja,puerta);
        
        //lave
            Shape sllave = new Rectangle(964,30,40,20);
            Shape sIntrsLlave = SVGPath.intersect(sNinja, sllave);
        if (hayllave) {
            lapiz.drawImage(llave,964,30);
        }
        
        lapiz.drawImage(obstaculo1,532,420);
        Shape techoBi = new Rectangle(532,419,40,4);
        Shape intrsPbi = SVGPath.intersect(sNinjaPiso, techoBi);
        superficies.add(intrsPbi);
        Shape lateralBiD = new Rectangle(568,423,4,40);
        Shape intrsBiD = SVGPath.intersect(sNinjaLateralI, lateralBiD);
        lateralesIzq.add(intrsBiD);
        Shape lateralBiI = new Rectangle(532,423,4,40);
        Shape intrsBiI = SVGPath.intersect(sNinjaLateralI, lateralBiI);
        lateralesDer.add(intrsBiI);
        
        //fuego
        for (int i = 0; i < 5; i++) {
            lapiz.drawImage(fuego,y1+444,536);
            Shape fuego = new Rectangle(y1+444,536,40,40);
            Shape obsFuego = SVGPath.intersect(sNinja, fuego);
            obstaculos.add(obsFuego);
            y1 += 44;
        }
        for (int i = 0; i < 2; i++) {
            lapiz.drawImage(obstaculo1,x2+160,x2+160);
            lapiz.drawImage(obstaculo1,532,420);
              Shape techoBaP = new Rectangle(x2+160,x2+159,40,4);
              Shape intrsBaP = SVGPath.intersect(sNinjaPiso, techoBaP);
              superficies.add(intrsBaP);
              Shape lateralDBaP = new Rectangle(x2+197,x2+163,4,40);
              Shape intrsDBaP = SVGPath.intersect(sNinjaLateralI,lateralDBaP);
              lateralesIzq.add(intrsDBaP);
            x2 += 60;
        }
        //piso inicio
        for (int i = 0; i < 4; i++) {
            
            //piso a puerta
               lapiz.drawImage(pisosprite,x1+280,280);
               Shape techobp = new Rectangle(x1+280,280,40,4);
               Shape intrsbp = SVGPath.intersect(sNinjaPiso, techobp);
               superficies.add(intrsbp);
               
            if (i == 3) {
               Shape lateralD = new Rectangle(x1+317,283,4,40);
               Shape intrsD = SVGPath.intersect(sNinjaLateralI, lateralD);
               lateralesIzq.add(intrsD);
               
               lapiz.drawImage(obstaculo2,x1+280,258);
               Shape obst = new Rectangle(x1+280,258,40,22);
               Shape intrsObst = SVGPath.intersect(sNinja, obst);
               obstaculos.add(intrsObst);
            }
            if (i == 0) {
               lapiz.drawImage(obstaculo2,x1+280,258);
               lapiz.drawImage(obstaculo2,x1+280,258);
               Shape obst = new Rectangle(x1+280,258,40,22);
               Shape intrsObst = SVGPath.intersect(sNinja, obst);
               obstaculos.add(intrsObst);
            }
               
            lapiz.drawImage(pisosprite,x1, 536);
            lapiz.drawImage(pisosprite,x1+280, 536);
            
            //piso de la puerta
            lapiz.drawImage(pisosprite,x1,80);
               Shape s1lT = new Rectangle(x1,79,40,4);
               Shape intrsp = SVGPath.intersect(sNinjaPiso, s1lT);
               superficies.add(intrsp);
               Shape s1lTD = new Rectangle(x1+37,82,4,40);
               Shape intrspD = SVGPath.intersect(sNinjaLateralI, s1lTD);
               lateralesIzq.add(intrspD);
               
            //plataformas
               Shape techo = new Rectangle(x1,535,40,4);
               Shape intrsP = SVGPath.intersect(sNinjaPiso, techo);
               superficies.add(intrsP);
               Shape techo2 = new Rectangle(x1+280,535,40,4);
               Shape intrsP1 = SVGPath.intersect(sNinjaPiso, techo2);
               superficies.add(intrsP1);
            if (i == 2) {
               Shape lateralD = new Rectangle(x1+37,538,4,40);
               Shape intrsD = SVGPath.intersect(sNinjaLateralI, lateralD);
               lateralesIzq.add(intrsD);
               Shape lateralDp = new Rectangle(x1+37,82,4,40);
               Shape intrsDp = SVGPath.intersect(sNinjaLateralI, lateralDp);
               lateralesIzq.add(intrsDp);   
            }
            if (x1+280 == 280) {
               Shape lateralI = new Rectangle(x1+279,539,4,40);
               Shape intrsI = SVGPath.intersect(sNinjaLateralD, lateralI);
               lateralesDer.add(intrsI);
            }
            if (x1+280 == 400) {
               lapiz.drawImage(obstaculo1,x1+294,496);
               Shape techoB = new Rectangle(x1+294,495,40,4);
               Shape intrsB = SVGPath.intersect(sNinjaPiso, techoB);
               superficies.add(intrsB);
               Shape lateralIB = new Rectangle(x1+299,499,4,40);
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
               Shape s1lTp = new Rectangle(x1+504,79,40,4);
               Shape intrspp = SVGPath.intersect(sNinjaPiso, s1lTp);
               superficies.add(intrspp);
               
            if (i == 0) {
               //lateral piso llave
               Shape lateralI = new Rectangle(x1+504,83,4,40);
               Shape intrsI = SVGPath.intersect(sNinjaLateralD, lateralI);
               lateralesDer.add(intrsI);
               
               //
               lapiz.drawImage(obstaculo1,x1+490,496);
               Shape techoB = new Rectangle(x1+490,495,40,4);
               Shape intrsB = SVGPath.intersect(sNinjaPiso, techoB);
               superficies.add(intrsB);
               Shape lateralIB = new Rectangle(x1+491,497,4,40);
               Shape intrsIB = SVGPath.intersect(sNinjaLateralD, lateralIB);
               lateralesDer.add(intrsIB);
            }
            if (i == 1) {
               lapiz.drawImage(obstaculo2,x1+484,514);
               Shape obs = new Rectangle(x1+484,514,40,22);
               Shape intrsObs = SVGPath.intersect(sNinja, obs);
               obstaculos.add(intrsObs);
            }
            if (i == 3) {
                for (int j = 0; j < 6; j++) {
                    lapiz.drawImage(pisosprite,x1+490,y);
                    Shape lateralI = new Rectangle(x1+489,y,4,40);
                    Shape intrsI = SVGPath.intersect(sNinjaLateralD, lateralI);
                    lateralesDer.add(intrsI);
                    Shape lateralDp = new Rectangle(x1+526,y,5,40);
                    Shape intrsDp = SVGPath.intersect(sNinjaLateralI, lateralDp);
                    lateralesIzq.add(intrsDp);
                    if (j == 2) {
                        lapiz.drawImage(obstaculo1,984,y-20);
                        Shape lateralIb = new Rectangle(990,y-17,4,40);
                        Shape intrsIb = SVGPath.intersect(sNinjaLateralD, lateralIb);
                        lateralesDer.add(intrsIb);
                        Shape techoB = new Rectangle(990,y-21,40,4);
                        Shape intrsB = SVGPath.intersect(sNinjaPiso, techoB);
                        superficies.add(intrsB);
                    }
                    if (j == 5) {
                        lapiz.drawImage(obstaculo2,x1+490,y-22);
                        Shape obs = new Rectangle(x1+490,y-22,40,22);
                        Shape intrsObs = SVGPath.intersect(sNinja, obs);
                        obstaculos.add(intrsObs);
                        
                        //piso antes de llave
                        
                        lapiz.drawImage(pisosprite,x1+370,y);
                        Shape techoB = new Rectangle(x1+370,y,40,2);
                        Shape intrsB = SVGPath.intersect(sNinjaPiso, techoB);
                        superficies.add(intrsB);
                        Shape ladoIB = new Rectangle(x1+369,y+3,4,40);
                        Shape intrsIB = SVGPath.intersect(sNinjaLateralD, ladoIB);
                        lateralesDer.add(intrsIB);
                        
                        lapiz.drawImage(pisosprite,x1+410,y);
                        Shape techoB0 = new Rectangle(x1+410,y,40,2);
                        Shape intrsB0 = SVGPath.intersect(sNinjaPiso, techoB0);
                        superficies.add(intrsB0);
                        Shape ladoDB = new Rectangle(x1+437,y+3,4,40);
                        Shape intrsDB = SVGPath.intersect(sNinjaLateralD, ladoDB);
                        lateralesIzq.add(intrsDB);
                        
                        
                    }
                    y -= 40;
                }
            }
            if (i <= 6 && i >= 3) {
                lapiz.drawImage(pisosprite,x1+490,430);
                Shape techoB = new Rectangle(x1+490,429,39,4);
                Shape intrsB = SVGPath.intersect(sNinjaPiso, techoB);
                superficies.add(intrsB);
                Shape s = new Rectangle(x1+490,466,40,5);
                Shape intrsS = SVGPath.intersect(sNinjaTecho, s);
                techos.add(intrsS);
                if (i == 6) {
                  Shape lateralDp = new Rectangle(x1+527,433,2,40);
                  Shape intrsDp = SVGPath.intersect(sNinjaLateralI, lateralDp);
                  lateralesIzq.add(intrsDp);
                  
                  Shape lateralDp1 = new Rectangle(x1+527,233,2,40);
                  Shape intrsDp1 = SVGPath.intersect(sNinjaLateralI, lateralDp1);
                  lateralesIzq.add(intrsDp1);
                }
                
                lapiz.drawImage(pisosprite,x1+490,230);
                Shape techoB1 = new Rectangle(x1+490,229,39,4);
                Shape intrsB1 = SVGPath.intersect(sNinjaPiso, techoB1);
                superficies.add(intrsB1);
                Shape s1 = new Rectangle(x1+490,266,40,5);
                Shape intrsS1 = SVGPath.intersect(sNinjaTecho, s1);
                techos.add(intrsS1);
                
            }
            
            x1 += 40;
        }
        
        for (int i = 0; i < 4; i++) {
            lapiz.drawImage(pisosprite,x1+10,y-40);
            Shape techoB1 = new Rectangle(x1+10,y-41,40,4);
            Shape intrsB1 = SVGPath.intersect(sNinjaPiso, techoB1);
            superficies.add(intrsB1);
            if (i == 0) {
                  Shape lateralDp1 = new Rectangle(x1+48,y-37,3,40);
                  Shape intrsDp1 = SVGPath.intersect(sNinjaLateralI, lateralDp1);
                  lateralesIzq.add(intrsDp1);
            }
            if (i == 3) {
                  Shape ladoIB = new Rectangle(x1+10,y-37,3,40);
                  Shape intrsIB = SVGPath.intersect(sNinjaLateralD, ladoIB);
                  lateralesDer.add(intrsIB);
            }
            x1 -= 40;
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
        
        //validando si tiene la llava
        if (sIntrsLlave.getBoundsInLocal().getWidth() != -1) {
            hayllave = false;
            tienellave = true;
        }
        //Validando si se ha chocado con algun obstaculo
        for (int i = 0; i < obstaculos.size(); i++) {
            if ((obstaculos.get(i).getBoundsInLocal().getWidth()) != -1) {
                ninja.setXref(2);
                ninja.setYref(470);
                
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

        
        if (sPuerta.getBoundsInLocal().getWidth() != -1 && tienellave) {
            nivelSuperado = true;
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
        
        if (nivelSuperado && pulsacionTeclado.contains("DOWN")) {
           Singleton singleton = Singleton.getSingleton();
           Stage stage = singleton.getStage();
           Controlador2 controlador = new Controlador2();
           Scene escena = controlador.getVista().getScene();
           stage.setScene(escena);
           stop();
        }
        
    }
    
    public Scene getScene() {
       return this.escena;     
    }

    public boolean isNivelSuperado() {
        return nivelSuperado;
    }

    
    
}
