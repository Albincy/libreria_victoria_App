package com.albincystudio.componets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;


public class CustomButtonSmall extends JButton {

    private final Icon view_pass_on;
    private final Icon view_pass_off;
    private final Icon view_pass_on_n;
    private final Icon view_pass_off_n;
    private boolean changeIcon = false;

    public CustomButtonSmall(int theme){
        URL iconOnURL = getClass().getResource("/assets/btn_icon/view_pass_on.png");
        URL iconOffURL = getClass().getResource("/assets/btn_icon/view_pass_off.png");
        URL iconOnURL_n = getClass().getResource("/assets/btn_icon/view_pass_on_n.png");
        URL iconOffURL_n = getClass().getResource("/assets/btn_icon/view_pass_off_n.png");
        String mouseEntered;
        String mouseExited;

        if (theme <= 0){
            mouseEntered = "#dedede";
            mouseExited = "#d5d5d5";
        }else{
            mouseEntered = "#808080";
            mouseExited = "#2C2C2C";
        }

        if (iconOnURL != null && iconOffURL != null) {
            view_pass_on = new ImageIcon(iconOnURL);
            view_pass_off = new ImageIcon(iconOffURL);
        } else {
            view_pass_on = new ImageIcon();
            view_pass_off = new ImageIcon();
            System.err.println("Error: Fallo al cargar iconos");
        }

        if (iconOnURL_n != null && iconOffURL_n != null){
            view_pass_on_n = new ImageIcon(iconOnURL_n);
            view_pass_off_n = new ImageIcon(iconOffURL_n);
        }else {
            view_pass_on_n = new ImageIcon();
            view_pass_off_n = new ImageIcon();
            System.err.println("Error: Fallo al cargar iconos");
        }

        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        setBackground(Color.decode(mouseExited));

        if (theme <=0){
            setIcon(view_pass_off_n);
        }else {
            setIcon(view_pass_off);
        }

        //hover
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(Color.decode(mouseEntered));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(Color.decode(mouseExited));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (theme <= 0){
                    if (changeIcon){
                        setIcon(view_pass_off_n);
                        changeIcon = false;
                    }else{
                        setIcon(view_pass_on_n);
                        changeIcon = true;
                    }
                }else {
                    if (changeIcon){
                        setIcon(view_pass_off);
                        changeIcon = false;
                    }else{
                        setIcon(view_pass_on);
                        changeIcon = true;
                    }
                }
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

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground().darker());
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
        g2.dispose();
    }
}
