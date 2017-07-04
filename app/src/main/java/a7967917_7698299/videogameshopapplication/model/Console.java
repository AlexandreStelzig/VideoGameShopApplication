package a7967917_7698299.videogameshopapplication.model;

import a7967917_7698299.videogameshopapplication.variables.ItemVariables;

/**
 * Created by alex on 2017-06-26.
 */

public class Console extends Item {

    private long consoleId;

    public Console(long consoleId, String name, double price, String description, ItemVariables.STAR_REVIEW review, String publisher, String datePublished) {
        super(ItemVariables.TYPE.CONSOLE, name, price, description, review, publisher, datePublished);
        this.consoleId = consoleId;
    }


    public long getConsoleId() {
        return consoleId;
    }

    public void setConsoleId(long consoleId) {
        this.consoleId = consoleId;
    }
}
