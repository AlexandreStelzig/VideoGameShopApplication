package a7967917_7698299.videogameshopapplication.model;

import a7967917_7698299.videogameshopapplication.variables.ItemVariables;

/**
 * Created by alex on 2017-06-26.
 */

public class CartItem {

    private long itemId;
    private long cartId;
    private int amount;
    private ItemVariables.TYPE itemType;

    public CartItem(ItemVariables.TYPE itemType, long itemId, long cartId, int amount) {
        this.itemType = itemType;
        this.itemId = itemId;
        this.cartId = cartId;
        this.amount = amount;
    }

    public ItemVariables.TYPE getItemType() {
        return itemType;
    }

    public void setItemType(ItemVariables.TYPE itemType) {
        this.itemType = itemType;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
