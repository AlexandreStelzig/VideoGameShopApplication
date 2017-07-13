package a7967917_7698299.videogameshopapplication.database;

import android.provider.BaseColumns;

/**
 * Created by alex on 2017-06-26.
 */

public final class DatabaseVariables {


    public static final String SQL_CREATE_TABLE_ADDRESS = "CREATE TABLE "
            + TABLE_ADDRESS.TABLE_NAME + " ("
            + TABLE_ADDRESS.COLUMN_ADDRESS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TABLE_ADDRESS.COLUMN_STREET + " TEXT NOT NULL,"
            + TABLE_ADDRESS.COLUMN_COUNTRY + " TEXT NOT NULL,"
            + TABLE_ADDRESS.COLUMN_STATE + " TEXT NOT NULL,"
            + TABLE_ADDRESS.COLUMN_CITY + " TEXT NOT NULL,"
            + TABLE_ADDRESS.COLUMN_POSTAL_CODE + " TEXT NOT NULL,"
            + TABLE_ADDRESS.COLUMN_USER_ID + " INTEGER,"
            + " FOREIGN KEY " + "(" + TABLE_ADDRESS.COLUMN_USER_ID + ")"
            + " REFERENCES " + TABLE_USER.TABLE_NAME + "(" + TABLE_USER.COLUMN_USER_ID + ")" + " );";

    public static final String SQL_CREATE_TABLE_CART = "CREATE TABLE "
            + TABLE_CART.TABLE_NAME + " ("
            + TABLE_CART.COLUMN_CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TABLE_CART.COLUMN_USER_ID + " INTEGER,"
            + " FOREIGN KEY " + "(" + TABLE_CART.COLUMN_USER_ID + ")"
            + " REFERENCES " + TABLE_USER.TABLE_NAME + "(" + TABLE_USER.COLUMN_USER_ID + ")" + " );";

    public static final String SQL_CREATE_TABLE_CART_ITEM_GAME = "CREATE TABLE "
            + TABLE_CART_ITEM_GAME.TABLE_NAME + " ("
            + TABLE_CART_ITEM_GAME.COLUMN_CART_ID + " INTEGER,"
            + TABLE_CART_ITEM_GAME.COLUMN_GAME_ID + " INTEGER,"
            + TABLE_CART_ITEM_GAME.COLUMN_AMOUNT + " INTEGER,"
            + " FOREIGN KEY " + "(" + TABLE_CART_ITEM_GAME.COLUMN_CART_ID + ")"
            + " REFERENCES " + TABLE_CART.TABLE_NAME + "(" + TABLE_CART.COLUMN_CART_ID + "),"
            + " FOREIGN KEY " + "(" + TABLE_CART_ITEM_GAME.COLUMN_GAME_ID + ")"
            + " REFERENCES " + TABLE_VIDEO_GAME.TABLE_NAME + "(" + TABLE_VIDEO_GAME.COLUMN_GAME_ID + ")"
            + " PRIMARY KEY (" + TABLE_CART_ITEM_GAME.COLUMN_CART_ID + ", " + TABLE_CART_ITEM_GAME.COLUMN_GAME_ID + ") " + ");";

    public static final String SQL_CREATE_TABLE_CART_ITEM_CONSOLE = "CREATE TABLE "
            + TABLE_CART_ITEM_CONSOLE.TABLE_NAME + " ("
            + TABLE_CART_ITEM_CONSOLE.COLUMN_CART_ID + " INTEGER,"
            + TABLE_CART_ITEM_CONSOLE.COLUMN_CONSOLE_ID + " INTEGER,"
            + TABLE_CART_ITEM_CONSOLE.COLUMN_AMOUNT + " INTEGER,"
            + " FOREIGN KEY " + "(" + TABLE_CART_ITEM_CONSOLE.COLUMN_CART_ID + ")"
            + " REFERENCES " + TABLE_CART.TABLE_NAME + "(" + TABLE_CART.COLUMN_CART_ID + "),"
            + " FOREIGN KEY " + "(" + TABLE_CART_ITEM_CONSOLE.COLUMN_CONSOLE_ID + ")"
            + " REFERENCES " + TABLE_CONSOLE.TABLE_NAME + "(" + TABLE_CONSOLE.COLUMN_CONSOLE_ID + ")"
            + " PRIMARY KEY (" + TABLE_CART_ITEM_CONSOLE.COLUMN_CART_ID + ", " + TABLE_CART_ITEM_CONSOLE.COLUMN_CONSOLE_ID + ") " + ");";

