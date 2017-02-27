package controller;

import pdfTemplates.Drillpdf;
import com.itextpdf.text.DocumentException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.Main;
import Draws.Shape;

import java.util.Stack;

import static controller.Sketch.ShapeType;
import java.io.File;

import java.io.IOException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.DataDealer;

import model.conexionBD;
import model.jugada;

public class Frame {

    private static Frame instance;

    // para poder inicializar los datos de la jugada
    private String namePlay = "";
    private String typePlay = "";
    private String subtypePlay = "";
    private String descripcion = "";
    private int maxContador = 1; //Lo inicializa por defecto en 1
    private String nameFondo = "Offense"; // Lo inicializa por defecto en Offense
    private String coordenadas = "";
    private String changeDescripcion = "";

    //Datos del locale
    String[] dataLocale = Main.getInstance().getLocale();
    Locale language = new Locale(dataLocale[1]);
    String path = dataLocale[0];
    ResourceBundle messages = ResourceBundle.getBundle(path, language);

    //Flag para detectar si se ha hecho algún cambio en la jugada
    public boolean Change = false;

    //Datos para la inicialización de la pantalla
    private int contadorsiguiente = 1;
    private ObservableList<jugada> playData;

    private static AnchorPane root;
    private Stage dialogStage;
    private final Stack<Shape> undone = new Stack<>();
    private conexionBD conexion;

    //Declaración de los botones
    @FXML
    private Button offensePlayer;
    @FXML
    private Button offensePlayer1;
    @FXML
    private Button offensePlayer2;
    @FXML
    private Button offensePlayer3;
    @FXML
    private Button offensePlayer4;
    @FXML
    private Button offensePlayer5;
    @FXML
    private Button offensePlayer6;
    @FXML
    private Button offensePlayer7;
    @FXML
    private Button offensePlayer8;
    @FXML
    private Button offensePlayer9;
    @FXML
    private Button defensePlayer;
    @FXML
    private Button defensePlayer1;
    @FXML
    private Button defensePlayer2;
    @FXML
    private Button defensePlayer3;
    @FXML
    private Button defensePlayer4;
    @FXML
    private Button defensePlayer5;
    @FXML
    private Button defensePlayer6;
    @FXML
    private Button defensePlayer7;
    @FXML
    private Button defensePlayer8;
    @FXML
    private Button defensePlayer9;
    @FXML
    private Button ball;
    @FXML
    private Button cone;
    @FXML
    private Button cesta;
    @FXML
    private Button coach;
    @FXML
    private Button basket;
    @FXML
    private Button stair;

    @FXML
    private Button rectangle;
    @FXML
    private Button circle;

    @FXML
    private Button line;
    @FXML
    private Button shootline;
    @FXML
    private Button passline;
    @FXML
    private Button bloqline;
    @FXML
    private Button drillline;

    //Label del número actual del diagrama
    @FXML
    private Label playNumberDiagram;

    //Label del nombre de la jugada marcada
    @FXML
    private Label labelNombre;

    @FXML
    private Button delete;

    @FXML
    private TextArea descripcionArea;

    @FXML
    private TableView<jugada> ListTable;
    @FXML
    private TableColumn<jugada, String> NameColumn;
    @FXML
    private TableColumn<jugada, String> TypeColumn;
    @FXML
    private TableColumn<jugada, String> subTypeColumn;

    //Para poder interactuar con los elementos internos desde otras clases
    public static Frame getInstance() {
        return instance;
    }

    public ObservableList<jugada> getPlayData() {

        return playData;
    }

    //Inicialización del programa, con datos por defecto
    @FXML
    private void initialize() {

        conexion = new conexionBD();
        conexion.establecerConexion();

        playData = FXCollections.observableArrayList();

        jugada.llenarInformacionJugadas(conexion.getConnection(), playData);
        ListTable.setItems(playData);
        instance = this;
        setNULL();
        //Carga el label, transformandolo a String
        playNumberDiagram.setText(Integer.toString(contadorsiguiente));

        //Carga los datos de las columnas
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("nombreJugada"));
        TypeColumn.setCellValueFactory(new PropertyValueFactory<>("tipoJugada"));
        subTypeColumn.setCellValueFactory(new PropertyValueFactory<>("subtipoJugada"));

