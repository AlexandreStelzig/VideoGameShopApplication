package a7967917_7698299.videogameshopapplication.database;

import a7967917_7698299.videogameshopapplication.variables.ItemVariables;
import a7967917_7698299.videogameshopapplication.variables.VideoGameVariables;

/**
 * Created by alex on 2017-06-28.
 */

public class DatabaseHardCodedValues {

    private DatabaseManager databaseManager;

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


    public void initDatabase() {
        // create app table
        databaseManager = DatabaseManager.getInstance();
        databaseManager.createApplicationTable();
        // TODO REMOVE HARDCODED USER
        long userId = databaseManager.createUser("test@test.com", "12345", "SEG", "3525");
        databaseManager.setCurrentActiveUser(userId);


        // call the hardcoded data here
        initConsoles();
        initGames();
    }


    private void initConsoles() {

        long switchId = databaseManager.createConsole(ItemVariables.CONSOLES.SWITCH, "Nintendo Switch", 399.99, "Introducing Nintendo Switch! In addition to providing single and multiplayer thrills at home, the Nintendo Switch system also enables gamers to play the same title wherever, whenever and with whomever they choose. The mobility of a handheld is now added to the power of a home gaming system to enable unprecedented new video game play styles.",
                5, "Nintendo", "March 3, 2017");
        databaseManager.createItemImage(ItemVariables.TYPE.CONSOLE, "http://media.nintendo.com/nintendo/cocoon/switch-static-pages/switch/etRgxnAu0zRX4bmWnt9K628wG7YQUI6t/images/switch/home/bundle1.jpg", switchId);

        long xboxId = databaseManager.createConsole(ItemVariables.CONSOLES.XBOXONE, "Xbox One S", 279.99, "Own the Xbox One S Battlefield™ 1 Bundle (500GB), featuring 4K Blu-ray™, 4K video streaming, High Dynamic Range, a full game download of Battlefield™ 1, and one month of EA Access. Discover a world at war through an adventure-filled campaign, or join in epic multiplayer battles with up to 64 players on Xbox Live. Fight as infantry or take control of amazing vehicles on land, air and sea, and adapt your gameplay to the most dynamic battles in Battlefield history. And with EA Access, play EA games for a limited time before they're released and get unlimited access to The Vault, exclusively on Xbox One. With over 100 console exclusives, and a growing library of Xbox 360 games, there's never been a better time to game with Xbox One.\n",
                3, "Microsoft", "November 21, 2016");
        databaseManager.createItemImage(ItemVariables.TYPE.CONSOLE, "  https://pisces.bbystatic.com/image2/BestBuy_US/images/products/5613/5613745_sd.jpg;maxHeight=550;maxWidth=642", xboxId);

        long ps4Id = databaseManager.createConsole(ItemVariables.CONSOLES.PS4, "PlayStation 4", 349.99, "Lose yourself in a world of immersive gaming and top-tier entertainment with PlayStation 4 Pro. Enjoy blockbuster storytelling with a unified gaming library full of exclusive content tailor-made for the PS4. Plus, the PS4 Pro streams 4K-compatible content, upscales standard Blu-ray DVDs to 4K, and delivers PS4 Pro Enhanced gaming content.",
                4, "Sony", "November 15, 2013");
        databaseManager.createItemImage(ItemVariables.TYPE.CONSOLE, "https://media.playstation.com/is/image/SCEA/playstation-4-slim-vertical-product-shot-01-us-07sep16?$TwoColumn_Image$", ps4Id);

        long threeDsId = databaseManager.createConsole(ItemVariables.CONSOLES.THREE_DS, "Nintendo 3DS XL - RED", 289.95, "New Nintendo 3DS XL offers the biggest screens, new speed, new controls, new 3D viewing and a whole new experience!\n" +
                        "\n" +
                        "New Nintendo 3DS has an area on the bottom screen with NFC reading and writing functions built in, so you can enjoy playing with amiibo in compatible software. Details about compatible software planned for Nintendo 3DS will be announced in the future.",
                5, "Nintendo", "August 19, 2012");
        databaseManager.createItemImage(ItemVariables.TYPE.CONSOLE, "  https://images-na.ssl-images-amazon.com/images/I/51FMjaFKYNL._AC_SX215_.jpg", threeDsId);


    }

