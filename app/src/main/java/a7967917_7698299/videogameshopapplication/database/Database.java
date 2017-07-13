package a7967917_7698299.videogameshopapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alex on 2017-06-26.
 */

public class Database extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "shop.db";


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseVariables.SQL_CREATE_TABLE_ADDRESS);
        db.execSQL(DatabaseVariables.SQL_CREATE_TABLE_CART);
        db.execSQL(DatabaseVariables.SQL_CREATE_TABLE_CART_ITEM_CONSOLE);
        db.execSQL(DatabaseVariables.SQL_CREATE_TABLE_CART_ITEM_GAME);
        db.execSQL(DatabaseVariables.SQL_CREATE_TABLE_CONSOLE);
        db.execSQL(DatabaseVariables.SQL_CREATE_TABLE_CONSOLE_VIDEO_GAME);
        db.execSQL(DatabaseVariables.SQL_CREATE_TABLE_ORDER);
        db.execSQL(DatabaseVariables.SQL_CREATE_TABLE_ORDER_ITEM_CONSOLE);
        db.execSQL(DatabaseVariables.SQL_CREATE_TABLE_ORDER_ITEM_GAME);
        db.execSQL(DatabaseVariables.SQL_CREATE_TABLE_PAYMENT_INFORMATION);
        db.execSQL(DatabaseVariables.SQL_CREATE_TABLE_USER);
        db.execSQL(DatabaseVariables.SQL_CREATE_TABLE_VIDEO_GAME);
        db.execSQL(DatabaseVariables.SQL_CREATE_TABLE_WISHLIST);
        db.execSQL(DatabaseVariables.SQL_CREATE_TABLE_WISHLIST_ITEM_CONSOLE);
        db.execSQL(DatabaseVariables.SQL_CREATE_TABLE_WISHLIST_ITEM_GAME);
        db.execSQL(DatabaseVariables.SQL_CREATE_TABLE_IMAGE_CONSOLE);
        db.execSQL(DatabaseVariables.SQL_CREATE_TABLE_IMAGE_GAME);
        db.execSQL(DatabaseVariables.SQL_CREATE_TABLE_APPLICATION);
        db.execSQL(DatabaseVariables.SQL_CREATE_TABLE_TRAILER_GAME);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatabaseVariables.SQL_DELETE_TABLE_ADDRESS);
        db.execSQL(DatabaseVariables.SQL_DELETE_TABLE_CART);
        db.execSQL(DatabaseVariables.SQL_DELETE_TABLE_CART_ITEM_CONSOLE);
        db.execSQL(DatabaseVariables.SQL_DELETE_TABLE_CART_ITEM_GAME);
        db.execSQL(DatabaseVariables.SQL_DELETE_TABLE_CONSOLE);
        db.execSQL(DatabaseVariables.SQL_DELETE_TABLE_CONSOLE_VIDEO_GAME);
        db.execSQL(DatabaseVariables.SQL_DELETE_TABLE_ORDER);
        db.execSQL(DatabaseVariables.SQL_DELETE_TABLE_ORDER_ITEM_CONSOLE);
        db.execSQL(DatabaseVariables.SQL_DELETE_TABLE_ORDER_ITEM_GAME);
        db.execSQL(DatabaseVariables.SQL_DELETE_TABLE_PAYMENT_INFORMATION);
        db.execSQL(DatabaseVariables.SQL_DELETE_TABLE_USER);
        db.execSQL(DatabaseVariables.SQL_DELETE_TABLE_VIDEO_GAME);
        db.execSQL(DatabaseVariables.SQL_DELETE_TABLE_WISHLIST);
        db.execSQL(DatabaseVariables.SQL_DELETE_TABLE_WISHLIST_ITEM_CONSOLE);
        db.execSQL(DatabaseVariables.SQL_DELETE_TABLE_WISHLIST_ITEM_GAME);
        db.execSQL(DatabaseVariables.SQL_DELETE_TABLE_IMAGE_CONSOLE);
        db.execSQL(DatabaseVariables.SQL_DELETE_TABLE_IMAGE_GAME);
        db.execSQL(DatabaseVariables.SQL_DELETE_TABLE_APPLICATION);
        db.execSQL(DatabaseVariables.SQL_DELETE_TABLE_TRAILER);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
