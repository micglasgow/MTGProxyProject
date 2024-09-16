// src/main/java/org/example/GlobalConfig.java
package org.example;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GlobalConfig {
    // Example API endpoint
    private static final String API_ENDPOINT = "https://db.ygoprodeck.com/api/v7/cardinfo.php";
    public static String getApiEndpoint() {
        return API_ENDPOINT;
    }

    private static final String ImageDirectory = "src/main/resources/images/";
    public static String getImageDirectory() {
        return ImageDirectory;
    }

    private static final String nanDeckScript = "src/main/resources/nanDeck/nanDeckScript2.txt";
    public static String getNanDeckScript() {
        return nanDeckScript;
    }

    private static final String VERSION = "_v1.0.1";
    public static String getVersion() {
        return VERSION;
    }

    private static final Boolean PrintBacks = true;
    public static Boolean getPrintBacks() {
        return PrintBacks;
    }
}
