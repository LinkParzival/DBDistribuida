package Cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

/**
 * @web http://clubjava.thelinkweb.com.mx
 * @author link
 */
public class SocketCliente implements Runnable{
    private Socket socket;
    private InputStream isEntrada;
    private DataInputStream disEntrada;
    private OutputStream osSalida;
    private DataOutputStream dosSalida;
    private final int PUERTO = 7316;
    private String host;
    
    public SocketCliente(String host){
        this.host = host;
    }
    
    public void conectar() {
        try {
            this.socket = new Socket(this.host, this.PUERTO);
            this.isEntrada = socket.getInputStream();
            this.osSalida = socket.getOutputStream();
            this.disEntrada = new DataInputStream(isEntrada);
            this.dosSalida = new DataOutputStream(osSalida);
        } catch (UnknownHostException ex) {
            JOptionPane.showMessageDialog(null, "UnknownHostException: " + ex.getLocalizedMessage(), "Error conectar()", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "IOException: " + ex.getLocalizedMessage(), "Error conectar()", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void ejecutarConsulta(String consulta, JTextPane jtpDatos){
        this.conectar();
        try {
            dosSalida.writeUTF(consulta);
            jtpDatos.setText(disEntrada.readUTF());
        } catch (IOException ex) {
             JOptionPane.showMessageDialog(null, "IOException: " + ex.getLocalizedMessage(), "Error ejecutarConsulta()", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void run() {
        try {
            System.out.println(disEntrada.readUTF());
        } catch (IOException ex) {
            Logger.getLogger(SocketCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
