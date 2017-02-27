/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author david
 */
public class jugada {

    private StringProperty nombreJugada;
    private StringProperty tipoJugada;
    private StringProperty subtipoJugada;
    private IntegerProperty contadorJugada;
    private StringProperty pistaJugada;
    private StringProperty descripcionJugada;
    private StringProperty coordenadasJugada;

    public jugada(String nombreJugada, String tipoJugada, String subtipoJugada, int contadorJugada, String pistaJugada, String descripcionJugada, String coordenadasJugada) {
        this.nombreJugada = new SimpleStringProperty(nombreJugada);
        this.tipoJugada = new SimpleStringProperty(tipoJugada);
        this.subtipoJugada = new SimpleStringProperty(subtipoJugada);
        this.contadorJugada = new SimpleIntegerProperty(contadorJugada);
        this.pistaJugada = new SimpleStringProperty(pistaJugada);
        this.descripcionJugada = new SimpleStringProperty(descripcionJugada);
        this.coordenadasJugada = new SimpleStringProperty(coordenadasJugada);

    }

    public jugada() {
        this(null, null, null, 1, null, null, null);
    }

    public String getNombreJugada() {
        return nombreJugada.get();
    }

    public void setNombreJugada(String nombreJugada) {
        this.nombreJugada = new SimpleStringProperty(nombreJugada);
    }

    public StringProperty nombreJugadaProperty() {
        return nombreJugada;
    }

    public String getTipoJugada() {
        return tipoJugada.get();
    }

    public void setTipoJugada(String tipoJugada) {
        this.tipoJugada = new SimpleStringProperty(tipoJugada);
    }

    public StringProperty tipoJugadaProperty() {
        return tipoJugada;
    }

    public String getSubtipoJugada() {
        return subtipoJugada.get();
    }

    public void setSubtipoJugada(String subtipoJugada) {
        this.subtipoJugada = new SimpleStringProperty(subtipoJugada);
    }

    public StringProperty subtipoJugadaProperty() {
        return subtipoJugada;
    }

    public int getContadorJugada() {
        return contadorJugada.get();
    }

    public void setContadorJugada(int contadorJugada) {
        this.contadorJugada = new SimpleIntegerProperty(contadorJugada);
    }

    public IntegerProperty contadorJugadaProperty() {
        return contadorJugada;
    }

    public String getPistaJugada() {
        return pistaJugada.get();
    }

    public void setPistaJugada(String pistaJugada) {
        this.pistaJugada = new SimpleStringProperty(pistaJugada);
    }

    public StringProperty pistaJugadaProperty() {
        return pistaJugada;
    }

    public String getDescripcionJugada() {
        return descripcionJugada.get();
    }

    public void setDescripcionJugada(String descripcionJugada) {
        this.descripcionJugada = new SimpleStringProperty(descripcionJugada);
    }

    public StringProperty descripcionJugadaProperty() {
        return descripcionJugada;
    }

    public String getCoordenadasJugada() {
        return coordenadasJugada.get();
    }

    public void setCoordenadasJugada(String coordenadasJugada) {
        this.coordenadasJugada = new SimpleStringProperty(coordenadasJugada);
    }

    public StringProperty coordenadasJugadaProperty() {
        return coordenadasJugada;
    }

    //Consultas a la base de datos
    
    
    //guarda na consulta nueva
    public int guardarRegistro(Connection connection) {
        try {

            PreparedStatement instruccion
                    = connection.prepareStatement("INSERT INTO `playbookData` (`Name`, `Type`, `subType`, `number`, `pista`, `descripcion`, `coordenadas`)"
                            + "VALUES (?, ?, ?, ?, ?, ?, ?)");
            instruccion.setString(1, nombreJugada.get());
            instruccion.setString(2, tipoJugada.get());
            instruccion.setString(3, subtipoJugada.get());
            instruccion.setInt(4, contadorJugada.get());
            instruccion.setString(5, pistaJugada.get());
            instruccion.setString(6, descripcionJugada.get());
            instruccion.setString(7, coordenadasJugada.get());
            return instruccion.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    //actualiza la información de la descripción, coordenadas y número de páginas
    public int updateInformacion(Connection connection) {
        try {
            PreparedStatement instruccion
                    = connection.prepareStatement(
                            "UPDATE `playbookData` "
                            + " SET 	`descripcion` = ?,  "
                            + " `coordenadas` = ?,  "
                            + " `number` = ? "
                            + " WHERE Name = ? AND Type = ? AND subType = ?;"
                    );
            instruccion.setString(1, descripcionJugada.get());
            instruccion.setString(2, coordenadasJugada.get());
            instruccion.setInt(3, contadorJugada.get());
            instruccion.setString(4, nombreJugada.get());
            instruccion.setString(5, tipoJugada.get());
            instruccion.setString(6, subtipoJugada.get());

            return instruccion.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    //actualiza el nombre, tipo y subtipo de un registro guardado
    public int updateRegistro(Connection connection, String anteriorName, String anteriorTipo, String anteriorSubtipo) {
        try {
            PreparedStatement instruccion
                    = connection.prepareStatement(
                            "UPDATE `playbookData` "
                            + " SET 	`Name` = ?,  "
                            + " `Type` = ?,  "
                            + " `subType` = ?,  "
                            + " `pista` = ?  "
                            + " WHERE Name = ? AND Type = ? AND subType = ?;"
                    );
            instruccion.setString(1, nombreJugada.get());
            instruccion.setString(2, tipoJugada.get());
            instruccion.setString(3, subtipoJugada.get());
            instruccion.setString(4, pistaJugada.get());
            instruccion.setString(5, anteriorName);
            instruccion.setString(6, anteriorTipo);
            instruccion.setString(7, anteriorSubtipo);

            return instruccion.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    //elimina un registro
    public int eliminarRegistro(Connection connection) {
        try {
            PreparedStatement instruccion = connection.prepareStatement(
                    "DELETE FROM `playbookData` "
                    + " WHERE Name = ? AND Type = ? AND subType = ?;"
            );
            instruccion.setString(1, nombreJugada.get());
            instruccion.setString(2, tipoJugada.get());
            instruccion.setString(3, subtipoJugada.get());
            return instruccion.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    
    //carga los datos a la tableView
    public static void llenarInformacionJugadas(Connection connection, ObservableList<jugada> lista) {
        try {
            Statement instruccion = connection.createStatement();
            ResultSet resultado = instruccion.executeQuery("SELECT * FROM playbookData");
            while (resultado.next()) {
                lista.add(new jugada(
                        resultado.getString("name"),
                        resultado.getString("type"),
                        resultado.getString("subtype"),
                        resultado.getInt("number"),
                        resultado.getString("pista"),
                        resultado.getString("descripcion"),
                        resultado.getString("coordenadas")
                )
                );
            }

        } catch (SQLException ex) {
           
        }

    }

}
