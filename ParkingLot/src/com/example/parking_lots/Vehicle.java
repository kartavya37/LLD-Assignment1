package src.com.example.parking_lots;
public class Vehicle {
    private String vehicleNumber;
    private VehicleType vehicleType;
    public Vehicle(String vehicleNumber, VehicleType vehicleType) {
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
    }
    public VehicleType getType() {
        return this.vehicleType;
    }
    public String getVehicleNumber() {
        return this.vehicleNumber;
    }
}
