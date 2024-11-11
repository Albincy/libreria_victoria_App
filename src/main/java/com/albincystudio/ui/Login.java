package com.albincystudio.ui;

import com.albincystudio.componets.*;
import com.albincystudio.root.configuration;
import com.albincystudio.users.authenticator;
import com.albincystudio.users.controllerUsers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Login extends JFrame {

    private static final Logger LOGGER = Logger.getLogger(configuration.class.getName());

    public Login(configuration conf){
        setTitle("Login");
        setSize(1280, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        SwingUtilities.invokeLater(() -> getContentPane().requestFocusInWindow());
        String theme = theme(conf.getTheme_app());
        String theme_txt = theme_txt(conf.getTheme_app());
        ImageIcon lg_comp;
        controllerUsers controllerUsers = new controllerUsers();

        final Properties languageSystem;
        Font fontSystem = new Font("Arial", Font.BOLD, 16);
        Font fontCredit = new Font("Arial", Font.PLAIN, 14);

        //Icon
        Image icon_app = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/assets/Icon.png"));
        setIconImage(icon_app);

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

        if (conf.getTheme_app() <= 0){
            lg_comp = new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/ui/logo_wind_w.png")));
        }else{
            lg_comp = new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/ui/logo_wind.png")));
        }

        //Panel
        JPanel panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            }
        };

        panel.setLayout(null);
        panel.setBackground(Color.decode(theme));
        panel.setSize(getWidth(),getHeight());
        add(panel, BorderLayout.CENTER);
        int w_user = 400;
        int ct_un = 400;

        JLabel label_icon = new JLabel(lg_comp);
        label_icon.setSize(257,168);
        label_icon.setLocation((getWidth() - label_icon.getWidth()) / 2,50);
        panel.add(label_icon);

        //other components - username
        JLabel label_username = new JLabel(languageSystem.getProperty("fi_usernameLabel"), SwingConstants.LEFT);
        label_username.setForeground(Color.decode(theme_txt));
        label_username.setFont(fontSystem);
        label_username.setBounds((panel.getWidth() - w_user)/2,label_icon.getHeight() + 100,w_user,20);
        panel.add(label_username);

        CustomTextField customTextField_username = new CustomTextField(20,conf.getTheme_app());
        customTextField_username.setBounds((panel.getWidth() - ct_un)/2,label_username.getY() + label_username.getHeight() + 20,ct_un,30);
        panel.add(customTextField_username);

        //other components - password
        JLabel label_password = new JLabel(languageSystem.getProperty("fi_passwordLabel"), SwingConstants.LEFT);
        label_password.setForeground(Color.decode(theme_txt));
        label_password.setFont(fontSystem);
        label_password.setBounds((panel.getWidth() - w_user)/2, customTextField_username.getY() + customTextField_username.getHeight() + 30,w_user,30);
        panel.add(label_password);

        CustomTextField customTextField_password = new CustomTextField(20,conf.getTheme_app());
        customTextField_password.setBounds((panel.getWidth() - ct_un)/2, label_password.getY() + label_password.getHeight() + 20,ct_un,30);
        panel.add(customTextField_password);
        customTextField_password.setVisible(false);

        CustomPasswordField customTextField_password_ocult = new CustomPasswordField(20,conf.getTheme_app());
        customTextField_password_ocult.setBounds((panel.getWidth() - ct_un)/2, label_password.getY() + label_password.getHeight() + 20,ct_un,30);
        panel.add(customTextField_password_ocult);

        CustomButtonSmall customButtonSmall = new CustomButtonSmall(conf.getTheme_app());
        customButtonSmall.setBounds(customTextField_password.getX() + customTextField_password.getWidth() + 20,customTextField_password.getY(),30,30);
        customButtonSmall.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                performAction_btn_eye(customTextField_password, customTextField_password_ocult);
            }
        });
        panel.add(customButtonSmall);

        //other components - button
        int wb = 150;
        CustomButton customButton = new CustomButton(languageSystem.getProperty("lg_login"),conf.getTheme_app());
        customButton.setBounds((panel.getWidth() - wb)/2, customTextField_password.getY() + customTextField_password.getHeight() + 40, wb,40);
        panel.add(customButton);

        //other components - credits
        int wc = 400;
        JLabel label_credit = new JLabel(languageSystem.getProperty("fi_copy"), SwingConstants.RIGHT);
        label_credit.setForeground(Color.decode(theme_txt));
        label_credit.setFont(fontCredit);
        label_credit.setBounds(panel.getWidth() - wc -35,panel.getHeight() - 80,wc,30);
        panel.add(label_credit);

        JLabel label_version = new JLabel(conf.getVersion(), SwingConstants.LEFT);
        label_version.setForeground(Color.decode(theme_txt));
        label_version.setFont(fontCredit);
        label_version.setBounds(20,panel.getHeight() - 80,wc,30);
        panel.add(label_version);

        customButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                PerformAction(panel, conf, customTextField_username, customTextField_password, customTextField_password_ocult, languageSystem, controllerUsers);
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                panel.setSize(getWidth(),getHeight());
                label_icon.setLocation((getWidth() - label_icon.getWidth()) / 2,50);
                label_username.setLocation((panel.getWidth() - w_user)/2,label_icon.getHeight() + 100);
                customTextField_username.setLocation((panel.getWidth() - ct_un)/2,label_username.getY() + label_username.getHeight() + 20);
                label_password.setLocation((panel.getWidth() - w_user)/2, customTextField_username.getY() + customTextField_username.getHeight() + 30);
                customTextField_password.setLocation((panel.getWidth() - ct_un)/2, label_password.getY() + label_password.getHeight() + 20);
                customTextField_password_ocult.setLocation((panel.getWidth() - ct_un)/2, label_password.getY() + label_password.getHeight() + 20);
                customButtonSmall.setLocation(customTextField_password.getX() + customTextField_password.getWidth() + 20,customTextField_password.getY());
                customButton.setLocation((panel.getWidth() - wb)/2, customTextField_password.getY() + customTextField_password.getHeight() + 40);
                label_credit.setLocation(panel.getWidth() - wc -35,panel.getHeight() - 80);
                label_version.setLocation(20,panel.getHeight() - 80);
            }
        });
    }

    public String theme(int numb){
        String code_color;
        if (numb <= 0){
            code_color = "#eeeeee";
        }else{
            code_color = "#141415";
        }
        return code_color;
    }

    public String theme_txt(int numb){
        String code_color;
        if (numb <= 0){
            code_color = "#4F4F4F";
        }else{
            code_color = "#959595";
        }
        return code_color;
    }

    private void PerformAction(JPanel panel, configuration conf, JTextField txt_username, JTextField txt_password, JPasswordField txt_password_o, Properties lg, controllerUsers controllerUsers){
        String txt_username_value = txt_username.getText();
        String txt_password_values;
        boolean data_done;
        authenticator auth = new authenticator();
        boolean session_on = false;

        if (txt_password.isVisible()){
            txt_password_values = txt_password.getText();
        }else{
            txt_password_values = new String(txt_password_o.getPassword());
        }

        if (txt_username_value.isEmpty() && txt_password_values.isEmpty()){
            notification(lg.getProperty("adv_ctc"),panel,conf.getTheme_app(),1);
        }else{
            data_done = authenticator.startSession(txt_username_value, txt_password_values, controllerUsers);
            if (data_done){
                if (!auth.session_user(controllerUsers.getUuid(), 1)){
                    notification(lg.getProperty("pass_sessiond"),panel,conf.getTheme_app(),0);
                    auth.auth_permission(controllerUsers);
                    if (auth.session_user(controllerUsers.getUuid(), 0)){
                        auth.accessSession(controllerUsers);
                    }else{
                        auth.sessionCreate(controllerUsers);
                    }
                    session_on = true;
                    controllerUsers.save_uuid_local(controllerUsers.getUuid());
                }else{
                    notification(lg.getProperty("error_and"),panel,conf.getTheme_app(),1);
                }
            }else{
                notification(lg.getProperty("error_une"),panel,conf.getTheme_app(),1);
            }

            if (session_on){
                Home.showWindows(conf,controllerUsers);
                this.dispose();
            }
        }
    }

    private void notification(String txt, JPanel panel, int theme, int type){
        NotificationLabel label_notification = new NotificationLabel(txt,theme,type);
        label_notification.setLocation(panel.getWidth() - label_notification.getWidth() - 40, 30);
        label_notification.showNotification(panel, label_notification);
    }

    private void performAction_btn_eye(JTextField txt_password, JPasswordField customPasswordField){
        if (txt_password.isVisible()){
            txt_password.setVisible(false);
            customPasswordField.setVisible(true);
            customPasswordField.setText(txt_password.getText());
        }else{
            customPasswordField.setVisible(false);
            txt_password.setVisible(true);
            txt_password.setText(new String(customPasswordField.getPassword()));
        }
    }

    public static void showWindows(configuration conf){
        SwingUtilities.invokeLater(() -> {
            Login window = new Login(conf);
            window.setVisible(true);
        });
    }
}
