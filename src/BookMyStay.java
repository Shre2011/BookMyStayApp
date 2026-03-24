import java.io.*;
import java.util.*;

class Reservation implements Serializable {
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

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println(reservationId + " | " + guestName + " | " + roomType);
    }
}

class RoomInventory implements Serializable {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
    }

    public HashMap<String, Integer> getInventory() {
        return inventory;
    }

    public void display() {
        System.out.println("Inventory: " + inventory);
    }
}

class SystemState implements Serializable {
    List<Reservation> bookings;
    RoomInventory inventory;

    public SystemState(List<Reservation> bookings, RoomInventory inventory) {
        this.bookings = bookings;
        this.inventory = inventory;
    }
}

class PersistenceService {

    private static final String FILE_NAME = "system_state.ser";

    public void save(SystemState state) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(state);
            System.out.println("System state saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    public SystemState load() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            SystemState state = (SystemState) in.readObject();
            System.out.println("System state loaded successfully.");
            return state;
        } catch (Exception e) {
            System.out.println("No previous state found. Starting fresh.");
            return new SystemState(new ArrayList<>(), new RoomInventory());
        }
    }
}

public class BookMyStay {
    public static void main(String[] args) {

        PersistenceService persistence = new PersistenceService();

        SystemState state = persistence.load();

        List<Reservation> bookings = state.bookings;
        RoomInventory inventory = state.inventory;

        System.out.println("\nRecovered Data:");
        inventory.display();

        for (Reservation r : bookings) {
            r.display();
        }

        System.out.println("\nAdding new booking...");

        Reservation newBooking = new Reservation("R200", "Alice", "Single Room");
        bookings.add(newBooking);

        persistence.save(new SystemState(bookings, inventory));

        System.out.println("\nApplication End.");
    }
}