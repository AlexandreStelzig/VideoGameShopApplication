package a7967917_7698299.videogameshopapplication.model;

import a7967917_7698299.videogameshopapplication.variables.ItemVariables;

/**
 * Created by alex on 2017-06-26.
 */

public class CartItemConsole {

    private ItemVariables.TYPE itemType;
    private long consoleId;
    private long cartId;
    private int amount;

    public CartItemConsole(ItemVariables.TYPE itemType, long consoleId, long cartId, int amount) {
        this.itemType = itemType;
        this.consoleId = consoleId;
        this.cartId = cartId;
        this.amount = amount;
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
