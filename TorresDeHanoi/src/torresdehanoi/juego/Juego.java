/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TorresHanoi.java
 *
 * @author Andrés Matta, Aaron Jara
 *
 */
package torresdehanoi.juego;

import java.awt.event.*;
import java.awt.*;
import javax.swing.Timer; // se utiliza en este caso para llevar el tiempo del juego.
import javax.swing.JOptionPane;  // emitir ventanas emergentes.
import torresdehanoi.Principal; // Se importa la ventana principal

public class Juego extends javax.swing.JFrame {
    //Dimension del la pantalla
    
    Dimension tamano = Toolkit.getDefaultToolkit().getScreenSize();
    //Cantidad de tiempo transcurrido por partida.
    int minutos = 0, segundos = 0, horas = 0;
    int encender=0;
    Timer cronometro;
    Timer parpadeo;
    //matriz que contiene la posicion de las pilas
    static int[][] matrizTorres = new int[11][11];
    //Posiciones de cada una de las torres
    int posicionTorre1 = 30;
    int posicionTorre2 = 280;
    int posicionTorre3 = 530;
    int i;
    int j;
    int b=0;
    //Cantidad de escalones
    static int escalones = 3;
    static int torre = 4;
    // Posiciones inicial y final
    public int desde = 0;
    public int hasta = 0;
    int vertical = 200;
    //contador para el bucle
    int contador; 
    // Almacenan valores para reponer
    int torreDesde;
    int guardaIDesde;
    int guardaDesde;
    int torreHasta;
    int guardaIHasta;
    int guardaHasta;
    //Cantidad de movimientos minimos posibles
    int jugadasPosibles;
    //Jugadas realzadas en la partida
    int jugadasRealizadas;
    //Cantidad de aciertos totales
    int aciertos;
    //Avisos emitidos
    int avisos;
    // coordenadas para colocar escalones
    int x;
    int y;
    int a;// situa verticalmete la torre que movemos (define para cada escalon una posicion)
    //int b;// define una posicion en horizontal para que cada escalon quede centrado en su torre ==> no hay manera
    double pixelVertical;
    //double pixelHorizontal;

    public Juego() {
        /*
         * El tamaño del jFrame se ha definido en 'propiedades' a 800x600 por
         * eso no se puede cambiar por códígo aunque podemos utilzar el codigo
         * siguiente para centrarlo en la pantalla si la resolución es mayor
         */
     
        this.setSize((int) tamano.getWidth() / 2, (int) tamano.getHeight() / 2);
        //this.setLocation((int) tamano.getWidth() / 2 - this.getWidth() / 2, (int) tamano.getHeight() / 2 - this.getHeight() / 2);

        initComponents();
        this.getContentPane().setBackground(Color.white);
        setResizable(false);// false--> no permite arrastrar margenes
        setLocationRelativeTo(null);// Coloca el JFrame en el centro de la pantalla.
        
        new Cronometro(); //llama a la clase cronometro
        //new Parpadeo();
        //setDefaultCloseOperation(0);//cerrar con aspa X (esquina superior derecha)

        /*
         * Por defecto, al arrancar el programa muestra en la primera torre solo
         * dos escalones.
         *
         */
        jugadasPosibles = (2 * 2) - 1;
        jLabel4.setText("" + jugadasPosibles);
        jLabel7.setVisible(false);// oculta la jlabel con cara alegre
        jLabel22.setVisible(false);// oculta la jlabel con cara triste

        //lamaa a estos metodos
        mostrarBaseTorres();
        llenarMatriz();

    }

    /*
     * Es interesante porque es un ejemplo de como funciona Timer
     * (javax.swing.Timer). Timer es una clase capaz de llamar al mÉtodo que
     * este definido con 'actionPerformed' colocado dentro de la clae definida
     * con 'ActionListener'. A cada intervalo fijo de tiempo especificado en
     * milisegundos 'new Timer(1000, this)', en décimas (10) de segundos o en
     * segundos (1) se ejecuta una acción. En el caso de esta clase cada segundo
     * imprime el cronometro, es decir lo actualiza cada segundo.
     *
     * También se puede decir que 'ActionListener' es una interfaz que sirve
     * para que el mismo jpanel (o jframe) escuche el evento timer
     */
    private class Cronometro implements ActionListener { //clase
        //Timer da milisegundos, pero aqui hacemos que funcione como cronómetro

        private Timer cronometro = new Timer(1000, this);
        //private Timer parpadeo = new Timer(1000, this);
        //metodos de la clase
        public Cronometro() {
            cronometro.start();// empieza desde 0 (o pone a cero)
        }

