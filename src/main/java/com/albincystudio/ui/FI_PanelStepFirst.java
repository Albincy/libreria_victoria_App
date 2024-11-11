package com.albincystudio.ui;

import com.albincystudio.componets.CustomButton;
import com.albincystudio.componets.CustomButtonSmall;
import com.albincystudio.componets.CustomPasswordField;
import com.albincystudio.componets.CustomTextField;
import com.albincystudio.root.configuration;
import com.albincystudio.users.FirstImpressions_data;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.awt.image.BufferedImage;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FI_PanelStepFirst extends JPanel {

    private BufferedImage lg_W;
    private static final Logger LOGGER = Logger.getLogger(configuration.class.getName());
    private final First_Impressions parentFrame;
    private final FirstImpressions_data save_data;

    public FI_PanelStepFirst(First_Impressions frame){
        //variables
        //configuration con = new configuration();
        final Properties languageSystem;
        Font fontSystem = new Font("Arial", Font.BOLD, 16);
        Font fontCredit = new Font("Arial", Font.PLAIN, 14);

        //panel config
        setLayout(null);
        setBackground(Color.decode("#141415"));
        this.parentFrame = frame;
        this.save_data = new FirstImpressions_data();

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

        //other components - username
        JLabel label_username = new JLabel(languageSystem.getProperty("fi_usernameLabel"), SwingConstants.LEFT);
        label_username.setForeground(Color.decode("#959595"));
        label_username.setFont(fontSystem);
        label_username.setSize(label_username.getPreferredSize());
        add(label_username);

        CustomTextField txt_username = new CustomTextField(20,1);
        txt_username.setSize(400, 30);
        add(txt_username);

        //other components - password
        JLabel label_password = new JLabel(languageSystem.getProperty("fi_passwordLabel"), SwingConstants.LEFT);
        label_password.setForeground(Color.decode("#959595"));
        label_password.setFont(fontSystem);
        label_password.setSize(label_password.getPreferredSize());
        add(label_password);

        CustomTextField txt_password = new CustomTextField(20,1);
        txt_password.setSize(400, 30);
        add(txt_password);
        txt_password.setVisible(false);

        CustomPasswordField customPasswordField = new CustomPasswordField(20,1);
        customPasswordField.setSize(400,30);
        add(customPasswordField);

        CustomButtonSmall customButtonSmall = new CustomButtonSmall(1);
        customButtonSmall.setSize(30,30);
        //event password clicked
        customButtonSmall.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                performAction_btn_eye(txt_password, customPasswordField);
            }
        });
        add(customButtonSmall);

        //other components - postdate
        JLabel label_postdate = new JLabel(languageSystem.getProperty("fi_postdate"), SwingConstants.CENTER);
        label_postdate.setForeground(Color.decode("#F9F9F9"));
        label_postdate.setFont(new Font("Arial", Font.PLAIN, 14));
        label_postdate.setSize(250,20);
        add(label_postdate);

        //btn for next
        CustomButton customButton = new CustomButton(languageSystem.getProperty("fi_btn_next"),1);
        customButton.setSize(150,40);
        customButton.addActionListener(e -> validateAndProceed(txt_password, customPasswordField, txt_username));
        add(customButton);

        //other reminder - copy
        JLabel label_reminder = new JLabel(languageSystem.getProperty("fi_mnj_reminder"), SwingConstants.LEFT);
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

    private void validateAndProceed(JTextField txt_password, JPasswordField txt_password_x, JTextField txt_username){
        String username_txt = txt_username.getText();
        String password_txt;

        //validate if txt_password is visible
        if (txt_password.isVisible()){
            password_txt = txt_password.getText();
        }else{
            password_txt = new String(txt_password_x.getPassword());
        }

        //validate data
        if (username_txt != null && !username_txt.isEmpty()){
            if (password_txt != null && !password_txt.isEmpty()){
                save_data.setUsername(username_txt);
                save_data.setPassword(password_txt);
                parentFrame.ChangePanel(new FI_PanelStepSecond(save_data, parentFrame));
            }else {
                JOptionPane.showMessageDialog(this, "Contraseña Invalida");
            }
        }else{
            JOptionPane.showMessageDialog(this, "Nombre de usuario Invalido");
        }
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

        //label username move - txtField username move
        JLabel label_username = (JLabel) getComponent(2);
        JTextField textField_username = (JTextField) getComponent(3);

        label_username.setLocation(textField_username.getX(), label2.getY() + label2.getHeight() + 50);
        textField_username.setLocation((getWidth() - textField_username.getWidth()) / 2, label_username.getY() + label_username.getHeight() + 20);

        //label password move - txtField password move - btn password view - PasswordField
        JLabel label_password = (JLabel) getComponent(4);
        JTextField textField_password = (JTextField) getComponent(5);
        JPasswordField passwordField_text = (JPasswordField) getComponent(6);

        label_password.setLocation(textField_password.getX(), textField_username.getY() + textField_username.getHeight() + 50);
        textField_password.setLocation((getWidth() - textField_password.getWidth()) / 2, label_password.getY() + label_password.getHeight() + 20);
        passwordField_text.setLocation((getWidth() - textField_password.getWidth()) / 2, label_password.getY() + label_password.getHeight() + 20);

        JButton button_viewPass = (JButton) getComponent(7);
        button_viewPass.setLocation(textField_password.getX() + textField_password.getWidth() + 20,label_password.getY() + label_password.getHeight() + 20);

        //label text postdate
        JLabel label3 = (JLabel) getComponent(8);
        label3.setLocation((getWidth() - label3.getWidth()) / 2, textField_password.getY() + textField_password.getHeight() + 25);

        //button next
        JButton button = (JButton) getComponent(9);
        button.setLocation((getWidth() - button.getWidth()) / 2, label3.getY() + label3.getHeight() + 40);

        //label text reminder
        int yPosition = getHeight() -40;
        JLabel label_reminder = (JLabel) getComponent(10);
        JLabel label_copy = (JLabel) getComponent(11);
        label_reminder.setLocation(20, yPosition);
        label_copy.setLocation(getWidth() - label_copy.getWidth() - 10, yPosition);
    }
}
