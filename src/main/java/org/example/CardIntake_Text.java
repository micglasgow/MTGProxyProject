package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardIntake_Text {

    public static class Card {
        private String name;
        private int count;

        public Card(String name, int count) {
            this.name = name;
            this.count = count;
        }

        public String getName() {
            return name;
        }

        public int getCount() {
            return count;
        }
    }

    public Map<String, Integer> cardMap = new HashMap<>();
    public List<String> cardList = new ArrayList<>();

    public void processCardString(String cardString) {
        String[] lines = cardString.split("\n");
        for (String line : lines) {
            String[] parts = line.split(" ", 2);
            int count = Integer.parseInt(parts[0]);
            String name = parts[1];
            Card card = new Card(name, count);
            cardList.add(card.getName());
            cardMap.put(name, count);
        }
    }

    public List<String> getCardList() {
        return cardList;
    }

    public Map<String, Integer> getCardMap() {
        return cardMap;
    }
}