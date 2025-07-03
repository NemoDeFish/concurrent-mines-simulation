/**
 * An abstract superclass that represents a stoppable point where
 * carts can be collected and delivered. A Stop can hold 1 cart
 * at a time.
 *
 * @author Si Yong Lim
 * @date 30/03/2025
 */

public abstract class Stop {
    // cart that is currently at the stop
    protected Cart cart = null;

    // called by engine to collect ready cart
    public synchronized Cart collectFrom() {
        while (!readyCollect()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        Cart tempCart = removeCart();
        System.out.println(tempCart + " collected from " + this);

        // notifies waiting engines that stop is empty and ready to receive cart
        notifyAll();

        // return removed cart to next engine for collection
        return tempCart;
    }

    // called by engine to deliver cart to stop
    public synchronized Cart deliverTo(Cart newCart) {
        while (!readyDeliver()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println(newCart + " delivered to " + this);
        addCart(newCart);

        // notifies stations that cart arrived and ready to receive gem
        notifyAll();

        // returns null to remove cart from previous engine
        return null;
    }

    // removes and returns cart from the stop
    public Cart removeCart() {
        Cart temp = cart;
        cart = null;
        return temp;
    }

    // places a cart at the stop
    public void addCart(Cart cart) {
        this.cart = cart;
    }

    // checks if cart is at the stop ready to be collected
    public boolean readyCollect() {
        return cart != null;
    }

    // checks if stop is empty and ready to receive cart
    public boolean readyDeliver() {
        return cart == null;
    }
}
