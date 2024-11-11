package com.albincystudio.ui;

import com.kitfox.svg.SVGUniverse;
import com.kitfox.svg.app.beans.SVGIcon;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.net.URI;
import java.net.URL;
import java.util.Objects;

public class HM_NavigationBar extends JPanel {
    private JButton activeButton = null;

    public HM_NavigationBar(int theme, String username, String version){
        setBackground(Color.decode(theme(0,theme)));//292929
        setPreferredSize(new Dimension(250,0));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 20));

        //path btn
        String pathProfile = "/assets/ui/icons_profile.svg";
        String pathProfile_d = "/assets/ui/icons_profile_d.svg";
        String pathBtn_Home_on;
        String pathBtn_Home_off = "/assets/ui/icons_home_off.svg";
        String pathBtn_Product_on;
        String pathBtn_Product_off = "/assets/ui/icons_product_off.svg";
        String pathBtn_Reports_on;
        String pathBtn_Reports_off = "/assets/ui/icons_reports_off.svg";
        String pathBtn_Settings_on;
        String pathBtn_Settings_off = "/assets/ui/icons_settings_off.svg";
        String versionColor;
        if (theme <=0){
            pathBtn_Home_on = "/assets/ui/icons_home_on_w.svg";
            pathBtn_Product_on = "/assets/ui/icons_product_on_w.svg";
            pathBtn_Reports_on = "/assets/ui/icons_reports_on_w.svg";
            pathBtn_Settings_on = "/assets/ui/icons_settings_on_w.svg";
            versionColor = "#000000";
        }else{
            pathBtn_Home_on = "/assets/ui/icons_home_on.svg";
            pathBtn_Product_on = "/assets/ui/icons_product_on.svg";
            pathBtn_Reports_on = "/assets/ui/icons_reports_on.svg";
            pathBtn_Settings_on = "/assets/ui/icons_settings_on.svg";
            versionColor = "#FFFFFF";
        }
        //profile
        add(createProfileSection(username,theme,pathProfile,pathProfile_d));
        add(Box.createRigidArea(new Dimension(0, 30)));

        //btn menu
        add(createNavButton("Principal",theme,pathBtn_Home_off,pathBtn_Home_on));
        add(Box.createRigidArea(new Dimension(0, 20)));

        add(createNavButton("Producto",theme,pathBtn_Product_off,pathBtn_Product_on));
        add(Box.createRigidArea(new Dimension(0, 20)));

        add(createNavButton("Reportes",theme,pathBtn_Reports_off,pathBtn_Reports_on));
        add(Box.createRigidArea(new Dimension(0, 20)));

        add(Box.createVerticalGlue());
        add(createNavButton("Configuración",theme,pathBtn_Settings_off,pathBtn_Settings_on));
        add(Box.createRigidArea(new Dimension(0, 30)));

        JLabel versionJ = new JLabel(version);
        versionJ.setAlignmentX(SwingConstants.LEFT);
        versionJ.setForeground(Color.decode("#7E7E7E"));
        //versionJ.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 10));
        versionJ.setSize(200,30);
        add(versionJ);
        add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private JPanel createProfileSection(String username, int thm, String pathIcon, String pathIconsD){
        JPanel profileJpanel = new JPanel(){
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();
            }
        };
        profileJpanel.setOpaque(false);
        profileJpanel.setBackground(Color.decode(theme(1,thm)));
        profileJpanel.setLayout(new GridBagLayout());
        profileJpanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 40, 10, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel profileIcon = new JLabel(IconSvg(pathIcon,40,40));
        //new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/ui/lg_username.png")))
        profileJpanel.add(profileIcon, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(10, 20, 10, 10);
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel profileName = new JLabel(username);
        profileName.setForeground(Color.decode(theme(2,thm)));
        profileName.setFont(new Font("Arial", Font.BOLD, 16));
        profileName.setHorizontalAlignment(SwingConstants.LEFT);
        profileJpanel.add(profileName,gbc);
        profileJpanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        profileJpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                profileIcon.setIcon(IconSvg(pathIconsD,40,40));
                if (thm <= 0){
                    profileName.setForeground(Color.decode("#2a2a2a"));
                }else{
                    profileName.setForeground(Color.decode("#505050"));
                }

            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                profileIcon.setIcon(IconSvg(pathIcon,40,40));
                profileName.setForeground(Color.decode(theme(2,thm)));
            }
        });

        return profileJpanel;
    }

    private JButton createNavButton(String text, int thm, String pathIconOff, String pathIconOn) {
        JButton button = new JButton(text){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
                g2d.dispose();
            }
        };

        SVGIcon iconOff = IconSvg(pathIconOff,32,32);
        SVGIcon iconOn = IconSvg(pathIconOn,32,32);

        button.putClientProperty("iconOff", iconOff);
        button.putClientProperty("iconOn", iconOn);
        button.setIcon(iconOff);

        button.setHorizontalTextPosition(SwingConstants.RIGHT);
        button.setVerticalTextPosition(SwingConstants.CENTER);
        button.setHorizontalAlignment(SwingConstants.LEFT);

        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBackground(Color.decode(theme(0,thm)));
        button.setForeground(Color.decode(theme(2,thm)));
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 10));
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setIconTextGap(20);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode(theme(1,thm)));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode(theme(0, thm)));
            }
        });

        button.addActionListener(e -> {
            setActiveButton(button, thm);
            if (thm <= 0){
                button.setForeground(Color.decode("#0072e4"));
            }else{
                button.setForeground(Color.decode("#FF6B27"));
            }
        });
        return button;
    }

    private void setActiveButton(JButton button, int thm) {
        if (activeButton != null && activeButton != button) {
            SVGIcon iconOff = (SVGIcon) activeButton.getClientProperty("iconOff");
            activeButton.setIcon(iconOff);
            activeButton.setForeground(Color.decode(theme(2,thm)));
        }
        activeButton = button;
        SVGIcon iconOn = (SVGIcon) button.getClientProperty("iconOn");
        activeButton.setIcon(iconOn);
    }

    private String theme(int component, int theme){
        //bg - 0, btnMenu - 1, text - >=2
        if (theme <=0 ){
            if (component==0){
                return "#E0E0E0";
            }else if(component==1){
                return "#D3D3D3";
            }else{
                return "#4A4A4A";
            }
        }else{
            if (component==0){
                return "#141415";
            }else if(component==1){
                return "#1B1B1B";
            }else{
                return "#959595";
            }
        }
    }

    private SVGIcon IconSvg(String path, int wPx, int hPx){
        SVGUniverse svgUniverse = new SVGUniverse();
        URL svgUrl = getClass().getResource(path);
        URI svgUri = svgUniverse.loadSVG(svgUrl);

        SVGIcon icon = new SVGIcon();
        icon.setSvgURI(svgUri);
        icon.setScaleToFit(true);
        icon.setPreferredSize(new Dimension(wPx, hPx));
        icon.setAntiAlias(true);
        return  icon;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
}
