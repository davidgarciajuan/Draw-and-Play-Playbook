package controller;

import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Main;
import model.jugada;

/**
 * Dialog to edit details of a play.
 *
 * @author David
 */
public class PlayEditDialogController {

    @FXML
    private TextField NameField;
    @FXML
    private TextField TypeField;
    @FXML
    private TextField subTypeField;
    @FXML
    private ComboBox cb;

    private Stage dialogStage;
    private jugada play;
    private boolean okClicked = false;
    private ObservableList<jugada> playData;
    private String newcourt;

    //Datos del locale
    String[] dataLocale = Main.getInstance().getLocale();
    Locale language = new Locale(dataLocale[1]);
    String path = dataLocale[0];
    ResourceBundle messages = ResourceBundle.getBundle(path, language);

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

        playData = Frame.getInstance().getPlayData();
        cb.setItems(FXCollections.observableArrayList(messages.getString("Full"), messages.getString("Attack"), messages.getString("Defense")));

    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the play to be edited in the dialog.
     *
     * @param play
     */
    public void setPlay(jugada play) {
        this.play = play;

        NameField.setText(play.getNombreJugada());
        TypeField.setText(play.getTipoJugada());
        subTypeField.setText(play.getSubtipoJugada());
        newcourt = play.getPistaJugada();

    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {

        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            play.setNombreJugada(NameField.getText());
            play.setTipoJugada(TypeField.getText());
            play.setSubtipoJugada(subTypeField.getText());
            play.setPistaJugada(newcourt);
            play.setCoordenadasJugada("");
            play.setDescripcionJugada("");

            okClicked = true;

            dialogStage.close();
        }

    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    @FXML
    private void courtPressed() {
        cb.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                newcourt = t1;

            }
        });

    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        int error2 = 2;
        if (NameField.getText() == null || NameField.getText().length() == 0) {
            errorMessage += messages.getString("Novalidname");
        }
        if (TypeField.getText() == null || TypeField.getText().length() == 0) {
            errorMessage += messages.getString("Novalidtype");
        }
        if (subTypeField.getText() == null || subTypeField.getText().length() == 0) {
            errorMessage += messages.getString("Novalidsubtype");
        }
        if (newcourt == null || newcourt.length() == 0) {
            errorMessage += messages.getString("Novalidcourt");
        }
        for (jugada Play : playData) {

            if (Play.getNombreJugada().equals(NameField.getText())
                    & Play.getTipoJugada().equals(TypeField.getText())
                    & Play.getSubtipoJugada().equals(subTypeField.getText())) {
                // Show the error message.
                Alert alert2 = new Alert(AlertType.ERROR);
                alert2.initOwner(dialogStage);
                alert2.setTitle(messages.getString("InvalidFields"));
                alert2.setHeaderText(messages.getString("correctInvalid"));
                alert2.setContentText(messages.getString("playExist"));

                alert2.showAndWait();
                return false;
            }

        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle(messages.getString("InvalidFields"));
            alert.setHeaderText(messages.getString("correctInvalid"));
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }

}
