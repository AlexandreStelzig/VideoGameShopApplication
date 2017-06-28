package a7967917_7698299.videogameshopapplication.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 2017-06-26.
 */

public class Cart {
    private long cartId;
    private long userId;
    private List<Item> cartItems;

    public Cart(long cartId, long userId) {
        this.cartId = cartId;
        this.userId = userId;
        cartItems = new ArrayList<>();
    }

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }



}
