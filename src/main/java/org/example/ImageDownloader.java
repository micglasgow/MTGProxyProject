package org.example;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ImageDownloader {

    private final String downloadDirectory;

    public ImageDownloader(String downloadDirectory) {
        this.downloadDirectory = downloadDirectory;
    }

    public void download(List<String> urls, List<MTGCard> mtgCardList) {
        try {
            Path dirPath = Paths.get(downloadDirectory);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }
            int i = 0;
            for (String urlString : urls) {

                URL url = null;
                try {
                    url = new URL(urlString);
                } catch (Exception e) {
                    System.out.println("Failed to initiate image from " + urlString);
                    continue;
                }
                String resouceName = Paths.get(url.getPath()).getFileName().toString();
                String fileName = mtgCardList.get(i).getFileName() + "_" + resouceName;
                MTGCard mtgCard = mtgCardList.get(i);
                mtgCard.setResourceIdentifier(resouceName);
                i = i + 1;
                Path filePath = dirPath.resolve(fileName);

                if (Files.exists(filePath)) {
                    System.out.println("File already exists: " + fileName);
                    continue;
                } else {
                    System.out.println("Downloading image from " + urlString + " as " + fileName);
                }

                try (BufferedInputStream in = new BufferedInputStream(url.openStream());
                     FileOutputStream fileOutputStream = new FileOutputStream(filePath.toFile())) {
                    byte dataBuffer[] = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                        fileOutputStream.write(dataBuffer, 0, bytesRead);
                    }
                } catch (IOException e) {
                    System.err.println("Failed to download image from " + urlString);
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            System.err.println("Failed to create download directory");
            e.printStackTrace();
        }
    }
}
