/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import main.Main;
import model.Plan;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.CheckBox;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import model.jugada;

/**
 * FXML Controller class
 *
 * @author david
 */
public class PlanController implements Initializable {

    @FXML
    private TextField Team;
    @FXML
    private TextField Season;
    @FXML
    private TextField Date;
    @FXML
    private TextField Start;
    @FXML
    private TextField End;
    @FXML
    private TextField Players;
    @FXML
    private TextField Place;
    @FXML
    private TextArea Equipment;
    @FXML
    private CheckBox checkboxDrills;
    @FXML
    private TableView<Plan> PlanTable;
    @FXML
    private TableColumn<Plan, String> starColumn;
    @FXML
    private TableColumn<Plan, String> endColumn;
    @FXML
    private TableColumn<Plan, String> drillColumn;
    @FXML
    private TableColumn<Plan, String> notesColumn;
    @FXML
    private Button createField;

    private Stage dialogStage;

    private int contadorlineas = 0;
    public ObservableList<Plan> planData = FXCollections.observableArrayList();

    private ObservableList<jugada> playData = Frame.getInstance().getPlayData();
    
    String[] dataLocale = Main.getInstance().getLocale();
    Locale language = new Locale(dataLocale[1]);
    String path = dataLocale[0];
    ResourceBundle messages = ResourceBundle.getBundle(path, language);

