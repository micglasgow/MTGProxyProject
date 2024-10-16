package org.example;

import javax.swing.*;
import java.awt.*;
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

    public File[] selectFiles2() {
        JFileChooser fileChooser = new JFileChooser("C:\\Users\\Michael's PC\\Downloads");
        fileChooser.setMultiSelectionEnabled(true);
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFiles();
        } else {
            return null;
        }
    }

    public File[] selectFiles() {
        Frame frame = new Frame(); // Required for AWT components
        FileDialog fileDialog = new FileDialog(frame, "Select File", FileDialog.LOAD);
        fileDialog.setMultipleMode(true); // Enable multiple file selection
        fileDialog.setVisible(true);

        // Get selected files
        File[] files = fileDialog.getFiles();

        if (files.length > 0) {
            System.out.println("Selected files:");
            for (File file : files) {
                System.out.println(file.getAbsolutePath());
            }
            if (files.length == 1) {
                this.name = files[0].getName().replaceAll(" ", "_").replaceAll(",","");
            }
        } else {
            System.out.println("No files selected.");
        }

        // Dispose the frame after use
        frame.dispose();

        return files;
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

    public static File[] openFileExplorer(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Invalid directory path: " + directoryPath);
            return null;
        }

        try {
            Desktop.getDesktop().open(directory);
            // Simulate file selection
            // In a real scenario, you would need to implement a way to capture the selected files
            // This is just a placeholder
            return new File[] { new File(directoryPath, "example.txt") };
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}