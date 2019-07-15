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
public class Ninja {
    private int xref;
    private int yref;
    private int ancho;
    private int alto;
    
    public Ninja(int xref, int yref, int ancho, int alto) {
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
    
    public void moverDerecha(){
      this.xref++;
    }
    public void moverIzquierda(){
      this.xref--;
    }
    public void moverArriba(){
      this.yref--;
    }
    public void moverAbajo(){
      this.yref++;
    }
}
