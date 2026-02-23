public class InvoiceItem {
    
    public final String name;
    public final int qty;
    public final double lineTotal;

    public InvoiceItem(String name, int qty, double lineTotal) {
        this.name = name;
        this.qty = qty;
        this.lineTotal = lineTotal;
    }
}
