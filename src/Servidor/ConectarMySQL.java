package Servidor;

/**
 * @(#)ConectarBD.java
 * Realiza la conexion a MySQL paara realisar consultas, altas, bajas, cambios a la Base de Datos.
 *
 * @author WING-Tec
 * @version 1.00 2010/04/27
 */
import java.sql.Connection;
import java.sql.DriverManager;

public class ConectarMySQL {

    private Connection conexion;
    private final String DRIVER = "com.mysql.jdbc.Driver"; //Driver que facilita la conexion
    private final int PORT_NUMBER = 3306;  //puerto que utilisara.
    private String server_name;
    private String database_name;
    private String username;
    private String password;    

    public ConectarMySQL(){
        this.server_name = "192.168.0.103";
        this.database_name = "virgin";
        this.username = "root";
        this.password = "sasa";        
    }
    
    public ConectarMySQL(String server_name, String database_name, String username, String password){
        this.server_name = server_name;
        this.database_name = database_name;
        this.username = username;
        this.password = password;        
    }
    
    public Connection obtenerConexion() {
        conexion = null;
        try {
            Class.forName(DRIVER);
            //Inicialisa la conexion con los parametros establecidos anteriormente.
            conexion = DriverManager.getConnection("jdbc:mysql://" + server_name + ":" + PORT_NUMBER + "/" + database_name, username, password);
        } catch (Exception ex) { //Captura si ubiera alguna excepcion ejemplo que no exista la BD o este fallando etc.
           // JOptionPane.showMessageDialog(null, "Exception: " + ex, "Error en obtenerConexion():", JOptionPane.ERROR_MESSAGE); //Mensaje de posible error.
        }
        return conexion;
    }
}
