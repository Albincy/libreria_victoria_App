package com.albincystudio.ui;

import com.albincystudio.componets.CustomButton;
import com.albincystudio.root.configuration;

import javax.swing.*;
import java.awt.*;

public class CustomErrorDialog extends JFrame {
    public CustomErrorDialog(String tittle_error, String body_error){
        setTitle("Albincy Studio Crashed");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(800, 430);
        setResizable(false);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        SwingUtilities.invokeLater(() -> getContentPane().requestFocusInWindow());

        //Icon
        Image icon_app = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/assets/lg.png"));
        setIconImage(icon_app);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(Color.decode("#141415"));

        JLabel titleLabel = new JLabel(tittle_error);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.decode("#FFFFFF"));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(10));

        JTextArea textArea = new JTextArea(body_error, 4, 40);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        textArea.setBorder(BorderFactory.createEmptyBorder());
        textArea.setBackground(Color.decode("#212121"));
        textArea.setForeground(Color.decode("#ececec"));
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBackground(Color.decode("#212121"));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(scrollPane);

        CustomButton closeButton = new CustomButton("Aceptar", 1);
        closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeButton.addActionListener(e -> performButton());
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(closeButton);
        add(contentPanel, BorderLayout.CENTER);
    }

    private void performButton(){
        dispose();
        System.exit(1);
    }

    public static void showWindows(String tittle_error, String body_error){
        SwingUtilities.invokeLater(() -> {
            CustomErrorDialog window = new CustomErrorDialog(tittle_error, body_error);
            window.setVisible(true);
        });
    }
}
