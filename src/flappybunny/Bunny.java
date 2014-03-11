/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package flappybunny;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

/**
 *
 * @author Ovidio
 */
public class Bunny extends Base {
    
    private double yvel;
    private double gravedad;
    private int jumpDelay;
            
    /**
    * Metodo constructor que hereda los atributos de la clase <code>Base</code>.
    * @param x es la <code>posiscion en x</code> del objeto Bueno.
    * @param y es el <code>posiscion en y</code> del objeto Bueno.
    * @param image es la <code>imagen</code> del objeto Bueno.
    */
    public Bunny(int x,int y){
        super(x,y);
        yvel = 0;
        gravedad = 0.5;
    }
    
    public void update(boolean click) {
        yvel += gravedad;

        if (jumpDelay > 0)
            jumpDelay--;

        if (click && jumpDelay <= 0) {
            yvel = -10;
            jumpDelay = 10;
        }

        y += (int)yvel;
    }
    
    public void setYVel(double yvel) {
        this.yvel = yvel;
    }
    
    public double getYVel() {
        return yvel;
    }
    
    public void setGravedad(double gravedad) {
        this.gravedad = gravedad;
    }
    
    public double getGravedad() {
        return gravedad;
    }
    
    public void setDelay(int jumpDelay) {
        this.jumpDelay = jumpDelay;
    }
    
    public int getDelay() {
        return jumpDelay;
    }
}
