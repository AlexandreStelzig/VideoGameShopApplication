package a7967917_7698299.videogameshopapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import a7967917_7698299.videogameshopapplication.helper.Helper;
import a7967917_7698299.videogameshopapplication.model.ApplicationTable;
import a7967917_7698299.videogameshopapplication.model.Cart;
import a7967917_7698299.videogameshopapplication.model.CartItem;
import a7967917_7698299.videogameshopapplication.model.Console;
import a7967917_7698299.videogameshopapplication.model.ConsoleVideoGame;
import a7967917_7698299.videogameshopapplication.model.Item;
import a7967917_7698299.videogameshopapplication.model.ItemImage;
import a7967917_7698299.videogameshopapplication.model.Order;
import a7967917_7698299.videogameshopapplication.model.OrderItemConsole;
import a7967917_7698299.videogameshopapplication.model.OrderItemGame;
import a7967917_7698299.videogameshopapplication.model.PaymentInformation;
import a7967917_7698299.videogameshopapplication.model.User;
import a7967917_7698299.videogameshopapplication.model.UserAddress;
import a7967917_7698299.videogameshopapplication.model.VideoGame;
import a7967917_7698299.videogameshopapplication.model.WishList;
import a7967917_7698299.videogameshopapplication.model.WishListItem;
import a7967917_7698299.videogameshopapplication.variables.ItemVariables;
import a7967917_7698299.videogameshopapplication.variables.OrderVariables;
import a7967917_7698299.videogameshopapplication.variables.VideoGameVariables;

/**
 * Created by alex on 2017-06-28.
 */


public class DatabaseManager {

    private static DatabaseManager databaseManager = null;
    private Database database;


    // call this method at launch
    public static void initDatabase(Context context, boolean rebuildDatabase) {
        if (databaseManager == null) {
            databaseManager = new DatabaseManager(context, rebuildDatabase);
        }
    }

    // access database with DatabaseManager.getInstance();
    public static DatabaseManager getInstance() {
        if (databaseManager == null) {
            return null;
        }
        return databaseManager;
    }


    private DatabaseManager(Context context, boolean rebuildDatabase) {

        if (rebuildDatabase) {
            context.deleteDatabase(Database.DATABASE_NAME);
        }

        database = new Database(context);

    }


    ////////////// GET METHODS //////////////

