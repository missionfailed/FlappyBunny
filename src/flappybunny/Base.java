/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package flappybunny;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 *
 * @author Ovidio Villarreal && Graciela Garcia
 */
public class Base {
    private ArrayList cuadros;
    private int x;
    private int y;
    protected Animacion anim;
    
    public Base (int x, int y) {
        this.x = x;
        this.y = y;
    }
    
        /**
	 * Metodo modificador usado para cambiar la posicion en x del objeto 
	 * @param x es la <code>posicion en x</code> del objeto.
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Metodo de acceso que regresa la posicion en x del objeto 
	 * @return x es la <code>posicion en x</code> del objeto.
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Metodo modificador usado para cambiar la posicion en y del objeto 
	 * @param y es la <code>posicion en y</code> del objeto.
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Metodo de acceso que regresa la posicion en y del objeto 
	 * @return y es la <code>posicion en y</code> del objeto.
	 */
	public int getY() {
		return y;
	}
        /**
	 * Metodo de acceso que regresa el ancho del icono 
	 * @return un objeto de la clase <code>ImageIcon</code> que es el ancho del icono.
	 */
	public int getAncho() {
		return (new ImageIcon(anim.getImagen())).getIconWidth();
	}
	
	/**
	 * Metodo de acceso que regresa el alto del icono 
	 * @return un objeto de la clase <code>ImageIcon</code> que es el alto del icono.
	 */
	public int getAlto() {
		return (new ImageIcon(anim.getImagen())).getIconHeight();
	}
        
	/**
	 * Metodo de acceso que regresa la imagen del icono 
	 * @return un objeto de la clase <code>Image</code> que es la imagen del icono.
	 */
	public Image getImagenI() {
		return (new ImageIcon(anim.getImagen())).getImage();
	}
	
	/**
	 * Metodo de acceso que regresa un nuevo rectangulo
	 * @return un objeto de la clase <code>Rectangle</code> que es el perimetro 
	 * del rectangulo
	 */
	public Rectangle getPerimetro(){
		return new Rectangle(getX(),getY(),getAncho(),getAlto());
	}
	
	/**
	 * Checa si el objeto <code>Animal</code> intersecta a otro <code>Animal</code>
	 *
	 * @return un valor boleano <code>true</code> si lo intersecta <code>false</code>
	 * en caso contrario
	 */
	public boolean intersecta(Base obj){
		return getPerimetro().intersects(obj.getPerimetro());
	}
        
        /**
         * Metodo de actualizacion de imagen de los sprites
         * @param tiempo 
         */
        public void actualiza(long tiempo) {
            anim.actualiza(tiempo);
        }
}
