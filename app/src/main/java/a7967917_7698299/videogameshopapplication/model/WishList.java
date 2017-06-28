package a7967917_7698299.videogameshopapplication.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 2017-06-26.
 */

public class WishList {
    private long wishListId;
    private long userId;
    private List<Item> wishListItems;


    public WishList(long wishListId, long userId, List<Item> wishListItems) {
        this.wishListId = wishListId;
        this.userId = userId;
        this.wishListItems = wishListItems;
    }

    public long getWishListId() {
        return wishListId;
    }

    public void setWishListId(long wishListId) {
        this.wishListId = wishListId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public List<Item> getWishListItems() {
        return wishListItems;
    }

    public void setWishListItems(List<Item> wishListItems) {
        this.wishListItems = wishListItems;
    }
}

