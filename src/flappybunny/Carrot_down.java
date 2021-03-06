/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package flappybunny;

import java.awt.Image;
import java.awt.Toolkit;

/**
 *
 * @author Ovidio Villarreal && Graciela Garcia
 */
public class Carrot_down extends Base {
    private int velX;
    
    public Carrot_down(int x, int y) {
        super(x,y);
        Image carrot = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/images/FlappyBunny_CarrotUP.png"));
        anim = new Animacion();
        anim.sumaCuadro(carrot, 100);
        velX = 10;
    }
    
    /**
     * Metodo que modifica la posicion en x del objeto
     */
     public void update() {
        setX(getX() - velX);
    }
    
     /**
    * Metodo modificador usado para cambiar la velocidad en x del objeto 
    * @param velX es la <code>velocidad en x</code> del objeto.
    */
    public void setVelX(int velX) {
        this.velX = velX;
    }
    
    /**
    * Metodo de acceso que regresa la velocidad en x del objeto 
    * @return velX es la <code>velocidad en x</code> del objeto.
    */
    public int getVelX() {
        return velX;
    }
}
