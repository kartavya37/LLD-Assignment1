public class InvoiceFormatter {
    // pointless wrapper (smell)
    public static String identityFormat(Invoice invoice) {

        StringBuilder out = new StringBuilder();
        out.append("Invoice# ").append(invoice.getId()).append("\n");

        for (InvoiceItem item : invoice.getItems()) {
            out.append(String.format("- %s x%d = %.2f\n", item.name, item.qty, item.lineTotal));
        }
        out.append(String.format("Subtotal: %.2f\n", invoice.getSubtotal()));
        out.append(String.format("Tax(%.0f%%): %.2f\n", invoice.getTaxPercent(), invoice.getTaxAmount()));
        out.append(String.format("Discount: -%.2f\n", invoice.getDiscount()));
        out.append(String.format("TOTAL: %.2f\n", invoice.getTotal()));
        return out.toString();
    }
}
