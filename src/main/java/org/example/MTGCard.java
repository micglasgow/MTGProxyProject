package org.example;

public class MTGCard {
    private String id;
    private String name;
    private String imageUrl;

    public MTGCard(String id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String toString() {
        return "MTGCard{" +
                name +
                ", id=" + id +
                ", borderCrop=" + imageUrl +
                '}';
    }
}
