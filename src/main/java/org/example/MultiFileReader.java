package org.example;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.UUID;

public class MultiFileReader {

    private String name;

    public MultiFileReader() {
        this.name = "Collection_"+ UUID.randomUUID().toString() +".csv";
    }

    public String getFileName() {
        return name;
    }

    public File[] selectFiles() {
        JFileChooser fileChooser = new JFileChooser("C:\\Users\\Michael's PC\\Downloads");
        fileChooser.setMultiSelectionEnabled(true);
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            if (fileChooser.getSelectedFiles().length == 1) {
               this.name = fileChooser.getSelectedFiles()[0].getName().replaceAll(" ", "_").replaceAll(",","");
            }
            return fileChooser.getSelectedFiles();
        } else {
            return null;
        }
    }

    public void printFileContents(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}