package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

//        DeckPastePrompt prompt = new DeckPastePrompt();
//        String inputText = prompt.showPasteDialog();
//        System.out.println("Pasted text: " + inputText);
        
        MultiFileReader multiFileReader = new MultiFileReader();
        File[] selectedFiles = multiFileReader.selectFiles();

        CardIntake cardIntake = new CardIntake(selectedFiles);
        List<MTGCard> mtgCardList = cardIntake.getCardList();

        List<String> ImageUrls = new ArrayList<>();
        for (MTGCard mtgCard : mtgCardList) {
            ImageUrls.add(mtgCard.getImageUrl());
        }
        ImageDownloader imageDownloader = new ImageDownloader("src/main/resources/images");
        imageDownloader.download(ImageUrls, mtgCardList);

        CSVGenerator csvGenerator = new CSVGenerator();
        csvGenerator.generateCSV(mtgCardList, "src/main/resources/deckCSVs", multiFileReader.getFileName(), cardIntake);

        NanDeckScriptGenerator nanDeckScriptGenerator = new NanDeckScriptGenerator();
        nanDeckScriptGenerator.printScript(GlobalConfig.getNanDeckScript(), multiFileReader.getFileName());

        int cardsPerSheet = 9;
        if (cardIntake.getTotalCardCount(!GlobalConfig.getSkipBasicLands()) % cardsPerSheet == 0) {
            System.out.println("Divisible by 9");
            System.out.println("Total cards: " + cardIntake.getTotalCardCount(!GlobalConfig.getSkipBasicLands()));
            System.out.println("Total sheets: " + cardIntake.getTotalCardCount(!GlobalConfig.getSkipBasicLands()) / cardsPerSheet);
        } else {
            System.out.println("\r\n\r\nWARNING!! Not divisible by 9\r\n\r\n");
            System.out.println("Total cards: " + cardIntake.getTotalCardCount(!GlobalConfig.getSkipBasicLands()));
            System.out.println("Total sheets: " + ((cardIntake.getTotalCardCount(!GlobalConfig.getSkipBasicLands()) / cardsPerSheet)+1)) ;
            System.out.println("Please add " + (cardsPerSheet - (cardIntake.getTotalCardCount(!GlobalConfig.getSkipBasicLands()) % cardsPerSheet)) + " cards to make it divisible by 9");
        }

        System.out.println("Done!");
    }
}
