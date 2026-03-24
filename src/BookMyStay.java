import java.util.*;

class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class BookingQueue {
    private Queue<Reservation> queue;

    public BookingQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation r) {
        queue.add(r);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void reduceAvailability(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }

    public void displayInventory() {
        System.out.println("Inventory: " + inventory);
    }
}

class BookingService {
    private RoomInventory inventory;
    private Set<String> allocatedRoomIds;
    private HashMap<String, Set<String>> roomAllocations;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        this.allocatedRoomIds = new HashSet<>();
        this.roomAllocations = new HashMap<>();
    }

    private String generateRoomId(String type) {
        return type.substring(0, 2).toUpperCase() + UUID.randomUUID().toString().substring(0, 4);
    }

    public void processBookings(BookingQueue queue) {

        while (!queue.isEmpty()) {

            Reservation r = queue.getNextRequest();
            String type = r.getRoomType();

            if (inventory.getAvailability(type) > 0) {

                String roomId;

                do {
                    roomId = generateRoomId(type);
                } while (allocatedRoomIds.contains(roomId));

                allocatedRoomIds.add(roomId);

                roomAllocations.putIfAbsent(type, new HashSet<>());
                roomAllocations.get(type).add(roomId);

                inventory.reduceAvailability(type);

                System.out.println("Booking Confirmed for " + r.getGuestName() +
                        " | Room: " + type + " | ID: " + roomId);

            } else {
                System.out.println("Booking Failed for " + r.getGuestName() +
                        " | No rooms available for " + type);
            }
        }
    }

    public void displayAllocations() {
        System.out.println("\nAllocated Rooms:");
        for (String type : roomAllocations.keySet()) {
            System.out.println(type + " -> " + roomAllocations.get(type));
        }
    }
}

public class BookMyStay {
    public static void main(String[] args) {

        BookingQueue queue = new BookingQueue();

        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Single Room"));
        queue.addRequest(new Reservation("Charlie", "Single Room"));
        queue.addRequest(new Reservation("David", "Suite Room"));

        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService(inventory);

        System.out.println("Processing Bookings...\n");

        service.processBookings(queue);

        service.displayAllocations();

        System.out.println();
        inventory.displayInventory();
    }
}