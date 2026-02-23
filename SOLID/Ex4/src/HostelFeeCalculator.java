import java.util.*;

public class HostelFeeCalculator {
    private final FakeBookingRepo repo;
    private final List<AddOnRule> addOnRules;

    public HostelFeeCalculator(FakeBookingRepo repo, List<AddOnRule> rules) {
        this.repo = repo;
        this.addOnRules = rules;
    }

    public void process(BookingRequest req, RoomType room) {
        Money monthly = calculateMonthly(req, room);
        Money deposit = new Money(5000.00);

        // Preservation of Output: ReceiptPrinter handles the display
        printReceipt(room.getName(), req.addOns, monthly, deposit);

        String bookingId = "H-" + (7000 + new Random(1).nextInt(1000));
        repo.save(bookingId, req, monthly, deposit);
    }

    private Money calculateMonthly(BookingRequest req, RoomType room) {
        double total = room.getBaseRate();
        
        for (AddOn a : req.addOns) {
            for (AddOnRule rule : addOnRules) {
                if (rule.appliesTo(a)) {
                    total += rule.getPrice();
                }
            }
        }
        return new Money(total);
    }

    private void printReceipt(String roomName, List<AddOn> addOns, Money monthly, Money deposit) {
        System.out.println("Room: " + roomName + " | AddOns: " + addOns);
        System.out.println("Monthly: " + monthly);
        System.out.println("Deposit: " + deposit);
        System.out.println("TOTAL DUE NOW: " + monthly.plus(deposit));
    }
}