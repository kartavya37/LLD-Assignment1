public class Student extends Customer {

    public double taxPercent() {
        return 5.0;
    }
    public double discountAmount(double subtotal , int lines) {
        return subtotal >= 180.0 ? 10.0 : 0;
    }
}
