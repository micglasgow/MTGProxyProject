package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;

public class CSVGenerator {

    public void generateCSV(List<MTGCard> cardsList, String downloadDirectory, String originalFileName, CardIntake cardIntake) {
        String csvFile = Paths.get(downloadDirectory, originalFileName).toString();
        String imagesDirectory = Paths.get(GlobalConfig.getImageDirectory()).toAbsolutePath().toString();
        String delimiter = ",";
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.append("NAME").append(delimiter);
            writer.append("image_url").append(delimiter);
            writer.append("IMAGE").append(delimiter);
            writer.append("TAG").append(delimiter);
            writer.append("BACK").append(delimiter);
            writer.append("COUNT").append(delimiter).append("\n");

            LinkedHashMap<String, Integer> cardCountMap = cardIntake.getCardCountMap();


            int counter = 0;
            for (MTGCard card : cardsList) {
                System.out.println(card.toString());
                if (card.getType().startsWith("Basic Land") && GlobalConfig.getSkipBasicLands()) {
                    System.out.println("Skipping basic land: " + card.getName());
                    continue;
                }

                if (counter % 9 == 0 && GlobalConfig.getPrintBacks()) {
                    for (int i = 0; i < 9; i++) {
                        String backName = "Card_Back";
                        String backImageUrl = null;
                        String backImagePath = imagesDirectory + "/" + "000_Proxy_back.png";
                        String backTag = "back";
                        writer.append(backName).append(delimiter).append(backImageUrl).append(delimiter).append(backImagePath).append(delimiter).append(backTag).append(delimiter).append("1").append("\n");
                    }
                }
                String backImagePath = imagesDirectory + "\\" + "000_Proxy_back.png";
                String name = card.getName().replaceAll(",", "");
                String imageUrl = card.getImageUrl();
                String imagePath = imagesDirectory + "\\" + card.getFileName() + "_" + card.getResourceIdentifier();
                String tag = imagesDirectory + "\\" + "ProxyTag.png";
                if (imageUrl == null) {
                    imagePath = imagesDirectory + "\\" + "000_Magic_card_back.png";
                }
                int count = cardCountMap.get(card.getFileName());

                writer.append(name).append(delimiter).append(imageUrl).append(delimiter).append(imagePath).append(delimiter).append(tag).append(delimiter).append(backImagePath).append(delimiter).append(String.valueOf(count)).append("\n");
                System.out.println("Writing: " + name + " " + imageUrl + " " + imagePath + " " + count);
                counter++;
            }
            int numberOfCards = cardIntake.getTotalCardCount(!GlobalConfig.getSkipBasicLands());
            int cardsToAdd = 9 - (numberOfCards % 9);
            if (numberOfCards % 9 == 0) {
                cardsToAdd = 0;
            }
            System.out.println("Adding blank cards");
            System.out.println("Number of cards: " + cardsToAdd);

            for (int i = 0; i < cardsToAdd; i++) {
                String name = "Card_Blank";
                String imageUrl = "local";
                String imagePath = imagesDirectory + "/" + "000_MTG_Card_Blank.png";
                String tag = imagesDirectory + "\\" + "ProxyTag.png";
                String backImagePath = imagesDirectory + "\\" + "000_Proxy_back.png";

                System.out.println("Writing: " + name + " " + imageUrl + " " + imagePath + " " + tag + " " + backImagePath + " " + 1);
                writer.append(name).append(delimiter).append(imageUrl).append(delimiter).append(imagePath).append(delimiter).append(tag).append(delimiter).append(backImagePath).append(delimiter).append("1").append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}