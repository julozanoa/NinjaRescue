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
public class Pixel {
    private int xRef;
    private int yRef;

    public Pixel(int xRef, int yRef) {
        this.xRef = xRef;
        this.yRef = yRef;
    }

    public int getxRef() {
        return xRef;
    }

    public void setxRef(int xRef) {
        this.xRef = xRef;
    }

    public int getyRef() {
        return yRef;
    }

    public void setyRef(int yRef) {
        this.yRef = yRef;
    }
    
    
}
