package co.edu.uptc.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import co.edu.uptc.utils.ConfigValue;
import co.edu.uptc.views.customComponents.CustomLabel;

public class KeyDialog extends JDialog {
    private JLabel lblPress, lblKey;
    private JButton btnDone;
    private int keyCode;

    public KeyDialog(ActionListener actionListener) {
        this.setSize(Integer.parseInt(ConfigValue.getProperty("dialogWidth")),
                Integer.parseInt(ConfigValue.getProperty("dialogHeight")));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLayout(new GridBagLayout());
        this.getContentPane().setBackground(new Color(255, 247, 112));
        initComponents(actionListener);
        putKeyListener();
        addComponents();
    }

    private void initComponents(ActionListener actionListener) {
        lblPress = new CustomLabel(ConfigValue.getProperty("keyPressText"), JLabel.CENTER);
        lblKey = new CustomLabel("", JLabel.CENTER);
        lblKey.setPreferredSize(new Dimension(100, 20));
        lblKey.setForeground(Color.GREEN);
        btnDone = new JButton(ConfigValue.getProperty("buttonDoneText"));
        btnDone.addActionListener(actionListener);
        btnDone.setFocusable(false);
        btnDone.setBackground(new Color(252, 240, 56));
    }

    private void addComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(2, 2, 2, 2);
        this.add(lblPress, gbc);
        this.add(lblKey, gbc);
        this.add(btnDone, gbc);
    }

    private void putKeyListener() {
        this.requestFocus();
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                lblKey.setText(KeyEvent.getKeyText(e.getKeyCode()));
                keyCode = e.getKeyCode();
            }
        });
    }

    public int getKeyCode() {
        return keyCode;
    }
}
