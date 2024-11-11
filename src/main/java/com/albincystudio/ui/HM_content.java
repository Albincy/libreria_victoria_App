package com.albincystudio.ui;

import javax.swing.*;
import java.awt.*;

public class HM_content extends JPanel {
    public HM_content(int theme){
        setBackground(Color.decode(themeReturn(theme)));
    }

    public String themeReturn(int thm){
        if (thm <= 0){
            return "#ececec";
        }else{
            return "#1f1f21";
        }
    }
}
