/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package flappybunny;

/**
 *
 * @author Ovidio Villarreal && Graciela Garcia
 */
import java.awt.*;
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

public class Game extends JFrame implements Runnable, MouseListener, KeyListener {
    private static final long serialVersionUID = 1L;
    //variables
    private boolean pausa;      // variable que checa si esta en pausa el juego
    private boolean start;      // variable que checa si ya empezo el juego
    private boolean click;      // variable que checa si se dio un click en el juego
    private boolean gameover;   // variable que checa si perdio en el juego
    private boolean nivel1;     // variable que checa si se encuentra en el nivel 1
    private boolean nivel2;     // variable que checa si se encuentra en el nivel 2
    private boolean nivel3;     // variable que checa si se encuentra en el nivel 3
    private int espacio;        // espacio entre carrots
    private int gap;            // gap por donde pasa el bunny
    private int score;          //variable que representa el puntaje
    private int highscore;
    private String []arr;
    private Bunny ponejito;     // Objeto de la clase bunny
    private Carrot_up cu;       //Objeto que representa obstaculo arriba
    private Carrot_down cd;     //Objeto que representa obstaculo abajo
    private LinkedList carrotDown;
    private LinkedList carrotUp;
    private Image background1;   // Imagen de background
    private Image background2;   // Imagen de background
    private Image background3;   // Imagen de background
    private Image home;         // Imagen de inicio
    private Image end;          // Imagen de gameover
    private Image dbImage;	// Imagen a proyectar
    private Graphics dbg;	// Objeto grafico
    private SoundClip punto;    // Objeto SoundClip
    private SoundClip fondo;    // Objeto SoundClip
    private Font tr;
    
    //Variables control de tiempo de animacion
    private long tiempoActual;
    private long tiempoInicial;
    
    public Game() {
        init();
        start();
    }
        
        public void init() {
            setSize(600, 800);
            try {
                leeArchivo();
            } catch (IOException ex) {
                System.out.println("Error en " + ex.toString());
            }
            pausa = true;
            start = false;
            click = false;
            gameover = false;
            nivel1 = true;
            nivel2 = false;
            nivel3 = false;
            score = 0;
            espacio = 400;
            gap = 250;
            // Obtener sonidos e imagenes
            tr = new Font("TimesRoman", Font.PLAIN, 40);
            punto = new SoundClip ("/sounds/twink.wav");
            fondo = new SoundClip ("/sounds/NyanNoNyan.wav");
            background1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/images/FlappyBunny_Main.png"));
            background2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/images/FlappyBunny_Main2.gif"));
            background3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/images/FlappyBunny_Main3.gif"));
            home = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/images/FlappyBunny_TitleScreen.png"));
            end = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/images/FlappyBunny_GameOver.png"));
            fondo.setLooping(true);
            fondo.play();
            // Inicializar Ponejito
            ponejito = new Bunny(0, 0);
            int x = 10;
            int y = getHeight()/2 + ponejito.getAlto()/2;
            ponejito.setX(x);
            ponejito.setY(y);
            // Inicializar los carrots
            cu = new Carrot_up(0, 0);
            cd = new Carrot_down(0, 0);
            cd.setX(getWidth());
            cu.setX(getWidth());
            cd.setY((int)(Math.random()*((-1*cd.getAlto())+80))-80);
            cu.setY(cd.getY()+cd.getAlto()+gap);
            carrotUp = new LinkedList();
            carrotDown = new LinkedList();
            carrotUp.add(cu);
            carrotDown.add(cd);
            
            for (int i = 1; i < 10; i++) {
                cu = new Carrot_up(0, 0);
                cd = new Carrot_down(0, 0);
                
                cu.setX(((Carrot_up)carrotUp.get(i-1)).getX() + ((Carrot_up)carrotUp.get(i-1)).getAncho() + espacio);
                cd.setX(((Carrot_down)carrotDown.get(i-1)).getX() + ((Carrot_down)carrotDown.get(i-1)).getAncho() + espacio);
                cd.setY((int)(Math.random()*((-1*cd.getAlto())+80))-80);
                cu.setY(cd.getY()+cd.getAlto()+gap);
                
                carrotUp.add(cu);
                carrotDown.add(cd);
            }
            
            
            
            setBackground(Color.white);
            addMouseListener(this);
            addKeyListener(this);
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
                        Thread.sleep (30);
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
            
            if (start && !pausa && !gameover) {
                //actualiza movimiento del ponejito
                ponejito.update(click);
                //ponejito.update2(click);

                //actualiza movimiento de los carrots
                for (int i = 0; i < carrotUp.size(); i++) {
                    ((Carrot_up)(carrotUp.get(i))).update();
                    ((Carrot_down)(carrotDown.get(i))).update();
                }


                //cu.update();
                //cd.update();
                
                //dependiendo del score aumenta la dificultad
                if (score < 20) {
                    nivel1 = true;
                    nivel2 = false;
                    nivel3 = false;
                } else if (score < 50) {
                    nivel1 = false;
                    nivel2 = true;
                    nivel3 = false;
                } else {
                    nivel1 = false;
                    nivel2 = false;
                    nivel3 = true;
                }
            }
        }
        