        //Carga los datos de la jugada seleccionada
        ListTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPlayDetails(newValue));
        ListTable.setFocusTraversable(false);

        conexion.cerrarConexion();

    }
//Coloca la selección a null, para cuando carga algo nuevo

    private void setNULL() {
        rectangle.setDisable(false);
        circle.setDisable(false);
        line.setDisable(false);
        offensePlayer.setDisable(false);
        offensePlayer1.setDisable(false);
        offensePlayer2.setDisable(false);
        offensePlayer3.setDisable(false);
        offensePlayer4.setDisable(false);
        offensePlayer5.setDisable(false);
        offensePlayer6.setDisable(false);
        offensePlayer7.setDisable(false);
        offensePlayer8.setDisable(false);
        offensePlayer9.setDisable(false);
        defensePlayer.setDisable(false);
        defensePlayer1.setDisable(false);
        defensePlayer2.setDisable(false);
        defensePlayer3.setDisable(false);
        defensePlayer4.setDisable(false);
        defensePlayer5.setDisable(false);
        defensePlayer6.setDisable(false);
        defensePlayer7.setDisable(false);
        defensePlayer8.setDisable(false);
        defensePlayer9.setDisable(false);
        ball.setDisable(false);
        cone.setDisable(false);
        cesta.setDisable(false);
        coach.setDisable(false);
        basket.setDisable(false);
        stair.setDisable(false);
        shootline.setDisable(false);
        passline.setDisable(false);
        bloqline.setDisable(false);
        drillline.setDisable(false);
        Sketch.getInstance().setShapeType(ShapeType.NULL);
        Change = false;
    }

    //Carga las acciones de los botones apretados 
    @FXML
    private void rectanglePressed() {
        setNULL();
        rectangle.setDisable(true);
        Sketch.getInstance().setShapeType(ShapeType.RECTANGLE);

    }

    @FXML
    private void offensePlayerPressed() {
        setNULL();
        offensePlayer.setDisable(true);
        Sketch.getInstance().setShapeType(ShapeType.OFFENSEPLAYER);
    }

    @FXML
    private void offensePlayer1Pressed() {
        setNULL();
        offensePlayer1.setDisable(true);
        Sketch.getInstance().setShapeType(ShapeType.OFFENSEPLAYER1);
    }

    @FXML
    private void offensePlayer2Pressed() {
        setNULL();
        offensePlayer2.setDisable(true);
        Sketch.getInstance().setShapeType(ShapeType.OFFENSEPLAYER2);
    }

    @FXML
    private void offensePlayer3Pressed() {
        setNULL();
        offensePlayer3.setDisable(true);
        Sketch.getInstance().setShapeType(ShapeType.OFFENSEPLAYER3);
    }

    @FXML
    private void offensePlayer4Pressed() {
        setNULL();
        offensePlayer4.setDisable(true);
        Sketch.getInstance().setShapeType(ShapeType.OFFENSEPLAYER4);
    }

    @FXML
    private void offensePlayer5Pressed() {
        setNULL();
        offensePlayer5.setDisable(true);
        Sketch.getInstance().setShapeType(ShapeType.OFFENSEPLAYER5);
    }

    @FXML
    private void offensePlayer6Pressed() {
        setNULL();
        offensePlayer6.setDisable(true);
        Sketch.getInstance().setShapeType(ShapeType.OFFENSEPLAYER6);
    }

    @FXML
    private void offensePlayer7Pressed() {
        setNULL();
        offensePlayer7.setDisable(true);
        Sketch.getInstance().setShapeType(ShapeType.OFFENSEPLAYER7);
    }

    @FXML
    private void offensePlayer8Pressed() {
        setNULL();
        offensePlayer8.setDisable(true);
        Sketch.getInstance().setShapeType(ShapeType.OFFENSEPLAYER8);
    }

    @FXML
    private void offensePlayer9Pressed() {
        setNULL();
        offensePlayer9.setDisable(true);
        Sketch.getInstance().setShapeType(ShapeType.OFFENSEPLAYER9);
    }

    @FXML
    private void defensePlayerPressed() {
        setNULL();
        defensePlayer.setDisable(true);
        Sketch.getInstance().setShapeType(ShapeType.DEFENSEPLAYER);
    }

    @FXML
    private void defensePlayer1Pressed() {
        setNULL();
        defensePlayer1.setDisable(true);
        Sketch.getInstance().setShapeType(ShapeType.DEFENSEPLAYER1);
    }

    @FXML
    private void defensePlayer2Pressed() {
        setNULL();
        defensePlayer2.setDisable(true);
        Sketch.getInstance().setShapeType(ShapeType.DEFENSEPLAYER2);
    }

    @FXML
    private void defensePlayer3Pressed() {
        setNULL();
        defensePlayer3.setDisable(true);
        Sketch.getInstance().setShapeType(ShapeType.DEFENSEPLAYER3);
    }

    @FXML
    private void defensePlayer4Pressed() {
        setNULL();
        defensePlayer4.setDisable(true);
        Sketch.getInstance().setShapeType(ShapeType.DEFENSEPLAYER4);
    }

    @FXML
    private void defensePlayer5Pressed() {
        setNULL();
        defensePlayer5.setDisable(true);
        Sketch.getInstance().setShapeType(ShapeType.DEFENSEPLAYER5);
    }

    @FXML
    private void defensePlayer6Pressed() {
        setNULL();
        defensePlayer6.setDisable(true);
        Sketch.getInstance().setShapeType(ShapeType.DEFENSEPLAYER6);
    }

    @FXML
    private void defensePlayer7Pressed() {
        setNULL();
        defensePlayer7.setDisable(true);
        Sketch.getInstance().setShapeType(ShapeType.DEFENSEPLAYER7);
    }

    @FXML
    private void defensePlayer8Pressed() {
        setNULL();
        defensePlayer8.setDisable(true);
        Sketch.getInstance().setShapeType(ShapeType.DEFENSEPLAYER8);
    }

    @FXML
    private void defensePlayer9Pressed() {
        setNULL();
        defensePlayer9.setDisable(true);
        Sketch.getInstance().setShapeType(ShapeType.DEFENSEPLAYER9);
    }

    @FXML
    private void ballPressed() {
        setNULL();
        ball.setDisable(true);
        Sketch.getInstance().setShapeType(ShapeType.BALL);
    }

    @FXML
    private void conePressed() {
        setNULL();
        cone.setDisable(true);
        Sketch.getInstance().setShapeType(ShapeType.CONE);
    }

    @FXML
    private void cestaPressed() {
        setNULL();
        cesta.setDisable(true);
        Sketch.getInstance().setShapeType(ShapeType.CESTA);
    }

    @FXML
    private void coachPressed() {
        setNULL();
        coach.setDisable(true);
        Sketch.getInstance().setShapeType(ShapeType.COACH);
    }

    @FXML
    private void basquetPressed() {
        setNULL();
        basket.setDisable(true);
        Sketch.getInstance().setShapeType(ShapeType.BASQUET);
    }

    @FXML
    private void stairPressed() {
        setNULL();
        stair.setDisable(true);
        Sketch.getInstance().setShapeType(ShapeType.STAIR);
    }

    @FXML
    private void shootLinePressed() {
        setNULL();
        shootline.setDisable(true);
        Sketch.getInstance().setShapeType(ShapeType.SHOOTLINE);
    }

    @FXML
    private void passLinePressed() {
        setNULL();
        passline.setDisable(true);
        Sketch.getInstance().setShapeType(ShapeType.PASSLINE);
    }

    @FXML
    private void bloqLinePressed() {
        setNULL();
        bloqline.setDisable(true);
        Sketch.getInstance().setShapeType(ShapeType.BLOQLINE);
    }

    @FXML
    private void drillLinePressed() {
        setNULL();
        drillline.setDisable(true);
        Sketch.getInstance().setShapeType(ShapeType.DRILLLINE);
    }

    @FXML
    private void circlePressed() {
        setNULL();
        circle.setDisable(true);
        Sketch.getInstance().setShapeType(ShapeType.CIRCLE);
    }

    @FXML
    private void linePressed() {
        setNULL();
        line.setDisable(true);
        Sketch.getInstance().setShapeType(ShapeType.LINE);

    }

    @FXML
    private void deletePressed() {

        delete.setDisable(true);
        Sketch.getInstance().setMoveFlag(false);
        Sketch.getInstance().setResizeFlag(false);
        Sketch.getInstance().setDeleteFlag(true);

    }

    @FXML
    public void undoPressed() {
        if (!Main.getInstance().getShapes().isEmpty()) {
            undone.push(Main.getInstance().getShapes().pop());
            undone.peek().remove();
        }
    }

    @FXML
    private void redoPressed() {
        if (!undone.isEmpty()) {
            Main.getInstance().getShapes().push(undone.pop());
            Main.getInstance().getShapes().peek().draw();

        }
    }

    @FXML
    private String leerTexto() {
        return descripcionArea.getText();
    }

    //Carga nuevos datos de la jugada seleccionada en la ListTable
    public void showPlayDetails(jugada play) {

        //Detecta si se ha hecho algun cambio en la jugada anterior marcada
        new Changes().checkChanges();

        //carga los datos de la nueva
        passName(play.getNombreJugada());
        passType(play.getTipoJugada());
        passSubtype(play.getSubtipoJugada());
        passFondo(play.getPistaJugada());
        passmaxContador(play.getContadorJugada());
        passLoadDescripcion(play.getDescripcionJugada());
        passLoadCoordenadas(play.getCoordenadasJugada());
        Sketch.getInstance().definirFondo();
        contadorsiguiente = 1;
        playNumberDiagram.setText(Integer.toString(contadorsiguiente));
        labelNombre.setText(play.getNombreJugada());
        labelNombre.setUnderline(true);
        labelNombre.setTextFill(Color.WHITE);

        //Carga datos de la pantalla
        try {
            DataDealer.load(play, 1);
        } catch (Exception e) {

            Main.getInstance().getShapes().forEach(Draws.Shape::remove);
            Main.getInstance().getShapes().clear();
        }

        putText();

        play.setDescripcionJugada(play.getDescripcionJugada());

        setNULL();

      
    }

    public void LoadChange(boolean Change) {
        this.Change = Change;
    }

    public boolean getLoadChange() {
        return Change;
    }

    public void passmaxContador(int maxiContador) {
        this.maxContador = maxiContador;
    }

    public boolean getChange() {
        return Change;
    }

    public void setChange(boolean Change) {
        this.Change = Change;
    }

    public int getmaxContador() {
        return maxContador;
    }

    public void passLoadDescripcion(String loadDescripcion) {
        this.descripcion = loadDescripcion;
    }

    public void passLoadCoordenadas(String loadCoordenadas) {
        this.coordenadas = loadCoordenadas;
    }

    public String getLoadCoordenadas() {
        return coordenadas;
    }

    public String getLoadDescripcion() {
        return descripcion;
    }

    @FXML
    private void newPressed() {

        Sketch.getInstance().setMainWidth(Double.valueOf(514));
        Sketch.getInstance().setMainHeight(Double.valueOf(445));

        Main.getInstance().getShapes().forEach(Draws.Shape::remove);
        Main.getInstance().getShapes().clear();

        Main.getInstance().getRoot().setCenter(Sketch.getInstance().getCanvas());

    }

    @FXML
    void savePressed() {

        saveTextos();
        String salvador = descripcion;

        int guardarContador = contadorsiguiente;

        //Lanza la query para actualizar los campos de descripción y coordenadas
        jugada a = new jugada(namePlay, typePlay, subtypePlay, maxContador, nameFondo, descripcion, coordenadas);

        coordenadas = DataDealer.save(a, contadorsiguiente);

        descripcion = salvador;
        a.setCoordenadasJugada(coordenadas);

        conexion.establecerConexion();
        a.updateInformacion(conexion.getConnection());
        conexion.cerrarConexion();

        //Crea a imagen correspondiente
        new manageImages().printImages(a.getNombreJugada() + "-" + a.getTipoJugada() + "-" + a.getSubtipoJugada() + "_" + contadorsiguiente);

        Change = false;

        conexion.establecerConexion();
        playData.clear();
        jugada.llenarInformacionJugadas(conexion.getConnection(), playData);
        conexion.cerrarConexion();
        ListTable.setItems(playData);

        showPlayDetails(a);

        ListTable.getSelectionModel().select(a);

        contadorsiguiente = guardarContador;
        playNumberDiagram.setText(Integer.toString(contadorsiguiente));
        DataDealer.load(a, contadorsiguiente);
    }

    public Button getDelete() {
        return delete;
    }

    @FXML
    private void handleNewPlay() {
        changeEnDescripcion();
        new Changes().checkChanges();
        jugada tempPlay = new jugada();

        boolean okClicked = Main.getInstance().showPlayEditDialog(tempPlay);
        if (okClicked) {

            conexion.establecerConexion();
            tempPlay.guardarRegistro(conexion.getConnection());
            playData.clear();
            jugada.llenarInformacionJugadas(conexion.getConnection(), playData);
            conexion.cerrarConexion();

            passName(tempPlay.getNombreJugada());
            passType(tempPlay.getTipoJugada());
            passSubtype(tempPlay.getSubtipoJugada());
            passFondo(tempPlay.getPistaJugada());

            newPressed();
            Sketch.getInstance().definirFondo();
        }
    }

    public void passFondo(String nameFondo) {
        this.nameFondo = nameFondo;
    }

    public String getpassFondo() {
        return nameFondo;
    }

    public void passName(String namePlay) {
        this.namePlay = namePlay;

    }

    public String getpassName() {
        return namePlay;
    }

    public void passType(String typePlay) {
        this.typePlay = typePlay;
    }

    public String getpassType() {
        return typePlay;
    }

    public void passSubtype(String subtypePlay) {
        this.subtypePlay = subtypePlay;
    }

    public String getpassSubtype() {
        return subtypePlay;
    }

    @FXML
    private void handleEditPlay() {
        new Changes().checkChanges();
        jugada selectedPlay = ListTable.getSelectionModel().getSelectedItem();

        if (selectedPlay != null) {
            String passName = selectedPlay.getNombreJugada();
            String passTipo = selectedPlay.getTipoJugada();
            String passSubtipo = selectedPlay.getSubtipoJugada();

            boolean okClicked = Main.getInstance().showPlayEditDialog(selectedPlay);
            if (okClicked) {
                showPlayDetails(selectedPlay);
                conexion.establecerConexion();
                selectedPlay.updateRegistro(conexion.getConnection(), passName, passTipo, passSubtipo);
                playData.clear();
                jugada.llenarInformacionJugadas(conexion.getConnection(), playData);
            }

        } else {
            // No hay seleccinado nada
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(Main.getInstance().getPrimaryStage());
            alert.setTitle(messages.getString("noSelection"));
            alert.setHeaderText(messages.getString("noPlaySelected"));
            alert.setContentText(messages.getString("selectAPlay"));

            alert.showAndWait();
        }
    }

    @FXML
    private void handleDeletePlay() {

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(messages.getString("Delete"));
        alert.setHeaderText(messages.getString("pressDelete"));
        alert.setContentText(messages.getString("sureDelete"));

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            int selectedIndex = ListTable.getSelectionModel().getSelectedIndex();

            if (selectedIndex >= 0) {
                manageImages.deleteImages(ListTable.getItems().get(selectedIndex).getNombreJugada(), ListTable.getItems().get(selectedIndex).getTipoJugada(), ListTable.getItems().get(selectedIndex).getSubtipoJugada(), ListTable.getItems().get(selectedIndex).getContadorJugada());

                conexion.establecerConexion();
                ListTable.getSelectionModel().getSelectedItem().eliminarRegistro(conexion.getConnection());
                playData.clear();
                jugada.llenarInformacionJugadas(conexion.getConnection(), playData);
                conexion.cerrarConexion();

            } else {
                // No hay seleccinado nada
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.initOwner(Main.getInstance().getPrimaryStage());
                alert2.setTitle(messages.getString("noSelection"));
                alert2.setHeaderText(messages.getString("noPlaySelected"));
                alert2.setContentText(messages.getString("selectAPlay"));

                alert2.showAndWait();
            }
        }
    }

    //Añade un campo nuevo a la jugada, en última posición
    @FXML
    private void crearOtrocampo() {
        new Changes().checkChanges();

        //Obtiene los datos de la jugada marcada, suma 1 al contador Maximo y actualiza
        jugada selectedPlay = ListTable.getSelectionModel().getSelectedItem();
        selectedPlay.setContadorJugada(getmaxContador() + 1);
        conexion.establecerConexion();
        selectedPlay.updateInformacion(conexion.getConnection());
        conexion.cerrarConexion();

        //Muestra el campo nuevo seleccionado
        playNumberDiagram.setText(Integer.toString(maxContador));
        contadorsiguiente = maxContador;
        DataDealer.load(selectedPlay, maxContador);
    }

    @FXML
    private void siguientedibujo() {
        changeEnDescripcion();
        new Changes().checkChanges();

        jugada selectedPlay = ListTable.getSelectionModel().getSelectedItem();
        maxContador = selectedPlay.getContadorJugada();
        passLoadDescripcion(selectedPlay.getDescripcionJugada());

        if (contadorsiguiente < maxContador) {
            contadorsiguiente = contadorsiguiente + 1;
            //Cargar siguiente parte 
            DataDealer.load(selectedPlay, contadorsiguiente);
        }

        playNumberDiagram.setText(Integer.toString(contadorsiguiente));
        putText();
        setNULL();
    }

    @FXML
    private void atrasdibujo() {
        changeEnDescripcion();
        new Changes().checkChanges();
        jugada selectedPlay = ListTable.getSelectionModel().getSelectedItem();

        contadorsiguiente = contadorsiguiente - 1;
        if (contadorsiguiente == 0) {
            contadorsiguiente = 1;
        }

        DataDealer.load(selectedPlay, contadorsiguiente);
        passLoadDescripcion(selectedPlay.getDescripcionJugada());
        playNumberDiagram.setText(Integer.toString(contadorsiguiente));
        putText();
        setNULL();
    }

    @FXML
    private void borrardibujo() {

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(messages.getString("Delete"));
        alert.setHeaderText(messages.getString("pressDelete"));
        alert.setContentText(messages.getString("sureDeletePage"));

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            int contadorActual = contadorsiguiente;

            jugada selectedPlay = ListTable.getSelectionModel().getSelectedItem();

            descripcion = borrarText(contadorActual);
            coordenadas = borrarCoordenadas(contadorActual);

            maxContador = getmaxContador();
            maxContador = maxContador - 1;
            selectedPlay.setContadorJugada(maxContador);

            conexion.establecerConexion();
            selectedPlay.updateInformacion(conexion.getConnection());
            conexion.cerrarConexion();
            setNULL();
        }
    }

    @FXML
    private void PlanPressed() {
        new Changes().checkChanges();
        Main.getInstance().showPlanDialog();
    }

    @FXML
    public void PrintPressed()
            throws DocumentException, IOException {
        new Changes().checkChanges();
        jugada selectedPlay = ListTable.getSelectionModel().getSelectedItem();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(messages.getString("saveDrill"));

        File fileSavePath = fileChooser.showSaveDialog(dialogStage);
        new Drillpdf().PrintPressed(selectedPlay, fileSavePath);
    }

    private void saveTextos() {
        String textoComparador = getLoadDescripcion();
        String textoAñadido = leerTexto();
        String cadena = "%$&" + contadorsiguiente;
        String devolver;

        try {
            if (!textoComparador.contains(cadena)) {
                devolver = textoComparador + cadena + textoAñadido;
            } else {
                try {
                    devolver = textoComparador.substring(0, textoComparador.indexOf(cadena)) + "%$&" + contadorsiguiente + textoAñadido + textoComparador.substring(textoComparador.indexOf("%$&", textoComparador.indexOf(cadena) + 3));
                } catch (Exception e) {
                    devolver = textoComparador.substring(0, textoComparador.indexOf(cadena)) + "%$&" + contadorsiguiente + textoAñadido;
                }
            }
        } catch (Exception e) {
            devolver = cadena + textoAñadido;
        }
        descripcion = devolver;
    }

    public void putText() {
        String mostrar;
        String textoComparador = getLoadDescripcion();
        String cadena = "%$&" + contadorsiguiente;

        if (maxContador == 1) {

            try {
                mostrar = textoComparador.substring(textoComparador.indexOf(cadena) + 4);
            } catch (Exception e) {
                mostrar = "";
            }
            descripcionArea.setText(mostrar);

        } else if (!textoComparador.contains(cadena)) {
            mostrar = "";

            descripcionArea.setText(mostrar);
        } else {

            try {
                mostrar = textoComparador.substring(textoComparador.indexOf(cadena) + 4, textoComparador.indexOf("%$&", textoComparador.indexOf(cadena) + 4));
            } catch (Exception e) {
                mostrar = textoComparador.substring(textoComparador.indexOf(cadena) + 4);
            }
            descripcionArea.setText(mostrar);
        }
        changeDescripcion = mostrar;
    }

    private void changeEnDescripcion() {
        if (!changeDescripcion.equals(leerTexto())) {
            Change = true;

        }
    }

    String borrarText(int contadorBorrar) {

        String resultado = "";
        String textoComparador = getLoadDescripcion();
        String Borrar = "%$&" + contadorBorrar;
        if (textoComparador.contains(Borrar)) {
            String substring;
            try {
                substring = textoComparador.substring(textoComparador.indexOf(Borrar), textoComparador.indexOf("%$&", textoComparador.indexOf(Borrar) + 3));
            } catch (Exception e) {
                substring = textoComparador.substring(textoComparador.indexOf(Borrar));
            }

            resultado = textoComparador.replace(substring, "");

        }
        for (int i = contadorBorrar; i < maxContador; i++) {

            String newTrozo = "%$&" + i;
            String Trozo = "%$&" + (i + 1);

            resultado = resultado.replace(Trozo, newTrozo);

        }

        return resultado;
    }

    String borrarCoordenadas(int contadorBorrar) {

        String resultado = "";
        String textoComparador = getLoadCoordenadas();
        String Borrar = "Bloque " + contadorBorrar;

        if (textoComparador.contains(Borrar)) {
            String substring;
            try {
                substring = textoComparador.substring(textoComparador.indexOf(Borrar), textoComparador.indexOf("Bloque ", textoComparador.indexOf(Borrar) + 3));
            } catch (Exception e) {
                substring = textoComparador.substring(textoComparador.indexOf(Borrar));
            }

            resultado = textoComparador.replace(substring, "");

        }
        for (int i = contadorBorrar; i < maxContador; i++) {

            String newTrozo = "Bloque " + i;
            String Trozo = "Bloque " + (i + 1);

            resultado = resultado.replace(Trozo, newTrozo);

        }

        return resultado;

    }

}
