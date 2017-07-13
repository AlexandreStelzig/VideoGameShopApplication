package a7967917_7698299.videogameshopapplication.model;

import a7967917_7698299.videogameshopapplication.variables.ItemVariables;

/**
 * Created by alex on 7/3/2017.
 */

public class ItemTrailer {

    private long trailerId;
    private String trailerURL;
    private long itemId;


    public ItemTrailer(long trailerId, String trailerURL, long itemId) {
        this.trailerId = trailerId;
        this.trailerURL = trailerURL;
        this.itemId = itemId;
    }

    public long getTrailerId() {
        return trailerId;
    }

    public void setTrailerId(long trailerId) {
        this.trailerId = trailerId;
    }

    public String getTrailerURL() {
        return trailerURL;
    }

    public void setTrailerURL(String trailerURL) {
        this.trailerURL = trailerURL;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }
}