    public static final String SQL_CREATE_TABLE_CONSOLE = "CREATE TABLE "
            + TABLE_CONSOLE.TABLE_NAME + " ("
            + TABLE_CONSOLE.COLUMN_CONSOLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TABLE_CONSOLE.COLUMN_DATE_PUBLISHED + " DATE,"
            + TABLE_CONSOLE.COLUMN_DESCRIPTION + " TEXT,"
            + TABLE_CONSOLE.COLUMN_NAME + " TEXT NOT NULL,"
            + TABLE_CONSOLE.COLUMN_PRICE + " DOUBLE,"
            + TABLE_CONSOLE.COLUMN_REVIEW + " INTEGER,"
            + TABLE_CONSOLE.COLUMN_CONSOLE_TYPE + " TEXT,"
            + TABLE_CONSOLE.COLUMN_PUBLISHER + " TEXT" + " );";

    public static final String SQL_CREATE_TABLE_VIDEO_GAME = "CREATE TABLE "
            + TABLE_VIDEO_GAME.TABLE_NAME + " ("
            + TABLE_VIDEO_GAME.COLUMN_GAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TABLE_VIDEO_GAME.COLUMN_CATEGORY + " TEXT,"
            + TABLE_VIDEO_GAME.COLUMN_DATE_PUBLISHED + " DATE,"
            + TABLE_VIDEO_GAME.COLUMN_DESCRIPTION + " TEXT,"
            + TABLE_VIDEO_GAME.COLUMN_ESRB + " TEXT,"
            + TABLE_VIDEO_GAME.COLUMN_LENGTH + " DOUBLE,"
            + TABLE_VIDEO_GAME.COLUMN_NAME + " TEXT,"
            + TABLE_VIDEO_GAME.COLUMN_NUMBER_PLAYER + " INTEGER,"
            + TABLE_VIDEO_GAME.COLUMN_PRICE + " DOUBLE,"
            + TABLE_VIDEO_GAME.COLUMN_PUBLISHER + " TEXT,"
            + TABLE_VIDEO_GAME.COLUMN_REGION + " TEXT,"
            + TABLE_VIDEO_GAME.COLUMN_REVIEW + " INTEGER" + " );";

    public static final String SQL_CREATE_TABLE_PAYMENT_INFORMATION = "CREATE TABLE "
            + TABLE_PAYMENT_INFORMATION.TABLE_NAME + " ("
            + TABLE_PAYMENT_INFORMATION.COLUMN_PAYMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TABLE_PAYMENT_INFORMATION.COLUMN_USER_ID + " INTEGER,"
            + TABLE_PAYMENT_INFORMATION.COLUMN_CARD_NUMBER + " INTEGER,"
            + TABLE_PAYMENT_INFORMATION.COLUMN_EXPIRATION_MONTH + " INTEGER,"
            + TABLE_PAYMENT_INFORMATION.COLUMN_EXPIRATION_YEAR + " INTEGER,"
            + TABLE_PAYMENT_INFORMATION.COLUMN_NAME_ON_CARD + " TEXT,"
            + " FOREIGN KEY " + "(" + TABLE_PAYMENT_INFORMATION.COLUMN_USER_ID + ")"
            + " REFERENCES " + TABLE_USER.TABLE_NAME + "(" + TABLE_USER.COLUMN_USER_ID + ")" + " );";


