package org.example;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class CardIntake {

    private List<MTGCard> cardList;
    private LinkedHashMap<String, Integer> cardCountMap;

    private Boolean isMiltiFile = false;

    public CardIntake(File[] csvFiles) {
        cardList = new ArrayList<>();
        cardCountMap = new LinkedHashMap<>();
        if (csvFiles.length > 1) {
            isMiltiFile = true;
        }
        for (File file : csvFiles) {
            processCSVFile(file);
        }
    }

    private void processCSVFile(File file) {
        try (CSVReader csvReader = new CSVReader(new FileReader(file))) {
            AllCards allCards = new AllCards();
            List<String[]> records = csvReader.readAll();
            String[] header = records.get(0);
            System.out.println("Header: " + Arrays.toString(header));
            System.out.println(header.length);
            for (String[] record : records) {
                System.out.println("Record: " + Arrays.toString(record));
            }
            for (int i = 0; i < records.size(); i++) {
                String[] record = records.get(i);
                if (record[2].equals("name")) {
                    continue;
                }
                String cardId = record[16];
                System.out.println("Card ID: " + cardId);

                if (allCards.isDoubleFaced(cardId)) {
                    List<MTGCard> doubleFacedCard = allCards.getDoubleFacedCards(cardId);
                    for (MTGCard card : doubleFacedCard) {
                        cardList.add(card);
                        int count = Integer.parseInt(record[1]);
                        cardCountMap.put(card.getFileName(), count);
                    }
                } else {
                    String cardName = record[2];
                    String type = record[4];

                    try {
                        Integer.parseInt(record[1]);
                    } catch (NumberFormatException e) {
                        continue;
                    }
                    int cardCount = Integer.parseInt(record[1]);
                    String imageUrl = allCards.getCardInfoById(cardId).getImageUrl();
                    System.out.println("Image URL: " + imageUrl);
                    MTGCard mtgCard = new MTGCard(cardId, cardName, imageUrl, type);
                    cardList.add(mtgCard);
                    cardCountMap.put(mtgCard.getFileName(), cardCount);
                }
            }

            System.out.println("Card ID List: \r\n" + cardList.toString().replaceAll("},", "},\n"));
            System.out.println("Card Count Map: \r\n" + cardCountMap);
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }


    public List<MTGCard> getCardList() {
        return cardList;
    }

    public LinkedHashMap<String, Integer> getCardCountMap() {
        return cardCountMap;
    }

    public int getTotalCardCount(Boolean countBasicLands) {
        int totalCards = 0;
        for (MTGCard card : cardList) {
            if (!countBasicLands && card.getType().startsWith("Basic Land")) {
                continue;
            }
            int count = cardCountMap.get(card.getFileName());
            totalCards += count;
        }
        return totalCards;
    }
}