public class Staff extends Customer {
    public double taxPercent() {
        return 2.0;
    }
    public double discountAmount(double subtotal , int lines) {
        return lines >=3 ? 15.0 : 0;
    }
}
