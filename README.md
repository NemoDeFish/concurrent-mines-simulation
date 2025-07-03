# ⛏️ Mines of Semaphoria

## 💎 Overview

**Mines of Semaphoria** is a concurrent system simulation inspired by a fictional mining operation deep beneath the Crypto Mountains. It models the coordination between carts, miners, engines, stations, and an elevator system operating on a circular underground track.

This project focuses on concurrent programming, system modeling, and verification using Java and FSP (Finite State Processes). It is part of my portfolio to demonstrate my understanding of concurrent systems design, synchronization challenges, and formal verification.

---

## 🚀 What It Simulates

* Carts arrive and descend into a mine via an elevator.
* Carts are transported around a ring-shaped underground track by a series of engines.
* At each station, a miner loads a gem into the cart (if available).
* Each cart collects one gem per station, then exits the mine via the elevator.
* Resources like stations, engines, and the elevator are synchronized to ensure safe operation.

---

## ⚙️ Technologies Used

* **Java**: Core simulation logic using threads and synchronized monitors.
* **FSP + LTSA**: Formal modeling and verification of system properties.
* **Thread-safe Design**: Use of `synchronized` methods and `wait/notify`.

---

## 📂 Project Structure

> ⚠️ **Note**: This repository contains *core components* only.
> You must implement your own `Main`, `Producer`, `Consumer`, `Params`, and `Cart` classes to run the full Java simulation.

```
MinesOfSemaphoria/
├── src/
│   ├── Elevator.java         # Elevator monitor
│   ├── Engine.java           # Engine monitor
│   ├── Stop.java             # Stop abstract class
│   ├── Station.java          # Mining station logic
│   ├── Miner.java            # Miner logic
│   ├── Operator.java         # Operator logic
│   ├── (Producer.java)       # User-defined: cart generator
│   ├── (Consumer.java)       # User-defined: cart remover
│   ├── (Params.java)         # User-defined: parameters
│   ├── (Cart.java)           # User-defined: cart structure
│   └── (Main.java)           # User-defined: simulation runner
├── model.lts                 # Original FSP model
├── model_modified.lts        # Improved version with deadlock fix
└── README.md
```

---

## 🔍 Simulation Trace Example

Sample logs generated during simulation:

```
cart [2: 1] loaded with a gem
cart [2: 1] collected from station 0
cart [3: 0] delivered to station 0
elevator ascends (empty)
elevator descends with cart [5: 0]
cart [1: 4] delivered to elevator
elevator ascends with cart [1: 4]
```

---

## 🧪 FSP + LTSA Modeling

* `model.lts`: Original concurrent system model.
* `model_modified.lts`: Revised version with stricter elevator control to avoid deadlock.

FSP allowed me to:

* Define clear behavioral constraints.
* Check **safety** and **liveness** properties using **LTSA**.
* Validate system correctness in a way not possible with Java alone.

---

## ✍️ Reflection & Learnings

### 👀 Observed Behaviours

The system ran smoothly under normal conditions, with multiple threads (engines, stations, elevator, etc.) coordinating effectively to move carts through the mine. It could handle up to 100 carts without crashing or halting.

### 🧩 Key Issues Identified
A deadlock occurred when the elevator, full of a cart, waited to ascend, while an engine waited to deliver another cart to the elevator — all while other carts were still cycling. This met the fourth **Coffman condition** for deadlock: circular wait.

---

## 🧠 Modifications Made in Model

* **Elevator Control Logic**: Prevent elevator from ascending or descending empty if a cart is ready.
* **Flow Regulation**: Carts already in the system are prioritized before new ones enter, preventing overloading and resource contention.
* **Result**: Reduced transition paths, slightly improved composition time in LTSA, and fewer invalid states.

Here’s your updated `README.md` with a new **Installation & Usage** section for both **Java** and **LTSA**, placed right after the Overview section to follow standard structure:

---

## 💾 Installation & Usage

### 🔧 Java Simulation (Terminal / IDE)

#### ✅ Prerequisites

* Java 8 or higher installed (`java -version`)
* A Java IDE (e.g. IntelliJ, Eclipse) or terminal for compilation

#### 📥 Setup

1. Clone or download the repository:

   ```bash
   git clone https://github.com/NemoDeFish/concurrent-mines-simulation
   cd concurrent-mines-simulation
   ```

2. **Implement required files** (these are **not** provided):

   * `Main.java`: simulation runner
   * `Producer.java`: thread to create new carts
   * `Consumer.java`: thread to remove carts
   * `Params.java`: constants (e.g., number of stations, timing)
   * `Cart.java`: cart object (ID, number of gems, etc.)

3. Compile the source code:

   ```bash
   javac src/*.java
   ```

4. Run the simulation:

   ```bash
   java -cp src Main
   ```


---

### 🧪 FSP Model with LTSA

#### ✅ Prerequisites

* Download **LTSA (Labelled Transition System Analyser)**:

  * Website: [http://www.doc.ic.ac.uk/ltsa/](http://www.doc.ic.ac.uk/ltsa/)
  * Direct link (JAR): [ltsa.jar](http://www.doc.ic.ac.uk/ltsa/ltsa.jar)
  * LTSA is a standalone Java application.

#### 📥 Setup & Run

1. Open LTSA:

   ```bash
   java -jar ltsa.jar
   ```

2. Load `model_modified.lts` or `model.lts`:

   * Go to `File` > `Open`
   * Select one of the `.lts` model files

3. Click **Check Progress** to verify **liveness**.

4. Click **Check Safety** to detect invalid states or deadlocks.

5. Use **Run** > **Animate** to simulate system execution and manually step through transitions.