    private static void initGames() {
        DatabaseManager databaseManager = DatabaseManager.getInstance();

        // BOTW
        long itemId = databaseManager.createVideoGame("The Legend of Zelda: Breath of the Wild", 77.95, "The Legend of Zelda: Breath of the Wild is an action-adventure video game developed and published by Nintendo for the Nintendo Switch and Wii U video game consoles. The game is a part of The Legend of Zelda series, and follows amnesiac protagonist Link, who awakens from a hundred-year slumber to a mysterious voice that guides him to defeat Calamity Ganon before he can destroy the kingdom of Hyrule.",
                5, "Nintendo", "March 3, 2017", VideoGameVariables.ESRB.EVERYONE.toString(), 60, VideoGameVariables.CATEGORY.ADVENTURE.toString(), VideoGameVariables.REGION.ALL.toString(), 1);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, " https://upload.wikimedia.org/wikipedia/en/0/0e/BreathoftheWildFinalCover.jpg", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, " https://static-ca.ebgames.ca/images/products/727843/7scrmax2.jpg", itemId);
        databaseManager.createConsoleVideoGame(ItemVariables.CONSOLES.SWITCH, itemId);

        // FIFA
        itemId = databaseManager.createVideoGame("FIFA 17", 39.99, "FIFA 17 is a sports video game in the FIFA series developed and published by Electronic Arts, which released in September 2016. This is the first FIFA game in the series to use the Frostbite game engine. Marco Reus serves as the cover athlete on the game",
                5, "EA Sports", "September 27, 2016", VideoGameVariables.ESRB.EVERYONE.toString(), 23, VideoGameVariables.CATEGORY.SPORTS.toString(), VideoGameVariables.REGION.ALL.toString(), 4);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, " https://upload.wikimedia.org/wikipedia/en/0/08/FIFA_17_cover.jpeg", itemId);
        databaseManager.createConsoleVideoGame(ItemVariables.CONSOLES.PS4, itemId);

        // FIRE EMBLEM
        itemId = databaseManager.createVideoGame("Fire Emblem Awakening", 39.99, "Awakening uses a turn-based tactical role-playing battle system. The terrain is displayed on the top screen of the 3DS, while unit information is displayed on the bottom screen. Before each battle, the player selects a limited number of characters from their roster for use in battle. The player can either control each unit manually or activate an auto-battle option. Character movement is dictated by a tile-based movement system. During combat, player-controlled sprite characters and enemy units controlled by the game's artificial intelligence (AI) each get one turn where they position their units. An additional turn is added when unaffiliated AI-controlled units are in the field. Playable characters positioned next to each other in the field will support one-another, granting buffs, and performing actions such as blocking attacks. Two characters can also pair up as a single mobile unit, enabling both units to attack at once. As the relationship between characters strengthens, they gain greater bonuses to their strength and effectiveness when paired up in battle. During combat, the perspective switched to a 3D scene between combatants. Optional camera angles, including a first-person view through the eyes of playable characters, can be activated using the 3DS stylus.",
                5, "Nintendo", "April 19, 2012", VideoGameVariables.ESRB.EVERYONE.toString(), 23, VideoGameVariables.CATEGORY.RPG.toString(), VideoGameVariables.REGION.ASIA.toString(), 4);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "https://upload.wikimedia.org/wikipedia/en/4/44/Fire_Emblem_Awakening_box_art.png", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "https://upload.wikimedia.org/wikipedia/en/9/92/FE%3B_Awakening_FPV_screenshot.jpg", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "https://images-na.ssl-images-amazon.com/images/I/51jVuudVUOL.jpg", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "http://fireemblem.nintendo.com/about/img/sc2_2.jpg", itemId);
        databaseManager.createConsoleVideoGame(ItemVariables.CONSOLES.THREE_DS, itemId);

    }


}
