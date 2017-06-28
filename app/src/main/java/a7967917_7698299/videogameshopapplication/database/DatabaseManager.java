package a7967917_7698299.videogameshopapplication.database;

import android.content.Context;

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

}
