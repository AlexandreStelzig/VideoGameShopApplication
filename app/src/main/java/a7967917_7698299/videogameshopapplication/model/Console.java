package a7967917_7698299.videogameshopapplication.model;

import java.util.Date;

import a7967917_7698299.videogameshopapplication.variables.ItemVariables;

/**
 * Created by alex on 2017-06-26.
 */

public class Console extends Item {

    private long consoleId;

    public Console(String name, double price, String description, ItemVariables.STAR_REVIEW review, ItemVariables.PUBLISHER publisher, Date datePublished, long consoleId) {
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
