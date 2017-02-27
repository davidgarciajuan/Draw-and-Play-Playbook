package main;

import model.jugada;
import controller.Sketch;
import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import Draws.Shape;

import java.util.Stack;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import controller.PlanController;
import controller.PlayEditDialogController;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import model.conexionBD;

public class Main extends Application {

    private static Main instance;
    public Stage primaryStage;
    private BorderPane root;
    private conexionBD conexion;
    
    ResourceBundle messages;
    String language;

    private Node canvas;
    private Stack<Shape> shapes = new Stack<>();

    private ObservableList<jugada> playData = FXCollections.observableArrayList();

    File file = new File("Playbook.xml");

    public static void main(String[] args) {
        launch(args);
    }

    public static Main getInstance() {
        return instance;
    }

    public static void setInstance(Main instance) {
        Main.instance = instance;
    }

    public Main() {

    }

    public ObservableList<jugada> getPlayData() {

        return playData;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        setInstance(this);
        this.setPrimaryStage(primaryStage);
        primaryStage.setMaximized(true);

        Locale castellano = new Locale("es");
        Locale ingles = new Locale("en");
        language = System.getProperty("user.language");

        Locale currentLocale = new Locale(language);

        messages = ResourceBundle.getBundle("Locales.MessagesBundle", currentLocale);
        
        initViews();
        primaryStage.getIcons().add(new Image("/Images/Logo.png"));
       
       
        primaryStage.setTitle("Draw and Play Playbook");
        primaryStage.show();

    }

    private void initViews() throws Exception {
        setRoot(FXMLLoader.load(getClass().getResource("/view/frame.fxml"),messages));
        
        getPrimaryStage().setScene(new Scene(getRoot(), 1300, 1000));
        setCanvas(FXMLLoader.load(getClass().getResource("/view/sketch.fxml"),messages));
        FXMLLoader.load(getClass().getResource("/view/PlayEditDialog.fxml"),messages);
        FXMLLoader.load(getClass().getResource("/view/Plan.fxml"),messages);
         conexion = new conexionBD();
        conexion.establecerConexion();

        Sketch.getInstance().setMainWidth(Double.valueOf(514));
        Sketch.getInstance().setMainHeight(Double.valueOf(445));

        Main.getInstance().getRoot().setCenter(Sketch.getInstance().getCanvas());

    }

    public boolean showPlayEditDialog(jugada play) {
        try {

            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/PlayEditDialog.fxml"));
            loader.setResources(messages);
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(messages.getString("Play"));

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
       
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the dialog icon.
             dialogStage.getIcons().add(new Image("file:resources/images/Logo.png"));
            // Set the play into the controller.
            PlayEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPlay(play);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            return false;
        }
    }

    public void showPlanDialog() {
        try {

            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/Plan.fxml"));
            loader.setResources(messages);
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(messages.getString("Plan"));
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the play into the controller.
            PlanController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Set the dialog icon.
             dialogStage.getIcons().add(new Image("file:resources/images/Logo.png"));
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Stack<Shape> getShapes() {
        return shapes;
    }

    public BorderPane getRoot() {
        return root;
    }

    public void setRoot(BorderPane root) {
        this.root = root;
    }

    public Node getCanvas() {
        return canvas;
    }

    public void setCanvas(Node canvas) {
        this.canvas = canvas;
    }

     public String[] getLocale (){
        String[] datosLocale = new  String[2];
        datosLocale[0] = messages.getBaseBundleName();
        datosLocale[1] = language;
       
        return datosLocale;
    }
    
}
