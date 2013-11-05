package trafiksimulering;

public class TrafficSystem {
	// Definierar de vägar och signaler som ingår i det
	// system som skall studeras.
	// Samlar statistik

	// Attribut som beskriver beståndsdelarna i systemet
	private Lane r0;
	private Lane r1;
	private Lane r2;
	private Lane graphLane1;
	private Lane graphLane2;
	private Light s1;
	private Light s2;
	private Car[] cars;
	private int newCarIndex = 0;
	private int time = 0;

	// Statistik

	// private int timeMovingCounter;
	// private int timeMovingCounterLane1;
	// private int timeMovingCounterLane2;

	private float ratioBetweenMovingAndStandingStill;

	private float avgTimeToPassRedlight;
	private int avgTimeToPassRedlight_counter;
	private float avgTimeToPassRedlight_sum;
	private float avgTimeToPassRedlightLane1;
	private int avgTimeToPassRedlightLane1_counter;
	private float avgTimeToPassRedlightLane1_sum;
	private float avgTimeToPassRedlightLane2;
	private int avgTimeToPassRedlightLane2_counter;
	private float avgTimeToPassRedlightLane2_sum;

	private int timeNotMovingCounter;
	private int timeNotMovingCounterLane1;
	private int timeNotMovingCounterLane2;

	private float avgTimeNotMoving;
	private int avgTimeNotMoving_counter;
	private float avgTimeNotMoving_sum;
	private float avgTimeNotMovingLane1;
	private int avgTimeNotMovingLane1_counter;
	private float avgTimeNotMovingLane1_sum;
	private float avgTimeNotMovingLane2;
	private int avgTimeNotMovingLane2_counter;
	private float avgTimeNotMovingLane2_sum;

	private float avgTimeMoving;
	private int avgTimeMoving_counter;
	private float avgTimeMoving_sum;
	private float avgTimeMovingLane1;
	private int avgTimeMovingLane1_counter;
	private float avgTimeMovingLane1_sum;
	private float avgTimeMovingLane2;
	private int avgTimeMovingLane2_counter;
	private float avgTimeMovingLane2_sum;

	private int nmbrOfCarsPassed;
	private int nmbrOfCarsPassedLane1;
	private int nmbrOfCarsPassedLane2;

	private float avgTimeQueueing;
	private int avgTimeQueueing_counter;
	private float avgTimeQueueing_sum;
	private float avgTimeQueueingLane1;
	private int avgTimeQueueingLane1_counter;
	private float avgTimeQueueingLane1_sum;
	private float avgTimeQueueingLane2;
	private int avgTimeQueueingLane2_counter;
	private float avgTimeQueueingLane2_sum;

	private int laneLength0;
	private int laneLength1;
	private int laneLength2;
	private int redLightCounterLight1;
	private int redLightCounterLight2;

	// Diverse attribut för simuleringsparametrar (ankomstintensiteter,
	// destinationer...)

	// Diverse attribut för statistiksamling
	// ....
	public int getTime() {
		return this.time;
	}

	public TrafficSystem(int trafficLight1_period, int trafficLight1_green,
			int trafficLight2_period, int trafficLight2_green, int laneLength0,
			int laneLength1, int laneLength2, int nmbrOfCars) {
		this.laneLength0 = laneLength0;
		this.laneLength0 = laneLength1;
		this.laneLength0 = laneLength2;

		Car car = new Car(-1, -1);
		cars = new Car[nmbrOfCars];
		for (int i = 0; i < cars.length - 1; i++) {
			cars[i] = car;
		}
		r0 = new Lane(laneLength0);
		r1 = new Lane(laneLength1);
		r2 = new Lane(laneLength2);
		graphLane1 = new Lane(7);
		graphLane2 = new Lane(5);
		s1 = new Light(trafficLight1_period, trafficLight1_green);
		s2 = new Light(trafficLight2_period, trafficLight2_green);
	}

	public void readParameters() {

		// Läser in parametrar för simuleringen
		// Metoden kan läsa från terminalfönster, dialogrutor
		// eller från en parameterfil. Det sista alternativet
		// är att föredra vid uttestning av programmet eftersom
		// man inte då behöver mata in värdena vid varje körning.
		// Standardklassen Properties är användbar för detta.
	}

