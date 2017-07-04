package a7967917_7698299.videogameshopapplication.database;

import a7967917_7698299.videogameshopapplication.variables.ItemVariables;
import a7967917_7698299.videogameshopapplication.variables.VideoGameVariables;

/**
 * Created by alex on 2017-06-28.
 */

public class DatabaseHardCodedValues {

    private static DatabaseHardCodedValues instance = null;

    // access database with DatabaseManager.getInstance();
    public static DatabaseHardCodedValues getInstance() {
        if (instance == null) {
            instance = new DatabaseHardCodedValues();
        }
        return instance;
    }


    private DatabaseHardCodedValues() {
    }


    long switchId;
    long xboxId;
    long ps4Id;
    long threeDsId;


    public void initDatabase(){
        // call the hardcoded data here
        initConsoles();
        initGames();
    }


    private void initConsoles() {
        DatabaseManager databaseManager = DatabaseManager.getInstance();

        switchId = databaseManager.createConsole("Nintendo Switch", 399.99, "The latest Nintendo console, available now!", 5, "Nintendo", "March 3, 2017");
        ps4Id = databaseManager.createConsole("PlayStation 4", 349.99, "Lose yourself in a world of immersive gaming and top-tier entertainment with PlayStation 4 Pro. Enjoy blockbuster storytelling with a unified gaming library full of exclusive content tailor-made for the PS4. Plus, the PS4 Pro streams 4K-compatible content, upscales standard Blu-ray DVDs to 4K, and delivers PS4 Pro Enhanced gaming content.",
                4, "Sony", "November 15, 2013");

        databaseManager.createItemImage(ItemVariables.TYPE.CONSOLE, "http://media.nintendo.com/nintendo/cocoon/switch-static-pages/switch/etRgxnAu0zRX4bmWnt9K628wG7YQUI6t/images/switch/home/bundle1.jpg", switchId);

    }

    private static void initGames() {
        DatabaseManager databaseManager = DatabaseManager.getInstance();

        long itemId = databaseManager.createVideoGame("The Legend of Zelda: Breath of the Wild", 77.95, "The Legend of Zelda: Breath of the Wild is an action-adventure video game developed and published by Nintendo for the Nintendo Switch and Wii U video game consoles. The game is a part of The Legend of Zelda series, and follows amnesiac protagonist Link, who awakens from a hundred-year slumber to a mysterious voice that guides him to defeat Calamity Ganon before he can destroy the kingdom of Hyrule.",
                5, "Nintendo", "March 3, 2017", VideoGameVariables.ERSB.EVERYONE.toString(), 60, VideoGameVariables.CATEGORY.ADVENTURE.toString(), VideoGameVariables.REGION.ALL.toString(), 1);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, " https://upload.wikimedia.org/wikipedia/en/0/0e/BreathoftheWildFinalCover.jpg", itemId);

        itemId = databaseManager.createVideoGame("FIFA 17", 39.99, "FIFA 17 is a sports video game in the FIFA series developed and published by Electronic Arts, which released in September 2016. This is the first FIFA game in the series to use the Frostbite game engine. Marco Reus serves as the cover athlete on the game",
                5, "EA Sports", "September 27, 2016", VideoGameVariables.ERSB.EVERYONE.toString(), 23, VideoGameVariables.CATEGORY.SPORTS.toString(), VideoGameVariables.REGION.ALL.toString(), 4);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, " https://upload.wikimedia.org/wikipedia/en/0/08/FIFA_17_cover.jpeg", itemId);

    }


}
