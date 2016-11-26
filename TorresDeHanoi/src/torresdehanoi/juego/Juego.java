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
import javax.swing.Timer;
import javax.swing.JOptionPane;  // el programa usa JOptionPane

public class Juego extends javax.swing.JFrame {

    Dimension tamano = Toolkit.getDefaultToolkit().getScreenSize();
    int minutos = 0, segundos = 0, horas = 0;
    int encender=0;
    Timer cronometro;
    Timer parpadeo;
    static int[][] matrizTorres = new int[11][11];// matriz
    int posicionTorre1 = 30;
    int posicionTorre2 = 280;
    int posicionTorre3 = 530;
    int i;
    int j;
    int b=0;
    static int escalones = 3;
    static int torre = 4;// 1,2,3
    // tore desde y hasta
    public int desde = 0;
    public int hasta = 0;
    int vertical = 200;
    int contador; //solo hasta 1
    // Almacenan valores para reponer
    int torreDesde;
    int guardaIDesde;
    int guardaDesde;
    int torreHasta;
    int guardaIHasta;
    int guardaHasta;
    int jugadasPosibles;//almacena resultado de formula matematica
    // Contadores
    int jugadasRealizadas;
    int aciertos;
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

        setResizable(false);// false--> no permite arrastrar margenes
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
     * Posiblemente pueda simplificarse con un algoritmo recursivo
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
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem11 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Juego Torres de Hanoi versión julio 2012 ");
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
        jButton4.setBounds(60, 380, 160, 150);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Jugadas posibles");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(240, 380, 180, 29);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setText("Jugadas realizadas");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(240, 410, 220, 29);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setText("Aciertos");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(240, 440, 190, 29);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("0");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(480, 380, 40, 20);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("0");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(470, 420, 50, 20);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 102, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("0");
        jLabel6.setOpaque(true);
        getContentPane().add(jLabel6);
        jLabel6.setBounds(440, 444, 80, 30);

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/sonrisa1p.png"))); // NOI18N
        getContentPane().add(jLabel7);
        jLabel7.setBounds(570, 360, 200, 190);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        getContentPane().add(jLabel8);
        jLabel8.setBounds(650, 10, 50, 50);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        getContentPane().add(jLabel9);
        jLabel9.setBounds(710, 10, 50, 50);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/escalon1a.gif"))); // NOI18N
        jLabel10.setOpaque(true);
        getContentPane().add(jLabel10);
        jLabel10.setBounds(10, 30, 50, 30);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/escalon2a.gif"))); // NOI18N
        jLabel11.setOpaque(true);
        getContentPane().add(jLabel11);
        jLabel11.setBounds(10, 60, 60, 30);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/escalon3a.gif"))); // NOI18N
        jLabel12.setOpaque(true);
        getContentPane().add(jLabel12);
        jLabel12.setBounds(10, 90, 80, 30);

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/escalon4a.gif"))); // NOI18N
        jLabel13.setOpaque(true);
        getContentPane().add(jLabel13);
        jLabel13.setBounds(10, 120, 100, 30);

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/escalon5c.gif"))); // NOI18N
        jLabel14.setOpaque(true);
        getContentPane().add(jLabel14);
        jLabel14.setBounds(10, 150, 130, 30);

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/escalon6f.gif"))); // NOI18N
        jLabel15.setOpaque(true);
        getContentPane().add(jLabel15);
        jLabel15.setBounds(10, 180, 160, 30);

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/escalon7.gif"))); // NOI18N
        jLabel16.setOpaque(true);
        getContentPane().add(jLabel16);
        jLabel16.setBounds(10, 210, 180, 30);

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/escalon8d.gif"))); // NOI18N
        jLabel17.setOpaque(true);
        getContentPane().add(jLabel17);
        jLabel17.setBounds(10, 240, 200, 30);

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel18.setText("Avisos");
        getContentPane().add(jLabel18);
        jLabel18.setBounds(240, 470, 160, 29);

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("0");
        getContentPane().add(jLabel19);
        jLabel19.setBounds(470, 474, 50, 30);

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel20.setText("Tiempo (seg.)");
        getContentPane().add(jLabel20);
        jLabel20.setBounds(240, 500, 160, 29);

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("0");
        getContentPane().add(jLabel21);
        jLabel21.setBounds(400, 500, 120, 30);

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/sonrisa2p.png"))); // NOI18N
        getContentPane().add(jLabel22);
        jLabel22.setBounds(570, 360, 210, 180);

        jMenu3.setText("Fichero");

