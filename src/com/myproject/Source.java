package com.myproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Source extends JPanel implements ActionListener {

    private final JButton buttonOpen = new JButton("Open file");
    private final JButton buttonSave = new JButton("Save file");
    private final JButton buttonClean = new JButton("Clean all");
    private final JButton buttonFile = new JButton("Select");
    private final JTextField fieldPath = new JTextField("file.txt",10);
    private final JTextArea textArea = new JTextArea();
    private final JFileChooser fileChooser = new JFileChooser();


    Source(){
        this.setLayout(new BorderLayout());

        JPanel panelPath = new JPanel();
        this.add(panelPath, BorderLayout.NORTH);
        panelPath.setLayout(new BorderLayout());
        panelPath.add(new JLabel("Path to file: "), BorderLayout.WEST);
        panelPath.add(fieldPath, BorderLayout.CENTER);
        panelPath.add(buttonFile, BorderLayout.EAST);
        buttonFile.addActionListener(this);

        JPanel panelText = new JPanel();
        this.add(panelText, BorderLayout.CENTER);
        panelText.setLayout(new BorderLayout());
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane textAreaScroll = new JScrollPane(textArea);
        textAreaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panelText.add(textAreaScroll, BorderLayout.CENTER);

        JPanel panelButton = new JPanel();
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
        int result = fileChooser.showOpenDialog(Source.this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            pathToFile = selectedFile.getAbsolutePath();
        }
        return pathToFile;
    }

    @SuppressWarnings("ThrowablePrintedToSystemOut")
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

    @SuppressWarnings("ThrowablePrintedToSystemOut")
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
