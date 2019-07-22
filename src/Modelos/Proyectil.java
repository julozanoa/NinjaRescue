/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos;

/**
 *
 * @author usuario1
 */
public class Proyectil {
    private int xref;
    private int yref;
    private int ancho;
    private int alto;

    public Proyectil(int xref, int yref, int ancho, int alto) {
        this.xref = xref;
        this.yref = yref;
        this.ancho = ancho;
        this.alto = alto;
    }

    public int getXref() {
        return xref;
    }

    public int getYref() {
        return yref;
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }
    
    public void moverIzquierda(int x){
      this.xref-=x;
    }
    public void moverDerecha(int x){
      this.xref+=x;
    }
}
