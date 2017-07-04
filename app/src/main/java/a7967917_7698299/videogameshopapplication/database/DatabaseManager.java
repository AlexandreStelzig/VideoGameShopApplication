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
                if(firstImageOnly)
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
                if(firstImageOnly)
                    break;
            }

        }

        cursor.close();
        return imageList;
    }


    ////////////// CREATE METHODS //////////////

    public long createConsole(String name, double price, String description, int reviewInt, String publisher, String datePublished) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();

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
                                String publisher, String datePublished, String ersbRating, double gameLength,
                                String gameCategory, String gameRegion, int numberOfPlayers) {

        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseVariables.TABLE_VIDEO_GAME.COLUMN_NAME, name);
        values.put(DatabaseVariables.TABLE_VIDEO_GAME.COLUMN_PRICE, price);
        values.put(DatabaseVariables.TABLE_VIDEO_GAME.COLUMN_DESCRIPTION, description);
        values.put(DatabaseVariables.TABLE_VIDEO_GAME.COLUMN_REVIEW, reviewInt);
        values.put(DatabaseVariables.TABLE_VIDEO_GAME.COLUMN_PUBLISHER, publisher);
        values.put(DatabaseVariables.TABLE_VIDEO_GAME.COLUMN_DATE_PUBLISHED, datePublished);

        values.put(DatabaseVariables.TABLE_VIDEO_GAME.COLUMN_ERSB, ersbRating);
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


    ////////////// UPDATE METHODS //////////////

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

        return new Console(consoleId, name, price, description, review, publisher, datePublished);
    }

    private ConsoleVideoGame fetchConsoleVideoGameFromCursor(Cursor cursor) {

        long consoleId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_CONSOLE_VIDEO_GAME.COLUMN_CONSOLE_ID));
        long gameId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_CONSOLE_VIDEO_GAME.COLUMN_GAME_ID));


        return new ConsoleVideoGame(consoleId, gameId);
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
        String ERSBString = cursor.getString(cursor
                .getColumnIndex(DatabaseVariables.TABLE_VIDEO_GAME.COLUMN_ERSB));
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
        VideoGameVariables.ERSB ersb = Helper.convertStringToERSB(ERSBString);
        VideoGameVariables.CATEGORY gameCategory = Helper.convertStringToCategory(gameCategoryString);
        VideoGameVariables.REGION gameRegion = Helper.convertStringToRegion(gameRegionString);

        return new VideoGame(name, price, description, review,
                publisher, datePublished, ersb,
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

}
