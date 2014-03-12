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
 * @author Ovidio Villarreal && Graciela Garcia
 */
public class Bunny extends Base {
    
    private double yvel;
    private double gravedad;
    private int jumpDelay;
            
    /**
    * Metodo constructor que hereda los atributos de la clase <code>Base</code>.
    * @param x es la <code>posiscion en x</code> del objeto Bunny.
    * @param y es el <code>posiscion en y</code> del objeto Bunny.
    * @param image es la <code>imagen</code> del objeto Bunny.
    */
    public Bunny(int x,int y){
        super(x,y);
        yvel = 0;
        gravedad = 0.5;
        
        Image bunny = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/images/FlappyBunny_FlyingBunny.gif"));
        anim = new Animacion();
        anim.sumaCuadro(bunny, 100);
    }
    
    /**
    * Metodo que modifica la posicion en y del objeto
    * @param click es si <code>se dio click</code> al applet
    */
    public void update(boolean click) {
        yvel += gravedad;

        if (jumpDelay > 0)
            jumpDelay--;

        if (click && jumpDelay <= 0) {
            yvel = -10;
            jumpDelay = 10;
        }
        
        setY(getY() + (int)yvel);
    }
    
    /**
    * Metodo que modifica la posicion en y del objeto
    * @param click es si <code>se dio click</code> al applet
    */
    public void update2(boolean click) {
        yvel -= gravedad;

        if (jumpDelay > 0)
            jumpDelay--;

        if (click && jumpDelay <= 0) {
            yvel = 10;
            jumpDelay = -10;
        }
        
        setY(getY() + (int)yvel);
    }
    
    /**
    * Metodo modificador usado para cambiar la velocidad en y del objeto 
    * @param yvel es la <code>velocidad en y</code> del objeto.
    */
    public void setYVel(double yvel) {
        this.yvel = yvel;
    }
    
    /**
    * Metodo de acceso que regresa la velocidad en y del objeto 
    * @return yvel es la <code>velocidad en y</code> del objeto.
    */
    public double getYVel() {
        return yvel;
    }
    
    /**
    * Metodo modificador usado para cambiar la gravedad del objeto 
    * @param gravedad es la <code>gravedad</code> del objeto.
    */
    public void setGravedad(double gravedad) {
        this.gravedad = gravedad;
    }
    
    /**
    * Metodo de acceso que regresa la gravedad del objeto 
    * @return gravedad es la <code>gravedad</code> del objeto.
    */
    public double getGravedad() {
        return gravedad;
    }
    
    /**
    * Metodo modificador usado para cambiar el delay del objeto 
    * @param jumpDelay es el <code>delay</code> del objeto.
    */
    public void setDelay(int jumpDelay) {
        this.jumpDelay = jumpDelay;
    }
    
    /**
    * Metodo de acceso que regresa el delay del objeto 
    * @return jumpDelay es el <code>delay</code> del objeto.
    */
    public int getDelay() {
        return jumpDelay;
    }
}
