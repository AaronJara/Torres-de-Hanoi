package torresdehanoi.simulacion;

import java.awt.BorderLayout;
import torresdehanoi.Principal;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Clase VentanaSimulación
 * @author Andrés Matta, Aaron Jara.
 */
public class VentanaSimulacion extends JFrame implements ActionListener, ChangeListener, KeyListener, MouseListener{

    private JLabel labelNroDiscos; //Label para los discos
    private JLabel labelInformacion; // Label de la barra de información
    private JSpinner spinnerNroDiscos; // JSpinner para la cantidad de discos
    private JButton botonIniciar; // Botón para iniciar la partida
    private JButton botonSalir; // Botón para volver al menú
    private Dibujo dibujo; // clase Dibujo en la que se crea el JPanel principal
    Principal p1 = new Principal(); //Ventana de menú

    /**
     *Constructor de la clase
     */
    public VentanaSimulacion() {
        super("Torres de Hanoi");
        this.addKeyListener(this); // Se agrega el KeyListener a la clase
        this.addMouseListener(this); // Se agrega el MouseListener a la clase
        configurarVentana(); // Llamada al método de configuración de la ventana.
        inicializarComponentes(); // Inicia los componentes principales del JFrame.
        this.setVisible(true); // Hace Visible al JFrame
        this.setTitle("Torres de Hanoi / Simular"); // Setea el título de la parte superior del JFrame
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/logoApp.png")); // Setea el icono de la parte superior del Jframe
        setIconImage(icon); 
    }
    
    /**
     *Override para hacer que el JFrame se vuelva foco, y así poder captar 
     * las acciones del teclado y del mouse
     * @return
     */
    @Override
    public boolean isFocusable(){
        return true; // Convierte el JFrame en focus.
    }
    
    private void configurarVentana() {
        this.setSize(680, 400); // Tamaño de JFrame
        this.setVisible(true); // Lo vuelve visible
        this.setLayout(new BorderLayout()); // Llama al Layout
        this.setLocationRelativeTo(null); // Mantiene el JFrame centrado
        this.setResizable(false); // No permite cambiar el tamaño
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); 
    }

    private void inicializarComponentes() {
        //Barra de la parte inferior
        JPanel panelInferior = new JPanel();
        //Label que muestra cantidad de discos
        labelNroDiscos = new JLabel("Numero De Fichas");
        panelInferior.add(labelNroDiscos);
        //Spinner para cambiar la cantidad de discos
        spinnerNroDiscos = new JSpinner(new SpinnerNumberModel(8, 1, 8, 1));
        spinnerNroDiscos.addChangeListener(this);
        panelInferior.add(spinnerNroDiscos);
        //Bonton para iniciar la simulacion
        botonIniciar = new JButton("Iniciar");
        botonIniciar.addActionListener(this);
        panelInferior.add(botonIniciar);
        //Label que aparece al ganar la partida
        labelInformacion = new JLabel("RESOLUCIÓN COMPLETADA");
        labelInformacion.setForeground(Color.red);
        labelInformacion.setVisible(false);
        panelInferior.add(labelInformacion);
        //Boton para volver al menú principal
        botonSalir = new JButton("Volver");
        botonSalir.setActionCommand("Salir");
        botonSalir.addActionListener(this);
        panelInferior.add(botonSalir);
        // Añade el JPanel principal e inferior al JFrame
        add(panelInferior, BorderLayout.SOUTH);
        dibujo = new Dibujo(8, this);
        add(dibujo, BorderLayout.CENTER);


    }

    /**
     * Este método se carga al presionar el bóton de Iniciar o Pausar en su defecto
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Salir")){
            int salir = JOptionPane.showConfirmDialog(null, "¿Realmente desea salir de la simulación?", "Confirmar salida", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (salir==0) {
                this.setVisible(false);
                p1.setVisible(true);
            }
        }else{
            if (botonIniciar.getText().equals("Pausar")) {
            dibujo.pausarAnimacion();
            botonIniciar.setText("Continuar");
            } else {
            if (botonIniciar.getText().equals("Iniciar De Nuevo")) {
                dibujo = new Dibujo(Integer.parseInt(spinnerNroDiscos.getValue().toString()), this);
                add(dibujo, BorderLayout.CENTER);
                botonIniciar.setText("Iniciar");
                labelInformacion.setVisible(false);
                this.setVisible(true);
            } else {
                dibujo.iniciarAnimacion();
                botonIniciar.setText("Pausar");
            }
            }
        }
        
    }

    /**
     *Se carga este método cuando se cabmia el número de pilas.
     * @param e
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        dibujo.pausarAnimacion();
        botonIniciar.setText("Iniciar");
        labelInformacion.setVisible(false);
        dibujo = new Dibujo(Integer.parseInt(spinnerNroDiscos.getValue().toString()), this);
        add(dibujo, BorderLayout.CENTER);
        this.setVisible(true);
    }

    /**
     * Este método se invoca al completar la simulación
     */
    public void resolucionCompletada() {
        botonIniciar.setText("Iniciar De Nuevo");
        labelInformacion.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * En este método se generan las entradas del teclado por medio 
     * de la clase KeyListener.
     * @param ke
     */
    @Override
    public void keyPressed(KeyEvent ke) {
    if(ke.getKeyCode()==KeyEvent.VK_ENTER){
        if (botonIniciar.getText().equals("Pausar")) {
            dibujo.pausarAnimacion();
            botonIniciar.setText("Continuar");
            } else {
            if (botonIniciar.getText().equals("Iniciar De Nuevo")) {
                dibujo = new Dibujo(Integer.parseInt(spinnerNroDiscos.getValue().toString()), this);
                add(dibujo, BorderLayout.CENTER);
                botonIniciar.setText("Iniciar");
                labelInformacion.setVisible(false);
                this.setVisible(true);
            } else {
                dibujo.iniciarAnimacion();
                botonIniciar.setText("Pausar");
            }
            }
    }
    if(ke.getKeyCode()==KeyEvent.VK_ESCAPE){
	int salir = JOptionPane.showConfirmDialog(null, "¿Realmente desea salir de la simulación?", "Confirmar salida", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (salir==0) {
                this.setVisible(false);
                p1.setVisible(true);
            }
	}
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseClicked(MouseEvent me) {
    //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent me) {
    //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Permite que el JFrame pueda interactuar con el KeyListener.
     * @param me
     */
    @Override
    public void mouseEntered(MouseEvent me) {
        this.isFocused();
    }

    @Override
    public void mouseExited(MouseEvent me) {
    //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
