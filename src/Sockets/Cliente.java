package Sockets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

class Cliente extends JFrame implements Runnable {

    JLabel lblmensaje = new JLabel("RECIBI DEL SERVIDOR LA CONSULTA:");
    JTextField txtmensaje = new JTextField(15);
    JButton btnenvia = new JButton("enviar");
    JTextArea area = new JTextArea();
    JLabel lblrcbmensaje = new JLabel("INTRODUCE LA CONSULTA");
    JPanel panel = new JPanel();
    Socket socket;
    InputStream entrada;
    DataInputStream fentrada;
    OutputStream salida;
    DataOutputStream fsalida;
    int puerto = 5432;
    String host = "192.168.4.146";

    Cliente() {
        setSize(450, 320);
        setTitle("CLIENTE");
        setVisible(true);
        conecta();
        Thread hilo = new Thread(this);
        hilo.start();

        Container contenedor = getContentPane();
        contenedor.add(panel);
        panel.setLayout(null);
        panel.add(lblmensaje);
        lblmensaje.setBounds(115, 10, 300, 30);
        panel.add(txtmensaje);
        txtmensaje.setBounds(100, 220, 200, 25);
        panel.add(btnenvia);
        btnenvia.setBounds(150, 250, 100, 25);
        panel.add(lblrcbmensaje);
        lblrcbmensaje.setBounds(130, 195, 150, 30);
        panel.add(area);
        area.setBounds(20, 40, 400, 150);
        panel.setBackground(Color.gray);
        btnenvia.addActionListener(
                new ActionListener() {

                    public void actionPerformed(ActionEvent var) {
                        try {
                            fsalida.writeUTF(txtmensaje.getText());
                        } catch (Exception er) {
                            JOptionPane.showMessageDialog(null, "Error al conectar el socket " + er);
                        }
                    }
                });
        this.setLocationRelativeTo(null);
        this.repaint();
    }

    public void conecta() {
        try {
            socket = new Socket(host, puerto);
            entrada = socket.getInputStream();
            salida = socket.getOutputStream();
            fentrada = new DataInputStream(entrada);
            fsalida = new DataOutputStream(salida);
        } catch (Exception er) {
            JOptionPane.showMessageDialog(null, "Error al conectar el socket " + er);
        }
    }

    public void run() {
        try {
            String msj = fentrada.readUTF();
            area.append(msj);
        } catch (Exception er) {
            JOptionPane.showMessageDialog(null, "Error de recepcion " + er);
        }
    }

    public static void main(String[] args) {
        new Cliente().show();
    }
}