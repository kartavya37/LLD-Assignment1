import java.util.*;

public class CafeteriaSystem {
    private final Map<String, MenuItem> menu = new LinkedHashMap<>();
    private final InvoiceRepo store;
    private int invoiceSeq = 1000;

    CafeteriaSystem(InvoiceRepo store) {
        this.store = store;
    }
    
    public void addToMenu(MenuItem i) { menu.put(i.id, i); }

    // Intentionally SRP-violating: menu mgmt + tax + discount + format + persistence.
    public void checkout(Customer customerType, List<OrderLine> lines) {

        String invId = "INV-" + (++invoiceSeq);
        
        Invoice invoice = InvoiceCalculator.calculate(invId, customerType, lines, menu);
        String printable = InvoiceFormatter.identityFormat(invoice);
        System.out.print(printable);

        store.save(invId, printable);
        System.out.println("Saved invoice: " + invId + " (lines=" + store.countLines(invId) + ")");
    }
}
