/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package torresdehanoi;

/**
 *
 * @author andre
 */
public class Logica {
  
  public static void Resolver(int n, int origen,  int auxiliar, int destino){
  if(n==1)
  System.out.println("mover disco de " + origen + " a " + destino);
  else{
     Resolver(n-1, origen, destino, auxiliar);
     System.out.println("mover disco de "+ origen + " a " + destino);
     Resolver(n-1, auxiliar, origen, destino);
   }
}
  
}
