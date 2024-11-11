package com.albincystudio.root;

import com.albincystudio.ui.LoadingScreen;

public class root {
    public static void main(String[] args) {
        //Structure App
        LoadingScreen.showWindows();
    }

    public static void restartApplication() {
        main(null);
    }
}