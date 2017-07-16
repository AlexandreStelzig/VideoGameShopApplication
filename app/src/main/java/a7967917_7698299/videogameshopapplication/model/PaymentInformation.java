package a7967917_7698299.videogameshopapplication.model;

/**
 * Created by alex on 2017-06-26.
 */

public class PaymentInformation {

    private long paymentId;
    private long userId;
    private long cardNumber;
    private String nameOnCard;
    private int expirationMonth;
    private int expirationYear;

    public PaymentInformation(long paymentId, long userId, long cardNumber, String nameOnCard, int expirationMonth, int expirationYear) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.cardNumber = cardNumber;
        this.nameOnCard = nameOnCard;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
    }

    public long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getCardNumber() {
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


}