        public void actionPerformed(ActionEvent e) {
            segundos++;
            if (segundos == 59) {
                segundos = 0;
                minutos++;
            }
            if (minutos == 59) {
                minutos = 0;
                horas++;
            }
            jLabel21.setText(horas + ":" + minutos + ":" + segundos);

            if (segundos == 10000000) {

                Toolkit.getDefaultToolkit().beep();
                cronometro.stop();
            }
            encender++;
        }
        
    }
    /*
     * En caso de movimiento de escalones erróneo entra en funcionamiento esta
     * clase tras pulsar Mover.
     * 
     * En la esquina superior derecha hay dos jLabel que indican las bases de
     * las torres pulsadas. Si se produce un error los numeros cambian a color
     * rojo y parpadean durante varios segundos. Despues vuelven a su color 
     * el negro. 
     */
private class Parpadeo implements ActionListener { //clase
        
        private Timer parpadeo = new Timer(10, this);
        //metodos de la clase
        public Parpadeo() {
            b = 0;// evita problemas de parpadeo si i >300 por errores consecutivos
            encender=0;// evita errores de que encender pueda ser mayor de 100
            parpadeo.start();// empieza desde 0 (o pone a cero)
        }

        public void actionPerformed(ActionEvent e) {
            //b=0;
            encender++;
            b++;
            jLabel8.setForeground(Color.red);//texto en rojo
            jLabel9.setForeground(Color.red);
            //System.out.println("texto en rojo"+b);  
            if (encender == 50) { //menor de 25 mas rapido, mayor mas lento
                 System.out.println("encender= "+encender); 
                jLabel8.setVisible(false);
                jLabel9.setVisible(false);

            } else {
                if (encender == 100) {
                    System.out.println("encender= "+encender); 
                    jLabel8.setVisible(true);
                    jLabel9.setVisible(true);
                    encender = 0;
                }
            }

            if (b == 300) {
                Toolkit.getDefaultToolkit().beep();
                parpadeo.stop();
                jLabel8.setVisible(true);
                jLabel9.setVisible(true);
                jLabel8.setForeground(Color.black);
                jLabel9.setForeground(Color.black);
            }
            System.out.println("encender= "+encender);   
            
        }
        
    }

    /*
     * Este método carga la última posición de la columna de cada torre con el
     * valor del número de escalones. Permite detectar el primer lugar
     * disponible de cada torre para colocar un nuevo escalón.
     *
     * y tambien:
     *
     * Este método tiene dos bucles for con el que se representan las torres.
     * Llena la matriz con el valor 'i' (escalones) en la primera torre y '0' en
     * las otras dos torres
     *
     * Mas adelante, en otros métodos, el programa analizará si existe '0' o un
     * número para decidir si el movimiento pedido cumple las reglas del juego o
     * hemos cometido un error.
     */
    public void llenarMatriz() {
        matrizTorres[escalones][1] = escalones;//podria utilizarse cualquier otro dato
        matrizTorres[escalones][2] = escalones;
        matrizTorres[escalones][3] = escalones;
        for (i = 1; i < escalones; i++) {
            for (j = 1; j < torre; j++) {
                matrizTorres[i][j] = 0;
                matrizTorres[i][1] = i;
            }
        }
        mostrarMatriz();
    }

    /*
     * Este módulo solo imprime en pantalla en modo texto el contenido de la
     * matrizTorres[i][j] Permite hacer un seguimiento en modo texto de los
     * movimientos realizados y que escalones tenemos en cada torre.
     */
    public void mostrarMatriz() {
        for (i = 1; i < escalones; i++) {
            for (j = 1; j < torre; j++) {
                System.out.print("               " + matrizTorres[i][j]);
            }
            System.out.println();
        }
        System.out.println("          ==========      ==========       =========");
        System.out.println("               1               2               3");
        System.out.println("          ------------------------------------------");
        modoGrafico();
    }

    public void mostrarBaseTorres() {
        //1.- Coodenada vertical 2.- Coordinada horizontal 3.- ancho del objeto 4.-Alto del objeto 
        jButton1.setBounds(30, 279, 220, 80);// 
        jButton2.setBounds(280, 279, 220, 80);// Botones: eje x e y para situarlos (280, 360,)
        jButton3.setBounds(530, 279, 220, 80);// y largo y alto (220, 60)
    }

