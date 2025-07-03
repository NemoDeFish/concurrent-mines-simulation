/**
 * A miner class who mines gems and deposits them in stations whenever it is empty.
 *
 * @author Si Yong Lim
 * @date 30/03/2025
 */

public class Miner extends Thread {
    // the station to which the miner is assigned
    private final Station station;

    // creates a Miner instance and assigns the miner to a station
    public Miner(Station station) {
        this.station = station;
    }

    // keeps mining gems and depositing them in stations whenever it is empty
    public void run() {
        while (!this.isInterrupted()) {
            try {
                // pause while miner is mining gem
                sleep(Params.MINING_TIME);

                station.depositGem();
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }
}
