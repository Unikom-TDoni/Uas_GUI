import javax.swing.JOptionPane;
import java.util.LinkedHashMap;
import javax.swing.event.ChangeEvent;
import javax.swing.text.JTextComponent;
import edu.kemahasiswaan.table.Pengguna;
import javax.swing.event.ChangeListener;
import edu.kemahasiswaan.handler.JTextFieldHandler;
import edu.kemahasiswaan.response.PenggunaResponse;
import edu.kemahasiswaan.controller.PenggunaController;
import edu.kemahasiswaan.validation.PenggunaLoginValidation;
import edu.kemahasiswaan.validation.PenggunaRegisterValidation;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Aris Prabowo
 */
public class LoginFrame extends javax.swing.JFrame {
    
    private int maxLoginAttempt = 2;
    
    public PenggunaController _penggunaController;
    public JTextFieldHandler<Pengguna> _loginTextHandler;
    public JTextFieldHandler<Pengguna> _registerTextHandler;
    
    public PenggunaLoginValidation _loginFormValidation;
    public PenggunaRegisterValidation _registerFormValidation;
    
    /**
     * Creates new form LoginFrame
     */
    public LoginFrame() 
    {
        initComponents();
        _loginTextHandler = new JTextFieldHandler<>(new LinkedHashMap<Pengguna, JTextComponent>()
        {{
            put(Pengguna.Username, LoginUsername);
            put(Pengguna.Password, LoginPassword);
        }});
        
        _registerTextHandler = new JTextFieldHandler<>(new LinkedHashMap<Pengguna, JTextComponent>(){{
            put(Pengguna.Username, RegisterUsername);
            put(Pengguna.Password, RegisterPassword);
            put(Pengguna.PasswordValidation, RegisterPassword1);
        }});
        
        _loginFormValidation = new PenggunaLoginValidation(_loginTextHandler);
        _registerFormValidation = new PenggunaRegisterValidation(_registerTextHandler);
        _penggunaController = new PenggunaController(_loginFormValidation, _registerFormValidation);
        
        TabLoginRegister.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if(TabLoginRegister.getSelectedIndex() == 1)
                    _loginTextHandler.ResetAllText();
                else
                    _registerTextHandler.ResetAllText();
            }
        });
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TabLoginRegister = new javax.swing.JTabbedPane();
        PanelLogin1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        LoginUsername = new javax.swing.JTextField();
        LoginPassword = new javax.swing.JPasswordField();
        jLabel7 = new javax.swing.JLabel();
        ButtonLogin = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        PanelLogin = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        RegisterUsername = new javax.swing.JTextField();
        RegisterPassword = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        ButtonRegister = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        RegisterPassword1 = new javax.swing.JPasswordField();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        TabLoginRegister.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                TabLoginRegisterStateChanged(evt);
            }
        });

        PanelLogin1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        jLabel5.setText("Login");

        jLabel6.setText("Username");

        jLabel7.setText("Password");

        ButtonLogin.setText("Login");
        ButtonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonLoginActionPerformed(evt);
            }
        });

        jLabel8.setForeground(new java.awt.Color(51, 51, 255));

        javax.swing.GroupLayout PanelLogin1Layout = new javax.swing.GroupLayout(PanelLogin1);
        PanelLogin1.setLayout(PanelLogin1Layout);
        PanelLogin1Layout.setHorizontalGroup(
            PanelLogin1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelLogin1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(PanelLogin1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(LoginUsername)
                    .addComponent(LoginPassword)
                    .addComponent(ButtonLogin, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        PanelLogin1Layout.setVerticalGroup(
            PanelLogin1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelLogin1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LoginUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LoginPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ButtonLogin)
                .addGap(22, 22, 22)
                .addComponent(jLabel8)
                .addGap(125, 125, 125))
        );

        TabLoginRegister.addTab("Login", PanelLogin1);

        PanelLogin.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        jLabel1.setText("Login");

        jLabel2.setText("Username");

        jLabel3.setText("Password");

        ButtonRegister.setText("Register");
        ButtonRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonRegisterActionPerformed(evt);
            }
        });

        jLabel4.setForeground(new java.awt.Color(51, 51, 255));

        jLabel9.setText("Konfirmasi Password");

        javax.swing.GroupLayout PanelLoginLayout = new javax.swing.GroupLayout(PanelLogin);
        PanelLogin.setLayout(PanelLoginLayout);
        PanelLoginLayout.setHorizontalGroup(
            PanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelLoginLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(PanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(PanelLoginLayout.createSequentialGroup()
                        .addGroup(PanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(RegisterPassword1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(RegisterUsername, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(RegisterPassword, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ButtonRegister, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        PanelLoginLayout.setVerticalGroup(
            PanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelLoginLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addGap(45, 45, 45)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RegisterUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RegisterPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RegisterPassword1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(ButtonRegister)
                .addGap(10, 10, 10)
                .addComponent(jLabel4)
                .addGap(34, 34, 34))
        );

        TabLoginRegister.addTab("Register", PanelLogin);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(272, Short.MAX_VALUE)
                .addComponent(TabLoginRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(251, 251, 251))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(TabLoginRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(99, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButtonRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonRegisterActionPerformed
        PenggunaResponse response = _penggunaController.Register();
        if(response == null) return;
        JOptionPane.showMessageDialog(null, 
            "Register Berhasi", "Error", 
            JOptionPane.INFORMATION_MESSAGE
        );
        _registerTextHandler.ResetAllText();
        NavigateTo(0);
        response.GenerateResultFromValidation(response.GetResult().getFirst());
    }//GEN-LAST:event_ButtonRegisterActionPerformed

    private void ButtonLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonLoginActionPerformed
        PenggunaResponse response = _penggunaController.CheckLogin();
        if((boolean)response.GetResult().getFirst().get(Pengguna.Username))
        {
            new FormUtama().setVisible(true);
            this.setVisible(false);
        }
        else 
        {
            if(maxLoginAttempt-- == 0)
            {
                JOptionPane.showMessageDialog(null, 
                "Terlalu Banyak melakukan login gagal system akan keluar", "Error", 
                JOptionPane.INFORMATION_MESSAGE
                );
                System.exit(0);
            }
            else 
            {
                JOptionPane.showMessageDialog(null, 
                "Silahkan Daftar Terlebih Dahulu", "Error", 
                JOptionPane.INFORMATION_MESSAGE
                );
            }
        }
    }//GEN-LAST:event_ButtonLoginActionPerformed

    private void TabLoginRegisterStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_TabLoginRegisterStateChanged
        
    }//GEN-LAST:event_TabLoginRegisterStateChanged

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
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }

    public void NavigateTo(int Destination)
    {
        TabLoginRegister.setSelectedIndex(Destination);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonLogin;
    private javax.swing.JButton ButtonRegister;
    private javax.swing.JPasswordField LoginPassword;
    private javax.swing.JTextField LoginUsername;
    private javax.swing.JPanel PanelLogin;
    private javax.swing.JPanel PanelLogin1;
    private javax.swing.JPasswordField RegisterPassword;
    private javax.swing.JPasswordField RegisterPassword1;
    private javax.swing.JTextField RegisterUsername;
    private javax.swing.JTabbedPane TabLoginRegister;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    // End of variables declaration//GEN-END:variables
}