    /*
     * Este método permite que se muestren en pantalla los escalones de la torre
     * (base) inicial y las distintas posiciones de los escalones según los
     * movemos por las demás torres. Solo los muestra en pantalla. Las reglas
     * del juego se controlan desde otros métodos.
     *
     */
    public void modoGrafico() {

        if (escalones == 3) {// por defecto carga una torre de dos escalones

            y = 218;// los coloca verticalmente en esa posición (x=30 (posicionTorre1));

            jLabel12.setVisible(false);
            jLabel13.setVisible(false);
            jLabel14.setVisible(false);
            jLabel15.setVisible(false);
            jLabel16.setVisible(false);
            jLabel17.setVisible(false);
        }


        /*
         * Aqui definimos distintas coordenadas para posicionar en pantalla los
         * escalones de cada torre según el número de escalones a representar.
         * Las variables utilizadas son: y--> ya la hemos visto en el comentario
         * anterior. x--> define el segundo punto de la coordenada según el
         * escalón se sitúe en la primera base, en la segunda o en la tercera.
         * b--> cada escalón tiene su propio valor b, permite situarlos
         * verticalmente uno encima de otro. En una resolución como la definida
         * para este programa: (800,600) la presentación en pantala del juego
         * queda bastante aceptable.
         */

        for (i = 1; i < escalones; i++) {
            for (j = 1; j < torre; j++) {
                if (j == 1) {
                    x = posicionTorre1;//Pimera base o torre
                }
                if (j == 2) {
                    x = posicionTorre2;//Segunda base
                }
                if (j == 3) {
                    x = posicionTorre3;//Tercera base
                }

                if (i == 1) {
                    a = 0;
                }
                if (i == 2) {
                    a = 30;
                }
                if (i == 3) {
                    a = 60;
                }
                if (i == 4) {
                    a = 90;
                }
                if (i == 5) {
                    a = 120;
                }
                if (i == 6) {
                    a = 150;
                }
                if (i == 7) {
                    a = 180;
                }
                if (i == 8) {
                    a = 210;
                }
                /*
                 * Imprime los escalones de la primera torre.
                 *
                 * x e y --> coordenadas de posición de cada escalón.
                 *
                 * 220, 30 --> largo y alto de cada escalón.
                 *
                 * En realidad son jLabel iguales en tamaño, pero dentro de cada
                 * uno tiene un icono centrado de distinto tamaño para
                 * representar cada escalón
                 */
                if (matrizTorres[i][j] == 1) {jLabel10.setBounds(x, y + a, 220, 30);}
                if (matrizTorres[i][j] == 2) {jLabel11.setBounds(x, y + a, 220, 30);}
                if (matrizTorres[i][j] == 3) {jLabel12.setBounds(x, y + a, 220, 30);}
                if (matrizTorres[i][j] == 4) {jLabel13.setBounds(x, y + a, 220, 30);}
                if (matrizTorres[i][j] == 5) {jLabel14.setBounds(x, y + a, 220, 30);}
                if (matrizTorres[i][j] == 6) {jLabel15.setBounds(x, y + a, 220, 30);}
                if (matrizTorres[i][j] == 7) {jLabel16.setBounds(x, y + a, 220, 30);}
                if (matrizTorres[i][j] == 8) {jLabel17.setBounds(x, y + a, 220, 30);}
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton5 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();

        jButton5.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jButton5.setText("Mover");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Torres de Hanoi / Jugar ");
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(800, 600));
        getContentPane().setLayout(null);

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jButton1.setText("1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(60, 280, 210, 70);

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jButton2.setText("2");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(290, 280, 210, 70);

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jButton3.setText("3");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3);
        jButton3.setBounds(520, 280, 210, 70);

        jButton4.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jButton4.setText("Mover");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4);
        jButton4.setBounds(70, 370, 160, 110);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Movimientos mínimos");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(240, 400, 180, 30);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Movimientos realizadas");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(240, 430, 220, 20);

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Partidas ganadas");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(240, 500, 190, 30);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("0");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(450, 400, 40, 20);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("0");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(440, 430, 50, 20);

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 102, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("0");
        jLabel6.setOpaque(true);
        getContentPane().add(jLabel6);
        jLabel6.setBounds(410, 500, 80, 30);

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/sucess.png"))); // NOI18N
        getContentPane().add(jLabel7);
        jLabel7.setBounds(560, 370, 200, 190);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        getContentPane().add(jLabel8);
        jLabel8.setBounds(650, 10, 50, 50);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        getContentPane().add(jLabel9);
        jLabel9.setBounds(710, 10, 50, 50);

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/1.png"))); // NOI18N
        jLabel10.setOpaque(true);
        getContentPane().add(jLabel10);
        jLabel10.setBounds(120, 40, 50, 30);

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/2.png"))); // NOI18N
        jLabel11.setOpaque(true);
        getContentPane().add(jLabel11);
        jLabel11.setBounds(120, 70, 60, 30);

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/3.png"))); // NOI18N
        jLabel12.setOpaque(true);
        getContentPane().add(jLabel12);
        jLabel12.setBounds(110, 100, 80, 30);

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/4.png"))); // NOI18N
        jLabel13.setOpaque(true);
        getContentPane().add(jLabel13);
        jLabel13.setBounds(100, 130, 100, 30);

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/5.png"))); // NOI18N
        jLabel14.setOpaque(true);
        getContentPane().add(jLabel14);
        jLabel14.setBounds(90, 160, 130, 30);