        jMenuItem8.setText("Ayuda");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem8);

        jMenuItem9.setText("Reglas del juego");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem9);

        jMenuItem10.setText("Historia y origen");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem10);
        jMenu3.add(jSeparator2);

        jMenuItem11.setText("Acerca de...");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem11);
        jMenu3.add(jSeparator1);

        jMenuItem12.setText("Salir");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem12);

        jMenuItem13.setText("Lo que queda");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem13);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Escalones");

        jMenuItem1.setText("2 Escalones");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem1);

        jMenuItem2.setText("3 Escalones");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem2);

        jMenuItem3.setText("4 Escalones");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem3);

        jMenuItem4.setText("5 Escalones");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem4);

        jMenuItem5.setText("6 Escalones");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem5);

        jMenuItem6.setText("7 Escalones");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem6);

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

    /*
     * El menu Fichero tiene varias opciones que se recogen a continuación
     */
    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Juego:\n\n Torres de Hanoi\n\nVersion del año 2010\nProgramado por:\nRafael Espada Piña\n\nPrograma realizado en Java\nNecesita máquina virtual java(JVM)", "Créditos", JOptionPane.INFORMATION_MESSAGE);

    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "\nEl juego esta diseñado para su uso en la escuela.Pueden jugar con él desde \nalumnos de infantil hasta ESO o incluso adultos.\n\nEmpieza mostrando una torre de dos escalones. Podemos cambiar el número de escalones\nen el Menu Escalones.Según el número de escalones con que queramos jugar el juego \nse hace más complejo.\n\nHay que montar la torre que aparece en la primera base en la base dos o en la base tres.\nEl programa funciona pulsando la base de donde queremos mover un escalón y la base\na donde queremos llevarlo. Pulsamos luego sobre el botón Mover para realizar\nel desplazamiento, ver Menú  Reglas del Juego\n\nLo que vemos en pantalla:\n-Jugadas posibles: según el número de Escalones el programa indica el número mínimo \n  de jugadas  posibles.\n-Jugadas realizadas: Acumula jugadas realizadas en las que el jugador consigue\n  desplazar un escalón desde una base a otra. Ayuda a valorar la habilidad del jugador\n-Aciertos: número de veces que el jugador logra montar los escalones de una torre\n  correctamente en una base distinta de la '1'. El programa da un mensaje de\n  felicitación y muestra una cara sonriente\n-Avisos: información sobre el número de avisos que se producen cuando realizamos \n  una jugada incorrecta, también muestra una cara triste:\n  a) Pulsamos una base vacía\n  b) Pulsamos solo una base llena (con uno o mas escalones) y luego la tecla Jugar\n  c) Pulsamos la misma base (llena) dos veces seguidas y luego Jugar\n  d) Si intentamos poner un escalón grande encima de uno pequeño\n  e) Si pulsamos la tecla Mover sin indicar 'desde' y 'hasta' donde desplazamos el escalón \n-Tiempo: Cronómetro que da el tiempo que se tarda en realizar cada torre, se pone a '0'\n  al empezar un juego nuevo\n-Esquina superior derecha: Vemos las bases de torre pulsadas. En caso de error parpadean\n y se muestran en rojo durante unos segundos (suena campanita al finalizar).", "Ayuda", JOptionPane.INFORMATION_MESSAGE);

    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "En su origen el juego se realizaba a mano. Consta de una tablilla a la que se\ncolocan tres varillas verticales y paralelas entre si.\nLos escalones de la torre tienen tamaños de menor a mayor. \nEl jugador elige con cuantos escalones quiere jugar y los coloca ordenados de mayor \na menor insertados en una de las varillas.\nA partir de ahora tiene que montar la torre en cualquiera de las otras dos varillas \nutilizando las siguientes reglas:\n 1.-Sólo se puede mover un disco cada vez.\n 2.-Un disco de mayor tamaño no puede descansar sobre uno más pequeño que él mismo.\n 3.-Sólo puedes desplazar el disco que se encuentre arriba en cada varilla. \n\nLeer el menu Ayuda para mas información sobre como funciona este programa \nHacer juegos de prueba para familiarizarse con el funcionamiento del programa", "Reglas del juego", JOptionPane.INFORMATION_MESSAGE);

    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Historia del juego Torres de Hanoi \n\nLa información que viene más abajo es sacada de Internet.\nParece que era un juego conocido en la India aunque fué difundido en Europa a partir de 1883\npor el matemático francés Edouard Lucas.\nEstá asociado a leyendas como que en un templo indú había un juego que tenía una torre de 64\nescalones en una de sus varillas y había que pasar esa torre a cualquiera de las otras dos \ncumpliendo las reglas (ver Menú Reglas del Juego).\nEn el caso de 64 escalones se ha calculado que haciendo una jugada cada segundo se terminaría \nen algo menos de 585 mil millones de años (si no tienes ningún fallo ).", "Historia y origen", JOptionPane.INFORMATION_MESSAGE);

    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "1.- Dar mivimiento a los escalones que desplazamos  \n2.-Quizá quitar avisos y mostrarlos en jLabel más resumidos\n3.-Crear clases nuevas y situar en ellas parte del código (mover, centrar torre...)\n4.-Crear ficheros .gif de gráficos con escalones mas 'aparentes' ", "Lo que queda por hacer", JOptionPane.INFORMATION_MESSAGE);

    }//GEN-LAST:event_jMenuItem13ActionPerformed

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
                new Juego().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
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
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    // End of variables declaration//GEN-END:variables
}
