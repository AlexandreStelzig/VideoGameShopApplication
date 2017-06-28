package a7967917_7698299.videogameshopapplication.model;

/**
 * Created by alex on 2017-06-26.
 */

public class Address {

    private long addressId;
    private long userId;
    private String street;
    private String country;
    private String state;
    private String city;
    private String postalCode;

    public Address(long addressId, long userId, String street, String country, String state, String city, String postalCode) {
        this.addressId = addressId;
        this.userId = userId;
        this.street = street;
        this.country = country;
        this.state = state;
        this.city = city;
        this.postalCode = postalCode;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