    public static final String SQL_CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER.TABLE_NAME + " ("
            + TABLE_USER.COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TABLE_USER.COLUMN_EMAIL + " TEXT,"
            + TABLE_USER.COLUMN_FIRST_NAME + " TEXT,"
            + TABLE_USER.COLUMN_LAST_NAME + " TEXT,"
            + TABLE_USER.COLUMN_PASSWORD + " TEXT" + " );";

    public static final String SQL_CREATE_TABLE_CONSOLE_VIDEO_GAME = "CREATE TABLE "
            + TABLE_CONSOLE_VIDEO_GAME.TABLE_NAME + " ("
            + TABLE_CONSOLE_VIDEO_GAME.COLUMN_CONSOLE_NAME + " TEXT,"
            + TABLE_CONSOLE_VIDEO_GAME.COLUMN_GAME_ID + " INTEGER,"
            + " FOREIGN KEY " + "(" + TABLE_CONSOLE_VIDEO_GAME.COLUMN_GAME_ID + ")"
            + " REFERENCES " + TABLE_VIDEO_GAME.TABLE_NAME + "(" + TABLE_VIDEO_GAME.COLUMN_GAME_ID + ")"
            + " PRIMARY KEY (" + TABLE_CONSOLE_VIDEO_GAME.COLUMN_CONSOLE_NAME + ", " + TABLE_CONSOLE_VIDEO_GAME.COLUMN_GAME_ID + ") " + ");";

    public static final String SQL_CREATE_TABLE_ORDER = "CREATE TABLE "
            + TABLE_ORDER.TABLE_NAME + " ("
            + TABLE_ORDER.COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TABLE_ORDER.COLUMN_USER_ID + " INTEGER,"
            + TABLE_ORDER.COLUMN_CARD_NUMBER + " INTEGER,"
            + TABLE_ORDER.COLUMN_CITY + " TEXT,"
            + TABLE_ORDER.COLUMN_COUNTRY + " TEXT,"
            + TABLE_ORDER.COLUMN_DATE_ARRIVING + " DATE,"
            + TABLE_ORDER.COLUMN_DATE_ORDERED + " DATE,"
            + TABLE_ORDER.COLUMN_DELIVER_TO + " TEXT,"
            + TABLE_ORDER.COLUMN_EXPIRATION_MONTH + " INTEGER,"
            + TABLE_ORDER.COLUMN_EXPIRATION_YEAR + " INTEGER,"
            + TABLE_ORDER.COLUMN_NAME_ON_CARD + " TEXT,"
            + TABLE_ORDER.COLUMN_POSTAL_CODE + " TEXT,"
            + TABLE_ORDER.COLUMN_STATE + " TEXT,"
            + TABLE_ORDER.COLUMN_STATUS + " TEXT,"
            + TABLE_ORDER.COLUMN_STREET + " TEXT,"
            + " FOREIGN KEY " + "(" + TABLE_ORDER.COLUMN_USER_ID + ")"
            + " REFERENCES " + TABLE_USER.TABLE_NAME + "(" + TABLE_USER.COLUMN_USER_ID + ")" + " );";


    public static final String SQL_CREATE_TABLE_ORDER_ITEM_CONSOLE = "CREATE TABLE "
            + TABLE_ORDER_ITEM_CONSOLE.TABLE_NAME + " ("
            + TABLE_ORDER_ITEM_CONSOLE.COLUMN_CONSOLE_ID + " INTEGER,"
            + TABLE_ORDER_ITEM_CONSOLE.COLUMN_ORDER_ID + " INTEGER,"
            + TABLE_ORDER_ITEM_CONSOLE.COLUMN_AMOUNT + " INTEGER,"
            + " FOREIGN KEY " + "(" + TABLE_ORDER_ITEM_CONSOLE.COLUMN_ORDER_ID + ")"
            + " REFERENCES " + TABLE_ORDER.TABLE_NAME + "(" + TABLE_ORDER.COLUMN_ORDER_ID + "),"
            + " FOREIGN KEY " + "(" + TABLE_ORDER_ITEM_CONSOLE.COLUMN_CONSOLE_ID + ")"
            + " REFERENCES " + TABLE_CONSOLE.TABLE_NAME + "(" + TABLE_CONSOLE.COLUMN_CONSOLE_ID + ")"
            + " PRIMARY KEY (" + TABLE_ORDER_ITEM_CONSOLE.COLUMN_CONSOLE_ID + ", " + TABLE_ORDER_ITEM_CONSOLE.COLUMN_ORDER_ID + ") " + ");";

