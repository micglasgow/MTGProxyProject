package org.example;

public class MTGCard {
    private String id;
    private String name;
    private String fileName;
    private String imageUrl;
    private String type;
    private String resourceIdentifier;

    public MTGCard(String id, String name, String imageUrl, String type) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.type = type;
        this.fileName = setFileNameFromName(name);
    }

    public MTGCard(String id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.type = "n/a";
        this.fileName = setFileNameFromName(name);
    }

    private String setFileNameFromName(String name) {
        String retVal = name.replaceAll("[^a-zA-Z0-9 ]", "").replaceAll(" ", "_");
        retVal = retVal;
        return retVal;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public String getFileName() {
        return fileName;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public String getType() {
        return type;
    }
    public String getResourceIdentifier() {
        return resourceIdentifier;
    }
    public void setResourceIdentifier(String resourceIdentifier) {
        this.resourceIdentifier = resourceIdentifier;
    }

    public String toString() {
        return "MTGCard{" +
                name +
                ", type=" + type +
                ", id=" + id +
                ", image=" + imageUrl +
                '}';
    }
}
