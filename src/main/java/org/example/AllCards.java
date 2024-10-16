package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllCards {

    private static JsonNode cards;
    private static Map<String, JsonNode> cardMap;

    public AllCards() {
        loadCards();
    }

    private void loadCards() {
        ObjectMapper objectMapper = new ObjectMapper();
        cardMap = new HashMap<>();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("default-cards.json")) {
            if (inputStream != null) {
                cards = objectMapper.readTree(inputStream);
                if (cards.isArray()) {
                    for (JsonNode card : cards) {
                        cardMap.put(card.get("id").asText(), card);
                    }
                }
            } else {
                throw new RuntimeException("default-cards.json not found in resources folder");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JsonNode getCards() {
        return cards;
    }

    public List<MTGCard> getCardInfo(List<MTGCard> cardNames) {
        List<MTGCard> cardInfoList = new ArrayList<>();
        if (cards != null && cards.isArray()) {
            for (JsonNode card : cards) {
                String cardName = card.get("name").asText();
                if (cardNames.contains(cardName)) {
                    String id = card.get("id").asText();
                    String borderCrop = card.get("image_uris").get("png").asText();
                    cardInfoList.add(new MTGCard(id, cardName, borderCrop));
                }
            }
        }
        return cardInfoList;
    }

    public Boolean isDoubleFaced(String id) {
        if (cards != null && cards.isArray()) {
            for (JsonNode card : cards) {
                if (card.get("id").asText().equals(id)) {
                    if (card.has("card_faces") && !card.has("image_uris")) {
                        System.out.println("Card " + card.get("name").asText() + " is double faced.");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static List<MTGCard> getDoubleFacedCards(String id) {
        List<MTGCard> doubleFacedCards = new ArrayList<>();
        if (cards != null && cards.isArray()) {
            for (JsonNode card : cards) {
                if (card.get("id").asText().equals(id)) {
                    if (card.has("card_faces") && card.get("card_faces").isArray()) {
                        for (JsonNode cardFace : card.get("card_faces")) {
                            String cardName = cardFace.get("name").asText().replaceAll(",", "");
                            String imageUrl = cardFace.get("image_uris").get("png").asText();
                            doubleFacedCards.add(new MTGCard(id, cardName, imageUrl));
                        }
                    }
                }
            }
        }
        System.out.println(doubleFacedCards.toString());
        return doubleFacedCards;
    }

    public static MTGCard getCardInfoById(String id) {
        JsonNode card = cardMap.get(id);
        System.out.println("Card: " + card);
        if (card != null) {
            String cardName = card.get("name").asText();
            String imageUrl = null;

            try {
                imageUrl = card.get("image_uris").get("png").asText();
                System.out.println("Found png image for card: " + cardName);
            } catch (NullPointerException e) {
                System.out.println("No png image found for card: " + cardName);
                System.out.println("Attempting to use large jpg image instead");
            }
            if (imageUrl == null) {
                try {
                    imageUrl = card.get("image_uris").get("large").asText();
                } catch (NullPointerException e) {
                    System.out.println("No large image found for card: " + cardName);
                    imageUrl = "000_Magic_card_back.png";
                }
            }
            return new MTGCard(id, cardName, imageUrl);
        }
        return null;
    }

    public List<String> getAllCardNames() {
        List<String> cardNames = new ArrayList<>();
        if (cards != null && cards.isArray()) {
            for (JsonNode card : cards) {
                cardNames.add(card.get("name").asText());
            }
        }
        return cardNames;
    }
}