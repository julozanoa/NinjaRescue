/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Vista.Escena1;
import Vista.Escena2;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 *
 * @author JUANPABLO
 */
public class Controlador2 {
    private Escena2 vista;
    private Pane layout;
    private Canvas canvas;
    private Scene escena;
    private GraphicsContext lapiz;
    
    public Controlador2() {
      this.layout = new Pane();
      this.canvas = new Canvas(1024,576);
      this.escena = new Scene(this.layout,1024,576, Color.WHITESMOKE);
      this.lapiz = this.canvas.getGraphicsContext2D();
      this.layout.getChildren().add(this.canvas);
      this.vista = new Escena2(this.escena,this.lapiz);
      this.vista.start();
    }
    
    public Escena2 getVista() {
        return vista;
    }

    public void setVista(Escena2 vista) {
        this.vista = vista;
    }
}
