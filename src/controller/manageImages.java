/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import main.Main;

/**
 *
 * @author david
 */
public class manageImages {
    
    String[] dataLocale = Main.getInstance().getLocale();
    Locale language = new Locale(dataLocale[1]);
    String path = dataLocale[0];
    ResourceBundle messages = ResourceBundle.getBundle(path, language);
    
    
    public static void deleteImages(String nombre, String tipo, String subTipo, int maxContador) {

        for (int i = 1; i < maxContador; i++) {

            try {
                File deletePNT2 = new File(nombre + "-" + tipo + "-" + subTipo + "_" + i + ".pnt");
                deletePNT2.delete();

            } catch (Exception e) {
            }
        }
    }
    
     void printImages(String Play) {
        String fondo1 = Frame.getInstance().getpassFondo();
        WritableImage wim;

        if (fondo1.contains(messages.getString("Attack")) || fondo1.contains(messages.getString("Defense"))) {
            wim = new WritableImage(514, 445);
        } else {
            wim = new WritableImage(514, 462);
        }

        Main.getInstance().getCanvas().snapshot(null, wim);
        File fileToSave = new File(Play);
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(wim, null), "png", fileToSave);
        } catch (Exception s) {

        }
    }
}