    public static final String SQL_CREATE_TABLE_ORDER_ITEM_GAME = "CREATE TABLE "
            + TABLE_ORDER_ITEM_GAME.TABLE_NAME + " ("
            + TABLE_ORDER_ITEM_GAME.COLUMN_GAME_ID + " INTEGER,"
            + TABLE_ORDER_ITEM_GAME.COLUMN_ORDER_ID + " INTEGER,"
            + TABLE_ORDER_ITEM_GAME.COLUMN_AMOUNT + " INTEGER,"
            + " FOREIGN KEY " + "(" + TABLE_ORDER_ITEM_GAME.COLUMN_ORDER_ID + ")"
            + " REFERENCES " + TABLE_ORDER.TABLE_NAME + "(" + TABLE_ORDER.COLUMN_ORDER_ID + "),"
            + " FOREIGN KEY " + "(" + TABLE_ORDER_ITEM_GAME.COLUMN_GAME_ID + ")"
            + " REFERENCES " + TABLE_VIDEO_GAME.TABLE_NAME + "(" + TABLE_VIDEO_GAME.COLUMN_GAME_ID + ")"
            + " PRIMARY KEY (" + TABLE_ORDER_ITEM_GAME.COLUMN_GAME_ID + ", " + TABLE_ORDER_ITEM_GAME.COLUMN_ORDER_ID + ") " + ");";

    public static final String SQL_CREATE_TABLE_WISHLIST = "CREATE TABLE "
            + TABLE_WISHLIST.TABLE_NAME + " ("
            + TABLE_WISHLIST.COLUMN_WISHLIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TABLE_WISHLIST.COLUMN_USER_ID + " INTEGER,"
            + " FOREIGN KEY " + "(" + TABLE_WISHLIST.COLUMN_USER_ID + ")"
            + " REFERENCES " + TABLE_USER.TABLE_NAME + "(" + TABLE_USER.COLUMN_USER_ID + ")" + " );";

    public static final String SQL_CREATE_TABLE_WISHLIST_ITEM_CONSOLE = "CREATE TABLE "
            + TABLE_WISHLIST_ITEM_CONSOLE.TABLE_NAME + " ("
            + TABLE_WISHLIST_ITEM_CONSOLE.COLUMN_CONSOLE_ID + " INTEGER,"
            + TABLE_WISHLIST_ITEM_CONSOLE.COLUMN_WISHLIST_ID + " INTEGER,"
            + " FOREIGN KEY " + "(" + TABLE_WISHLIST_ITEM_CONSOLE.COLUMN_WISHLIST_ID + ")"
            + " REFERENCES " + TABLE_WISHLIST.TABLE_NAME + "(" + TABLE_WISHLIST.COLUMN_WISHLIST_ID + "),"
            + " FOREIGN KEY " + "(" + TABLE_WISHLIST_ITEM_CONSOLE.COLUMN_CONSOLE_ID + ")"
            + " REFERENCES " + TABLE_CONSOLE.TABLE_NAME + "(" + TABLE_CONSOLE.COLUMN_CONSOLE_ID + ")"
            + " PRIMARY KEY (" + TABLE_WISHLIST_ITEM_CONSOLE.COLUMN_CONSOLE_ID + ", " + TABLE_WISHLIST_ITEM_CONSOLE.COLUMN_WISHLIST_ID + ") " + ");";