	public void printStatistics() {
		System.out.println("Amount of cars that have passed any green light: "
				+ nmbrOfCarsPassed);
		System.out
				.println("Amount of cars that have passed the green light on lane 1: "
						+ nmbrOfCarsPassedLane1);
		System.out
				.println("Amount of cars that have passed the green light on lane 2: "
						+ nmbrOfCarsPassedLane2);
System.out.println("\nThe amount of times the traffic light has been red on lane 1: " + s1.get_redLightCounter());
System.out.println("The amount of times the traffic light has been red on lane 2: " + s2.get_redLightCounter());
		System.out
				.println("\nRatio between cars going towards lane 1 and lane 2 (on format lane1 / lane2): "
						+ ((float) nmbrOfCarsPassedLane1 / nmbrOfCarsPassedLane2));
		System.out
				.println("Ratio between cars going towards lane 1 and lane 2 (on format lane2 / lane1): "
						+ ((float) nmbrOfCarsPassedLane2 / nmbrOfCarsPassedLane1));

		System.out.println("\nAverage time for a car to pass any green light: "
				+ avgTimeToPassRedlight);
		System.out
				.println("Average time for a car to pass the green light at lane 1: "
						+ avgTimeToPassRedlightLane1);
		System.out
				.println("Average time for a car to pass the green light at lane 2: "
						+ avgTimeToPassRedlightLane2);
/*
		System.out
				.println("\nThe average time a car is spending halting when moving towards any green light: "
						+ avgTimeNotMoving);
		System.out
				.println("The average time a car is spending halting when moving towards the green light at lane 1: "
						+ avgTimeNotMovingLane1);
		System.out
				.println("The average time a car is spending halting when moving towards any green light at lane 2: "
						+ avgTimeNotMovingLane2);
*/
		System.out
				.println("\nThe average time a car is spending queueing before it can enter lane 0: "
						+ avgTimeQueueing + "\n");/*
		System.out
		.println("\nThe average time a car is spending queueing before it can enter lane 1: "
				+ avgTimeQueueingLane1);
		
		System.out
		.println("\nThe average time a car is spending queueing before it can enter lane 2: "
				+ avgTimeQueueingLane2);
		
		
		System.out
				.println("\nRatio between time moving and standing still while going "
						+ "towards lane 1 (on the format (moving/halting): "
						+ (avgTimeNotMoving / avgTimeMoving) + "\n");
		System.out
				.println("Ratio between time moving and standing still while going "
						+ "towards \ngreen light at lane 1 (on the format (halting/moving): "
						+ (avgTimeMoving / avgTimeNotMoving) + "\n");
*/
	}

	public void step() {
		newCarIndex++;
		if (newCarIndex == cars.length)
			newCarIndex = 0;

		int carDest = 0;
		int nmbrOfDupclicateBornTime = 0;
		int indexOfCarToInitiate = -1;
		int nullIndex = 0;
		boolean newCarAlreadyFound = false;

		for (int carIndex = 0; carIndex < cars.length - 1; carIndex++) {
			if (!newCarAlreadyFound) {
				if (cars[carIndex].get_bornTime() == time) {
					indexOfCarToInitiate = carIndex;
					newCarAlreadyFound = true;
				}
			} else if (newCarAlreadyFound) {
				if (cars[carIndex].get_bornTime() == time) {
					cars[carIndex]
							.set_bornTime(cars[carIndex].get_bornTime() + 1);
				}
			}
		}
		stepTopLanes();

		if (r0.carAtEnd())
			moveFirstCarToNextLaneAndStep();
		else
			r0.step();

		if (newCarAlreadyFound)
			carInsertion(indexOfCarToInitiate);

		graph();
		time++;
		s1.step();
		s2.step();
	}

	public void step(int destination, int bornTime) {
		newCarIndex++;
		if (newCarIndex == cars.length - 1)
			newCarIndex = 0;

		Car car = new Car(bornTime, destination);
		cars[newCarIndex] = car;
		int carDest = 0;
		int nmbrOfDupclicateBornTime = 0;
		int indexOfCarToInitiate = -1;
		int nullIndex = 0;
		boolean newCarAlreadyFound = false;

		for (int carIndex = 0; carIndex < cars.length - 1; carIndex++) {
			if (!newCarAlreadyFound) {
				if (cars[carIndex].get_bornTime() == time) {
					indexOfCarToInitiate = carIndex;
					newCarAlreadyFound = true;
				}
			} else if (newCarAlreadyFound) {
				if (cars[carIndex].get_bornTime() == time) {
					cars[carIndex]
							.set_bornTime(cars[carIndex].get_bornTime() + 1);
				}
			}
		}
		stepTopLanes();

		if (r0.carAtEnd()) {
			moveFirstCarToNextLaneAndStep();
		} else {
			r0.step();
		}

		if (newCarAlreadyFound)
			carInsertion(indexOfCarToInitiate);

		graph();

		time++;
		s1.step();
		s2.step();
	}

