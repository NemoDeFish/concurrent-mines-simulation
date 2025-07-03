/**
 * An operator class that periodically triggers the elevator's movement
 * whenever it is empty at random intervals.
 *
 * @author Si Yong Lim
 * @date 30/03/2025
 */

public class Operator extends Thread {
    // the elevator which the operator will be operating on
    private final Elevator elevator;

    // creates an operator instance and assigns it to an elevator
    public Operator(Elevator elevator) {
        this.elevator = elevator;
    }

    // at random intervals, the operator will move the elevator
    public void run() {
        while(!this.isInterrupted()) {
            try {
                sleep(Params.operatorPause());
                elevator.move();
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }
}
