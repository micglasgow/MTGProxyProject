package org.example;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;


public class NanDeckScriptGenerator {

    public void printScript(String scriptFilePath, String deckName) {
        StringBuilder sb = new StringBuilder();
        String fileDeckName = deckName.replaceAll("[^a-zA-Z0-9]", "") + GlobalConfig.getVersion();
        try (BufferedReader reader = new BufferedReader(new FileReader(scriptFilePath))) {
            sb.append("; Generated NanDeck script ======================================");
            sb.append(System.lineSeparator());
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                if (line.contains("PRINT_HERE")) {
                    sb.append("LINK=" + Paths.get("src/main/resources/deckCSVs").toAbsolutePath().toString() + "\\" + deckName + "\"");
                    sb.append(System.lineSeparator());
                    sb.append("; ").append(fileDeckName);
                    sb.append(System.lineSeparator());
                } else {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        sb.append("; End of generated NanDeck script ================================");
        System.out.println("\r\n\r\n" + sb.toString());
        CopytoClipboard(sb.toString());
        System.out.println("Script copied to clipboard");
    }

    private void CopytoClipboard(String myString) {
        StringSelection stringSelection = new StringSelection(myString);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }
}