	public void graph() {
		System.out.println("\t|");
		System.out.println("\t|");
		System.out.println("\t|");
		System.out.print("\t| C");
		for (int i = 0; i < r2.laneLength(); i++)
			if (!(i % 5 == 0))
				System.out.print(" ");
		System.out.print("B");
		for (int i = 0; i < r0.laneLength() + r1.laneLength(); i++)
			if (!(i % 5 == 0))
				System.out.print(" ");
		System.out.println("A");
		System.out.print("D1<");
		if (graphLane1.carAtElement(0))
			System.out.print("1");
		else
			System.out.print("-");
		if (graphLane1.carAtElement(1))
			System.out.print("1");
		else
			System.out.print("-");
		if (graphLane1.carAtElement(2))
			System.out.print("1");
		else
			System.out.print("-");
		if (graphLane1.carAtElement(3))
			System.out.print("1");
		else
			System.out.print("-");
		if (graphLane1.carAtElement(4))
			System.out.print("1");
		else
			System.out.print("-");
		if (graphLane1.carAtElement(5))
			System.out.print("1");
		else
			System.out.print("|");
		if (graphLane1.carAtElement(6))
			System.out.print("1");
		else
			System.out.print("-");
		System.out.print(s1.toString());
		System.out.println(r1.toString() + r0.toString_());
		if (graphLane2.carAtElement(3))
			System.out.print("\t2");
		else
			System.out.print("\t|");
		if (graphLane2.carAtElement(4))
			System.out.print("2" + s1.toString());
		else
			System.out.print("-" + s1.toString());
		System.out.println(r2.toString());
		if (graphLane2.carAtElement(2))
			System.out.println("\t2");
		else
			System.out.println("\t|");
		if (graphLane2.carAtElement(1))
			System.out.println("\t2");
		else
			System.out.println("\t|");
		if (graphLane2.carAtElement(0))
			System.out.println("\t2");
		else
			System.out.println("\t|");
		System.out.println("\tV");
		System.out.println("\tD2");
	}

	public void carInsertion(int index) {
		if (r0.putLast(cars[index]) == false) {
			cars[index].set_bornTime(cars[index].get_bornTime() + 1);
		}
	}

	public void stepTopLanes() {
		graphLane1.step();
		graphLane2.step();
		Car temp = new Car(0, 0);
		if (s1.isGreen()) {
			if (r1.firstCar() != null)
				statistics(r1.firstCar());
			graphLane1.putLast(r1.getFirst());
			r1.step();
		} else {
			if (r1.findNull() != -1)
				r1.step(r1.findNull());
		}
		if (s2.isGreen()) {
			if (r2.firstCar() != null)
				statistics(r2.firstCar());
			graphLane2.putLast(r2.getFirst());
			r2.step();
		} else {
			if (r2.findNull() != -1)
				r2.step(r2.findNull());
		}
	}

	public void moveFirstCarToNextLaneAndStep() {
		int carDest = r0.firstCar().get_destination();
		if (carDest == 1) {
			if (r1.lastFree()) {
				r0.firstCar().set_timeBeforeEnteringLane(
						time - r0.firstCar().get_timeBeforeEnteringLane());
				r1.putLast(r0.getFirst());
				r0.step();
			} else {
				if (r0.firstCar().get_timeBeforeEnteringLane() != -1) {
					r0.firstCar().set_timeBeforeEnteringLane(time);
				}
				if (r0.findNull() != -1)
					r0.step(r0.findNull());
			}
		} else if (carDest == 2) {
			if (r2.lastFree()) {
				r0.firstCar().set_timeBeforeEnteringLane(
						time - r0.firstCar().get_timeBeforeEnteringLane());
				r2.putLast(r0.getFirst());
				r0.step();
			} else {
				if (r0.firstCar().get_timeBeforeEnteringLane() != -1) {
					r0.firstCar().set_timeBeforeEnteringLane(time);
					if (r0.findNull() != -1)
						r0.step(r0.findNull());
				}

			}
		}
	}
	public float avgCalculator(float sum, int amount) {
		return (sum / amount);
	}

