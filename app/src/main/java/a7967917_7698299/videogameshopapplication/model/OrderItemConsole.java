package a7967917_7698299.videogameshopapplication.model;

import a7967917_7698299.videogameshopapplication.variables.ItemVariables;

/**
 * Created by alex on 2017-06-26.
 */

public class OrderItemConsole {

    private long consoleId;
    private long orderId;
    private int amount;

    public OrderItemConsole(long consoleId, long orderId, int amount) {
        this.consoleId = consoleId;
        this.orderId = orderId;
        this.amount = amount;
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
