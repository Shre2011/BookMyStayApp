import java.util.*;

class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void reduce(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }

    public void increase(String type) {
        inventory.put(type, inventory.get(type) + 1);
    }

    public void display() {
        System.out.println("Inventory: " + inventory);
    }
}

class BookingService {
    private RoomInventory inventory;
    private HashMap<String, String> reservationToRoomId;
    private HashMap<String, String> reservationToType;
    private Stack<String> rollbackStack;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        this.reservationToRoomId = new HashMap<>();
        this.reservationToType = new HashMap<>();
        this.rollbackStack = new Stack<>();
    }

    public void confirmBooking(Reservation r) {

        if (inventory.getAvailability(r.getRoomType()) <= 0) {
            System.out.println("Booking Failed for " + r.getGuestName());
            return;
        }

        String roomId = UUID.randomUUID().toString().substring(0, 5);

        reservationToRoomId.put(r.getReservationId(), roomId);
        reservationToType.put(r.getReservationId(), r.getRoomType());

        inventory.reduce(r.getRoomType());

        System.out.println("Booking Confirmed: " + r.getReservationId() +
                " | RoomID: " + roomId);
    }

    public void cancelBooking(String reservationId) {

        if (!reservationToRoomId.containsKey(reservationId)) {
            System.out.println("Cancellation Failed: Invalid reservation ID");
            return;
        }

        String roomId = reservationToRoomId.get(reservationId);
        String type = reservationToType.get(reservationId);

        rollbackStack.push(roomId);

        inventory.increase(type);

        reservationToRoomId.remove(reservationId);
        reservationToType.remove(reservationId);

        System.out.println("Booking Cancelled: " + reservationId +
                " | RoomID released: " + roomId);
    }

    public void displayRollbackStack() {
        System.out.println("Rollback Stack: " + rollbackStack);
    }
}

public class BookMyStay {
    public static void main(String[] args) {

        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService(inventory);

        Reservation r1 = new Reservation("R101", "Alice", "Single Room");

        service.confirmBooking(r1);
        inventory.display();

        System.out.println("\nCancelling Booking...\n");

        service.cancelBooking("R101");

        inventory.display();
        service.displayRollbackStack();
    }
}