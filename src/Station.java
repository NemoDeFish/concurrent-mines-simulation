/**
 * A station class that resides in the bottom of the Mines of Semaphoria.
 * It can hold up to 1 cart and 1 gem. Since it is a stop, where
 * the carts can stop, it extends from the Stop parent class.
 *
 * @author Si Yong Lim
 * @date 30/03/2025
 */

public class Station extends Stop {
    // an id associated with the station
    private final int id;

    // boolean to keep track whether gem has been mined and deposited
    private volatile boolean gem = false;

    // boolean to keep track whether gem has been loaded onto cart
    protected volatile boolean loaded = false;

    // creates a Station instance and assigns an id to it
    public Station(int id) {
        this.id = id;
    }

    // called by miner when the gem is ready to be deposited to the station
    public synchronized void depositGem() {
        // checks to ensure that there is only 1 gem at the station at a time
        while (hasGem()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // deposits gem to station
        gem = true;

        // loads gem onto cart if a cart is waiting at the station
        loadGem();

        // notify engines that the cart is ready to be carried away
        notifyAll();
    }

    // loads gem onto cart
    public synchronized void loadGem() {
        // checks if cart is waiting and makes sure it can only
        // load 1 at current station
        while (!(cart != null && !loaded)) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // loads gem onto cart
        loaded = true;
        cart.gems++;
        System.out.println(cart + " loaded with a gem");

        // removes gem from station so miner can mine again
        gem = false;
    }

    // checks if cart is ready to be collected and loaded with gem
    @Override
    public boolean readyCollect() {
        return super.readyCollect() && loaded;
    }

    // called by engine to remove loaded cart from station
    @Override
    public Cart removeCart() {
        loaded = false;
        return super.removeCart();
    }

    // getter to check if station has a gem
    public boolean hasGem() {
        return gem;
    }

    // returns a string representation of the station object
    @Override
    public String toString() {
        return "station " + id;
    }
}
