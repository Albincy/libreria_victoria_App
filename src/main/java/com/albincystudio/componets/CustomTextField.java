package com.albincystudio.componets;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class CustomTextField extends JTextField {
    private final Border defaultBorder;
    private final Border focusBorder;

    public CustomTextField(int columns, int theme){
        super(columns);
        String colorBord;
        String colorFocusBord;

        if (theme <= 0){
            setBackground(Color.decode("#F0F0F0"));
            colorBord = "#CCCCCC";
            colorFocusBord = "#0066CC";
            setCaretColor(Color.BLACK);
            setForeground(Color.BLACK);
        }else{
            setBackground(Color.decode("#2C2C2C"));
            colorBord = "#808080";
            colorFocusBord = "#FF5000";
            setCaretColor(Color.WHITE);
            setForeground(Color.WHITE);
        }
        setFont(new Font("Arial", Font.PLAIN, 14));

        //default border
        defaultBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.decode(colorBord), 1),
                BorderFactory.createEmptyBorder(2, 10, 1, 10)
        );

        //color border
        focusBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.decode(colorFocusBord), 2),
                BorderFactory.createEmptyBorder(2, 10, 1, 10)
        );
        setBorder(defaultBorder);

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                setBorder(focusBorder);
            }

            @Override
            public void focusLost(FocusEvent e) {
                setBorder(defaultBorder);
            }
        });

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
}
