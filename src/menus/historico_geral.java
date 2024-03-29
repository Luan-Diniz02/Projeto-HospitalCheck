/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package menus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import sistema_de_visitas.Conexao;

/**
 *
 * @author Luan Diniz
 */
public class historico_geral extends javax.swing.JFrame {

    /**
     * Creates new form cadastro
     */
    public historico_geral() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        historico_geral = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela_historico_geral = new javax.swing.JTable();
        voltar_historico = new javax.swing.JButton();
        fundo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("HISTORICO GERAL");
        setMinimumSize(new java.awt.Dimension(600, 400));
        setSize(new java.awt.Dimension(600, 400));
        getContentPane().setLayout(null);

        historico_geral.setText("GERAR HISTORICO");
        historico_geral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                historico_geralActionPerformed(evt);
            }
        });
        getContentPane().add(historico_geral);
        historico_geral.setBounds(270, 250, 140, 23);

        tabela_historico_geral.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "NOME", "CATEGORIA", "PACIENTE", "DATA", "HORARIO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tabela_historico_geral);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(20, 60, 460, 180);

        voltar_historico.setText("VOLTAR");
        voltar_historico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                voltar_historicoActionPerformed(evt);
            }
        });
        getContentPane().add(voltar_historico);
        voltar_historico.setBounds(113, 250, 90, 23);

        fundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/fundo_vs.jpeg"))); // NOI18N
        fundo.setMaximumSize(new java.awt.Dimension(0, 300));
        getContentPane().add(fundo);
        fundo.setBounds(0, 0, 520, 320);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
private void voltar_historicoActionPerformed(java.awt.event.ActionEvent evt) {                                                 
                javax.swing.SwingUtilities.getWindowAncestor(voltar_historico).dispose();

    }    
    
    private void historico_geralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_historico_geralActionPerformed
    
        String jdbcUrl = "jdbc:mysql://localhost:3306/visitas?serverTimezone=America/Sao_Paulo";
        String username = "root";
        String password = "root";

        //recebendo o modelo da tabela que foi criada através da interface gráfica
        DefaultTableModel modelo = (DefaultTableModel) tabela_historico_geral.getModel();
                
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {

            String tabela = "acompanhantes_visitantes";
            //iniciando conexao com o banco de dados 
            Conexao conexao = new Conexao();
            //chamando o método que exibe todos os acompanahntes e visitas cadastrados na tabela que foi criada
            conexao.imprimirTodosDados(tabela, connection, modelo);
            
            } catch (SQLException e) {
            e.printStackTrace();}
        }    

       /**

        // TODO add your handling code here:
    }//GEN-LAST:event_historico_geralActionPerformed

    private void voltar_historicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_voltar_historicoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_voltar_historicoActionPerformed
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(historico_geral.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(historico_geral.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(historico_geral.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(historico_geral.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new historico_geral().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel fundo;
    private javax.swing.JButton historico_geral;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabela_historico_geral;
    private javax.swing.JButton voltar_historico;
    // End of variables declaration//GEN-END:variables
}
