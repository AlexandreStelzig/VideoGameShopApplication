package a7967917_7698299.videogameshopapplication.model;

import java.util.Date;

import a7967917_7698299.videogameshopapplication.variables.ItemVariables;

/**
 * Created by alex on 2017-06-26.
 */

public class Item {

    private ItemVariables.TYPE itemType;
    private String name;
    private double price;
    private String description;
    private ItemVariables.STAR_REVIEW review;
    private ItemVariables.PUBLISHER publisher;
    private Date datePublished;


    public Item(ItemVariables.TYPE itemType, String name, double price, String description, ItemVariables.STAR_REVIEW review, ItemVariables.PUBLISHER publisher, Date datePublished) {
        this.itemType = itemType;
        this.name = name;
        this.price = price;
        this.description = description;
        this.review = review;
        this.publisher = publisher;
        this.datePublished = datePublished;
    }

    public ItemVariables.TYPE getItemType() {
        return itemType;
    }

    public void setItemType(ItemVariables.TYPE itemType) {
        this.itemType = itemType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ItemVariables.STAR_REVIEW getReview() {
        return review;
    }

    public void setReview(ItemVariables.STAR_REVIEW review) {
        this.review = review;
    }

    public ItemVariables.PUBLISHER getPublisher() {
        return publisher;
    }

    public void setPublisher(ItemVariables.PUBLISHER publisher) {
        this.publisher = publisher;
    }

    public Date getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Date datePublished) {
        this.datePublished = datePublished;
    }
}
