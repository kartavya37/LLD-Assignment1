
public class FareCalculator {
	public static double calculateFareByKm(double km) {
		double fare = 50.0 + km * 6.6666666667;
		fare = Math.round(fare * 100.0) / 100.0;
		return fare;
	}
}
