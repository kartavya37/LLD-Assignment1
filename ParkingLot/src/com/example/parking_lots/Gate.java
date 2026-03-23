package src.com.example.parking_lots;

public class Gate {
    private int gateId;
    private int level;

    public Gate(int gateId, int level) {
        this.gateId = gateId;
        this.level = level;
    }

    public int getGateId() {
        return this.gateId;
    }

    public int getLevel() {
        return this.level;
    }
}
