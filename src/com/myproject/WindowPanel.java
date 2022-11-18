package com.myproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WindowPanel extends JPanel implements ActionListener {

    JPanel panelPath = new JPanel();
    JPanel panelButton = new JPanel();
    JPanel panelText = new JPanel();
    JButton buttonOpen = new JButton("Open file");
    JButton buttonSave = new JButton("Save file");
    JButton buttonClean = new JButton("Clean all");
    JButton buttonFile = new JButton("Select");
    JTextField fieldPath = new JTextField("file.txt",10);
    JTextArea textArea = new JTextArea();
    JScrollPane textAreaScroll = new JScrollPane(textArea);
    JFileChooser fileChooser = new JFileChooser();


    WindowPanel(){
        setLayout(new BorderLayout());

        this.add(panelPath, BorderLayout.NORTH);
        panelPath.setLayout(new BorderLayout());
        panelPath.add(new JLabel("Path to file: "), BorderLayout.WEST);
        panelPath.add(fieldPath, BorderLayout.CENTER);
        panelPath.add(buttonFile, BorderLayout.EAST);
        buttonFile.addActionListener(this);

        this.add(panelText, BorderLayout.CENTER);
        panelText.setLayout(new BorderLayout());
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textAreaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panelText.add(textAreaScroll, BorderLayout.CENTER);

        this.add(panelButton, BorderLayout.SOUTH);
        panelButton.add(buttonOpen);
        panelButton.add(buttonSave);
        panelButton.add(buttonClean);
        buttonOpen.addActionListener(this);
        buttonSave.addActionListener(this);
        buttonClean.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == buttonOpen && !fieldPath.getText().isBlank()){
            textArea.setText(openFile(fieldPath.getText()));
        }

        if(e.getSource() == buttonSave){
            String pathToFile = fieldPath.getText();
            if(pathToFile.length() == 0) {
                pathToFile = selectFilePath();
                fieldPath.setText(pathToFile);
            }
            saveFile(pathToFile, textArea.getText());
        }

        if(e.getSource() == buttonClean){
            textArea.setText(null);
            fieldPath.setText("");
        }

        if(e.getSource() == buttonFile){
            fieldPath.setText(selectFilePath());
        }

    }

    private String selectFilePath(){
        String pathToFile = null;
        int result = fileChooser.showOpenDialog(WindowPanel.this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            pathToFile = selectedFile.getAbsolutePath();
        }
        return pathToFile;
    }

    private String openFile(String pathToFile){
        StringBuilder textRead = new StringBuilder();
        try {
            FileReader fileReader = new FileReader(pathToFile);
            int i;
            while((i = fileReader.read()) != -1) {
                textRead.append((char) i);
            }
            fileReader.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        return textRead.toString();
    }

    private void saveFile(String pathToFile, String contentText){
        try {
            FileWriter fileWriter = new FileWriter(pathToFile);
            fileWriter.write(contentText);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
