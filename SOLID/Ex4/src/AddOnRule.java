public interface AddOnRule {
    boolean appliesTo(AddOn addOn);
    double getPrice();
}