    public static final String SQL_CREATE_TABLE_WISHLIST_ITEM_GAME = "CREATE TABLE "
            + TABLE_WISHLIST_ITEM_GAME.TABLE_NAME + " ("
            + TABLE_WISHLIST_ITEM_GAME.COLUMN_GAME_ID + " INTEGER,"
            + TABLE_WISHLIST_ITEM_GAME.COLUMN_WISHLIST_ID + " INTEGER,"
            + " FOREIGN KEY " + "(" + TABLE_WISHLIST_ITEM_GAME.COLUMN_WISHLIST_ID + ")"
            + " REFERENCES " + TABLE_WISHLIST.TABLE_NAME + "(" + TABLE_WISHLIST.COLUMN_WISHLIST_ID + "),"
            + " FOREIGN KEY " + "(" + TABLE_WISHLIST_ITEM_GAME.COLUMN_GAME_ID + ")"
            + " REFERENCES " + TABLE_VIDEO_GAME.TABLE_NAME + "(" + TABLE_VIDEO_GAME.COLUMN_GAME_ID + ")"
            + " PRIMARY KEY (" + TABLE_WISHLIST_ITEM_GAME.COLUMN_GAME_ID + ", " + TABLE_WISHLIST_ITEM_GAME.COLUMN_WISHLIST_ID + ") " + ");";

    public static final String SQL_CREATE_TABLE_IMAGE_CONSOLE = "CREATE TABLE "
            + TABLE_IMAGE_CONSOLE.TABLE_NAME + " ("
            + TABLE_IMAGE_CONSOLE.COLUMN_IMAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TABLE_IMAGE_CONSOLE.COLUMN_IMAGE_URL + " TEXT,"
            + TABLE_IMAGE_CONSOLE.COLUMN_CONSOLE_ID + " INTEGER,"
            + " FOREIGN KEY " + "(" + TABLE_IMAGE_CONSOLE.COLUMN_CONSOLE_ID + ")"
            + " REFERENCES " + TABLE_CONSOLE.TABLE_NAME + "(" + TABLE_CONSOLE.COLUMN_CONSOLE_ID + ")" + " );";

    public static final String SQL_CREATE_TABLE_IMAGE_GAME = "CREATE TABLE "
            + TABLE_IMAGE_GAME.TABLE_NAME + " ("
            + TABLE_IMAGE_GAME.COLUMN_IMAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TABLE_IMAGE_GAME.COLUMN_IMAGE_URL + " TEXT,"
            + TABLE_IMAGE_GAME.COLUMN_GAME_ID + " INTEGER,"
            + " FOREIGN KEY " + "(" + TABLE_IMAGE_GAME.COLUMN_GAME_ID + ")"
            + " REFERENCES " + TABLE_VIDEO_GAME.TABLE_NAME + "(" + TABLE_VIDEO_GAME.COLUMN_GAME_ID + ")" + " );";

    public static final String SQL_CREATE_TABLE_APPLICATION = "CREATE TABLE "
            + TABLE_APPLICATION.TABLE_NAME + " ("
            + TABLE_APPLICATION.COLUMN_APPLICATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TABLE_APPLICATION.COLUMN_CURRENT_USER + " INTEGER,"
            + " FOREIGN KEY " + "(" + TABLE_APPLICATION.COLUMN_CURRENT_USER + ")"
            + " REFERENCES " + TABLE_USER.TABLE_NAME + "(" + TABLE_USER.COLUMN_USER_ID + ")" + " );";

