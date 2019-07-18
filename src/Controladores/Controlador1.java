/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Vista.Escena1;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author JUANPABLO
 */
public class Controlador1 {
    private Escena1 vista;
    private Pane layout;
    private Canvas canvas;
    private Scene escena;
    private GraphicsContext lapiz;
    
    public Controlador1() {
      this.layout = new Pane();
      this.canvas = new Canvas(1024,576);
      this.escena = new Scene(this.layout,1024,576, Color.WHITESMOKE);
      this.lapiz = this.canvas.getGraphicsContext2D();
      this.layout.getChildren().add(this.canvas);
      this.vista = new Escena1(this.escena,this.lapiz);
      this.vista.start();
    }
    public Escena1 getVista() {
        return vista;
    }

    public void setVista(Escena1 vista) {
        this.vista = vista;
    }
}
