package a7967917_7698299.videogameshopapplication.model;

import a7967917_7698299.videogameshopapplication.variables.ItemVariables;

/**
 * Created by alex on 2017-06-26.
 */

public class WishListItemConsole {

    private long consoleId;
    private long wishListId;


    public WishListItemConsole( long consoleId, long wishListId) {

        this.consoleId = consoleId;
        this.wishListId = wishListId;
    }

    public long getConsoleId() {
        return consoleId;
    }

    public void setConsoleId(long consoleId) {
        this.consoleId = consoleId;
    }

    public long getWishListId() {
        return wishListId;
    }

    public void setWishListId(long wishListId) {
        this.wishListId = wishListId;
    }
}
