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

    public CardIntake(File[] csvFiles) {
        cardList = new ArrayList<>();
        cardCountMap = new LinkedHashMap<>();
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
            for (int i = 1; i < records.size(); i++) {
                String[] record = records.get(i);
                if (i == 1) {
                    continue;
                }
                String cardId = record[16];
                String cardName = record[2];

                try {
                    Integer.parseInt(record[1]);
                } catch (NumberFormatException e) {
                    continue;
                }
                int cardCount = Integer.parseInt(record[1]);
                String imageUrl = allCards.getCardInfoById(cardId).getImageUrl();
                cardList.add(new MTGCard(cardId, cardName.toString().replaceAll(",", ""), imageUrl));
                cardCountMap.put(cardId, cardCount);
            }

            System.out.println("Card ID List: " + cardList.toString().replaceAll("},", "},\n"));
            System.out.println("Card Count Map: " + cardCountMap);
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

    public boolean isDivisibleByNumber(int number) {
        int totalCards = cardCountMap.values().stream().mapToInt(Integer::intValue).sum();
        return totalCards % number == 0;
    }
}