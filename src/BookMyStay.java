import java.util.*;

class AddOnService {
    private String name;
    private double cost;

    public AddOnService(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }
}

class AddOnServiceManager {
    private HashMap<String, List<AddOnService>> serviceMap;

    public AddOnServiceManager() {
        serviceMap = new HashMap<>();
    }

    public void addService(String reservationId, AddOnService service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);
    }

    public void displayServices(String reservationId) {
        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        System.out.println("Add-On Services for Reservation " + reservationId + ":");
        for (AddOnService s : services) {
            System.out.println("- " + s.getName() + " : " + s.getCost());
        }
    }

    public double calculateTotalCost(String reservationId) {
        List<AddOnService> services = serviceMap.get(reservationId);
        double total = 0;

        if (services != null) {
            for (AddOnService s : services) {
                total += s.getCost();
            }
        }

        return total;
    }
}

public class BookMyStay {
    public static void main(String[] args) {

        String reservationId = "SR1234";

        AddOnService wifi = new AddOnService("WiFi", 500);
        AddOnService breakfast = new AddOnService("Breakfast", 300);
        AddOnService parking = new AddOnService("Parking", 200);

        AddOnServiceManager manager = new AddOnServiceManager();

        manager.addService(reservationId, wifi);
        manager.addService(reservationId, breakfast);
        manager.addService(reservationId, parking);

        manager.displayServices(reservationId);

        double totalCost = manager.calculateTotalCost(reservationId);

        System.out.println("Total Add-On Cost: " + totalCost);
        System.out.println("\nCore booking and inventory remain unchanged.");
    }
}