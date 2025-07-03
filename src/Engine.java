/**
 * An engine class that facilitates the movement between two stations.
 *
 * @author Si Yong Lim
 * @date 30/03/2025
 */

public class Engine extends Thread {
    // two stations that the engine will be alternating to and from
    private final Stop stationA, stationB;

    // cart that engine is currently carrying
    private Cart cart = null;

    // creates a new engine with its corresponding stations
    public Engine(Stop stationA, Stop stationB) {
        this.stationA = stationA;
        this.stationB = stationB;
    }

    // engines carry carts to and from the stations
    public void run() {
        while (!this.isInterrupted()) {
            try {
                cart = stationA.collectFrom();

                // pause for engine transporting carts from stationA to stationB
                sleep(Params.ENGINE_TIME);

                // null is returned after the delivery to mean that engine is now empty
                cart = stationB.deliverTo(cart);

                // if the station to transport to is an elevator, immediately ascend
                if (stationB instanceof Elevator) {
                    ((Elevator) stationB).ascend();
                }

                // pause for engine transporting carts from stationB to stationA
                sleep(Params.ENGINE_TIME); // engine going from stationB to stationA
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }
}
