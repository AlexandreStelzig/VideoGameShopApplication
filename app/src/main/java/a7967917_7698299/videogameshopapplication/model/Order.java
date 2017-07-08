package a7967917_7698299.videogameshopapplication.model;

import java.util.List;

import a7967917_7698299.videogameshopapplication.variables.OrderVariables;

/**
 * Created by alex on 2017-06-26.
 */

public class Order {


    private long orderId;
    private long userId;
    private String dateOrdered;
    private String dateArriving;
    private OrderVariables.STATUS status;
    private String deliverTo;

    // payment
    private int cardNumber;
    private String nameOnCard;
    private int expirationMonth;
    private int expirationYear;

    // address
    private String street;
    private String country;
    private String state;
    private String city;
    private String postalCode;

    public Order(long orderId, long userId, String deliverTo, String dateOrdered, String dateArriving, OrderVariables.STATUS status, int cardNumber, String nameOnCard, int expirationMonth, int expirationYear, String street, String country, String state, String city, String postalCode) {
        this.orderId = orderId;
        this.userId = userId;
        this.dateOrdered = dateOrdered;
        this.dateArriving = dateArriving;
        this.status = status;
        this.cardNumber = cardNumber;
        this.nameOnCard = nameOnCard;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.street = street;
        this.country = country;
        this.state = state;
        this.city = city;
        this.postalCode = postalCode;
        this.deliverTo = deliverTo;
    }

    public String getDeliverTo() {
        return deliverTo;
    }

    public void setDeliverTo(String deliverTo) {
        this.deliverTo = deliverTo;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getDateOrdered() {
        return dateOrdered;
    }

    public void setDateOrdered(String dateOrdered) {
        this.dateOrdered = dateOrdered;
    }

    public String getDateArriving() {
        return dateArriving;
    }

    public void setDateArriving(String dateArriving) {
        this.dateArriving = dateArriving;
    }

    public OrderVariables.STATUS getStatus() {
        return status;
    }

    public void setStatus(OrderVariables.STATUS status) {
        this.status = status;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public int getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(int expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public int getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(int expirationYear) {
        this.expirationYear = expirationYear;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
