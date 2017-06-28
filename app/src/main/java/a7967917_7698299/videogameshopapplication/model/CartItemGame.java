package a7967917_7698299.videogameshopapplication.model;

import a7967917_7698299.videogameshopapplication.variables.ItemVariables;

/**
 * Created by alex on 2017-06-26.
 */

public class CartItemGame {

    private ItemVariables.TYPE itemType;
    private long gameId;
    private long cartId;
    private int amount;

    public CartItemGame(ItemVariables.TYPE itemType, long gameId, long cartId, int amount) {
        this.itemType = itemType;
        this.gameId = gameId;
        this.cartId = cartId;
        this.amount = amount;
    }

    public ItemVariables.TYPE getItemType() {
        return itemType;
    }

    public void setItemType(ItemVariables.TYPE itemType) {
        this.itemType = itemType;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
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