    public static final String SQL_CREATE_TABLE_TRAILER_GAME = "CREATE TABLE "
            + TABLE_TRAILER.TABLE_NAME + " ("
            + TABLE_TRAILER.COLUMN_TRAILER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TABLE_TRAILER.COLUMN_TRAILER_URL + " TEXT,"
            + TABLE_TRAILER.COLUMN_GAME_ID + " INTEGER,"
            + " FOREIGN KEY " + "(" + TABLE_TRAILER.COLUMN_GAME_ID + ")"
            + " REFERENCES " + TABLE_VIDEO_GAME.TABLE_NAME + "(" + TABLE_VIDEO_GAME.COLUMN_GAME_ID + ")" + " );";


    public static final String SQL_DELETE_TABLE_ADDRESS = "DROP TABLE IF EXISTS " + TABLE_ADDRESS.TABLE_NAME;
    public static final String SQL_DELETE_TABLE_CART = "DROP TABLE IF EXISTS " + TABLE_CART.TABLE_NAME;
    public static final String SQL_DELETE_TABLE_CART_ITEM_GAME = "DROP TABLE IF EXISTS " + TABLE_CART_ITEM_GAME.TABLE_NAME;
    public static final String SQL_DELETE_TABLE_CART_ITEM_CONSOLE = "DROP TABLE IF EXISTS " + TABLE_CART_ITEM_CONSOLE.TABLE_NAME;
    public static final String SQL_DELETE_TABLE_CONSOLE = "DROP TABLE IF EXISTS " + TABLE_CONSOLE.TABLE_NAME;
    public static final String SQL_DELETE_TABLE_CONSOLE_VIDEO_GAME = "DROP TABLE IF EXISTS " + TABLE_CONSOLE_VIDEO_GAME.TABLE_NAME;
    public static final String SQL_DELETE_TABLE_ORDER = "DROP TABLE IF EXISTS " + TABLE_ORDER.TABLE_NAME;
    public static final String SQL_DELETE_TABLE_ORDER_ITEM_GAME = "DROP TABLE IF EXISTS " + TABLE_ORDER_ITEM_GAME.TABLE_NAME;
    public static final String SQL_DELETE_TABLE_ORDER_ITEM_CONSOLE = "DROP TABLE IF EXISTS " + TABLE_ORDER_ITEM_CONSOLE.TABLE_NAME;
    public static final String SQL_DELETE_TABLE_PAYMENT_INFORMATION = "DROP TABLE IF EXISTS " + TABLE_PAYMENT_INFORMATION.TABLE_NAME;
    public static final String SQL_DELETE_TABLE_USER = "DROP TABLE IF EXISTS " + TABLE_USER.TABLE_NAME;
    public static final String SQL_DELETE_TABLE_VIDEO_GAME = "DROP TABLE IF EXISTS " + TABLE_VIDEO_GAME.TABLE_NAME;
    public static final String SQL_DELETE_TABLE_WISHLIST = "DROP TABLE IF EXISTS " + TABLE_WISHLIST.TABLE_NAME;
    public static final String SQL_DELETE_TABLE_WISHLIST_ITEM_GAME = "DROP TABLE IF EXISTS " + TABLE_WISHLIST_ITEM_GAME.TABLE_NAME;
    public static final String SQL_DELETE_TABLE_WISHLIST_ITEM_CONSOLE = "DROP TABLE IF EXISTS " + TABLE_WISHLIST_ITEM_CONSOLE.TABLE_NAME;
    public static final String SQL_DELETE_TABLE_IMAGE_CONSOLE = "DROP TABLE IF EXISTS " + TABLE_IMAGE_CONSOLE.TABLE_NAME;
    public static final String SQL_DELETE_TABLE_IMAGE_GAME = "DROP TABLE IF EXISTS " + TABLE_IMAGE_GAME.TABLE_NAME;
    public static final String SQL_DELETE_TABLE_APPLICATION = "DROP TABLE IF EXISTS " + TABLE_APPLICATION.TABLE_NAME;
    public static final String SQL_DELETE_TABLE_TRAILER = "DROP TABLE IF EXISTS " + TABLE_TRAILER.TABLE_NAME;


