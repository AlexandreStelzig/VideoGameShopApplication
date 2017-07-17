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



        // call the hardcoded data here
        initConsoles();
        initGames();
    }


    // date formats are MONTH DAY, YEAR

    private void initConsoles() {

        // SWITCH

        long switchId = databaseManager.createConsole(ItemVariables.CONSOLES.SWITCH, "Nintendo Switch", 399.99, "Introducing Nintendo Switch! In addition to providing single and multiplayer thrills at home, the Nintendo Switch system also enables gamers to play the same title wherever, whenever and with whomever they choose. The mobility of a handheld is now added to the power of a home gaming system to enable unprecedented new video game play styles.",
                5, "Nintendo", "March 3, 2017");
        databaseManager.createItemImage(ItemVariables.TYPE.CONSOLE, "http://media.nintendo.com/nintendo/cocoon/switch-static-pages/switch/etRgxnAu0zRX4bmWnt9K628wG7YQUI6t/images/switch/home/bundle1.jpg", switchId);


        // XBOX

        long xboxId = databaseManager.createConsole(ItemVariables.CONSOLES.XBOXONE, "Xbox One S", 279.99, "Own the Xbox One S Battlefield™ 1 Bundle (500GB), featuring 4K Blu-ray™, 4K video streaming, High Dynamic Range, a full game download of Battlefield™ 1, and one month of EA Access. Discover a world at war through an adventure-filled campaign, or join in epic multiplayer battles with up to 64 players on Xbox Live. Fight as infantry or take control of amazing vehicles on land, air and sea, and adapt your gameplay to the most dynamic battles in Battlefield history. And with EA Access, play EA games for a limited time before they're released and get unlimited access to The Vault, exclusively on Xbox One. With over 100 console exclusives, and a growing library of Xbox 360 games, there's never been a better time to game with Xbox One.\n",
                3, "Microsoft", "November 21, 2016");
        databaseManager.createItemImage(ItemVariables.TYPE.CONSOLE, "  https://pisces.bbystatic.com/image2/BestBuy_US/images/products/5613/5613745_sd.jpg;maxHeight=550;maxWidth=642", xboxId);

        xboxId = databaseManager.createConsole(ItemVariables.CONSOLES.XBOXONE, "Microsoft Xbox One 500 GB", 269.99, "This item includes the Xbox One console, 1 wireless controller, HDMI cable, and power supply\n" +
                        "The best exclusive games, the most advanced multiplayer, and unique entertainment experiences\n" +
                        "Play games like Titanfall and Halo on a network powered by over 300,000 servers for maximum performance\n" +
                        "The most advanced multiplayer on Xbox Live with Smart Match to find new challengers\n" +
                        "Note: Kinect sensor sold separate\n" +
                        "Shop for the newest Xbox One Bundle or Xbox One Console.",
                4, "Microsoft", "June 9, 2014");
        databaseManager.createItemImage(ItemVariables.TYPE.CONSOLE, "https://images-na.ssl-images-amazon.com/images/I/61CnOKdmBeL._AC_SL1500_.jpg", xboxId);
        databaseManager.createItemImage(ItemVariables.TYPE.CONSOLE, "https://images-na.ssl-images-amazon.com/images/I/51k5EFggZoL._AC_SL1000_.jpg", xboxId);

        xboxId = databaseManager.createConsole(ItemVariables.CONSOLES.XBOXONE, "Xbox One 1TB Console - Tom Clancy's The Division Bundle", 268.95, "This product includes: 1TB hard drive Xbox One, Tom Clancy's The Division, newly updated Xbox One black wireless controller with a 3.5mm headset jack so you can plug in any compatible headset, 14-day trial of Xbox Live Gold, AC Power Cable, and an HDMI Cable.\n" +
                        "Take back New York City from a deadly pandemic in The Division, an online, open-world, RPG experience like no other.\n" +
                        "Get new game content first on Xbox.\n" +
                        "Quickly switch between your games, live TV, and apps like Amazon Video, Netflix, YouTube, and HBO GO.\n" +
                        "Play select Xbox 360 games on your Xbox One with backwards compatibility.",
                4, "Microsoft", "March 8, 2016");
        databaseManager.createItemImage(ItemVariables.TYPE.CONSOLE, "https://images-na.ssl-images-amazon.com/images/I/81MFkS3EtjL._AC_SL1276_.jpg", xboxId);
        databaseManager.createItemImage(ItemVariables.TYPE.CONSOLE, "https://images-na.ssl-images-amazon.com/images/I/61NPl2e9k2L._AC_SL1500_.jpg", xboxId);
        databaseManager.createItemImage(ItemVariables.TYPE.CONSOLE, "https://images-na.ssl-images-amazon.com/images/I/81AF66%2BmQ5L._AC_SL1026_.jpg", xboxId);


        // PS4

        long ps4Id = databaseManager.createConsole(ItemVariables.CONSOLES.PS4, "PlayStation 4", 349.99, "Lose yourself in a world of immersive gaming and top-tier entertainment with PlayStation 4 Pro. Enjoy blockbuster storytelling with a unified gaming library full of exclusive content tailor-made for the PS4. Plus, the PS4 Pro streams 4K-compatible content, upscales standard Blu-ray DVDs to 4K, and delivers PS4 Pro Enhanced gaming content.",
                4, "Sony", "November 15, 2013");
        databaseManager.createItemImage(ItemVariables.TYPE.CONSOLE, "https://media.playstation.com/is/image/SCEA/playstation-4-slim-vertical-product-shot-01-us-07sep16?$TwoColumn_Image$", ps4Id);


        // 3DS

        long threeDsId = databaseManager.createConsole(ItemVariables.CONSOLES.THREE_DS, "Nintendo 3DS XL - RED", 289.95, "New Nintendo 3DS XL offers the biggest screens, new speed, new controls, new 3D viewing and a whole new experience!\n" +
                        "\n" +
                        "New Nintendo 3DS has an area on the bottom screen with NFC reading and writing functions built in, so you can enjoy playing with amiibo in compatible software. Details about compatible software planned for Nintendo 3DS will be announced in the future.",
                5, "Nintendo", "August 19, 2012");
        databaseManager.createItemImage(ItemVariables.TYPE.CONSOLE, "  https://images-na.ssl-images-amazon.com/images/I/51FMjaFKYNL._AC_SX215_.jpg", threeDsId);

        threeDsId = databaseManager.createConsole(ItemVariables.CONSOLES.THREE_DS, "Nintendo 3DS Aqua Blue", 289.95, "Nintendo 3DS offers a new way to play, 3D without the need for special glasses. The 3D Depth Slider lets your determine how much 3D you want to see.\n" +
                        "Play 3D games and take 3D pictures with Nintendo 3DS. One inner camera and two outer cameras. Resolutions are 640 x 480 for each camera. Lens are single focus and uses the CMOS capture element.\n" +
                        "Connect to a deeper wireless experience with SpotPass and StreetPass, giving you more exclusive content and connecting you with other Nintendo 3DS users\n" +
                        "Complete with an adjustable stylus, 6 AR cards, and fun built-in software such as Face Raiders, Nintendo 3DS Sound, and the Mii Maker application\n" +
                        "Use Parental Controls to restrict 3D mode for children 6 and under",
                4, "Nintendo", "August 19, 2012");
        databaseManager.createItemImage(ItemVariables.TYPE.CONSOLE, "https://images-na.ssl-images-amazon.com/images/I/51XU4kVlohL.jpg", threeDsId);
        databaseManager.createItemImage(ItemVariables.TYPE.CONSOLE, "https://images-na.ssl-images-amazon.com/images/I/315KROuPm0L.jpg", threeDsId);
        databaseManager.createItemImage(ItemVariables.TYPE.CONSOLE, "https://images-na.ssl-images-amazon.com/images/I/41UkjYJpwkL.jpg", threeDsId);
        databaseManager.createItemImage(ItemVariables.TYPE.CONSOLE, "https://images-na.ssl-images-amazon.com/images/I/41MRJzkUtQL.jpg", threeDsId);


        threeDsId = databaseManager.createConsole(ItemVariables.CONSOLES.THREE_DS, "Nintendo 3DS XL Black/Black", 268.95, "Nintendo 3DS XL Black/Black",
                5, "Nintendo", "May 21, 2013");

        databaseManager.createItemImage(ItemVariables.TYPE.CONSOLE, "https://images-na.ssl-images-amazon.com/images/I/41JeC0J6v9L.jpg", threeDsId);
        databaseManager.createItemImage(ItemVariables.TYPE.CONSOLE, "https://images-na.ssl-images-amazon.com/images/I/313XMMdKG8L.jpg", threeDsId);


    }

    private static void initGames() {
        DatabaseManager databaseManager = DatabaseManager.getInstance();

        // BOTW
        long itemId = databaseManager.createVideoGame("The Legend of Zelda: Breath of the Wild", 77.95, "The Legend of Zelda: Breath of the Wild is an action-adventure video game developed and published by Nintendo for the Nintendo Switch and Wii U video game consoles. The game is a part of The Legend of Zelda series, and follows amnesiac protagonist Link, who awakens from a hundred-year slumber to a mysterious voice that guides him to defeat Calamity Ganon before he can destroy the kingdom of Hyrule.",
                5, "Nintendo", "March 3, 2017", VideoGameVariables.ESRB.EVERYONE.toString(), 60, VideoGameVariables.CATEGORY.ADVENTURE.toString(), VideoGameVariables.REGION.ALL.toString(), 1);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, " https://upload.wikimedia.org/wikipedia/en/0/0e/BreathoftheWildFinalCover.jpg", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, " https://static-ca.ebgames.ca/images/products/727843/7scrmax2.jpg", itemId);
        databaseManager.createConsoleVideoGame(ItemVariables.CONSOLES.SWITCH, itemId);
        databaseManager.createTrailerGame("https://www.youtube.com/embed/zw47_q9wbBE", itemId);

        // FIFA
        itemId = databaseManager.createVideoGame("FIFA 17", 39.99, "FIFA 17 is a sports video game in the FIFA series developed and published by Electronic Arts, which released in September 2016. This is the first FIFA game in the series to use the Frostbite game engine. Marco Reus serves as the cover athlete on the game.",
                5, "EA Sports", "September 27, 2016", VideoGameVariables.ESRB.EVERYONE.toString(), 23, VideoGameVariables.CATEGORY.SPORTS.toString(), VideoGameVariables.REGION.ALL.toString(), 4);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "https://upload.wikimedia.org/wikipedia/en/0/08/FIFA_17_cover.jpeg", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "https://media.contentapi.ea.com/content/www-easports/en_US/fifa/news/2016/fifa-17-pre-order-offers/_jcr_content/headerImages/image.img.jpg", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "http://www.storehack.com/wp-content/uploads/2016/10/fifa-17-5.jpg", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "https://apollo2.dl.playstation.net/cdn/EP0006/CUSA03214_00/FREE_CONTENTT2TeWoztnrgWCRqm816O/PREVIEW_SCREENSHOT1_497047.jpg", itemId);
        databaseManager.createConsoleVideoGame(ItemVariables.CONSOLES.PS4, itemId);
        databaseManager.createTrailerGame("https://www.youtube.com/embed/P9LHzVEPodg", itemId);

        itemId = databaseManager.createVideoGame("FIFA 16", 25.99, "Create more moments of magic than ever before with FIFA 16. Make every match memorable with increased control in Midfield, improved defensive moves, more stars, and a new way to play. Build your dream team in FIFA Ultimate Team, or compete as one of 12 Women’s National Teams for the first time ever in the FIFA franchise, including Germany, USA, France, Sweden, England, Brazil, and more. FIFA 16 innovates across the entire pitch, delivering a lifelike and authentic football experience.",
                5, "EA Sports", "September 22, 2015", VideoGameVariables.ESRB.EVERYONE.toString(), 23, VideoGameVariables.CATEGORY.SPORTS.toString(), VideoGameVariables.REGION.ALL.toString(), 4);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "https://upload.wikimedia.org/wikipedia/en/thumb/2/27/FIFA_16_cover.jpg/250px-FIFA_16_cover.jpg", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "https://data2.origin.com/content/dam/originx/web/app/games/fifa/fifa-16/screenshots/fifa-16/1032134_screenhi_930x524_en_US_03.jpg", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "https://data1.origin.com/content/dam/originx/web/app/games/fifa/fifa-16/screenshots/fifa-16/1032134_screenhi_930x524_en_US_01.jpg", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "https://data3.origin.com/content/dam/originx/web/app/games/fifa/fifa-16/screenshots/fifa-16/1032134_screenhi_930x524_RM_vs_AM.jpg", itemId);
        databaseManager.createConsoleVideoGame(ItemVariables.CONSOLES.XBOXONE, itemId);

        itemId = databaseManager.createVideoGame("FIFA 15", 19.99, "Whether you’re a new player or a seasoned veteran, FIFA 15's a fantastic game of football. Everything looks and feels more lifelike thanks to the power of the new Ignite Engine, enhancing the experience with astounding players and living stadiums. Challenge great AI opponents, take your skills online against other players, or become an armchair manager and create your Ultimate Team. FIFA 15 has everything football fans want.",
                5, "EA Sports", "September 23, 2014", VideoGameVariables.ESRB.EVERYONE.toString(), 23, VideoGameVariables.CATEGORY.SPORTS.toString(), VideoGameVariables.REGION.ALL.toString(), 4);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "https://upload.wikimedia.org/wikipedia/en/thumb/2/23/FIFA_15_Cover_Art.jpg/260px-FIFA_15_Cover_Art.jpg", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "https://data3.origin.com/content/dam/originx/web/app/games/fifa/fifa-15/screenshots/fifa-15/1024871_screenhi_930x524_AuthenticPlayerVisual_Messi_%5E_2014-06-18-14-08-09_2799f1b7db533b83f483c1d2c3401cac1cb6b3ec.jpg", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "https://i.ytimg.com/vi/v86GDADq3bg/maxresdefault.jpg", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "https://data3.origin.com/content/dam/originx/web/app/games/fifa/fifa-15/screenshots/fifa-15/1024871_screenhi_930x524_MantoManBattles_Napoli_vs_ACMilan.jpg", itemId);
        databaseManager.createConsoleVideoGame(ItemVariables.CONSOLES.PS4, itemId);


        // FIRE EMBLEM
        itemId = databaseManager.createVideoGame("Fire Emblem Awakening", 39.99, "Awakening uses a turn-based tactical role-playing battle system. The terrain is displayed on the top screen of the 3DS, while unit information is displayed on the bottom screen. Before each battle, the player selects a limited number of characters from their roster for use in battle. The player can either control each unit manually or activate an auto-battle option. Character movement is dictated by a tile-based movement system. During combat, player-controlled sprite characters and enemy units controlled by the game's artificial intelligence (AI) each get one turn where they position their units. An additional turn is added when unaffiliated AI-controlled units are in the field. Playable characters positioned next to each other in the field will support one-another, granting buffs, and performing actions such as blocking attacks. Two characters can also pair up as a single mobile unit, enabling both units to attack at once. As the relationship between characters strengthens, they gain greater bonuses to their strength and effectiveness when paired up in battle. During combat, the perspective switched to a 3D scene between combatants. Optional camera angles, including a first-person view through the eyes of playable characters, can be activated using the 3DS stylus.",
                5, "Nintendo", "April 19, 2012", VideoGameVariables.ESRB.EVERYONE.toString(), 23, VideoGameVariables.CATEGORY.RPG.toString(), VideoGameVariables.REGION.ASIA.toString(), 4);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "https://upload.wikimedia.org/wikipedia/en/4/44/Fire_Emblem_Awakening_box_art.png", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "https://upload.wikimedia.org/wikipedia/en/9/92/FE%3B_Awakening_FPV_screenshot.jpg", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "https://images-na.ssl-images-amazon.com/images/I/51jVuudVUOL.jpg", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "http://fireemblem.nintendo.com/about/img/sc2_2.jpg", itemId);
        databaseManager.createConsoleVideoGame(ItemVariables.CONSOLES.THREE_DS, itemId);

        // THE DIVISION
        itemId = databaseManager.createVideoGame("Tom Clancy's The Division", 39.99, "Tom Clancy’s The Division™ is a ground-breaking RPG experience that brings the genre into a modern military setting for the first time. In the wake of a devastating pandemic that sweeps through New York City, basic services fail one by one, and without access to food or water, the city quickly descends into chaos. As an agent of The Division, you’ll specialize, modify, and level up your gear, weapons, and skills to take back New York on your own terms.",
                5, "Ubisoft", "March 8, 2016", VideoGameVariables.ESRB.MATURE_17PLUS.toString(), 40, VideoGameVariables.CATEGORY.ACTION.toString(), VideoGameVariables.REGION.NA.toString(), 1);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "https://static-ca.ebgames.ca/images/products/714639/3max.jpg", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "https://static-ca.ebgames.ca/images/products/714639/5scrmax1.jpg", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "https://static-ca.ebgames.ca/images/products/714639/7scrmax2.jpg", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "https://static-ca.ebgames.ca/images/products/714639/9scrmax3.jpg", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "https://static-ca.ebgames.ca/images/products/714639/11scrmax4.jpg", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "https://static-ca.ebgames.ca/images/products/714639/13scrmax5.jpg", itemId);
        databaseManager.createConsoleVideoGame(ItemVariables.CONSOLES.XBOXONE, itemId);

        // COD
        itemId = databaseManager.createVideoGame("Call of Duty: Black Ops III", 49.99, "Co-Op Campaign: Treyarch elevates the Call of Duty® social gaming experience by delivering a campaign with the ability to play cooperatively with up to four players online, using the same battle-tested network infrastructure and social systems that support its world-class Multiplayer and Zombies game modes.\n" +
                        "\n" +
                        "Cinematic Arena-Style Play: Designed for co-op and re-playability, players encounter all the epic cinematic gameplay moments Call of Duty is known for delivering as well as new open-area arena-style gameplay elements designed to allow players to approach the game with a different strategy each time they play.\n" +
                        "\n" +
                        "Character Progression: And now, every player is completely customizable: from weapons and loadouts, to abilities and outfits, all with full progression systems and a personalized armory to show off accomplishments, providing a constantly-evolving campaign experience.",
                5, "Activision", "November 6, 2015", VideoGameVariables.ESRB.MATURE_17PLUS.toString(), 35, VideoGameVariables.CATEGORY.ACTION.toString(), VideoGameVariables.REGION.ALL.toString(), 1);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "http://cdn.akamai.steamstatic.com/steam/apps/311210/header.jpg?t=1496943039", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "http://cdn.akamai.steamstatic.com/steam/apps/311210/ss_ca7376d838d5714f916936f0070824c27c4c5641.600x338.jpg?t=1496943039", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "http://cdn.akamai.steamstatic.com/steam/apps/311210/ss_150874824f4fff0915e63ea2ade5410e576a2b70.600x338.jpg?t=1496943039", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "http://cdn.akamai.steamstatic.com/steam/apps/311210/ss_4b270e7a32d3a93a8119e7bcc3d8dcaa784f84f1.600x338.jpg?t=1496943039", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "http://cdn.akamai.steamstatic.com/steam/apps/311210/ss_c4f8101a3529e2e430641af1e061926b1f57e4e7.600x338.jpg?t=1496943039", itemId);
        databaseManager.createConsoleVideoGame(ItemVariables.CONSOLES.PS4, itemId);

        // Tom Clancy's Ghost Recon: Wildlands
        itemId = databaseManager.createVideoGame("Tom Clancy's Ghost Recon: Wildlands", 79.99, "Experience total freedom of choice in Tom Clancy's Ghost Recon Wildlands, the ultimate military shooter set in a massive open world setting.\n" +
                        "\n" +
                        "The Santa Blanca drug cartel has transformed the beautiful South American country of Bolivia into a perilous narco-state, leading to lawlessness, fear, and rampant violence. With their corrosive influence growing, the cartel plagues the citizens of Bolivia but all hope is not lost. The Ghosts, an elite US Special Forces team, are tasked to combat the cartel and save the country from collapse.\n" +
                        "\n" +
                        "You decide how to play, and every decision affects the world around you.\n" +
                        "\n",
                4, "Ubisoft", "March 7, 2017", VideoGameVariables.ESRB.MATURE_17PLUS.toString(), 35, VideoGameVariables.CATEGORY.ACTION.toString(), VideoGameVariables.REGION.ALL.toString(), 1);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "https://upload.wikimedia.org/wikipedia/en/b/b9/Ghost_Recon_Wildlands_cover_art.jpg", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "https://static-ca.ebgames.ca/images/products/719386/4scrmax1.jpg", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "https://static-ca.ebgames.ca/images/products/719386/6scrmax2.jpg", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "https://static-ca.ebgames.ca/images/products/719386/8scrmax3.jpg", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "https://static-ca.ebgames.ca/images/products/719386/10scrmax4.jpg", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "https://static-ca.ebgames.ca/images/products/719386/12scrmax5.jpg", itemId);
        databaseManager.createConsoleVideoGame(ItemVariables.CONSOLES.PS4, itemId);

        // Pokemon Sun
        itemId = databaseManager.createVideoGame("Pokémon Sun", 49.99, "Catch 'em all and build yourself a powerful Pokémon army in Pokémon Sun for Nintendo 3DS. Set out on a fresh adventure in a new world to capture unique characters, turning them into powerful fighters. You can also transfer Pokémon you collected in previous games using the Pokémon Bank.",
                5, "Nintendo", "November 18, 2016", VideoGameVariables.ESRB.EVERYONE.toString(), 35, VideoGameVariables.CATEGORY.ADVENTURE.toString(), VideoGameVariables.REGION.ALL.toString(), 1);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "https://www.serebii.net/sunmoon/sunbox.jpg", itemId);
        databaseManager.createConsoleVideoGame(ItemVariables.CONSOLES.THREE_DS, itemId);

        // Pokemon Moon
        itemId = databaseManager.createVideoGame("Pokémon Moon", 49.99, "In the Pokémon Sun and Pokémon Moon games, embark on an adventure as a Pokémon Trainer and catch, battle and trade all-new Pokémon on the tropical islands of the Alola Region. Engage in intense battles, and unleash new powerful moves. Discover and interact with Pokémon while training and connecting with your Pokémon to become the Pokémon Champion!",
                5, "Nintendo", "November 18, 2016", VideoGameVariables.ESRB.EVERYONE.toString(), 35, VideoGameVariables.CATEGORY.ADVENTURE.toString(), VideoGameVariables.REGION.ALL.toString(), 1);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "https://www.serebii.net/sunmoon/moonbox.jpg", itemId);
        databaseManager.createConsoleVideoGame(ItemVariables.CONSOLES.THREE_DS, itemId);


        // Splatoon 2
        itemId = databaseManager.createVideoGame("Splatoon 2", 59.99, "Coat a virtual world with ink when you play this Nintendo Switch Splatoon 2 game, which lets you battle for turf online or keep the action local. Stick to single-player mode for solo fun or challenge your friends in multi-player mode. Vibrant colors and innovative weapons make this Nintendo Switch Splatoon 2 game an engaging choice for kids and adults.",
                5, "Nintendo", "July 21, 2017", VideoGameVariables.ESRB.EVERYONE.toString(), 35, VideoGameVariables.CATEGORY.ACTION.toString(), VideoGameVariables.REGION.ALL.toString(), 4);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "https://pisces.bbystatic.com/image2/BestBuy_US/images/products/5721/5721523_sd.jpg;maxHeight=550;maxWidth=642", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "https://pisces.bbystatic.com/image2/BestBuy_US/images/products/5721/5721523cv11d.jpg;maxHeight=550;maxWidth=642", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "https://pisces.bbystatic.com/image2/BestBuy_US/images/products/5721/5721523cv12d.jpg;maxHeight=550;maxWidth=642", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "https://pisces.bbystatic.com/image2/BestBuy_US/images/products/5721/5721523cv13d.jpg;maxHeight=550;maxWidth=642", itemId);
        databaseManager.createConsoleVideoGame(ItemVariables.CONSOLES.SWITCH, itemId);

        // GTA
        itemId = databaseManager.createVideoGame("GTA 5", 59.99, "When a young street hustler, a retired bank robber and a terrifying psychopath find themselves entangled with some of the most frightening and deranged elements of the criminal underworld, the U.S. government and the entertainment industry, they must pull off a series of dangerous heists to survive in a ruthless city in which they can trust nobody, least of all each other.",
                3, "Rockstar Games", "September 17, 2013", VideoGameVariables.ESRB.MATURE_17PLUS.toString(), 50, VideoGameVariables.CATEGORY.ACTION.toString(), VideoGameVariables.REGION.ALL.toString(), 1);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "http://cdn.edgecast.steamstatic.com/steam/apps/271590/header.jpg?t=1497989696", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "http://cdn.edgecast.steamstatic.com/steam/apps/271590/ss_e929649b2b98ad76795d92d8489470bc5dbffddb.600x338.jpg?t=1497989696", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "http://cdn.edgecast.steamstatic.com/steam/apps/271590/ss_e080b9646300458e7e6fde55ad68c8fd3650371c.600x338.jpg?t=1497989696", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "http://cdn.edgecast.steamstatic.com/steam/apps/271590/ss_3ce5439cfdd04d1c53487f7057d45360839c0205.600x338.jpg?t=1497989696", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "http://cdn.edgecast.steamstatic.com/steam/apps/271590/ss_bb5725e2200df97b28908bccb9e8268780489506.600x338.jpg?t=1497989696", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "http://cdn.edgecast.steamstatic.com/steam/apps/271590/ss_ea299fb00e9789ce97bc6c690d9f99180bab03ab.600x338.jpg?t=1497989696", itemId);
        databaseManager.createItemImage(ItemVariables.TYPE.GAME, "http://cdn.edgecast.steamstatic.com/steam/apps/271590/ss_d1f60c9aae855a810bcdabebf43a1e03e9600ac0.600x338.jpg?t=1497989696", itemId);
        databaseManager.createConsoleVideoGame(ItemVariables.CONSOLES.XBOXONE, itemId);
        databaseManager.createTrailerGame("https://www.youtube.com/embed/VjZ5tgjPVfU", itemId);
    }


}
