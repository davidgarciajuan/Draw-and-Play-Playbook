/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import main.Main;

/**
 * Detecta si han habido cambios en la jugada
 * 
 */
public class Changes {

    String[] dataLocale = Main.getInstance().getLocale();
    Locale language = new Locale(dataLocale[1]);
    String path = dataLocale[0];
    ResourceBundle messages = ResourceBundle.getBundle(path, language);

     void checkChanges() {
        if (Frame.getInstance().getChange()) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(messages.getString("SaveChanges"));
            alert.setHeaderText(messages.getString("madeChanges"));
            alert.setContentText(messages.getString("wantChanges"));

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Frame.getInstance().savePressed();
                Frame.getInstance().setChange(false);
            } else {
                Frame.getInstance().setChange(false);
            }

        }

    }
}
