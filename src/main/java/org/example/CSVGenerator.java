package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;

public class CSVGenerator {

    public void generateCSV(List<MTGCard> cardsList, String downloadDirectory, String originalFileName, CardIntake cardIntake) {
        String csvFile = Paths.get(downloadDirectory, originalFileName + ".csv").toString();
        String imagesDirectory = Paths.get(GlobalConfig.getImageDirectory()).toAbsolutePath().toString();
        String delimiter = ",";
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.append("NAME").append(delimiter);
            writer.append("image_url").append(delimiter);
            writer.append("IMAGE").append(delimiter);
            writer.append("COUNT").append(delimiter).append("\n");

            LinkedHashMap<String, Integer> cardCountMap = cardIntake.getCardCountMap();

            for (MTGCard card : cardsList) {
                    String name = card.getName();
                    String imageUrl = card.getImageUrl();
                    String imagePath = imagesDirectory + "/" + card.getId() + ".png";
                    if (imageUrl == null) {
                        imagePath = imagesDirectory + "/" + "0.jpg";
                    }
                    int count = cardCountMap.get(card.getId());
                    writer.append(name).append(delimiter).append(imageUrl).append(delimiter).append(imagePath).append(delimiter).append(Integer.toString(count)).append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}