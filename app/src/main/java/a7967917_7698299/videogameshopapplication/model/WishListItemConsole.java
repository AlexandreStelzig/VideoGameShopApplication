package a7967917_7698299.videogameshopapplication.model;

import a7967917_7698299.videogameshopapplication.variables.ItemVariables;

/**
 * Created by alex on 2017-06-26.
 */

public class WishListItemConsole {

    private ItemVariables.TYPE itemType;
    private long consoleId;
    private long wishListId;


    public WishListItemConsole(ItemVariables.TYPE itemType, long consoleId, long wishListId) {
        this.itemType = itemType;
        this.consoleId = consoleId;
        this.wishListId = wishListId;
    }

    public ItemVariables.TYPE getItemType() {
        return itemType;
    }

    public void setItemType(ItemVariables.TYPE itemType) {
        this.itemType = itemType;
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