        /**
        * Metodo <I>checaColision</I>
        * Este metodo checa la colision entre los personajes,
        * la colision de los malos con la parte inferior del applet y
        * la  colision del bueno con los extremos del applet
        */
        public void checaColision() {
            //colision del ponejito
            for (int i = 0; i < carrotUp.size(); i++) {
                cu = ((Carrot_up)carrotUp.get(i));
                cd = ((Carrot_down)carrotDown.get(i));
                if(ponejito.intersecta(cu) || ponejito.intersecta(cd) || ponejito.getAlto()+ponejito.getY() > getHeight()) {
                    fondo.stop();
                    gameover = true;
                    highscore = (score > highscore)? score:highscore;
                    try {
                        grabaArchivo();
                    } catch (IOException ex) {
                        System.out.println("Error en " + ex.toString());
                    }
                }
                if(ponejito.getY()<=0)
                    ponejito.setY(0);

                //colision de los carrots
                if (cu.getX()+cu.getAncho() < 0 && cd.getX()+cd.getAncho() < 0) {
                    carrotUp.removeFirst();
                    carrotDown.removeFirst();
                    punto.play();
                    score += 1;
                    cd.setX(((Carrot_down)carrotDown.get(carrotDown.size()-1)).getX() + ((Carrot_down)carrotDown.get(carrotDown.size()-1)).getAncho() + espacio);
                    cu.setX(((Carrot_up)carrotUp.get(carrotUp.size()-1)).getX() + ((Carrot_up)carrotUp.get(carrotUp.size()-1)).getAncho() + espacio);
                    cd.setY((int)(Math.random()*((-1*cd.getAlto())+80))-80);
                    cu.setY(cd.getY()+cd.getAlto()+gap);
                    carrotUp.add(cu);
                    carrotDown.add(cd);
                }
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
            if (!start) {
                g.drawImage(home, 0, 0, this);
            } else if (!gameover) {
                if (nivel1)
                    g.drawImage(background1, 0, 0, this);
                if (nivel2)
                    g.drawImage(background2, 0, 0, this);
                if (nivel3)
                    g.drawImage(background3, 0, 0, this);
                if (ponejito!=null && !carrotUp.isEmpty() && !carrotDown.isEmpty()) {
                    for (int i = 0; i < carrotUp.size(); i++) {
                        cu = ((Carrot_up)carrotUp.get(i));
                        cd = ((Carrot_down)carrotDown.get(i));
                        g.drawImage(cd.getImagenI(), cd.getX(), cd.getY(), this);
                        g.drawImage(cu.getImagenI(), cu.getX(), cu.getY(), this);
                    }
                    g.drawImage(ponejito.getImagenI(), ponejito.getX(), ponejito.getY(), this);
                    g.drawString(Integer.toString(score), getWidth()/2, 60);
                } else {
                    //Da un mensaje mientras se carga el dibujo	
                    g.drawString("No se cargo la imagen..",20,20);
                }
            } else {
                g.drawImage(end, 0, 0, this);
                g.setFont(tr); 
                g.drawString(Integer.toString(score), getWidth()/3+60, 280);
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
             if (!start) {
                 start = true;
                 pausa = false;
             }
             
             if (gameover) {
                 init();
             }
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
        
        /**
	 * Metodo <I>keyPressed</I> sobrescrito de la interface <code>KeyListener</code>.<P>
	 * En este metodo maneja el evento que se genera al presionar cualquier la tecla.
	 * @param e es el <code>evento</code> generado al presionar las teclas.
	 */
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_P) {    //Presiono flecha arriba
		    pausa = !pausa;
		} 
        }   
    
         /**
	 * Metodo <I>keyTyped</I> sobrescrito de la interface <code>KeyListener</code>.<P>
	 * En este metodo maneja el evento que se genera al presionar una tecla que no es de accion.
	 * @param e es el <code>evento</code> que se genera en al presionar las teclas.
	 */
        public void keyTyped(KeyEvent e){

        }
    
        /**
	 * Metodo <I>keyReleased</I> sobrescrito de la interface <code>KeyListener</code>.<P>
	 * En este metodo maneja el evento que se genera al soltar la tecla presionada.
	 * @param e es el <code>evento</code> que se genera en al soltar las teclas.
	 */
        public void keyReleased(KeyEvent e){

        }
    
        /**
        * Metodo que lee a informacion de un archivo y lo agrega a un vector.
        *
        * @throws IOException
        */
        public void leeArchivo() throws IOException {
                                                          
                BufferedReader fileIn;
                try {
                        fileIn = new BufferedReader(new FileReader("highscore.txt"));
                } catch (FileNotFoundException e){
                        File data = new File("highscore.txt");
                        PrintWriter fileOut = new PrintWriter(data);
                        fileOut.println(""+0);
                        fileOut.close();
                        fileIn = new BufferedReader(new FileReader("highscore.txt"));
                }
                String dato = fileIn.readLine();
                highscore = Integer.parseInt(dato);
                fileIn.close();
        }
        /**
        * Metodo que agrega la informacion del vector al archivo.
        *
        * @throws IOException
        */
        public void grabaArchivo() throws IOException {
                                                          
                PrintWriter fileOut = new PrintWriter(new FileWriter("highscore.txt"));
                fileOut.println(""+highscore);
                fileOut.close();
        }
}
