package Servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

/**
 * @web http://clubjava.thelinkweb.com.mx
 * @author link
 */
public class SocketServidor  implements Runnable{
    private ServerSocket socketServidor;
    private Socket socket;
    private InputStream isEntrada;
    private DataInputStream disEntrada;
    private OutputStream osSalida;
    private DataOutputStream dosSalida;    
    private JTextPane jtpMensajes;
    private Datos datosDB;
    private final int PUERTO = 7316;       
    
    public SocketServidor(JTextPane jtpMensajes){
        this.jtpMensajes = jtpMensajes;
        try {
            this.jtpMensajes.setText(">Configurando Servidor...");
            this.socketServidor = new ServerSocket(this.PUERTO);                               
            Thread hilo = new Thread(this);
            this.jtpMensajes.setText(this.jtpMensajes.getText()+"\n>Listo");
            hilo.start();            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getLocalizedMessage(), "Error SocketServidor()", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                this.jtpMensajes.setText(this.jtpMensajes.getText()+"\n>Esperando Cliente....");
                this.socket = socketServidor.accept();
                this.isEntrada = socket.getInputStream();
                this.osSalida = socket.getOutputStream();
                this.disEntrada = new DataInputStream(isEntrada);
                this.dosSalida = new DataOutputStream(osSalida);
                //Recibe consulta
                String recibe = disEntrada.readUTF();
                if(recibe.equalsIgnoreCase("Prueba Conexion")){
                    this.jtpMensajes.setText(this.jtpMensajes.getText()+"\n>Recibi Peticion de Preuba de Conexion del Cliente: " +socket.getInetAddress().toString());
                    dosSalida.writeUTF("Conexion Establecida");                    
                    this.jtpMensajes.setText(this.jtpMensajes.getText()+"\n>Respuesta Enviada al Cliente: " + socket.getInetAddress().toString());
                }else{
                    this.jtpMensajes.setText(this.jtpMensajes.getText()+"\n>Recibi consulta SQL: '"+recibe+"' del Cliente: " +socket.getInetAddress().toString());
                    datosDB = new Datos(recibe);
                    String respuesta = datosDB.enviarRespuesta();
                    if(respuesta != null)
                        dosSalida.writeUTF(respuesta);                    
                    else
                        dosSalida.writeUTF("Error: no se pudo ejecutar la sentencia SQL.\nPosibles Causas:\n-Sinaxis SQL incorrecta\n-La Tabla no existe en ninguna de la Base de Datos");
                    this.jtpMensajes.setText(this.jtpMensajes.getText()+"\n>Respuesta Enviada al Cliente: " + socket.getInetAddress().toString());
                }
                socket.close();
            }
        } catch (IOException ex) {            
            JOptionPane.showMessageDialog(null, "Error: " + ex.getLocalizedMessage(), "Error SocketServidor()", JOptionPane.ERROR_MESSAGE);
        }
    }
 
    
}