    public List<Console> getAllConsoles() {
        SQLiteDatabase db = database.getReadableDatabase();


        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseVariables.TABLE_CONSOLE.TABLE_NAME, null);
        List<Console> consoles = new ArrayList<Console>();

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                consoles.add(fetchConsoleFromCursor(cursor));
                cursor.moveToNext();
            }
        }

        cursor.close();
        return consoles;
    }

    public List<VideoGame> getAllGames() {
        SQLiteDatabase db = database.getReadableDatabase();


        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseVariables.TABLE_VIDEO_GAME.TABLE_NAME, null);
        List<VideoGame> games = new ArrayList<VideoGame>();

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                games.add(fetchVideoGameFromCursor(cursor));
                cursor.moveToNext();
            }
        }

        cursor.close();
        return games;
    }

    public VideoGame getGameById(long gameId) {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseVariables.TABLE_VIDEO_GAME.TABLE_NAME + " WHERE "
                + DatabaseVariables.TABLE_VIDEO_GAME.COLUMN_GAME_ID + "=" + gameId, null);
        VideoGame game = null;

        if (cursor.moveToFirst()) {
            game = fetchVideoGameFromCursor(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        return game;
    }

    public Console getConsoleById(long consoleId) {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseVariables.TABLE_CONSOLE.TABLE_NAME + " WHERE "
                + DatabaseVariables.TABLE_CONSOLE.COLUMN_CONSOLE_ID + "=" + consoleId, null);
        Console console = null;

        if (cursor.moveToFirst()) {
            console = fetchConsoleFromCursor(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        return console;
    }

    public List<Item> getAllItems() {

        List<Console> consoleList = getAllConsoles();
        List<VideoGame> gameList = getAllGames();

        List<Item> itemList = new ArrayList<>();

        for (int i = 0; i < consoleList.size(); i++) {
            itemList.add(consoleList.get(i));
        }

        for (int i = 0; i < gameList.size(); i++) {
            itemList.add(gameList.get(i));
        }

        return itemList;
    }


    public List<ItemImage> getImagesFromConsoleId(long consoleId, boolean firstImageOnly) {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseVariables.TABLE_IMAGE_CONSOLE.TABLE_NAME + " WHERE "
                + DatabaseVariables.TABLE_IMAGE_CONSOLE.COLUMN_CONSOLE_ID + "=" + consoleId, null);

        List<ItemImage> imageList = new ArrayList<ItemImage>();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {
                imageList.add(fetchImageConsoleFromCursor(cursor));
                cursor.moveToNext();
                if (firstImageOnly)
                    break;
            }

        }

        cursor.close();
        return imageList;
    }


    public List<ItemImage> getImagesFromGameId(long gameId, boolean firstImageOnly) {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseVariables.TABLE_IMAGE_GAME.TABLE_NAME + " WHERE "
                + DatabaseVariables.TABLE_IMAGE_GAME.COLUMN_GAME_ID + "=" + gameId, null);


        List<ItemImage> imageList = new ArrayList<ItemImage>();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {
                imageList.add(fetchImageGameFromCursor(cursor));
                cursor.moveToNext();
                if (firstImageOnly)
                    break;
            }

        }

        cursor.close();
        return imageList;
    }

    public List<ConsoleVideoGame> getConsoleVideoGameFromGameId(long gameId) {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseVariables.TABLE_CONSOLE_VIDEO_GAME.TABLE_NAME + " WHERE "
                + DatabaseVariables.TABLE_CONSOLE_VIDEO_GAME.COLUMN_GAME_ID + "=" + gameId, null);


        List<ConsoleVideoGame> consoleList = new ArrayList<ConsoleVideoGame>();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {
                consoleList.add(fetchConsoleVideoGameFromCursor(cursor));
                cursor.moveToNext();
            }

        }

        cursor.close();
        return consoleList;
    }

    public List<ConsoleVideoGame> getConsoleVideoGameFromConsoleType(ItemVariables.CONSOLES consoleType) {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseVariables.TABLE_CONSOLE_VIDEO_GAME.TABLE_NAME + " WHERE "
                + DatabaseVariables.TABLE_CONSOLE_VIDEO_GAME.COLUMN_CONSOLE_NAME + "='" + consoleType.toString() + "'", null);


        List<ConsoleVideoGame> consoleList = new ArrayList<ConsoleVideoGame>();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {
                consoleList.add(fetchConsoleVideoGameFromCursor(cursor));
                cursor.moveToNext();
            }

        }

        cursor.close();
        return consoleList;
    }


    public List<Item> getGamesFromConsoleType(ItemVariables.CONSOLES consoleType) {

        List<ConsoleVideoGame> consoleVideoGames = getConsoleVideoGameFromConsoleType(consoleType);


        List<Item> videoGames = new ArrayList<>();
        for (int i = 0; i < consoleVideoGames.size(); i++) {

            VideoGame game = getGameById(consoleVideoGames.get(i).getGameId());

            if (game != null)
                videoGames.add((Item) game);
        }

        return videoGames;
    }

    public List<ItemVariables.CONSOLES> getConsolesFromGameId(long gameId) {
        List<ConsoleVideoGame> consoleVideoGames = getConsoleVideoGameFromGameId(gameId);

        List<ItemVariables.CONSOLES> consoles = new ArrayList<>();
        for (int i = 0; i < consoleVideoGames.size(); i++) {

            ItemVariables.CONSOLES console = Helper.convertStringToConsole(consoleVideoGames.get(i).getConsoleName());

            if (console != null)
                consoles.add(console);
        }

        return consoles;
    }

    public List<Item> getGamesByCategory(VideoGameVariables.CATEGORY category) {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseVariables.TABLE_VIDEO_GAME.TABLE_NAME + " WHERE "
                + DatabaseVariables.TABLE_VIDEO_GAME.COLUMN_CATEGORY + "='" + category.toString() + "'", null);


        List<Item> gameList = new ArrayList<Item>();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {
                gameList.add(fetchVideoGameFromCursor(cursor));
                cursor.moveToNext();
            }

        }

        cursor.close();
        return gameList;
    }

    public List<Item> getItemsByQuery(String query) {
        List<Item> items = new ArrayList<>();
        // ugly stuff but it works
        List<VideoGame> videoGames = getAllGames();
        List<Console> consoles = getAllConsoles();

        for (int i = 0; i < videoGames.size(); i++) {
            VideoGame videoGameTemp = videoGames.get(i);
            if (videoGameTemp.getName().toLowerCase().contains(query))
                items.add(videoGameTemp);
        }

        for (int i = 0; i < consoles.size(); i++) {
            Console consoleTemp = consoles.get(i);
            if (consoleTemp.getName().toLowerCase().contains(query))
                items.add(consoleTemp);
        }


        return items;

    }


    public List<Item> getConsolesByType(ItemVariables.CONSOLES consoleType) {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseVariables.TABLE_CONSOLE.TABLE_NAME + " WHERE "
                + DatabaseVariables.TABLE_CONSOLE.COLUMN_CONSOLE_TYPE + "='" + consoleType.toString() + "'", null);

        List<Item> consoleList = new ArrayList<Item>();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {
                consoleList.add(fetchConsoleFromCursor(cursor));
                cursor.moveToNext();
            }

        }

        cursor.close();
        return consoleList;
    }

    public ApplicationTable getApplicationTable() {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseVariables.TABLE_APPLICATION.TABLE_NAME, null);

        ApplicationTable app = null;

        if (cursor.moveToFirst()) {
            app = fetchApplicationFromCursor(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        return app;
    }

    public User getCurrentActiveUser() {
        long activeUserId = getApplicationTable().getActiveUserId();
        User user = getUserById(activeUserId);

        // returns null if no active user
        return user;
    }

    public User getUserById(long userId) {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseVariables.TABLE_USER.TABLE_NAME + " WHERE "
                + DatabaseVariables.TABLE_USER.COLUMN_USER_ID + "=" + userId, null);

        User user = null;

        if (cursor.moveToFirst()) {
            user = fetchUserFromCursor(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        return user;
    }

    public WishList getWishlistByUserId(long userId) {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseVariables.TABLE_WISHLIST.TABLE_NAME + " WHERE "
                + DatabaseVariables.TABLE_WISHLIST.COLUMN_USER_ID + "=" + userId, null);

        WishList wishList = null;

        if (cursor.moveToFirst()) {
            wishList = fetchWishListFromCursor(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        return wishList;
    }

    public WishList getCurrentActiveUserWishlist() {
        User currentUser = getCurrentActiveUser();

        if (currentUser != null)
            return getWishlistByUserId(currentUser.getUserId());
        return null;
    }

    public List<Item> getAllWishListItems() {
        List<Item> itemList = new ArrayList<>();
        itemList.addAll(getAllWishlistConsoles());
        itemList.addAll(getAllWishlistGames());

        return itemList;
    }

    private List<Item> getAllWishlistConsoles() {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseVariables.TABLE_WISHLIST_ITEM_CONSOLE.TABLE_NAME + " WHERE "
                + DatabaseVariables.TABLE_WISHLIST_ITEM_CONSOLE.COLUMN_WISHLIST_ID + "=" + getWishlistByUserId(getCurrentActiveUser().getUserId()).getWishListId(), null);


        List<WishListItem> wishListItems = new ArrayList<>();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {
                wishListItems.add(fetchWishListItemConsoleFromCursor(cursor));
                cursor.moveToNext();
            }
        }

        List<Item> consoleList = new ArrayList<>();
        for (int i = 0; i < wishListItems.size(); i++) {
            consoleList.add(getConsoleById(wishListItems.get(i).getItemId()));
        }

        cursor.close();
        return consoleList;
    }

    private List<Item> getAllWishlistGames() {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseVariables.TABLE_WISHLIST_ITEM_GAME.TABLE_NAME + " WHERE "
                + DatabaseVariables.TABLE_WISHLIST_ITEM_GAME.COLUMN_WISHLIST_ID + "=" + getWishlistByUserId(getCurrentActiveUser().getUserId()).getWishListId(), null);


        List<WishListItem> wishListItems = new ArrayList<>();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {
                wishListItems.add(fetchWishListItemGameFromCursor(cursor));
                cursor.moveToNext();
            }
        }

        List<Item> gameList = new ArrayList<>();
        for (int i = 0; i < wishListItems.size(); i++) {
            gameList.add(getGameById(wishListItems.get(i).getItemId()));
        }

        cursor.close();
        return gameList;
    }


    ////////////// CREATE METHODS //////////////
    public long createApplicationTable() {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseVariables.TABLE_APPLICATION.COLUMN_CURRENT_USER, -1);


        long newRowId = -1;
        newRowId = db.insert(
                DatabaseVariables.TABLE_APPLICATION.TABLE_NAME,
                null,
                values);

        if (newRowId == -1)
            Log.d("DatabaseManager", "Error while creating app");
        else {
            Log.d("DatabaseManager", "added app");
        }
        return newRowId;
    }

    public long createConsole(ItemVariables.CONSOLES consoleType, String name, double price, String description, int reviewInt, String publisher, String datePublished) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseVariables.TABLE_CONSOLE.COLUMN_CONSOLE_TYPE, consoleType.toString());
        values.put(DatabaseVariables.TABLE_CONSOLE.COLUMN_NAME, name);
        values.put(DatabaseVariables.TABLE_CONSOLE.COLUMN_PRICE, price);
        values.put(DatabaseVariables.TABLE_CONSOLE.COLUMN_DESCRIPTION, description);
        values.put(DatabaseVariables.TABLE_CONSOLE.COLUMN_REVIEW, reviewInt);
        values.put(DatabaseVariables.TABLE_CONSOLE.COLUMN_PUBLISHER, publisher);
        values.put(DatabaseVariables.TABLE_CONSOLE.COLUMN_DATE_PUBLISHED, datePublished);


        long newRowId = -1;
        newRowId = db.insert(
                DatabaseVariables.TABLE_CONSOLE.TABLE_NAME,
                null,
                values);

        if (newRowId == -1)
            Log.d("DatabaseManager", "Error while adding console " + name);
        else {
            Log.d("DatabaseManager", "added " + name);
        }
        return newRowId;
    }

    public long createVideoGame(String name, double price, String description, int reviewInt,
                                String publisher, String datePublished, String esrbRating, double gameLength,
                                String gameCategory, String gameRegion, int numberOfPlayers) {

        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseVariables.TABLE_VIDEO_GAME.COLUMN_NAME, name);
        values.put(DatabaseVariables.TABLE_VIDEO_GAME.COLUMN_PRICE, price);
        values.put(DatabaseVariables.TABLE_VIDEO_GAME.COLUMN_DESCRIPTION, description);
        values.put(DatabaseVariables.TABLE_VIDEO_GAME.COLUMN_REVIEW, reviewInt);
        values.put(DatabaseVariables.TABLE_VIDEO_GAME.COLUMN_PUBLISHER, publisher);
        values.put(DatabaseVariables.TABLE_VIDEO_GAME.COLUMN_DATE_PUBLISHED, datePublished);

        values.put(DatabaseVariables.TABLE_VIDEO_GAME.COLUMN_ESRB, esrbRating);
        values.put(DatabaseVariables.TABLE_VIDEO_GAME.COLUMN_LENGTH, gameLength);
        values.put(DatabaseVariables.TABLE_VIDEO_GAME.COLUMN_CATEGORY, gameCategory);
        values.put(DatabaseVariables.TABLE_VIDEO_GAME.COLUMN_REGION, gameRegion);
        values.put(DatabaseVariables.TABLE_VIDEO_GAME.COLUMN_NUMBER_PLAYER, numberOfPlayers);

        long newRowId = -1;
        newRowId = db.insert(
                DatabaseVariables.TABLE_VIDEO_GAME.TABLE_NAME,
                null,
                values);

        if (newRowId == -1)
            Log.d("DatabaseManager", "Error while adding console " + name);
        else {
            Log.d("DatabaseManager", "added " + name);
        }
        return newRowId;

    }

    public long createUser(String email, String password, String firstName, String lastName) {

        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseVariables.TABLE_USER.COLUMN_EMAIL, email);
        values.put(DatabaseVariables.TABLE_USER.COLUMN_PASSWORD, password);
        values.put(DatabaseVariables.TABLE_USER.COLUMN_FIRST_NAME, firstName);
        values.put(DatabaseVariables.TABLE_USER.COLUMN_LAST_NAME, lastName);

        long newRowId = -1;
        newRowId = db.insert(
                DatabaseVariables.TABLE_USER.TABLE_NAME,
                null,
                values);

        // create user 1 to 1 associations
        createWishlist(newRowId);
        createCart(newRowId);

        if (newRowId == -1)
            Log.d("DatabaseManager", "Error while adding user " + email);
        else {
            Log.d("DatabaseManager", "added " + email);
        }
        return newRowId;
    }

    private long createWishlist(long userId) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseVariables.TABLE_WISHLIST.COLUMN_USER_ID, userId);

        long newRowId = -1;
        newRowId = db.insert(
                DatabaseVariables.TABLE_WISHLIST.TABLE_NAME,
                null,
                values);
        if (newRowId == -1)
            Log.d("DatabaseManager", "Error while adding wishlist " + userId);
        else {
            Log.d("DatabaseManager", "added " + userId);
        }
        return newRowId;
    }

    private long createCart(long userId) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseVariables.TABLE_CART.COLUMN_USER_ID, userId);

        long newRowId = -1;
        newRowId = db.insert(
                DatabaseVariables.TABLE_CART.TABLE_NAME,
                null,
                values);
        if (newRowId == -1)
            Log.d("DatabaseManager", "Error while adding cart " + userId);
        else {
            Log.d("DatabaseManager", "added " + userId);
        }
        return newRowId;
    }


    public long createItemImage(ItemVariables.TYPE itemType, String imageURL, long itemId) {

        if (itemType == ItemVariables.TYPE.CONSOLE)
            return createItemImageConsole(imageURL, itemId);
        else
            return createItemImageGame(imageURL, itemId);

    }

    private long createItemImageConsole(String imageURL, long itemId) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseVariables.TABLE_IMAGE_CONSOLE.COLUMN_IMAGE_URL, imageURL);
        values.put(DatabaseVariables.TABLE_IMAGE_CONSOLE.COLUMN_CONSOLE_ID, itemId);

        long newRowId = -1;
        newRowId = db.insert(
                DatabaseVariables.TABLE_IMAGE_CONSOLE.TABLE_NAME,
                null,
                values);

        if (newRowId == -1)
            Log.d("DatabaseManager", "Error while adding console " + itemId);
        else {
            Log.d("DatabaseManager", "added " + itemId);
        }
        return newRowId;

    }

    private long createItemImageGame(String imageURL, long itemId) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseVariables.TABLE_IMAGE_GAME.COLUMN_IMAGE_URL, imageURL);
        values.put(DatabaseVariables.TABLE_IMAGE_GAME.COLUMN_GAME_ID, itemId);

        long newRowId = -1;
        newRowId = db.insert(
                DatabaseVariables.TABLE_IMAGE_GAME.TABLE_NAME,
                null,
                values);

        if (newRowId == -1)
            Log.d("DatabaseManager", "Error while adding console " + itemId);
        else {
            Log.d("DatabaseManager", "added " + itemId);
        }
        return newRowId;

    }


    public long createConsoleVideoGame(ItemVariables.CONSOLES consoleType, long gameId) {

        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseVariables.TABLE_CONSOLE_VIDEO_GAME.COLUMN_CONSOLE_NAME, consoleType.toString());
        values.put(DatabaseVariables.TABLE_CONSOLE_VIDEO_GAME.COLUMN_GAME_ID, gameId);

        long newRowId = -1;
        newRowId = db.insert(
                DatabaseVariables.TABLE_CONSOLE_VIDEO_GAME.TABLE_NAME,
                null,
                values);

        if (newRowId == -1)
            Log.d("DatabaseManager", "Error while adding consoleVideoGame " + gameId);
        else {
            Log.d("DatabaseManager", "added " + gameId);
        }
        return newRowId;

    }

    public long createCartItemConsole(ItemVariables.CONSOLES consoleType, long gameId) {

        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseVariables.TABLE_CONSOLE_VIDEO_GAME.COLUMN_CONSOLE_NAME, consoleType.toString());
        values.put(DatabaseVariables.TABLE_CONSOLE_VIDEO_GAME.COLUMN_GAME_ID, gameId);

        long newRowId = -1;
        newRowId = db.insert(
                DatabaseVariables.TABLE_CONSOLE_VIDEO_GAME.TABLE_NAME,
                null,
                values);

        if (newRowId == -1)
            Log.d("DatabaseManager", "Error while adding consoleVideoGame " + gameId);
        else {
            Log.d("DatabaseManager", "added " + gameId);
        }
        return newRowId;

    }


    public long createWishList(ItemVariables.TYPE itemType, long itemId) {

        if (itemType == ItemVariables.TYPE.CONSOLE)
            return createWishlistConsole(itemId);
        else
            return createWishlistGame(itemId);

    }

    private long createWishlistGame(long itemId) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseVariables.TABLE_WISHLIST_ITEM_GAME.COLUMN_GAME_ID, itemId);
        values.put(DatabaseVariables.TABLE_WISHLIST_ITEM_GAME.COLUMN_WISHLIST_ID, getCurrentActiveUserWishlist().getWishListId());

        long newRowId = -1;
        newRowId = db.insert(
                DatabaseVariables.TABLE_WISHLIST_ITEM_GAME.TABLE_NAME,
                null,
                values);

        if (newRowId == -1)
            Log.d("DatabaseManager", "Error while adding console " + itemId);
        else {
            Log.d("DatabaseManager", "added " + itemId);
        }
        return newRowId;

    }

    private long createWishlistConsole(long itemId) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseVariables.TABLE_WISHLIST_ITEM_CONSOLE.COLUMN_CONSOLE_ID, itemId);
        values.put(DatabaseVariables.TABLE_WISHLIST_ITEM_CONSOLE.COLUMN_WISHLIST_ID, getCurrentActiveUserWishlist().getWishListId());

        long newRowId = -1;
        newRowId = db.insert(
                DatabaseVariables.TABLE_WISHLIST_ITEM_CONSOLE.TABLE_NAME,
                null,
                values);

        if (newRowId == -1)
            Log.d("DatabaseManager", "Error while adding console " + itemId);
        else {
            Log.d("DatabaseManager", "added " + itemId);
        }
        return newRowId;
    }


    ////////////// UPDATE METHODS //////////////

    public void setCurrentActiveUser(long userId) {
        SQLiteDatabase db = database.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseVariables.TABLE_APPLICATION.COLUMN_CURRENT_USER, userId);
        db.update(DatabaseVariables.TABLE_APPLICATION.TABLE_NAME, values,
                DatabaseVariables.TABLE_APPLICATION.COLUMN_APPLICATION_ID + "=" + getApplicationTable().getAppId(), null);

    }
    ////////////// DELETE METHODS //////////////

    public boolean deleteWishlist(long itemId, ItemVariables.TYPE itemType) {
        SQLiteDatabase db = database.getReadableDatabase();

        if (itemType == ItemVariables.TYPE.CONSOLE)
            return db.delete(DatabaseVariables.TABLE_WISHLIST_ITEM_CONSOLE.TABLE_NAME, DatabaseVariables.TABLE_WISHLIST_ITEM_CONSOLE.COLUMN_CONSOLE_ID
                    + "=" + itemId, null) > 0;
        else
            return db.delete(DatabaseVariables.TABLE_WISHLIST_ITEM_GAME.TABLE_NAME, DatabaseVariables.TABLE_WISHLIST_ITEM_GAME.COLUMN_GAME_ID
                    + "=" + itemId, null) > 0;


    }


    ////////////// HELPERS METHODS //////////////

    private UserAddress fetchAddressFromCursor(Cursor cursor) {

        int addressId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_ADDRESS.COLUMN_ADDRESS_ID));
        int userId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_ADDRESS.COLUMN_USER_ID));
        String street = cursor.getString(cursor
                .getColumnIndex(DatabaseVariables.TABLE_ADDRESS.COLUMN_STREET));
        String country = cursor.getString(cursor
                .getColumnIndex(DatabaseVariables.TABLE_ADDRESS.COLUMN_COUNTRY));
        String state = cursor.getString(cursor
                .getColumnIndex(DatabaseVariables.TABLE_ADDRESS.COLUMN_STATE));
        String city = cursor.getString(cursor
                .getColumnIndex(DatabaseVariables.TABLE_ADDRESS.COLUMN_CITY));
        String postalCode = cursor.getString(cursor
                .getColumnIndex(DatabaseVariables.TABLE_ADDRESS.COLUMN_POSTAL_CODE));

        return new UserAddress(addressId, userId, street, country, state, city, postalCode);
    }


    private Cart fetchCartFromCursor(Cursor cursor) {
        int cartId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_CART.COLUMN_CART_ID));
        int userId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_CART.COLUMN_USER_ID));

        return new Cart(cartId, userId);
    }

    private CartItem fetchCartItemConsoleFromCursor(Cursor cursor) {
        int cartId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_CART_ITEM_CONSOLE.COLUMN_CART_ID));
        int consoleId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_CART_ITEM_CONSOLE.COLUMN_CONSOLE_ID));
        int amount = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_CART_ITEM_CONSOLE.COLUMN_AMOUNT));

        return new CartItem(ItemVariables.TYPE.CONSOLE, consoleId, cartId, amount);
    }

    private CartItem fetchCartItemGameFromCursor(Cursor cursor) {
        int cartId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_CART_ITEM_GAME.COLUMN_CART_ID));
        int gameId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_CART_ITEM_GAME.COLUMN_GAME_ID));
        int amount = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_CART_ITEM_GAME.COLUMN_AMOUNT));

        return new CartItem(ItemVariables.TYPE.GAME, gameId, cartId, amount);
    }

    private Console fetchConsoleFromCursor(Cursor cursor) {

        long consoleId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_CONSOLE.COLUMN_CONSOLE_ID));
        String name = cursor.getString(cursor
                .getColumnIndex(DatabaseVariables.TABLE_CONSOLE.COLUMN_NAME));
        String console = cursor.getString(cursor
                .getColumnIndex(DatabaseVariables.TABLE_CONSOLE.COLUMN_CONSOLE_TYPE));
        double price = cursor.getDouble(cursor
                .getColumnIndex(DatabaseVariables.TABLE_CONSOLE.COLUMN_PRICE));
        String description = cursor.getString(cursor
                .getColumnIndex(DatabaseVariables.TABLE_CONSOLE.COLUMN_DESCRIPTION));
        int reviewInt = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_CONSOLE.COLUMN_REVIEW));
        String publisher = cursor.getString(cursor
                .getColumnIndex(DatabaseVariables.TABLE_CONSOLE.COLUMN_PUBLISHER));
        String datePublished = cursor.getString(cursor
                .getColumnIndex(DatabaseVariables.TABLE_CONSOLE.COLUMN_DATE_PUBLISHED));

        ItemVariables.STAR_REVIEW review = Helper.convertIntegerToReview(reviewInt);
        ItemVariables.CONSOLES consoleType = Helper.convertStringToConsole(console);

        return new Console(ItemVariables.TYPE.CONSOLE, name, price, description, review, publisher, datePublished, consoleId, consoleType);
    }

    private ConsoleVideoGame fetchConsoleVideoGameFromCursor(Cursor cursor) {

        String consoleName = cursor.getString(cursor
                .getColumnIndex(DatabaseVariables.TABLE_CONSOLE_VIDEO_GAME.COLUMN_CONSOLE_NAME));
        long gameId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_CONSOLE_VIDEO_GAME.COLUMN_GAME_ID));


        return new ConsoleVideoGame(consoleName, gameId);
    }

    private ItemImage fetchImageConsoleFromCursor(Cursor cursor) {

        long itemId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_IMAGE_CONSOLE.COLUMN_IMAGE_ID));
        long consoleId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_IMAGE_CONSOLE.COLUMN_CONSOLE_ID));
        String imageURL = cursor.getString(cursor
                .getColumnIndex(DatabaseVariables.TABLE_IMAGE_CONSOLE.COLUMN_IMAGE_URL));

        return new ItemImage(ItemVariables.TYPE.CONSOLE, itemId, imageURL, consoleId);
    }

    private ItemImage fetchImageGameFromCursor(Cursor cursor) {

        long itemId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_IMAGE_GAME.COLUMN_IMAGE_ID));
        long gameId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_IMAGE_GAME.COLUMN_GAME_ID));
        String imageURL = cursor.getString(cursor
                .getColumnIndex(DatabaseVariables.TABLE_IMAGE_GAME.COLUMN_IMAGE_URL));

        return new ItemImage(ItemVariables.TYPE.GAME, itemId, imageURL, gameId);
    }

    private Order fetchOrderFromCursor(Cursor cursor) {

        long orderId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_ORDER.COLUMN_ORDER_ID));
        long userId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_ORDER.COLUMN_USER_ID));
        String dateOrdered = cursor.getString(cursor
                .getColumnIndex(DatabaseVariables.TABLE_ORDER.COLUMN_DATE_ORDERED));
        String dateArriving = cursor.getString(cursor
                .getColumnIndex(DatabaseVariables.TABLE_ORDER.COLUMN_DATE_ARRIVING));
        String status = cursor.getString(cursor
                .getColumnIndex(DatabaseVariables.TABLE_ORDER.COLUMN_STATUS));
        int cardNumber = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_ORDER.COLUMN_CARD_NUMBER));
        int nameOnCard = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_ORDER.COLUMN_NAME_ON_CARD));
        int expirationMonth = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_ORDER.COLUMN_EXPIRATION_MONTH));
        int expirationYear = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_ORDER.COLUMN_EXPIRATION_YEAR));
        String street = cursor.getString(cursor
                .getColumnIndex(DatabaseVariables.TABLE_ORDER.COLUMN_STREET));
        String country = cursor.getString(cursor
                .getColumnIndex(DatabaseVariables.TABLE_ORDER.COLUMN_COUNTRY));
        String state = cursor.getString(cursor
                .getColumnIndex(DatabaseVariables.TABLE_ORDER.COLUMN_STATE));
        String city = cursor.getString(cursor
                .getColumnIndex(DatabaseVariables.TABLE_ORDER.COLUMN_CITY));
        String postalCode = cursor.getString(cursor
                .getColumnIndex(DatabaseVariables.TABLE_ORDER.COLUMN_POSTAL_CODE));

        OrderVariables.STATUS convertedStatus = Helper.convertStringToStatus(status);

        return new Order(orderId, userId, dateOrdered,
                dateArriving, convertedStatus, cardNumber, nameOnCard,
                expirationMonth, expirationYear, street, country, state,
                city, postalCode);
    }


    private OrderItemConsole fetchOrderItemConsoleFromCursor(Cursor cursor) {

        long consoleId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_ORDER_ITEM_CONSOLE.COLUMN_CONSOLE_ID));
        long orderId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_ORDER_ITEM_CONSOLE.COLUMN_ORDER_ID));
        int amount = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_ORDER_ITEM_CONSOLE.COLUMN_AMOUNT));


        return new OrderItemConsole(consoleId, orderId, amount);
    }


    private OrderItemGame fetchOrderItemGameFromCursor(Cursor cursor) {

        long gameId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_ORDER_ITEM_GAME.COLUMN_GAME_ID));
        long orderId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_ORDER_ITEM_GAME.COLUMN_ORDER_ID));
        int amount = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_ORDER_ITEM_GAME.COLUMN_AMOUNT));


        return new OrderItemGame(gameId, orderId, amount);
    }

    private PaymentInformation fetchPaymentInformationFromCursor(Cursor cursor) {

        long paymentId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_PAYMENT_INFORMATION.COLUMN_PAYMENT_ID));
        long userId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_PAYMENT_INFORMATION.COLUMN_USER_ID));
        int cardNumber = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_PAYMENT_INFORMATION.COLUMN_CARD_NUMBER));
        String nameOnCard = cursor.getString(cursor
                .getColumnIndex(DatabaseVariables.TABLE_PAYMENT_INFORMATION.COLUMN_NAME_ON_CARD));
        int expirationMonth = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_PAYMENT_INFORMATION.COLUMN_EXPIRATION_MONTH));
        int expirationYear = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_PAYMENT_INFORMATION.COLUMN_EXPIRATION_YEAR));

        return new PaymentInformation(paymentId, userId, cardNumber, nameOnCard, expirationMonth, expirationYear);
    }

    private User fetchUserFromCursor(Cursor cursor) {

        long userId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_USER.COLUMN_USER_ID));
        String email = cursor.getString(cursor
                .getColumnIndex(DatabaseVariables.TABLE_USER.COLUMN_EMAIL));
        String password = cursor.getString(cursor
                .getColumnIndex(DatabaseVariables.TABLE_USER.COLUMN_PASSWORD));
        String firstName = cursor.getString(cursor
                .getColumnIndex(DatabaseVariables.TABLE_USER.COLUMN_FIRST_NAME));
        String lastName = cursor.getString(cursor
                .getColumnIndex(DatabaseVariables.TABLE_USER.COLUMN_LAST_NAME));


        return new User(userId, email, password, firstName, lastName);
    }

    private ApplicationTable fetchApplicationFromCursor(Cursor cursor) {

        long appId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_APPLICATION.COLUMN_APPLICATION_ID));
        long userId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_APPLICATION.COLUMN_CURRENT_USER));


        return new ApplicationTable(appId, userId);
    }

    private WishList fetchWishListFromCursor(Cursor cursor) {
        int wishListId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_WISHLIST.COLUMN_WISHLIST_ID));
        int userId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_WISHLIST.COLUMN_USER_ID));

        return new WishList(wishListId, userId);
    }

    private WishListItem fetchWishListItemGameFromCursor(Cursor cursor) {

        long gameId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_WISHLIST_ITEM_GAME.COLUMN_GAME_ID));
        long wishListId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_WISHLIST_ITEM_GAME.COLUMN_WISHLIST_ID));

        return new WishListItem(ItemVariables.TYPE.GAME, gameId, wishListId);
    }

    private WishListItem fetchWishListItemConsoleFromCursor(Cursor cursor) {

        long consoleId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_WISHLIST_ITEM_CONSOLE.COLUMN_CONSOLE_ID));
        long wishListId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_WISHLIST_ITEM_CONSOLE.COLUMN_WISHLIST_ID));

        return new WishListItem(ItemVariables.TYPE.CONSOLE, consoleId, wishListId);
    }

    private VideoGame fetchVideoGameFromCursor(Cursor cursor) {

        String name = cursor.getString(cursor
                .getColumnIndex(DatabaseVariables.TABLE_VIDEO_GAME.COLUMN_NAME));
        double price = cursor.getDouble(cursor
                .getColumnIndex(DatabaseVariables.TABLE_VIDEO_GAME.COLUMN_PRICE));
        String description = cursor.getString(cursor
                .getColumnIndex(DatabaseVariables.TABLE_VIDEO_GAME.COLUMN_DESCRIPTION));
        int reviewInteger = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_VIDEO_GAME.COLUMN_REVIEW));
        String publisher = cursor.getString(cursor
                .getColumnIndex(DatabaseVariables.TABLE_VIDEO_GAME.COLUMN_PUBLISHER));
        String datePublished = cursor.getString(cursor
                .getColumnIndex(DatabaseVariables.TABLE_VIDEO_GAME.COLUMN_DATE_PUBLISHED));
        String esrbString = cursor.getString(cursor
                .getColumnIndex(DatabaseVariables.TABLE_VIDEO_GAME.COLUMN_ESRB));
        double gameLength = cursor.getDouble(cursor
                .getColumnIndex(DatabaseVariables.TABLE_VIDEO_GAME.COLUMN_LENGTH));
        String gameCategoryString = cursor.getString(cursor
                .getColumnIndex(DatabaseVariables.TABLE_VIDEO_GAME.COLUMN_CATEGORY));
        String gameRegionString = cursor.getString(cursor
                .getColumnIndex(DatabaseVariables.TABLE_VIDEO_GAME.COLUMN_REGION));
        int numberOfPlayers = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_VIDEO_GAME.COLUMN_NUMBER_PLAYER));
        long gameId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_VIDEO_GAME.COLUMN_GAME_ID));

        ItemVariables.STAR_REVIEW review = Helper.convertIntegerToReview(reviewInteger);
        VideoGameVariables.ESRB esrb = Helper.convertStringToesrb(esrbString);
        VideoGameVariables.CATEGORY gameCategory = Helper.convertStringToCategory(gameCategoryString);
        VideoGameVariables.REGION gameRegion = Helper.convertStringToRegion(gameRegionString);

        return new VideoGame(name, price, description, review,
                publisher, datePublished, esrb,
                gameLength, gameCategory, gameRegion, numberOfPlayers, gameId);
    }


    public boolean isDatabaseEmpty() {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM " + DatabaseVariables.TABLE_CONSOLE.TABLE_NAME, null);

        if (mCursor.getCount() == 0) {
            Log.d("isDatabaseEmpty", "init database");
            return true;
        } else {
            Log.d("isDatabaseEmpty", "database already initiated");
            return false;
        }
    }

    public boolean isItemAlreadyInWishlist(long itemId, ItemVariables.TYPE itemType) {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor mCursor = null;
        if (itemType == ItemVariables.TYPE.CONSOLE) {
            mCursor = db.rawQuery("SELECT * FROM " + DatabaseVariables.TABLE_WISHLIST_ITEM_CONSOLE.TABLE_NAME + " WHERE "
                    + DatabaseVariables.TABLE_WISHLIST_ITEM_CONSOLE.COLUMN_CONSOLE_ID + "=" + itemId, null);
        } else {
            mCursor = db.rawQuery("SELECT * FROM " + DatabaseVariables.TABLE_WISHLIST_ITEM_GAME.TABLE_NAME + " WHERE "
                    + DatabaseVariables.TABLE_WISHLIST_ITEM_GAME.COLUMN_GAME_ID + "=" + itemId, null);
        }

        if (mCursor.getCount() == 0) {
            Log.d("isDatabaseEmpty", "item doesn't exist");
            return false;
        } else {
            Log.d("isDatabaseEmpty", "item already exist");
            return true;
        }

    }

    public boolean isItemAlreadyInCart(long itemId, ItemVariables.TYPE itemType) {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor mCursor = null;
        if (itemType == ItemVariables.TYPE.CONSOLE) {
            mCursor = db.rawQuery("SELECT * FROM " + DatabaseVariables.TABLE_CART_ITEM_CONSOLE.TABLE_NAME + " WHERE "
                    + DatabaseVariables.TABLE_CART_ITEM_CONSOLE.COLUMN_CONSOLE_ID + "=" + itemId, null);
        } else {
            mCursor = db.rawQuery("SELECT * FROM " + DatabaseVariables.TABLE_CART_ITEM_GAME.TABLE_NAME + " WHERE "
                    + DatabaseVariables.TABLE_CART_ITEM_GAME.COLUMN_GAME_ID + "=" + itemId, null);
        }

        if (mCursor.getCount() == 0) {
            Log.d("isDatabaseEmpty", "item doesn't exist");
            return false;
        } else {
            Log.d("isDatabaseEmpty", "item already exist");
            return true;
        }

    }

}
