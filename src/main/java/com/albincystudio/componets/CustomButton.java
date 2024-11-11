package com.albincystudio.componets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomButton extends JButton {
    public CustomButton(String text, int theme) {
        super(text);
        String bg_entered;
        String bg_exited;
        String bg_presed;

        if (theme <=0){
            setBackground(Color.decode("#0072e4"));
            setForeground(Color.white);
            bg_entered = "#0080ff";
            bg_exited = "#0072e4";
            bg_presed = "#005fbe";
        }else{
            setBackground(Color.decode("#FF6B27"));
            setForeground(Color.white);
            bg_entered = "#FF8147";
            bg_exited = "#FF6B27";
            bg_presed = "#ff5000";
        }

        setContentAreaFilled(false);
        setFont(new Font("Arial", Font.BOLD, 16));
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);

        //hover
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(Color.decode(bg_entered));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(Color.decode(bg_exited));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(Color.decode(bg_presed));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(Color.decode(bg_entered));
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getModel().isPressed()) {
            g2.setColor(getBackground().darker());
        } else {
            g2.setColor(getBackground());
        }

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground().darker());
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
        g2.dispose();
    }
}