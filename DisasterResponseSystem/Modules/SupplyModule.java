package src.modules;

import src.core.Module;
import src.core.AbstractRequest;
import java.util.*;

public class SupplyModule extends Module {
    static class SupplyItem {
        private String name;
        private int quantity;
        public SupplyItem(String name, int quantity) { this.name = name; this.quantity = quantity; }
        public String getName() { return name; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int q) { quantity = q; }
    }

    static class ReliefRequest extends AbstractRequest {
        private final Map<String, Integer> items = new LinkedHashMap<>();
        public ReliefRequest(int id, String req, String loc) { super(id, req, loc); }
        public void addItem(String n, int q) { items.put(n, items.getOrDefault(n, 0) + q); }
        @Override public String displaySummary() {
            StringBuilder sb = new StringBuilder();
            sb.append("ReliefRequest ID: ").append(getId())
              .append(" | Requester: ").append(getRequester())
              .append(" | Location: ").append(getLocation())
              .append(" | Items: ");
            items.forEach((k,v)-> sb.append(k).append("(").append(v).append(") "));
            return sb.toString();
        }
    }

    private final List<SupplyItem> inventory = new ArrayList<>();
    private final List<ReliefRequest> requests = new ArrayList<>();
    private int nextRequestId = 1;
    private static final Scanner SC = new Scanner(System.in);

    @Override public String getModuleName() { return "Supply & Relief Requests"; }

    @Override public void run() {
        int choice;
        do {
            System.out.println("\n--- Supply Menu ---");
            System.out.println("1. Add Supply");
            System.out.println("2. View Supplies");
            System.out.println("3. Submit Relief Request");
            System.out.println("4. View Requests");
            System.out.println("5. Fulfill Request");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            choice = readIntRange(0, 5);
            switch (choice) {
                case 1 -> addSupply();
                case 2 -> viewSupplies();
                case 3 -> submitRequest();
                case 4 -> viewRequests();
                case 5 -> fulfillRequest();
            }
        } while (choice != 0);
    }

    void addSupply() {
        String name = readNonEmpty("Name: ");
        int qty = readInt("Qty: ");
        inventory.add(new SupplyItem(name, qty));
    }

    void viewSupplies() {
        for (SupplyItem s : inventory)
            System.out.println(s.getName() + " : " + s.getQuantity());
    }

    void submitRequest() {
        String requester = readNonEmpty("Requester: ");
        String loc = readNonEmpty("Location: ");
        ReliefRequest rr = new ReliefRequest(nextRequestId++, requester, loc);
        while (true) {
            System.out.print("Item (or done): ");
            String item = SC.nextLine().trim();
            if (item.equalsIgnoreCase("done")) break;
            int q = readInt("Qty: ");
            rr.addItem(item, q);
        }
        requests.add(rr);
        System.out.println(rr.displaySummary());
    }

    void viewRequests() {
        for (ReliefRequest r : requests) System.out.println(r.displaySummary());
    }

    void fulfillRequest() {
        if (requests.isEmpty()) return;
        int id = readInt("Request ID: ");
        ReliefRequest target = null;
        for (ReliefRequest r : requests) if (r.getId() == id) target = r;
        if (target == null) return;

        requests.remove(target);
        System.out.println("Request fulfilled.");
    }

    static int readInt(String p) {
        System.out.print(p);
        while (true) {
            try { return Integer.parseInt(SC.nextLine().trim()); }
            catch (Exception e) { System.out.print("Invalid: "); }
        }
    }

    static int readIntRange(int min, int max) {
        while (true) {
            try {
                int v = Integer.parseInt(SC.nextLine().trim());
                if (v >= min && v <= max) return v;
            } catch (Exception ignored) {}
            System.out.print("Try again: ");
        }
    }

    static String readNonEmpty(String p) {
        String s;
        do {
            System.out.print(p);
            s = SC.nextLine().trim();
        } while (s.isEmpty());
        return s;
    }
}
