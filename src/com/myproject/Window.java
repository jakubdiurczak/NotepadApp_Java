package com.myproject;

import javax.swing.*;

public class Window extends JFrame {

    Window(){
        this.add(new Source());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(400,300);
        this.setTitle("NotepadApp");
    }
}
