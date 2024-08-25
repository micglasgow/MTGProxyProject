package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AllCards {

    private static JsonNode cards;

    public AllCards() {
        loadCards();
    }

    private void loadCards() {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("default-cards.json")) {
            if (inputStream != null) {
                cards = objectMapper.readTree(inputStream);
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
                    String borderCrop = card.get("image_uris").get("border_crop").asText();
                    cardInfoList.add(new MTGCard(id, cardName, borderCrop));
                }
            }
        }
        return cardInfoList;
    }

    public static MTGCard getCardInfoById(String id) {
        if (cards != null && cards.isArray()) {
            for (JsonNode card : cards) {
                if (card.get("id").asText().equals(id)) {
                    String cardName = card.get("name").asText();
                    String borderCrop = card.get("image_uris").get("png").asText();
                    return new MTGCard(id, cardName, borderCrop);
                }
            }
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