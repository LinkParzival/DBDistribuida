package Servidor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 * DatosSQL.java
 * Clase DatosSQL que permite obtener los datos de una consulta de sql en arreglos para incluirlos en
 * las tablas de java.
 *
 * @web http://clubjava.thelinkweb.com.mx
 * @author WING-Tec
 * @version 1.00 2010/04/30
 */
public class Datos {
    //Elementos para hacer conexion
    private Connection conexion;
    private static Statement sentencia;
    private static ResultSet resultado;
    private String resp;
    //Propiedades de la Clase
   private ConectarMySQL conectarMySQL;
    
    public Datos(String consulta) {
        this.resp =null;
        this.conectarMySQL = new ConectarMySQL();
        if(!conexionBD1(consulta))
            if(!conexionBD2(consulta))
                if(!conexionBD3(consulta))
                    resp = "Error Posibles Causas \n- No exite tabla en ninguna Base de Datos\n- No hay conexion a las Base de Datos";
    }
    
    private boolean conexionBD1(String consulta) {
        try {
            boolean exito = true;

            conexion = conectarMySQL.obtenerConexion();
            if (conexion == null) 
                return false;
            
            sentencia = conexion.createStatement();
            if (consulta.contains("UPDATE") == true || consulta.contains("INSERT") == true) {
                if(sentencia.execute(consulta)){
                    resp = ">Sentencia Executada Correctamente.";
                    exito = true;
                }else
                    exito = false;
            } else {
                resultado = sentencia.executeQuery(consulta);
                if(resultado.getMetaData().getColumnCount()>0)
                    exito = true;
                else 
                    exito = false;
            }
            return exito;
        } catch (SQLException ex) {
            //Logger.getLogger(Datos.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    private boolean conexionBD2(String consulta) {
        conectarMySQL = new ConectarMySQL("localhost", "Prueba", "root", "");
        return conexionBD1(consulta);
    }
    
    private boolean conexionBD3(String consulta) {
        try {
            boolean exito = true;
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            conexion = DriverManager.getConnection("jdbc:odbc:Prueba");
            if(conexion == null)           
                return false;
            
            sentencia = conexion.createStatement();
            if (consulta.contains("UPDATE") == true || consulta.contains("INSERT") == true) {
                if(sentencia.execute(consulta)){
                    resp = ">Sentencia Executada Correctamente.";
                    exito = true;
                }else
                    exito = false;
            } else {
                resultado = sentencia.executeQuery(consulta);
                if(resultado.getMetaData().getColumnCount()>0)
                    exito = true;
                else 
                    exito = false;
            }
            return exito;
        } catch (SQLException ex) {
            //Logger.getLogger(Datos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            //Logger.getLogger(Datos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public String enviarRespuesta() {       
            try {
                 if (resp == null && resultado != null) {
                int cols, pos;
                resp = "---------------------------------------------------------------------------------------\n";                
                resultado.first();
                cols = (resultado.getMetaData()).getColumnCount();
                do{
                    for (pos = 1; pos <= cols; pos++) {
                        resp += "|" + resultado.getString(pos) + " | ";
                    }
                    resp += "\n---------------------------------------------------------------------------------------\n";
                }while (resultado.next());
                return resp;
                 }else
                     return null;
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "SQLException: " + ex.getMessage(), "ERROR en enviarRespuesta()", JOptionPane.ERROR_MESSAGE);
                return "Error";
            }        
    }
    
    public String enviarRespuesta2() {       
            try {
                 if (resp == null && resultado != null) {
                int cols, pos;
                resp = "---------------------------------------------------------------------------------------\n";                                
                cols = (resultado.getMetaData()).getColumnCount();
                do{
                    for (pos = 1; pos <= cols; pos++) {
                        resp += "|" + resultado.getString(pos) + " | ";
                    }
                    resp += "\n---------------------------------------------------------------------------------------\n";
                }while (resultado.next());
                return resp;
                 }else
                     return null;
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "SQLException: " + ex.getMessage(), "ERROR en enviarRespuesta2()", JOptionPane.ERROR_MESSAGE);
                return "Error";
            }        
    }
}
