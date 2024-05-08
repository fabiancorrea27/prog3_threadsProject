package co.edu.uptc.views;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import co.edu.uptc.views.customComponents.CustomLabel;

public class InformationPanel extends JPanel {

    private JLabel lblTime, lblTimeValue, lblAliens, lblAliensAmount, lblAliensEliminated, lblAliensEliminatedAmount;

    public InformationPanel() {
        this.setBackground(new Color(252, 240, 56));
        initComponents();
        addComponents();
    }

    private void initComponents() {
        lblTime = new CustomLabel("Tiempo:", JLabel.CENTER);
        createLblTimeValue();
        lblAliens = new CustomLabel("Aliens:", JLabel.CENTER);
        createLblAliensAmount();
        lblAliensEliminated = new CustomLabel("Aliens eliminados:", JLabel.CENTER);
        createLblAliensEliminatedAmount();
    }

    private void createLblTimeValue() {
        lblTimeValue = new CustomLabel("00:00:00");
        lblTimeValue.setPreferredSize(new Dimension(100, 20));
    }

    private void createLblAliensAmount() {
        lblAliensAmount = new CustomLabel("0");
        lblAliensAmount.setPreferredSize(new Dimension(30, 20));
    }

    private void createLblAliensEliminatedAmount() {
        lblAliensEliminatedAmount = new CustomLabel("0");
        lblAliensEliminatedAmount.setForeground(Color.RED);
        lblAliensEliminatedAmount.setPreferredSize(new Dimension(30, 20));
    }

    private void addComponents() {
        this.add(lblTime);
        this.add(lblTimeValue);
        this.add(lblAliens);
        this.add(lblAliensAmount);
        this.add(lblAliensEliminated);
        this.add(lblAliensEliminatedAmount);
    }

    public void setTimeValue(String timeValue) {
        lblTimeValue.setText(timeValue);
    }

    public void setAliensAmount(String aliensAmount) {
        lblAliensAmount.setText(aliensAmount);
    }

    public void setAliensEliminatedAmount(String aliensEliminatedAmount) {
        lblAliensEliminatedAmount.setText(aliensEliminatedAmount);
    }
}
