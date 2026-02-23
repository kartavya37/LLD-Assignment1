public class MessAddOn implements AddOnRule {
    public boolean appliesTo(AddOn a) { return a == AddOn.MESS; }
    public double getPrice() { return 1000.0; }
}