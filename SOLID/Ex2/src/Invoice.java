import java.util.List;
public class Invoice {
    String invId;
    double subtotal;
    double taxPct;
    double tax;
    double discount;
    double total;
    List<InvoiceItem> items;

    public Invoice(String invId, double subtotal, double taxPct,double tax, double discount, double total,List<InvoiceItem> items) {
        this.invId = invId;
        this.subtotal = subtotal;
        this.taxPct = taxPct;
        this.tax = tax;
        this.discount = discount;
        this.total = total;
        this.items = items;
    }

    public String getId() {
        return invId;
    }
    public List<InvoiceItem> getItems() {
        return items;
    }
    public double getSubtotal() {
        return subtotal;
    }
    public double getTaxAmount() {
        return tax;
    }
    public double getTaxPercent() {
        return taxPct;
    }
    public double getDiscount() {
        return discount;
    }
    public double getTotal() {
        return total;
    }
}