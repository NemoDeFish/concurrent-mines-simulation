/**
 * An elevator class that moves between the TOP and BOTTOM positions,
 * carrying carts into and out of the mines. Since it is a stop, where
 * the carts can stop, it extends from the Stop parent class.
 *
 * @author Si Yong Lim
 * @date 30/03/2025
 */

import static java.lang.Thread.sleep;

public class Elevator extends Stop {
    // enum representing the two possible positions of the elevator
    public enum Position {TOP, BOTTOM}

    protected Position position = Position.TOP;

    // cart that arrived and is waiting for elevator to descend
    private Cart waitingCart = null;

    // handles the arrival of a new cart
    public synchronized void arrive(Cart newCart) {
        // elevator must be at the TOP and empty to receive a new cart
        waitingCart = newCart;
        while (!(cart == null && position == Position.TOP)) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // new cart is now in the elevator ready to descend
        cart = newCart;
        waitingCart = null;
        System.out.println("elevator descends with " + cart);

        // pause for elevator moving
        try {
            sleep(Params.ELEVATOR_TIME);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // elevator with new cart has now reached the bottom
        position = Position.BOTTOM;
        notifyAll();
    }

    // handles the departure of cart from mines
    public synchronized Cart depart() {
        // check to ensure that consumer only removes carts that are present, full,
        // and while elevator is at the top
        while (!(cart != null && cart.gems == Params.STATIONS && position == Position.TOP)) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // removes full cart from elevator
        Cart tempCart = cart;
        cart = null;
        notifyAll();

        return tempCart;
    }

    // moves the elevator to the TOP when full cart waiting at the BOTTOM
    // removes the need to check for elevator's position because the elevator must
    // be at the bottom to be successfully delivered.
    public synchronized void ascend() {
        System.out.println("elevator ascends with " + cart);

        // pause for elevator moving
        try {
            sleep(Params.ELEVATOR_TIME);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // elevator with full cart is now at the TOP
        position = Position.TOP;
        notifyAll();
    }

    // random movement by the operator when elevator is empty
    public synchronized void move() {
        if (cart == null) {
            // avoid the operator moving the elevator down when there is a
            // cart waiting at the top to descend
            if (position == Elevator.Position.TOP && waitingCart == null) {
                position = Elevator.Position.BOTTOM;
                System.out.println("elevator descends (empty)");
            } else if (position == Elevator.Position.BOTTOM) {
                position = Elevator.Position.TOP;
                System.out.println("elevator ascends (empty)");
            }

            // pause for elevator moving
            try {
                sleep(Params.ELEVATOR_TIME);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            notifyAll();
        }
    }

    // checks if elevator is ready to be collected by engine
    @Override
    public boolean readyCollect() {
        return super.readyCollect() && position == Position.BOTTOM;
    }

    // checks if elevator is ready to be delivered to by engine
    @Override
    public boolean readyDeliver() {
        return super.readyDeliver() && position == Position.BOTTOM;
    }

    // returns a string representation of the Elevator object
    public String toString() {
        return "elevator";
    }
}