        jLabel15.setBackground(new java.awt.Color(255, 255, 255));
        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/6.png"))); // NOI18N
        jLabel15.setOpaque(true);
        getContentPane().add(jLabel15);
        jLabel15.setBounds(80, 190, 160, 30);

        jLabel16.setBackground(new java.awt.Color(255, 255, 255));
        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/7.png"))); // NOI18N
        jLabel16.setOpaque(true);
        getContentPane().add(jLabel16);
        jLabel16.setBounds(70, 220, 180, 30);

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/8.png"))); // NOI18N
        jLabel17.setOpaque(true);
        getContentPane().add(jLabel17);
        jLabel17.setBounds(60, 250, 200, 30);

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel18.setText("Avisos");
        getContentPane().add(jLabel18);
        jLabel18.setBounds(240, 450, 160, 30);

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("0");
        getContentPane().add(jLabel19);
        jLabel19.setBounds(440, 450, 50, 30);

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel20.setText("Tiempo en segundos");
        getContentPane().add(jLabel20);
        jLabel20.setBounds(240, 370, 170, 30);

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("0");
        getContentPane().add(jLabel21);
        jLabel21.setBounds(370, 370, 120, 30);

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/fail.png"))); // NOI18N
        getContentPane().add(jLabel22);
        jLabel22.setBounds(560, 370, 210, 180);

