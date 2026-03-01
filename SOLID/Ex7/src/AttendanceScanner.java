public class AttendanceScanner implements SmartClassroomDevice {
	@Override
	public void powerOn() {
		/* ok */ }

	@Override
	public void powerOff() {
		/* no output */ }

	// No interface for this because this function is way too specific
	public int scanAttendance() {
		return 3;
	}
}
