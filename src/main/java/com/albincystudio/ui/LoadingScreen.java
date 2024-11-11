package com.albincystudio.ui;

import com.albincystudio.database.Database;
import com.albincystudio.root.configuration;
import com.albincystudio.users.authenticator;
import com.albincystudio.users.controllerUsers;

import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoadingScreen extends JFrame {

    private final Properties languageSystem = new Properties();
    private static final Logger LOGGER = Logger.getLogger(configuration.class.getName());
    private int bar = 0;
    private static int mouseX;
    private static int mouseY;
    JProgressBar progressBar = new JProgressBar(0, 100);
    JLabel label_txt_infoL = new JLabel();
    configuration conf = new configuration();
    controllerUsers co = new controllerUsers();
    authenticator au = new authenticator();
    boolean login = true;
    boolean connection = true;

    public LoadingScreen(){

        setSize(680, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setLocationRelativeTo(null);
        SwingUtilities.invokeLater(() -> getContentPane().requestFocusInWindow());

        //fonts and settings
        Font fontSystem = new Font("Arial", Font.BOLD, 16);

        //Icon
        Image icon_app = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/assets/Icon.png"));
        setIconImage(icon_app);

        //load properties language
        try (InputStream input = getClass().getResourceAsStream("/lang/lang.properties")) {
            if (input == null) {
                System.err.println("Error al encontrar archivo");
                return;
            }
            languageSystem.load(input);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error load properties language", e);
        }

        //cargar panel etc
        initComponents(fontSystem);
        applyRoundedCorners();
        initCustomListeners();
    }

    private void applyRoundedCorners() {
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));
            }
        });
    }

    public void initComponents(Font font){
        progressBar.setValue(0);
        progressBar.setStringPainted(false);
        progressBar.setBackground(Color.decode("#2C2C2C"));
        progressBar.setForeground(Color.decode("#FF6B27"));
        progressBar.setBorderPainted(false);
        progressBar.setUI(new BasicProgressBarUI(){
            @Override
            protected void paintDeterminate(Graphics g, JComponent c) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Insets b = progressBar.getInsets();
                int width = progressBar.getWidth() - (b.right + b.left);
                int height = progressBar.getHeight() - (b.top + b.bottom);

                int amountFull = (int) (width * progressBar.getPercentComplete());
                g2d.setColor(progressBar.getForeground());
                g2d.fillRect(b.left, b.top, amountFull, height);

                g2d.setStroke(new BasicStroke(1));
                g2d.setColor(Color.decode("#FF6B27"));
                g2d.drawRect(b.left, b.top, width - 1, height - 1);
            }
        });

        ImageIcon icon_logoV = new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/ui/logo_wind.png")));
        JLabel label_icon = new JLabel(icon_logoV);
        label_txt_infoL.setText(languageSystem.getProperty("ld_loading"));
        label_txt_infoL.setForeground(Color.decode("#959595"));
        label_txt_infoL.setFont(font);

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
        panel.setBackground(Color.decode("#141415"));
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                int x = e.getXOnScreen() - mouseX;
                int y = e.getYOnScreen() - mouseY;
                setLocation(x, y);
            }
        });

        //position
        label_icon.setBounds(120,30,427,252);
        progressBar.setBounds(70,label_icon.getY() + label_icon.getHeight() + 40,540,20);
        label_txt_infoL.setBounds(progressBar.getX(), label_icon.getY() + label_icon.getHeight() + 10,400,20);

        panel.add(label_icon);
        panel.add(progressBar);
        panel.add(label_txt_infoL);

        this.add(panel);
    }

    private void initCustomListeners(){
        ActionListener ac = ae -> {
            bar++;
            progressBar.setValue(bar);

            if (bar == 20){
                label_txt_infoL.setText(languageSystem.getProperty("ld_loading_txt"));
                boolean exist = conf.existUserdata();
                while (!exist){
                    exist = conf.existUserdata();
                }
                conf.loadSettings();
            }

            if (bar == 40){
                label_txt_infoL.setText(languageSystem.getProperty("ld_loading_txt2"));
                boolean exist = co.existAuth_local();
                if (exist){
                    co.load_auth_local();
                    login = false;
                }
            }

            if (bar == 60){
                if (Database.CheckDatabaseConnection()){
                    if (login){
                        label_txt_infoL.setText(languageSystem.getProperty("ld_loading_txt4"));
                    }else{
                        label_txt_infoL.setText(languageSystem.getProperty("ld_loading_txt3"));
                        co = au.auth_uuid(co.getUuid());
                        if(co.getUserName() == null){
                            label_txt_infoL.setText(languageSystem.getProperty("ld_loading_txt4"));
                            login = true;
                        }
                    }
                }else{
                    connection = false;
                    label_txt_infoL.setText(languageSystem.getProperty("ld_connection"));
                }
            }

            if (connection){
                if (bar == 80){
                    if (!login){
                        if (au.session_user(co.getUuid(),1)){
                            label_txt_infoL.setText(languageSystem.getProperty("ld_loading_txt5"));
                            login = true;
                        }else{
                            if(au.session_user(co.getUuid(),0)){
                                label_txt_infoL.setText(languageSystem.getProperty("ld_loading_txt7"));
                                au.accessSession(co);
                            }else{
                                label_txt_infoL.setText(languageSystem.getProperty("ld_loading_txt6"));
                                au.sessionCreate(co);
                            }
                        }
                    }
                }

                if (bar == 90) {
                    if (conf.isNewUseApp()){
                        First_Impressions.showWindows();
                    }else{
                        if (login){
                            Login.showWindows(conf);
                        }else{
                            Home.showWindows(conf,co);
                        }
                    }
                }
            }

            if (bar == 100){
                ((Timer) ae.getSource()).stop();
                this.dispose();
            }
        };
        Timer time = new Timer(10, ac);
        time.start();
    }

    public static void showWindows(){
        SwingUtilities.invokeLater(() -> {
            LoadingScreen window = new LoadingScreen();
            window.setVisible(true);
        });
    }
}
