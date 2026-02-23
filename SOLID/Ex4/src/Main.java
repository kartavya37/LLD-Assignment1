import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Hostel Fee Calculator ===");
        
        BookingRequest req = new BookingRequest(LegacyRoomTypes.DOUBLE, List.of(AddOn.LAUNDRY, AddOn.MESS));
        
        RoomRegistry roomRegistry = new RoomRegistry();
        RoomType selectedRoom = roomRegistry.getRoom(req.roomType); 

        List<AddOnRule> rules = List.of(new MessAddOn(), new LaundryAddOn(), new GymAddOn());

        HostelFeeCalculator calc = new HostelFeeCalculator(new FakeBookingRepo(), rules);
        calc.process(req, selectedRoom);
    }
}