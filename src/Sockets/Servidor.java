package Sockets;

import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.sql.*;

class Servidor extends JFrame implements Runnable {

    JLabel lblmensaje = new JLabel("SERVIDOR ESPERANDO");
    JTextArea area = new JTextArea();
    JTextArea area1 = new JTextArea();
//	JLabel lblrcbmensaje=new JLabel("EL CLIENTE SOLICITO LA CONSULTA");
    JPanel panel = new JPanel();
    Socket socket;
    ServerSocket s;
    InputStream entrada;
    DataInputStream fentrada;
    OutputStream salida;
    DataOutputStream fsalida;
    int puerto = 5432;

    Servidor() {
        setSize(450, 320);
        setTitle("Servidor");
        setVisible(true);

        Container contenedor = getContentPane();
        contenedor.add(panel);
        panel.setLayout(null);
        panel.add(lblmensaje);
        lblmensaje.setBounds(170, 30, 300, 30);
//		panel.add(lblrcbmensaje);lblrcbmensaje.setBounds(130,10,220,25);
//		panel.add(area);area.setBounds(20,120,400,170);
//		panel.add(area1);area1.setBounds(150,40,200,25);

        conecta();

        Thread hilo = new Thread(this);
        hilo.start();



        panel.setBackground(Color.white);
    }

    public void conecta() {
        try {
            s = new ServerSocket(puerto);
            socket = s.accept();
            entrada = socket.getInputStream();
            salida = socket.getOutputStream();
            fentrada = new DataInputStream(entrada);
            fsalida = new DataOutputStream(salida);
            try {
                Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                lblmensaje.setText("Conexion Establecida...");
                panel.add(lblmensaje);
                lblmensaje.setBounds(170, 80, 300, 30);
            } catch (ClassNotFoundException e) {
                System.out.println("Error de controlador...");
            }
        } catch (Exception er) {
            JOptionPane.showMessageDialog(null, "Error al conectar el socket " + er);
        }
    }

    public void run() {
        try {
            while (true) {
                int cols, pos;
                String resultado = "";
                Connection conexion;
                Statement sentencia;
                String recibe = fentrada.readUTF();

                conexion = DriverManager.getConnection("jdbc:odbc:Prueba");
                sentencia = conexion.createStatement();

                //Checamos si fue una insercion o actualizacion
                if ((recibe.contains("update") == true) || (recibe.contains("insert") == true)) {
                    try {
                        sentencia.execute(recibe);
                        resultado = "La operacion fue hecha con exito...\n";
                    } catch (Exception er) {
                        resultado = "ERROR:\n" + er + "\n";
                    }
                } else //Sino, fue una consulta...
                {
                    ResultSet rs = sentencia.executeQuery(recibe);
                    cols = (rs.getMetaData()).getColumnCount();
                    while (rs.next()) {
                        for (pos = 1; pos <= cols; pos++) {
                            resultado += rs.getString(pos) + " ";
                        }
                        resultado += "\n";
                    }
                }
                fsalida.writeUTF(resultado);
                lblmensaje.setText("Respuesta Enviada...");
                panel.add(lblmensaje);
                lblmensaje.setBounds(170, 80, 300, 30);
            }
        } catch (Exception er) {
            JOptionPane.showMessageDialog(null, "Error de recepcion " + er);
        }
    }

    public static void main(String[] args) {
        new Servidor().show();
    }
}