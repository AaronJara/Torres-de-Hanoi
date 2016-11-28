/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package torresdehanoi;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import torresdehanoi.juego.Juego;
import torresdehanoi.simulacion.Logica;

/**
 *
 * @author andre
 */
public class Principal extends javax.swing.JFrame {

    /**
     * Creates new form Principal
     * 
     */
    
    Dimension tamano = Toolkit.getDefaultToolkit().getScreenSize();
    
    public Principal() {
        
        initComponents();
        this.setTitle("Torres de Hanoi");
        this.setSize((int) 485, (int) 400);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jRadioButton1 = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem8 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem12 = new javax.swing.JMenuItem();

        jRadioButton1.setText("jRadioButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(840, 840));
        getContentPane().setLayout(null);

        jButton1.setText("SIMULACION");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(10, 100, 110, 67);

        jButton2.setText("JUEGO");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(10, 20, 110, 67);

        jButton3.setText("SALIR");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3);
        jButton3.setBounds(389, 302, 81, 31);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/fondo.jpg"))); // NOI18N
        jLabel1.setText("jLabel1");
        jLabel1.setToolTipText("");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 480, 350);

        jMenu3.setText("Archivo");

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

        jMenuItem8.setText("Ayuda");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem8);
        jMenu3.add(jSeparator1);

        jMenuItem12.setText("Salir");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem12);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Juego j1 = new Juego();
        this.setVisible(false);
        j1.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String args[] = new String[0];
        Logica l1 = new Logica();
        this.setVisible(false);
        l1.main(args);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // 
        JOptionPane.showMessageDialog(null, "En su origen el juego se realizaba a mano. Consta de una tablilla a la que se\ncolocan tres varillas verticales y paralelas entre si.\nLos escalones de la torre tienen tamaños de menor a mayor. \nEl jugador elige con cuantos escalones quiere jugar y los coloca ordenados de mayor \na menor insertados en una de las varillas.\nA partir de ahora tiene que montar la torre en cualquiera de las otras dos varillas \nutilizando las siguientes reglas:\n 1.-Sólo se puede mover un disco cada vez.\n 2.-Un disco de mayor tamaño no puede descansar sobre uno más pequeño que él mismo.\n 3.-Sólo puedes desplazar el disco que se encuentre arriba en cada varilla. \n\nLeer el menu Ayuda para mas información sobre como funciona este programa \nHacer juegos de prueba para familiarizarse con el funcionamiento del programa", "Reglas del juego", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // Historia del juego
        JOptionPane.showMessageDialog(null, "Historia del juego Torres de Hanoi \n\nLa información que viene más abajo es sacada de Internet.\nParece que era un juego conocido en la India aunque fué difundido en Europa a partir de 1883\npor el matemático francés Edouard Lucas.\nEstá asociado a leyendas como que en un templo indú había un juego que tenía una torre de 64\nescalones en una de sus varillas y había que pasar esa torre a cualquiera de las otras dos \ncumpliendo las reglas (ver Menú Reglas del Juego).\nEn el caso de 64 escalones se ha calculado que haciendo una jugada cada segundo se terminaría \nen algo menos de 585 mil millones de años (si no tienes ningún fallo ).", "Historia y origen", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // Reglas del juego:
        JOptionPane.showMessageDialog(null, "Empieza mostrando una torre de dos escalones. Podemos cambiar el número de escalones\nen el Menu Escalones.Según el número de escalones con que queramos jugar el juego \nse hace más complejo.\n\nHay que montar la torre que aparece en la primera base en la base dos o en la base tres.\nEl programa funciona pulsando la base de donde queremos mover un escalón y la base\na donde queremos llevarlo. Pulsamos luego sobre el botón Mover para realizar\nel desplazamiento, ver Menú  Reglas del Juego\n\nLo que vemos en pantalla:\n-Jugadas posibles: según el número de Escalones el programa indica el número mínimo \n  de jugadas  posibles.\n-Jugadas realizadas: Acumula jugadas realizadas en las que el jugador consigue\n  desplazar un escalón desde una base a otra. Ayuda a valorar la habilidad del jugador\n-Aciertos: número de veces que el jugador logra montar los escalones de una torre\n  correctamente en una base distinta de la '1'. El programa da un mensaje de\n  felicitación y muestra una cara sonriente\n-Avisos: información sobre el número de avisos que se producen cuando realizamos \n  una jugada incorrecta, también muestra una cara triste:\n  a) Pulsamos una base vacía\n  b) Pulsamos solo una base llena (con uno o mas escalones) y luego la tecla Jugar\n  c) Pulsamos la misma base (llena) dos veces seguidas y luego Jugar\n  d) Si intentamos poner un escalón grande encima de uno pequeño\n  e) Si pulsamos la tecla Mover sin indicar 'desde' y 'hasta' donde desplazamos el escalón \n-Tiempo: Cronómetro que da el tiempo que se tarda en realizar cada torre, se pone a '0'\n  al empezar un juego nuevo\n-Esquina superior derecha: Vemos las bases de torre pulsadas. En caso de error parpadean\n y se muestran en rojo durante unos segundos (suena campanita al finalizar).", "Ayuda", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    // End of variables declaration//GEN-END:variables
}
