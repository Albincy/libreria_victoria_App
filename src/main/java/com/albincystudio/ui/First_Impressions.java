package com.albincystudio.ui;

import javax.swing.*;
import java.awt.*;

public class First_Impressions extends JFrame{
    public First_Impressions() {
        setTitle("First Impressions");
        setSize(1280, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Icon
        Image icon_app = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/assets/Icon.png"));
        setIconImage(icon_app);

        //panel
        FI_PanelStepFirst currentPanel = new FI_PanelStepFirst(this);
        add(currentPanel, BorderLayout.CENTER);
        SwingUtilities.invokeLater(() -> getContentPane().requestFocusInWindow());

    }

    public static void showWindows(){
        SwingUtilities.invokeLater(() -> {
            First_Impressions window = new First_Impressions();
            window.setVisible(true);
        });
    }

    public void ChangePanel(JPanel newPanel){
        getContentPane().removeAll();
        add(newPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
        SwingUtilities.invokeLater(() -> getContentPane().requestFocusInWindow());
    }
}
