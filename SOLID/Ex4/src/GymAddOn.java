public class GymAddOn implements AddOnRule {
    public boolean appliesTo(AddOn a) { return a == AddOn.GYM; }
    public double getPrice() { return 300.0; }
}