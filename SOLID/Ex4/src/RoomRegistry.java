import java.util.HashMap;
import java.util.Map;

public class RoomRegistry {
    private final Map<Integer, RoomType> registry = new HashMap<>();

    public RoomRegistry() {
        // Register all your room types here
        registry.put(LegacyRoomTypes.SINGLE, new SingleRoom());
        registry.put(LegacyRoomTypes.DOUBLE, new DoubleRoom());
        registry.put(LegacyRoomTypes.TRIPLE, new TripleRoom());
        registry.put(LegacyRoomTypes.DELUXE, new DeluxeRoom());
    }

    public RoomType getRoom(int type) {
        RoomType room = registry.get(type);
        if (room == null) throw new IllegalArgumentException("Invalid room type: " + type);
        return room;
    }
}