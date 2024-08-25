package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeckPastePrompt {

    public String showPasteDialog() {
        // Create a new JDialog
        JDialog dialog = new JDialog((Frame) null, "Paste Text", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new BorderLayout());

        // Create a JTextArea for text input
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        dialog.add(scrollPane, BorderLayout.CENTER);

        // Create a JButton to submit the text
        JButton submitButton = new JButton("Submit");
        dialog.add(submitButton, BorderLayout.SOUTH);

        // Array to store the input text
        final String[] pastedText = new String[1];

        // Handle button click
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pastedText[0] = textArea.getText();
                dialog.dispose();
            }
        });

        // Show the dialog
        dialog.setVisible(true);

        // Return the input text
        return pastedText[0];
    }
}