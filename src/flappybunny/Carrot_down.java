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
 * @author Ovidio
 */
public class Carrot_down extends Base {
    private int velX;
    
    public Carrot_down(int x, int y) {
        super(x,y);
        Image carrot = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/images/FlappyBunny_CarrotDOWN.png"));
        anim = new Animacion();
        anim.sumaCuadro(carrot, 100);
        velX = 10;
    }
    
     public void update() {
        setX(getX() - velX);
    }
    
    public void setVelX(int velX) {
        this.velX = velX;
    }
    
    public int getVelX() {
        return velX;
    }
}