	public void statistics(Car c) {
		if (c.get_destination() == 1) {
			nmbrOfCarsPassed++;
			nmbrOfCarsPassedLane1++;
			avgTimeToPassRedlight_sum += (time - c.get_bornTime());
			avgTimeToPassRedlightLane1_sum += (time - c.get_bornTime());
			avgTimeNotMoving_sum += (time - c.get_bornTime() - (laneLength1 + laneLength0));
			avgTimeNotMovingLane1_sum += (time - c.get_bornTime() - (laneLength1 + laneLength0));
			avgTimeMovingLane1_sum += (laneLength1 + laneLength0);
			avgTimeQueueing_sum += (c.get_bornTime() - c.get_initialBornTime());
			avgTimeQueueingLane1_sum += c.get_timeBeforeEnteringLane();

			avgTimeToPassRedlight = avgCalculator(avgTimeToPassRedlight_sum,
					nmbrOfCarsPassed);
			avgTimeToPassRedlightLane1 = avgCalculator(
					avgTimeToPassRedlightLane1_sum, nmbrOfCarsPassedLane1);
			avgTimeQueueing = avgCalculator(avgTimeQueueing_sum,
					nmbrOfCarsPassed);
			avgTimeNotMoving_sum = avgCalculator(avgTimeNotMoving_sum,
					nmbrOfCarsPassed);
			avgTimeNotMovingLane1_sum = avgCalculator(
					avgTimeNotMovingLane1_sum, nmbrOfCarsPassedLane1);
			avgTimeNotMoving = avgCalculator(avgTimeNotMoving_sum,
					nmbrOfCarsPassed);
			avgTimeNotMovingLane1 = avgCalculator(avgTimeNotMovingLane1_sum,
					nmbrOfCarsPassedLane1);
			avgTimeMovingLane1 = avgCalculator(avgTimeMoving_sum,
					nmbrOfCarsPassedLane1);
			avgTimeQueueingLane1 = avgCalculator(avgTimeQueueingLane1_sum,
					nmbrOfCarsPassedLane1);

		}

		else {
			nmbrOfCarsPassed++;
			nmbrOfCarsPassedLane2++;
			avgTimeToPassRedlight_sum += (time - c.get_bornTime());
			avgTimeToPassRedlightLane2_sum += (time - c.get_bornTime());
			avgTimeNotMoving_sum += (time - c.get_bornTime() - (laneLength2 + laneLength0));
			avgTimeNotMovingLane2_sum += (time - c.get_bornTime() - (laneLength2 + laneLength0));
			avgTimeMovingLane2_sum += (laneLength2 + laneLength0);
			avgTimeQueueing_sum += (c.get_bornTime() - c.get_initialBornTime());
			avgTimeQueueingLane2_sum += c.get_timeBeforeEnteringLane();

			avgTimeToPassRedlight = avgCalculator(avgTimeToPassRedlight_sum,
					nmbrOfCarsPassed);
			avgTimeToPassRedlightLane2 = avgCalculator(
					avgTimeToPassRedlightLane2_sum, nmbrOfCarsPassedLane2);
			avgTimeQueueing = avgCalculator(avgTimeQueueing_sum,
					nmbrOfCarsPassed);
			avgTimeNotMoving_sum = avgCalculator(avgTimeNotMoving_sum,
					nmbrOfCarsPassed);
			avgTimeNotMovingLane2_sum = avgCalculator(
					avgTimeNotMovingLane2_sum, nmbrOfCarsPassedLane2);
			avgTimeNotMoving = avgCalculator(avgTimeNotMoving_sum,
					nmbrOfCarsPassed);
			avgTimeNotMovingLane2 = avgCalculator(avgTimeNotMovingLane2_sum,
					nmbrOfCarsPassedLane2);
			avgTimeMovingLane2 = avgCalculator(avgTimeMoving_sum,
					nmbrOfCarsPassedLane2);
			avgTimeQueueingLane2 = avgCalculator(avgTimeQueueingLane2_sum,
					nmbrOfCarsPassedLane2);
		}

	}

}

/*
 * ratioBetweenMovingAndStandingStill;
 * 
 * avgTimeToPassRedlight; avgTimeToPassRedlight_sum; avgTimeToPassRedlightLane1;
 * avgTimeToPassRedlightLane1_sum; avgTimeToPassRedlightLane2;
 * avgTimeToPassRedlightLane2_sum;
 * 
 * avgTimeNotMoving; avgTimeNotMoving_counter; avgTimeNotMoving_sum;
 * avgTimeNotMovingLane1; avgTimeNotMovingLane1_counter;
 * avgTimeNotMovingLane1_sum; avgTimeNotMovingLane2;
 * avgTimeNotMovingLane2_counter; avgTimeNotMovingLane2_sum;
 * 
 * avgTimeQueueing; avgTimeQueueing_counter; avgTimeQueueing_sum;
 * 
 * avgTimeMoving; avgTimeMoving_counter; avgTimeMoving_sum; avgTimeMovingLane1;
 * avgTimeMovingLane1_counter; avgTimeMovingLane1_sum; avgTimeMovingLane2;
 * avgTimeMovingLane2_counter; avgTimeMovingLane2_sum;
 * 
 * nmbrOfCarsPassed; nmbrOfCarsPassedLane1; nmbrOfCarsPassedLane2;
 */
