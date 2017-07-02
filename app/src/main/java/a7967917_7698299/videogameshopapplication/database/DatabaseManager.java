package a7967917_7698299.videogameshopapplication.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;

import a7967917_7698299.videogameshopapplication.model.Cart;
import a7967917_7698299.videogameshopapplication.model.CartItemConsole;
import a7967917_7698299.videogameshopapplication.model.CartItemGame;
import a7967917_7698299.videogameshopapplication.model.Console;
import a7967917_7698299.videogameshopapplication.model.UserAddress;
import a7967917_7698299.videogameshopapplication.variables.ItemVariables;

/**
 * Created by alex on 2017-06-28.
 */


public class DatabaseManager {

    private static DatabaseManager databaseManager = null;
    private Database database;


    // call this method at launch
    public static void initDatabase(Context context){
        if (databaseManager == null) {
            databaseManager = new DatabaseManager(context);
        }
    }

    // access database with DatabaseManager.getInstance();
    public static DatabaseManager getInstance() {
        if (databaseManager == null) {
            return null;
        }
        return databaseManager;
    }


    private DatabaseManager(Context context) {
        database = new Database(context);
    }


    ////////////// GET METHODS //////////////

    ////////////// CREATE METHODS //////////////

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

        return new UserAddress(addressId,  userId,  street,  country,  state,  city,  postalCode);
    }


    private Cart fetchCartFromCursor(Cursor cursor) {
        int cartId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_CART.COLUMN_CART_ID));
        int userId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_CART.COLUMN_USER_ID));

        return new Cart(cartId, userId);
    }

    private CartItemConsole fetchCartItemConsoleFromCursor(Cursor cursor) {
        int cartId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_CART_ITEM_CONSOLE.COLUMN_CART_ID));
        int consoleId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_CART_ITEM_CONSOLE.COLUMN_CONSOLE_ID));
        int amount = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_CART_ITEM_CONSOLE.COLUMN_AMOUNT));

        return new CartItemConsole(consoleId, cartId, amount);
    }

    private CartItemGame fetchCartItemGameFromCursor(Cursor cursor) {
        int cartId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_CART_ITEM_GAME.COLUMN_CART_ID));
        int gameId = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_CART_ITEM_GAME.COLUMN_GAME_ID));
        int amount = cursor.getInt(cursor
                .getColumnIndex(DatabaseVariables.TABLE_CART_ITEM_GAME.COLUMN_AMOUNT));

        return new CartItemGame(gameId, cartId, amount);
    }

    public boolean isDatabaseEmpty() {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor mCursor = db.rawQuery("SELECT *", null);

        if (mCursor.moveToFirst())
        {
            return false;
        } else
        {
            return true;
        }
    }
}
