package hr.algebra.panel;

import hr.algebra.frame.MainFrame;
import hr.algebra.utils.MessageUtils;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Kevin Furjan
 */
public class LoginPanel extends javax.swing.JPanel {

    private static final String CANNOT_INITIATE_THE_FORM = "Cannot initiate the form";
    private static final String UNRECOVERABLE_ERROR = "Unrecoverable error";

    private List<JTextComponent> validationFields;
    private List<JLabel> errorLabels;

    /**
     * Creates new form LoginPanel
     */
    public LoginPanel() {
        initComponents();
        init();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        lblUsername = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        txtPassword = new javax.swing.JTextField();
        btnLogin = new javax.swing.JButton();
        lblUsernameError = new javax.swing.JLabel();
        lblPasswordError = new javax.swing.JLabel();

        java.awt.GridBagLayout layout = new java.awt.GridBagLayout();
        layout.columnWidths = new int[] {0, 80, 0, 80, 0, 80, 0, 80, 0, 80, 0};
        layout.rowHeights = new int[] {0, 20, 0, 20, 0, 20, 0, 20, 0, 20, 0, 20, 0};
        layout.columnWeights = new double[] {1.1};
        layout.rowWeights = new double[] {1.0};
        setLayout(layout);

        lblUsername.setText("Username:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(lblUsername, gridBagConstraints);

        lblPassword.setText("Password:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.weightx = 0.2;
        add(lblPassword, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(txtUsername, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.weightx = 0.2;
        add(txtPassword, gridBagConstraints);

        btnLogin.setText("Log in");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(btnLogin, gridBagConstraints);

        lblUsernameError.setForeground(new java.awt.Color(255, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        add(lblUsernameError, gridBagConstraints);

        lblPasswordError.setForeground(new java.awt.Color(255, 0, 51));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 2;
        add(lblPasswordError, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed

        if (formValid()) {
            MainFrame topFrame = (MainFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.getTpContent().add("New login", new LoginPanel());
        }
    }//GEN-LAST:event_btnLoginActionPerformed

    private void init() {

        try {
            initValidation();
        } catch (Exception ex) {
            Logger.getLogger(LoginPanel.class.getName()).log(Level.SEVERE, null, ex);
            MessageUtils.showErrorMessage(UNRECOVERABLE_ERROR, CANNOT_INITIATE_THE_FORM);
            System.exit(1);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblPasswordError;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JLabel lblUsernameError;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables

    private void initValidation() {

        errorLabels = Arrays.asList(lblPasswordError, lblUsernameError);
        validationFields = Arrays.asList(txtUsername, txtPassword);
    }

    private boolean formValid() {
        boolean formOk = true;

        for (int i = 0; i < validationFields.size(); i++) {
            formOk &= !validationFields
                    .get(i)
                    .getText()
                    .trim()
                    .isEmpty();
            errorLabels
                    .get(i)
                    .setText(validationFields
                            .get(i)
                            .getText()
                            .trim()
                            .isEmpty() ? "X" : "");
        }

        return formOk;
    }
}
