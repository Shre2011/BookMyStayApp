import java.util.HashMap;

abstract class Room {
    private int beds;
    private double price;
    private String type;

    public Room(String type, int beds, double price) {
        this.type = type;
        this.beds = beds;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public int getBeds() {
        return beds;
    }

    public double getPrice() {
        return price;
    }

    public void displayDetails() {
        System.out.println("Room Type: " + type);
        System.out.println("Beds: " + beds);
        System.out.println("Price: " + price);
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 2000);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 3500);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 6000);
    }
}

class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    public void displayInventory() {
        System.out.println("Current Room Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " -> " + inventory.get(type));
        }
    }
}

public class BookMyStay {
    public static void main(String[] args) {

        Room single = new SingleRoom();
        Room doubleroom = new DoubleRoom();
        Room suite = new SuiteRoom();

        RoomInventory inventory = new RoomInventory();

        System.out.println("Welcome to Book My Stay App\n");

        single.displayDetails();
        System.out.println("Available: " + inventory.getAvailability(single.getType()) + "\n");

        doubleroom.displayDetails();
        System.out.println("Available: " + inventory.getAvailability(doubleroom.getType()) + "\n");

        suite.displayDetails();
        System.out.println("Available: " + inventory.getAvailability(suite.getType()) + "\n");

        inventory.displayInventory();

        System.out.println("\nUpdating availability...\n");

        inventory.updateAvailability("Single Room", 4);
        inventory.updateAvailability("Suite Room", 1);

        inventory.displayInventory();

        System.out.println("\nApplication Finished.");
    }
}
