package a7967917_7698299.videogameshopapplication.model;

import a7967917_7698299.videogameshopapplication.variables.ItemVariables;

/**
 * Created by alex on 2017-06-26.
 */

public class OrderItemConsole {

    private ItemVariables.TYPE itemType;
    private long consoleId;
    private long orderId;
    private int amount;

    public OrderItemConsole(ItemVariables.TYPE itemType, long consoleId, long orderId, int amount) {
        this.itemType = itemType;
        this.consoleId = consoleId;
        this.orderId = orderId;
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
