package com.albincystudio.ui;

import com.albincystudio.root.configuration;
import com.albincystudio.users.authenticator;
import com.albincystudio.users.controllerUsers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Logger;

public class Home extends JFrame {

    //private static final Logger LOGGER = Logger.getLogger(configuration.class.getName());

    public Home(configuration conf, controllerUsers co){
        setTitle("Home");
        setSize(1280, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        SwingUtilities.invokeLater(() -> getContentPane().requestFocusInWindow());

        //Icon
        Image icon_app = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/assets/Icon.png"));
        setIconImage(icon_app);

        HM_NavigationBar navigationBar = new HM_NavigationBar(conf.getTheme_app(), co.getUserName(),conf.getVersion());
        add(navigationBar, BorderLayout.WEST);

        HM_content content = new HM_content(conf.getTheme_app());
        add(content, BorderLayout.CENTER);

        //closetSession
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                authenticator auth = new authenticator();
                auth.closetSession(co.getSession_id());
                System.exit(1);
            }
        });
    }

    public static void showWindows(configuration conf, controllerUsers co){
        SwingUtilities.invokeLater(() -> {
            Home window = new Home(conf,co);
            window.setVisible(true);
        });
    }
}
