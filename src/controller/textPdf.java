/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *Asigna el texto a los pdf
 * @author david
 */
public class textPdf {
     public static String textPdf(int contadorPDF, String textoCargado, int maxContador) {
        String mostrar;
        String textoComparador = textoCargado;

        String cadena = "%$&" + contadorPDF;
        
        if (maxContador == 1) {

            try {
                mostrar = textoComparador.substring(textoComparador.indexOf(cadena) + 4);
            } catch (Exception e) {
                mostrar = "";
            }

        } else if (!textoComparador.contains(cadena)) {
            mostrar = "";
  
        } else {

            try {
                mostrar = textoComparador.substring(textoComparador.indexOf(cadena) + 4, textoComparador.indexOf("%$&", textoComparador.indexOf(cadena) + 4));
            } catch (Exception e) {
                mostrar = textoComparador.substring(textoComparador.indexOf(cadena) + 4);
            }

        }
        return mostrar;

    }
}