        jButton6.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton6.setText("Volver");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton6);
        jButton6.setBounds(70, 500, 160, 40);

        jMenu4.setText("Escalones");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_2, java.awt.event.InputEvent.SHIFT_MASK));
        jMenuItem1.setText("2 Escalones");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_3, java.awt.event.InputEvent.SHIFT_MASK));
        jMenuItem2.setText("3 Escalones");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_4, java.awt.event.InputEvent.SHIFT_MASK));
        jMenuItem3.setText("4 Escalones");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem3);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_5, java.awt.event.InputEvent.SHIFT_MASK));
        jMenuItem4.setText("5 Escalones");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem4);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_6, java.awt.event.InputEvent.SHIFT_MASK));
        jMenuItem5.setText("6 Escalones");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem5);

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_7, java.awt.event.InputEvent.SHIFT_MASK));
        jMenuItem6.setText("7 Escalones");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem6);

        jMenuItem7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_8, java.awt.event.InputEvent.SHIFT_MASK));
        jMenuItem7.setText("8 escalones");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem7);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    /*
     * El programa tiene tres botones situados uno en la base de cada torre.
     * Tienen un mismo cóodigo. Según el botón que pulsemos el programa mueve
     * 'desde' una torre 'hasta' otra torre un escalón.
     *
     * Puede darse el caso que pulsemos como 'desde' sobre una base de torre en
     * la que en ese momento no hay ningún escalón, el programa lo detecta a
     * través del método 'torreVacia'. Da un mensaje y restaura valores previos
     * de las variables que han intervenido.
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if (desde == 0) {
            desde = 1;
            jLabel8.setText("" + desde);
        } else {
            if (hasta == 0) {
                hasta = 1;
                jLabel9.setText("" + hasta);
            }
        }
        torreDesdeVacia();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        if (desde == 0) {
            desde = 2;
            jLabel8.setText("" + desde);
        } else {
            if (hasta == 0) {
                hasta = 2;
                jLabel9.setText("" + hasta);
            }
        }
        torreDesdeVacia();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if (desde == 0) {
            desde = 3;
            jLabel8.setText("" + desde);
        } else {
            if (hasta == 0) {
                hasta = 3;
                jLabel9.setText("" + hasta);
            }
        }
        torreDesdeVacia();
    }//GEN-LAST:event_jButton3ActionPerformed

    /*
     * Una vez que el programa sabe 'desde' que base y 'hasta' que base movemos
     * un escalón debemos pulsar el boton Jugar para realizar ese movimiento.
     *
     * El error que hay que evitar es pulsar solo sobre la base 'desde' pues
     * necesitamos también el dato de la base 'hasta' para realizar correctamnte
     * el movimiento. De esto se encarga el metodo 'torreHastaVacia'.
     *
     * El método 'colocarEscalones' se encarga de cambiar de sitio el escalón de
     * la base 'desde' a la base 'hasta' que se han elegido
     *
     * El método mostrarMatriz coloca en sus nuevas posiciones los números que
     * represetan los escalones en la matriz 'matrizTorres[i][j]'
     *
     * La variable jugadasRealizadas aumenta en uno las jugadas realizadas y lo
     * imprime en jLabel5.
     *
     * Si se pulsa el botón de una sola base o el botón 'Jugar' sin seleccionar
     * antes desde y hasta da un aviso y aumenta esa variable en 1, y se igualan
     * a '0' los valores 'desde' y 'hasta' y se borran los recogidos en las
     * jLabel 8 y 9.
     *
     * El módulo 'felicitacion' da un mensaje de felicitación al terminar de
     * montar la torre en cualquiera de las otras dos bases.
     */
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        torreHastaVacia();
        jugadasRealizadas = jugadasRealizadas + 1;
        jLabel5.setText("" + jugadasRealizadas);
        colocarEscalones();
        mostrarMatriz();
        desde = 0;
        jLabel8.setText("");
        hasta = 0;
        jLabel9.setText("");
        felicitacion();
    }//GEN-LAST:event_jButton4ActionPerformed

    /*
     * Con la configuración actual el programa puede mostrar juegos con torres
     * hasta de 8 escalones. Se selecciona el número de escalones a través de la
     * opción menu Escalones que tiene 7 opciones. Todas las opciones tienen la
     * misma estructura. Solo cambia el número de escalones, la formula para
     * calcular el número de jugadas posibles y el número de jLabel que pueden
     * verse.
     */
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        y = 218;
        jLabel10.setVisible(true);
        jLabel11.setVisible(true);
        jLabel12.setVisible(false);
        jLabel13.setVisible(false);
        jLabel14.setVisible(false);
        jLabel15.setVisible(false);
        jLabel16.setVisible(false);
        jLabel17.setVisible(false);
        escalones = 3;
        minutos = 0;
        segundos = 0;
        horas = 0;
        llenarMatriz();
        jugadasPosibles = (int) Math.pow(2, 2) - 1;//potencia de un numero
        jLabel4.setText("" + jugadasPosibles);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        y = 188;
        jLabel10.setVisible(true);
        jLabel11.setVisible(true);
        jLabel12.setVisible(true);
        jLabel13.setVisible(false);
        jLabel14.setVisible(false);
        jLabel15.setVisible(false);
        jLabel16.setVisible(false);
        jLabel17.setVisible(false);
        escalones = 4;
        minutos = 0;
        segundos = 0;
        horas = 0;
        llenarMatriz();
        jugadasPosibles = (int) Math.pow(2, 3) - 1;//potencia de un numero
        jLabel4.setText("" + jugadasPosibles);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        y = 158;
        jLabel10.setVisible(true);
        jLabel11.setVisible(true);
        jLabel12.setVisible(true);
        jLabel13.setVisible(true);
        jLabel14.setVisible(false);
        jLabel15.setVisible(false);
        jLabel16.setVisible(false);
        jLabel17.setVisible(false);
        escalones = 5;
        minutos = 0;
        segundos = 0;
        horas = 0;
        llenarMatriz();
        jugadasPosibles = (int) Math.pow(2, 4) - 1;//potencia de un numero
        jLabel4.setText("" + jugadasPosibles);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        y = 128;
        jLabel10.setVisible(true);
        jLabel11.setVisible(true);
        jLabel12.setVisible(true);
        jLabel13.setVisible(true);
        jLabel14.setVisible(true);
        jLabel15.setVisible(false);
        jLabel16.setVisible(false);
        jLabel17.setVisible(false);
        minutos = 0;
        segundos = 0;
        horas = 0;
        escalones = 6;
        llenarMatriz();
        jugadasPosibles = (int) Math.pow(2, 5) - 1;//potencia de un numero
        jLabel4.setText("" + jugadasPosibles);
}//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        y = 98;
        jLabel10.setVisible(true);
        jLabel11.setVisible(true);
        jLabel12.setVisible(true);
        jLabel13.setVisible(true);
        jLabel14.setVisible(true);
        jLabel15.setVisible(true);
        jLabel16.setVisible(false);
        jLabel17.setVisible(false);
        minutos = 0;
        segundos = 0;
        horas = 0;
        escalones = 7;
        llenarMatriz();
        jugadasPosibles = (int) Math.pow(2, 6) - 1;//potencia de un numero
        jLabel4.setText("" + jugadasPosibles);
}//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        y = 68;
        jLabel10.setVisible(true);
        jLabel11.setVisible(true);
        jLabel12.setVisible(true);
        jLabel13.setVisible(true);
        jLabel14.setVisible(true);
        jLabel15.setVisible(true);
        jLabel16.setVisible(true);
        jLabel17.setVisible(false);
        minutos = 0;
        segundos = 0;
        horas = 0;
        escalones = 8;
        llenarMatriz();
        jugadasPosibles = (int) Math.pow(2, 7) - 1;//potencia de un numero
        jLabel4.setText("" + jugadasPosibles);
}//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
        y = 38;
        jLabel10.setVisible(true);
        jLabel11.setVisible(true);
        jLabel12.setVisible(true);
        jLabel13.setVisible(true);
        jLabel14.setVisible(true);
        jLabel15.setVisible(true);
        jLabel16.setVisible(true);
        jLabel17.setVisible(true);
        minutos = 0;
        segundos = 0;
        horas = 0;
        escalones = 9;
        llenarMatriz();
        jugadasPosibles = (int) Math.pow(2, 8) - 1;//potencia de un numero
        jLabel4.setText("" + jugadasPosibles);
}//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
     Principal p1 = new Principal();
    int salir = JOptionPane.showConfirmDialog(null, "¿Realmente desea salir del juego?", "Confirmar salida", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (salir==0) {
            this.setVisible(false);
            p1.setVisible(true);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    /*
     * El metodo 'colocarEscalones' es el que realiza el cambio de escalones
     * entre las tres torres. Toma el escalón de la base que pulsemos primero
     * (desde) y lo deposita en el boton que pulsemos segundo (hasta).
     *
     * Primero se hace en modo texto, podemos ver como los escalones que
     * aparecen en la primera torre van pasando a la segunda o tercera según
     * indiquemos pulsando los botones.
     *
     * En modo gráfico se lee el contenido de 'matrizTorres[i][j]' y se coloca
     * cada escalón en su lugar correspondiente.
     */
    public void colocarEscalones() {

        /*
         * DESDE Eligiendo un escalon: Pulsando el botón de la base de una torre
         * indicamos 'desde' que torre queremos mover el escalón. El este código
         * evalúa en la matriz la posición con el primer escalón disponible para
         * mover.
         */

        for (i = 1; i < escalones; i++) {
            if (matrizTorres[i][desde] != 0) {
                /*
                 * Si en la torre 'desde' el contenido de la matriz es '0' lee
                 * la siguiente posicion. La primera jugadas 'desde' será
                 * siempre la base o torre '1', pero despues,en las siguientes
                 * jugadas, según la base que pulseemos 'desde' podrá ser 1,2 o
                 * 3.
                 *
                 * Si el contenido de la matriz 'matrizTorres[i][desde]' no es
                 * '0' pasa contenido a la variable 'torreDesde' y luego
                 * adjudica el valor '0' al contenido de la matriz
                 * 'matrizTorres[i][desde]' para quitar ,borrar, el escalón que
                 * queremos mover
                 *
                 * Para que la acción se realice solo una vez, sobre el primer
                 * escalón que encuentra lleno, utilizo el siguiente código: un
                 * contador y una condicion if que solo funciona cuando el
                 * contador es igual a 1.
                 *
                 * Cuando el bucle termina el contador se pone de nuevo a cero
                 * esperando la siguiente pulsacion desde.
                 *
                 * El valor de i es el primero que encuentra la matriz
                 * 'escalones[i][desde]' donde hay un escalón (un número desde 1
                 * a 'escalones') siempre ordenados de menor a mayor aunque no
                 * necesariamente de manera consecutiva si ya esta avanzado el
                 * juego.
                 *
                 */

                contador = contador + 1;
                if (contador == 1) {
                    torreDesde = matrizTorres[i][desde];
                    guardaIDesde = i;// imprescindible para retornar valores en caso de poner escalón grande encima de pequeño
                    matrizTorres[i][desde] = 0;//borramos el escalon
                }
            }
        }

        contador = 0;// ponemos a cero una vez el bucle for anterior se ha ejecutado

        /*
         * HASTA Elegimos en la torre 'hasta' primer escalón vacío (disponible)
         * de la torre o base 'hasta' para colocar él el el escalón seleccionado
         * en 'desde'.
         *
         * Evaluamos en la matriz 'matrizTorres[i][hasta]' si ya hay algún
         * escalón puesto o es el primero que se va a poner para situarlo a
         * continuación. Una vez econtrado donde colocarlo 'escalones[i][hasta]'
         * toma el valor almacenado anteriormente en 'torreDesde'
         *
         * Esta parte llama a dos métodos que evitan: Poner un escalón mas
         * grande que el previo en la torre, envía al método 'escalonGrande' y
         * Pulsar la misma torre para 'desde' y 'hasta', nos envia al método
         * 'mismaTorre'
         *
         */

        for (i = 1; i < escalones; i++) {
            /*
             * Para colocar el escalón en la torre 'hasta' verificamos si en la
             * posicion i+1 hay algún escalón o el número de la variable
             * 'escalones' que se le adjudico en el metodo 'llenarMatriz()'(en
             * realidad podria rellenarse con cualquier otro dato). Esto permite
             * colocar en la posición i el nuevo escalón.
             *
             * Nota: Recordaras que definiomos en la matriz la ultima posicion
             * como: //matrizTorres[escalones][1] = escalones;
             * //matrizTorres[escalones][2] = escalones;
             * //matrizTorres[escalones][3] = escalones;
             *
             * Esta posición permanece con ese valor durante todo el juego y
             * ayuda a determinar que a partir de ellos se situan los demás
             * escalones. Cuando matrizTorres[i + 1][hasta] detecta que en esa
             * posicion (i+1) hay un dato, aunque la torre, 'oficialmente', aún
             * no tiene ningún escalón.
             */

            if (matrizTorres[i + 1][hasta] != 0) {
                /*
                 * Aqui utilizamos de nuevo un contador y una condicion con el
                 * contador que funciona de manera similar al del bucle que
                 * utilice en el codigo 'DESDE'. Conseguimos conservar el valor
                 * de 'i' que junto con el de 'hasta' hacen que podamos
                 * adjudicar a las posiciones que representa la matriz
                 * 'matrizTorres[i][hasta]' el valor del escalón que queremos
                 * depositar en la base 'hasta' que hasta ahora esta almacenado
                 * en la variable 'torreDesde'.
                 *
                 */

                escalonGrande();
                contador = contador + 1;

                if (contador == 1) {
                    mismaTorre();
                    guardaIHasta = i; //imprescindible para retornar valores en caso de poner escalon grande encima de pequeño
                    matrizTorres[i][hasta] = torreDesde;
                }
            }
        }

        contador = 0;
    }

    /*
     * A partir de aqui vienen los métodos que indican algún error en la
     * pulsación de las bases o en las normas del juego y el mensaje de
     * felicitación al completar la torre correctamente
     */

    /*
     * Si por error se pulsa dos veces la misma base
     */
    public void mismaTorre() {
        if (desde == hasta) {
            jLabel22.setVisible(true);//Se muestra una cara triste
            new Parpadeo();
            Object[] opciones = {"Aceptar"};
            JOptionPane.showOptionDialog(null, "            AVISO-1\n\n Intentas mover un escalón \n desde la torre " + desde + " hasta la torre " + hasta, "ATENCIÓN: Se ha producido un error",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                    null, opciones, opciones[0]);

            jugadasRealizadas = jugadasRealizadas - 1;// descuenta uno: interesa que solo aumente si mueve escalón
            jLabel5.setText("" + jugadasRealizadas);
            jLabel22.setVisible(false);//Se borra la cara triste
            avisos = avisos + 1;//Esta variable recoge el número de errores de pulsación cometidos
            jLabel19.setText("" + avisos);
            //desde=0;
            jLabel8.setText("");
            //hasta=0;
            jLabel9.setText("");
        }
    }

    /*
     * Si se intenta poner un escalón grande encima de uno pequeño.
     *
     * Interesante destacar las variables 'guardaIDesde' y 'guardaIHasta'
     * generadas en el metodo 'generarEscalon' que representan en que posición
     * 'i' se encontraba el escalon en la torre 'desde' y restaurar su valor en
     * la torre 'hasta' respectivamente.
     *
     * Así, las variables 'torreDesde' y 'torreHasta' restauran sus respectivos
     * valores a la que tenian antes de la jugada en las posiciones de la matriz
     * 'escalones[guardaIDesde][desde]' y 'escalones[guardaIHasta][hasta]'
     *
     */
    public void escalonGrande() {

        if (matrizTorres[i - 1][hasta] > matrizTorres[i][hasta]) {
            jLabel22.setVisible(true);//Se muestra una cara triste
            new Parpadeo();
            Object[] opciones = {"Aceptar"};
            JOptionPane.showOptionDialog(null, "            AVISO\n\n El nuevo escalón es más grande que el que ya hay \n en la torre donde desea ponerlo", "ATENCIÓN: Se ha producido un error",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                    null, opciones, opciones[0]);
            
            matrizTorres[guardaIDesde][desde] = torreDesde; //Permite devolver a la torre 'desde' el contenido que tenia
            matrizTorres[guardaIHasta][hasta] = torreHasta; //Permite devolver a la torre 'hasta' el contenido que tenia en esa posicion la matriz
            jLabel22.setVisible(false);// borra la cara triste
            jugadasRealizadas = jugadasRealizadas - 1;// descuenta uno: interesa que solo aumente si mueve escalón
            jLabel5.setText("" + jugadasRealizadas);
            avisos = avisos + 1;
            jLabel19.setText("" + avisos);
        }
    }
//d
    /*
     * Si pulsamos primero (desde) el botón de una torre que está vacia
     */
    public void torreDesdeVacia() {
        if (matrizTorres[escalones - 1][desde] == 0) {
            jLabel22.setVisible(true);//Se muestra una cara triste
            new Parpadeo();
            Object[] opciones = {"Aceptar"};
            JOptionPane.showOptionDialog(null, "            AVISO-2\n\n Intentas mover un escalón \n desde la torre " + desde + " hasta la torre " + hasta, "ATENCIÓN: Se ha producido un error",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                    null, opciones, opciones[0]);

            avisos = avisos + 1;
            jLabel19.setText("" + avisos);
            desde = 0;
            jLabel8.setText("");
            jLabel22.setVisible(false);// borra la cara triste
        }
    }

    /*
     * Este método solo se utiliza para el caso que el jugador pulse la torre
     * desde y no pulse la torre hasta. Al pulsar el botón Jugar se entra en
     * este método que evalúa si el valor de 'hasta' existe, si no da mensaje
     */
    public void torreHastaVacia() {
        if (matrizTorres[i][hasta] == 0) {
            jLabel22.setVisible(true);//Se muestra una cara triste
            new Parpadeo();
            Object[] opciones = {"Aceptar"};
            JOptionPane.showOptionDialog(null, "            AVISO-3\n\n Intentas mover un escalón \n desde la torre " + desde + " hasta la torre " + hasta, "ATENCIÓN: JUGAR Se ha producido un error",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                    null, opciones, opciones[0]);

            jugadasRealizadas = jugadasRealizadas - 1;// descuenta uno: interesa que solo aumente su muev escalon
            jLabel5.setText("" + jugadasRealizadas);
            avisos = avisos + 1;
            jLabel19.setText("" + avisos);
            desde = 0;
            jLabel8.setText("");
            jLabel22.setVisible(false);// borra la cara triste
        }
    }

    /*
     * Mensaje de felicitación: Detecta que el escalón superior de la torre
     * mantada en la base dos o en la base tres es igual a '1', el escalon mas
     * pequeño de la torre.
     *
     * Al pulsar 'Aceptar' pone a '0' todas las variables
     *
     */
    public void felicitacion() {

        if (matrizTorres[1][2] == 1 || matrizTorres[1][3] == 1) {
            if (matrizTorres[escalones - 1][1] == 0) {
                aciertos = aciertos + 1;
                jLabel6.setText("" + aciertos);
                jLabel7.setVisible(true);// muestra una cara alegre
                Object[] opciones = {"Aceptar"};
                JOptionPane.showOptionDialog(null, "================================\n==                                                                 ==\n==                    FELICIDADES                     ==\n==                                                                 ==\n================================", "ATENCIÓN: Has teminado la torre",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                        null, opciones, opciones[0]);

                minutos = 0;
                segundos = 0;
                horas = 0;
                jugadasRealizadas = 0;
                jLabel5.setText("" + jugadasRealizadas);
                avisos = 0;
                jLabel19.setText("" + avisos);
                jLabel7.setVisible(false);//oculta cara con sonrisa
                llenarMatriz();// no tocar: esta linea monta de nuevo la torre en la primera base despues de montarla en la dos o la tres
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                Juego j1 = new Juego();
                j1.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    // End of variables declaration//GEN-END:variables
}
