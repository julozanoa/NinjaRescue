/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvc;


import Controladores.Singleton;
import Controladores.Controlador1;
import Controladores.Controlador2;
import Controladores.Controlador3;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import Vista.Escena1;
/**
 *
 * @author Estudiante
 */
public class MVC extends Application{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
       Singleton singleton = Singleton.getSingleton();
       singleton.setStage(primaryStage);
       Controlador3 controlador = new Controlador3();
       Scene escena = controlador.getVista().getScene();
       primaryStage.setTitle("Escena 1");
       primaryStage.setScene(escena);
       primaryStage.show();
        
    }
    
}