    public ObservableList<Plan> getPlanData() {

        return planData;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        starColumn.setCellValueFactory(cellData -> cellData.getValue().StartProperty());
        endColumn.setCellValueFactory(cellData -> cellData.getValue().EndProperty());
        drillColumn.setCellValueFactory(cellData -> cellData.getValue().DrillProperty());
        notesColumn.setCellValueFactory(cellData -> cellData.getValue().NotesProperty());

        PlanTable.setEditable(true);

        starColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        starColumn.setOnEditCommit((CellEditEvent<Plan, String> t) -> {
            ((Plan) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setStart(t.getNewValue());
        });

        endColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        endColumn.setOnEditCommit((CellEditEvent<Plan, String> t) -> {
            ((Plan) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setEnd(t.getNewValue());
        });

        drillColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        drillColumn.setOnEditCommit((CellEditEvent<Plan, String> t) -> {
            ((Plan) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setDrill(t.getNewValue());
        });

        notesColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        notesColumn.setOnEditCommit((CellEditEvent<Plan, String> t) -> {
            ((Plan) t.getTableView().getItems().get(
                    t.getTablePosition().getRow())).setNotes(t.getNewValue());
        });

        PlanTable.setItems(getPlanData());

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void createDrill() {
        planData.add(new Plan(messages.getString("Edit"), messages.getString("Edit"),messages.getString("Edit"), messages.getString("Edit")));
        contadorlineas++;
    }

    public void guardarNombre() throws DocumentException, IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(messages.getString("savePlan"));

        File file = fileChooser.showSaveDialog(dialogStage);
        PrintPressed(file.getPath());
        dialogStage.close();
    }

    public Document PrintPressed(String Path)
            throws DocumentException, IOException {
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter.getInstance(document, new FileOutputStream(Path));
        // step 3
        document.open();
        // step 4
        PdfPTable tablaDatosPlan = new PdfPTable(2);

        tablaDatosPlan.setWidthPercentage(100);

        if (!Team.getText().isEmpty()) {
            PdfPCell cell1 = new PdfPCell(new Phrase(messages.getString("Team")+":" + Team.getText()));
            cell1.setBorderColor(BaseColor.WHITE);
            tablaDatosPlan.addCell(cell1);
        }
        if (!Season.getText().isEmpty()) {
            PdfPCell cell1 = new PdfPCell(new Phrase(messages.getString("Season")+":" + Season.getText()));
            cell1.setBorderColor(BaseColor.WHITE);
            tablaDatosPlan.addCell(cell1);
        }
        if (!Date.getText().isEmpty()) {
            PdfPCell cell1 = new PdfPCell(new Phrase(messages.getString("Date")+":" + Date.getText()));
            cell1.setBorderColor(BaseColor.WHITE);
            tablaDatosPlan.addCell(cell1);
        }
        if (!Players.getText().isEmpty()) {
            PdfPCell cell1 = new PdfPCell(new Phrase(messages.getString("Players")+":"+ Players.getText()));
            cell1.setBorderColor(BaseColor.WHITE);
            tablaDatosPlan.addCell(cell1);
        }
        if (!Start.getText().isEmpty()) {
            PdfPCell cell1 = new PdfPCell(new Phrase(messages.getString("Start")+":" + Start.getText()));
            cell1.setBorderColor(BaseColor.WHITE);
            tablaDatosPlan.addCell(cell1);
        }
        if (!End.getText().isEmpty()) {
            PdfPCell cell1 = new PdfPCell(new Phrase(messages.getString("End")+":" + End.getText()));
            cell1.setBorderColor(BaseColor.WHITE);
            tablaDatosPlan.addCell(cell1);
        }
        if (!Place.getText().isEmpty()) {
            PdfPCell cell1 = new PdfPCell(new Phrase(messages.getString("Place")+":" + Place.getText()));
            cell1.setBorderColor(BaseColor.WHITE);
            tablaDatosPlan.addCell(cell1);
        }
        if (!Equipment.getText().isEmpty()) {
            PdfPCell cell1 = new PdfPCell(new Phrase(messages.getString("Equipment")+":" + Equipment.getText()));
            cell1.setBorderColor(BaseColor.WHITE);
            tablaDatosPlan.addCell(cell1);
        }

        document.add(tablaDatosPlan);

        document.add(new Phrase("\n"));
        document.add(new Phrase("\n"));

        PdfPTable tablaEjercicios = new PdfPTable(4);
        tablaEjercicios.setHeaderRows(contadorlineas);
        tablaEjercicios.addCell(messages.getString("Start"));
        tablaEjercicios.addCell(messages.getString("End"));
        tablaEjercicios.addCell(messages.getString("Drill"));
        tablaEjercicios.addCell(messages.getString("Notes"));
        int cont = 0;
        for (int aw = 4; aw < contadorlineas + 4; aw++) {

            tablaEjercicios.addCell(starColumn.getCellData(cont));
            tablaEjercicios.addCell(endColumn.getCellData(cont));
            tablaEjercicios.addCell(drillColumn.getCellData(cont));
            tablaEjercicios.addCell(notesColumn.getCellData(cont));
            cont++;
        }

        document.add(tablaEjercicios);
        int contadorListado = 0, contadorRecorrer = 0;

        if (checkboxDrills.isSelected()) {

            while (contadorListado < planData.size()) {
                contadorRecorrer = 0;

                while (contadorRecorrer < playData.size()) {
                        System.out.println("Busco el ejercicio " +drillColumn.getCellData(contadorListado) );
                    if (drillColumn.getCellData(contadorListado).contains(playData.get(contadorRecorrer).getNombreJugada())) {
                        int contador = 1;
                        System.out.println("Ejercicio encontrado");
                        document.add(new Paragraph(messages.getString("Drill")+" " + drillColumn.getCellData(contadorListado)));
                        while (contador < playData.get(contadorRecorrer).getContadorJugada() + 1) {

                            document.add(new Paragraph(messages.getString("Diagram")+" " + contador));
                            document.add(new Paragraph(" "));

                            Image image2 = Image.getInstance(playData.get(contadorRecorrer).getNombreJugada() + "-" + playData.get(contadorRecorrer).getTipoJugada() + "-" + playData.get(contadorRecorrer).getSubtipoJugada() + "_" + contador);
                            image2.scalePercent(50);

                            PdfPTable table1 = new PdfPTable(2);

                            table1.addCell(image2);
                            table1.addCell(textPdf.textPdf(contador, playData.get(contadorRecorrer).getDescripcionJugada(), playData.get(contadorRecorrer).getContadorJugada()));
                            document.add(table1);

                            contador++;

                        }

                    }
                    contadorRecorrer++;

                }
                contadorListado++;
            }

        }
        // step 5
        document.close();
        return document;

    }

}
