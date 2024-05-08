package co.edu.uptc.views.customComponents;

import java.awt.Font;

import javax.swing.JLabel;

public class CustomLabel extends JLabel {
    public CustomLabel(String text){
        super(text);
        this.setVerticalAlignment(CENTER);
        this.setOpaque(false);
        Font font = new Font("ARIAL", Font.PLAIN, 20);
        this.setFont(font);
        
    }

    public CustomLabel(String text, int horizontalAlignment){
        super(text, horizontalAlignment);
        this.setVerticalAlignment(CENTER);
        this.setOpaque(false);
        Font font = new Font("ARIAL", Font.PLAIN, 20);
        this.setFont(font);
    }
}
