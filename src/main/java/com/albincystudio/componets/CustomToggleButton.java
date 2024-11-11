package com.albincystudio.componets;

import javax.swing.*;
import java.awt.*;

public class CustomToggleButton extends JToggleButton {
    public CustomToggleButton() {
        setFocusable(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int diameter = Math.min(getWidth(), getHeight()) - 4;
        int x = (getWidth() - diameter) / 2;
        int y = (getHeight() - diameter) / 2;

        if (isSelected()) {
            g2d.setColor(Color.decode("#FF5000"));
            g2d.fillOval(x, y, diameter, diameter);
        } else {
            g2d.setColor(getBackground());
            g2d.fillOval(x, y, diameter, diameter);

            g2d.setColor(getForeground());
            g2d.setStroke(new BasicStroke(1));
            g2d.drawOval(x, y, diameter - 1, diameter - 1);
        }
    }

    @Override
    protected void paintBorder(Graphics g) {
        // No need to add anything here anymore
    }
}
