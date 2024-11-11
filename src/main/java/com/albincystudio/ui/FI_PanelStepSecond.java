package com.albincystudio.ui;

import com.albincystudio.componets.CustomButton;
import com.albincystudio.componets.CustomTextField;
import com.albincystudio.componets.CustomToggleButton;
import com.albincystudio.componets.CustomToolTip;
import com.albincystudio.database.users.InsertsUsers;
import com.albincystudio.root.configuration;
import com.albincystudio.root.root;
import com.albincystudio.users.FirstImpressions_data;
import com.albincystudio.users.controllerUsers;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FI_PanelStepSecond extends JPanel {

    private final First_Impressions parentFrame;
    private static final Logger LOGGER = Logger.getLogger(configuration.class.getName());
    private BufferedImage lg_W;

    public FI_PanelStepSecond(FirstImpressions_data data, First_Impressions frame){
        this.parentFrame = frame;
        //configuration con = new configuration();
        final Properties languageSystem;
        //fonts and settings
        Font fontSystem = new Font("Arial", Font.BOLD, 16);
        Font fontSystem2 = new Font("Arial", Font.BOLD, 14);
        Font fontCredit = new Font("Arial", Font.PLAIN, 14);

        UIManager.put("ToolTip.background", new ColorUIResource(Color.darkGray));
        UIManager.put("ToolTip.foreground", new ColorUIResource(Color.white));
        UIManager.put("ToolTip.font", fontCredit);
        UIManager.put("ToolTip.border", BorderFactory.createLineBorder(Color.GRAY));
        ToolTipManager.sharedInstance().setInitialDelay(0);

        //panel config
        setLayout(null);
        setBackground(Color.decode("#141415"));

        //load properties language
        languageSystem = new Properties();
        try (InputStream input = getClass().getResourceAsStream("/lang/lang.properties")) {
            if (input == null) {
                System.err.println("Error al encontrar archivo");
                return;
            }
            languageSystem.load(input);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error load properties language", e);
        }

        //load img
        try {
            lg_W = ImageIO.read(Objects.requireNonNull(getClass().getResource("/assets/logo_w.png")));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error load a icon", e);
        }

        //other components - welcome
        JLabel label_welcome = new JLabel(languageSystem.getProperty("fi_welcome"), SwingConstants.CENTER);
        label_welcome.setForeground(Color.decode("#959595"));
        label_welcome.setFont(fontSystem);
        label_welcome.setSize(label_welcome.getPreferredSize());
        add(label_welcome);

        //other components - context
        JLabel label_contextWel = new JLabel(languageSystem.getProperty("fi_context"), SwingConstants.CENTER);
        label_contextWel.setForeground(Color.decode("#F9F9F9"));
        label_contextWel.setFont(fontSystem);
        label_contextWel.setSize(label_contextWel.getPreferredSize());
        add(label_contextWel);

        //other components - code access
        JLabel label_codeAccess = new JLabel(languageSystem.getProperty("fi_codeAccessLabel"), SwingConstants.LEFT);
        label_codeAccess.setForeground(Color.decode("#959595"));
        label_codeAccess.setFont(fontSystem);
        label_codeAccess.setSize(400,30);
        add(label_codeAccess);

        CustomTextField txt_codeAccess = new CustomTextField(20,1);
        txt_codeAccess.setSize(400, 30);
        add(txt_codeAccess);

        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/btn_icon/i.png")));
        JLabel question_txtCodeAccess = new JLabel(icon){
            @Override
            public JToolTip createToolTip() {
                return new CustomToolTip();
            }
        };
        question_txtCodeAccess.setForeground(Color.decode("#959595"));
        question_txtCodeAccess.setFont(fontSystem);
        question_txtCodeAccess.setSize(24,24);
        question_txtCodeAccess.setToolTipText(languageSystem.getProperty("fi_codeAccess_function"));
        add(question_txtCodeAccess);

        //other components - save backup DB
        CustomToggleButton toggleButton_bk = new CustomToggleButton();
        toggleButton_bk.setSize(30,30);
        toggleButton_bk.setBackground(Color.decode("#2C2C2C"));
        add(toggleButton_bk);

        JLabel label_txt_save_bk = new JLabel(languageSystem.getProperty("fi_saveBackcupDB"), SwingConstants.LEFT);
        label_txt_save_bk.setForeground(Color.decode("#959595"));
        label_txt_save_bk.setFont(fontSystem2);
        label_txt_save_bk.setSize(600,20);
        add(label_txt_save_bk);

        //other components - Theme System
        CustomToggleButton toggleButton_theme_l = new CustomToggleButton();
        toggleButton_theme_l.setSize(30,30);
        toggleButton_theme_l.setBackground(Color.decode("#2C2C2C"));
        add(toggleButton_theme_l);

        JLabel label_txt_theme_l = new JLabel(languageSystem.getProperty("fi_themeWhite"), SwingConstants.LEFT);
        label_txt_theme_l.setForeground(Color.decode("#959595"));
        label_txt_theme_l.setFont(fontSystem2);
        label_txt_theme_l.setSize(100,20);
        add(label_txt_theme_l);

        CustomToggleButton toggleButton_theme_d = new CustomToggleButton();
        toggleButton_theme_d.setSize(30,30);
        toggleButton_theme_d.setBackground(Color.decode("#2C2C2C"));
        add(toggleButton_theme_d);

        JLabel label_txt_theme_d = new JLabel(languageSystem.getProperty("fi_themeDark"), SwingConstants.LEFT);
        label_txt_theme_d.setForeground(Color.decode("#959595"));
        label_txt_theme_d.setFont(fontSystem2);
        label_txt_theme_d.setSize(100,20);
        add(label_txt_theme_d);

        toggleButton_theme_l.addActionListener(e -> {
            if (toggleButton_theme_l.isSelected()) {
                toggleButton_theme_d.setSelected(false);
                }
            });

        toggleButton_theme_d.addActionListener(e -> {
            if (toggleButton_theme_d.isSelected()) {
                toggleButton_theme_l.setSelected(false);
            }
        });

        //other components button save
        CustomButton customButton_save = new CustomButton(languageSystem.getProperty("fi_btn_save"),1);
        customButton_save.setSize(150,40);
        customButton_save.addActionListener(e -> Save_configuration(toggleButton_bk, toggleButton_theme_l, toggleButton_theme_d, data));
        add(customButton_save);

        //credits
        JLabel label_reminder = new JLabel(languageSystem.getProperty("fi_postdate_c"), SwingConstants.LEFT);
        label_reminder.setForeground(Color.decode("#9e9e9e"));
        label_reminder.setFont(fontCredit);
        label_reminder.setSize(400,20);
        add(label_reminder);

        JLabel label_copy = new JLabel(languageSystem.getProperty("fi_copy"), SwingConstants.RIGHT);
        label_copy.setForeground(Color.decode("#9e9e9e"));
        label_copy.setFont(fontCredit);
        label_copy.setSize(600, 20);
        add(label_copy);
    }

    private void Save_configuration(JToggleButton save_bk, JToggleButton thm_l, JToggleButton thm_d, FirstImpressions_data data){
        configuration configuration_save = new configuration();
        InsertsUsers sql_users = new InsertsUsers();
        String hexUser = null;
        controllerUsers cu = new controllerUsers();

        configuration_save.setSavebackcupDB(save_bk.isSelected());
        int themeSystem = resultTheme(thm_l, thm_d);
        configuration_save.setTheme_app(themeSystem);
        configuration_save.setNewUseApp(false);
        configuration_save.saveSettings();

        hexUser = controllerUsers.generatorUuid(data.getUsername());
        sql_users.createUser(hexUser, data.getUsername(), data.getPassword(), "ROL1");
        cu.save_uuid_local(hexUser);

        root.restartApplication();
        parentFrame.dispose();
    }

    private int resultTheme(JToggleButton thm_l, JToggleButton thm_d){
        int n = 0;
        if (thm_l.isSelected() && !thm_d.isSelected()){
            n = 2;
        }else{
            n = 1;
        }
        return n;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (lg_W != null) {
            //lg_W move
            int x = (getWidth() - lg_W.getWidth()) / 2;
            int y = 50;
            g2d.drawImage(lg_W, x, y, this);
        }

        //label welcome move
        JLabel label = (JLabel) getComponent(0);
        label.setLocation((getWidth() - label.getWidth()) / 2, 50 + lg_W.getHeight() + 20);

        //label context move
        JLabel label2 = (JLabel) getComponent(1);
        label2.setLocation((getWidth() - label2.getWidth()) / 2, label.getY() + label.getHeight() + 5);

        //label code access move - txtField code access move
        JLabel label_codeAccess = (JLabel) getComponent(2);
        JTextField textField_codeAccess = (JTextField) getComponent(3);
        JLabel label_sig_code = (JLabel) getComponent(4);

        label_codeAccess.setLocation(textField_codeAccess.getX(), label2.getY() + label2.getHeight() + 50);
        textField_codeAccess.setLocation((getWidth() - textField_codeAccess.getWidth()) / 2, label_codeAccess.getY() + label_codeAccess.getHeight() + 20);
        label_sig_code.setLocation(textField_codeAccess.getX() + textField_codeAccess.getWidth() + 20, textField_codeAccess.getY() + textField_codeAccess.getHeight() / 5);

        //Toggle btn backup db
        JToggleButton toggleButton_bk = (JToggleButton) getComponent(5);
        toggleButton_bk.setLocation(textField_codeAccess.getX(),textField_codeAccess.getY() + textField_codeAccess.getHeight() + 30);

        JLabel label_txt_bk = (JLabel) getComponent(6);
        label_txt_bk.setLocation(toggleButton_bk.getX() + toggleButton_bk.getWidth() + 10, toggleButton_bk.getY() + 7);

        //Toggle btn theme li
        JToggleButton toggleButton_thm_l = (JToggleButton) getComponent(7);
        toggleButton_thm_l.setLocation(textField_codeAccess.getX(),textField_codeAccess.getY() + textField_codeAccess.getHeight() + 80);

        JLabel jLabel_thm_l = (JLabel) getComponent(8);
        jLabel_thm_l.setLocation(toggleButton_thm_l.getX() + toggleButton_thm_l.getWidth() + 10, toggleButton_thm_l.getY() + 7);

        JToggleButton toggleButton_thm_d = (JToggleButton) getComponent(9);
        toggleButton_thm_d.setLocation(jLabel_thm_l.getX() + jLabel_thm_l.getWidth() + 10, textField_codeAccess.getY() + textField_codeAccess.getHeight() + 80);

        JLabel jLabel_thm_d = (JLabel) getComponent(10);
        jLabel_thm_d.setLocation(toggleButton_thm_d.getX() + toggleButton_thm_d.getWidth() + 10,toggleButton_thm_d.getY() + 7);

        JButton button_save_info = (JButton) getComponent(11);
        button_save_info.setLocation((getWidth() - button_save_info.getWidth()) / 2, toggleButton_thm_l.getY() + toggleButton_thm_l.getHeight() + 40);

        //label text reminder
        int yPosition = getHeight() -40;
        JLabel label_reminder = (JLabel) getComponent(12);
        JLabel label_copy = (JLabel) getComponent(13);
        label_reminder.setLocation(20, yPosition);
        label_copy.setLocation(getWidth() - label_copy.getWidth() - 10, yPosition);
    }
}
