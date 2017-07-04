package a7967917_7698299.videogameshopapplication.model;

import a7967917_7698299.videogameshopapplication.variables.ItemVariables;

/**
 * Created by alex on 7/3/2017.
 */

public class ItemImage {

    private long imageId;
    private String imageURL;
    private long itemId;
    private ItemVariables.TYPE itemType;

    public ItemImage(ItemVariables.TYPE itemType, long imageId, String imageURL, long itemId) {
        this.itemType = itemType;
        this.imageId = imageId;
        this.imageURL = imageURL;
        this.itemId = itemId;
    }

    public ItemVariables.TYPE getItemType() {
        return itemType;
    }

    public void setItemType(ItemVariables.TYPE itemType) {
        this.itemType = itemType;
    }

    public long getImageId() {
        return imageId;
    }

    public void setImageId(long imageId) {
        this.imageId = imageId;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }
}
