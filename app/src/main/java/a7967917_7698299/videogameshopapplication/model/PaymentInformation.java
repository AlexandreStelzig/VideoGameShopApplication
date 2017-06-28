package a7967917_7698299.videogameshopapplication.model;

/**
 * Created by alex on 2017-06-26.
 */

public class PaymentInformation {

    private int paymentId;
    private int userId;
    private int cardNumber;
    private int nameOnCard;
    private int expirationMonth;
    private int expirationYear;

    public PaymentInformation(int paymentId, int userId, int cardNumber, int nameOnCard, int expirationMonth, int expirationYear) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.cardNumber = cardNumber;
        this.nameOnCard = nameOnCard;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(int nameOnCard) {
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
