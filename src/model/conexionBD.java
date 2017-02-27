/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Crea la configuración para guardar en la base de datos
 * 
 * @author david
 */
public class conexionBD {

    private Connection connection;
    private String url = "jdbc:sqlite:/home/david/NetBeansProjects/JavaFXApplication8quitar/tfg.sqlite";
  
    
    String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"tfg.sqlite";
    

    public  Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public void establecerConexion(){
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println(filePath);
            connection = DriverManager.getConnection("jdbc:sqlite:"+filePath);
           
            System.out.println("Conexión correcta");
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(conexionBD.class.getName()).log(Level.SEVERE, null, ex);
             System.out.println("Esto ha petado");
        }
    }
    
    public void cerrarConexion(){
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(conexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