    public static abstract class TABLE_ADDRESS implements BaseColumns {
        public static final String TABLE_NAME = "address";
        public static final String COLUMN_ADDRESS_ID = "address_id";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_STREET = "street";
        public static final String COLUMN_COUNTRY = "country";
        public static final String COLUMN_STATE = "state";
        public static final String COLUMN_CITY = "city";
        public static final String COLUMN_POSTAL_CODE = "postal_code";
    }

    public static abstract class TABLE_CART implements BaseColumns {
        public static final String TABLE_NAME = "cart";
        public static final String COLUMN_CART_ID = "cart_id";
        public static final String COLUMN_USER_ID = "user_id";
    }

    public static abstract class TABLE_CART_ITEM_GAME implements BaseColumns {
        public static final String TABLE_NAME = "cart_item_game";
        public static final String COLUMN_CART_ID = "cart_id";
        public static final String COLUMN_GAME_ID = "game_id";
        public static final String COLUMN_AMOUNT = "amount";
    }

    public static abstract class TABLE_CART_ITEM_CONSOLE implements BaseColumns {
        public static final String TABLE_NAME = "cart_item_console";
        public static final String COLUMN_CART_ID = "cart_id";
        public static final String COLUMN_CONSOLE_ID = "console_id";
        public static final String COLUMN_AMOUNT = "amount";
    }

    public static abstract class TABLE_CONSOLE implements BaseColumns {
        public static final String TABLE_NAME = "console";
        public static final String COLUMN_CONSOLE_ID = "console_id";
        public static final String COLUMN_NAME = "console_name";
        public static final String COLUMN_PRICE = "console_price";
        public static final String COLUMN_DESCRIPTION = "console_description";
        public static final String COLUMN_REVIEW = "console_review";
        public static final String COLUMN_PUBLISHER = "console_publisher";
        public static final String COLUMN_DATE_PUBLISHED = "console_date_published";
        public static final String COLUMN_CONSOLE_TYPE = "console_type";
    }

    public static abstract class TABLE_CONSOLE_VIDEO_GAME implements BaseColumns {
        public static final String TABLE_NAME = "console_video_game";
        public static final String COLUMN_CONSOLE_NAME = "console_name";
        public static final String COLUMN_GAME_ID = "game_id";
    }

    // because we don't want the information to change if we edit address or payment
    // we duplicate the fields inside of doing foreign keys
    public static abstract class TABLE_ORDER implements BaseColumns {
        public static final String TABLE_NAME = "user_order";
        public static final String COLUMN_ORDER_ID = "order_id";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_DATE_ORDERED = "date_ordered";
        public static final String COLUMN_DATE_ARRIVING = "date_arriving";
        public static final String COLUMN_STATUS = "status";
        public static final String COLUMN_DELIVER_TO = "deliver_to";

        public static final String COLUMN_CARD_NUMBER = "card_number";
        public static final String COLUMN_NAME_ON_CARD = "name_on_card";
        public static final String COLUMN_EXPIRATION_MONTH = "expiration_month";
        public static final String COLUMN_EXPIRATION_YEAR = "expiration_year";

        public static final String COLUMN_STREET = "street";
        public static final String COLUMN_COUNTRY = "country";
        public static final String COLUMN_STATE = "state";
        public static final String COLUMN_CITY = "city";
        public static final String COLUMN_POSTAL_CODE = "postal_code";
    }

    public static abstract class TABLE_ORDER_ITEM_GAME implements BaseColumns {
        public static final String TABLE_NAME = "order_item_game";
        public static final String COLUMN_ORDER_ID = "order_id";
        public static final String COLUMN_GAME_ID = "game_id";
        public static final String COLUMN_AMOUNT = "amount";
    }

    public static abstract class TABLE_ORDER_ITEM_CONSOLE implements BaseColumns {
        public static final String TABLE_NAME = "order_item_console";
        public static final String COLUMN_ORDER_ID = "order_id";
        public static final String COLUMN_CONSOLE_ID = "console_id";
        public static final String COLUMN_AMOUNT = "amount";
    }

