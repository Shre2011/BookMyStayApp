import java.util.*;

class Reservation {
    private String guestName;
    private String roomType;
    private String reservationId;

    public Reservation(String guestName, String roomType, String reservationId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.reservationId = reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public void display() {
        System.out.println("ID: " + reservationId + " | Guest: " + guestName + " | Room: " + roomType);
    }
}

class BookingHistory {
    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    public void addReservation(Reservation r) {
        history.add(r);
    }

    public List<Reservation> getAllReservations() {
        return history;
    }
}

class BookingReportService {

    public void displayAllBookings(List<Reservation> history) {
        System.out.println("\nBooking History:");
        for (Reservation r : history) {
            r.display();
        }
    }

    public void generateSummary(List<Reservation> history) {
        HashMap<String, Integer> countMap = new HashMap<>();

        for (Reservation r : history) {
            String type = r.getRoomType();
            countMap.put(type, countMap.getOrDefault(type, 0) + 1);
        }

        System.out.println("\nBooking Summary Report:");
        for (String type : countMap.keySet()) {
            System.out.println(type + " booked: " + countMap.get(type));
        }
    }
}

public class BookMyStay {
    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        Reservation r1 = new Reservation("Alice", "Single Room", "SR101");
        Reservation r2 = new Reservation("Bob", "Double Room", "DR102");
        Reservation r3 = new Reservation("Charlie", "Single Room", "SR103");

        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        reportService.displayAllBookings(history.getAllReservations());
        reportService.generateSummary(history.getAllReservations());
    }
}