package com.albincystudio.componets;

import com.albincystudio.root.configuration;
import com.kitfox.svg.SVGUniverse;
import com.kitfox.svg.app.beans.SVGIcon;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NotificationLabel extends JLabel {

    private static final SVGUniverse svgUniverse = new SVGUniverse();
    private static final Logger LOGGER = Logger.getLogger(configuration.class.getName());

    public NotificationLabel(String text, int theme, int type) {
        super(text);

        setOpaque(false);
        setFont(new Font("Arial", Font.BOLD, 14));
        setHorizontalAlignment(SwingConstants.CENTER);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setIconTextGap(20);
        SVGIcon icon = new SVGIcon();
        URL resourceUrl;

        FontMetrics metrics = getFontMetrics(getFont());
        int textWidth = metrics.stringWidth(text);
        int textHeight = metrics.getHeight();
        int dimensionIcon = 24;
        int widthLabel = textWidth + dimensionIcon + getIconTextGap() + 45;
        int heightLabel = Math.max(textHeight, dimensionIcon) + 24;
        this.setSize(widthLabel, heightLabel);

        try {
            if (theme <= 0) {
                // Tema claro
                resourceUrl = switch (type) {
                    case 0 -> getClass().getResource("/assets/btn_icon/not_type_1_b.svg");
                    case 1 -> getClass().getResource("/assets/btn_icon/not_type_2_b.svg");
                    default -> getClass().getResource("/assets/btn_icon/not_type_3_b.svg");
                };
                setBackground(Color.decode("#F0F0F0"));
                setForeground(Color.BLACK);
            } else {
                // Tema oscuro
                resourceUrl = switch (type) {
                    case 0 -> getClass().getResource("/assets/btn_icon/not_type_1_w.svg");
                    case 1 -> getClass().getResource("/assets/btn_icon/not_type_2_w.svg");
                    default -> getClass().getResource("/assets/btn_icon/not_type_3_w.svg");
                };
                setBackground(Color.decode("#2C2C2C"));
                setForeground(Color.WHITE);
            }

            if (resourceUrl != null) {
                URI uri = resourceUrl.toURI();
                svgUniverse.loadSVG(uri.toURL());
                icon.setSvgURI(uri);

                icon.setScaleToFit(true);
                icon.setPreferredSize(new Dimension(dimensionIcon, dimensionIcon));
                icon.setAntiAlias(true);
            }
            this.setIcon(icon);

        }catch (Exception e){
            LOGGER.log(Level.SEVERE, "Error load a icon and color theme", e);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        int shadowSize = 4;
        g2.setColor(new Color(0, 0, 0, 30));
        g2.fillRoundRect(0, shadowSize, getWidth(), getHeight() - shadowSize, 30, 30);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight() - shadowSize, 30, 30);
        super.paintComponent(g);
    }

    public void showNotification(JPanel panel, NotificationLabel not) {
        Timer timerAnim = new Timer(3, e -> {
            Point location = not.getLocation();
            if (location.y > 30 - 5) {
                not.setLocation(location.x, location.y - 1);
            } else {
                ((Timer) e.getSource()).stop();
            }
        });
        timerAnim.start();
        panel.add(this);

        Timer timer = new Timer(3000, e -> {
            setVisible(false);
            panel.remove(this);
            panel.revalidate();
            panel.repaint();
        });
        timer.setRepeats(false);
        timer.start();
    }
}