    public static abstract class TABLE_PAYMENT_INFORMATION implements BaseColumns {
        public static final String TABLE_NAME = "payment_information";
        public static final String COLUMN_PAYMENT_ID = "payment_id";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_CARD_NUMBER = "card_number";
        public static final String COLUMN_NAME_ON_CARD = "name_on_card";
        public static final String COLUMN_EXPIRATION_MONTH = "expiration_month";
        public static final String COLUMN_EXPIRATION_YEAR = "expiration_year";
    }

    public static abstract class TABLE_USER implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PASSWORD = "password";
        public static final String COLUMN_FIRST_NAME = "first_name";
        public static final String COLUMN_LAST_NAME = "last_name";
    }


    public static abstract class TABLE_VIDEO_GAME implements BaseColumns {
        public static final String TABLE_NAME = "videogame";
        public static final String COLUMN_GAME_ID = "game_id";
        public static final String COLUMN_NAME = "game_name";
        public static final String COLUMN_PRICE = "game_price";
        public static final String COLUMN_DESCRIPTION = "game_description";
        public static final String COLUMN_REVIEW = "game_review";
        public static final String COLUMN_PUBLISHER = "game_publisher";
        public static final String COLUMN_DATE_PUBLISHED = "game_date_published";
        public static final String COLUMN_ESRB = "game_esrb";
        public static final String COLUMN_LENGTH = "game_length";
        public static final String COLUMN_CATEGORY = "game_category";
        public static final String COLUMN_REGION = "game_region";
        public static final String COLUMN_NUMBER_PLAYER = "game_number_player";

    }

    public static abstract class TABLE_WISHLIST implements BaseColumns {
        public static final String TABLE_NAME = "wishlist";
        public static final String COLUMN_WISHLIST_ID = "wishlist_id";
        public static final String COLUMN_USER_ID = "user_id";
    }

    public static abstract class TABLE_WISHLIST_ITEM_GAME implements BaseColumns {
        public static final String TABLE_NAME = "wishlist_item_game";
        public static final String COLUMN_WISHLIST_ID = "wishlist_id";
        public static final String COLUMN_GAME_ID = "game_id";
    }

    public static abstract class TABLE_WISHLIST_ITEM_CONSOLE implements BaseColumns {
        public static final String TABLE_NAME = "wishlist_item_wishlist";
        public static final String COLUMN_WISHLIST_ID = "wishlist_id";
        public static final String COLUMN_CONSOLE_ID = "console_id";
    }

    public static abstract class TABLE_IMAGE_CONSOLE implements BaseColumns{
        public static final String TABLE_NAME = "item_image_console";
        public static final String COLUMN_IMAGE_ID = "image_id";
        public static final String COLUMN_IMAGE_URL = "image_url";
        public static final String COLUMN_CONSOLE_ID = "console_id";
    }

    public static abstract class TABLE_IMAGE_GAME implements BaseColumns{
        public static final String TABLE_NAME = "item_image_game";
        public static final String COLUMN_IMAGE_ID = "image_id";
        public static final String COLUMN_IMAGE_URL = "image_url";
        public static final String COLUMN_GAME_ID = "game_id";
    }

    public static abstract class TABLE_APPLICATION implements BaseColumns{
        public static final String TABLE_NAME = "application";
        public static final String COLUMN_APPLICATION_ID = "app_id";
        public static final String COLUMN_CURRENT_USER = "user_id";
    }

    public static abstract class TABLE_TRAILER implements BaseColumns{
        public static final String TABLE_NAME = "trailer";
        public static final String COLUMN_TRAILER_ID = "trailer_id";
        public static final String COLUMN_TRAILER_URL = "trailer_url";
        public static final String COLUMN_GAME_ID = "game_id";
    }

}