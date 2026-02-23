import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InvoiceCalculator {
    public static Invoice calculate(String invId, Customer customerType, List<OrderLine> lines ,Map<String, MenuItem> menu ) {

        
        double subtotal = 0.0;
        List<InvoiceItem> list = new ArrayList<>();
        for (OrderLine l : lines) {
            MenuItem item = menu.get(l.itemId);
            double lineTotal = item.price * l.qty;
            subtotal += lineTotal;
            list.add(new InvoiceItem(item.name, l.qty, lineTotal));
        }
        double taxPct = customerType.taxPercent();
        double tax = subtotal * (taxPct / 100.0);

        double discount = customerType.discountAmount(subtotal, lines.size());
        double total = subtotal + tax - discount;

        return new Invoice(invId, subtotal, taxPct, tax, discount, total, list);
    }
}
