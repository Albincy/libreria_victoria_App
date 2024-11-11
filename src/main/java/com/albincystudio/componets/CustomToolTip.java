package com.albincystudio.componets;

import javax.swing.*;
import java.awt.*;

public class CustomToolTip extends JToolTip {
    private static final int MAX_WIDTH = 300;
    private static final int PADDING = 10;

    public CustomToolTip(){
        setUI(new MultiLineToolTipUI());
    }

    @Override
    public Dimension getPreferredSize() {
        return super.getPreferredSize();
    }

    private class MultiLineToolTipUI extends javax.swing.plaf.basic.BasicToolTipUI {
        private String[] lines;

        @Override
        public void paint(Graphics g, JComponent c) {
            g.setFont(c.getFont());
            FontMetrics metrics = g.getFontMetrics();
            Dimension size = c.getSize();
            g.setColor(c.getBackground());
            g.fillRect(0, 0, size.width, size.height);
            g.setColor(c.getForeground());
            if (lines != null) {
                for (int i = 0; i < lines.length; i++) {
                    g.drawString(lines[i], PADDING, PADDING + (metrics.getHeight() * (i + 1)));
                }
            }
            g.setColor(Color.GRAY);
            g.drawRect(0, 0, size.width - 1, size.height - 1);
        }

        @Override
        public Dimension getPreferredSize(JComponent c) {
            FontMetrics metrics = c.getFontMetrics(c.getFont());
            String tipText = ((JToolTip) c).getTipText();
            if (tipText == null) {
                return new Dimension(0, 0);
            }
            lines = breakIntoLines(tipText, metrics);
            int maxWidth = 0;
            for (String line : lines) {
                int width = SwingUtilities.computeStringWidth(metrics, line);
                if (maxWidth < width) {
                    maxWidth = width;
                }
            }
            int height = metrics.getHeight() * lines.length;
            return new Dimension(maxWidth + 2 * PADDING, height + 2 * PADDING);
        }

        private String[] breakIntoLines(String text, FontMetrics metrics) {
            String[] words = text.split(" ");
            StringBuilder line = new StringBuilder(words[0]);
            StringBuilder newText = new StringBuilder();
            for (int i = 1; i < words.length; i++) {
                if (SwingUtilities.computeStringWidth(metrics, line.toString() + " " + words[i]) < MAX_WIDTH) {
                    line.append(" ").append(words[i]);
                } else {
                    newText.append(line).append("\n");
                    line = new StringBuilder(words[i]);
                }
            }
            newText.append(line);
            return newText.toString().split("\n");
        }
    }
}
