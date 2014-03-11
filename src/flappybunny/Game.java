/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package flappybunny;

/**
 *
 * @author Ovidio
 */
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class Game extends JFrame implements Runnable, MouseListener {
    private static final long serialVersionUID = 1L;
    //variables
    private boolean pausa;
    private boolean start;
    private boolean click;
    private boolean gameover;
    private int gap;        // gap entre carrots
    private int score;
    private Bunny ponejito;     // Objeto de la clase bunny
    private Carrot_up cu;
    private Carrot_down cd;
    private Carrot_up []carrotUp;
    private Carrot_down []carrotDown;
    private Image background;   // Imagen de background
    private Image dbImage;	// Imagen a proyectar
    private Graphics dbg;	// Objeto grafico
    
    //Variables control de tiempo de animacion
    private long tiempoActual;
    private long tiempoInicial;
    
    public Game() {
        init();
        start();
    }
        
        public void init() {
            setSize(500, 500);
            pausa = false;
            start = false;
            click = false;
            gameover = false;
            score = 0;
            gap = 300;
            background = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/images/background.png"));
            // Inicializar Ponejito
            ponejito = new Bunny(0, 0);
            int x = getWidth()/2;
            int y = getHeight()/2 + ponejito.getAlto()/2;
            ponejito.setX(x);
            ponejito.setY(y);
            // Inicializar los tubos
            cu = new Carrot_up(0, 0);
            cd = new Carrot_down(0, 0);
            carrotUp = new Carrot_up[5];
            carrotDown = new Carrot_down[5];
            cd.setX(getWidth());
            cu.setX(getWidth());
            cd.setY((int)(Math.random()*-1*cd.getAlto())+100);
            cu.setY(cd.getY()+cd.getAlto()+gap);
            setBackground(Color.white);
            addMouseListener(this);
        }
    
        /** 
	 * Metodo <I>start</I> sobrescrito de la clase <code>Applet</code>.<P>
        * En este metodo se crea e inicializa el hilo
        * para la animacion este metodo es llamado despues del init o 
        * cuando el usuario visita otra pagina y luego regresa a la pagina
        * en donde esta este <code>Applet</code>
        * 
        */
	public void start () {
		// Declaras un hilo
		Thread th = new Thread (this);
		// Empieza el hilo
		th.start ();
	}
        
        /**
	 * Metodo <I>stop</I> sobrescrito de la clase <code>Applet</code>.<P>
	 * En este metodo se pueden tomar acciones para cuando se termina
	 * de usar el <code>Applet</code>. Usualmente cuando el usuario sale de la pagina
	 * en donde esta este <code>Applet</code>.
	 */
	public void stop() {
       
	}
        
        /**
	 * Metodo <I>destroy</I> sobrescrito de la clase <code>Applet</code>.<P>
	 * En este metodo se toman las acciones necesarias para cuando
	 * el <code>Applet</code> ya no va a ser usado. Usualmente cuando el usuario
	 * cierra el navegador.
	 */
	public void destroy() {
	    
	}
        
        /** 
        * Metodo <I>run</I> sobrescrito de la clase <code>Thread</code>.<P>
        * En este metodo se ejecuta el hilo, es un ciclo indefinido donde se incrementa
        * la posicion en x o y dependiendo de la direccion, finalmente 
        * se repinta el <code>Applet</code> y luego manda a dormir el hilo.
        * 
        */
        public void run () {
            //Guarda el tiempo actual del sistema
            tiempoActual = System.currentTimeMillis();
            while (true) {
                actualiza();
                checaColision();
                repaint();    // Se actualiza el <code>Applet</code> repintando el contenido.
                try	{
                        // El thread se duerme.
                        Thread.sleep (200);
                }
                catch (InterruptedException ex)	{
                        System.out.println("Error en " + ex.toString());
                }
            }
        }
        
        /**
         * Metodo <I>actualiza</I>
         * Este metodo actualiza a los personajes en el applet en sus movimientos
        */
        public void actualiza() {
            //Determina el tiempo que ha transcurrido desde que el Applet inicio su ejecución
            long tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;
            //Guarda el tiempo actual
            tiempoActual += tiempoTranscurrido;
            
            //actualiza movimiento del ponejito
            ponejito.update(click);
            
            //actualiza movimiento de los carrots
            cu.update();
            cd.update();
        }
        
        /**
        * Metodo <I>checaColision</I>
        * Este metodo checa la colision entre los personajes,
        * la colision de los malos con la parte inferior del applet y
        * la  colision del bueno con los extremos del applet
        */
        public void checaColision() {
            if(ponejito.intersecta(cu) || ponejito.intersecta(cd) || ponejito.getAlto()+ponejito.getY() > getHeight()) {
                gameover = true;
            }
        }
        
        /**
	 * Metodo <I>paint</I> sobrescrito de la clase <code>Applet</code>,
	 * heredado de la clase Container.<P>
	 * En este metodo se dibuja la imagen con la posicion actualizada,
	 * ademas que cuando la imagen es cargada te despliega una advertencia.
	 * @param g es el <code>objeto grafico</code> usado para dibujar.
	 */
        
        public void paint(Graphics g) {
                //Inicializa el DoubleBuffer
                if (dbImage == null){
                    dbImage = createImage(this.getSize().width, this.getSize().height);
                    dbg = dbImage.getGraphics();
                }
                //Actualiza la imagen de fondo
                dbg.setColor(getBackground());
                dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);
                //Actualiza el Foreground
                dbg.setColor(getForeground());
                paint1(dbg);
                //Dibuja la imagen actualizada
                g.drawImage(dbImage, 0, 0, this);
        }
        
        public void paint1 (Graphics g) {
            g.drawImage(background, 0, 0, this);
            if (ponejito!=null && cd!=null && cu!=null) {
                g.drawImage(ponejito.getImagenI(), ponejito.getX(), ponejito.getY(), this);
                g.drawImage(cd.getImagenI(), cd.getX(), cd.getY(), this);
                g.drawImage(cu.getImagenI(), cu.getX(), cu.getY(), this);
            } else {
                //Da un mensaje mientras se carga el dibujo	
                g.drawString("No se cargo la imagen..",20,20);
            }
        }
        
         /**
	 * Metodo mouseClicked sobrescrito de la interface MouseListener.
	 * En este metodo maneja el evento que se genera al hacer click con el mouse
	 * sobre algun componente.
	 * e es el evento generado al hacer click con el mouse.
	 */
        public void mouseClicked(MouseEvent e) {
           
        }

        /**
	 * Metodo mousePressed sobrescrito de la interface MouseListener.
	 * En este metodo maneja el evento que se genera al presionar un botÃ³n
	 * del mouse sobre algun componente.
	 * e es el evento generado al presionar un botÃ³n del mouse sobre algun componente.
	 */
        public void mousePressed(MouseEvent e) {
             click = true;
        }

        /**
	 * Metodo mouseReleased sobrescrito de la interface MouseListener.
	 * En este metodo maneja el evento que se genera al soltar un botÃ³n
	 * del mouse sobre algun componente.
	 * e es el evento generado al soltar un botÃ³n del mouse sobre algun componente.
	 */
        public void mouseReleased(MouseEvent e) {
            click = false;
        }

        /**
	 * Metodo mouseEntered sobrescrito de la interface MouseListener.
	 * En este metodo maneja el evento que se genera cuando el mouse
	 * entra en algun componente.
	 * e es el evento generado cuando el mouse entra en algun componente.
	 */
        public void mouseEntered(MouseEvent e) {

        }

        /**
	 * Metodo mouseExited sobrescrito de la interface MouseListener.
	 * En este metodo maneja el evento que se genera cuando el mouse
	 * sale de algun componente.
	 * e es el evento generado cuando el mouse sale de algun componente.
	 */
        public void mouseExited(MouseEvent e) {

        }
}
