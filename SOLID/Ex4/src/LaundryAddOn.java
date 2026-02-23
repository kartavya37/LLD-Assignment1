public class LaundryAddOn implements AddOnRule {
    public boolean appliesTo(AddOn a) { return a == AddOn.LAUNDRY; }
    public double getPrice() { return 500.0; }
}