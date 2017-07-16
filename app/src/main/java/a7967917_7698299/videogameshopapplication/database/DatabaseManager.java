package a7967917_7698299.videogameshopapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import a7967917_7698299.videogameshopapplication.helper.Helper;
import a7967917_7698299.videogameshopapplication.manager.SortingManager;
import a7967917_7698299.videogameshopapplication.model.ApplicationTable;
import a7967917_7698299.videogameshopapplication.model.Cart;
import a7967917_7698299.videogameshopapplication.model.CartItem;
import a7967917_7698299.videogameshopapplication.model.Console;
import a7967917_7698299.videogameshopapplication.model.ConsoleVideoGame;
import a7967917_7698299.videogameshopapplication.model.Item;
import a7967917_7698299.videogameshopapplication.model.ItemImage;
import a7967917_7698299.videogameshopapplication.model.ItemTrailer;
import a7967917_7698299.videogameshopapplication.model.Order;
import a7967917_7698299.videogameshopapplication.model.OrderItem;
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

    public List<Item> getAllConsoles() {
        SQLiteDatabase db = database.getReadableDatabase();


        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseVariables.TABLE_CONSOLE.TABLE_NAME, null);
        List<Item> consoles = new ArrayList<Item>();

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                consoles.add(fetchConsoleFromCursor(cursor));
                cursor.moveToNext();
            }
        }

        cursor.close();
        return consoles;
    }

    public List<Item> getAllGames() {
        SQLiteDatabase db = database.getReadableDatabase();


        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseVariables.TABLE_VIDEO_GAME.TABLE_NAME, null);
        List<Item> games = new ArrayList<Item>();

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

        List<Item> consoleList = getAllConsoles();
        List<Item> gameList = getAllGames();

        List<Item> itemList = new ArrayList<>();

        for (int i = 0; i < consoleList.size(); i++) {
            itemList.add(consoleList.get(i));
        }

        for (int i = 0; i < gameList.size(); i++) {
            itemList.add(gameList.get(i));
        }

        itemList = SortingManager.getInstance().sortItemList(itemList);

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

    public ItemTrailer getTrailerFromGameId(long gameId) {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseVariables.TABLE_TRAILER.TABLE_NAME + " WHERE "
                + DatabaseVariables.TABLE_TRAILER.COLUMN_GAME_ID + "=" + gameId, null);


        ItemTrailer trailer = null;

        if (cursor.moveToFirst()) {

            trailer = (fetchTrailerGameFromCursor(cursor));
            cursor.moveToNext();

        }

        cursor.close();
        return trailer;
    }


    public CartItem getCartItemConsoleById(long consoleId) {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM " + DatabaseVariables.TABLE_CART_ITEM_CONSOLE.TABLE_NAME + " WHERE "
                + DatabaseVariables.TABLE_CART_ITEM_CONSOLE.COLUMN_CONSOLE_ID + "=" + consoleId, null);

        CartItem cartItem = null;

        if (mCursor.moveToFirst()) {
            cartItem = fetchCartItemConsoleFromCursor(mCursor);
            mCursor.moveToNext();
        }
        mCursor.close();
        return cartItem;
    }


    public CartItem getCartItemGameById(long gameId) {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT * FROM " + DatabaseVariables.TABLE_CART_ITEM_GAME.TABLE_NAME + " WHERE "
                + DatabaseVariables.TABLE_CART_ITEM_GAME.COLUMN_GAME_ID + "=" + gameId, null);

        CartItem cartItem = null;

        if (mCursor.moveToFirst()) {
            cartItem = fetchCartItemGameFromCursor(mCursor);
            mCursor.moveToNext();
        }
        mCursor.close();
        return cartItem;
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

        videoGames = SortingManager.getInstance().sortItemList(videoGames);

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

        gameList = SortingManager.getInstance().sortItemList(gameList);

        cursor.close();
        return gameList;
    }

    public List<Item> getItemsByQuery(String query) {
        query = query.toLowerCase();
        List<Item> items = new ArrayList<>();
        // ugly stuff but it works
        List<Item> videoGames = getAllGames();
        List<Item> consoles = getAllConsoles();

        for (int i = 0; i < videoGames.size(); i++) {
            VideoGame videoGameTemp = (VideoGame) videoGames.get(i);
            if (videoGameTemp.getName().toLowerCase().contains(query))
                items.add(videoGameTemp);
        }

        for (int i = 0; i < consoles.size(); i++) {
            Console consoleTemp = (Console) consoles.get(i);
            if (consoleTemp.getName().toLowerCase().contains(query))
                items.add(consoleTemp);
        }


        items = SortingManager.getInstance().sortItemList(items);


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

        consoleList = SortingManager.getInstance().sortItemList(consoleList);

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

    public User getUserByEmail(String email){
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseVariables.TABLE_USER.TABLE_NAME + " WHERE "
                + DatabaseVariables.TABLE_USER.COLUMN_EMAIL + "='" + email + "'", null);

        User user = null;

        if (cursor.moveToFirst()) {
            user = fetchUserFromCursor(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        return user;
    }

    private WishList getWishlistByUserId(long userId) {
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


    private WishList getCurrentActiveUserWishlist() {
        User currentUser = getCurrentActiveUser();

        if (currentUser != null)
            return getWishlistByUserId(currentUser.getUserId());
        return null;
    }


    public List<Item> getAllWishListItemsFromActiveUser() {
        List<Item> itemList = new ArrayList<>();
        itemList.addAll(getAllWishlistConsoles());
        itemList.addAll(getAllWishlistGames());

        return itemList;
    }

    private List<Item> getAllWishlistConsoles() {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseVariables.TABLE_WISHLIST_ITEM_CONSOLE.TABLE_NAME + " WHERE "
                + DatabaseVariables.TABLE_WISHLIST_ITEM_CONSOLE.COLUMN_WISHLIST_ID + "=" + getCurrentActiveUserWishlist().getWishListId(), null);


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
                + DatabaseVariables.TABLE_WISHLIST_ITEM_GAME.COLUMN_WISHLIST_ID + "=" + getCurrentActiveUserWishlist().getWishListId(), null);


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

    public List<Item> getCurrentActiveUserItemsInCart() {
        List<CartItem> cartItems = getAllCartItems();


        List<Item> items = new ArrayList<>();
        for (int i = 0; i < cartItems.size(); i++) {

            CartItem temp = cartItems.get(i);

            if (temp.getItemType() == ItemVariables.TYPE.CONSOLE) {
                items.add(getConsoleById(temp.getItemId()));
            } else {
                items.add(getGameById(temp.getItemId()));
            }
        }
        return items;

    }

    public List<CartItem> getAllCartItems() {
        List<CartItem> cartItemList = new ArrayList<>();
        cartItemList.addAll(getAllCartItemConsoles());
        cartItemList.addAll(getAllCartItemGames());

        return cartItemList;
    }

    private List<CartItem> getAllCartItemConsoles() {

        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseVariables.TABLE_CART_ITEM_CONSOLE.TABLE_NAME + " WHERE "
                + DatabaseVariables.TABLE_CART_ITEM_CONSOLE.COLUMN_CART_ID + "=" + getCurrentActiveUserCart().getCartId(), null);


        List<CartItem> cartItems = new ArrayList<>();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {
                cartItems.add(fetchCartItemConsoleFromCursor(cursor));
                cursor.moveToNext();
            }
        }

        cursor.close();
        return cartItems;
    }

    private List<CartItem> getAllCartItemGames() {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseVariables.TABLE_CART_ITEM_GAME.TABLE_NAME + " WHERE "
                + DatabaseVariables.TABLE_CART_ITEM_GAME.COLUMN_CART_ID + "=" + getCurrentActiveUserCart().getCartId(), null);


        List<CartItem> cartItems = new ArrayList<>();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {
                cartItems.add(fetchCartItemGameFromCursor(cursor));
                cursor.moveToNext();
            }
        }

        cursor.close();
        return cartItems;
    }


    private Cart getCartByUserId(long userId) {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseVariables.TABLE_CART.TABLE_NAME + " WHERE "
                + DatabaseVariables.TABLE_CART.COLUMN_USER_ID + "=" + userId, null);

        Cart cart = null;

        if (cursor.moveToFirst()) {
            cart = fetchCartFromCursor(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        return cart;
    }

    private Cart getCurrentActiveUserCart() {
        User currentUser = getCurrentActiveUser();

        if (currentUser != null)
            return getCartByUserId(currentUser.getUserId());
        return null;
    }


    public List<UserAddress> getAllAddressesFromActiveUser() {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseVariables.TABLE_ADDRESS.TABLE_NAME + " WHERE "
                + DatabaseVariables.TABLE_ADDRESS.COLUMN_USER_ID + "=" + getCurrentActiveUser().getUserId(), null);
        List<UserAddress> addressList = new ArrayList<>();
        if(cursor.moveToFirst()){
            while(cursor.isAfterLast() == false) {
                addressList.add(fetchAddressFromCursor(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return addressList;
    }

    public List<PaymentInformation> getAllPaymentMethodsFromActiveUser(){
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseVariables.TABLE_PAYMENT_INFORMATION.TABLE_NAME + " WHERE "
                + DatabaseVariables.TABLE_PAYMENT_INFORMATION.COLUMN_USER_ID + "=" + getCurrentActiveUser().getUserId(), null);
        List<PaymentInformation> paymentList = new ArrayList<>();
        if(cursor.moveToFirst()){
            while(cursor.isAfterLast() == false) {
                paymentList.add(fetchPaymentInformationFromCursor(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return paymentList;
    }

    public UserAddress getAddressById(long addressId){
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseVariables.TABLE_ADDRESS.TABLE_NAME + " WHERE "
                + DatabaseVariables.TABLE_ADDRESS.COLUMN_ADDRESS_ID + "=" + addressId, null);

        UserAddress address = null;
        if (cursor.moveToFirst()) {
            address = fetchAddressFromCursor(cursor);
            cursor.moveToNext();
        }
        cursor.close();
        return address;

    }

    public List<Order> getAllOrdersFromActiveUser() {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseVariables.TABLE_ORDER.TABLE_NAME + " WHERE "
                + DatabaseVariables.TABLE_ORDER.COLUMN_USER_ID + "=" + getCurrentActiveUser().getUserId(), null);


        List<Order> orderList = new ArrayList<>();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {
                orderList.add(fetchOrderFromCursor(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return orderList;
    }

    public Order getOrderByOrderId(long orderId) {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseVariables.TABLE_ORDER.TABLE_NAME + " WHERE "
                + DatabaseVariables.TABLE_ORDER.COLUMN_USER_ID + "=" + getCurrentActiveUser().getUserId()
                + " AND " + DatabaseVariables.TABLE_ORDER.COLUMN_ORDER_ID + "=" + orderId, null);


        Order order = null;

        if (cursor.moveToFirst()) {
            order = (fetchOrderFromCursor(cursor));
            cursor.moveToNext();

        }
        cursor.close();
        return order;
    }


    public List<OrderItem> getOrderItemsFromOrderId(long orderId) {

        List<OrderItem> consoles = getAllOrderItemConsoleFromOrderId(orderId);
        List<OrderItem> games = getAllOrderItemGameFromOrderId(orderId);

        List<OrderItem> items = new ArrayList<>();
        items.addAll(consoles);
        items.addAll(games);

        return items;
    }

    private List<OrderItem> getAllOrderItemConsoleFromOrderId(long orderId) {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseVariables.TABLE_ORDER_ITEM_CONSOLE.TABLE_NAME + " WHERE "
                + DatabaseVariables.TABLE_ORDER_ITEM_CONSOLE.COLUMN_ORDER_ID + "=" + orderId, null);


        List<OrderItem> orderItemsConsole = new ArrayList<>();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {
                orderItemsConsole.add(fetchOrderItemConsoleFromCursor(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return orderItemsConsole;
    }

    private List<OrderItem> getAllOrderItemGameFromOrderId(long orderId) {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseVariables.TABLE_ORDER_ITEM_GAME.TABLE_NAME + " WHERE "
                + DatabaseVariables.TABLE_ORDER_ITEM_GAME.COLUMN_ORDER_ID + "=" + orderId, null);


        List<OrderItem> orderItemsGame = new ArrayList<>();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {
                orderItemsGame.add(fetchOrderItemGameFromCursor(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return orderItemsGame;
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

    public long createAddress(String street, String country, String province, String city, String postalCode, long userId){
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseVariables.TABLE_ADDRESS.COLUMN_STREET, street);
        values.put(DatabaseVariables.TABLE_ADDRESS.COLUMN_COUNTRY, country);
        values.put(DatabaseVariables.TABLE_ADDRESS.COLUMN_STATE, province);
        values.put(DatabaseVariables.TABLE_ADDRESS.COLUMN_CITY, city);
        values.put(DatabaseVariables.TABLE_ADDRESS.COLUMN_POSTAL_CODE, postalCode);
        values.put(DatabaseVariables.TABLE_ADDRESS.COLUMN_USER_ID, userId);

        long newRowId = -1;
        newRowId = db.insert(DatabaseVariables.TABLE_ADDRESS.TABLE_NAME, null, values);
        if (newRowId == -1)
            Log.d("DatabaseManager", "Error while adding address " + postalCode);
        else {
            Log.d("DatabaseManager", "added " + postalCode);
        }
        return newRowId;
    }

    public long createPaymentInformation(long cardNumber, String nameOnCard, int expirationMonth, int expirationYear, long userId){
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseVariables.TABLE_PAYMENT_INFORMATION.COLUMN_CARD_NUMBER, cardNumber);
        values.put(DatabaseVariables.TABLE_PAYMENT_INFORMATION.COLUMN_NAME_ON_CARD, nameOnCard);
        values.put(DatabaseVariables.TABLE_PAYMENT_INFORMATION.COLUMN_EXPIRATION_MONTH, expirationMonth);
        values.put(DatabaseVariables.TABLE_PAYMENT_INFORMATION.COLUMN_EXPIRATION_YEAR, expirationYear);
        values.put(DatabaseVariables.TABLE_PAYMENT_INFORMATION.COLUMN_USER_ID, userId);

        long newRowId = -1;
        newRowId = db.insert(DatabaseVariables.TABLE_PAYMENT_INFORMATION.TABLE_NAME, null, values);
        if (newRowId == -1)
            Log.d("DatabaseManager", "Error while adding credit card ending with " + cardNumber%10000);
        else {
            Log.d("DatabaseManager", "added credit card ending with " + cardNumber%10000);
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


    public long createTrailerGame(String trailerURL, long gameId) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(DatabaseVariables.TABLE_TRAILER.COLUMN_TRAILER_URL, trailerURL);
        values.put(DatabaseVariables.TABLE_TRAILER.COLUMN_GAME_ID, gameId);

        long newRowId = -1;
        newRowId = db.insert(
                DatabaseVariables.TABLE_TRAILER.TABLE_NAME,
                null,
                values);

        if (newRowId == -1)
            Log.d("DatabaseManager", "Error while adding console " + trailerURL);
        else {
            Log.d("DatabaseManager", "added " + trailerURL);
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

    public long createCartItemConsole(long consoleId) {

        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseVariables.TABLE_CART_ITEM_CONSOLE.COLUMN_CART_ID, getCurrentActiveUserCart().getCartId());
        values.put(DatabaseVariables.TABLE_CART_ITEM_CONSOLE.COLUMN_CONSOLE_ID, consoleId);
        values.put(DatabaseVariables.TABLE_CART_ITEM_CONSOLE.COLUMN_AMOUNT, 1);

        long newRowId = -1;
        newRowId = db.insert(
                DatabaseVariables.TABLE_CART_ITEM_CONSOLE.TABLE_NAME,
                null,
                values);

        if (newRowId == -1)
            Log.d("DatabaseManager", "Error while adding cart_console " + consoleId);
        else {
            Log.d("DatabaseManager", "added " + consoleId);
        }
        return newRowId;

    }

    public long createCartItemGame(long gameId) {

        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseVariables.TABLE_CART_ITEM_GAME.COLUMN_CART_ID, getCurrentActiveUserCart().getCartId());
        values.put(DatabaseVariables.TABLE_CART_ITEM_GAME.COLUMN_GAME_ID, gameId);
        values.put(DatabaseVariables.TABLE_CART_ITEM_GAME.COLUMN_AMOUNT, 1);

        long newRowId = -1;
        newRowId = db.insert(
                DatabaseVariables.TABLE_CART_ITEM_GAME.TABLE_NAME,
                null,
                values);

        if (newRowId == -1)
            Log.d("DatabaseManager", "Error while adding cart_console " + gameId);
        else {
            Log.d("DatabaseManager", "added " + gameId);
        }
        return newRowId;

    }

    public long addItemToCart(ItemVariables.TYPE itemType, long itemId) {

        if (isItemAlreadyInCart(itemId, itemType)) {
            incrementCartAmount(itemType, itemId);
            return -1;
        }

        if (itemType == ItemVariables.TYPE.CONSOLE)
            return createCartItemConsole(itemId);
        else
            return createCartItemGame(itemId);
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

    public long createOrderFromItemsInCart(String deliverTo, String dateOrdered, String dateArriving, OrderVariables.STATUS status, long cardNumber, String nameOnCard, int expirationMonth, int expirationYear, String street, String country, String state, String city, String postalCode, boolean extraShipping) {


        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseVariables.TABLE_ORDER.COLUMN_CARD_NUMBER, cardNumber);
        values.put(DatabaseVariables.TABLE_ORDER.COLUMN_CITY, city);
        values.put(DatabaseVariables.TABLE_ORDER.COLUMN_COUNTRY, country);
        values.put(DatabaseVariables.TABLE_ORDER.COLUMN_DATE_ARRIVING, dateArriving);
        values.put(DatabaseVariables.TABLE_ORDER.COLUMN_DATE_ORDERED, dateOrdered);
        values.put(DatabaseVariables.TABLE_ORDER.COLUMN_DELIVER_TO, deliverTo);
        values.put(DatabaseVariables.TABLE_ORDER.COLUMN_EXPIRATION_MONTH, expirationMonth);
        values.put(DatabaseVariables.TABLE_ORDER.COLUMN_EXPIRATION_YEAR, expirationYear);
        values.put(DatabaseVariables.TABLE_ORDER.COLUMN_NAME_ON_CARD, nameOnCard);
        values.put(DatabaseVariables.TABLE_ORDER.COLUMN_POSTAL_CODE, postalCode);
        values.put(DatabaseVariables.TABLE_ORDER.COLUMN_STATE, state);
        values.put(DatabaseVariables.TABLE_ORDER.COLUMN_STATUS, status.toString());
        values.put(DatabaseVariables.TABLE_ORDER.COLUMN_STREET, street);
        values.put(DatabaseVariables.TABLE_ORDER.COLUMN_USER_ID, getCurrentActiveUser().getUserId());
        values.put(DatabaseVariables.TABLE_ORDER.COLUMN_EXTRA_SHIPPING, extraShipping);

        long newRowId = -1;
        newRowId = db.insert(
                DatabaseVariables.TABLE_ORDER.TABLE_NAME,
                null,
                values);

        if (newRowId == -1)
            Log.d("DatabaseManager", "Error while adding TABLE_ORDER ");
        else {
            Log.d("DatabaseManager", "added TABLE_ORDER");
        }


        // add items from cart
        List<CartItem> cartItemist = getAllCartItems();
        List<Item> itemList = getCurrentActiveUserItemsInCart();

        for (int i = 0; i < itemList.size(); i++) {

            Item temp = itemList.get(i);

            if (temp.getItemType() == ItemVariables.TYPE.CONSOLE) {
                createOrderItemConsole(temp, cartItemist.get(i).getAmount(), newRowId);
            } else {
                createOrderItemGame(temp, cartItemist.get(i).getAmount(), newRowId);
            }
        }

        // empty wishlist that was in cart
        deleteWishListItemThatWereInCart();

        // empty cart
        deleteAllCartItems();


        return newRowId;
    }


    private long createOrderItemGame(Item item, int amount, long orderId) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseVariables.TABLE_ORDER_ITEM_GAME.COLUMN_ORDER_ID, orderId);
        values.put(DatabaseVariables.TABLE_ORDER_ITEM_GAME.COLUMN_GAME_ID, item.getItemId());
        values.put(DatabaseVariables.TABLE_ORDER_ITEM_GAME.COLUMN_AMOUNT, amount);

        long newRowId = -1;
        newRowId = db.insert(
                DatabaseVariables.TABLE_ORDER_ITEM_GAME.TABLE_NAME,
                null,
                values);

        if (newRowId == -1)
            Log.d("DatabaseManager", "Error while adding TABLE_ORDER_ITEM_CONSOLE " + item.getItemId());
        else {
            Log.d("DatabaseManager", "added TABLE_ORDER_ITEM_CONSOLE" + item.getItemId());
        }
        return newRowId;
    }

    private long createOrderItemConsole(Item item, int amount, long orderId) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseVariables.TABLE_ORDER_ITEM_CONSOLE.COLUMN_ORDER_ID, orderId);
        values.put(DatabaseVariables.TABLE_ORDER_ITEM_CONSOLE.COLUMN_CONSOLE_ID, item.getItemId());
        values.put(DatabaseVariables.TABLE_ORDER_ITEM_CONSOLE.COLUMN_AMOUNT, amount);

        long newRowId = -1;
        newRowId = db.insert(
                DatabaseVariables.TABLE_ORDER_ITEM_CONSOLE.TABLE_NAME,
                null,
                values);

        if (newRowId == -1)
            Log.d("DatabaseManager", "Error while adding TABLE_ORDER_ITEM_CONSOLE " + item.getItemId());
        else {
            Log.d("DatabaseManager", "added TABLE_ORDER_ITEM_CONSOLE" + item.getItemId());
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

    public void incrementCartAmount(ItemVariables.TYPE itemType, long itemId) {
        if (itemType == ItemVariables.TYPE.CONSOLE)
            updateCartAmountConsole(itemId, getCartItemConsoleById(itemId).getAmount() + 1);
        else
            updateCartAmountGame(itemId, getCartItemGameById(itemId).getAmount() + 1);
    }

    public void updateCartAmount(ItemVariables.TYPE itemType, long itemId, int amount) {
        if (itemType == ItemVariables.TYPE.CONSOLE)
            updateCartAmountConsole(itemId, amount);
        else
            updateCartAmountGame(itemId, amount);
    }


    public void updateCartAmountConsole(long itemId, int amount) {
        SQLiteDatabase db = database.getReadableDatabase();
        ContentValues values = new ContentValues();

        CartItem cartItemConsole = getCartItemConsoleById(itemId);

        values.put(DatabaseVariables.TABLE_CART_ITEM_CONSOLE.COLUMN_AMOUNT, amount);
        db.update(DatabaseVariables.TABLE_CART_ITEM_CONSOLE.TABLE_NAME, values,
                DatabaseVariables.TABLE_CART_ITEM_CONSOLE.COLUMN_CONSOLE_ID + "=" + itemId
                        + " AND " + DatabaseVariables.TABLE_CART_ITEM_CONSOLE.COLUMN_CART_ID + "=" + getCartByUserId(getCurrentActiveUser().getUserId()).getCartId(), null);

    }

    public void updateCartAmountGame(long itemId, int amount) {
        SQLiteDatabase db = database.getReadableDatabase();
        ContentValues values = new ContentValues();

        CartItem cartItemConsole = getCartItemGameById(itemId);

        values.put(DatabaseVariables.TABLE_CART_ITEM_GAME.COLUMN_AMOUNT, amount);
        db.update(DatabaseVariables.TABLE_CART_ITEM_GAME.TABLE_NAME, values,
                DatabaseVariables.TABLE_CART_ITEM_GAME.COLUMN_GAME_ID + "=" + itemId
                        + " AND " + DatabaseVariables.TABLE_CART_ITEM_GAME.COLUMN_CART_ID + "=" + getCartByUserId(getCurrentActiveUser().getUserId()).getCartId(), null);

    }


    public void updateAddress(long addressId, String street, String country, String province, String city, String postalCode, long userId){
        SQLiteDatabase db = database.getReadableDatabase();
        ContentValues values = new ContentValues();


        values.put(DatabaseVariables.TABLE_ADDRESS.COLUMN_STREET, street);
        values.put(DatabaseVariables.TABLE_ADDRESS.COLUMN_COUNTRY, country);
        values.put(DatabaseVariables.TABLE_ADDRESS.COLUMN_STATE, province);
        values.put(DatabaseVariables.TABLE_ADDRESS.COLUMN_CITY, city);
        values.put(DatabaseVariables.TABLE_ADDRESS.COLUMN_POSTAL_CODE, postalCode);
        values.put(DatabaseVariables.TABLE_ADDRESS.COLUMN_USER_ID, userId);
        db.update(DatabaseVariables.TABLE_ADDRESS.TABLE_NAME, values, DatabaseVariables.TABLE_ADDRESS.COLUMN_ADDRESS_ID + "=" + addressId, null);
    }

    public void updatePaymentInformation(long paymentId, long cardNumber, String nameOnCard, int expirationMonth, int expirationYear, long userId){
        SQLiteDatabase db = database.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseVariables.TABLE_PAYMENT_INFORMATION.COLUMN_CARD_NUMBER, cardNumber);
        values.put(DatabaseVariables.TABLE_PAYMENT_INFORMATION.COLUMN_NAME_ON_CARD, nameOnCard);
        values.put(DatabaseVariables.TABLE_PAYMENT_INFORMATION.COLUMN_EXPIRATION_MONTH, expirationMonth);
        values.put(DatabaseVariables.TABLE_PAYMENT_INFORMATION.COLUMN_EXPIRATION_YEAR, expirationYear);
        values.put(DatabaseVariables.TABLE_PAYMENT_INFORMATION.COLUMN_USER_ID, userId);

        db.update(DatabaseVariables.TABLE_PAYMENT_INFORMATION.TABLE_NAME, values, DatabaseVariables.TABLE_PAYMENT_INFORMATION.COLUMN_PAYMENT_ID + "=" + paymentId, null);
    }

    public void updateCurrentUser(String email, String password, String firstName, String lastName){
        long userId = getCurrentActiveUser().getUserId();
        updateUserById(userId, email, password, firstName, lastName);
    }

    public void updateUserById(long userId, String email, String password, String firstName, String lastName){
        SQLiteDatabase db = database.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseVariables.TABLE_USER.COLUMN_EMAIL, email);
        values.put(DatabaseVariables.TABLE_USER.COLUMN_PASSWORD, password);
        values.put(DatabaseVariables.TABLE_USER.COLUMN_FIRST_NAME, firstName);
        values.put(DatabaseVariables.TABLE_USER.COLUMN_LAST_NAME, lastName);
        db.update(DatabaseVariables.TABLE_USER.TABLE_NAME, values, DatabaseVariables.TABLE_USER.COLUMN_USER_ID + "=" + userId, null);
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


    public boolean deleteCartItem(long itemId, ItemVariables.TYPE itemType) {
        SQLiteDatabase db = database.getReadableDatabase();

        if (itemType == ItemVariables.TYPE.CONSOLE)
            return db.delete(DatabaseVariables.TABLE_CART_ITEM_CONSOLE.TABLE_NAME, DatabaseVariables.TABLE_CART_ITEM_CONSOLE.COLUMN_CONSOLE_ID
                    + "=" + itemId + " AND " + DatabaseVariables.TABLE_CART_ITEM_CONSOLE.COLUMN_CART_ID + "=" + getCartByUserId(getCurrentActiveUser().getUserId()).getCartId(), null) > 0;
        else
            return db.delete(DatabaseVariables.TABLE_CART_ITEM_GAME.TABLE_NAME, DatabaseVariables.TABLE_CART_ITEM_GAME.COLUMN_GAME_ID
                    + "=" + itemId + " AND " + DatabaseVariables.TABLE_CART_ITEM_GAME.COLUMN_CART_ID + "=" + getCartByUserId(getCurrentActiveUser().getUserId()).getCartId(), null) > 0;


    }

    public boolean deleteAllCartItems() {
        List<CartItem> cartItems = getAllCartItems();

        boolean passed = true;

        for (int i = 0; i < cartItems.size(); i++) {
            boolean ok = deleteCartItem(cartItems.get(i).getItemId(), cartItems.get(i).getItemType());
            if (passed && !ok)
                passed = false;
        }

        return passed;
    }

    private void deleteWishListItemThatWereInCart() {
        List<CartItem> cartItems = getAllCartItems();
        List<Item> wishListItemList = getAllWishListItemsFromActiveUser();

        for (int counterCart = 0; counterCart < cartItems.size(); counterCart++) {

            CartItem cartItemTemp = cartItems.get(counterCart);
            for (int counterWishlist = 0; counterWishlist < wishListItemList.size(); counterWishlist++) {
                Item wishListItemTemp = wishListItemList.get(counterWishlist);

                if (cartItemTemp.getItemType() == wishListItemTemp.getItemType() &&
                        cartItemTemp.getItemId() == wishListItemTemp.getItemId()) {
                    deleteWishlist(wishListItemTemp.getItemId(), wishListItemTemp.getItemType());
                }
            }

        }

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

    private ItemTrailer fetchTrailerGameFromCursor(Cursor cursor) {

        long trailerID = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_TRAILER.COLUMN_TRAILER_ID));
        long gameId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_TRAILER.COLUMN_GAME_ID));
        String imageURL = cursor.getString(cursor
                .getColumnIndex(DatabaseVariables.TABLE_TRAILER.COLUMN_TRAILER_URL));

        return new ItemTrailer(trailerID, imageURL, gameId);
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
        String nameOnCard = cursor.getString(cursor
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
        String deliverTo = cursor.getString(cursor
                .getColumnIndex(DatabaseVariables.TABLE_ORDER.COLUMN_DELIVER_TO));
        boolean extraShipping = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_ORDER.COLUMN_EXTRA_SHIPPING)) > 0;


        OrderVariables.STATUS convertedStatus = Helper.convertStringToStatus(status);

        return new Order(orderId, userId, dateOrdered,
                dateArriving, convertedStatus, deliverTo,cardNumber, nameOnCard,
                expirationMonth, expirationYear, street, country, state,
                city, postalCode,extraShipping);
    }


    private OrderItem fetchOrderItemConsoleFromCursor(Cursor cursor) {

        long consoleId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_ORDER_ITEM_CONSOLE.COLUMN_CONSOLE_ID));
        long orderId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_ORDER_ITEM_CONSOLE.COLUMN_ORDER_ID));
        int amount = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_ORDER_ITEM_CONSOLE.COLUMN_AMOUNT));


        return new OrderItem(consoleId, orderId, amount, ItemVariables.TYPE.CONSOLE);
    }


    private OrderItem fetchOrderItemGameFromCursor(Cursor cursor) {

        long gameId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_ORDER_ITEM_GAME.COLUMN_GAME_ID));
        long orderId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_ORDER_ITEM_GAME.COLUMN_ORDER_ID));
        int amount = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_ORDER_ITEM_GAME.COLUMN_AMOUNT));


        return new OrderItem(gameId, orderId, amount, ItemVariables.TYPE.GAME);
    }

    private PaymentInformation fetchPaymentInformationFromCursor(Cursor cursor) {

        long paymentId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_PAYMENT_INFORMATION.COLUMN_PAYMENT_ID));
        long userId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_PAYMENT_INFORMATION.COLUMN_USER_ID));
        long cardNumber = cursor.getLong(cursor
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

    public int getNbItemsInCart() {
        List<CartItem> cartItems = getAllCartItems();

        int nb = 0;

        for (int i = 0; i < cartItems.size(); i++) {
            CartItem cartItemTemp = cartItems.get(i);
            nb += cartItemTemp.getAmount();
        }

        return nb;
    }

    public List<Item> getXNumberItem(int nbItems, ItemVariables.TYPE itemType) {

        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = null;
        if (itemType == ItemVariables.TYPE.CONSOLE) {
            cursor = db.rawQuery("SELECT * FROM " + DatabaseVariables.TABLE_CONSOLE.TABLE_NAME + " LIMIT " + nbItems, null);
            List<Item> consoles = new ArrayList<Item>();

            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() == false) {
                    consoles.add(fetchConsoleFromCursor(cursor));
                    cursor.moveToNext();
                }
            }

            cursor.close();
            return consoles;
        } else {
            cursor = db.rawQuery("SELECT * FROM " + DatabaseVariables.TABLE_VIDEO_GAME.TABLE_NAME + " LIMIT " + nbItems, null);
            List<Item> games = new ArrayList<Item>();

            if (cursor.moveToFirst()) {
                while (cursor.isAfterLast() == false) {
                    games.add(fetchVideoGameFromCursor(cursor));
                    cursor.moveToNext();
                }
            }

            cursor.close();
            return games;
        }
    }

    // not efficient but works
    public List<Item> getXNumberItemRandom(int nbItems) {

        List<Item> itemList = new ArrayList<>();
        itemList.addAll(getAllConsoles());
        itemList.addAll(getAllGames());

        List<Item> randomList = new ArrayList<>();
        for (int i = 0; i < nbItems; i++) {

            int range = (itemList.size());

            int position = (int) (Math.random() * range);

            randomList.add(itemList.get(position));
            itemList.remove(position);

        }

        return randomList;
    }

}
