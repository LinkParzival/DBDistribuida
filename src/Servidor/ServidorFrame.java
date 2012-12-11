/*
 * ServidorFrame.java
 *
 * Created on 28-jun-2011, 15:57:35
 */
package Servidor;

/**
 *
 * @author link
 */
public class ServidorFrame extends javax.swing.JFrame {
    private SocketServidor socketServidor;
    
    /** Creates new form ServidorFrame */
    public ServidorFrame() {
        initComponents();
        this.setLocationRelativeTo(null);
        socketServidor = new SocketServidor(this.jtpMensajes);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlServidor = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtpMensajes = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Servidor DBDistribuida");
        setResizable(false);

        jlServidor.setText("Historial:");

        jtpMensajes.setBackground(new java.awt.Color(1, 1, 1));
        jtpMensajes.setEditable(false);
        jtpMensajes.setForeground(new java.awt.Color(2, 255, 0));
        jtpMensajes.setAutoscrolls(true);
        jtpMensajes.setCaretColor(new java.awt.Color(22, 253, 19));
        jScrollPane1.setViewportView(jtpMensajes);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlServidor, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlServidor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new ServidorFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jlServidor;
    private javax.swing.JTextPane jtpMensajes;
    // End of variables declaration//GEN-END:variables
}
