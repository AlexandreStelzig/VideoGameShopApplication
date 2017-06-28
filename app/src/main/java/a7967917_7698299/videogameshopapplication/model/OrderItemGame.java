package a7967917_7698299.videogameshopapplication.model;

import a7967917_7698299.videogameshopapplication.variables.ItemVariables;

/**
 * Created by alex on 2017-06-26.
 */

public class OrderItemGame {

    private ItemVariables.TYPE itemType;
    private long gameId;
    private long orderId;
    private int amount;

    public OrderItemGame(ItemVariables.TYPE itemType, long gameId, long orderId, int amount) {
        this.itemType = itemType;
        this.gameId = gameId;
        this.orderId = orderId;
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

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
