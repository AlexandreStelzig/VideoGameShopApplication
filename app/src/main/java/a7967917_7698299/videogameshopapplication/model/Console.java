package a7967917_7698299.videogameshopapplication.model;

import a7967917_7698299.videogameshopapplication.variables.ItemVariables;

/**
 * Created by alex on 2017-06-26.
 */

public class Console extends Item {

    private ItemVariables.CONSOLES consoleType;

    public Console(ItemVariables.TYPE itemType, String name, double price, String description, ItemVariables.STAR_REVIEW review, String publisher, String datePublished, long consoleId, ItemVariables.CONSOLES consoleType) {
        super(consoleId, itemType, name, price, description, review, publisher, datePublished);
        this.consoleType = consoleType;
    }

    public ItemVariables.CONSOLES getConsoleType() {
        return consoleType;
    }

    public void setConsoleType(ItemVariables.CONSOLES consoleType) {
        this.consoleType = consoleType;
    }
}
