/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package torresdehanoi;

import java.util.Scanner;
import static torresdehanoi.Logica.Resolver;

/**
 *
 * @author andre
 */
public class TorresDeHanoi {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       Scanner sc = new Scanner(System.in);
        int n;
        System.out.println("Numero de discos: ");
        n = sc.nextInt();
        Resolver(n,1,2,3);  //1:origen  2:auxiliar 3:destino
    }
    
}
